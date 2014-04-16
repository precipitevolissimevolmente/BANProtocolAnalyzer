package Util;

import AppLogic.RuleContainer;
import ban.ActionType;
import ban.Rule;
import message.*;

import static ban.ActionType.BELIEVES;
import static message.BanObjectType.*;

/**
 * Created with IntelliJ IDEA.
 * User: CG
 * Date: 4/16/14
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Report {
    private static byte[] obtainedRules;

    public static void PrintReport()
    {
        if(obtainedRules[0]==0){
            System.out.println("B nu s-a autentificat cu succes la A");
        }
        if(obtainedRules[1]==0){
            System.out.println("A nu s-a autentificat cu succes la B");
        }
        if(obtainedRules[2]==0){
            System.out.println("A nu crede in activitatea recenta a lui B");
        }
        if(obtainedRules[3]==0){
            System.out.println("B nu crede in activitatea recenta a lui A");
        }
    }
    public static void CheckForGoals()
    {
        obtainedRules=new byte[4];
        for (BanObject rl : RuleContainer.RULES) {
                if( ((Rule)rl).getLeft().getType().equals(PRINCIPAL) && (((Principal)(((Rule)rl).getLeft())).getIdentity().equals("A")) &&
                        ((Rule)rl).getAction().equals(BELIEVES) &&
                        ((Rule)rl).getRight().getType().equals(BanObjectType.KEY) &&
                        (((Key) (((Rule)rl).getRight())).getKeyType().equals(KeyType.SHARED_KEY)) &&
                        (((Key) (((Rule)rl).getRight())).getP().getIdentity().equals("A"/*(Principal)((Rule)rl).getLeft()*/) || (((Key) (((Rule)rl).getRight())).getQ().getIdentity().equals("A"/*(Principal)((Rule)rl).getLeft()*/))) &&
                        (((Key) (((Rule)rl).getRight())).getP().getIdentity().equals("B"/*(Principal)((Rule)rl).getLeft()*/) || (((Key) (((Rule)rl).getRight())).getQ().getIdentity().equals("B"/*(Principal)((Rule)rl).getLeft()*/)))
                        )
                    {
                        obtainedRules[0]=1;
                    }
                else if( ((Rule)rl).getLeft().getType().equals(PRINCIPAL) && (((Principal)(((Rule)rl).getLeft())).getIdentity().equals("B")) &&
                        ((Rule)rl).getAction().equals(BELIEVES) &&
                        ((Rule)rl).getRight().getType().equals(BanObjectType.KEY) &&
                        (((Key) (((Rule)rl).getRight())).getKeyType().equals(KeyType.SHARED_KEY)) &&
                        (((Key) (((Rule)rl).getRight())).getP().getIdentity().equals("A"/*(Principal)((Rule)rl).getLeft()*/) || (((Key) (((Rule)rl).getRight())).getQ().getIdentity().equals("A"/*(Principal)((Rule)rl).getLeft()*/))) &&
                        (((Key) (((Rule)rl).getRight())).getP().getIdentity().equals("B"/*(Principal)((Rule)rl).getLeft()*/) || (((Key) (((Rule)rl).getRight())).getQ().getIdentity().equals("B"/*(Principal)((Rule)rl).getLeft()*/)))
                        )
                    {
                        obtainedRules[1]=1;
                    }
                else if( ((Rule)rl).getLeft().getType().equals(PRINCIPAL) && (((Principal)(((Rule)rl).getLeft())).getIdentity().equals("A")) &&
                        ((Rule)rl).getAction().equals(BELIEVES) &&
                        ((Rule)rl).getRight().getType().equals(BanObjectType.RULE) &&
                        (((Rule) (((Rule)(rl)).getRight())).getLeft().getType().equals(PRINCIPAL)) &&
                        (((Principal)(((Rule) (((Rule)(rl)).getRight())).getLeft())).getIdentity().equals("B"))&&
                        (((Rule) (((Rule)(rl)).getRight())).getAction().equals(BELIEVES)) &&
                        (           (((Rule) ((Rule)(rl)).getRight())).getRight().getType().equals(KEY))&&


                        ((   (Key)  (((Rule) ((Rule)(rl)).getRight())).getRight()   ).getKeyType().equals(KeyType.SHARED_KEY)) &&
                        ((   (Key)  (((Rule) ((Rule)(rl)).getRight())).getRight()   ).getP().getIdentity().equals("A"/*(Principal)((Rule)rl).getLeft()*/) || ((   (Key)  (((Rule) ((Rule)(rl)).getRight())).getRight()   ).getQ().getIdentity().equals("A"/*(Principal)((Rule)rl).getLeft()*/))) &&
                        ((   (Key)  (((Rule) ((Rule)(rl)).getRight())).getRight()   ).getP().getIdentity().equals("B"/*(Principal)((Rule)rl).getLeft()*/) || ((   (Key)  (((Rule) ((Rule)(rl)).getRight())).getRight()   ).getQ().getIdentity().equals("B"/*(Principal)((Rule)rl).getLeft()*/)))
                        )
                {

                    obtainedRules[2]=1;
                }

                else if( ((Rule)rl).getLeft().getType().equals(PRINCIPAL) && (((Principal)(((Rule)rl).getLeft())).getIdentity().equals("B")) &&
                        ((Rule)rl).getAction().equals(BELIEVES) &&
                        ((Rule)rl).getRight().getType().equals(BanObjectType.RULE) &&
                        (((Rule) (((Rule)(rl)).getRight())).getLeft().getType().equals(PRINCIPAL)) &&
                        (((Principal)(((Rule) (((Rule)(rl)).getRight())).getLeft())).getIdentity().equals("A"))&&
                        (((Rule) (((Rule)(rl)).getRight())).getAction().equals(BELIEVES)) &&
                        (           (((Rule) ((Rule)(rl)).getRight())).getRight().getType().equals(KEY))&&


                        ((   (Key)  (((Rule) ((Rule)(rl)).getRight())).getRight()   ).getKeyType().equals(KeyType.SHARED_KEY)) &&
                        ((   (Key)  (((Rule) ((Rule)(rl)).getRight())).getRight()   ).getP().getIdentity().equals("A"/*(Principal)((Rule)rl).getLeft()*/) || ((   (Key)  (((Rule) ((Rule)(rl)).getRight())).getRight()   ).getQ().getIdentity().equals("A"/*(Principal)((Rule)rl).getLeft()*/))) &&
                        ((   (Key)  (((Rule) ((Rule)(rl)).getRight())).getRight()   ).getP().getIdentity().equals("B"/*(Principal)((Rule)rl).getLeft()*/) || ((   (Key)  (((Rule) ((Rule)(rl)).getRight())).getRight()   ).getQ().getIdentity().equals("B"/*(Principal)((Rule)rl).getLeft()*/)))
                        )
                {

                    obtainedRules[3]=1;
                }

        }
    }

}
