package com.ryderbelserion.chatmanager.commands.subs.player.conversations;

import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.commands.subs.BaseCommand;
import com.ryderbelserion.vital.paper.api.builders.PlayerBuilder;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

public class CommandReply extends BaseCommand {

    @Command("reply")
    @Permission(value = "chatmanager.reply", def = PermissionDefault.TRUE, description = "Reply to another player")
    public void reply(final CommandSender sender, final String message) {
        if (message.isEmpty()) {
            Messages.message_empty.sendMessage(sender);

            return;
        }

        final User receiver = this.userManager.getUser(sender);

        final String target = receiver.replyPlayer;

        if (target.isEmpty()) {
            // no one to reply to

            return;
        }

        final PlayerBuilder builder = new PlayerBuilder(target);

        if (builder.getPlayer() == null || !builder.getPlayer().isOnline()) { //todo() add a mailbox feature, i.e. if you send a message to an offline player. it'll notify them on join
            Messages.player_not_found.sendMessage(sender, "{target}", builder.name());

            return;
        }

        final Player player = builder.getPlayer();
    }
}