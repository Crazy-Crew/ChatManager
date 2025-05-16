package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.enums.Files;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class CommandMOTD extends Global implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("MOTD.Enable", false) || !command.getName().equalsIgnoreCase("motd")) return true;

		for (final String motd : config.getStringList("MOTD.Message")) {
			Methods.sendMessage(commandSender, motd, true);
		}

		return false;
	}
}