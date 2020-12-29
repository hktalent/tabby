package tabby.core.data;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tabby.config.GlobalConfiguration;
import tabby.util.FileUtils;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author wh1t3P1g
 * @since 2020/11/20
 */
@Slf4j
@Data
@Component
public class RulesContainer {

    private Map<String, TabbyRule> rules = new HashMap<>();

    public RulesContainer() throws FileNotFoundException {
        load();
    }

    public TabbyRule.Rule getRule(String classname, String method){
        if(rules.containsKey(classname)){
            TabbyRule rule = rules.get(classname);
            if(rule.contains(method)){
                return rule.getRule(method);
            }
        }
        return null;
    }

    public boolean isType(String classname, String method, String type){
        if(rules.containsKey(classname)){
            TabbyRule rule = rules.get(classname);
            if(rule.contains(method)){
                TabbyRule.Rule tr = rule.getRule(method);
                return type.equals(tr.getType());
            }
        }
        return false;
    }

    public Map<String, String> getFunctionActions(String classname, String method){
        if(rules.containsKey(classname)){
            TabbyRule rule = rules.get(classname);
            if(rule.contains(method)){
                TabbyRule.Rule tr = rule.getRule(method);
                return tr.getActions();
            }
        }
        return null;
    }



    @SuppressWarnings({"unchecked"})
    private void load() throws FileNotFoundException {
        TabbyRule[] tempRules = (TabbyRule[]) FileUtils.getJsonContent(GlobalConfiguration.KNOWLEDGE_PATH, TabbyRule[].class);
        if(tempRules == null){
            throw new FileNotFoundException("Sink File Not Found");
        }
        for(TabbyRule rule:tempRules){
            rule.init();
            rules.put(rule.getName(), rule);
        }
        log.info("load "+ rules.size() +" rules success!");
    }
}