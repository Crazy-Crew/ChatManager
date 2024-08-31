package com.ryderbelserion.chatmanager.api.enums.other;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.properties.Property;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.impl.messages.commands.ChatKeys;
import com.ryderbelserion.chatmanager.configs.impl.messages.ErrorKeys;
import com.ryderbelserion.chatmanager.configs.impl.messages.MiscKeys;
import com.ryderbelserion.chatmanager.configs.impl.messages.PlayerKeys;
import com.ryderbelserion.chatmanager.configs.impl.messages.commands.SpyKeys;
import com.ryderbelserion.chatmanager.configs.impl.messages.commands.ToggleKeys;
import com.ryderbelserion.chatmanager.configs.impl.types.ConfigKeys;
import com.ryderbelserion.chatmanager.configs.impl.types.MessageKeys;
import com.ryderbelserion.chatmanager.utils.MsgUtils;
import com.ryderbelserion.vital.common.utils.StringUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Messages {

    feature_disabled(MiscKeys.feature_disabled),
    unknown_command(MiscKeys.unknown_command),
    correct_usage(MiscKeys.correct_usage),

    internal_error(ErrorKeys.internal_error),
    message_empty(ErrorKeys.message_empty),

    player_not_found(PlayerKeys.not_online),
    no_permission(PlayerKeys.no_permission),
    same_player(PlayerKeys.same_player),

    must_be_a_player(PlayerKeys.must_be_a_player),
    must_be_console_sender(PlayerKeys.must_be_console_sender),
    inventory_not_empty(PlayerKeys.inventory_not_empty),
    plugin_reload(MessageKeys.plugin_reload),

    spy_toggle(SpyKeys.spy_toggle),
    spy_enabled(SpyKeys.spy_enabled),
    spy_disabled(SpyKeys.spy_disabled),

    spy_chat_format(SpyKeys.spy_chat_format),
    spy_command_format(SpyKeys.spy_command_format),

    chatradius_toggle(ChatKeys.chatradius_toggle),
    chatradius_enabled(ChatKeys.chatradius_enabled),
    chatradius_disabled(ChatKeys.chatradius_disabled),
    chatradius_help(ChatKeys.chatradius_help, true),
    chatradius_already_enabled(ChatKeys.chatradius_already_enabled),

    chat_toggle(ToggleKeys.chat_toggle),
    toggle_enabled(ToggleKeys.toggle_enabled),
    toggle_disabled(ToggleKeys.toggle_disabled),

    filter_value_added(ChatKeys.filter_value_added),
    filter_value_remove(ChatKeys.filter_value_remove),
    filter_value_exists(ChatKeys.filter_value_exists),
    filter_value_not_found(ChatKeys.filter_value_not_found),
    filter_command_notify_staff(ChatKeys.filter_command_notify_staff),
    filter_word_notify_staff(ChatKeys.filter_word_notify_staff),
    filter_command_help(ChatKeys.filter_command_help, true),

    // old keys
    anti_advertising_chat_message(MessageKeys.anti_advertising_chat_message),
    anti_advertising_chat_notify_staff(MessageKeys.anti_advertising_chat_notify_staff),

    anti_advertising_commands_message(MessageKeys.anti_advertising_commands_message),
    anti_advertising_commands_notify_staff(MessageKeys.anti_advertising_commands_notify_staff),

    anti_advertising_signs_message(MessageKeys.anti_advertising_signs_message),
    anti_advertising_signs_notify_staff(MessageKeys.anti_advertising_signs_notify_staff),

    anti_bot_deny_chat_message(MessageKeys.anti_bot_deny_chat),
    anti_bot_deny_command_message(MessageKeys.anti_bot_deny_command),

    anti_caps_message_chat(MessageKeys.anti_caps_message_chat),
    anti_caps_message_commands(MessageKeys.anti_caps_message_command),

    anti_spam_chat_repetitive_message(MessageKeys.anti_spam_chat_repeated_message),
    anti_spam_chat_delay_message(MessageKeys.anti_spam_chat_delay_message),

    anti_spam_command_repetitive_message(MessageKeys.anti_spam_command_repeated_message),
    anti_spam_command_delay_message(MessageKeys.anti_spam_command_delay_message),

    anti_swear_chat_message(MessageKeys.anti_swear_chat_message),
    anti_swear_chat_notify_staff_format(MessageKeys.anti_swear_chat_notify_staff),

    anti_swear_command_message(MessageKeys.anti_swear_commands_message),
    anti_swear_command_notify_staff_format(MessageKeys.anti_swear_commands_notify_staff),

    anti_swear_signs_message(MessageKeys.anti_swear_signs_message),
    anti_swear_signs_notify_staff_format(MessageKeys.anti_swear_signs_notify_staff),

    anti_swear_blacklisted_word_added(MessageKeys.blacklist_added),
    anti_swear_blacklisted_word_exists(MessageKeys.blacklist_exists),
    anti_swear_blacklisted_word_removed(MessageKeys.blacklist_removed),
    anti_swear_blacklisted_word_not_found(MessageKeys.blacklist_not_found),

    anti_swear_whitelisted_word_added(MessageKeys.whitelist_added),
    anti_swear_whitelisted_word_exists(MessageKeys.whitelist_exists),
    anti_swear_whitelisted_word_removed(MessageKeys.whitelist_removed),
    anti_swear_whitelisted_word_not_found(MessageKeys.whitelist_not_found),

    anti_unicode_message(MessageKeys.anti_unicode_message),
    anti_unicode_notify_staff_format(MessageKeys.anti_unicode_notify_staff),

    auto_broadcast_list(MessageKeys.auto_broadcast_list),
    auto_broadcast_added(MessageKeys.auto_broadcast_added),
    auto_broadcast_created(MessageKeys.auto_broadcast_created),

    chat_radius_local_chat_enabled(MessageKeys.chat_radius_local_chat_enabled),
    chat_radius_local_chat_already_enabled(MessageKeys.chat_radius_local_chat_already_enabled),

    chat_radius_global_chat_enabled(MessageKeys.chat_radius_global_chat_enabled),
    chat_radius_global_chat_already_enabled(MessageKeys.chat_radius_global_chat_already_enabled),

    chat_radius_world_chat_enabled(MessageKeys.chat_radius_world_chat_enabled),
    chat_radius_world_chat_already_enabled(MessageKeys.chat_radius_world_chat_already_enabled),

    chat_radius_spy_enabled(MessageKeys.chat_radius_spy_enabled),
    chat_radius_spy_disabled(MessageKeys.chat_radius_spy_disabled),

    clear_chat_staff_message(MessageKeys.clear_chat_staff_message),
    clear_chat_broadcast_message(MessageKeys.clear_chat_broadcast_message, true),

    mute_chat_denied_message(MessageKeys.mute_chat_denied_message),
    mute_chat_broadcast_messages_enabled(MessageKeys.mute_chat_broadcast_enabled),
    mute_chat_broadcast_messages_disabled(MessageKeys.mute_chat_broadcast_disabled),
    mute_chat_blocked_commands_message(MessageKeys.mute_chat_blocked_commands_message),

    per_world_chat_bypass_enabled(MessageKeys.per_world_chat_bypass_enabled),
    per_world_chat_bypass_disabled(MessageKeys.per_world_chat_bypass_disabled),

    private_message_recipient_not_found(MessageKeys.private_message_recipient_not_found),
    private_message_toggled(MessageKeys.private_message_toggled),
    private_message_ignored(MessageKeys.private_message_ignored),
    private_message_self(MessageKeys.private_message_self),
    private_message_afk(MessageKeys.private_message_afk),

    staff_chat_enabled(MessageKeys.staff_chat_enabled),
    staff_chat_disabled(MessageKeys.staff_chat_disabled);

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

    private final SettingsManager config = ConfigManager.getConfig();

    private final SettingsManager messages = ConfigManager.getMessages();

    private boolean isList() {
        return this.isList;
    }

    public String getString() {
        return this.messages.getProperty(this.property);
    }

    public List<String> getList() {
        return this.messages.getProperty(this.properties);
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
        return parse(sender, placeholders).replaceAll("\\{prefix}", MsgUtils.getPrefix());
    }

    public void sendMessage(final CommandSender sender, final String placeholder, final String replacement) {
        sender.sendRichMessage(getMessage(sender, placeholder, replacement));
    }

    public void sendMessage(final CommandSender sender, final Map<String, String> placeholders) {
        sender.sendRichMessage(getMessage(sender, placeholders));
    }

    public void sendMessage(final CommandSender sender) {
        sender.sendRichMessage(getMessage(sender));
    }

    private @NotNull String parse(@NotNull final CommandSender sender, @Nullable final Map<String, String> placeholders) {
        String message;

        if (isList()) {
            message = StringUtil.chomp(StringUtil.convertList(getList()));
        } else {
            message = getString();
        }

        return MsgUtils.getMessage(sender, message, placeholders);
    }

    public void broadcast() {
        broadcast(null);
    }

    public void broadcast(@Nullable final Map<String, String> placeholders) {
        sendMessage(this.plugin.getServer().getConsoleSender(), placeholders);

        for (Player player : this.plugin.getServer().getOnlinePlayers()) {
            sendMessage(player, placeholders);
        }
    }
}