package AppLogic;

import ban.ActionType;
import ban.Rule;
import message.BanObjectType;
import message.Key;
import message.KeyType;

/**
 * Created with IntelliJ IDEA.
 * User: CG
 * Date: 3/25/14
 * Time: 5:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleBuilder {

    public void CombineRuleWith2Parameters(Rule rule1, Rule rule2)
    {
        //Message-meaning rules

        //for shared keys

        if(rule1.getLeft().equals(rule2.getLeft()))
        {
            if(rule1.getAction().getType().equals(ActionType.BELIEVES) && rule2.getAction().getType().equals(ActionType.SEES))
            {
                if( (rule1.getRight().getType().equals(BanObjectType.KEY)) && ((Key)(rule1.getRight())).getKeyType().equals(KeyType.SHARED_KEY))
                {}
            }
        }
    }
}
