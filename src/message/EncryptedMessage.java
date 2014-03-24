package message;

/**
 * Created with IntelliJ IDEA.
 * User: G
 * Date: 24.03.2014
 * Time: 23:27
 * To change this template use File | Settings | File Templates.
 */
public class EncryptedMessage implements BanObject {
    @Override
    public BanObjectType getType() {
        return BanObjectType.ENCRYPTED_MESSAGE;
    }
}
