package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.enums.Files;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class CommandRules implements CommandExecutor {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		FileConfiguration config = Files.CONFIG.getConfiguration();

		if (cmd.getName().equalsIgnoreCase("rules")) {
			if (sender.hasPermission(Permissions.COMMAND_RULES.getNode())) {
				if (args.length == 0) {
					for (String rules : config.getStringList("Server_Rules.Rules.1")) {
						this.plugin.getMethods().sendMessage(sender, rules, true);
					}
				} else if (args.length == 1) {
					int page = Integer.parseInt(args[0]);

					for (String rules : config.getStringList("Server_Rules.Rules." + page)) {
						this.plugin.getMethods().sendMessage(sender, rules, true);
					}
				} else {
					this.plugin.getMethods().sendMessage(sender, "&cCommand Usage: &7/rules <page>", true);
				}
			} else {
				this.plugin.getMethods().sendMessage(sender, this.plugin.getMethods().noPermission(), true);
			}
		}

		return true;
	}
}