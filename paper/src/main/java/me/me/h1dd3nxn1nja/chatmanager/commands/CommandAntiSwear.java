package me.me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.utils.JSONMessage;
import me.h1dd3nxn1nja.chatmanager.utils.Version;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandAntiSwear implements CommandExecutor {
	
	public ChatManager plugin;
	
	public CommandAntiSwear(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		FileConfiguration bannedWords = ChatManager.settings.getBannedWords();
		FileConfiguration messages = ChatManager.settings.getMessages();
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("Error: You can only use that command in game");
			return true;
		}
		
		Player player = (Player) sender;
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("AntiSwear")) {
				if (player.hasPermission("chatmanager.antiswear")) {
					if (args.length == 0) {
						if (Version.getCurrentVersion().isNewer(Version.v1_8_R2) && Version.getCurrentVersion().isOlder(Version.v1_17_R1)) {
							JSONMessage.create("").send(player);
							JSONMessage.create(" &3Anti Swear Help Menu &f(v" + plugin.getDescription().getVersion() + ")").send(player);
							JSONMessage.create("").send(player);
							JSONMessage.create(" &6<> &f= Required Arguments").send(player);
							JSONMessage.create("").send(player);
							JSONMessage.create(" &f/AntiSwear Help &e- Shows a list of commands for Anti Swear.").tooltip(Methods.color("&f/AntiSwear Help \n&7Shows a list of commands for Anti Swear.")).suggestCommand("/antiswear help").send(player);
							JSONMessage.create(" &f/AntiSwear add &6<blacklist|whitelist> <word> &e- Add a blacklisted or whitelisted swear word.").tooltip(Methods.color("&f/AntiSwear add &6<blacklist|whitelist> <word> \n&7Add a blacklisted or whitelisted swear word.")).suggestCommand("/antiswear add <blacklist|whitelist> <word>").send(player);
							JSONMessage.create(" &f/AntiSwear remove &6<blacklist|whitelist> <word> &e- Remove a blacklisted or whitelisted swear word.").tooltip(Methods.color("&f/AntiSwear remove &6<blacklist|whitelist> <word> \n&7Remove a blacklisted or whitelisted swear word.")).suggestCommand("/antiswear remove <blacklist|whitelist> <word>").send(player);
							JSONMessage.create(" &f/AntiSwear List &e- Shows your list of blacklisted and whitelisted swear words.").tooltip(Methods.color("&f/AntiSwear List \n&7Shows your list of blacklisted swear words.")).suggestCommand("/antiswear list").send(player);
							JSONMessage.create("").send(player);
							JSONMessage.create(" &e&lTIP &7Try to hover or click the command!").tooltip(Methods.color("&7Hover over the commands to get information about them. \n&7Click on the commands to insert them in the chat.")).send(player);
							JSONMessage.create("").send(player);
							return true;
						} else {
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
							return true;
						}
					}
				} else {
					player.sendMessage(Methods.noPermission());
					return true;
				}
				if (args[0].equalsIgnoreCase("help")) {
					if (player.hasPermission("chatmanager.antiswear.help")) {
						if (args.length == 1) {
							if (Version.getCurrentVersion().isNewer(Version.v1_8_R2) && Version.getCurrentVersion().isOlder(Version.v1_17_R1)) {
								JSONMessage.create("").send(player);
								JSONMessage.create(Methods.color(" &3Anti Swear Help Menu &f(v" + plugin.getDescription().getVersion() + ")")).send(player);
								JSONMessage.create("").send(player);
								JSONMessage.create(Methods.color(" &6<> &f= Required Arguments")).send(player);
								JSONMessage.create("").send(player);
								JSONMessage.create(Methods.color(" &f/AntiSwear Help &e- Shows a list of commands for Anti Swear.")).tooltip(Methods.color("&f/AntiSwear Help \n&7Shows a list of commands for Anti Swear.")).suggestCommand("/antiswear help").send(player);
								JSONMessage.create(Methods.color(" &f/AntiSwear add &6<blacklist|whitelist> <word> &e- Add a blacklisted or whitelisted swear word.")).tooltip(Methods.color("&f/AntiSwear add &6<blacklist|whitelist> <word> \n&7Add a blacklisted or whitelisted swear word.")).suggestCommand("/antiswear add <blacklist|whitelist> <word>").send(player);
								JSONMessage.create(Methods.color(" &f/AntiSwear remove &6<blacklist|whitelist> <word> &e- Remove a blacklisted or whitelisted swear word.")).tooltip(Methods.color("&f/AntiSwear remove &6<blacklist|whitelist> <word> \n&7Remove a blacklisted or whitelisted swear word.")).suggestCommand("/antiswear remove <blacklist|whitelist> <word>").send(player);
								JSONMessage.create(Methods.color(" &f/AntiSwear List &e- Shows your list of blacklisted swear words.")).tooltip(Methods.color("&f/AntiSwear List \n&7Shows your list of blacklisted swear words.")).suggestCommand("/antiswear list").send(player);
								JSONMessage.create("").send(player);
								JSONMessage.create(Methods.color(" &e&lTIP &7Try to hover or click the command!")).tooltip(Methods.color("&7Hover over the commands to get information about them. \n&7Click on the commands to insert them in the chat.")).send(player);
								JSONMessage.create("").send(player);
								return true;
							} else {
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
								return true;
							}	
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

					if (args[1].equalsIgnoreCase("blacklist")) {
						if (player.hasPermission("chatmanager.antiswear.add")) {
							if (args.length == 3) {
								if (!bannedWords.getStringList("Banned-Words").contains(args[2])) {
									List<String> swearWords = bannedWords.getStringList("Banned-Words");
									swearWords.add(args[2].toLowerCase());
									bannedWords.set("Banned-Words", swearWords);
									ChatManager.settings.saveBannedWords();
									ChatManager.settings.reloadBannedWords();
									player.sendMessage(Methods.color(player, messages.getString("Anti_Swear.Blacklisted_Word.Added").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
								} else {
									player.sendMessage(Methods.color(player, messages.getString("Anti_Swear.Blacklisted_Word.Exists").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
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
									ChatManager.settings.saveBannedWords();
									ChatManager.settings.reloadBannedWords();
									player.sendMessage(Methods.color(player, messages.getString("Anti_Swear.Whitelisted_Word.Added").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
								} else {
									player.sendMessage(Methods.color(player, messages.getString("Anti_Swear.Whitelisted_Word.Exists").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
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
					
					if (args[1].equalsIgnoreCase("blacklist")) {
						if (player.hasPermission("chatmanager.antiswear.remove")) {
							if (args.length == 3) {
								if (bannedWords.getStringList("Banned-Words").contains(args[2])) {
									List<String> list = bannedWords.getStringList("Banned-Words");
									list.remove(args[2].toLowerCase());
									bannedWords.set("Banned-Words", list);
									ChatManager.settings.saveBannedWords();
									ChatManager.settings.reloadBannedWords();
									player.sendMessage(Methods.color(player, messages.getString("Anti_Swear.Blacklisted_Word.Removed").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
								} else {
									player.sendMessage(Methods.color(player, messages.getString("Anti_Swear.Blacklisted_Word.Not_Found").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
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
									ChatManager.settings.saveBannedWords();
									ChatManager.settings.reloadBannedWords();
									player.sendMessage(Methods.color(player, messages.getString("Anti_Swear.Whitelisted_Word.Removed").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
								} else {
									player.sendMessage(Methods.color(player, messages.getString("Anti_Swear.Whitelisted_Word.Not_Found").replace("{word}", args[2]).replace("{Prefix}", messages.getString("Message.Prefix"))));
								}
							} else {
								player.sendMessage(Methods.color("&cCommand Usage: &7/Antiswear remove whitelist <word>"));
							}
						} else {
							player.sendMessage(Methods.noPermission());
						}
					}
				}
				
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
		}
		return true;
	}
}