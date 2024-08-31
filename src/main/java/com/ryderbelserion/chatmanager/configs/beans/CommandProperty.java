package com.ryderbelserion.chatmanager.configs.beans;

import ch.jalu.configme.Comment;
import ch.jalu.configme.beanmapper.ExportName;

public class CommandProperty {

    @Comment("Should we execute a command?")
    @ExportName("is_enabled")
    private boolean execute;

    @Comment("The command that will be executed.")
    private String value;

    public CommandProperty() {
        this.execute = false;
        this.value = "kick {player} Please do not advertise!";
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    public boolean isExecute() {
        return this.execute;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}