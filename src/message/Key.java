package message;

import static message.BanObjectType.KEY;

public class Key implements BanObject {
    private KeyType keyType;
    private Principal p;
    private Principal q;
    private boolean fresh;

    public boolean isFresh() {
        return fresh;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    public Key() {
    }

    public Key(KeyType sharedKey, Principal p, Principal q) {
        this.keyType = sharedKey;
        this.p=p;
        this.q=q;
    }

    public Principal getP() {
        return p;
    }

    public void setP(Principal p) {
        this.p = p;
    }

    public Principal getQ() {
        return q;
    }

    public void setQ(Principal q) {
        this.q = q;
    }

    public KeyType getKeyType() {
        return keyType;
    }

    public void setKeyType(KeyType keyType) {
        this.keyType = keyType;
    }

    @Override
    public BanObjectType getType() {
        return KEY;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Key) {
        if((((Key)obj).getKeyType().equals(this.getKeyType()))
                && (((Key)obj).getP().equals(this.getP())) && (((Key)obj).getQ().equals(this.getQ()))
                && (((Key)obj).isFresh() == (this.isFresh()))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Key{" + keyType +
                ", p=" + p +
                ", q=" + q +
                ", fresh=" + fresh +
                '}';
    }
}
