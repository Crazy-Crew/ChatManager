package com.ryderbelserion.chatmanager.commands.subs.staff.chat;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.api.cache.UserManager;
import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.commands.subs.BaseCommand;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.impl.types.ConfigKeys;
import com.ryderbelserion.chatmanager.utils.MsgUtils;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Optional;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

public class CommandStaffChat extends BaseCommand {

    private final UserManager userManager = this.plugin.getUserManager();
    private final SettingsManager config = ConfigManager.getConfig();

    @Command("staffchat")
    @Permission(value = "chatmanager.staff.chat", def = PermissionDefault.OP, description = "Allows you to send messages to other staff!")
    public void chat(final CommandSender sender, @Optional final String message) {
        if (!this.config.getProperty(ConfigKeys.staff_chat_toggle)) {
            Messages.feature_disabled.sendMessage(sender);

            return;
        }

        if (sender instanceof Player player) {
            final User user = this.userManager.getUser(player);

            if (!message.isEmpty()) {
                MsgUtils.send(user.player, message, false);

                return;
            }

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

        MsgUtils.send(sender, message, true);
    }
}