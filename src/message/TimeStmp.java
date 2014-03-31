package message;

import static message.BanObjectType.TIMESTAMP;

public class TimeStmp extends Nonce {
    @Override
    public BanObjectType getType() {
        return TIMESTAMP;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TimeStmp) {
            if ((((TimeStmp) obj).getNonceIdentity().equals(this.getNonceIdentity())) &&
                    (((TimeStmp) obj).getType().equals(this.getType()))) {
                return true;
            }
        }
        return false;
    }
}
