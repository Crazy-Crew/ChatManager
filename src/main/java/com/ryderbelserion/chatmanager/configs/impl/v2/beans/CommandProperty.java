package com.ryderbelserion.chatmanager.configs.impl.v2.beans;

public class CommandProperty {

    private boolean execute;

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