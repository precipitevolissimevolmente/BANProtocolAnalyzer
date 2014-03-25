package message;

import static message.BanObjectType.PRINCIPAL;

public class Principal implements BanObject {
    String identity;
    boolean trustedAuthority;

    public Principal(String identity) {
        this.identity = identity;
    }

    public Principal(String identity, boolean trustedAuthority) {
        this.identity = identity;
        this.trustedAuthority = trustedAuthority;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public boolean isTrustedAuthority() {
        return trustedAuthority;
    }

    public void setTrustedAuthority(boolean trustedAuthority) {
        this.trustedAuthority = trustedAuthority;
    }

    @Override
    public BanObjectType getType() {
        return PRINCIPAL;
    }

    @Override
    public String toString() {
        return trustedAuthority ? "TrustedAuthority" + identity : identity;
    }
}
