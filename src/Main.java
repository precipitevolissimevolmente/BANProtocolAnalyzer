import AppLogic.RuleBuilder;
import AppLogic.RuleContainer;
import ban.Rule;
import json.JSONBuilder;
import message.*;

import java.util.List;

import static ban.ActionType.*;

public class Main {
    public static void main(String[] args) {
//            JSONBuilder kerberos = new JSONBuilder("/resources/Kerberos.json", "/resources/KerberosAssumption.json");
//            List<Rule> idealisedRules = kerberos.getIdealisedRules();
//            List<Rule> assumptions = kerberos.getAssumptions();
        JSONBuilder nssk = new JSONBuilder("/resources/Needham-Schroeder-Shared-keys.json", "/resources/NSSKAssumptions.json");

        List<Rule> idealisedRules = nssk.getIdealisedRules();
        List<Rule> assumptions = nssk.getAssumptions();


        RuleContainer.RULES.addAll(assumptions);
        System.out.println(idealisedRules.size());
        int i=0;
        for (Rule ir : idealisedRules) {
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
