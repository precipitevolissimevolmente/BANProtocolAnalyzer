package message;

public class Nonce {
    boolean fresh;

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    public boolean isFresh() {
        return fresh;
    }
}
