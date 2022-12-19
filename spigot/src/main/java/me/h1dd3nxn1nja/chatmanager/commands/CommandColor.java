package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandColor implements CommandExecutor {

	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

		if (!(sender instanceof Player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("Colors")) {
			if (player.hasPermission("chatmanager.colors")) {
				if (args.length == 0) {
					player.sendMessage(ChatColor.RED + "=========================");
					player.sendMessage(Methods.color("&7Color Codes"));
					player.sendMessage(ChatColor.RED + "=========================");
					player.sendMessage(ChatColor.WHITE + "  &0 = " + ChatColor.BLACK + "Black");
					player.sendMessage(ChatColor.WHITE + "  &1 = " + ChatColor.DARK_BLUE + "Dark Blue");
					player.sendMessage(ChatColor.WHITE + "  &2 = " + ChatColor.DARK_GREEN + "Dark Green");
					player.sendMessage(ChatColor.WHITE + "  &3 = " + ChatColor.DARK_AQUA + "Dark Aqua");
					player.sendMessage(ChatColor.WHITE + "  &4 = " + ChatColor.DARK_RED + "Dark Red");
					player.sendMessage(ChatColor.WHITE + "  &5 = " + ChatColor.DARK_PURPLE + "Dark Purple");
					player.sendMessage(ChatColor.WHITE + "  &6 = " + ChatColor.GOLD + "Gold");
					player.sendMessage(ChatColor.WHITE + "  &7 = " + ChatColor.GRAY + "Gray");
					player.sendMessage(ChatColor.WHITE + "  &8 = " + ChatColor.DARK_GRAY + "Dark Gray");
					player.sendMessage(ChatColor.WHITE + "  &9 = " + ChatColor.BLUE + "Blue");
					player.sendMessage(ChatColor.WHITE + "  &a = " + ChatColor.GREEN + "Green");
					player.sendMessage(ChatColor.WHITE + "  &b = " + ChatColor.AQUA + "Aqua");
					player.sendMessage(ChatColor.WHITE + "  &c = " + ChatColor.RED + "Red");
					player.sendMessage(ChatColor.WHITE + "  &d = " + ChatColor.LIGHT_PURPLE + "Light Purple");
					player.sendMessage(ChatColor.WHITE + "  &e = " + ChatColor.YELLOW + "Yellow");
					player.sendMessage(ChatColor.WHITE + "  &f = " + ChatColor.WHITE + "White");
					player.sendMessage("");
					player.sendMessage(ChatColor.GRAY + "Type '/Formats' to view the Chat Formats.");
					player.sendMessage(ChatColor.RED + "=========================");
					return true;
				} else {
					Methods.sendMessage(player, "&cCommand Usage: &7/Colors", true);
				}
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
			}
		}

		if (cmd.getName().equalsIgnoreCase("Formats")) {
			if (player.hasPermission("chatmanager.formats")) {
				if (args.length == 0) {
					player.sendMessage(ChatColor.RED + "=========================");
					player.sendMessage(Methods.color("&7Format Codes"));
					player.sendMessage(ChatColor.RED + "=========================");
					player.sendMessage(ChatColor.WHITE + "  &k = " + ChatColor.MAGIC + "Magic");
					player.sendMessage(ChatColor.WHITE + "  &l = " + ChatColor.BOLD + "Bold");
					player.sendMessage(ChatColor.WHITE + "  &m = " + ChatColor.STRIKETHROUGH + "Strikethrough");
					player.sendMessage(ChatColor.WHITE + "  &n = " + ChatColor.UNDERLINE + "Underline");
					player.sendMessage(ChatColor.WHITE + "  &o = " + ChatColor.ITALIC + "Italic");
					player.sendMessage(ChatColor.WHITE + "  &r = " + ChatColor.RESET + "Reset");
					player.sendMessage("");
					player.sendMessage(ChatColor.GRAY + "Type '/Colors' to view the Chat Colors.");
					player.sendMessage(ChatColor.RED + "=========================");
					return true;
				} else {
					Methods.sendMessage(player, "&cCommand Usage: &7/Formats", true);
				}
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
			}
		}

		return true;
	}
}