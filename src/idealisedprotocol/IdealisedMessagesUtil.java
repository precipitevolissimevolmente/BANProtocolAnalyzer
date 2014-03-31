package idealisedprotocol;

import ban.Rule;
import json.BuildMessagesFromJSON;
import message.BanObject;
import message.BanObjectType;
import message.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ban.ActionType.SEES;

public class IdealisedMessagesUtil {
    public static List<Rule> getIdealisedProtocol(String resource) {
        BuildMessagesFromJSON fromJSON = new BuildMessagesFromJSON();
        List<IdealisedMessage> idealisedMessages = null;
        try {
            idealisedMessages = fromJSON.build(resource);
        } catch (IOException e) {
            return null;
        }
        List<Rule> listPAM = new ArrayList<>();
        for (IdealisedMessage idealisedMessage : idealisedMessages) {
            if(idealisedMessage.getMessage().getType().equals(BanObjectType.ENCRYPTED_MESSAGE)) {
                listPAM.add(new Rule(idealisedMessage.getReceiver(), SEES, idealisedMessage.getMessage()));
            } else if (idealisedMessage.getMessage().getType().equals(BanObjectType.MESSAGE)) {
                for (BanObject banObject : ((Message) idealisedMessage.getMessage()).getMessageList()) {
                    listPAM.add(new Rule(idealisedMessage.getReceiver(), SEES, banObject));
                }
            }
        }
        return listPAM;
    }
}
