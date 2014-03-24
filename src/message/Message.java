package message;

import java.util.ArrayList;
import java.util.List;

public class Message {
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

}
