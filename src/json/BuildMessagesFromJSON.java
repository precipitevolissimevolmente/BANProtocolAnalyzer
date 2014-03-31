package json;


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
    private static final String ENCRYPTED_MESSAGE = "encryptedMessage";


    public List<IdealisedMessage> build(String jsonFile) throws IOException {
        List<IdealisedMessage> idealisedMessages = new ArrayList<>();

        InputStream is =
                BuildMessagesFromJSON.class.getResourceAsStream(jsonFile);
        String jsonTxt = IOUtils.toString(is);

        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(jsonTxt);
        JSONArray protocolMessages = jsonObject.getJSONArray("protocolMessages");

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
            Key key = new Key();
            key.setKeyType(KeyType.SHARED_KEY);
            key.setP(p);
            key.setQ(q);

            return getNewOrExistentKey(key);
        }

        return null;
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


    public class FindKey implements Predicate<Key> {
        private Key keyToFind;

        public FindKey(Key keyToFind) {
            this.keyToFind = keyToFind;
        }

        @Override
        public boolean apply(Key key) {
            return key.getKeyType().equals(keyToFind.getKeyType()) && key.getP().equals(keyToFind.getP()) && key.getQ().equals(keyToFind.getQ());
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
