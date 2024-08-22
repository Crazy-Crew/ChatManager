package com.ryderbelserion.chatmanager.utils;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.api.events.MessageSendEvent;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.types.ConfigKeys;
import com.ryderbelserion.vital.paper.api.enums.Support;
import com.ryderbelserion.vital.paper.util.ItemUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.Map;

public class MsgUtils {

    private static final ChatManager plugin = ChatManager.get();

    private static final SettingsManager config = ConfigManager.getConfig();

    public static void sendMessage(final CommandSender sender, final String message, @Nullable final Map<String, String> placeholders) {
        if (message.isEmpty()) return;

        final String msg = getMessage(sender, message, placeholders);

        if (plugin.isLegacy()) {
            sender.sendMessage(ItemUtil.color(msg));
        } else {
            sender.sendRichMessage(msg);
        }
    }

    public static void sendMessage(final CommandSender sender, final String message, final String placeholder, final String replacement) {
        sendMessage(sender, message, new HashMap<>() {{
            put(placeholder, replacement);
        }});
    }

    public static void sendMessage(final CommandSender sender, final String message) {
        sendMessage(sender, message, null);
    }

    public static String getMessage(final CommandSender sender, final String message, @Nullable final Map<String, String> placeholders) {
        String msg = message;

        if (sender instanceof Player player) {
            if (Support.placeholder_api.isEnabled()) {
                msg = PlaceholderAPI.setPlaceholders(player, msg);
            }
        }

        if (placeholders != null && !placeholders.isEmpty()) {
            for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
                if (placeholder != null) {
                    final String key = placeholder.getKey();
                    final String value = placeholder.getValue();

                    if (key != null && value != null) {
                        msg = msg.replace(key, value).replace(key.toLowerCase(), value);
                    }
                }
            }
        }

        return msg;
    }

    public static void send(final CommandSender sender, final String message, final boolean isConsole) {
        // call the event, with only the sender.
        plugin.getServer().getPluginManager().callEvent(new MessageSendEvent(sender, message));

        plugin.getServer().getOnlinePlayers().forEach(player -> {
            if (sender instanceof Player person) {
                if (player.getUniqueId().equals(person.getUniqueId())) {
                    return;
                }
            }

            if (Permissions.RECEIVE_STAFF_CHAT.hasPermission(player)) {
                MsgUtils.sendMessage(player, config.getProperty(ConfigKeys.staff_chat_format), new HashMap<>() {{
                    put("{player}", sender.getName());
                    put("{message}", message);
                }});
            }
        });

        if (isConsole) {
            // send to console
            MsgUtils.sendMessage(plugin.getServer().getConsoleSender(), config.getProperty(ConfigKeys.staff_chat_format), new HashMap<>() {{
                put("{player}", sender.getName());
                put("{message}", message);
            }});
        } else {
            // send to the sender, so they know what they sent
            MsgUtils.sendMessage(sender, config.getProperty(ConfigKeys.staff_chat_format), new HashMap<>() {{
                put("{player}", sender.getName());
                put("{message}", message);
            }});
        }
    }

    /**
     * @return the {@link String}
     */
    public static @NotNull String getPrefix() {
        return ConfigManager.getConfig().getProperty(ConfigKeys.prefix);
    }
}