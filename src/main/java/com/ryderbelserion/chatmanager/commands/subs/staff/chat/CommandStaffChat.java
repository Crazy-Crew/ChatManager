package com.ryderbelserion.chatmanager.commands.subs.staff.chat;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.api.cache.UserManager;
import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.commands.subs.BaseCommand;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.types.ConfigKeys;
import com.ryderbelserion.chatmanager.utils.MsgUtils;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Optional;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import java.util.HashMap;

public class CommandStaffChat extends BaseCommand {

    private final UserManager userManager = this.plugin.getUserManager();
    private final SettingsManager config = ConfigManager.getConfig();

    @Command
    @Permission(value = "chatmanager.staff.chat", def = PermissionDefault.OP)
    public void execute(final CommandSender sender, @Optional final String message) {
        if (!this.config.getProperty(ConfigKeys.staff_chat_toggle)) return;

        if (sender instanceof Player player) {
            final User user = this.userManager.getUser(player);

            if (user.isStaffChat) {
                user.isStaffChat = false;

                if (this.config.getProperty(ConfigKeys.staff_bossbar_toggle)) {
                    user.hideBossBar();
                }

                Messages.staff_chat_disabled.sendMessage(user.player);

                return;
            }

            user.isStaffChat = true;

            if (this.config.getProperty(ConfigKeys.staff_bossbar_toggle) && !this.plugin.isLegacy()) {
                user.showBossBar();
            }

            Messages.staff_chat_enabled.sendMessage(user.player);

            return;
        }

        MsgUtils.sendMessage(sender, this.config.getProperty(ConfigKeys.staff_chat_format), new HashMap<>() {{
            put("{player}", sender.getName());
            put("{message}", message);
        }});
    }
}