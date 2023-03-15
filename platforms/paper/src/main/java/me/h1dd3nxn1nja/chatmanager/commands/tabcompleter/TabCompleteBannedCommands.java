package me.h1dd3nxn1nja.chatmanager.commands.tabcompleter;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class TabCompleteBannedCommands implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String commandLabel, String[] args) {
		List<String> completions = new ArrayList<>();

		if (args.length == 1) {
			if (hasPermission(sender, "help")) completions.add("help");
			if (hasPermission(sender, "add")) completions.add("add");
			if (hasPermission(sender, "remove")) completions.add("remove");
			if (hasPermission(sender, "list")) completions.add("list");return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
		} else if (args.length == 2) {
			switch (args[0].toLowerCase()) {
				case "help":
				case "add":
				case "remove":
				case "list":
			}

			return StringUtil.copyPartialMatches(args[1], completions, new ArrayList<>());
		}

		return null;
	}

	public boolean hasPermission(CommandSender sender, String node) {
		return sender.hasPermission("chatmanager.bannedcommands." + node) || sender.hasPermission("chatmanager.commands.all") ||sender.hasPermission("chatmanager.*");
	}
}