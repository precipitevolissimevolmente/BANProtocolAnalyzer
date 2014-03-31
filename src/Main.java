import AppLogic.RuleBuilder;
import AppLogic.RuleContainer;
import ban.ActionType;
import ban.Rule;
import idealisedprotocol.IdealisedMessage;
import idealisedprotocol.IdealisedMessagesUtil;
import json.BuildMessagesFromJSON;
import message.BanObject;
import message.BanObjectType;
import message.Message;
import message.Principal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ban.ActionType.BELIEVES;
import static ban.ActionType.SAID;
import static ban.ActionType.SEES;

public class Main {
    public static void main(String[] args) throws IOException {

        List<Rule> KerberosIdealisedMessages = IdealisedMessagesUtil.getIdealisedProtocol("/resources/Kerberos.json");
//        List<Rule> NSSKIdealisedMessages = IdealisedMessagesUtil.getIdealisedProtocol("/resources/Needham-Schroeder-Shared-keys.json");

        boolean fresh = true;
        Message messageX = new Message(fresh);
        Principal P = new Principal("P");
        Rule rule1 = new Rule(P, BELIEVES, messageX);
        Rule rule2 = new Rule(P, BELIEVES, new Rule(new Principal("Q"), SAID, messageX));

        RuleBuilder.CombineRuleWith2Parameters(rule1, rule2);

        System.out.println(RuleContainer.Rules.size());
        System.out.println("--------------");

    }

    private static List<Rule> getIdealisedProtocol(List<IdealisedMessage> idealisedMessages) {

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
