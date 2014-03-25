package AppLogic;

import ban.Action;
import ban.ActionType;
import ban.Rule;
import message.*;

import static ban.ActionType.BELIEVES;
import static ban.ActionType.SEES;
import static message.BanObjectType.*;
import static message.KeyType.SHARED_KEY;

public class RuleBuilder {

    public Rule CombineRuleWith2Parameters(Rule rule1, Rule rule2) {
        //Message-meaning rules

        //for shared keys

        if (rule1.getLeft().equals(rule2.getLeft())) {
            if (rule1.getAction().getType().equals(BELIEVES) && rule2.getAction().getType().equals(SEES)) {
                if ((rule1.getRight().getType().equals(BanObjectType.KEY)) && ((Key) (rule1.getRight())).getKeyType().equals(SHARED_KEY)) {
                    if ((((Key) (rule1.getRight())).getQ()).equals(((Principal) rule1.getLeft()))) {
                        //return new Rule();
                    }
                }
            }

            /**
             *  Nonce-verification rule
             *  P believes fresh(X), P believes Q said X =>  P believes Q said X
             */
            if ((rule1.getAction().getType().equals(BELIEVES)) &&
                    (rule1.getRight().getType().equals(MESSAGE)) && (((Message) rule1.getRight()).isFresh()) &&

                    (rule2.getAction().getType().equals(BELIEVES)) &&
                    ((rule2.getRight().getType().equals(RULE)) &&
                            (((Rule) rule2.getRight()).getLeft().getType().equals(PRINCIPAL)) &&
                            (((Rule) rule2.getRight()).getAction().getType().equals(SEES)) &&
                            (((Rule) rule2.getRight()).getRight().getType().equals(MESSAGE))) &&

                    //x == x
                    (rule1.getRight().equals(((Rule) rule2.getRight()).getRight()))) {
                //P believes Q said X
                return new Rule(rule1.getLeft(), new Action(BELIEVES), new Rule(((Rule)rule2.getRight()).getLeft(), new Action(BELIEVES), rule1.getRight()));
            }


        }
        return null;
    }
}
