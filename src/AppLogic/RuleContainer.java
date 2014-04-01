package AppLogic;

import ban.Rule;
import message.BanObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: CG
 * Date: 3/26/14
 * Time: 9:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleContainer {

    public static List<BanObject> RULES =new ArrayList<BanObject>();


    private RuleContainer() {

    }

    public static void addRule(Rule r) {
        if(RULES.contains(r)) {
            return;
        }
        RULES.add(r);
    }

}
