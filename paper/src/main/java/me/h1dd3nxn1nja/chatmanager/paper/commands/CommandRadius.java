package me.h1dd3nxn1nja.chatmanager.paper.commands;

import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.Methods;
import me.h1dd3nxn1nja.chatmanager.paper.SettingsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandRadius implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();
	
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		FileConfiguration messages = settingsManager.getMessages();
		
		if (!(sender instanceof Player player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

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
					}
				} else {
					Methods.sendMessage(player, Methods.noPermission(), true);
					return true;
				}
			}

			if (args[0].equalsIgnoreCase("Local")) {
				if (player.hasPermission("chatmanager.chatradius.local")) {
					if (args.length == 1) {

						boolean isValid = plugin.api().getLocalChatData().containsUser(player.getUniqueId());

						if (isValid) {
							plugin.api().getGlobalChatData().removeUser(player.getUniqueId());
							plugin.api().getWorldChatData().removeUser(player.getUniqueId());
							plugin.api().getLocalChatData().addUser(player.getUniqueId());

							Methods.sendMessage(player, messages.getString("Chat_Radius.Local_Chat.Enabled"), true);

							return true;
						}

						Methods.sendMessage(player, messages.getString("Chat_Radius.Local_Chat.Already_Enabled"), true);
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
						boolean isValid = plugin.api().getGlobalChatData().containsUser(player.getUniqueId());

						if (isValid) {
							plugin.api().getLocalChatData().removeUser(player.getUniqueId());
							plugin.api().getWorldChatData().removeUser(player.getUniqueId());
							plugin.api().getGlobalChatData().addUser(player.getUniqueId());

							Methods.sendMessage(player, messages.getString("Chat_Radius.Global_Chat.Enabled"), true);
							return true;
						}

						Methods.sendMessage(player, messages.getString("Chat_Radius.Global_Chat.Already_Enabled"), true);
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
						boolean isValid = plugin.api().getWorldChatData().containsUser(player.getUniqueId());

						if (isValid) {
							plugin.api().getLocalChatData().removeUser(player.getUniqueId());
							plugin.api().getGlobalChatData().removeUser(player.getUniqueId());
							plugin.api().getWorldChatData().addUser(player.getUniqueId());

							Methods.sendMessage(player, messages.getString("Chat_Radius.World_Chat.Enabled"), true);
							return true;
						}

						Methods.sendMessage(player, messages.getString("Chat_Radius.World_Chat.Already_Enabled"), true);
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
						boolean isValid = plugin.api().getSpyChatData().containsUser(player.getUniqueId());

						if (isValid) {
							plugin.api().getSpyChatData().removeUser(player.getUniqueId());
							Methods.sendMessage(player, messages.getString("Chat_Radius.Spy.Disabled"), true);

							return true;
						}

						plugin.api().getSpyChatData().addUser(player.getUniqueId());
						Methods.sendMessage(player, messages.getString("Chat_Radius.Spy.Enabled"), true);
					} else {
						Methods.sendMessage(player, ("&cCommand Usage: &7/ChatRadius Spy"), true);
					}
				} else {
					Methods.sendMessage(player, Methods.noPermission(), true);
				}
			}
		}

		return true;
	}
}