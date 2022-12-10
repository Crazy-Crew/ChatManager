package me.h1dd3nxn1nja.chatmanager.commands;

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

public class CommandBannedCommands implements CommandExecutor {
	
	public ChatManager plugin;
	
	public CommandBannedCommands(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		FileConfiguration messages = ChatManager.settings.getMessages();
		FileConfiguration bannedCommands = ChatManager.settings.getBannedCommands();
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("Error: You can only use that command in game");
			return true;
		}

		Player player = (Player) sender;
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("BannedCommands")) {
				if (player.hasPermission("chatmanager.bannedcommands")) {
					if (args.length == 0) {
						if (Version.getCurrentVersion().isNewer(Version.v1_8_R2) && Version.getCurrentVersion().isOlder(Version.v1_17_R1)) {
							JSONMessage.create("").send(player);
							JSONMessage.create(" &3Banned Commands Help Menu &f(v" + plugin.getDescription().getVersion() + ")").send(player);
							JSONMessage.create("").send(player);
							JSONMessage.create(" &6<> &f= Required Arguments").send(player);
							JSONMessage.create("").send(player);
							JSONMessage.create(" &f/BannedCommands Help &e- Shows the banned commands help menu.").tooltip(Methods.color("&f/BannedCommands Help \n&7Shows the banned commands help menu.")).suggestCommand("/bannedcommands help").send(player);
							JSONMessage.create(" &f/BannedCommands Add &6<command> &e- Add a command to the \n&ebanned command list.").tooltip(Methods.color("&f/BannedCommands add &6<command> \n&7Add a command to the banned command list.")).suggestCommand("/bannedcommands add <command>").send(player);
							JSONMessage.create(" &f/BannedCommands Remove &6<command> &e- Remove a command from the \n&ebanned command list.").tooltip(Methods.color("&f/BannedCommands remove &6<command> \n&7Remove a command from the banned command list.")).suggestCommand("/bannedcommands remove <command>").send(player);
							JSONMessage.create(" &f/BannedCommands List &e- Shows a list of the servers banned commands.").tooltip(Methods.color("&f/BannedCommands List \n&7Shows a list of the servers banned commands")).suggestCommand("/bannedcommands list").send(player);
							JSONMessage.create("").send(player);
							JSONMessage.create(" &e&lTIP &7Try to hover or click the command!").tooltip(Methods.color("&7Hover over the commands to get information about them. \n&7Click on the commands to insert them in the chat.")).send(player);
							JSONMessage.create("").send(player);
							return true;
						} else {
							player.sendMessage("");
							player.sendMessage(Methods.color(" &3Banned Commands Help Menu &f(v" + plugin.getDescription().getVersion() + ")"));
							player.sendMessage("");
							player.sendMessage(Methods.color(" &6<> &f= Required Arguments"));
							player.sendMessage("");
							player.sendMessage(Methods.color(" &f/BannedCommands Help &e- Shows the banned commands help menu."));
							player.sendMessage(Methods.color(" &f/BannedCommands Add &6<command> &e- Add a command to the banned command list."));
							player.sendMessage(Methods.color(" &f/BannedCommands Remove &6<command> &e- Remove a command from the banned command list."));
							player.sendMessage(Methods.color(" &f/BannedCommands List &e- Shows a list of the servers banned commands."));
							player.sendMessage("");
							return true;
						}
					}
				} else {
					player.sendMessage(Methods.noPermission());
					return true;
				}

				if (args[0].equalsIgnoreCase("help")) {
					if (player.hasPermission("chatmanager.bannedcommands.help")) {
						if (args.length == 1) {
							if (Version.getCurrentVersion().isNewer(Version.v1_8_R2) && Version.getCurrentVersion().isOlder(Version.v1_17_R1)) {
								JSONMessage.create("").send(player);
								JSONMessage.create(Methods.color(" &3Banned Commands Help Menu &f(v" + plugin.getDescription().getVersion() + ")")).send(player);
								JSONMessage.create("").send(player);
								JSONMessage.create(Methods.color(" &6<> &f= Required Arguments")).send(player);
								JSONMessage.create("").send(player);
								JSONMessage.create(Methods.color(" &f/BannedCommands Help &e- Shows the banned commands help menu.")).tooltip(Methods.color("&f/BannedCommands Help \n&7Shows the banned commands help menu.")).suggestCommand("/bannedcommands help").send(player);
								JSONMessage.create(Methods.color(" &f/BannedCommands Add &6<command> &e- Add a command to the \n&ebanned command list.")).tooltip(Methods.color("&f/BannedCommands add &6<command> \n&7Add a command to the banned command list.")).suggestCommand("/bannedcommands add <command>").send(player);
								JSONMessage.create(Methods.color(" &f/BannedCommands Remove &6<command> &e- Remove a command from the \n&ebanned command list.")).tooltip(Methods.color("&f/BannedCommands remove &6<command> \n&7Remove a command from the banned command list.")).suggestCommand("/bannedcommands remove <command>").send(player);
								JSONMessage.create(Methods.color(" &f/BannedCommands List &e- Shows a list of the servers banned commands.")).tooltip(Methods.color("&f/BannedCommands List \n&7Shows a list of the servers banned commands")).suggestCommand("/bannedcommands list").send(player);
								JSONMessage.create("").send(player);
								JSONMessage.create(Methods.color(" &e&lTIP &7Try to hover or click the command!")).tooltip(Methods.color("&7Hover over the commands to get information about them. \n&7Click on the commands to insert them in the chat.")).send(player);
								JSONMessage.create("").send(player);
								return true;
							} else {
								player.sendMessage("");
								player.sendMessage(Methods.color(" &3Banned Commands Help Menu &f(v" + plugin.getDescription().getVersion() + ")"));
								player.sendMessage("");
								player.sendMessage(Methods.color(" &6<> &f= Required Arguments"));
								player.sendMessage("");
								player.sendMessage(Methods.color(" &f/BannedCommands Help &e- Shows the banned commands help menu."));
								player.sendMessage(Methods.color(" &f/BannedCommands Add &6<command> &e- Add a command to the banned command list."));
								player.sendMessage(Methods.color(" &f/BannedCommands Remove &6<command> &e- Remove a command from the banned command list."));
								player.sendMessage(Methods.color(" &f/BannedCommands List &e- Shows a list of the servers banned commands."));
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
					if (player.hasPermission("chatmanager.bannedcommands.add")) {
						if (args.length == 2) {
							if (!bannedCommands.getStringList("Banned-Commands").contains(args[1])) {
								List<String> list = bannedCommands.getStringList("Banned-Commands");
								list.add(args[1].toLowerCase());
								bannedCommands.set("Banned-Commands", list);
								ChatManager.settings.saveBannedCommands();
								ChatManager.settings.reloadBannedCommands();
								player.sendMessage(Methods.color(player, messages.getString("Banned_Commands.Command_Added").replace("{command}", args[1]).replace("{Prefix}", messages.getString("Message.Prefix"))));
							} else {
								player.sendMessage(Methods.color(player, messages.getString("Banned_Commands.Command_Exists").replace("{command}", args[1]).replace("{Prefix}", messages.getString("Message.Prefix"))));
							}
						} else {
							player.sendMessage(Methods.color("&cCommand Usage: &7/Bannedcommands add <command>"));
						}
					} else {
						player.sendMessage(Methods.noPermission());
					}
				}
				if (args[0].equalsIgnoreCase("remove")) {
					if (player.hasPermission("chatmanager.bannedcommands.remove")) {
						if (args.length == 2) {
							if (bannedCommands.getStringList("Banned-Commands").contains(args[1])) {
								List<String> list = bannedCommands.getStringList("Banned-Commands");
								list.remove(args[1].toLowerCase());
								bannedCommands.set("Banned-Commands", list);
								ChatManager.settings.saveBannedCommands();
								ChatManager.settings.reloadBannedCommands();
								player.sendMessage(Methods.color(player, messages.getString("Banned_Commands.Command_Removed").replace("{command}", args[1]).replace("{Prefix}", messages.getString("Message.Prefix"))));
							} else {
								player.sendMessage(Methods.color(player, messages.getString("Banned_Commands.Command_Not_Found").replace("{command}", args[1]).replace("{Prefix}", messages.getString("Message.Prefix"))));
							}
						} else {
							player.sendMessage(Methods.color("&cCommand Usage: &7/Bannedcommands remove <command>"));
						}
					} else {
						player.sendMessage(Methods.noPermission());
					}
				}
				if (args[0].equalsIgnoreCase("list")) {
					if (player.hasPermission("chatmanager.bannedcommands.list")) {
						if (args.length == 1) {
							String list = bannedCommands.getStringList("Banned-Commands").toString().replace("[", "").replace("]", "");
							player.sendMessage("");
							player.sendMessage(Methods.color("&cCommands: &7" + list));
							player.sendMessage(" ");
						} else {
							player.sendMessage(Methods.color("&cCommand Usage: &7/Bannedcommands list"));
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