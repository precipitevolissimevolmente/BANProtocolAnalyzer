import ban.Action;
import ban.ActionType;
import ban.Rule;
import message.*;

import java.util.ArrayList;
import java.util.List;

import static ban.ActionType.BELIEVES;
import static ban.ActionType.CONTROLS;

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

        if((messages.get(0).getMessageList().get(0)) instanceof Principal) {
            System.out.println(((Principal)(messages.get(0).getMessageList().get(0))).getIdentity());
        }

        Rule banObject = new Rule(new Principal("A"), new Action(BELIEVES), new Rule(new Principal("B"), new Action(CONTROLS), new Nonce()));

        if(banObject.getType() == BanObjectType.RULE) {
            if(banObject.getType() == BanObjectType.PRINCIPAL) {}
        }
    }
}
