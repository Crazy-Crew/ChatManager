package com.ryderbelserion.chatmanager.commands.subs.player.conversations;

import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.commands.subs.BaseCommand;
import com.ryderbelserion.chatmanager.utils.MsgUtils;
import com.ryderbelserion.vital.paper.api.builders.PlayerBuilder;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.ArgName;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Suggestion;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

public class CommandMsg extends BaseCommand {

    @Command("msg")
    @Permission(value = "chatmanager.msg", def = PermissionDefault.TRUE, description = "Send a message to another player")
    public void msg(final CommandSender sender, @ArgName("player") @Suggestion("players") final PlayerBuilder builder, final String message) {
        if (builder.getPlayer() == null || !builder.getPlayer().isOnline()) { //todo() add a mailbox feature, i.e. if you send a message to an offline player. it'll notify them on join
            Messages.player_not_found.sendMessage(sender, "{target}", builder.name());

            return;
        }

        if (message.isEmpty()) {
            Messages.message_empty.sendMessage(sender);

            return;
        }

        final Player target = builder.getPlayer();

        MsgUtils.sendMessage(sender, target);
    }
}