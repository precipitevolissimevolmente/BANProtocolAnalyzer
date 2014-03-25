package ban;


import message.BanObject;
import message.BanObjectType;

import static message.BanObjectType.RULE;

public class Rule implements BanObject {

    private BanObject left;
    private ActionType action;
    private BanObject right;

    public Rule(BanObject left, ActionType action, BanObject right) {
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

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
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

    @Override
    public String toString() {
        String ruleString = left + " " + action + " ";

        if (right.getType().equals(RULE)) {
            ruleString += getString(right, " ");
        } else {
            ruleString += right;
        }

        return ruleString;
    }

    private String getString(BanObject rule, String buff) {
        while (rule.getType().equals(RULE)) {
            Rule temp = ((Rule) rule);
            buff += temp.left + " " + temp.getAction();
            rule = temp.right;
        }
        return buff + " " + rule;
    }
}
