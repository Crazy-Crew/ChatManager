package me.h1dd3nxn1nja.chatmanager.tabcompleter;

import java.util.ArrayList;
import java.util.List;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import me.h1dd3nxn1nja.chatmanager.ChatManager;

public class TabCompleteAutoBroadcast implements TabCompleter {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String commandLable, String[] args) {
		
		List<String> completions = new ArrayList<>();
		
		if (args.length == 1) {
			if (hasPermission(sender, "help")) completions.add("help");
			if (hasPermission(sender, "list")) completions.add("list");
			if (hasPermission(sender, "add")) completions.add("add");
			if (hasPermission(sender, "create")) completions.add("create");return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
		} else if (args.length == 2) {
			switch (args[0].toLowerCase()) {
				case "help":
				case "list":
				case "add":
					completions.add("global");
					completions.add("world");
					completions.add("actionbar");
					completions.add("title");
					completions.add("bossbar");
					break;
				case "create":
					plugin.getServer().getWorlds().forEach(world -> completions.add(world.getName()));
					break;
			}
			return StringUtil.copyPartialMatches(args[1], completions, new ArrayList<>());
		} else if (args.length == 3) {
			switch (args[0].toLowerCase()) {
				case "list":
					completions.addAll(settingsManager.getAutoBroadcast().getConfigurationSection("Auto_Broadcast.Per_World_Messages.Messages").getKeys(false));
					break;
				case "add":
					plugin.getServer().getWorlds().forEach(world -> completions.add(world.getName()));
					break;
				case "create":
					completions.add("<message>");
					break;
			}

			return StringUtil.copyPartialMatches(args[2], completions, new ArrayList<>());
		} else if (args.length == 4) {
			switch (args[0].toLowerCase()) {
				case "add":
					completions.add("<message>");
					break;
			}

			return StringUtil.copyPartialMatches(args[3], completions, new ArrayList<>());
		}

		return null;
	}
	
	public boolean hasPermission(CommandSender sender, String node) {
		return sender.hasPermission("chatmanager.autobroadcast." + node) || sender.hasPermission("chatmanager.commands.all") ||sender.hasPermission("chatmanager.*");
	}
}