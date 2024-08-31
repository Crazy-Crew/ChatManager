package com.ryderbelserion.chatmanager.configs.impl.v2.chat;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import com.ryderbelserion.chatmanager.configs.impl.v2.beans.AdvertProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newBeanProperty;

public class AdvertisingKeys implements SettingsHolder {

    @Comment("Blocks advertising in chat!")
    public static final Property<AdvertProperty> block_advertising_chat = newBeanProperty(AdvertProperty.class, "feature.anti-advertising.chat", new AdvertProperty());

    @Comment("Blocks advertising in signs!")
    public static final Property<AdvertProperty> block_advertising_signs = newBeanProperty(AdvertProperty.class, "feature.anti-advertising.signs", new AdvertProperty());

    @Comment("Blocks advertising in commands!")
    public static final Property<AdvertProperty> block_advertising_commands = newBeanProperty(AdvertProperty.class, "feature.anti-advertising.commands", new AdvertProperty());

}