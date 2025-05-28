package com.ryderbelserion.chatmanager.core.enums;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.properties.Property;
import com.ryderbelserion.chatmanager.api.ChatManagerProvider;
import com.ryderbelserion.chatmanager.api.configs.locale.ErrorKeys;
import com.ryderbelserion.chatmanager.api.configs.locale.RootKeys;
import com.ryderbelserion.chatmanager.api.enums.Action;
import com.ryderbelserion.chatmanager.api.interfaces.IChatManager;
import com.ryderbelserion.chatmanager.api.interfaces.IUserManager;
import com.ryderbelserion.chatmanager.api.objects.User;
import com.ryderbelserion.chatmanager.core.configs.ConfigManager;
import com.ryderbelserion.chatmanager.api.configs.ConfigKeys;
import com.ryderbelserion.fusion.adventure.FusionAdventure;
import com.ryderbelserion.fusion.adventure.utils.StringUtils;
import com.ryderbelserion.fusion.core.FusionCore;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public enum Messages {

    must_be_console_sender(RootKeys.must_be_console_sender),
    inventory_not_empty(RootKeys.inventory_not_empty),
    must_be_a_player(RootKeys.must_be_a_player),
    feature_disabled(RootKeys.feature_disabled),
    unknown_command(RootKeys.unknown_command),
    player_not_found(RootKeys.not_online),
    no_permission(RootKeys.no_permission),
    reload_plugin(RootKeys.reload_plugin),
    correct_usage(RootKeys.correct_usage),
    same_player(RootKeys.same_player),

    internal_error(ErrorKeys.internal_error),
    message_empty(ErrorKeys.message_empty);

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

    private final FusionAdventure fusion = (FusionAdventure) FusionCore.Provider.get();

    private final IChatManager provider = ChatManagerProvider.getInstance();

    private final IUserManager userManager = this.provider.getUserManager();

    private final SettingsManager config = ConfigManager.getConfig();

    private final SettingsManager locale = ConfigManager.getLocale();

    private boolean isList() {
        return this.isList;
    }

    public List<String> getList(final Audience sender) {
        if (!isList()) {
            return List.of(getString(sender));
        }

        @NotNull final Optional<UUID> uuid = sender.get(Identity.UUID);

        if (uuid.isPresent()) {
            @Nullable final User user = this.userManager.getUser(uuid.get());

            if (user == null) {
                return this.locale.getProperty(this.properties);
            }

            return user.locale().getProperty(this.properties);
        }

        return this.locale.getProperty(this.properties);
    }

    public String getString(final Audience sender) {
        @NotNull final Optional<UUID> uuid = sender.get(Identity.UUID);

        if (uuid.isPresent()) {
            @Nullable final User user = this.userManager.getUser(uuid.get());

            if (user == null) {
                return this.locale.getProperty(this.property);
            }

            return user.locale().getProperty(this.property);
        }

        return this.locale.getProperty(this.property);
    }

    public @NotNull Component getMessage(@NotNull final Audience sender) {
        return getMessage(sender, new HashMap<>());
    }

    public @NotNull Component getMessage(@NotNull final Audience sender, @NotNull final String placeholder, @NotNull final String replacement) {
        return getMessage(sender, new HashMap<>() {{
            put(placeholder, replacement);
        }});
    }

    public @NotNull Component getMessage(@NotNull final Audience sender, @NotNull final Map<String, String> placeholders) {
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
        final Component content = getMessage(sender, placeholder, replacement);

        if (content.equals(Component.empty())) return;

        sender.sendActionBar(content);
    }

    public void sendActionBar(final Audience sender, final Map<String, String> placeholders) {
        final Component content = getMessage(sender, placeholders);

        if (content.equals(Component.empty())) return;

        sender.sendActionBar(content);
    }

    public void sendActionBar(final Audience sender) {
        final Component content = getMessage(sender);

        if (content.equals(Component.empty())) return;

        sender.sendActionBar(content);
    }

    public void sendRichMessage(final Audience sender, final String placeholder, final String replacement) {
        final Component msg = getMessage(sender, placeholder, replacement);

        if (msg.equals(Component.empty())) return;

        sender.sendMessage(msg);
    }

    public void sendRichMessage(final Audience sender, final Map<String, String> placeholders) {
        final Component content = getMessage(sender, placeholders);

        if (content.equals(Component.empty())) return;

        sender.sendMessage(content);
    }

    public void sendRichMessage(final Audience sender) {
        final Component content = getMessage(sender);

        if (content.equals(Component.empty())) return;

        sender.sendMessage(content);
    }

    private @NotNull Component parse(@NotNull final Audience sender, @NotNull final Map<String, String> placeholders) {
        String message;

        if (isList()) {
            message = StringUtils.toString(getList(sender));
        } else {
            message = getString(sender);
        }
        
        placeholders.put("{prefix}", this.config.getProperty(ConfigKeys.command_prefix));

        return this.fusion.color(sender, message, placeholders);
    }
}