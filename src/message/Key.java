package message;

import static message.BanObjectType.KEY;

public class Key implements BanObject {
    private KeyType keyType;
    private Principal p;
    private Principal q;

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
                && (((Key)obj).getP().equals(this.getP())) && (((Key)obj).getQ().equals(this.getQ()))) {
                return true;
            }
        }
        return false;
    }
}
