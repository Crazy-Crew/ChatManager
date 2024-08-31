package com.ryderbelserion.chatmanager.configs.impl.v2.beans;

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
    private CommandProperty commandProperty;

    @Comment({
            "This will log to console or a file, if set to true.",
            "",
            "Where it logs, depends on the configuration section."
    })
    @ExportName("is_logging")
    private boolean logging;

    @Comment("The command/message to allow in chat/signs/messages")
    private List<String> whitelist;

    public GenericProperty(final String type) {
        this.enabled = false;
        this.sensitivity = "low";
        this.notify_staff = false;
        this.commandProperty = new CommandProperty();
        this.logging = false;

        switch (type) {
            case "block_advertising_chat", "block_advertising_signs" -> this.whitelist = List.of("google.com");

            case "block_advertising_commands" -> this.whitelist = List.of("/report");

            case "blocked_commands" -> this.whitelist = List.of("/register", "/login");
        }
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

    public void setCommandProperty(final CommandProperty commandProperty) {
        this.commandProperty  = commandProperty;
    }

    public CommandProperty getCommandProperty() {
        return this.commandProperty;
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