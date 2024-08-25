package com.ryderbelserion.chatmanager.commands.subs.player;

import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.api.enums.chat.ChatType;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.commands.subs.BaseCommand;
import com.ryderbelserion.chatmanager.configs.impl.messages.commands.ChatKeys;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Suggestion;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import java.util.HashMap;

public class CommandRadius extends BaseCommand {

    @Command("chatradius")
    @Permission(value = "chatmanager.chatradius", def = PermissionDefault.OP, description = "Access to /chatmanager radius <type>")
    public void radius(final Player player, @Suggestion("chat_type") final String value) {
        if (value.equalsIgnoreCase("help")) {
            Messages.chatradius_help.sendMessage(player);

            return;
        }

        final ChatType type = ChatType.getChatType(value);

        final User user = this.userManager.getUser(player);

        if (user.chatType.getName().equalsIgnoreCase(type.getName())) {
            Messages.chatradius_already_enabled.sendMessage(player, new HashMap<>() {{
                put("{state}", type.getPrettyName());
                put("{status}", messages.getProperty(ChatKeys.chatradius_enabled));
            }});

            return;
        }

        user.chatType = type;

        Messages.chatradius_toggle.sendMessage(player, new HashMap<>() {{
            put("{state}", type.getPrettyName());
            put("{status}", messages.getProperty(ChatKeys.chatradius_enabled));
        }});
    }
}