package AppLogic;

import ban.Action;
import ban.ActionType;
import ban.Rule;
import message.*;

import static ban.ActionType.*;
import static message.BanObjectType.*;
import static message.KeyType.*;

public class RuleBuilder {

    public static Rule CombineRuleWith2Parameters(Rule rule1, Rule rule2) {
        //Message-meaning rules

        //for shared keys

        if (rule1.getLeft().equals(rule2.getLeft())) {
            if (rule1.getAction().equals(BELIEVES) && rule2.getAction().equals(SEES)) {
                if(rule2.getRight().getType().equals(BanObjectType.ENCRYPTED_MESSAGE)){
                    if ((rule1.getRight().getType().equals(BanObjectType.KEY))
                            &&((Key) (rule1.getRight())).getKeyType().equals(SHARED_KEY)
                            && (((EncryptedMessage) (rule2.getRight())).getKey().getKeyType().equals(SHARED_KEY))
                            )
                    {
                        if ((((Key) (rule1.getRight())).getQ()).equals(rule1.getLeft()))
                        {
                            return new Rule(rule1.getLeft(),BELIEVES,new Rule(((Key)(rule1.getRight())).getQ(),SAID,
                                    ((EncryptedMessage)(rule2.getRight())).getMessage()));
                        }
                        else if ((((Key) (rule1.getRight())).getP()).equals(rule1.getLeft()))
                        {
                            return new Rule(rule1.getLeft(),BELIEVES,new Rule(((Key)(rule1.getRight())).getP(),SAID,
                                    ((EncryptedMessage)(rule2.getRight())).getMessage()));
                        }
                    }
                }
            }

            /**
             *  Nonce-verification rule
             *  P believes fresh(X), P believes Q said X =>  P believes Q said X
             */
            if ((rule1.getAction().equals(BELIEVES)) &&
                    (rule1.getRight().getType().equals(MESSAGE)) && (((Message) rule1.getRight()).isFresh()) &&

                    (rule2.getAction().equals(BELIEVES)) &&
                    ((rule2.getRight().getType().equals(RULE)) &&
                            (((Rule) rule2.getRight()).getLeft().getType().equals(PRINCIPAL)) &&
                            (((Rule) rule2.getRight()).getAction().equals(SAID)) &&
                            (((Rule) rule2.getRight()).getRight().getType().equals(MESSAGE))) &&

                    //x == x
                    (rule1.getRight().equals(((Rule) rule2.getRight()).getRight()))) {
                //P believes Q said X
                return new Rule(rule1.getLeft(), BELIEVES, new Rule(((Rule)rule2.getRight()).getLeft(), BELIEVES, rule1.getRight()));
            }

            //P believes public Q, P sees {X}_private_K => P believes Q said X

            if ((rule1.getAction().equals(BELIEVES))&& rule2.getAction().equals(SEES))
            {
                if(rule1.getRight().getType().equals(BanObjectType.KEY)
                        && ((Key) (rule1.getRight())).getKeyType().equals(PUBLIC_KEY)
                        && (rule2.getType().equals(ENCRYPTED_MESSAGE))
                        && ((EncryptedMessage)(rule1.getRight())).getKey().getKeyType().equals(PRIVATE_KEY))
                    {
                            if(((Key)(rule1.getRight())).getQ()!=null)
                            {
                                return new Rule(rule1.getLeft(),BELIEVES,new Rule(((Key)(rule1.getRight())).getQ(),SAID,
                                        ((EncryptedMessage)(rule2.getRight())).getMessage()));
                            }
                            if(((Key)(rule1.getRight())).getP()!=null)
                            {
                                return new Rule(rule1.getLeft(),BELIEVES,new Rule(((Key)(rule1.getRight())).getP(),SAID,
                                        ((EncryptedMessage)(rule2.getRight())).getMessage()));
                            }
                    }
            }

            //Jurisdiction
            //P believes Q controls X, p believes Q believes X=> P believes X
            if (rule1.getAction().equals(BELIEVES) && rule2.getAction().equals(BELIEVES))
            {
                if(rule1.getRight().getType().equals(BanObjectType.RULE)
                        && rule2.getRight().getType().equals(BanObjectType.RULE))
                {
                    Rule rightRule1=(Rule)rule1.getRight();
                    Rule rightRule2=(Rule)rule2.getRight();
                    if(rightRule1.getLeft().equals(rightRule2.getLeft()))
                    {
                        if(rightRule1.getAction().equals(CONTROLS)&& (rightRule2).getAction().equals(BELIEVES))
                        {
                            if (rightRule1.getRight().getType().equals(rightRule2.getRight().getType()))
                            {
                                if(rightRule1.getRight().equals(rightRule2.getRight()))
                                {
                                return new Rule(rule1.getLeft(),BELIEVES,rightRule1.getRight());
                                }
                            }
                        }
                    }
                }
            }


        }
        // Teoretic s-ar putea facea regula cu compuneri, discutabil




        return null;
    }
}
