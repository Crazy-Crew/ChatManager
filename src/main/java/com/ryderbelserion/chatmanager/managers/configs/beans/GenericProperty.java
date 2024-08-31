package com.ryderbelserion.chatmanager.managers.configs.beans;

import ch.jalu.configme.Comment;
import ch.jalu.configme.beanmapper.ExportName;
import java.util.List;

public class GenericProperty {

    @Comment("Should it be enabled?")
    @ExportName("is_enabled")
    private boolean enabled;

    @Comment({
            "This defines how strict the checks should be.",
            "",
            "Available Types:",
            " ⤷ low",
            " ⤷ high"
    })
    private String sensitivity;

    @Comment("Should we notify staff if the message/command is detected?")
    @ExportName("notify_staff")
    private boolean notify_staff;

    @Comment("The command section which handles the command to run.")
    @ExportName("command_property")
    private CommandProperty command;

    @Comment({
            "This will log to console or a file, if set to true.",
            "",
            "Where it logs, depends on the configuration section."
    })
    @ExportName("is_logging")
    private boolean isLogging;

    @Comment("The command/message to allow in chat/signs/messages")
    private List<String> whitelist;

    public final GenericProperty populate(final boolean enabled, final boolean notify_staff, final boolean isLogging, final String sensitivity, final CommandProperty command, final List<String> whitelist) {
        this.enabled = enabled;
        this.sensitivity = sensitivity;
        this.notify_staff = notify_staff;
        this.isLogging = isLogging;

        this.command = command;
        this.whitelist = whitelist;

        return this;
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

    public void setCommand(final CommandProperty command) {
        this.command  = command;
    }

    public CommandProperty getCommand() {
        return this.command;
    }

    public void setLogging(final boolean isLogging) {
        this.isLogging = isLogging;
    }

    public boolean isLogging() {
        return this.isLogging;
    }

    public void setWhitelist(final List<String> whitelist) {
        this.whitelist = whitelist;
    }

    public List<String> getWhitelist() {
        return this.whitelist;
    }
}