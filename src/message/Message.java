package message;

import java.util.ArrayList;
import java.util.List;

import static message.BanObjectType.MESSAGE;

public class Message implements BanObject {
    List<BanObject> messageList = new ArrayList<BanObject>();

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
        return MESSAGE;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
