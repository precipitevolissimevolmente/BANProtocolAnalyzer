package message;

import static message.BanObjectType.NONCE;

public class Nonce implements BanObject {
    boolean fresh;
    private Principal NonceIdentity;

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
}
