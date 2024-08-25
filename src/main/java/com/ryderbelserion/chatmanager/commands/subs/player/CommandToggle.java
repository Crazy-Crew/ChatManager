package com.ryderbelserion.chatmanager.commands.subs.player;

import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.api.enums.chat.ToggleType;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.commands.subs.BaseCommand;
import com.ryderbelserion.chatmanager.configs.impl.messages.commands.ToggleKeys;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Suggestion;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import java.util.HashMap;

public class CommandToggle extends BaseCommand {

    @Command("toggle")
    @Permission(value = "chatmanager.toggle", def = PermissionDefault.OP, description = "Access to /chatmanager toggle <type>")
    public void toggle(final Player player, @Suggestion("toggle_type") final String value) {
        final ToggleType type = ToggleType.getToggleType(value);

        final User user = this.userManager.getUser(player);

        if (user.activeChatTypes.contains(type.getName())) {
            user.activeChatTypes.remove(type.getName());

            Messages.chat_toggle.sendMessage(player, new HashMap<>() {{
                put("{state}", type.getPrettyName());
                put("{status}", messages.getProperty(ToggleKeys.toggle_disabled));
            }});

            return;
        }

        user.activeChatTypes.add(type.getName());

        Messages.chat_toggle.sendMessage(player, new HashMap<>() {{
            put("{state}", type.getPrettyName());
            put("{status}", messages.getProperty(ToggleKeys.toggle_enabled));
        }});
    }
}