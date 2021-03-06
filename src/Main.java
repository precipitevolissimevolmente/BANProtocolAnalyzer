import AppLogic.RuleBuilder;
import AppLogic.RuleContainer;
import Util.Report;
import Util.TechFunctions;
import ban.Rule;
import json.JSONBuilder;
import message.*;

import java.util.ArrayList;
import java.util.List;

import static ban.ActionType.*;

public class Main {
    public static void main(String[] args) {
        JSONBuilder nssk1 = new JSONBuilder("/resources/ANDREW-SECURE-RPC-HANDSHAKE", "/resources/ASSUMPTIONS-ANDREW-SECURE-RPC-HANDSHAKE");
//            JSONBuilder kerberos = new JSONBuilder("/resources/Kerberos.json", "/resources/KerberosAssumption.json");
//            List<Rule> idealisedRules = kerberos.getIdealisedRules();
//            List<Rule> assumptions = kerberos.getAssumptions();
        JSONBuilder nssk = new JSONBuilder("/resources/Needham-Schroeder-Shared-keys.json", "/resources/NSSKAssumptions.json");
        List<Rule> idealisedRules = nssk.getIdealisedRules();
        List<Rule> assumptions = nssk.getAssumptions();

        TechFunctions.Print(idealisedRules,assumptions);

        System.out.println("****");

        Report.CheckForGoals();
        Report.PrintReport();
    }
}
