package org.jeasy.rules.tutorials.spireontutorial;
import org.jeasy.rules.api.*;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;
import org.jeasy.rules.mvel.MVELRule;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.composite.ActivationRuleGroup;
import org.jeasy.rules.support.composite.ConditionalRuleGroup;
import org.jeasy.rules.support.composite.UnitRuleGroup;
import org.jeasy.rules.support.reader.YamlRuleDefinitionReader;

import java.io.FileReader;


public class launcher {
    public static void main(String[] args) throws Exception {
        Dataclass obj1=new Dataclass(50,30000,3,"bigbasket");
        Facts facts = new Facts();
        facts.put("p", obj1);
        /*-------------- This is for over speed-----------------------------*/
        Condition cond1= new Condition() {
            @Override
            public boolean evaluate(Facts facts) {
                Dataclass m1 = facts.get("p");
                return m1.getSpeed() >  12  ;
            }
        };
        Action action1 = new Action() {
            @Override
            public void execute(Facts facts) throws Exception {
                System.out.println("over speed");
            }
        };

        Rule rule1 = new RuleBuilder()
                .name("over speed")
                .description("checking for over speed")
                .priority(1)
                .when(cond1)
                .then(action1)
                .build();
   /*-------------- This is low tyre pressure---------------------------------------------*/
        Condition cond2 =new Condition() {
            @Override
            public boolean evaluate(Facts facts) {
                Dataclass m2 =facts.get("p");
                return m2.getTyrepressure()<10;
            }
        };
        Action action2= new Action() {
            @Override
            public void execute(Facts facts) throws Exception {
                System.out.println("low tyre pressure");

            }
        };
        Rule rule2 = new RuleBuilder()
                .name("low tyre pressure")
                .description("checking for low tyre pressure")
                .priority(2)
                .when(cond2)
                .then(action2)
                .build();
/*-------------------this for vehicle service request------------------------------------*/
        Condition cond3=new Condition() {
            @Override
            public boolean evaluate(Facts facts) {
                Dataclass m3 = facts.get("p");
                return m3.getOdometer()>20000;
            }
        };
        Action action3= new Action() {
            @Override
            public void execute(Facts facts) throws Exception {
                System.out.println("Time for service of vehicle");
            }
        };

        Rule rule3= new RuleBuilder()
                .name("service request")
                .description("request to service the vehicle")
                .priority(3)
                .when(cond3)
                .then(action3)
                .build();

/*-----------creating a composite rule from two primitive ones--------------*/
        UnitRuleGroup myunitrule= new UnitRuleGroup("myunitrulegrp","unit of rule1 and rule2");
        myunitrule.addRule(rule1);
        myunitrule.addRule(rule2);

        ActivationRuleGroup actvrule = new ActivationRuleGroup("actvrule","fires the first applicable rule and ignores other rules in the group ");
        actvrule.addRule(rule1);
        actvrule.addRule(rule2);

        ConditionalRuleGroup condrule  = new ConditionalRuleGroup("condrule","composite rule where the rule with the highest priority acts as a condition: if the rule with the highest priority evaluates to true, then the rest of the rules are fired.");
        condrule.addRule(rule1);
        condrule.addRule(rule2);
        condrule.addRule(rule3);

        /*-----------creating a composite rule from two Composite ones--------------*/
        ActivationRuleGroup actvrule1 = new ActivationRuleGroup("actvrule1","heh");
        actvrule1.addRule(myunitrule);
        actvrule1.addRule(actvrule);

        UnitRuleGroup unitr1 = new UnitRuleGroup("unitr1","comp");
        unitr1.addRule(myunitrule);
        unitr1.addRule(actvrule);
        /*-------------------this for yml file------------------------------------*/

        String filename1 = args.length != 0 ? args[0] :  "C:/Users/jogen/Downloads/easy-rules-master/easy-rules-master/easy-rules-tutorials/src/main/java/org/jeasy/rules/tutorials/spireontutorial/"+obj1.companyname+"rule1.yml";
        String filename2 = args.length != 0 ? args[0] :  "C:/Users/jogen/Downloads/easy-rules-master/easy-rules-master/easy-rules-tutorials/src/main/java/org/jeasy/rules/tutorials/spireontutorial/"+obj1.companyname+"rule2.yml";
        MVELRuleFactory ruleFactory = new MVELRuleFactory(new YamlRuleDefinitionReader());
        Rules rules1 = ruleFactory.createRules(new FileReader(filename1));
        Rules rules2= ruleFactory.createRules(new FileReader(filename2));


        String filename3 = args.length != 0 ? args[0] :  "C:/Users/jogen/Downloads/easy-rules-master/easy-rules-master/easy-rules-tutorials/src/main/java/org/jeasy/rules/tutorials/spireontutorial/"+obj1.companyname+"rule3.yml";
        String filename4 = args.length != 0 ? args[0] :  "C:/Users/jogen/Downloads/easy-rules-master/easy-rules-master/easy-rules-tutorials/src/main/java/org/jeasy/rules/tutorials/spireontutorial/"+obj1.companyname+"rule4.yml";
        Rule rule3big  = ruleFactory.createRule(new FileReader(filename3));
        Rule rule4big  = ruleFactory.createRule(new FileReader(filename4));
        UnitRuleGroup unitr2 = new UnitRuleGroup("unitr2","comp");
        unitr2.addRule(rule3big);
        unitr2.addRule(rule4big);



        /*-------rule registration----------------------------- */
        Rules rules = new Rules();
        //rules.register(rule1);
        //rules.register(rule2);
        //rules.register(rule3);
        //rules.register(myunitrule);
        //rules.register(actvrule);
         //rules.register(actvrule1);
       // rules.register(unitr1);
        //rules.register(unitr2);
        rules.register(rule3big);
        rules.register(rule4big);
        //rules.register(condrule);*/
       // rules.register(amt);

  /*-------rule firing--------------------------------------*/
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
       // rulesEngine.fire(rules1, facts);
       // rulesEngine.fire(rules2, facts);




    }
}
