package ban;


import message.BanObject;
import message.BanObjectType;

import static message.BanObjectType.RULE;

public class Rule implements BanObject {

    private BanObject left;
    private Action action;
    private BanObject right;

    public Rule(BanObject left, Action action, BanObject right) {
        this.left = left;
        this.action = action;
        this.right = right;
    }

    public BanObject getLeft() {
        return left;
    }

    public void setLeft(BanObject left) {
        this.left = left;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public BanObject getRight() {
        return right;
    }

    public void setRight(BanObject right) {
        this.right = right;
    }

    @Override
    public BanObjectType getType() {
        return RULE;
    }
}
