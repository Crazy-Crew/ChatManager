package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import me.h1dd3nxn1nja.chatmanager.utils.ServerProtocol;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.managers.AutoBroadcastManager;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import me.h1dd3nxn1nja.chatmanager.utils.Debug;
import me.h1dd3nxn1nja.chatmanager.utils.JSONMessage;

public class CommandChatManager implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		FileConfiguration messages = settingsManager.getMessages();

		if (cmd.getName().equalsIgnoreCase("ChatManager")) {
			if (args.length == 0) {
				sender.sendMessage(Methods.color("&7This server is using the plugin &cChatManager "
						+ "&7version " + plugin.getDescription().getVersion() + " by &cH1DD3NxN1NJA."));
				sender.sendMessage(Methods.color("&7Commands: &c/Chatmanager help"));
				return true;
			}
			
			if (args[0].equalsIgnoreCase("reload")) {
				if (sender.hasPermission("chatmanager.reload")) {
					if (args.length == 1) {
						for (Player all : plugin.getServer().getOnlinePlayers()) {
							Methods.cm_chatCooldown.remove(all);
							Methods.cm_cooldownTask.remove(all);
							Methods.cm_commandCooldown.remove(all);
							Methods.cm_cooldownTask.remove(all);

							if (ServerProtocol.isAtLeast(ServerProtocol.v1_9_R1)) {
								BossBarUtil bossBar = new BossBarUtil();
								bossBar.removeBossBar(all);
							}
						}
						
						settingsManager.reloadConfig();
						settingsManager.reloadMessages();
						settingsManager.reloadAutoBroadcast();
						settingsManager.reloadBannedCommands();
						settingsManager.reloadBannedWords();
						settingsManager.setup();
						
						plugin.getServer().getScheduler().cancelTasks(plugin);

						try {
							if (settingsManager.getAutoBroadcast().getBoolean("Auto_Broadcast.Actionbar_Messages.Enable")) AutoBroadcastManager.actionbarMessages();
							if (settingsManager.getAutoBroadcast().getBoolean("Auto_Broadcast.Global_Messages.Enable")) AutoBroadcastManager.globalMessages();
							if (settingsManager.getAutoBroadcast().getBoolean("Auto_Broadcast.Per_World_Messages.Enable")) AutoBroadcastManager.perWorldMessages();
							if (settingsManager.getAutoBroadcast().getBoolean("Auto_Broadcast.Title_Messages.Enable")) AutoBroadcastManager.titleMessages();
							if (settingsManager.getAutoBroadcast().getBoolean("Auto_Broadcast.Bossbar_Messages.Enable")) AutoBroadcastManager.bossBarMessages();
						} catch (Exception ex) {
							plugin.getServer().getConsoleSender().sendMessage("There has been an error setting up auto broadcast. Stacktrace...");
							ex.printStackTrace();
						}
						
						sender.sendMessage(Methods.color(messages.getString("Message.Reload").replace("{Prefix}", messages.getString("Message.Prefix"))));
						
					} else {
						sender.sendMessage(Methods.color("&cCommand Usage: &7/Chatmanager reload"));
					}
				} else {
					sender.sendMessage(Methods.noPermission());
				}
			}

			if (args[0].equalsIgnoreCase("debug")) {
				if (!sender.hasPermission("chatmanager.debug")) {
					sender.sendMessage(Methods.noPermission());
					return true;
				}

				if (args.length == 1) {
					sender.sendMessage("");
					sender.sendMessage(Methods.color(" &3Chat Manager Debug Help Menu &f(v" + plugin.getDescription().getVersion() + ")"));
					sender.sendMessage("");
					sender.sendMessage(Methods.color(" &f/Chatmanager Debug &e- Shows a list of commands to debug."));
					sender.sendMessage(Methods.color(" &f/Chatmanager Debug All &e- Debugs all the configuration files."));
					sender.sendMessage(Methods.color(" &f/Chatmanager Debug AutoBroadcast &e- Debugs the autobroadcast.yml file"));
					sender.sendMessage(Methods.color(" &f/Chatmanager Debug Config &e- Debugs the config.yml file."));
					sender.sendMessage(Methods.color(" &f/Chatmanager Debug Messages &e- Debugs the messages.yml file."));
					sender.sendMessage("");
					return true;
				}

				if (args[1].equalsIgnoreCase("all")) {
					if (sender.hasPermission("chatmanager.debug")) {
						if (args.length == 2) {
							sender.sendMessage(Methods.color(Methods.getPrefix() + " &7Debugging all configuration files, please go to your console to see the debug log."));
							Debug.debugAutoBroadcast();
							Debug.debugConfig();
							Debug.debugMessages();
						} else {
							sender.sendMessage(Methods.color("&cCommand Usage: &7/Chatmanager debug all"));
						}
					} else {
						sender.sendMessage(Methods.noPermission());
					}
				}

				if (args[1].equalsIgnoreCase("autobroadcast")) {
					if (sender.hasPermission("chatmanager.debug")) {
						if (args.length == 2) {
							sender.sendMessage(Methods.color(Methods.getPrefix() + " &7Debugging autobroadcast, please go to your console to see the debug log."));
							Debug.debugAutoBroadcast();
						} else {
							sender.sendMessage(Methods.color("&cCommand Usage: &7/Chatmanager debug autobroadcast"));
						}
					} else {
						sender.sendMessage(Methods.noPermission());
					}
				}

				if (args[1].equalsIgnoreCase("config")) {
					if (sender.hasPermission("chatmanager.debug")) {
						if (args.length == 2) {
							sender.sendMessage(Methods.color(Methods.getPrefix() + " &7Debugging config, please go to your console to see the debug."));
							Debug.debugConfig();
						} else {
							sender.sendMessage(Methods.color("&cCommand Usage: &7/Chatmanager debug config"));
						}
					} else {
						sender.sendMessage(Methods.noPermission());
					}
				}

				if (args[1].equalsIgnoreCase("messages")) {
					if (sender.hasPermission("chatmanager.debug")) {
						if (args.length == 2) {
							sender.sendMessage(Methods.color(Methods.getPrefix() + " &7Debugging messages, please go to your console to see the debug."));
							Debug.debugMessages();
						} else {
							sender.sendMessage(Methods.color("&cCommand Usage: &7/Chatmanager debug messages"));
						}
					} else {
						sender.sendMessage(Methods.noPermission());
					}
				}
			}

			if (!(sender instanceof ConsoleCommandSender)) {
				Player player = (Player) sender;
				if (args[0].equalsIgnoreCase("help")) {
					if (args.length == 1) {
						if (ServerProtocol.isAtLeast(ServerProtocol.v1_9_R1) && ServerProtocol.isOlder(ServerProtocol.v1_17_R1)) {
							JSONMessage.create("").send(player);
							JSONMessage.create(" &3Chat Manager &f(v" + plugin.getDescription().getVersion() + ")").send(player);
							JSONMessage.create("").send(player);
							JSONMessage.create(" &6<> &f= Required Arguments").send(player);
							JSONMessage.create("").send(player);
							JSONMessage.create(" &f/Chatmanager Help &e- Help menu for chat manager.")
								.tooltip("&f/Chatmanager Help \n&7Help menu for chat manager.").suggestCommand("/chatmanager help").send(player);
							JSONMessage.create(" &f/Chatmanager Reload &e- Reloads all the configuration files.")
								.tooltip("&f/Chatmanager Reload \n&7Reloads all the configuration files.").suggestCommand("/chatmanager reload").send(player);
							JSONMessage.create(" &f/Chatmanager Debug &e- Debugs all the configuration files.")
								.tooltip("&f/Chatmanager Debug \n&7Debugs all the configuration files.").suggestCommand("/chatmanager debug").send(player);
							JSONMessage.create(" &f/AntiSwear &e- Shows a list of commands for Anti Swear.")
									.tooltip("&f/AntiSwear \n&7Shows a list of commands for Anti Swear.").suggestCommand("/antiswear").send(player);
							JSONMessage.create(" &f/AutoBroadcast &e- Shows a list of the autobroadcast commands.")
									.tooltip("&f/AutoBroadcast \n&7Shows a list of commands for Auto-Broadcast.").suggestCommand("/autobroadcast").send(player);
							JSONMessage.create(" &f/BannedCommands &e- Shows a list of commands for Banned Commands.")
									.tooltip("&f/BannedCommands \n&7Shows a list of commands for Banned Commands.").suggestCommand("/bannedcommands").send(player);
							JSONMessage.create(" &f/ChatRadius &e- Shows a list of commands for Chat Radius.")
									.tooltip("&f/ChatRadius \n&7Shows a list of commands for Chat Radius.").suggestCommand("/chatradius").send(player);
							JSONMessage.create("").send(player);
							JSONMessage.create(" &7Page 1/3. Type /Chatmanager help 2 to go to the next page.")
									.tooltip("&f/ChatManager help 2").suggestCommand("/chatmanager help 2").send(player);
							JSONMessage.create("").send(player);
						return true;
					} else {
						player.sendMessage("");
						player.sendMessage(Methods.color(" &3Chat Manager &f(v" + plugin.getDescription().getVersion() + ")"));
						player.sendMessage("");
						player.sendMessage(Methods.color(" &6<> &f= Required Arguments"));
						player.sendMessage("");
						player.sendMessage(Methods.color(" &f/Chatmanager Help &e- Help menu for chat manager."));
						player.sendMessage(Methods.color(" &f/Chatmanager Reload &e- Reloads all the configuration files."));
						player.sendMessage(Methods.color(" &f/Chatmanager Debug &e- Debugs all the configuration files."));
						player.sendMessage(Methods.color(" &f/AntiSwear &6- Shows a list of commands for Anti Swear."));
						player.sendMessage(Methods.color(" &f/AutoBroadcast &e- Shows a list of commands for Auto-Broadcast."));
						player.sendMessage(Methods.color(" &f/BannedCommands &e- Shows a list of commands for Banned Commands."));
						player.sendMessage(Methods.color(" &f/ChatRadius &e- Shows a list of commands for Chat Radius."));
						player.sendMessage("");
						player.sendMessage(Methods.color(" &7Page 1/3. Type /Chatmanager help 2 to go to the next page."));
						player.sendMessage("");
						return true;
						}
					}

					if (args[1].equalsIgnoreCase("1")) {
						if (args.length == 2) {
							if (ServerProtocol.isAtLeast(ServerProtocol.v1_9_R1) && ServerProtocol.isOlder(ServerProtocol.v1_17_R1)) {
								JSONMessage.create("").send(player);
								JSONMessage.create(" &3Chat Manager &f(v" + plugin.getDescription().getVersion() + ")").send(player);
								JSONMessage.create("").send(player);
								JSONMessage.create(" &6<> &f= Required Arguments").send(player);
								JSONMessage.create("").send(player);
								JSONMessage.create(" &f/Chatmanager Help &e- Help menu for chat manager.")
									.tooltip("&f/Chatmanager Help \n&7Help menu for chat manager.").suggestCommand("/chatmanager help").send(player);
								JSONMessage.create(" &f/Chatmanager Reload &e- Reloads all the configuration files.")
									.tooltip("&f/Chatmanager Reload \n&7Reloads all the configuration files.").suggestCommand("/chatmanager reload").send(player);
								JSONMessage.create(" &f/Chatmanager Debug &e- Debugs all the configuration files.")
									.tooltip("&f/Chatmanager Debug \n&7Debugs all the configuration files.").suggestCommand("/chatmanager debug").send(player);
								JSONMessage.create(" &f/AntiSwear &e- Shows a list of commands for Anti Swear.")
										.tooltip("&f/AntiSwear \n&7Shows a list of commands for Anti Swear.").suggestCommand("/antiswear").send(player);
								JSONMessage.create(" &f/AutoBroadcast &e- Shows a list of the autobroadcast commands.")
										.tooltip("&f/AutoBroadcast \n&7Shows a list of commands for Auto-Broadcast.").suggestCommand("/autobroadcast").send(player);
								JSONMessage.create(" &f/BannedCommands &e- Shows a list of commands for Banned Commands.")
										.tooltip("&f/BannedCommands \n&7Shows a list of commands for Banned Commands.").suggestCommand("/bannedcommands").send(player);
								JSONMessage.create(" &f/ChatRadius &e- Shows a list of commands for Chat Radius.")
										.tooltip("&f/ChatRadius \n&7Shows a list of commands for Chat Radius.").suggestCommand("/chatradius").send(player);
								JSONMessage.create("").send(player);
								JSONMessage.create(" &7Page 1/3. Type /Chatmanager help 2 to go to the next page.").tooltip("&f/ChatManager help 2").suggestCommand("/chatmanager help 2").send(player);
								JSONMessage.create("").send(player);
							return true;
						} else {
							player.sendMessage("");
							player.sendMessage(Methods.color(" &3Chat Manager &f(v" + plugin.getDescription().getVersion() + ")"));
							player.sendMessage("");
							player.sendMessage(Methods.color(" &6<> &f= Required Arguments"));
							player.sendMessage("");
							player.sendMessage(Methods.color(" &f/Chatmanager Help &e- Help menu for chat manager."));
							player.sendMessage(Methods.color(" &f/Chatmanager Reload &e- Reloads all the configuration files."));
							player.sendMessage(Methods.color(" &f/Chatmanager Debug &e- Debugs all the configuration files."));
							player.sendMessage(Methods.color(" &f/AntiSwear &6- Shows a list of commands for Anti Swear."));
							player.sendMessage(Methods.color(" &f/AutoBroadcast &e- Shows a list of commands for Auto-Broadcast."));
							player.sendMessage(Methods.color(" &f/BannedCommands &e- Shows a list of commands for Banned Commands."));
							player.sendMessage(Methods.color(" &f/ChatRadius &e- Shows a list of commands for Chat Radius."));
							player.sendMessage("");
							player.sendMessage(Methods.color(" &7Page 1/3. Type /Chatmanager help 2 to go to the next page."));
							player.sendMessage("");
							return true;
							}
						}
					}

					if (args[1].equalsIgnoreCase("2")) {
						if (args.length == 2) {
							if (ServerProtocol.isAtLeast(ServerProtocol.v1_9_R1) && ServerProtocol.isOlder(ServerProtocol.v1_17_R1)) {
								JSONMessage.create("").send(player);
								JSONMessage.create(" &3Chat Manager &f(v" + plugin.getDescription().getVersion() + ")").send(player);
								JSONMessage.create("").send(player);
								JSONMessage.create(" &6<> &f= Required Arguments").send(player);
								JSONMessage.create(" &2[] &f= Optional Arguments").send(player);
								JSONMessage.create("").send(player);
								JSONMessage.create(" &f/Announcement &6<message> &e- Broadcast an announcement to the server.")
										.tooltip("&f/Announcement &6<message> \n&7Broadcast an announcement to the server.").suggestCommand("/announcement <message>").send(player);
								JSONMessage.create(" &f/Broadcast &6<message> &e- Broadcast a message to the server.")
										.tooltip("&f/Broadcast &6<message> \n&7Broadcasts a message to the server.").suggestCommand("/broadcast <message>").send(player);
								JSONMessage.create(" &f/Clearchat &e- Clears chat for all the players on the server.")
										.tooltip("&f/ClearChat \n&7Clears chat for all the players on the server.").suggestCommand("/clearchat").send(player);
								JSONMessage.create(" &f/Colors &e- Shows a list of all the color codes.")
										.tooltip("&f/Colors \n&7Shows a list of all the color codes.").suggestCommand("/colors").send(player);
								JSONMessage.create(" &f/Commandspy &e- See all the commands players use.")
										.tooltip("&f/CommandSpy \n&7See all the commands players use.").suggestCommand("/commandspy").send(player);
								JSONMessage.create(" &f/Formats &e- Shows a list of all the format codes.")
										.tooltip("&f/Formats \n&7Shows a list of all the format codes.")
										.suggestCommand("/formats").send(player);
								JSONMessage.create(" &f/Message &6<player> <message> &e- Sends a player a private message.")
										.tooltip("&f/Message &6<player> <message> \n&7Sends a player a private message.").suggestCommand("/message <player> <message>").send(player);
								JSONMessage.create(" &f/Motd &e- Shows the servers message of the day.")
										.tooltip("&f/Motd \n&7Shows the servers message of the day.").suggestCommand("/motd").send(player);
								JSONMessage.create(" &f/Mutechat &e- Mutes the server chat preventing players from talking in chat.")
										.tooltip("&f/MuteChat \n&7Mutes the server chat preventing players from talking in chat.").suggestCommand("/mutechat").send(player);
								JSONMessage.create("").send(player);
								JSONMessage.create(" &7Page 2/3. Type /Chatmanager help 3 to go to the next page.").tooltip("&f/ChatManager help 3").suggestCommand("/chatmanager help 3").send(player);
								JSONMessage.create("").send(player);
								return true;
							} else {
								player.sendMessage("");
								player.sendMessage(Methods.color(" &3Chat Manager &f(v" + plugin.getDescription().getVersion() + ")"));
								player.sendMessage("");
								player.sendMessage(Methods.color(" &6<> &f= Required Arguments"));
								player.sendMessage(Methods.color(" &2[] &f= Optional Arguments"));
								player.sendMessage("");
								player.sendMessage(Methods.color(" &f/Announcement &6<message> &e- Broadcasts an announcement message to the server."));
								player.sendMessage(Methods.color(" &f/Broadcast &6<message> &e- Broadcasts a message to the server."));
								player.sendMessage(Methods.color(" &f/Clearchat &e- Clears global chat."));
								player.sendMessage(Methods.color(" &f/Colors &e- Shows a list of color codes."));
								player.sendMessage(Methods.color(" &f/Commandspy &e- Staff can see what commands every player types on the server."));
								player.sendMessage(Methods.color(" &f/Formats &e- Shows a list of format codes."));
								player.sendMessage(Methods.color(" &f/Message &6<player> <message> &e- Sends a player a private message."));
								player.sendMessage(Methods.color(" &f/Motd &e- Shows the servers MOTD."));
								player.sendMessage(Methods.color(" &f/Mutechat &e- Mutes the server chat preventing players from talking in chat."));
								player.sendMessage("");
								player.sendMessage(Methods.color(" &7Page 2/3. Type /Chatmanager help 3 to go to the next page."));
								player.sendMessage("");
								return true;
							}
						}
					}

					if (args[1].equalsIgnoreCase("3")) {
						if (args.length == 2) {
							if (ServerProtocol.isAtLeast(ServerProtocol.v1_9_R1) && ServerProtocol.isOlder(ServerProtocol.v1_17_R1)) {
								JSONMessage.create("").send(player);
								JSONMessage.create(" &3Chat Manager &f(v" + plugin.getDescription().getVersion() + ")").send(player);
								JSONMessage.create("").send(player);
								JSONMessage.create(" &6<> &f= Required Arguments").send(player);
								JSONMessage.create(" &2[] &f= Optional Arguments").send(player);
								JSONMessage.create("").send(player);
								JSONMessage.create(" &f/Perworldchat Bypass &e- Bypass the perworld chat feature.")
										.tooltip("&f/Perworldchat Bypass \n&7Bypass the perworld chat feature.").suggestCommand("/perworldchat bypass").send(player);
								JSONMessage.create(" &f/Ping &2[player] &e- Shows your current ping.")
										.tooltip("&f/Ping &2[player] \n&7Shows your current ping").suggestCommand("/ping [player]").send(player);
								JSONMessage.create(" &f/Reply &6<message> &e- Quickly reply to the last player that messaged you.")
										.tooltip("&f/Reply &6<message> \n&7Quickly reply to the last player that messaged you.").suggestCommand("/reply <message>").send(player);
								JSONMessage.create(" &f/Rules &e- Shows a list of the server rules.")
										.tooltip("&f/Rules \n&7Shows a list of the server rules").suggestCommand("/rules").send(player);
								JSONMessage.create(" &f/StaffChat &2[message] &e- Talk secretly to other staff members online.")
										.tooltip("&f/StaffChat &2[message] \n&7Talk secretly to other staff members online.").suggestCommand("/staffchat [message]").send(player);
								JSONMessage.create(" &f/Togglechat &e- Blocks a player from receiving chat messages.")
										.tooltip("&f/Togglechat \n&7Blocks a player from receiving chat messages.").suggestCommand("/togglechat").send(player);
								JSONMessage.create(" &f/Togglementions &e- Blocks a player from receiving mention notifications.")
										.tooltip("&f/Togglechat \n&7Blocks a player from receiving chat messages.").suggestCommand("/togglechat").send(player);
								JSONMessage.create(" &f/Togglepm &e- Blocks players from sending private messages to you.")
										.tooltip("&f/Togglepm \n&7Blocks players from sending private messages to you.").suggestCommand("/togglepm").send(player);
								JSONMessage.create(" &F/Warning &6<message> &e- Broadcasts a warning message to the server.")
										.tooltip("&f/Warning &6<message> \n&7Broadcasts a warning message to the server.")
										.suggestCommand("/warning <message>").send(player);
								JSONMessage.create("").send(player);
								JSONMessage.create(" &7Page 3/3. Type /Chatmanager help 2 to go to the previous page.").tooltip("&f/ChatManager help 2").suggestCommand("/chatmanager help 2").send(player);
								JSONMessage.create("").send(player);
								return true;
							} else {
								player.sendMessage("");
								player.sendMessage(Methods.color(" &3Chat Manager &f(v" + plugin.getDescription().getVersion() + ")"));
								player.sendMessage("");
								player.sendMessage(Methods.color(" &6<> &f= Required Arguments"));
								player.sendMessage(Methods.color(" &2[] &f= Optional Arguments"));
								player.sendMessage("");
								player.sendMessage(Methods.color(" &f/Perworldchat Bypass &e- Bypass the perworld chat feature."));
								player.sendMessage(Methods.color(" &f/Ping &2 [player] &e- Shows your current ping."));
								player.sendMessage(Methods.color(" &f/Reply &6<message> &e- Quickly reply to the last player to message you."));
								player.sendMessage(Methods.color(" &f/Rules &e- Shows a list of the server rules."));
								player.sendMessage(Methods.color(" &f/StaffChat &2[message] &e- Talk secretly to other staff members online."));
								player.sendMessage(Methods.color(" &f/Togglechat &e- Blocks a player from receiving chat messages."));
								player.sendMessage(Methods.color(" &f/Togglementions &e- Blocks a player from receiving mention notifications."));
								player.sendMessage(Methods.color(" &f/Togglepm &e- Blocks players from sending private messages to you."));
								player.sendMessage(Methods.color(" &f/Warning &6<message> &e - Broadcasts a warning message to the server."));
								player.sendMessage("");
								player.sendMessage(Methods.color(" &7Page 3/3. Type /Chatmanager help 2 to go to the previous page."));
								player.sendMessage("");
								return true;
							}
						}
					}
				}
			} else {
				sender.sendMessage("Error: You can only use that command in game");
			}
		}

		return true;
	}
}