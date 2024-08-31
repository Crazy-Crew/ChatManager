package com.ryderbelserion.chatmanager.configs.impl.v2.beans;

import ch.jalu.configme.Comment;
import java.util.List;

public class AdvertProperty {

    @Comment("Should we prevent advertisements at all?")
    private boolean enabled;

    @Comment("The sensitivity the anti-advertisement checks should be at")
    private String sensitivity;

    @Comment("Notifies any staff online if a message/command contains advertisements")
    private boolean notify_staff;

    @Comment("The command to execute when someone advertises")
    private CommandProperty property;

    @Comment("Logs any message/command containing advertisements to a logs file")
    private boolean logging;

    @Comment({
            "Whitelist any command/message from being blocked in chat/commands",
            "",
            "Depending on the section of the config being edited, the whitelist will require websites or commands."
    })
    private List<String> whitelist;

    public AdvertProperty() {
        this.enabled = false;
        this.sensitivity = "LOW";
        this.notify_staff = false;
        this.property = new CommandProperty();
        this.logging = false;

        this.whitelist = List.of("/report", "google.com");
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public String getSensitivity() {
        return this.sensitivity;
    }

    public void setSensitivity(String sensitivity) {
        this.sensitivity = sensitivity;
    }

    public boolean isNotify() {
        return this.notify_staff;
    }

    public void setNotify(final boolean notify) {
        this.notify_staff = notify;
    }

    public void setProperty(final CommandProperty property) {
        this.property = property;
    }

    public CommandProperty getProperty() {
        return this.property;
    }

    public void setLogging(final boolean logging) {
        this.logging = logging;
    }

    public boolean isLogging() {
        return this.logging;
    }

    public void setWhitelist(final List<String> whitelist) {
        this.whitelist = whitelist;
    }

    public List<String> getWhitelist() {
        return this.whitelist;
    }
}