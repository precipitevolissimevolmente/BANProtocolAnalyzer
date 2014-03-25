package message;

import java.util.ArrayList;
import java.util.List;

import static message.BanObjectType.MESSAGE;

public class Message implements BanObject {
    private boolean fresh;
    List<BanObject> messageList = new ArrayList<BanObject>();

    public Message() {
    }

    public Message(boolean fresh) {
        this.fresh = fresh;
    }

    public boolean isFresh() {
        return fresh;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    public List<BanObject> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<BanObject> messageList) {
        this.messageList = messageList;
    }

    public void add(BanObject o) {
        messageList.add(o);
    }

    public void  removesTheFirstOccurrence(BanObject o) {
        messageList.remove(o);
    }

    public void removeByIndex(int index) {
        messageList.remove(index);
    }

    @Override
    public BanObjectType getType() {
        return MESSAGE;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageList=" + messageList +
                '}';
    }
}
