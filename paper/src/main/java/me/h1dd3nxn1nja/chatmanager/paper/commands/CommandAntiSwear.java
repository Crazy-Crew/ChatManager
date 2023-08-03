package me.h1dd3nxn1nja.chatmanager.paper.commands;

import com.ryderbelserion.chatmanager.paper.files.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.UUID;

public class CommandAntiSwear implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (!(sender instanceof Player player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		UUID uuid = player.getUniqueId();

		if (cmd.getName().equalsIgnoreCase("AntiSwear")) {
			if (player.hasPermission("chatmanager.antiswear")) {
				if (args.length == 0) {
					player.sendMessage("");
					player.sendMessage(Methods.color(" &3Anti Swear Help Menu &f(v" + plugin.getDescription().getVersion() + ")"));
					player.sendMessage("");
					player.sendMessage(Methods.color(" &6<> &f= Required Arguments"));
					player.sendMessage("");
					player.sendMessage(Methods.color(" &f/AntiSwear Help &e- Shows a list of commands for Anti Swear."));
					player.sendMessage(Methods.color(" &f/AntiSwear add &6<blacklist|whitelist> <word> &e- Add a blacklisted or whitelisted swear word."));
					player.sendMessage(Methods.color(" &f/AntiSwear remove &6<blacklist|whitelist> <word> &e- Remove a blacklisted or whitelisted swear word."));
					player.sendMessage(Methods.color(" &f/AntiSwear List &e- Shows your list of blacklisted swear words."));
					player.sendMessage("");
				}
			} else {
				player.sendMessage(Methods.noPermission());
				return true;
			}

			FileConfiguration messages = Files.MESSAGES.getFile();

			if (args[0].equalsIgnoreCase("help")) {
				if (player.hasPermission("chatmanager.antiswear.help")) {
					if (args.length == 1) {
						player.sendMessage("");
						player.sendMessage(Methods.color(" &3Anti Swear Help Menu &f(v" + plugin.getDescription().getVersion() + ")"));
						player.sendMessage("");
						player.sendMessage(Methods.color(" &6<> &f= Required Arguments"));
						player.sendMessage("");
						player.sendMessage(Methods.color(" &f/AntiSwear Help &e- Shows a list of commands for Anti Swear."));
						player.sendMessage(Methods.color(" &f/AntiSwear add &6<blacklist|whitelist> <word> &e- Add a blacklisted or whitelisted swear word."));
						player.sendMessage(Methods.color(" &f/AntiSwear remove &6<blacklist|whitelist> <word> &e- Remove a blacklisted or whitelisted swear word."));
						player.sendMessage(Methods.color(" &f/AntiSwear List &e- Shows your list of blacklisted swear words."));
						player.sendMessage("");
					}
				} else {
					player.sendMessage(Methods.noPermission());
					return true;
				}
			}

			if (args[0].equalsIgnoreCase("add")) {
				if (!player.hasPermission("chatmanager.antiswear.add")) {
					player.sendMessage(Methods.noPermission());
					return true;
				}

				if (args.length == 1) {
					player.sendMessage(Methods.color("&cCommand Usage: &7/Antiswear add <blacklist|whitelist> <word>"));
					return true;
				}

				FileConfiguration bannedWords = Files.BANNED_WORDS.getFile();

				if (args[1].equalsIgnoreCase("blacklist")) {
					if (player.hasPermission("chatmanager.antiswear.add")) {
						if (args.length == 3) {
							if (!Files.BANNED_WORDS.getFile().getStringList("Banned-Words").contains(args[2])) {
								List<String> swearWords = bannedWords.getStringList("Banned-Words");
								swearWords.add(args[2].toLowerCase());
								bannedWords.set("Banned-Words", swearWords);
								Files.BANNED_WORDS.saveFile();
								Files.BANNED_WORDS.reloadFile();
								player.sendMessage(Methods.color(uuid, messages.getString("Anti_Swear.Blacklisted_Word.Added").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
							} else {
								player.sendMessage(Methods.color(uuid, messages.getString("Anti_Swear.Blacklisted_Word.Exists").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
							}
						} else {
							player.sendMessage(Methods.color("&cCommand Usage: &7/Antiswear add blacklist <word>"));
						}
					} else {
						player.sendMessage(Methods.noPermission());
					}
				}

				if (args[1].equalsIgnoreCase("whitelist")) {
					if (player.hasPermission("chatmanager.antiswear.add")) {
						if (args.length == 3) {
							if (!bannedWords.getStringList("Whitelisted_Words").contains(args[2])) {
								List<String> swearWords = bannedWords.getStringList("Whitelisted_Words");
								swearWords.add(args[2].toLowerCase());
								bannedWords.set("Whitelisted_Words", swearWords);
								Files.BANNED_WORDS.saveFile();
								Files.BANNED_WORDS.reloadFile();
								player.sendMessage(Methods.color(uuid, messages.getString("Anti_Swear.Whitelisted_Word.Added").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
							} else {
								player.sendMessage(Methods.color(uuid, messages.getString("Anti_Swear.Whitelisted_Word.Exists").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
							}
						} else {
							player.sendMessage(Methods.color("&cCommand Usage: &7/Antiswear add whitelist <word>"));
						}
					} else {
						player.sendMessage(Methods.noPermission());
					}
				}
			}

			if (args[0].equalsIgnoreCase("remove")) {
				if (!player.hasPermission("chatmanager.antiswear.remove")) {
					player.sendMessage(Methods.noPermission());
					return true;
				}

				if (args.length == 1) {
					player.sendMessage(Methods.color("&cCommand Usage: &7/Antiswear remove <blacklist|whitelist> <word>"));
					return true;
				}

				FileConfiguration bannedWords = Files.BANNED_WORDS.getFile();

				if (args[1].equalsIgnoreCase("blacklist")) {
					if (player.hasPermission("chatmanager.antiswear.remove")) {
						if (args.length == 3) {
							if (bannedWords.getStringList("Banned-Words").contains(args[2])) {
								List<String> list = bannedWords.getStringList("Banned-Words");
								list.remove(args[2].toLowerCase());
								bannedWords.set("Banned-Words", list);
								Files.BANNED_WORDS.saveFile();
								Files.BANNED_WORDS.reloadFile();
								player.sendMessage(Methods.color(uuid, messages.getString("Anti_Swear.Blacklisted_Word.Removed").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
							} else {
								player.sendMessage(Methods.color(uuid, messages.getString("Anti_Swear.Blacklisted_Word.Not_Found").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
							}
						} else {
							player.sendMessage(Methods.color("&cCommand Usage: &7/Antiswear remove blacklist <word>"));
						}
					} else {
						player.sendMessage(Methods.noPermission());
					}
				}

				if (args[1].equalsIgnoreCase("whitelist")) {
					if (player.hasPermission("chatmanager.antiswear.remove")) {
						if (args.length == 3) {
							if (bannedWords.getStringList("Whitelisted_Words").contains(args[2])) {
								List<String> list = bannedWords.getStringList("Whitelisted_Words");
								list.remove(args[2].toLowerCase());
								bannedWords.set("Whitelisted_Words", list);
								Files.BANNED_WORDS.saveFile();
								Files.BANNED_WORDS.reloadFile();
								player.sendMessage(Methods.color(uuid, messages.getString("Anti_Swear.Whitelisted_Word.Removed").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
							} else {
								player.sendMessage(Methods.color(uuid, messages.getString("Anti_Swear.Whitelisted_Word.Not_Found").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
							}
						} else {
							player.sendMessage(Methods.color("&cCommand Usage: &7/Antiswear remove whitelist <word>"));
						}
					} else {
						player.sendMessage(Methods.noPermission());
					}
				}
			}

			FileConfiguration bannedWords = Files.BANNED_WORDS.getFile();

			if (args[0].equalsIgnoreCase("list")) {
				if (player.hasPermission("chatmanager.antiswear.list")) {
					if (args.length == 1) {
						String list = bannedWords.getStringList("Banned-Words").toString().replace("[", "").replace("]", "");
						String list2 = bannedWords.getStringList("Whitelisted_Words").toString().replace("[", "").replace("]", "");
						player.sendMessage("");
						player.sendMessage(Methods.color("&cSwear Words: &7" + list));
						player.sendMessage(" ");
						player.sendMessage(Methods.color("&cWhitelisted Words: &7" + list2));
						return true;
					} else {
						player.sendMessage(Methods.color("&cCommand Usage: &7/Antiswear list"));
					}
				} else {
					player.sendMessage(Methods.noPermission());
				}
			}
		}

		return true;
	}
}