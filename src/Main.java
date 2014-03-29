import AppLogic.RuleBuilder;
import AppLogic.RuleContainer;
import ban.Rule;
import idealisedprotocol.IdealisedMessage;
import json.BuildMessagesFromJSON;
import message.Message;
import message.Principal;

import java.io.IOException;
import java.util.List;

import static ban.ActionType.BELIEVES;
import static ban.ActionType.SAID;

public class Main {
    public static void main(String[] args) throws IOException {
        BuildMessagesFromJSON fromJSON = new BuildMessagesFromJSON();
        List<IdealisedMessage> idealisedMessages = fromJSON.build("/resources/Kerberos.json");

        boolean fresh = true;
        Message messageX = new Message(fresh);
        Principal P = new Principal("P");
        Rule rule1 = new Rule(P, BELIEVES, messageX);
        Rule rule2 = new Rule(P, BELIEVES, new Rule(new Principal("Q"), SAID, messageX));

        RuleBuilder.CombineRuleWith2Parameters(rule1, rule2);

        System.out.println(RuleContainer.Rules.size());
        System.out.println("--------------");

    }
}
