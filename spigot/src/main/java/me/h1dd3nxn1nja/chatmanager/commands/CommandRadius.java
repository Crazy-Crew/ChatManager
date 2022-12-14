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

public class CommandRadius implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		FileConfiguration messages = settingsManager.getMessages();
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("Error: You can only use that command in game");
			return true;
		}

		Player player = (Player) sender;

		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("chatradius")) {
				if (player.hasPermission("chatmanager.chatradius")) {
					if (args.length == 0) {
						if (ServerProtocol.isAtLeast(ServerProtocol.v1_9_R1) && ServerProtocol.isOlder(ServerProtocol.v1_17_R1)) {
							JSONMessage.create("").send(player);
							JSONMessage.create(" &3Chat Radius Help Menu &f(v" + plugin.getDescription().getVersion() + ")").send(player);
							JSONMessage.create("").send(player);
							JSONMessage.create(" &f/ChatRadius Help &e- Shows a list of commands for chat radius.").tooltip(Methods.color("&f/ChatRadius Help \n&7Shows a list of commands for chat radius.")).suggestCommand("/chatradius help").send(player);
							JSONMessage.create(" &f/ChatRadius Local &e- Enables local chat.").tooltip(Methods.color("&f/ChatRadius Local \n&7Enables local chat.")).suggestCommand("/chatradius local").send(player);
							JSONMessage.create(" &f/ChatRadius Global &e- Enables global chat.").tooltip(Methods.color("&f/ChatRadius Global \n&7Enables global chat.")).suggestCommand("/chatradius global").send(player);
							JSONMessage.create(" &f/ChatRadius World &e- Enables world chat.").tooltip(Methods.color("&f/ChatRadius World \n&7Enables world chat.")).suggestCommand("/chatradius world").send(player);
							JSONMessage.create("").send(player);
							JSONMessage.create(" &e&lTIP &7Try to hover or click the command!").tooltip(Methods.color("&7Hover over the commands to get information about them. \n&7Click on the commands to insert them in the chat.")).send(player);
							JSONMessage.create("").send(player);
							return true;
						} else {
							player.sendMessage("");
							player.sendMessage(Methods.color(" &3Chat Radius Help Menu &f(v" + plugin.getDescription().getVersion() + ")"));
							player.sendMessage("");
							player.sendMessage(Methods.color(" &f/ChatRadius Help &e- Shows a list of commands for chat radius."));
							player.sendMessage(Methods.color(" &f/ChatRadius Local &e- Enables local chat."));
							player.sendMessage(Methods.color(" &f/ChatRadius Global &e- Enables global chat."));
							player.sendMessage(Methods.color(" &f/ChatRadius World &e- Enables world chat."));
							player.sendMessage("");
							return true;
						}
					}
				} else {
					player.sendMessage(Methods.noPermission());
					return true;
				}

				if (args[0].equalsIgnoreCase("help")) {
					if (player.hasPermission("chatmanager.chatradius")) {
						if (args.length == 1) {
							if (ServerProtocol.isAtLeast(ServerProtocol.v1_9_R1) && ServerProtocol.isOlder(ServerProtocol.v1_17_R1)) {
								JSONMessage.create("").send(player);
								JSONMessage.create(" &3Chat Radius Help Menu &f(v" + plugin.getDescription().getVersion() + ")").send(player);
								JSONMessage.create("").send(player);
								JSONMessage.create(" &f/ChatRadius Help &e- Shows a list of commands for chat radius.").tooltip(Methods.color("&f/ChatRadius Help \n&7Shows a list of commands for chat radius.")).suggestCommand("/chatradius help").send(player);
								JSONMessage.create(" &f/ChatRadius Local &e- Enables local chat.").tooltip(Methods.color("&f/ChatRadius Local \n&7Enables local chat.")).suggestCommand("/chatradius local").send(player);
								JSONMessage.create(" &f/ChatRadius Global &e- Enables global chat.").tooltip(Methods.color("&f/ChatRadius Global \n&7Enables global chat.")).suggestCommand("/chatradius global").send(player);
								JSONMessage.create(" &f/ChatRadius World &e- Enables world chat.").tooltip(Methods.color("&f/ChatRadius World \n&7Enables world chat.")).suggestCommand("/chatradius world").send(player);
								JSONMessage.create(" &f/ChatRadius Spy &e- Enables chat radius spy. \n&ePlayers will be able to see all messages sent.").tooltip(Methods.color("&f/ChatRadius Spy \n&7Enables chat radius spy. Players will be able to see all messages sent.")).suggestCommand("/chatradius spy").send(player);
								JSONMessage.create("").send(player);
								JSONMessage.create(" &e&lTIP &7Try to hover or click the command!").tooltip(Methods.color("&7Hover over the commands to get information about them. \n&7Click on the commands to insert them in the chat.")).send(player);
								JSONMessage.create("").send(player);
								return true;
							} else {
								player.sendMessage("");
								player.sendMessage(Methods.color(" &3Chat Radius Help Menu &f(v" + plugin.getDescription().getVersion() + ")"));
								player.sendMessage("");
								player.sendMessage(Methods.color(" &f/ChatRadius Help &e- Shows a list of commands for chat radius."));
								player.sendMessage(Methods.color(" &f/ChatRadius Local &e- Enables local chat."));
								player.sendMessage(Methods.color(" &f/ChatRadius Global &e- Enables global chat."));
								player.sendMessage(Methods.color(" &f/ChatRadius World &e- Enables world chat."));
								player.sendMessage(Methods.color(" &f/ChatRadius Spy &e- Enables chat radius spy. Players will be able to see all messages sent."));
								player.sendMessage("");
								return true;
							}
						}
					} else {
						player.sendMessage(Methods.noPermission());
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
								player.sendMessage(Methods.color(player, messages.getString("Chat_Radius.Local_Chat.Enabled").replace("{Prefix}", messages.getString("Message.Prefix"))));
							} else {
								player.sendMessage(Methods.color(player, messages.getString("Chat_Radius.Local_Chat.Already_Enabled").replace("{Prefix}", messages.getString("Message.Prefix"))));
							}
						} else {
							player.sendMessage(Methods.color("&cCommand Usage: &7/ChatRadius Local"));
						}
					} else {
						player.sendMessage(Methods.noPermission());
					}
				}

				if (args[0].equalsIgnoreCase("Global")) {
					if (player.hasPermission("chatmanager.chatradius.global")) {
						if (args.length == 1) {
							if (!Methods.cm_globalChat.contains(player.getUniqueId())) {
								Methods.cm_localChat.remove(player.getUniqueId());
								Methods.cm_worldChat.remove(player.getUniqueId());
								Methods.cm_globalChat.add(player.getUniqueId());
								player.sendMessage(Methods.color(player, messages.getString("Chat_Radius.Global_Chat.Enabled").replace("{Prefix}", messages.getString("Message.Prefix"))));
							} else {
								player.sendMessage(Methods.color(player, messages.getString("Chat_Radius.Global_Chat.Already_Enabled").replace("{Prefix}", messages.getString("Message.Prefix"))));
							}
						} else {
							player.sendMessage(Methods.color("&cCommand Usage: &7/ChatRadius Global"));
						}
					} else {
						player.sendMessage(Methods.noPermission());
					}
				}

				if (args[0].equalsIgnoreCase("World")) {
					if (player.hasPermission("chatmanager.chatradius.world")) {
						if (args.length == 1) {
							if (!Methods.cm_worldChat.contains(player.getUniqueId())) {
								Methods.cm_localChat.remove(player.getUniqueId());
								Methods.cm_globalChat.remove(player.getUniqueId());
								Methods.cm_worldChat.add(player.getUniqueId());
								player.sendMessage(Methods.color(player, messages.getString("Chat_Radius.World_Chat.Enabled").replace("{Prefix}", messages.getString("Message.Prefix"))));
							} else {
								player.sendMessage(Methods.color(player, messages.getString("Chat_Radius.World_Chat.Already_Enabled").replace("{Prefix}", messages.getString("Message.Prefix"))));
							}
						} else {
							player.sendMessage(Methods.color("&cCommand Usage: &7/ChatRadius World"));
						}
					} else {
						player.sendMessage(Methods.noPermission());
					}
				}

				if (args[0].equalsIgnoreCase("Spy")) {
					if (player.hasPermission("chatmanager.chatradius.spy")) {
						if (args.length == 1) {
							if (!Methods.cm_spyChat.contains(player.getUniqueId())) {
								Methods.cm_spyChat.add(player.getUniqueId());
								player.sendMessage(Methods.color(player, messages.getString("Chat_Radius.Spy.Enabled")
										.replace("{Prefix}", messages.getString("Message.Prefix"))));
							} else {
								Methods.cm_spyChat.remove(player.getUniqueId());
								player.sendMessage(Methods.color(player, messages.getString("Chat_Radius.Spy.Disabled")
										.replace("{Prefix}", messages.getString("Message.Prefix"))));
							}
						} else {
							player.sendMessage(Methods.color("&cCommand Usage: &7/ChatRadius Spy"));
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