package message;

public class EncryptedMessage implements BanObject {
    private Message message;
    private Key key;

    public EncryptedMessage(Key key) {
        this.key = key;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    @Override
    public BanObjectType getType() {
        return BanObjectType.ENCRYPTED_MESSAGE;
    }

    @Override
    public String toString() {
        return "EncryptedMessage{" +
                "message=" + message +
                ", key=" + key +
                '}';
    }
}
