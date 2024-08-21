package com.ryderbelserion.chatmanager.configs.types.rules;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import com.ryderbelserion.chatmanager.api.objects.Rules;
import static ch.jalu.configme.properties.PropertyInitializer.newBeanProperty;

public class RuleKeys implements SettingsHolder {

    @Comment("A list of configurable rules for /rules")
    public static final Property<Rules> rules = newBeanProperty(Rules.class, "rules", new Rules());

}