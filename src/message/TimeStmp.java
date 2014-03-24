package message;

import static message.BanObjectType.TIMESTAMP;

public class TimeStmp extends Nonce {
    @Override
    public BanObjectType getType() {
        return TIMESTAMP;
    }
}
