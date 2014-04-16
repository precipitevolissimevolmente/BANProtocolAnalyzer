package message;

import static message.BanObjectType.NONCE;

public class Nonce implements BanObject {
    boolean fresh;
    private Principal NonceIdentity;
    private String NonceName="";

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
        return fresh ? "Fresh NONCE " + NonceName + NonceIdentity : "NONCE "+ NonceIdentity.toString();
    }

    public String getNonceName() {
        return NonceName;
    }

    public void setNonceName(String nonceName) {
        NonceName = nonceName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Nonce) {
            if ((((Nonce) obj).getNonceIdentity().equals(this.getNonceIdentity())) &&
                    (((Nonce) obj).getNonceName().equals(this.getNonceName())) &&
                    (((Nonce) obj).isFresh() == this.isFresh()) &&
                    (((Nonce) obj).getType() == this.getType())) {
                return true;
            }
        }
        return false;
    }
}
