package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandRadius implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		FileConfiguration messages = settingsManager.getMessages();

		if (!(sender instanceof Player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("chatradius")) {
			if (player.hasPermission("chatmanager.chatradius")) {
				if (args.length == 0) {
					Methods.sendMessage(player, "", true);
					Methods.sendMessage(player, (" &3Chat Radius Help Menu &f(v" + plugin.getDescription().getVersion() + ")"), true);
					Methods.sendMessage(player, "", true);
					Methods.sendMessage(player, (" &f/ChatRadius Help &e- Shows a list of commands for chat radius."), true);
					Methods.sendMessage(player, (" &f/ChatRadius Local &e- Enables local chat."), true);
					Methods.sendMessage(player, (" &f/ChatRadius Global &e- Enables global chat."), true);
					Methods.sendMessage(player, (" &f/ChatRadius World &e- Enables world chat."), true);
					Methods.sendMessage(player, "", true);

					return true;
				}
			}
		} else {
			Methods.sendMessage(player, Methods.noPermission(), true);
			return true;
		}

		if (args[0].equalsIgnoreCase("help")) {
			if (player.hasPermission("chatmanager.chatradius")) {
				if (args.length == 1) {
					Methods.sendMessage(player, "", true);
					Methods.sendMessage(player, (" &3Chat Radius Help Menu &f(v" + plugin.getDescription().getVersion() + ")"), true);
					Methods.sendMessage(player, "", true);
					Methods.sendMessage(player, (" &f/ChatRadius Help &e- Shows a list of commands for chat radius."), true);
					Methods.sendMessage(player, (" &f/ChatRadius Local &e- Enables local chat."), true);
					Methods.sendMessage(player, (" &f/ChatRadius Global &e- Enables global chat."), true);
					Methods.sendMessage(player, (" &f/ChatRadius World &e- Enables world chat."), true);
					Methods.sendMessage(player, (" &f/ChatRadius Spy &e- Enables chat radius spy. Players will be able to see all messages sent."), true);
					Methods.sendMessage(player, "", true);
					return true;
				}
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
				return true;
			}
		}

		if (args[0].equalsIgnoreCase("Local")) {
			if (player.hasPermission("chatmanager.chatradius.local")) {
				if (args.length == 1) {
					if (!Methods.cm_localChat.contains(player.getUniqueId())) {
						Methods.cm_globalChat.remove(player.getUniqueId());
						Methods.cm_worldChat.remove(player.getUniqueId());
						Methods.cm_localChat.add(player.getUniqueId());
						Methods.sendMessage(player, messages.getString("Chat_Radius.Local_Chat.Enabled"), true);
					} else {
						Methods.sendMessage(player, messages.getString("Chat_Radius.Local_Chat.Already_Enabled"), true);
					}
				} else {
					Methods.sendMessage(player, "&cCommand Usage: &7/ChatRadius Local", true);
				}
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
			}
		}

		if (args[0].equalsIgnoreCase("Global")) {
			if (player.hasPermission("chatmanager.chatradius.global")) {
				if (args.length == 1) {
					if (!Methods.cm_globalChat.contains(player.getUniqueId())) {
						Methods.cm_localChat.remove(player.getUniqueId());
						Methods.cm_worldChat.remove(player.getUniqueId());
						Methods.cm_globalChat.add(player.getUniqueId());
						Methods.sendMessage(player, messages.getString("Chat_Radius.Global_Chat.Enabled"), true);
					} else {
						Methods.sendMessage(player, messages.getString("Chat_Radius.Global_Chat.Already_Enabled"), true);
					}
				} else {
					Methods.sendMessage(player, "&cCommand Usage: &7/ChatRadius Global", true);
				}
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
			}
		}

		if (args[0].equalsIgnoreCase("World")) {
			if (player.hasPermission("chatmanager.chatradius.world")) {
				if (args.length == 1) {
					if (!Methods.cm_worldChat.contains(player.getUniqueId())) {
						Methods.cm_localChat.remove(player.getUniqueId());
						Methods.cm_globalChat.remove(player.getUniqueId());
						Methods.cm_worldChat.add(player.getUniqueId());
						Methods.sendMessage(player, messages.getString("Chat_Radius.World_Chat.Enabled"), true);
					} else {
						Methods.sendMessage(player, messages.getString("Chat_Radius.World_Chat.Already_Enabled"), true);
					}
				} else {
					Methods.sendMessage(player, "&cCommand Usage: &7/ChatRadius World", true);
				}
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
			}
		}

		if (args[0].equalsIgnoreCase("Spy")) {
			if (player.hasPermission("chatmanager.chatradius.spy")) {
				if (args.length == 1) {
					if (!Methods.cm_spyChat.contains(player.getUniqueId())) {
						Methods.cm_spyChat.add(player.getUniqueId());
						Methods.sendMessage(player, messages.getString("Chat_Radius.Spy.Enabled"), true);
					} else {
						Methods.cm_spyChat.remove(player.getUniqueId());
						Methods.sendMessage(player, messages.getString("Chat_Radius.Spy.Disabled"), true);
					}
				} else {
					Methods.sendMessage(player, ("&cCommand Usage: &7/ChatRadius Spy"), true);
				}
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
			}
		}

		return true;
	}
}