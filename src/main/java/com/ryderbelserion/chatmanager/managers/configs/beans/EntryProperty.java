package com.ryderbelserion.chatmanager.managers.configs.beans;

import java.util.List;
import java.util.Map;

public class EntryProperty {

    private Map<String, List<String>> entry;

    public final EntryProperty populate(Map<String, List<String>> entry) {
        this.entry = entry;

        return this;
    }

    public void setEntry(Map<String, List<String>> help) {
        this.entry = help;
    }

    public Map<String, List<String>> getEntry() {
        return this.entry;
    }
}