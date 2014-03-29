package idealisedprotocol;

import message.Message;
import message.Principal;

public class IdealisedMessage {
    private Principal sender;
    private Principal receiver;
    private Message message;

    public Principal getSender() {
        return sender;
    }

    public void setSender(Principal sender) {
        this.sender = sender;
    }

    public Principal getReceiver() {
        return receiver;
    }

    public void setReceiver(Principal receiver) {
        this.receiver = receiver;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
