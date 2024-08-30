package com.ryderbelserion.chatmanager.utils;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.cache.UserManager;
import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.api.enums.chat.ToggleType;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.api.events.MessageSendEvent;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.impl.types.ConfigKeys;
import com.ryderbelserion.vital.common.api.interfaces.IPlugin;
import com.ryderbelserion.vital.paper.api.enums.Support;
import com.ryderbelserion.vital.paper.util.AdvUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MsgUtils {

    private static final ChatManager plugin = ChatManager.get();

    private static final UserManager userManager = plugin.getUserManager();

    private static final SettingsManager config = ConfigManager.getConfig();

    public static void sendMessage(final CommandSender sender, final String message, @Nullable final Map<String, String> placeholders) {
        if (message.isEmpty()) return;

        final String msg = getMessage(sender, message, placeholders);

        sender.sendRichMessage(msg);
    }

    public static void sendActionBar(final CommandSender sender, final String message, @Nullable final Map<String, String> placeholders) {
        if (message.isEmpty()) return;

        sender.sendActionBar(AdvUtil.parse(getMessage(sender, message, placeholders)));
    }

    public static void sendTitle(final Player player, final String message, @Nullable final Map<String, String> placeholders) {
        if (message.isEmpty()) return;

        final Title.Times times = Title.Times.times(Duration.ofMillis(400), Duration.ofMillis(200), Duration.ofMillis(400));
        final Title title = Title.title(AdvUtil.parse(getMessage(player, message, placeholders)), Component.empty(), times);

        player.showTitle(title);
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

            if (Permissions.STAFF_CHAT_RECEIVE.hasPermission(player)) {
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
        return config.getProperty(ConfigKeys.prefix);
    }

    public static void sendMessage(final CommandSender sender, final Player target, final String message) {
        final User user = userManager.getUser(target);
        final User receiver = userManager.getUser(sender);

        // player-specific sender
        if (sender instanceof Player player) {
            final UUID id = player.getUniqueId();

            if (id.equals(target.getUniqueId())) {
                Messages.private_message_self.sendMessage(player);

                return;
            }

            if (target.getGameMode() == GameMode.SPECTATOR && !player.hasPermission(Permissions.BYPASS_SPECTATOR.getNode())) {
                Messages.player_not_found.sendMessage(player, "{target}", target.getName());

                return;
            }

            if (!player.canSee(target)) {
                Messages.player_not_found.sendMessage(player, "{target}", target.getName());

                return;
            }

            boolean shouldReturn = false;
            boolean isIgnored = false;
            boolean isMuted = false;
            boolean isAfk = false;

            for (final IPlugin plugin : ChatUtils.getPlugins()) {
                if (plugin.isEnabled()) {
                    if (plugin.isMuted(player.getUniqueId())) {
                        shouldReturn = true;
                        isMuted = true;

                        break;
                    }

                    if (plugin.isVanished(target.getUniqueId())) {
                        shouldReturn = true;

                        break;
                    }

                    if (plugin.isAfk(target.getUniqueId())) {
                        shouldReturn = true;
                        isAfk = true;

                        break;
                    }

                    if (plugin.isIgnored(player.getUniqueId(), target.getUniqueId())) {
                        shouldReturn = true;
                        isIgnored = true;

                        break;
                    }
                }
            }

            if (shouldReturn) {
                if (isMuted) { // don't send message if muted, the plugin handling the mute will send one.
                    return;
                }

                if (isAfk) {
                    Messages.private_message_afk.sendMessage(player, "{target}", target.getName());

                    return;
                }

                if (isIgnored) {
                    //todo() we need to add in a chatmanager check, as we have our own /ignore command
                    Messages.private_message_ignored.sendMessage(player, "{target}", target.getName());

                    return;
                }

                Messages.player_not_found.sendMessage(player, "{target}", target.getName());

                return;
            }

            if (user.activeChatTypes.contains(ToggleType.toggle_private_messages.getName()) && !player.hasPermission(Permissions.BYPASS_TOGGLE_PM.getNode())) {
                Messages.private_message_toggled.sendMessage(player);

                return;
            }

            final String senderFormat = config.getProperty(ConfigKeys.private_messages_sender_format);
            final String receiverFormat = config.getProperty(ConfigKeys.private_messages_receiver_format);

            MsgUtils.sendMessage(player, senderFormat, new HashMap<>() {{
                put("{receiver}", target.getName());
                put("{message}", message);
            }});

            MsgUtils.sendMessage(target, receiverFormat, new HashMap<>() {{
                put("{player}", target.getName());
                put("{message}", message);
            }});

            if (config.getProperty(ConfigKeys.private_messages_sound_toggle)) {
                final String sound = config.getProperty(ConfigKeys.private_messages_sound_value);
                final double volume = config.getProperty(ConfigKeys.private_messages_sound_volume);
                final double pitch = config.getProperty(ConfigKeys.private_messages_sound_pitch);

                MiscUtils.playSound(target, sound, volume, pitch);
            }

            // add reply data to player
            receiver.replyPlayer = target.getUniqueId().toString();
            user.replyPlayer = player.getUniqueId().toString();

            return;
        }

        final String senderFormat = config.getProperty(ConfigKeys.private_messages_sender_format);
        final String receiverFormat = config.getProperty(ConfigKeys.private_messages_receiver_format);

        MsgUtils.sendMessage(sender, senderFormat.replace("{receiver}", target.getName()));
        MsgUtils.sendMessage(target, receiverFormat.replace("{player}", sender.getName()));

        receiver.replyPlayer = target.getUniqueId().toString();
        user.replyPlayer = sender.getName();
    }
}