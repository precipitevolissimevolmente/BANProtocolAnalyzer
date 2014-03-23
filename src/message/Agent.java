package message;

public class Agent {
    String identity;
    boolean trustedAuthority;

    public Agent(String identity) {
        this.identity = identity;
    }

    public Agent(String identity, boolean trustedAuthority) {
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
}
