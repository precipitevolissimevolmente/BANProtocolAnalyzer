package message;

public class EncryptedMessage extends Message {
    private Key key;

    public EncryptedMessage(Key key) {
        this.key = key;
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
}
