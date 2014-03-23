import message.Agent;
import message.Key;
import message.Message;
import message.Nonce;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Message> messages = new ArrayList<Message>();
        Message message1 = new Message();
        Nonce nonce1 = new Nonce();
        message1.add(new Agent("A"));
        message1.add(nonce1);
        message1.add(new Key());
        message1.removesTheFirstOccurrence(nonce1);
        System.out.println("Size:" + message1.getMessageList().size());

        Message message2 = new Message();
        message1.add(new Agent("B"));
        message1.add(new Agent("S"));

        messages.add(message1);
        messages.add(message2);

        if((messages.get(0).getMessageList().get(0)) instanceof Agent) {
            System.out.println(((Agent)(messages.get(0).getMessageList().get(0))).getIdentity());
        }

    }
}
