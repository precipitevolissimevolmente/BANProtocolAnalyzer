package message;

import static message.BanObjectType.NONCE;

public class Nonce implements BanObject {
    boolean fresh;
    private Principal NonceIdentity;
    //private String Label;

    public Principal getNonceIdentity() {
        return NonceIdentity;
    }

    public void setNonceIdentity(Principal nonceIdentity) {
        NonceIdentity = nonceIdentity;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    public boolean isFresh() {

        return fresh;
    }

    @Override
    public BanObjectType getType() {
        return NONCE;
    }

    @Override
    public String toString() {
        return fresh ? "Fresh NONCE " + NonceIdentity : "NONCE "+ NonceIdentity.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Nonce) {
            if ((((Nonce) obj).getNonceIdentity().equals(this.getNonceIdentity())) &&
                    (((Nonce) obj).isFresh() == this.isFresh()) &&
                    (((Nonce) obj).getType() == this.getType())) {
                return true;
            }
        }
        return false;
    }
}
