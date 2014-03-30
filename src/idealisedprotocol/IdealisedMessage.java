package idealisedprotocol;

import message.BanObject;
import message.Message;
import message.Principal;

public class IdealisedMessage {
    private Principal sender;
    private Principal receiver;
    private BanObject message;

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

    public BanObject getMessage() {
        return message;
    }

    public void setMessage(BanObject message) {
        this.message = message;
    }
}
