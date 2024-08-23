package com.ryderbelserion.chatmanager.commands.subs.player;

import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.api.enums.chat.ToggleState;
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
    @Permission(value = "chatmanager.toggle", def = PermissionDefault.OP, description = "Access to /chatmanager toggle <option>")
    public void toggle(final Player player, @Suggestion("toggle_states") final String value) {
        final ToggleState state = ToggleState.getToggleState(value);

        final User user = this.userManager.getUser(player);

        if (user.activeChatToggles.contains(state.getName())) {
            user.activeChatToggles.remove(state.getName());

            Messages.chat_toggle.sendMessage(player, new HashMap<>() {{
                put("{state}", state.getPrettyName());
                put("{status}", messages.getProperty(ToggleKeys.toggle_disabled));
            }});

            return;
        }

        user.activeChatToggles.add(state.getName());

        Messages.chat_toggle.sendMessage(player, new HashMap<>() {{
            put("{state}", state.getPrettyName());
            put("{status}", messages.getProperty(ToggleKeys.toggle_enabled));
        }});
    }
}