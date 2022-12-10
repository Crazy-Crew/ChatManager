package me.h1dd3nxn1nja.chatmanager.tabcompleter;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.hooks.HookManager;
import me.h1dd3nxn1nja.chatmanager.hooks.SuperVanishHook;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabCompleteMessage implements TabCompleter {

	public ChatManager plugin;

	public TabCompleteMessage(ChatManager plugin) {
		this.plugin = plugin;
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

		if (args.length <= 0) {
			return null;
		}

		List<Player> matchPlayer = Bukkit.matchPlayer(args[args.length - 1]);
		ArrayList<String> list = new ArrayList<String>();
		if (!(sender instanceof Player)) {
			matchPlayer.forEach(player -> list.add(player.getName()));
			return list;
		}

		Player player2 = (Player) sender;
		boolean hasPermission = player2.hasPermission("chatmanager.bypass.ignored");
		boolean hasPermission2 = player2.hasPermission("chatmanager.bypass.vanish");
		boolean hasPermission3 = player2.hasPermission("chatmanager.bypass.togglepm");
		for (Player player3 : matchPlayer) {
			if (!hasPermission2) {
				if (HookManager.isSuperVanishLoaded() && SuperVanishHook.isVanished(player3)) {
					continue;
				}
				if (HookManager.isPremiumVanishLoaded() && SuperVanishHook.isVanished(player3)) {
					continue;
				}
			}
			if (!hasPermission3 && Methods.cm_togglePM.contains(player3.getUniqueId())) {
				continue;
			}
			list.add(player3.getName());
		}
		return list;
	}
}