package com.ryderbelserion.chatmanager.managers.configs.beans.groups;

import ch.jalu.configme.Comment;
import ch.jalu.configme.beanmapper.ExportName;
import java.util.Map;

public class ChatProperty {

    @Comment("Should this feature be enabled?")
    @ExportName("enable")
    private boolean enabled;

    @Comment("The default format, if a group is not found.")
    @ExportName("default_format")
    private String defaultFormat;

    @Comment("The groups that have specific formats for chat.")
    @ExportName("groups")
    private Map<String, String> groups;

    public final ChatProperty populate(final boolean enabled, final String defaultFormat, final Map<String, String> groups) {
        this.enabled = enabled;
        this.defaultFormat = defaultFormat;
        this.groups = groups;

        return this;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setDefaultFormat(String defaultFormat) {
        this.defaultFormat = defaultFormat;
    }

    public void setGroups(Map<String, String> groups) {
        this.groups = groups;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getDefaultFormat() {
        return this.defaultFormat;
    }

    public Map<String, String> getGroups() {
        return this.groups;
    }
}