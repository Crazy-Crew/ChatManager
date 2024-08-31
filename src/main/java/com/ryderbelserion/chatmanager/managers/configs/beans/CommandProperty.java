package com.ryderbelserion.chatmanager.managers.configs.beans;

import ch.jalu.configme.Comment;
import ch.jalu.configme.beanmapper.ExportName;
import java.util.List;

public class CommandProperty {

    @Comment("Should we execute commands?")
    @ExportName("is_enabled")
    private boolean execute;

    @Comment("The commands that will be executed.")
    private List<String> values;

    public CommandProperty(final boolean execute, final List<String> values) {
        this.execute = execute;
        this.values = values;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    public boolean isExecute() {
        return this.execute;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<String> getValues() {
        return this.values;
    }
}