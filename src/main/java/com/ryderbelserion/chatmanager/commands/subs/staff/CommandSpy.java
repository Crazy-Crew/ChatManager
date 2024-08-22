package com.ryderbelserion.chatmanager.commands.subs.staff;

import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.api.enums.chat.SpyState;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.commands.subs.BaseCommand;
import com.ryderbelserion.chatmanager.configs.impl.messages.commands.SpyKeys;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Suggestion;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import java.util.HashMap;

public class CommandSpy extends BaseCommand {

    @Command("spy")
    @Permission(value = "chatmanager.spy", def = PermissionDefault.OP, description = "Access to /chatmanager spy <state>")
    public void spy(final Player player, @Suggestion("spy_states") final String state) {
        final SpyState spyState = SpyState.getSpyState(state);

        final User user = this.userManager.getUser(player);

        if (user.activeSpyStates.contains(spyState)) {
            user.activeSpyStates.remove(spyState);

            Messages.spy_toggle.sendMessage(player, new HashMap<>() {{
                put("{state}", spyState.getSpyState());
                put("{status}", messages.getProperty(SpyKeys.spy_disabled));
            }});

            return;
        }

        user.activeSpyStates.add(spyState);

        Messages.spy_toggle.sendMessage(player, new HashMap<>() {{
            put("{state}", spyState.getSpyState());
            put("{status}", messages.getProperty(SpyKeys.spy_enabled));
        }});
    }
}