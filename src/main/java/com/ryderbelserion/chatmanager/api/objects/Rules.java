package com.ryderbelserion.chatmanager.api.objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rules {

    private Map<Integer, List<String>> rules = new HashMap<>() {{
        put(1, List.of(
                "&7&m----------&8[ &aServer Rules Page 1/{max} &8]&7&m----------",
                "&aNo Hacking",
                "&aNo DDOS Threats",
                "&aBe Kind to all players and staff"
        ));
        put(2, List.of(
                "&7&m----------&8[ &aServer Rules Page 2/{max} &8]&7&m----------",
                "&aNo swearing anywhere at anytime!",
                "&aDo NOT abusing caps!",
                "&aNo Advertising any other minecraft servers!"
        ));
        put(3, List.of(
                "&7&m----------&8[ &aServer Rules Page 3/{max} &8]&7&m----------",
                "&aDo not use any special characters in chat.",
                "&aDo not ask for staff.",
                "&aDo not spam in chat."
        ));
    }};

    public void setRules(Map<Integer, List<String>> rules) {
        this.rules = rules;
    }

    public Map<Integer, List<String>> getRules() {
        return this.rules;
    }

    public int getMaxPages() {
        return this.rules.size();
    }
}