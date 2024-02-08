package me.h1dd3nxn1nja.chatmanager.paper.commands;

import com.ryderbelserion.chatmanager.paper.enums.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import com.ryderbelserion.chatmanager.paper.enums.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.UUID;

public class CommandAntiSwear implements CommandExecutor {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (!(sender instanceof Player player)) {
			this.plugin.getMethods().sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		UUID uuid = player.getUniqueId();

		if (cmd.getName().equalsIgnoreCase("antiswear")) {
			if (player.hasPermission(Permissions.COMMAND_ANTISWEAR_HELP.getNode())) {
				if (args.length == 0) {
					player.sendMessage("");
					player.sendMessage(this.plugin.getMethods().color(" &3Anti Swear Help Menu &f(v" + plugin.getDescription().getVersion() + ")"));
					player.sendMessage("");
					player.sendMessage(this.plugin.getMethods().color(" &6<> &f= Required Arguments"));
					player.sendMessage("");
					player.sendMessage(this.plugin.getMethods().color(" &f/antiswear Help &e- Shows a list of commands for Anti Swear."));
					player.sendMessage(this.plugin.getMethods().color(" &f/antiswear add &6<blacklist|whitelist> <word> &e- Add a blacklisted or whitelisted swear word."));
					player.sendMessage(this.plugin.getMethods().color(" &f/antiswear remove &6<blacklist|whitelist> <word> &e- Remove a blacklisted or whitelisted swear word."));
					player.sendMessage(this.plugin.getMethods().color(" &f/antiswear List &e- Shows your list of blacklisted swear words."));
					player.sendMessage("");
				}
			} else {
				player.sendMessage(this.plugin.getMethods().noPermission());
				return true;
			}

			FileConfiguration messages = Files.MESSAGES.getFile();

			if (args[0].equalsIgnoreCase("help")) {
				if (player.hasPermission(Permissions.COMMAND_ANTISWEAR_HELP.getNode())) {
					if (args.length == 1) {
						player.sendMessage("");
						player.sendMessage(this.plugin.getMethods().color(" &3Anti Swear Help Menu &f(v" + plugin.getDescription().getVersion() + ")"));
						player.sendMessage("");
						player.sendMessage(this.plugin.getMethods().color(" &6<> &f= Required Arguments"));
						player.sendMessage("");
						player.sendMessage(this.plugin.getMethods().color(" &f/antiswear Help &e- Shows a list of commands for Anti Swear."));
						player.sendMessage(this.plugin.getMethods().color(" &f/antiswear add &6<blacklist|whitelist> <word> &e- Add a blacklisted or whitelisted swear word."));
						player.sendMessage(this.plugin.getMethods().color(" &f/antiswear remove &6<blacklist|whitelist> <word> &e- Remove a blacklisted or whitelisted swear word."));
						player.sendMessage(this.plugin.getMethods().color(" &f/antiswear List &e- Shows your list of blacklisted swear words."));
						player.sendMessage("");
					}
				} else {
					player.sendMessage(this.plugin.getMethods().noPermission());
					return true;
				}
			}

			if (args[0].equalsIgnoreCase("add")) {
				if (!player.hasPermission(Permissions.COMMAND_ANTISWEAR_ADD.getNode())) {
					player.sendMessage(this.plugin.getMethods().noPermission());
					return true;
				}

				if (args.length == 1) {
					player.sendMessage(this.plugin.getMethods().color("&cCommand Usage: &7/antiswear add <blacklist|whitelist> <word>"));
					return true;
				}

				FileConfiguration bannedWords = Files.BANNED_WORDS.getFile();

				if (args[1].equalsIgnoreCase("blacklist")) {
					if (player.hasPermission(Permissions.COMMAND_ANTISWEAR_ADD.getNode())) {
						if (args.length == 3) {
							if (!Files.BANNED_WORDS.getFile().getStringList("Banned-Words").contains(args[2])) {
								List<String> swearWords = bannedWords.getStringList("Banned-Words");
								swearWords.add(args[2].toLowerCase());
								bannedWords.set("Banned-Words", swearWords);
								Files.BANNED_WORDS.saveFile();
								Files.BANNED_WORDS.reloadFile();
								player.sendMessage(this.plugin.getMethods().color(uuid, messages.getString("Anti_Swear.Blacklisted_Word.Added").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
							} else {
								player.sendMessage(this.plugin.getMethods().color(uuid, messages.getString("Anti_Swear.Blacklisted_Word.Exists").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
							}
						} else {
							player.sendMessage(this.plugin.getMethods().color("&cCommand Usage: &7/antiswear add blacklist <word>"));
						}
					} else {
						player.sendMessage(this.plugin.getMethods().noPermission());
					}
				}

				if (args[1].equalsIgnoreCase("whitelist")) {
					if (player.hasPermission(Permissions.COMMAND_ANTISWEAR_ADD.getNode())) {
						if (args.length == 3) {
							if (!bannedWords.getStringList("Whitelisted_Words").contains(args[2])) {
								List<String> swearWords = bannedWords.getStringList("Whitelisted_Words");
								swearWords.add(args[2].toLowerCase());
								bannedWords.set("Whitelisted_Words", swearWords);
								Files.BANNED_WORDS.saveFile();
								Files.BANNED_WORDS.reloadFile();
								player.sendMessage(this.plugin.getMethods().color(uuid, messages.getString("Anti_Swear.Whitelisted_Word.Added").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
							} else {
								player.sendMessage(this.plugin.getMethods().color(uuid, messages.getString("Anti_Swear.Whitelisted_Word.Exists").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
							}
						} else {
							player.sendMessage(this.plugin.getMethods().color("&cCommand Usage: &7/antiswear add whitelist <word>"));
						}
					} else {
						player.sendMessage(this.plugin.getMethods().noPermission());
					}
				}
			}

			if (args[0].equalsIgnoreCase("remove")) {
				if (!player.hasPermission(Permissions.COMMAND_ANTISWEAR_REMOVE.getNode())) {
					player.sendMessage(this.plugin.getMethods().noPermission());
					return true;
				}

				if (args.length == 1) {
					player.sendMessage(this.plugin.getMethods().color("&cCommand Usage: &7/antiswear remove <blacklist|whitelist> <word>"));
					return true;
				}

				FileConfiguration bannedWords = Files.BANNED_WORDS.getFile();

				if (args[1].equalsIgnoreCase("blacklist")) {
					if (player.hasPermission(Permissions.COMMAND_ANTISWEAR_REMOVE.getNode())) {
						if (args.length == 3) {
							if (bannedWords.getStringList("Banned-Words").contains(args[2])) {
								List<String> list = bannedWords.getStringList("Banned-Words");
								list.remove(args[2].toLowerCase());
								bannedWords.set("Banned-Words", list);
								Files.BANNED_WORDS.saveFile();
								Files.BANNED_WORDS.reloadFile();
								player.sendMessage(this.plugin.getMethods().color(uuid, messages.getString("Anti_Swear.Blacklisted_Word.Removed").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
							} else {
								player.sendMessage(this.plugin.getMethods().color(uuid, messages.getString("Anti_Swear.Blacklisted_Word.Not_Found").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
							}
						} else {
							player.sendMessage(this.plugin.getMethods().color("&cCommand Usage: &7/antiswear remove blacklist <word>"));
						}
					} else {
						player.sendMessage(this.plugin.getMethods().noPermission());
					}
				}

				if (args[1].equalsIgnoreCase("whitelist")) {
					if (player.hasPermission(Permissions.COMMAND_ANTISWEAR_REMOVE.getNode())) {
						if (args.length == 3) {
							if (bannedWords.getStringList("Whitelisted_Words").contains(args[2])) {
								List<String> list = bannedWords.getStringList("Whitelisted_Words");
								list.remove(args[2].toLowerCase());
								bannedWords.set("Whitelisted_Words", list);
								Files.BANNED_WORDS.saveFile();
								Files.BANNED_WORDS.reloadFile();
								player.sendMessage(this.plugin.getMethods().color(uuid, messages.getString("Anti_Swear.Whitelisted_Word.Removed").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
							} else {
								player.sendMessage(this.plugin.getMethods().color(uuid, messages.getString("Anti_Swear.Whitelisted_Word.Not_Found").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
							}
						} else {
							player.sendMessage(this.plugin.getMethods().color("&cCommand Usage: &7/antiswear remove whitelist <word>"));
						}
					} else {
						player.sendMessage(this.plugin.getMethods().noPermission());
					}
				}
			}

			FileConfiguration bannedWords = Files.BANNED_WORDS.getFile();

			if (args[0].equalsIgnoreCase("list")) {
				if (player.hasPermission(Permissions.COMMAND_ANTISWEAR_LIST.getNode())) {
					if (args.length == 1) {
						String list = bannedWords.getStringList("Banned-Words").toString().replace("[", "").replace("]", "");
						String list2 = bannedWords.getStringList("Whitelisted_Words").toString().replace("[", "").replace("]", "");
						player.sendMessage("");
						player.sendMessage(this.plugin.getMethods().color("&cSwear Words: &7" + list));
						player.sendMessage(" ");
						player.sendMessage(this.plugin.getMethods().color("&cWhitelisted Words: &7" + list2));
						return true;
					} else {
						player.sendMessage(this.plugin.getMethods().color("&cCommand Usage: &7/antiswear list"));
					}
				} else {
					player.sendMessage(this.plugin.getMethods().noPermission());
				}
			}
		}

		return true;
	}
}