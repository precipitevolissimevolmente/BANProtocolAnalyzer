package message;

public class Key {
    private KeyType keyType;
    private Agent p;
    private Agent q;

    public Agent getP() {
        return p;
    }

    public void setP(Agent p) {
        this.p = p;
    }

    public Agent getQ() {
        return q;
    }

    public void setQ(Agent q) {
        this.q = q;
    }

    public KeyType getKeyType() {
        return keyType;
    }

    public void setKeyType(KeyType keyType) {
        this.keyType = keyType;
    }
}
