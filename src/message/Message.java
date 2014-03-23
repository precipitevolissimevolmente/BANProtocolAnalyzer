package message;

import java.util.ArrayList;
import java.util.List;

public class Message {
    List<Object> messageList = new ArrayList<Object>();

    public List<Object> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Object> messageList) {
        this.messageList = messageList;
    }

    public void add(Object o) {
        messageList.add(o);
    }

    public void  removesTheFirstOccurrence(Object o) {
        messageList.remove(o);
    }

    public void removeByIndex(int index) {
        messageList.remove(index);
    }

}
