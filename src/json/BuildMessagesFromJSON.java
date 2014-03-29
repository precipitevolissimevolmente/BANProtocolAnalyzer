package json;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import idealisedprotocol.IdealisedMessage;
import message.Principal;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.contains;
import static com.google.common.collect.Iterables.find;
import static com.google.common.collect.Iterables.transform;

public class BuildMessagesFromJSON {
    private List<Principal> principals = new ArrayList<Principal>();

    public List<IdealisedMessage> build(String jsonFile) throws IOException {
        List<IdealisedMessage> idealisedMessages = new ArrayList<IdealisedMessage>();

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
//            idealisedMessage.setMessage(ge);
            idealisedMessages.add(idealisedMessage);
        }

        return idealisedMessages;
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
        if(contains(transform(principals, principalToIdentity()), principalIdentity)) {
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
