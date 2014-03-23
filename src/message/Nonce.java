package message;

public class Nonce {
    boolean fresh;
    private Agent NonceIdentity;

    public Agent getNonceIdentity() {
        return NonceIdentity;
    }

    public void setNonceIdentity(Agent nonceIdentity) {
        NonceIdentity = nonceIdentity;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    public boolean isFresh() {
        return fresh;
    }
}
