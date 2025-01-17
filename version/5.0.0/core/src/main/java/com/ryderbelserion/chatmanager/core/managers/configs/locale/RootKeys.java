package com.ryderbelserion.chatmanager.core.managers.configs.locale;

import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class RootKeys implements SettingsHolder {

    protected RootKeys() {}

    public static final Property<String> reload_plugin = newProperty("root.reload-plugin", "<prefix><green>Config has been reloaded.");

}