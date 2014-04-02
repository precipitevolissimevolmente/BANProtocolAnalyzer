package AppLogic;

import ban.Rule;
import message.*;

import java.util.ArrayList;
import java.util.List;

import static ban.ActionType.*;
import static message.BanObjectType.*;
import static message.KeyType.*;

public class RuleBuilder {

    public static void CombineRuleWith2Parameters(Rule rule1, Rule rule2, boolean invert) {
        //Message-meaning rules

        //for shared keys

        if (rule1 != null && rule2 != null && rule1.getLeft().equals(rule2.getLeft())) {
            if (rule1.getAction().equals(BELIEVES) && rule2.getAction().equals(SEES)) {
                if (rule2.getRight().getType().equals(BanObjectType.ENCRYPTED_MESSAGE)) {
                    if ((rule1.getRight().getType().equals(BanObjectType.KEY))
                            && ((Key) (rule1.getRight())).getKeyType().equals(SHARED_KEY)
                            && (((EncryptedMessage) (rule2.getRight())).getKey().getKeyType().equals(SHARED_KEY))
                            && (((EncryptedMessage) (rule2.getRight())).getKey().getQ().equals(rule1.getLeft())
                            || ((EncryptedMessage) (rule2.getRight())).getKey().getP().equals(rule1.getLeft())
                    )) {
                        if ((((Key) (rule1.getRight())).getQ()).equals(rule1.getLeft()) &&
                                !(((Key) (rule1.getRight())).getP()).equals(rule1.getLeft()))    //cheia nu este partajata de un singur agent
                        {

//                            if(RuleContainer.RULES.contains())
                            RuleContainer.addRule(new Rule(rule1.getLeft(), BELIEVES, new Rule(((Key) (rule1.getRight())).getP(), SAID,
                                    ((EncryptedMessage) (rule2.getRight())).getMessage())));
//                            return new Rule(rule1.getLeft(),BELIEVES,new Rule(((Key)(rule1.getRight())).getQ(),SAID,
//                                    ((EncryptedMessage)(rule2.getRight())).getMessage()));
                        } else if ((((Key) (rule1.getRight())).getP()).equals(rule1.getLeft())
                                && !(((Key) (rule1.getRight())).getQ()).equals(rule1.getLeft()))        //cheia nu este partajata de un singur agent
                        {
                            System.out.println("ok");
                            RuleContainer.addRule(new Rule(rule1.getLeft(), BELIEVES, new Rule(((Key) (rule1.getRight())).getQ(), SAID,
                                    ((EncryptedMessage) (rule2.getRight())).getMessage())));
//                            return new Rule(rule1.getLeft(),BELIEVES,new Rule(((Key)(rule1.getRight())).getP(),SAID,
//                                    ((EncryptedMessage)(rule2.getRight())).getMessage()));
                        }
                    }
                }
            }
            //P believes public Q, P sees {X}_shared_K => P SEES X
            if (rule1.getAction().equals(BELIEVES) && rule2.getAction().equals(SEES)) {
                if (rule2.getRight().getType().equals(BanObjectType.ENCRYPTED_MESSAGE)) {
                    if ((rule1.getRight().getType().equals(BanObjectType.KEY))
                            && ((Key) (rule1.getRight())).getKeyType().equals(SHARED_KEY)
                            && (((EncryptedMessage) (rule2.getRight())).getKey().getKeyType().equals(SHARED_KEY))
                            && (((EncryptedMessage) (rule2.getRight())).getKey().getQ().equals(rule1.getLeft())
                            || ((EncryptedMessage) (rule2.getRight())).getKey().getP().equals(rule1.getLeft()))
                            ) {
                        if ((((Key) (rule1.getRight())).getQ()).equals(rule1.getLeft()) &&
                                !(((Key) (rule1.getRight())).getP()).equals(rule1.getLeft()))           //cheia nu este partajata de un singur agent
                        {
                            RuleContainer.addRule(new Rule(rule1.getLeft(), SEES,
                                    ((EncryptedMessage) (rule2.getRight())).getMessage()));
//                            return new Rule(rule1.getLeft(),SEES,
//                                    ((EncryptedMessage)(rule2.getRight())).getMessage());
                        } else if ((((Key) (rule1.getRight())).getP()).equals(rule1.getLeft()) &&
                                !(((Key) (rule1.getRight())).getQ()).equals(rule1.getLeft()))    //cheia nu este partajata de un singur agent
                        {
                            RuleContainer.addRule(new Rule(rule1.getLeft(), SEES,
                                    ((EncryptedMessage) (rule2.getRight())).getMessage()));
//                            return new Rule(rule1.getLeft(),SEES,
//                                    ((EncryptedMessage)(rule2.getRight())).getMessage());
                        }
                    }
                }
            }


            /**
             *  Nonce-verification rule
             *  P believes fresh(X), P believes Q said X =>  P believes Q said X
             */
            if ((rule1.getAction().equals(BELIEVES)) &&
                    isRightSideFresh(rule1) &&

                    (rule2.getAction().equals(BELIEVES)) && (rule2.getRight().getType().equals(RULE)) &&
                    ((((Rule) rule2.getRight()).getLeft().getType().equals(PRINCIPAL)) &&
                            (((Rule) rule2.getRight()).getAction().equals(SAID)) &&
                            ((((Rule) rule2.getRight()).getRight().getType().equals(MESSAGE)))
                                ||(((Rule) rule2.getRight()).getRight().getType().equals(TIMESTAMP))
                                ||(((Rule) rule2.getRight()).getRight().getType().equals(NONCE))) &&

                    //x == x
                    rule1RightEqRule2Right(rule1, rule2)) {
                //P believes Q said X
                RuleContainer.addRule(new Rule(rule1.getLeft(), BELIEVES, new Rule(((Rule) rule2.getRight()).getLeft(), BELIEVES, rule1.getRight())));
            }

            //P believes public Q, P sees {X}_private_K => P believes Q said X

            if ((rule1.getAction().equals(BELIEVES)) && rule2.getAction().equals(SEES)) {
                if (rule1.getRight().getType().equals(BanObjectType.KEY)
                        && ((Key) (rule1.getRight())).getKeyType().equals(PUBLIC_KEY)
                        && (rule2.getRight().getType().equals(ENCRYPTED_MESSAGE))
                        && ((EncryptedMessage) (rule2.getRight())).getKey().getKeyType().equals(PRIVATE_KEY)) {
                    if (((Key) (rule1.getRight())).getQ() != null && ((Key) (rule1.getRight())).getP() == null
                            && (
                            ((Key) (rule1.getRight())).getQ().equals(((EncryptedMessage) (rule2.getRight())).getKey().getQ())
                                    || ((Key) (rule1.getRight())).getQ().equals(((EncryptedMessage) (rule2.getRight())).getKey().getP())))

                    {

                        RuleContainer.addRule(new Rule(rule1.getLeft(), BELIEVES, new Rule(((Key) (rule1.getRight())).getQ(), SAID,
                                ((EncryptedMessage) (rule2.getRight())).getMessage())));
//                                return new Rule(rule1.getLeft(),BELIEVES,new Rule(((Key)(rule1.getRight())).getQ(),SAID,
//                                        ((EncryptedMessage)(rule2.getRight())).getMessage()));
                    }
                    if (((Key) (rule1.getRight())).getP() != null && ((Key) (rule1.getRight())).getQ() == null &&
                            ((Key) (rule1.getRight())).getP().equals(((EncryptedMessage) (rule2.getRight())).getKey().getQ()) ||
                            ((Key) (rule1.getRight())).getP().equals(((EncryptedMessage) (rule2.getRight())).getKey().getP())) {
                        RuleContainer.addRule(new Rule(rule1.getLeft(), BELIEVES, new Rule(((Key) (rule1.getRight())).getP(), SAID,
                                ((EncryptedMessage) (rule2.getRight())).getMessage())));

//                                return new Rule(rule1.getLeft(),BELIEVES,new Rule(((Key)(rule1.getRight())).getP(),SAID,
//                                        ((EncryptedMessage)(rule2.getRight())).getMessage()));
                    }
                }
            }

            //P believes public Q, P sees {X}_private_K => P SEES X
            if ((rule1.getAction().equals(BELIEVES)) && rule2.getAction().equals(SEES)) {
                if (rule1.getRight().getType().equals(BanObjectType.KEY)
                        && ((Key) (rule1.getRight())).getKeyType().equals(PUBLIC_KEY)
                        && (rule2.getRight().getType().equals(ENCRYPTED_MESSAGE))
                        && ((EncryptedMessage) (rule2.getRight())).getKey().getKeyType().equals(PRIVATE_KEY)) {
                    if (((Key) (rule1.getRight())).getQ() != null && ((Key) (rule1.getRight())).getP() == null &&
                            (((Key) (rule1.getRight())).getQ().equals(((EncryptedMessage) (rule2.getRight())).getKey().getQ())
                            || ((Key) (rule1.getRight())).getQ().equals(((EncryptedMessage) (rule2.getRight())).getKey().getP()))) {
                        RuleContainer.addRule(new Rule(rule1.getLeft(), SEES,
                                ((EncryptedMessage) (rule2.getRight())).getMessage()));
//                        return new Rule(rule1.getLeft(),SEES,
//                                ((EncryptedMessage)(rule2.getRight())).getMessage());
                    }
                    if (((Key) (rule1.getRight())).getP() != null && ((Key) (rule1.getRight())).getQ() == null
                            && ((Key) (rule1.getRight())).getP().equals(((EncryptedMessage) (rule2.getRight())).getKey().getQ())
                            || ((Key) (rule1.getRight())).getP().equals(((EncryptedMessage) (rule2.getRight())).getKey().getP())
                            ) {
                        RuleContainer.addRule(new Rule(rule1.getLeft(), SEES,
                                ((EncryptedMessage) (rule2.getRight())).getMessage()));
//                        return new Rule(rule1.getLeft(),SEES,
//                                ((EncryptedMessage)(rule2.getRight())).getMessage());
                    }
                }
            }
            //P believes public Q, P sees {X}_public_K => P SEES X
            if ((rule1.getAction().equals(BELIEVES)) && rule2.getAction().equals(SEES)) {
                if (rule1.getRight().getType().equals(BanObjectType.KEY)
                        && ((Key) (rule1.getRight())).getKeyType().equals(PUBLIC_KEY)
                        && (rule2.getType().equals(ENCRYPTED_MESSAGE))
                        && ((EncryptedMessage) (rule1.getRight())).getKey().getKeyType().equals(PUBLIC_KEY)) {
                    if (((Key) (rule1.getRight())).getQ() == rule1.getLeft()) {
                        RuleContainer.addRule(new Rule(rule1.getLeft(), SEES,
                                ((EncryptedMessage) (rule2.getRight())).getMessage()));
//                        return new Rule(rule1.getLeft(),SEES,
//                                ((EncryptedMessage)(rule2.getRight())).getMessage());
                    }
                    if (((Key) (rule1.getRight())).getP() == rule1.getLeft()) {
                        RuleContainer.addRule(new Rule(rule1.getLeft(), SEES,
                                ((EncryptedMessage) (rule2.getRight())).getMessage()));
//                        return new Rule(rule1.getLeft(),SEES,
//                                ((EncryptedMessage)(rule2.getRight())).getMessage());
                    }
                }
            }


            //Jurisdiction
            //P believes Q controls X, p believes Q believes X=> P believes X
            if (rule1.getAction().equals(BELIEVES) && rule2.getAction().equals(BELIEVES)) {
                if (rule1.getRight().getType().equals(BanObjectType.RULE)
                        && rule2.getRight().getType().equals(BanObjectType.RULE)) {
                    Rule rightRule1 = (Rule) rule1.getRight();
                    Rule rightRule2 = (Rule) rule2.getRight();
                    if (rightRule1.getLeft().equals(rightRule2.getLeft())) {
                        if (rightRule1.getAction().equals(CONTROLS) && (rightRule2).getAction().equals(BELIEVES)) {
                            if (rightRule1.getRight().getType().equals(rightRule2.getRight().getType())) {
                                if (rightRule1.getRight().equals(rightRule2.getRight())) {
                                    RuleContainer.addRule(new Rule(rule1.getLeft(), BELIEVES, rightRule1.getRight()));
//                                return new Rule(rule1.getLeft(),BELIEVES,rightRule1.getRight());
                                }
                            }
                        }
                    }
                }
            }
           /* if(rule1.getAction().equals(BELIEVES) && rule2.getAction().equals(BELIEVES)){
                if((rule1.getRight().getType().equals(MESSAGE)||rule1.getRight().getType().equals(NONCE)||rule1.getRight().getType().equals(TIMESTAMP)||rule1.getRight().getType().equals(ENCRYPTED_MESSAGE))&&
                   (rule2.getRight().getType().equals(MESSAGE)||rule2.getRight().getType().equals(NONCE)||rule2.getRight().getType().equals(TIMESTAMP)||rule2.getRight().getType().equals(ENCRYPTED_MESSAGE)))
                {
                     Message msg=new Message();
                     msg.add(rule1.getRight());
                     msg.add(rule2.getRight());
                     RuleContainer.RULES.add(new Rule(rule1.getLeft(),BELIEVES,msg));
                }

            }   */


        }



        if (rule1 == null && rule2 != null) {  //reguli cu un singur input
            if (rule2.getLeft().getType().equals(BanObjectType.PRINCIPAL)) {
                if (rule2.getAction().equals(SEES)) {

                    if (rule2.getType().equals(BanObjectType.MESSAGE)) {
                        for (BanObject bo : ((Message) (rule2.getRight())).getMessageList()) {
                            RuleContainer.addRule(new Rule(rule2.getLeft(), SEES, bo));
                        }

                    }
                }
                if(rule2.getAction().equals(BELIEVES)){
                    if(rule2.getRight().getType().equals(BanObjectType.MESSAGE)){
                        for (BanObject o  : ((Message)(rule2.getRight())).getMessageList()) {
                            if(o.getType().equals(TIMESTAMP))
                            {
                                if(((TimeStmp)o).isFresh())
                                    ((Message)(rule2.getRight())).setFresh(true);
                                RuleContainer.addRule(new Rule(rule2.getLeft(), BELIEVES, rule2.getRight()));
                            }
                            if(o.getType().equals(NONCE))
                            {
                                if(((Nonce)o).isFresh())
                                    ((Message)(rule2.getRight())).setFresh(true);
                                RuleContainer.addRule(new Rule(rule2.getLeft(), BELIEVES, rule2.getRight()));

                            }
                            if((o.getType().equals(KEY)) && ((Key)o).getKeyType().equals(SHARED_KEY))
                            {
                                if(((Key)o).isFresh())
                                    ((Message)(rule2.getRight())).setFresh(true);
                                RuleContainer.addRule(new Rule(rule2.getLeft(), BELIEVES, rule2.getRight()));

                            }
                        }
                    }
                }
            }
            if(rule2.getLeft().getType().equals(PRINCIPAL))
            {
                if(rule2.getAction().equals(BELIEVES))
                {
                    if (rule2.getRight().getType().equals(RULE)){
                        if(((Rule)(rule2.getRight())).getLeft().getType().equals(PRINCIPAL))
                        {
                            if(((Rule)(rule2.getRight())).getAction().equals(BELIEVES))
                            {
                                if(((Rule)(rule2.getRight())).getRight().getType().equals(MESSAGE))
                                {
                                    for (BanObject o : ((Message)(((Rule)(rule2.getRight())).getRight())).getMessageList()) {
                                          RuleContainer.addRule(new Rule(rule2.getLeft(), BELIEVES, new Rule(((Rule) (rule2.getRight())).getLeft(), BELIEVES, o)));
                                    }
                                }
                            }
                            if(((Rule)(rule2.getRight())).getAction().equals(SAID))
                            {
                                if(((Rule)(rule2.getRight())).getRight().getType().equals(MESSAGE))
                                {
                                    for (BanObject o : ((Message)(((Rule)(rule2.getRight())).getRight())).getMessageList()) {
                                        RuleContainer.addRule(new Rule(rule2.getLeft(),BELIEVES,new Rule(((Rule)(rule2.getRight())).getLeft(),SAID,o)));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (rule1 != null && rule2 == null) {

            if (rule1.getLeft().getType().equals(BanObjectType.PRINCIPAL)) {
                if (rule1.getAction().equals(SEES)) {
                    if (rule1.getRight().getType().equals(BanObjectType.MESSAGE)) {
                        for (BanObject bo : ((Message) (rule1.getRight())).getMessageList()) {
                            RuleContainer.addRule(new Rule(rule1.getLeft(), SEES, bo));
                        }

                    }
                }
                //if(rule1.getAction().equals(BELIEVES)){
                    if(rule1.getRight().getType().equals(BanObjectType.MESSAGE)){
                        for (BanObject o  : ((Message)(rule1.getRight())).getMessageList()) {
                             if(o.getType().equals(TIMESTAMP))
                             {
                                 if(CheckFreshness((Principal)rule1.getLeft(),(TimeStmp)o))
                                 {
                                 ((Message)(rule1.getRight())).setFresh(true);
                                 ((TimeStmp)(o)).setFresh(true);
                                 RuleContainer.addRule(new Rule(rule1.getLeft(), BELIEVES, rule1.getRight()));
                                 }
                             }
                            if(o.getType().equals(NONCE))
                            {
                                if(CheckFreshness((Principal)rule1.getLeft(),(Nonce)o))
                                {((Message)(rule1.getRight())).setFresh(true);
                                ((Nonce)(o)).setFresh(true);
                                RuleContainer.addRule(new Rule(rule1.getLeft(), BELIEVES, rule1.getRight()));
                                }
                            }
                            /*if((o.getType().equals(KEY)) && ((Key)o).getKeyType().equals(SHARED_KEY))
                            {
                                if(((Key)o).isFresh())
                                    ((Message)(rule1.getRight())).setFresh(true);
                                RuleContainer.addRule(new Rule(rule1.getLeft(), BELIEVES, rule1.getRight()));
                            }                     */
                        }
                    }

                if(rule1.getRight().getType().equals(BanObjectType.RULE) && ((Rule)(rule1.getRight())).getRight().getType().equals(MESSAGE)){
                    for (BanObject o  : ((Message)(((Rule)(rule1.getRight())).getRight())).getMessageList()) {
                        if(o.getType().equals(TIMESTAMP))
                        {
                            if(CheckFreshness((Principal)rule1.getLeft(),(TimeStmp)o))
                            {
                                ((Message)(((Rule)(rule1.getRight())).getRight())).setFresh(true);
                                ((TimeStmp)o).setFresh(true);
                                RuleContainer.addRule(new Rule(rule1.getLeft(), BELIEVES, rule1.getRight()));
                            }
                        }
                        if(o.getType().equals(NONCE))
                        {
                            if(CheckFreshness((Principal)rule1.getLeft(),(Nonce)o))
                            {
                                ((Message)(((Rule)(rule1.getRight())).getRight())).setFresh(true);
                            ((Nonce)o).setFresh(true);
                            RuleContainer.addRule(new Rule(rule1.getLeft(), BELIEVES, rule1.getRight()));
                            }
                        }
                        /*if((o.getType().equals(KEY)) && ((Key)o).getKeyType().equals(SHARED_KEY))
                        {
                            if(((Key)o).isFresh())
                                ((Message)(rule1.getRight())).setFresh(true);
                            RuleContainer.addRule(new Rule(rule1.getLeft(), BELIEVES, rule1.getRight()));
                        }  */
                    }
                }
                //}
            }
            if(rule1.getLeft().getType().equals(PRINCIPAL))
            {
                if(rule1.getAction().equals(BELIEVES))
                {
                    if (rule1.getRight().getType().equals(RULE)){
                        if(((Rule)(rule1.getRight())).getLeft().getType().equals(PRINCIPAL))
                        {
                            if(((Rule)(rule1.getRight())).getAction().equals(BELIEVES))
                            {
                                if(((Rule)(rule1.getRight())).getRight().getType().equals(MESSAGE))
                                {
                                    for (BanObject o : ((Message)(((Rule)(rule1.getRight())).getRight())).getMessageList()) {
                                        RuleContainer.addRule(new Rule(rule1.getLeft(),BELIEVES,new Rule(((Rule)(rule1.getRight())).getLeft(),BELIEVES,o)));
                                    }
                                }
                            }
                            if(((Rule)(rule1.getRight())).getAction().equals(SAID))
                            {
                                if(((Rule)(rule1.getRight())).getRight().getType().equals(MESSAGE))
                                {
                                    for (BanObject o : ((Message)(((Rule)(rule1.getRight())).getRight())).getMessageList()) {
                                        RuleContainer.addRule(new Rule(rule1.getLeft(),BELIEVES,new Rule(((Rule)(rule1.getRight())).getLeft(),SAID,o)));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (invert) {
            CombineRuleWith2Parameters(rule2, rule1, false);
        }
        // Teoretic s-ar putea facea regula cu compuneri, discutabil

    }

    private static boolean rule1RightEqRule2Right(Rule rule1, Rule rule2) {
        if(rule1.getRight().getType().equals(((Rule) rule2.getRight()).getRight().getType())) {
            if((rule1.getRight().getType().equals(MESSAGE)) &&
                    ((Message)rule1.getRight()).getMessageList().equals(((Message)((Rule) rule2.getRight()).getRight()).getMessageList())) {
                return true;
            } else if((rule1.getRight().getType().equals(TIMESTAMP)) && (((TimeStmp)rule1.getRight()).getNonceIdentity().equals(((TimeStmp)((Rule) rule2.getRight()).getRight()).getNonceIdentity()))) {
                return true;
            } else if((rule1.getRight().getType().equals(NONCE)) && (((Nonce)rule1.getRight()).getNonceIdentity().equals(((Nonce)((Rule) rule2.getRight()).getRight()).getNonceIdentity()))) {
                return true;
            } else if((rule1.getRight().getType().equals(KEY)) &&
                    (((Key)rule1.getRight()).getKeyType().equals(((Key)((Rule) rule2.getRight()).getRight()).getKeyType())) &&
                    (((Key)rule1.getRight()).getP().equals(((Key)((Rule) rule2.getRight()).getRight()).getP())) &&
                    (((Key)rule1.getRight()).getQ().equals(((Key)((Rule) rule2.getRight()).getRight()).getQ()))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isRightSideFresh(Rule rule1) {
        return ((rule1.getRight().getType().equals(MESSAGE)) && (((Message) rule1.getRight()).isFresh())) ||
                ((rule1.getRight().getType().equals(NONCE)) && (((Nonce) rule1.getRight()).isFresh())) ||
                ((rule1.getRight().getType().equals(TIMESTAMP)) && (((TimeStmp) rule1.getRight()).isFresh()));
    }

    public static List<BanObject> GenerateRules(List<BanObject> Rules) {
        List<BanObject> result = new ArrayList<>();
        result.addAll(Rules);
        int initialSize = 0;
        while (RuleContainer.RULES.size() != initialSize) {
            initialSize = RuleContainer.RULES.size();
            if (Rules.size() == 0) {
                System.out.println("Empty set of rules");
            } else if (Rules.size() == 1) {
                //System.out.println((Rule)RULES.get(0));
                CombineRuleWith2Parameters((Rule) result.get(0), null, false);
            } else if (Rules.size() > 1) {
                for (int i = 0; i < result.size() - 1; i++) {
                    for (int j = i + 1; j < result.size(); j++) {
                        CombineRuleWith2Parameters((Rule) result.get(i), (Rule) result.get(j), true);

                    }
                }

            }
            result.clear();
            result.addAll(RuleContainer.RULES);

            for (int i = 0; i < result.size(); i++) {
                CombineRuleWith2Parameters((Rule) result.get(i), null, false);
            }
            result.clear();
            result.addAll(RuleContainer.RULES);
        }
        return result;
    }



    static private boolean CheckFreshness(Principal P,TimeStmp timeStmp)
    {
        for (BanObject o : RuleContainer.RULES) {
            if(o.getType().equals(RULE))
            {
                if(((Rule)(o)).getLeft().equals(P) && ((Rule)(o)).getAction().equals(BELIEVES) && ((Rule)(o)).getRight().getType().equals(TIMESTAMP))
                {
                    if(timeStmp.getNonceIdentity().equals(((TimeStmp)((Rule)(o)).getRight()).getNonceIdentity()))
                        return true;
                }
            }
        }
        return false;
    }
    static private boolean CheckFreshness(Principal P,Nonce nonce)
    {
        for (BanObject o : RuleContainer.RULES) {
            if(o.getType().equals(RULE))
            {
                if(((Rule)(o)).getLeft().equals(P) && ((Rule)(o)).getAction().equals(BELIEVES) && ((Rule)(o)).getRight().getType().equals(NONCE))
                {
                    if(nonce.getNonceIdentity().equals(((Nonce)((Rule)(o)).getRight()).getNonceIdentity()))
                        return true;
                }
            }
        }

        return false;
    }

}
