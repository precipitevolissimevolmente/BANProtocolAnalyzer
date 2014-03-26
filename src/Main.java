import AppLogic.RuleBuilder;
import AppLogic.RuleContainer;
import ban.Action;
import ban.ActionType;
import ban.Rule;
import message.*;

import java.util.ArrayList;
import java.util.List;

import static ban.ActionType.BELIEVES;
import static ban.ActionType.CONTROLS;
import static ban.ActionType.SAID;

public class Main {
    public static void main(String[] args) {
        List<Message> messages = new ArrayList<Message>();
        Message message1 = new Message();
        Nonce nonce1 = new Nonce();
        message1.add(new Principal("A"));
        message1.add(nonce1);
        message1.add(new Key());
        message1.removesTheFirstOccurrence(nonce1);
        System.out.println("Size:" + message1.getMessageList().size());

        Message message2 = new Message();
        message1.add(new Principal("B"));
        message1.add(new Principal("S"));

        messages.add(message1);
        messages.add(message2);

        boolean fresh = true;
        Message messageX = new Message(fresh);
        Principal P = new Principal("P");
        Rule rule1 = new Rule(P, BELIEVES, messageX);
        Rule rule2 = new Rule(P, BELIEVES, new Rule(new Principal("Q"), SAID, messageX));

        Rule rule = RuleBuilder.CombineRuleWith2Parameters(rule1, rule2);

        System.out.println(RuleContainer.Rules.size());
        System.out.println("--------------");
        System.out.println(rule);

    }
}
