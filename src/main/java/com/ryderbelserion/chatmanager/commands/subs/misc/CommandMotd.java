package com.ryderbelserion.chatmanager.commands.subs.misc;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.commands.subs.BaseCommand;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.types.ConfigKeys;
import com.ryderbelserion.chatmanager.utils.MsgUtils;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

public class CommandMotd extends BaseCommand {

    private final SettingsManager config = ConfigManager.getConfig();

    @Command("motd")
    @Permission(value = "chatmanager.motd", def = PermissionDefault.OP)
    public void motd(final CommandSender sender) {
        if (!this.config.getProperty(ConfigKeys.motd_toggle)) {
            Messages.feature_disabled.sendMessage(sender);

            return;
        }

        for (final String line : this.config.getProperty(ConfigKeys.motd_message)) {
            MsgUtils.sendMessage(sender, line, "{player}", sender.getName());
        }
    }
}