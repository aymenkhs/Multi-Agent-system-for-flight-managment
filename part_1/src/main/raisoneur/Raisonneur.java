package main.raisoneur;

import org.json.JSONObject;

import java.util.HashMap;


public class Raisonneur {

    public static String raisonner(JSONObject JSONrules, HashMap<String, String> facts){
        Rules activeRules = new Rules(JSONrules);
        boolean isStatic = false, ruleValue;

        while (!isStatic){
            isStatic = true;
            for (Rule rule:activeRules){
                if (!rule.IsRuleIgnored()){
                    if (!rule.areThereConditions()){
                        continue;
                    }
                    ruleValue = true;
                    for (Condition condition:rule){
                        if (facts.containsKey(condition.getVariableName())){
                            rule.ignoreRule(true);
                            if (!condition.makeOperation(facts.get(condition.getVariableName()))){
                                ruleValue = false;
                                break;
                            }
                        } else {
                            ruleValue = false;
                            break;
                        }
                    }
                    if (ruleValue){
                        isStatic = false;
                        // add the rule to he base fact
                        if (!facts.containsKey(rule.getVariableResultat())){
                            facts.put(rule.getVariableResultat(), rule.getResultats());
                        } else {
                            if(!rule.getResultats().equals(facts.get(rule.getVariableResultat()))){
                                System.out.println("PROBLEM: Facts are incosistants, we must stop the reasoning");
                            }
                        }
                        System.out.println(rule.getRulename());
                    }
                }
            }
        }


        return "Hi";
    }

}
