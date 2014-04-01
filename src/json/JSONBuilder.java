package json;

import ban.Rule;
import idealisedprotocol.IdealisedMessage;
import message.BanObject;
import message.BanObjectType;
import message.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ban.ActionType.SEES;

public class JSONBuilder {

    private BuildMessagesFromJSON fromJSON;
    private List<Rule> idealisedRules = new ArrayList<>();
    private List<Rule> assumptions = new ArrayList<>();

    public JSONBuilder(String protocolResource, String assumptionsResource) {
        this.fromJSON = new BuildMessagesFromJSON();

        buildIdealisedProtocol(protocolResource);
        buildAssumptionsFromJSON(assumptionsResource);
    }

    private void buildIdealisedProtocol(String protocolResource) {
        List<IdealisedMessage> idealisedMessages = new ArrayList<>();
        try {
            idealisedMessages = fromJSON.buildIdealisedMessages(protocolResource);
        } catch (IOException e) {
            System.out.println("wrong path");
        }
        for (IdealisedMessage idealisedMessage : idealisedMessages) {
            if (idealisedMessage.getMessage().getType().equals(BanObjectType.ENCRYPTED_MESSAGE)) {
                idealisedRules.add(new Rule(idealisedMessage.getReceiver(), SEES, idealisedMessage.getMessage()));
            } else if (idealisedMessage.getMessage().getType().equals(BanObjectType.MESSAGE)) {
                for (BanObject banObject : ((Message) idealisedMessage.getMessage()).getMessageList()) {
                    idealisedRules.add(new Rule(idealisedMessage.getReceiver(), SEES, banObject));
                }
            }
        }
    }

    private void buildAssumptionsFromJSON(String assumptionsResource) {
        try {
            assumptions = fromJSON.buildAssumptions(assumptionsResource);
        } catch (IOException e) {
            System.out.println("wrong path");
        }
    }

    public List<Rule> getIdealisedRules() {
        return idealisedRules;
    }

    public List<Rule> getAssumptions() {
        return assumptions;
    }
}
