package message;

public class Agent {
    String name;
    boolean trustedAuthority;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTrustedAuthority() {
        return trustedAuthority;
    }

    public void setTrustedAuthority(boolean trustedAuthority) {
        this.trustedAuthority = trustedAuthority;
    }

}
