package com.ryderbelserion.chatmanager.commands.subs.player.conversations;

import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.api.enums.chat.ToggleType;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.commands.subs.BaseCommand;
import com.ryderbelserion.chatmanager.configs.types.ConfigKeys;
import com.ryderbelserion.chatmanager.utils.ChatUtils;
import com.ryderbelserion.chatmanager.utils.MsgUtils;
import com.ryderbelserion.vital.common.api.interfaces.IPlugin;
import com.ryderbelserion.vital.paper.api.builders.PlayerBuilder;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.ArgName;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Suggestion;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import java.util.UUID;

public class CommandMsg extends BaseCommand {

    @Command("msg")
    @Permission(value = "chatmanager.msg", def = PermissionDefault.TRUE, description = "Send a message to another player")
    public void msg(final CommandSender sender, @ArgName("player") @Suggestion("players") final PlayerBuilder builder, final String message) {
        if (builder.getPlayer() == null || !builder.getPlayer().isOnline()) { //todo() add a mailbox feature, i.e. if you send a message to an offline player. it'll notify them on join
            Messages.player_not_found.sendMessage(sender, "{target}", builder.name());

            return;
        }

        if (message.isEmpty()) {
            //todo() message can't be empty.

            return;
        }

        // player-specific sender
        if (sender instanceof Player player) {
            final UUID id = player.getUniqueId();
            final Player target = builder.getPlayer();

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

            final User user = this.userManager.getUser(target);

            if (user.activeChatTypes.contains(ToggleType.toggle_private_messages.getName()) && !player.hasPermission(Permissions.BYPASS_TOGGLE_PM.getNode())) {
                Messages.private_message_toggled.sendMessage(player);

                return;
            }

            final String senderFormat = this.config.getProperty(ConfigKeys.private_messages_sender_format);
            final String receiverFormat = this.config.getProperty(ConfigKeys.private_messages_receiver_format);

            MsgUtils.sendMessage(player, senderFormat.replace("{receiver}", target.getName()));

            MsgUtils.sendMessage(target, receiverFormat.replace("{player}", player.getName()));

            //Methods.playSound(target, config, "Private_Messages.sound");
        }
    }
}