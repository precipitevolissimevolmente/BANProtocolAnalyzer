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
                    (((TimeStmp) obj).isFresh() == this.isFresh())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return fresh ? "Fresh TIMESTAMP " + this.getNonceIdentity().toString() : "TIMESTAMP "+ this.getNonceIdentity().toString();
    }
}
