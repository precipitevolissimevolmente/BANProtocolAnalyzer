import AppLogic.RuleBuilder;
import AppLogic.RuleContainer;
import ban.Rule;
import json.JSONBuilder;
import message.*;

import java.util.List;

import static ban.ActionType.*;

public class Main {
    public static void main(String[] args) {
        JSONBuilder kerberos = new JSONBuilder("/resources/Kerberos.json", "/resources/KerberosAssumption.json");
        List<Rule> idealisedRules = kerberos.getIdealisedRules();
        List<Rule> assumptions = kerberos.getAssumptions();
//        RuleContainer.RULES.addAll(idealisedRules);
//        RuleContainer.RULES.addAll(assumptions);

        JSONBuilder nssk = new JSONBuilder("/resources/Needham-Schroeder-Shared-keys.json", "/resources/NSSKAssumptions.json");
        RuleContainer.RULES.addAll(nssk.getIdealisedRules());
        RuleContainer.RULES.addAll(nssk.getAssumptions());

        boolean fresh = true;
        Message messageX = new Message(fresh);
        Principal P = new Principal("P");
        Nonce nA=new Nonce();
        TimeStmp tmp=new TimeStmp();
        tmp.setNonceIdentity(P);
        nA.setNonceIdentity(new Principal("A"));
        messageX.add(nA);


        messageX.add(tmp);
        EncryptedMessage em=new EncryptedMessage(new Key(KeyType.SHARED_KEY,P,new Principal("Q")));
        em.setMessage(messageX);
        Rule rule1 = new Rule(P, BELIEVES, messageX);
        Rule rule2 = new Rule(P, BELIEVES, new Rule(new Principal("Q"), SAID, messageX));
        Rule rule3=new Rule(P,SEES,messageX);
        //RuleContainer.RULES.add(rule1);
        //RuleContainer.RULES.add(rule2);
        //RuleContainer.RULES.add(rule3);
        //RuleBuilder.CombineRuleWith2Parameters(rule1, rule2,true);

//        Rule rl=new Rule(P,BELIEVES,new Key(KeyType.SHARED_KEY,P,new Principal("Q")));
//        Rule r2=new Rule(P,SEES,em);
//        RuleBuilder.CombineRuleWith2Parameters(rl,r2,true);


        System.out.println(RuleContainer.RULES.size());
        System.out.println("--------------");

       /* RuleBuilder.GenerateRules(RuleContainer.RULES);
        for (BanObject bo : RuleContainer.RULES) {
            System.out.println(bo.toString());

        }
        //Test Area
        Principal A=new Principal("A");
        Principal B=new Principal("B");
        Message msg=new Message();
        Key shrK=new Key();
        shrK.setKeyType(KeyType.SHARED_KEY);
        shrK.setQ(B);
        shrK.setP(B);
        EncryptedMessage emsg=new EncryptedMessage(shrK);
        emsg.setMessage(msg);

        Rule rul1=new Rule();
        rul1.setLeft(A);
        rul1.setAction(BELIEVES);
        rul1.setRight(shrK);
        Rule rul2=new Rule();
        rul2.setLeft(A);
        rul2.setAction(SEES);
        rul2.setRight(emsg);
        RuleContainer.RULES.add(rul1);
        RuleContainer.RULES.add(rul2);                  */
        RuleBuilder.GenerateRules(RuleContainer.RULES);
        for (BanObject bo : RuleContainer.RULES) {
            System.out.println(bo.toString());

        }
    }
}
