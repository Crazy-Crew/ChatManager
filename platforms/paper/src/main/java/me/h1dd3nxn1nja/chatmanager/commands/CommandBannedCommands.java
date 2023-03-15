package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import me.h1dd3nxn1nja.chatmanager.utils.JSONMessage;
import me.h1dd3nxn1nja.chatmanager.utils.ServerProtocol;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import java.util.List;

public class CommandBannedCommands implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		FileConfiguration messages = settingsManager.getMessages();
		FileConfiguration bannedCommands = settingsManager.getBannedCommands();

		if (!(sender instanceof Player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("BannedCommands")) {
			if (player.hasPermission("chatmanager.bannedcommands")) {
				if (args.length == 0) {
					if (ServerProtocol.isAtLeast(ServerProtocol.v1_9_R1) && ServerProtocol.isOlder(ServerProtocol.v1_17_R1)) {
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
						Methods.sendMessage(player, "", true);
						Methods.sendMessage(player, " &3Banned Commands Help Menu &f(v" + plugin.getDescription().getVersion() + ")", true);
						Methods.sendMessage(player, "", true);
						Methods.sendMessage(player, " &6<> &f= Required Arguments", true);
						Methods.sendMessage(player, "", true);
						Methods.sendMessage(player, " &f/BannedCommands Help &e- Shows the banned commands help menu.", true);
						Methods.sendMessage(player, " &f/BannedCommands Add &6<command> &e- Add a command to the banned command list.", true);
						Methods.sendMessage(player, " &f/BannedCommands Remove &6<command> &e- Remove a command from the banned command list.", true);
						Methods.sendMessage(player, " &f/BannedCommands List &e- Shows a list of the servers banned commands.", true);
						Methods.sendMessage(player, "", true);
						return true;
					}
				}
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
				return true;
			}

			if (args[0].equalsIgnoreCase("help")) {
				if (player.hasPermission("chatmanager.bannedcommands.help")) {
					if (args.length == 1) {
						if (ServerProtocol.isAtLeast(ServerProtocol.v1_9_R1) && ServerProtocol.isOlder(ServerProtocol.v1_17_R1)) {
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
							Methods.sendMessage(player, "", true);
							Methods.sendMessage(player, " &3Banned Commands Help Menu &f(v" + plugin.getDescription().getVersion() + ")", true);
							Methods.sendMessage(player, "", true);
							Methods.sendMessage(player, " &6<> &f= Required Arguments", true);
							Methods.sendMessage(player, "", true);
							Methods.sendMessage(player, " &f/BannedCommands Help &e- Shows the banned commands help menu.", true);
							Methods.sendMessage(player, " &f/BannedCommands Add &6<command> &e- Add a command to the banned command list.", true);
							Methods.sendMessage(player, " &f/BannedCommands Remove &6<command> &e- Remove a command from the banned command list.", true);
							Methods.sendMessage(player, " &f/BannedCommands List &e- Shows a list of the servers banned commands.", true);
							Methods.sendMessage(player, "", true);
							return true;
						}
					}
				} else {
					Methods.sendMessage(player, Methods.noPermission(), true);
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
							settingsManager.saveBannedCommands();
							settingsManager.reloadBannedCommands();
							Methods.sendMessage(player, messages.getString("Banned_Commands.Command_Added").replace("{command}", args[1]), true);
						} else {
							Methods.sendMessage(player, messages.getString("Banned_Commands.Command_Exists").replace("{command}", args[1]), true);
						}
					} else {
						Methods.sendMessage(player, "&cCommand Usage: &7/Bannedcommands add <command>", true);
					}
				} else {
					Methods.sendMessage(player, Methods.noPermission(), true);
				}
			}
			if (args[0].equalsIgnoreCase("remove")) {
				if (player.hasPermission("chatmanager.bannedcommands.remove")) {
					if (args.length == 2) {
						if (bannedCommands.getStringList("Banned-Commands").contains(args[1])) {
							List<String> list = bannedCommands.getStringList("Banned-Commands");
							list.remove(args[1].toLowerCase());
							bannedCommands.set("Banned-Commands", list);
							settingsManager.saveBannedCommands();
							settingsManager.reloadBannedCommands();
							Methods.sendMessage(player, messages.getString("Banned_Commands.Command_Removed").replace("{command}", args[1]), true);
						} else {
							Methods.sendMessage(player, messages.getString("Banned_Commands.Command_Not_Found").replace("{command}", args[1]), true);
						}
					} else {
						Methods.sendMessage(player, "&cCommand Usage: &7/Bannedcommands remove <command>", true);
					}
				} else {
					Methods.sendMessage(player, Methods.noPermission(), true);
				}
			}
			if (args[0].equalsIgnoreCase("list")) {
				if (player.hasPermission("chatmanager.bannedcommands.list")) {
					if (args.length == 1) {
						String list = bannedCommands.getStringList("Banned-Commands").toString().replace("[", "").replace("]", "");
						Methods.sendMessage(player, "", true);
						Methods.sendMessage(player, "&cCommands: &7" + list, true);
						player.sendMessage(" ");
					} else {
						Methods.sendMessage(player, "&cCommand Usage: &7/Bannedcommands list", true);
					}
				} else {
					Methods.sendMessage(player, Methods.noPermission(), true);
				}
			}
		}

		return true;
	}
}