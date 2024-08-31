package com.ryderbelserion.chatmanager.configs.beans;

import java.util.List;
import java.util.Map;

public class EntryProperty {

    private Map<Integer, List<String>> entry;

    public EntryProperty(Map<Integer, List<String>> entry) {
        this.entry = entry;
    }

    public void setEntry(Map<Integer, List<String>> help) {
        this.entry = help;
    }

    public Map<Integer, List<String>> getEntry() {
        return this.entry;
    }
}