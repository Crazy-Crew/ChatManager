package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandAntiSwear extends Global implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, final String @NotNull [] args) {
		if (!(sender instanceof Player player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);

			return true;
		}

		if (cmd.getName().equalsIgnoreCase("antiswear")) {
			if (player.hasPermission(Permissions.COMMAND_ANTISWEAR_HELP.getNode())) {
				if (args.length == 0) {
					List.of(
							" &3Anti Swear Help Menu &f(v" + plugin.getDescription().getVersion() + ")",
							"",
							" &6<> &f= Required Arguments",
							"",
							" &f/antiswear Help &e- Shows a list of commands for Anti Swear.",
							" &f/antiswear add &6<blacklist|whitelist> <word> &e- Add a blacklisted or whitelisted swear word.",
							" &f/antiswear remove &6<blacklist|whitelist> <word> &e- Remove a blacklisted or whitelisted swear word.",
							" &f/antiswear List &e- Shows your list of blacklisted swear words.",
							""
					).forEach(msg -> Methods.sendMessage(player, msg, false));
				}
			} else {
				Messages.NO_PERMISSION.sendMessage(player);

				return true;
			}

			if (args[0].equalsIgnoreCase("help")) {
				if (player.hasPermission(Permissions.COMMAND_ANTISWEAR_HELP.getNode())) {
					if (args.length == 1) {
						List.of(
								" &3Anti Swear Help Menu &f(v" + plugin.getDescription().getVersion() + ")",
								"",
								" &6<> &f= Required Arguments",
								"",
								" &f/antiswear Help &e- Shows a list of commands for Anti Swear.",
								" &f/antiswear add &6<blacklist|whitelist> <word> &e- Add a blacklisted or whitelisted swear word.",
								" &f/antiswear remove &6<blacklist|whitelist> <word> &e- Remove a blacklisted or whitelisted swear word.",
								" &f/antiswear List &e- Shows your list of blacklisted swear words.",
								""
						).forEach(msg -> Methods.sendMessage(player, msg, false));
					}
				} else {
					Messages.NO_PERMISSION.sendMessage(player);

					return true;
				}
			}

			if (args[0].equalsIgnoreCase("add")) {
				if (!player.hasPermission(Permissions.COMMAND_ANTISWEAR_ADD.getNode())) {
					Messages.NO_PERMISSION.sendMessage(player);

					return true;
				}

				if (args.length == 1) {
					Messages.INVALID_USAGE.sendMessage(player, "{usage}", "/antiswear add <blacklist|whitelist> <word>");

					return true;
				}

				FileConfiguration bannedWords = Files.BANNED_WORDS.getConfiguration();

				if (args[1].equalsIgnoreCase("blacklist")) {
					if (player.hasPermission(Permissions.COMMAND_ANTISWEAR_ADD.getNode())) {
						if (args.length == 3) {
							if (!bannedWords.getStringList("Banned-Words").contains(args[2])) {
								List<String> swearWords = bannedWords.getStringList("Banned-Words");
								swearWords.add(args[2].toLowerCase());

								bannedWords.set("Banned-Words", swearWords);

								Files.BANNED_WORDS.save();

								Messages.ANTI_SWEAR_BLACKLISTED_WORD_ADDED.sendMessage(player, "{word}", args[2]);
							} else {
								Messages.ANTI_SWEAR_BLACKLISTED_WORD_EXISTS.sendMessage(player, "{word}", args[2]);
							}
						} else {
							Messages.INVALID_USAGE.sendMessage(player, "{usage}", "/antiswear add blacklist <word>");
						}
					} else {
						Messages.NO_PERMISSION.sendMessage(player);
					}
				}

				if (args[1].equalsIgnoreCase("whitelist")) {
					if (player.hasPermission(Permissions.COMMAND_ANTISWEAR_ADD.getNode())) {
						if (args.length == 3) {
							if (!bannedWords.getStringList("Whitelisted_Words").contains(args[2])) {
								List<String> swearWords = bannedWords.getStringList("Whitelisted_Words");
								swearWords.add(args[2].toLowerCase());

								bannedWords.set("Whitelisted_Words", swearWords);

								Files.BANNED_WORDS.save();

								Messages.ANTI_SWEAR_WHITELISTED_WORD_EXISTS.sendMessage(player, "{word}", args[2]);
							} else {
								Messages.ANTI_SWEAR_WHITELISTED_WORD_EXISTS.sendMessage(player, "{word}", args[2]);
							}
						} else {
							Messages.INVALID_USAGE.sendMessage(player, "{usage}", "/antiswear add whitelist <word>");
						}
					} else {
						Messages.NO_PERMISSION.sendMessage(player);
					}
				}
			}

			if (args[0].equalsIgnoreCase("remove")) {
				if (!player.hasPermission(Permissions.COMMAND_ANTISWEAR_REMOVE.getNode())) {
					Messages.NO_PERMISSION.sendMessage(player);

					return true;
				}

				if (args.length == 1) {
					Messages.INVALID_USAGE.sendMessage(player, "{usage}", "/antiswear remove <blacklist|whitelist> <word>");

					return true;
				}

				FileConfiguration bannedWords = Files.BANNED_WORDS.getConfiguration();

				if (args[1].equalsIgnoreCase("blacklist")) {
					if (player.hasPermission(Permissions.COMMAND_ANTISWEAR_REMOVE.getNode())) {
						if (args.length == 3) {
							if (bannedWords.getStringList("Banned-Words").contains(args[2])) {
								List<String> list = bannedWords.getStringList("Banned-Words");
								list.remove(args[2].toLowerCase());

								bannedWords.set("Banned-Words", list);

								Files.BANNED_WORDS.save();

								Messages.ANTI_SWEAR_BLACKLISTED_WORD_REMOVED.sendMessage(player, "{word}", args[2]);
							} else {
								Messages.ANTI_SWEAR_BLACKLISTED_WORD_NOT_FOUND.sendMessage(player, "{word}", args[2]);
							}
						} else {
							Messages.INVALID_USAGE.sendMessage(player, "{usage}", "/antiswear remove blacklist <word>");
						}
					} else {
						Messages.NO_PERMISSION.sendMessage(player);
					}
				}

				if (args[1].equalsIgnoreCase("whitelist")) {
					if (player.hasPermission(Permissions.COMMAND_ANTISWEAR_REMOVE.getNode())) {
						if (args.length == 3) {
							if (bannedWords.getStringList("Whitelisted_Words").contains(args[2])) {
								List<String> list = bannedWords.getStringList("Whitelisted_Words");
								list.remove(args[2].toLowerCase());

								bannedWords.set("Whitelisted_Words", list);

								Files.BANNED_WORDS.save();

								Messages.ANTI_SWEAR_WHITELISTED_WORD_REMOVED.sendMessage(player, "{word}", args[2]);
							} else {
								Messages.ANTI_SWEAR_WHITELISTED_WORD_NOT_FOUND.sendMessage(player, "{word}", args[2]);
							}
						} else {
							Messages.INVALID_USAGE.sendMessage(player, "{usage}", "/antiswear remove whitelist <word>");
						}
					} else {
						Messages.NO_PERMISSION.sendMessage(player);
					}
				}
			}

			FileConfiguration bannedWords = Files.BANNED_WORDS.getConfiguration();

			if (args[0].equalsIgnoreCase("list")) {
				if (player.hasPermission(Permissions.COMMAND_ANTISWEAR_LIST.getNode())) {
					if (args.length == 1) {
						String list = bannedWords.getStringList("Banned-Words").toString().replace("[", "").replace("]", "");
						String list2 = bannedWords.getStringList("Whitelisted_Words").toString().replace("[", "").replace("]", "");

						List.of(
								"",
								"&cSwear Words: &7" + list,
								"",
								"&cWhitelisted Words: &7" + list2
						).forEach(msg -> Methods.sendMessage(player, msg, false));

						return true;
					} else {
						Messages.INVALID_USAGE.sendMessage(player, "{usage}", "/antiswear list");
					}
				} else {
					Messages.NO_PERMISSION.sendMessage(player);
				}
			}
		}

		return true;
	}
}