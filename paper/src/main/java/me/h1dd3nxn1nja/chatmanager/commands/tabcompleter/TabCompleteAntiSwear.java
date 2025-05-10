package me.h1dd3nxn1nja.chatmanager.commands.tabcompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TabCompleteAntiSwear implements TabCompleter {
	
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String commandLabel, String[] args) {
		List<String> completions = new ArrayList<>();
		
		if (args.length == 1) {
			completions.add("help");
			if (hasPermission(sender, "reload")) completions.add("reload");
			if (hasPermission(sender, "add")) completions.add("add");
			if (hasPermission(sender, "remove")) completions.add("remove");
			if (hasPermission(sender, "list")) completions.add("list"); return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
		} else if (args.length == 2) {
			switch (args[0].toLowerCase()) {
				case "help", "reload", "list", "add", "remove" -> {
					completions.add("blacklist");
					completions.add("whitelist");
				}
			}
			
			return StringUtil.copyPartialMatches(args[1], completions, new ArrayList<>());
		}

		return null;
	}
	
	public boolean hasPermission(CommandSender sender, String node) {
		return sender.hasPermission("chatmanager.antiswear." + node) || sender.hasPermission("chatmanager.commands.all") ||sender.hasPermission("chatmanager.*");
	}
}