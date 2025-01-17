package com.ryderbelserion.chatmanager.core.enums;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.properties.Property;
import com.ryderbelserion.chatmanager.core.managers.configs.ConfigManager;
import com.ryderbelserion.chatmanager.core.managers.configs.config.ConfigKeys;
import com.ryderbelserion.chatmanager.core.managers.configs.locale.RootKeys;
import com.ryderbelserion.core.FusionProvider;
import com.ryderbelserion.core.util.Methods;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Messages {

    reload_plugin(RootKeys.reload_plugin);

    private Property<String> property;

    private Property<List<String>> properties;
    private boolean isList = false;

    Messages(@NotNull final Property<String> property) {
        this.property = property;
    }

    Messages(@NotNull final Property<List<String>> properties, final boolean isList) {
        this.properties = properties;
        this.isList = isList;
    }

    private final SettingsManager config = ConfigManager.getConfig();

    private final SettingsManager messages = ConfigManager.getLocale();

    private boolean isList() {
        return this.isList;
    }

    public String getString() {
        return this.messages.getProperty(this.property);
    }

    public List<String> getList() {
        return this.messages.getProperty(this.properties);
    }

    public Component getMessage(@NotNull final Audience sender) {
        return getMessage(sender, new HashMap<>());
    }

    public Component getMessage(@NotNull final Audience sender, @NotNull final String placeholder, @NotNull final String replacement) {
        Map<String, String> placeholders = new HashMap<>() {{
            put(placeholder, replacement);
        }};

        return getMessage(sender, placeholders);
    }

    public Component getMessage(@NotNull final Audience sender, @NotNull final Map<String, String> placeholders) {
        placeholders.putIfAbsent("prefix", this.config.getProperty(ConfigKeys.command_prefix));
        
        return parse(sender, placeholders);
    }

    public void sendMessage(final Audience sender, final String placeholder, final String replacement) {
        final Action action = this.config.getProperty(ConfigKeys.message_action);

        switch (action) {
            case send_message -> sendRichMessage(sender, placeholder, replacement);
            case send_actionbar -> sendActionBar(sender, placeholder, replacement);
        }
    }

    public void sendMessage(final Audience sender, final Map<String, String> placeholders) {
        final Action action = this.config.getProperty(ConfigKeys.message_action);

        switch (action) {
            case send_message -> sendRichMessage(sender, placeholders);
            case send_actionbar -> sendActionBar(sender, placeholders);
        }
    }

    public void sendMessage(final Audience sender) {
        final Action action = this.config.getProperty(ConfigKeys.message_action);

        switch (action) {
            case send_message -> sendRichMessage(sender);
            case send_actionbar -> sendActionBar(sender);
        }
    }

    public void sendActionBar(final Audience sender, final String placeholder, final String replacement) {
        final Component component = getMessage(sender, placeholder, replacement);

        if (component.equals(Component.empty())) return;

        sender.sendActionBar(component);
    }

    public void sendActionBar(final Audience sender, final Map<String, String> placeholders) {
        final Component component = getMessage(sender, placeholders);

        if (component.equals(Component.empty())) return;

        sender.sendActionBar(component);
    }

    public void sendActionBar(final Audience sender) {
        final Component component = getMessage(sender);

        if (component.equals(Component.empty())) return;

        sender.sendActionBar(component);
    }

    public void sendRichMessage(final Audience sender, final String placeholder, final String replacement) {
        final Component component = getMessage(sender, placeholder, replacement);

        if (component.equals(Component.empty())) return;

        sender.sendMessage(component);
    }

    public void sendRichMessage(final Audience sender, final Map<String, String> placeholders) {
        final Component component = getMessage(sender, placeholders);

        if (component.equals(Component.empty())) return;

        sender.sendMessage(component);
    }

    public void sendRichMessage(final Audience sender) {
        final Component component = getMessage(sender);

        if (component.equals(Component.empty())) return;

        sender.sendMessage(component);
    }

    public void migrate() {
        if (this.isList) {
            this.messages.setProperty(this.properties, Methods.convert(this.messages.getProperty(this.properties), true));

            return;
        }

        this.messages.setProperty(this.property, Methods.convert(this.messages.getProperty(this.property), true));
    }

    private @NotNull Component parse(@NotNull final Audience sender, @NotNull final Map<String, String> placeholders) {
        String message;

        if (isList()) {
            message = Methods.toString(getList());
        } else {
            message = getString();
        }

        return FusionProvider.get().placeholders(sender, message, placeholders);
    }
}