package Util;

import AppLogic.RuleBuilder;
import AppLogic.RuleContainer;
import ban.Rule;
import message.BanObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: CG
 * Date: 4/16/14
 * Time: 8:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class TechFunctions {

    public static void Print(List<Rule> idealisedProtocol, List<Rule> assumptions)
    {
        RuleContainer.RULES.addAll(assumptions);
        int i=0;
        for (Rule ir : idealisedProtocol) {
            RuleContainer.addRule(ir);
            i++;
            System.out.println("Dupa regula "+i+"\n\n");
            RuleBuilder.GenerateRules(RuleContainer.RULES);
            for (BanObject bo : RuleContainer.RULES) {
                System.out.println(bo.toString());

            }
            System.out.println(RuleContainer.RULES.size());
            System.out.println("--------------");

        }
    }
}
