package json;


import ban.ActionType;
import ban.Rule;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import idealisedprotocol.IdealisedMessage;
import message.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.*;

public class BuildMessagesFromJSON {
    private List<Principal> principals = new ArrayList<>();
    private List<Key> keys = new ArrayList<>();
    private List<TimeStmp> timeStamps = new ArrayList<>();
    private List<Nonce> nonces = new ArrayList<>();
    private static final String ENCRYPTED_MESSAGE = "encryptedMessage";
    private static String NAME = "name";


    public List<IdealisedMessage> buildIdealisedMessages(String jsonFile) throws IOException {
        List<IdealisedMessage> idealisedMessages = new ArrayList<>();

        JSONArray protocolMessages = getJSONArray(jsonFile, "protocolMessages");

        for (int i = 0; i < protocolMessages.size(); i++) {
            IdealisedMessage idealisedMessage = new IdealisedMessage();
            JSONObject jsonIdealisedMessage = protocolMessages.getJSONObject(i);
            idealisedMessage.setSender(getSender(jsonIdealisedMessage));
            idealisedMessage.setReceiver(getReceiver(jsonIdealisedMessage));
            idealisedMessage.setMessage(getMessages(jsonIdealisedMessage.getJSONArray("messages")));
            idealisedMessages.add(idealisedMessage);
        }

        return idealisedMessages;
    }

    public List<Rule> buildAssumptions(String jsonFile) throws IOException {
        List<Rule> rules = new ArrayList<>();

        JSONArray protocolMessages = getJSONArray(jsonFile, "assumptions");

        for (int i = 0; i < protocolMessages.size(); i++) {
            rules.add(getRule(protocolMessages.getJSONObject(i)));
        }

        return rules;
    }

    private BanObject getRight(JSONObject jsonObject) {
        switch (jsonObject.getString("type").toLowerCase()) {
            case "key":
                return getKey(jsonObject);
            case "timestamp":
                return getTimeStamp(jsonObject);
            case "nonce":
                return getNonce(jsonObject);
            case "encryptedmessage":
                return getEncryptedMessage(jsonObject);
            case "rule":
                return getRule(jsonObject);
        }

        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    private Rule getRule(JSONObject jsonRule) {
        Rule rule = new Rule();
        rule.setLeft(getLeft(jsonRule));
        rule.setAction(getAction(jsonRule));
        rule.setRight(getRight(jsonRule.getJSONObject("right")));
        return rule;
    }

    private ActionType getAction(JSONObject jsonRule) {
        String actionName = jsonRule.getString("action");
        return ActionType.valueOf(actionName.toUpperCase());
    }

    private BanObject getLeft(JSONObject jsonRule) {
        String principalIdentity = jsonRule.getString("left");
        boolean isTrustedAuthority = jsonRule.has("leftTA");
        return getPrincipal(principalIdentity, isTrustedAuthority);
    }

    private JSONArray getJSONArray(String jsonFile, String id) throws IOException {
        InputStream is =
                BuildMessagesFromJSON.class.getResourceAsStream(jsonFile);
        String jsonTxt = IOUtils.toString(is);

        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(jsonTxt);
        return jsonObject.getJSONArray(id);
    }

    private BanObject getMessages(JSONArray messages) {
        if (messages.size() == 1) {
            return buildMessage(messages.getJSONObject(0));
        }

        Message message = new Message();
        for (int i = 0; i < messages.size(); i++) {
            BanObject banObject = buildMessage(messages.getJSONObject(i));
            message.add(banObject);
        }
        return message;
    }

    private BanObject buildMessage(JSONObject jsonObject) {
        if (jsonObject.getString("type").equalsIgnoreCase(ENCRYPTED_MESSAGE)) {
            return getEncryptedMessage(jsonObject);
        }

        return null;
    }

    private Message getMessage(JSONArray messageJSONArray) {
        Message message = new Message();
        for (int j = 0; j < messageJSONArray.size(); j++) {
            switch (messageJSONArray.getJSONObject(j).getString("type").toLowerCase()) {
                case "key":
                    message.add(getKey(messageJSONArray.getJSONObject(j)));
                    break;
                case "timestamp":
                    message.add(getTimeStamp(messageJSONArray.getJSONObject(j)));
                    break;
                case "nonce":
                    message.add(getNonce(messageJSONArray.getJSONObject(j)));
                    break;
                case "encryptedmessage":
                    message.add(getEncryptedMessage(messageJSONArray.getJSONObject(j)));
                    break;
            }
        }

        return message;
    }

    private BanObject getEncryptedMessage(JSONObject jsonObject) {
        JSONObject keyJSON = jsonObject.getJSONObject("key");
        EncryptedMessage message = new EncryptedMessage(getKey(keyJSON));
        message.setMessage(getMessage(jsonObject.getJSONArray("message")));
        return message;
    }

    private BanObject getNonce(JSONObject jsonObject) {
        Principal identity = getPrincipal(jsonObject.getString("identity"), false);
        boolean fresh = jsonObject.has("fresh");
        Nonce nonce = new Nonce();
        nonce.setNonceIdentity(identity);
        nonce.setFresh(fresh);

        if (jsonObject.has(NAME)) {
            nonce.setNonceName(jsonObject.getString(NAME));
        }

        return getNewOrExistentNonce(nonce);
    }

    private BanObject getTimeStamp(JSONObject jsonObject) {
        Principal identity = getPrincipal(jsonObject.getString("identity"), false);
        boolean fresh = jsonObject.has("fresh");
        TimeStmp timeStmp = new TimeStmp();
        timeStmp.setNonceIdentity(identity);
        timeStmp.setFresh(fresh);

        return getNewOrExistentTimeStamp(timeStmp);
    }

    private Key getKey(JSONObject keyJSON) {
        String keyTypeJSON = keyJSON.getString("keyType");
        if (KeyType.SHARED_KEY.name().equalsIgnoreCase(keyTypeJSON)) {
            JSONArray keyPrincipalsJSON = keyJSON.getJSONArray("between");
            Principal p = getPrincipal(keyPrincipalsJSON.getString(0), false);
            Principal q = getPrincipal(keyPrincipalsJSON.getString(1), false);
            boolean fresh = keyJSON.has("fresh");
            Key key = new Key();
            key.setKeyType(KeyType.SHARED_KEY);
            key.setP(p);
            key.setQ(q);
            key.setFresh(fresh);
            if (keyJSON.has(NAME)) {
                key.setKeyName(keyJSON.getString(NAME));
            }
            return getNewOrExistentKey(key);
        }

        return null;
    }

    private BanObject getNewOrExistentNonce(Nonce nonce) {
        if(nonces.contains(nonce)) {
            return find(nonces, new FindNonce(nonce));
        }
        nonces.add(nonce);
        return nonce;
    }

    private BanObject getNewOrExistentTimeStamp(TimeStmp timeStmp) {
        if (timeStamps.contains(timeStmp)) {
            return find(timeStamps, new FindTimeStmp(timeStmp));
        }
        timeStamps.add(timeStmp);
        return timeStmp;
    }

    private Key getNewOrExistentKey(Key key) {
        if (keys.contains(key)) {
            return find(keys, new FindKey(key));
        }
        keys.add(key);
        return key;
    }

    private Principal getSender(JSONObject jsonIdealisedMessage) {
        String principalIdentity = jsonIdealisedMessage.getString("sender");
        boolean isTrustedAuthority = jsonIdealisedMessage.has("senderTA");
        return getPrincipal(principalIdentity, isTrustedAuthority);
    }

    private Principal getReceiver(JSONObject jsonIdealisedMessage) {
        String principalIdentity = jsonIdealisedMessage.getString("receiver");
        boolean isTrustedAuthority = jsonIdealisedMessage.has("receiverTA");
        return getPrincipal(principalIdentity, isTrustedAuthority);
    }

    private Principal getPrincipal(String principalIdentity, boolean trustedAuthority) {
        if (contains(transform(principals, principalToIdentity()), principalIdentity)) {
            return find(principals, new FindPrincipal(principalIdentity));
        }
        Principal newPrincipal = new Principal(principalIdentity, trustedAuthority);
        principals.add(newPrincipal);

        return newPrincipal;
    }

    private Function<Principal, String> principalToIdentity() {
        return new Function<Principal, String>() {
            public String apply(Principal from) {
                return from.getIdentity();
            }
        };
    }

    private class FindTimeStmp implements Predicate<TimeStmp> {
        private TimeStmp timeStmpToFind;

        public FindTimeStmp(TimeStmp timeStmp) {
            this.timeStmpToFind = timeStmp;
        }

        @Override
        public boolean apply(TimeStmp timeStmp) {
            return this.timeStmpToFind.equals(timeStmp);
        }
    }

    private class FindNonce implements Predicate<Nonce> {
        private Nonce nonceToFind;

        public FindNonce(Nonce nonceToFind) {
            this.nonceToFind = nonceToFind;
        }

        @Override
        public boolean apply(Nonce nonce) {
            return this.nonceToFind.equals(nonce);
        }
    }

    public class FindKey implements Predicate<Key> {
        private Key keyToFind;

        public FindKey(Key keyToFind) {
            this.keyToFind = keyToFind;
        }

        @Override
        public boolean apply(Key key) {
            return key.equals(keyToFind);
        }
    }

    public class FindPrincipal implements Predicate<Principal> {
        private String stringToFind;

        public FindPrincipal(String stringToFind) {
            this.stringToFind = stringToFind;
        }

        @Override
        public boolean apply(Principal principal) {
            return principal.getIdentity().equals(stringToFind);
        }
    }
}
