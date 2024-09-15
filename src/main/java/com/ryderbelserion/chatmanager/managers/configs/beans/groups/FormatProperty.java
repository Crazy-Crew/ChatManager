package com.ryderbelserion.chatmanager.managers.configs.beans.groups;

import java.util.Map;

public class FormatProperty {

    private Map<String, Map<String, Integer>> groups;

    public final FormatProperty populate(Map<String, Map<String, Integer>> groups) {
        this.groups = groups;

        return this;
    }

    public void setGroups(Map<String, Map<String, Integer>> groups) {
        this.groups = groups;
    }

    public Map<String, Map<String, Integer>> getGroups() {
        return this.groups;
    }
}