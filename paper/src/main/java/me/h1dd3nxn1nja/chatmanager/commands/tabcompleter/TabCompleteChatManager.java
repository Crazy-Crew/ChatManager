package me.h1dd3nxn1nja.chatmanager.commands.tabcompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TabCompleteChatManager implements TabCompleter {
	
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String commandLabel, String[] args) {
		List<String> completions = new ArrayList<>();
		
		if (args.length == 1) {
			completions.add("help");

			if (hasPermission(sender, "reload")) completions.add("reload");
			if (hasPermission(sender, "debug")) completions.add("debug");
			if (hasPermission(sender, "preview")) completions.add("preview"); return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
		} else if (args.length == 2) {
			switch (args[0].toLowerCase()) {
				case "help", "reload", "debug" -> {
					completions.add("all");
					completions.add("autobroadcast");
					completions.add("chatbot");
					completions.add("config");
					completions.add("messages");
					completions.add("rules");
				}
				case "preview" -> {
					completions.add("title");
					completions.add("actionbar");
				}
			}

			return StringUtil.copyPartialMatches(args[1], completions, new ArrayList<>());
		} else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("preview")) {
                completions.add("join");
                completions.add("firstjoin");
            }

			return StringUtil.copyPartialMatches(args[2], completions, new ArrayList<>());
		}

		return new ArrayList<>();
	}

	public boolean hasPermission(CommandSender sender, String node) {
		return sender.hasPermission("chatmanager." + node) || sender.hasPermission("chatmanager.commands.all") ||sender.hasPermission("chatmanager.*");
	}
}