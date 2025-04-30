package com.ryderbelserion.chatmanager.enums;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.properties.Property;
import com.ryderbelserion.chatmanager.api.configs.locale.RootKeys;
import com.ryderbelserion.chatmanager.api.objects.PaperUser;
import com.ryderbelserion.chatmanager.managers.ConfigManager;
import com.ryderbelserion.chatmanager.managers.UserManager;
import com.ryderbelserion.fusion.core.utils.StringUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.PluginSupport;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum Messages {

    PREFIX(RootKeys.message_prefix),
    NO_PERMISSION(RootKeys.no_permission),
    PLAYER_NOT_FOUND(RootKeys.player_not_found),
    PLUGIN_RELOAD(RootKeys.reload),
    INVALID_USAGE(RootKeys.invalid_usage),

    ANTI_ADVERTISING_CHAT_MESSAGE(RootKeys.anti_advertising_chat_message),
    ANTI_ADVERTISING_CHAT_NOTIFY_STAFF(RootKeys.anti_advertising_chat_notify_staff),

    ANTI_ADVERTISING_COMMANDS_MESSAGE(RootKeys.anti_advertising_commands_message),
    ANTI_ADVERTISING_COMMANDS_NOTIFY_STAFF(RootKeys.anti_advertising_commands_notify_staff),

    ANTI_ADVERTISING_SIGNS_MESSAGE(RootKeys.anti_advertising_signs_message),
    ANTI_ADVERTISING_SIGNS_NOTIFY_STAFF(RootKeys.anti_advertising_signs_notify_staff),

    ANTI_BOT_DENY_CHAT_MESSAGE(RootKeys.anti_bot_deny_chat_message),
    ANTI_BOT_DENY_COMMAND_MESSAGE(RootKeys.anti_bot_deny_command_message),

    ANTI_CAPS_MESSAGE_CHAT(RootKeys.anti_caps_message_chat),
    ANTI_CAPS_MESSAGE_COMMANDS(RootKeys.anti_caps_message_commands),

    ANTI_SPAM_CHAT_REPETITIVE_MESSAGE(RootKeys.anti_spam_chat_repetition),
    ANTI_SPAM_CHAT_DELAY_MESSAGE(RootKeys.anti_spam_chat_delay),

    ANTI_SPAM_COMMAND_REPETITIVE_MESSAGE(RootKeys.anti_spam_command_repetition),
    ANTI_SPAM_COMMAND_DELAY_MESSAGE(RootKeys.anti_spam_command_delay),

    ANTI_SWEAR_CHAT_MESSAGE(RootKeys.anti_swear_chat_message),
    ANTI_SWEAR_CHAT_NOTIFY_STAFF_FORMAT(RootKeys.anti_swear_chat_notify_staff),

    ANTI_SWEAR_COMMAND_MESSAGE(RootKeys.anti_swear_commands_message),
    ANTI_SWEAR_COMMAND_NOTIFY_STAFF_FORMAT(RootKeys.anti_swear_commands_notify_staff),

    ANTI_SWEAR_SIGNS_MESSAGE(RootKeys.anti_swear_signs_message),
    ANTI_SWEAR_SIGNS_NOTIFY_STAFF_FORMAT(RootKeys.anti_swear_signs_notify_staff),
    ANTI_UNICODE_MESSAGE(RootKeys.anti_unicode_message),
    ANTI_UNICODE_NOTIFY_STAFF_FORMAT(RootKeys.anti_unicode_notify_staff),

    CHAT_RADIUS_LOCAL_CHAT_ENABLED(RootKeys.chat_radius_local_chat_enabled),
    CHAT_RADIUS_LOCAL_CHAT_ALREADY_ENABLED(RootKeys.chat_radius_local_chat_already_enabled),

    CHAT_RADIUS_GLOBAL_CHAT_ENABLED(RootKeys.chat_radius_global_chat_enabled),
    CHAT_RADIUS_GLOBAL_CHAT_ALREADY_ENABLED(RootKeys.chat_radius_global_chat_already_enabled),

    CHAT_RADIUS_WORLD_CHAT_ENABLED(RootKeys.chat_radius_world_chat_enabled),
    CHAT_RADIUS_WORLD_CHAT_ALREADY_ENABLED(RootKeys.chat_radius_world_chat_already_enabled),

    CHAT_RADIUS_SPY_ENABLED(RootKeys.chat_radius_spy_enabled),
    CHAT_RADIUS_SPY_DISABLED(RootKeys.chat_radius_spy_disabled),

    CLEAR_CHAT_STAFF_MESSAGE(RootKeys.clear_chat_staff_message),
    CLEAR_CHAT_BROADCAST_MESSAGE(RootKeys.clear_chat_broadcast_message, true),

    BANNED_COMMANDS_MESSAGE(RootKeys.banned_commands_message),
    BANNED_COMMANDS_NOTIFY_STAFF(RootKeys.banned_commands_notify_staff),

    COMMAND_SPY_FORMAT(RootKeys.command_spy_format),
    COMMAND_SPY_ENABLED(RootKeys.command_spy_enabled),
    COMMAND_SPY_DISABLED(RootKeys.command_spy_disabled),

    MUTE_CHAT_DENIED_MESSAGE(RootKeys.mute_chat_denied_message),
    MUTE_CHAT_BROADCAST_MESSAGES_ENABLED(RootKeys.mute_chat_broadcast_message_enabled),
    MUTE_CHAT_BROADCAST_MESSAGES_DISABLED(RootKeys.mute_chat_broadcast_message_disabled),
    MUTE_CHAT_BLOCKED_COMMANDS_MESSAGE(RootKeys.mute_chat_blocked_commands_message),

    PER_WORLD_CHAT_BYPASS_ENABLED(RootKeys.per_world_chat_bypass_enabled),
    PER_WORLD_CHAT_BYPASS_DISABLED(RootKeys.per_world_chat_bypass_disabled),

    PING_PLAYERS_PING(RootKeys.ping_players_ping),
    PING_TARGETS_PING(RootKeys.ping_targets_ping),

    PRIVATE_MESSAGE_RECIPIENT_NOT_FOUND(RootKeys.private_message_recipient_not_found),
    PRIVATE_MESSAGE_TOGGLED(RootKeys.private_message_toggled),
    PRIVATE_MESSAGE_IGNORED(RootKeys.private_message_ignored),
    PRIVATE_MESSAGE_SELF(RootKeys.private_message_self),
    PRIVATE_MESSAGE_AFK(RootKeys.private_message_afk),

    SOCIAL_SPY_FORMAT(RootKeys.social_spy_format),
    SOCIAL_SPY_ENABLED(RootKeys.social_spy_enabled),
    SOCIAL_SPY_DISABLED(RootKeys.social_spy_disabled),

    STAFF_CHAT_ENABLED(RootKeys.staff_chat_enabled),
    STAFF_CHAT_DISABLED(RootKeys.staff_chat_disabled),

    TOGGLE_CHAT_ENABLED(RootKeys.toggle_chat_enabled),
    TOGGLE_CHAT_DISABLED(RootKeys.toggle_chat_disabled),

    TOGGLE_MENTIONS_ENABLED(RootKeys.toggle_mentions_enabled),
    TOGGLE_MENTIONS_DISABLED(RootKeys.toggle_mentions_disabled),

    TOGGLE_PM_ENABLED(RootKeys.toggle_pm_enabled),
    TOGGLE_PM_DISABLED(RootKeys.toggle_pm_disabled);

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

    private final ChatManager plugin = ChatManager.get();

    private final Server server = this.plugin.getServer();

    private final UserManager userManager = this.plugin.getUserManager();

    private final SettingsManager locale = ConfigManager.getLocale();

    public String getString(final CommandSender sender) {
        if (sender instanceof Player player) {
            final Optional<PaperUser> user = this.userManager.getUser(player.getUniqueId());

            return user.map(paperUser -> paperUser.locale().getProperty(this.property)).orElseGet(() -> this.locale.getProperty(this.property));
        }

        return this.locale.getProperty(this.property);
    }

    public List<String> getList(final CommandSender sender) {
        if (!isList()) {
            return List.of(getString(sender));
        }

        if (sender instanceof Player player) {
            final Optional<PaperUser> user = this.userManager.getUser(player.getUniqueId());

            return user.map(output -> output.locale().getProperty(this.properties)).orElseGet(() -> this.locale.getProperty(this.properties));
        }

        return this.locale.getProperty(this.properties);
    }

    public String getMessage(@NotNull final CommandSender sender) {
        return getMessage(sender, new HashMap<>());
    }

    public String getMessage(@NotNull final CommandSender sender, @NotNull final String placeholder, @NotNull final String replacement) {
        Map<String, String> placeholders = new HashMap<>() {{
            put(placeholder, replacement);
        }};

        return getMessage(sender, placeholders);
    }

    public String getMessage(@NotNull final CommandSender sender, @NotNull final Map<String, String> placeholders) {
        final String prefix = Methods.getPrefix();

        placeholders.putIfAbsent("{prefix}", prefix);
        placeholders.putIfAbsent("{Prefix}", prefix);

        return parse(sender, placeholders);
    }

    public void sendMessage(final CommandSender sender, final String placeholder, final String replacement) {
        sendRichMessage(sender, placeholder, replacement);
    }

    public void sendMessage(final CommandSender sender, final Map<String, String> placeholders) {
        sendRichMessage(sender, placeholders);
    }

    public void sendMessage(final CommandSender sender) {
        sendRichMessage(sender);
    }

    public void sendRichMessage(final CommandSender sender, final String placeholder, final String replacement) {
        final String component = getMessage(sender, placeholder, replacement);

        if (component.isEmpty()) return;

        sender.sendMessage(component);
    }

    public void sendRichMessage(final CommandSender sender, final Map<String, String> placeholders) {
        final String component = getMessage(sender, placeholders);

        if (component.isEmpty()) return;

        sender.sendMessage(component);
    }

    public void sendRichMessage(final CommandSender sender) {
        final String component = getMessage(sender);

        if (component.isEmpty()) return;

        sender.sendMessage(component);
    }

    private String parse(final CommandSender sender, final Map<String, String> placeholders) {
        String message;

        if (isList()) {
            message = StringUtils.toString(getList(sender));
        } else {
            final String prefix = Methods.getPrefix();

            message = this.locale.getProperty(this.property).replaceAll("\\{prefix}", prefix).replaceAll( "\\{Prefix}", prefix);

            if (sender instanceof Player player) {
                if (PluginSupport.PLACEHOLDERAPI.isPluginEnabled()) {
                    message = PlaceholderAPI.setPlaceholders(player, message);
                }
            }

            for (final String ph : placeholders.keySet()) {
                if (message.contains(ph)) {
                    message = message.replace(ph, placeholders.get(ph)).replace(ph, placeholders.get(ph).toLowerCase());
                }
            }
        }

        return message;
    }

    public void broadcast(final CommandSender sender, final String permission) {
        final String message = getMessage(sender, new HashMap<>() {{
            put("{player}", sender.getName());
            put("{prefix}", Methods.getPrefix());
        }});

        if (permission.isBlank()) {
            this.server.broadcastMessage(message);

            return;
        }

        this.server.broadcast(message, permission);
    }

    public void broadcast(final CommandSender sender) {
        broadcast(sender, "");
    }

    public final boolean isList() {
        return this.isList;
    }
}