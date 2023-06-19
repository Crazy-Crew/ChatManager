package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.managers.AutoBroadcastManager;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import me.h1dd3nxn1nja.chatmanager.utils.Debug;
import org.jetbrains.annotations.NotNull;

public class CommandChatManager implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		FileConfiguration messages = settingsManager.getMessages();

		if (cmd.getName().equalsIgnoreCase("ChatManager")) {
			if (args.length == 0) {
				Methods.sendMessage(sender, "&7This server is using the plugin &cChatManager &7version " + plugin.getDescription().getVersion() + " by &cH1DD3NxN1NJA.", true);
				Methods.sendMessage(sender, "&7Commands: &c/Chatmanager help", true);

				return true;
			}

			if (args[0].equalsIgnoreCase("reload")) {
				if (sender.hasPermission("chatmanager.reload")) {
					if (args.length == 1) {
						for (Player player : plugin.getServer().getOnlinePlayers()) {
							plugin.api().getChatCooldowns().removeUser(player.getUniqueId());
							plugin.api().getCooldownTask().removeUser(player.getUniqueId());
							plugin.api().getCmdCooldowns().removeUser(player.getUniqueId());

							BossBarUtil bossBar = new BossBarUtil();
							bossBar.removeAllBossBars(player);
						}

						settingsManager.reloadConfig();
						settingsManager.reloadMessages();
						settingsManager.reloadAutoBroadcast();
						settingsManager.reloadBannedCommands();
						settingsManager.reloadBannedWords();
						settingsManager.setup();

						plugin.getServer().getScheduler().cancelTasks(plugin);

						try {
							if (settingsManager.getAutoBroadcast().getBoolean("Auto_Broadcast.Actionbar_Messages.Enable"))
								AutoBroadcastManager.actionbarMessages();
							if (settingsManager.getAutoBroadcast().getBoolean("Auto_Broadcast.Global_Messages.Enable"))
								AutoBroadcastManager.globalMessages();
							if (settingsManager.getAutoBroadcast().getBoolean("Auto_Broadcast.Per_World_Messages.Enable"))
								AutoBroadcastManager.perWorldMessages();
							if (settingsManager.getAutoBroadcast().getBoolean("Auto_Broadcast.Title_Messages.Enable"))
								AutoBroadcastManager.titleMessages();
							if (settingsManager.getAutoBroadcast().getBoolean("Auto_Broadcast.Bossbar_Messages.Enable"))
								AutoBroadcastManager.bossBarMessages();
						} catch (Exception e) {
							Methods.tellConsole("There was an error setting up auto broadcast. Stack-trace:", true);
							e.printStackTrace();
						}

						Methods.sendMessage(sender, messages.getString("Message.Reload"), true);

					} else {
						Methods.sendMessage(sender, "&cCommand Usage: &7/Chatmanager reload", true);
					}
				} else {
					Methods.sendMessage(sender, Methods.noPermission(), true);
				}
			}

			if (args[0].equalsIgnoreCase("debug")) {
				if (!sender.hasPermission("chatmanager.debug")) {
					Methods.sendMessage(sender, Methods.noPermission(), true);
					return true;
				}

				if (args.length == 1) {

					Methods.sendMessage(sender, "", true);
					Methods.sendMessage(sender, "&3ChatManager Debug Help Menu &f(v" + plugin.getDescription().getVersion() + ")", true);
					Methods.sendMessage(sender, "", true);
					Methods.sendMessage(sender, " &f/Chatmanager Debug &e- Shows a list of commands to debug.", true);
					Methods.sendMessage(sender, " &f/Chatmanager Debug All &e- Debugs all configuration files.", true);
					Methods.sendMessage(sender, " &f/Chatmanager Debug AutoBroadcast &e- Debugs the autobroadcast.yml file.", true);
					Methods.sendMessage(sender, " &f/Chatmanager Debug Config &e- Debugs the config.yml file.", true);
					Methods.sendMessage(sender, " &f/Chatmanager Debug Messages &e- Debugs the messages.yml file", true);
					Methods.sendMessage(sender, "", true);

					return true;
				}

				if (args[1].equalsIgnoreCase("all")) {
					if (sender.hasPermission("chatmanager.debug")) {
						if (args.length == 2) {
							Methods.sendMessage(sender, "&7Debugging all configuration files, Please go to your console to see the debug low.", true);
							Debug.debugAutoBroadcast();
							Debug.debugConfig();
							Debug.debugMessages();
						} else {
							Methods.sendMessage(sender, "&cCommand Usage: &7/Chatmanager debug all", true);
						}
					} else {
						Methods.sendMessage(sender, Methods.noPermission(), true);
					}
				}

				if (args[1].equalsIgnoreCase("autobroadcast")) {
					if (sender.hasPermission("chatmanager.debug")) {
						if (args.length == 2) {
							Methods.sendMessage(sender, "&7Debugging autobroadcast, Please go to your console to see the debug log.", true);
							Debug.debugAutoBroadcast();
						} else {
							Methods.sendMessage(sender, "&cCommand Usage: &7/Chatmanager debug autobroadcast", true);
						}
					} else {
						Methods.sendMessage(sender, Methods.noPermission(), true);
					}
				}

				if (args[1].equalsIgnoreCase("config")) {
					if (sender.hasPermission("chatmanager.debug")) {
						if (args.length == 2) {
							Methods.sendMessage(sender, "&7Debugging config, Please go to your console to see the debug log.", true);
							Debug.debugConfig();
						} else {
							Methods.sendMessage(sender, "&cCommand Usage: &7/Chatmanager debug config", true);
						}
					} else {
						Methods.sendMessage(sender, Methods.noPermission(), true);
					}
				}

				if (args[1].equalsIgnoreCase("messages")) {
					if (sender.hasPermission("chatmanager.debug")) {
						if (args.length == 2) {
							Methods.sendMessage(sender, "&7Debugging config, Please go to your console to see the debug log.", true);
							Debug.debugMessages();
						} else {
							Methods.sendMessage(sender, "&cCommand Usage: &7/Chatmanager debug messages", true);
						}
					} else {
						Methods.sendMessage(sender, Methods.noPermission(), true);
					}
				}
			}

			if (sender instanceof Player) {
				Player player = (Player) sender;

				if (args[0].equalsIgnoreCase("help")) {
					if (args.length == 1) return sendJsonMessage(player);

					if (args[1].equalsIgnoreCase("1")) {
						if (args.length == 2) return sendJsonMessage(player);
					}

					if (args[1].equalsIgnoreCase("2")) {
						if (args.length == 2) {
							Methods.sendMessage(player, "", true);
							Methods.sendMessage(player, "&3ChatManager &f(v" + plugin.getDescription().getVersion() + ")", true);
							Methods.sendMessage(player, "", true);
							Methods.sendMessage(player, "&6<> &f= Required Arguments", true);
							Methods.sendMessage(player, "&2[] &f= Optional Arguments", true);
							Methods.sendMessage(player, "", true);
							Methods.sendMessage(player, "&f/Announcement &6<message> &e- Broadcasts an announcement message to the server.", true);
							Methods.sendMessage(player, "&f/Broadcast &6<message> &e- Broadcasts a message to the server.", true);
							Methods.sendMessage(player, "&f/Clearchat &e- Clears global chat.", true);
							Methods.sendMessage(player, "&f/Colors &e- Shows a list of color codes.", true);
							Methods.sendMessage(player, "&f/Commandspy &e- Staff can see what commands every player types on the server.", true);
							Methods.sendMessage(player, "&f/Formats &e- Shows a list of format codes.", true);
							Methods.sendMessage(player, "&f/Message &6<player> <message> &e- Sends a player a private message.", true);
							Methods.sendMessage(player, "&f/Motd &e- Shows the servers MOTD.", true);
							Methods.sendMessage(player, "&f/Mutechat &e- Mutes the server chat preventing players from talking in chat.", true);
							Methods.sendMessage(player, "", true);
							Methods.sendMessage(player, "&7Page 2/3. Type /Chatmanager help 3 to go to the next page.", true);
							Methods.sendMessage(player, "", true);
							return true;
						}
					}

					if (args[1].equalsIgnoreCase("3")) {
						if (args.length == 2) {
							Methods.sendMessage(player, "", true);
							Methods.sendMessage(player, "&3ChatManager &f(v" + plugin.getDescription().getVersion() + ")", true);
							Methods.sendMessage(player, "", true);
							Methods.sendMessage(player, "&6<> &f= Required Arguments", true);
							Methods.sendMessage(player, "&2[] &f= Optional Arguments", true);
							Methods.sendMessage(player, "", true);
							Methods.sendMessage(player, "&f/Perworldchat Bypass &e- Bypass the perworld chat feature.", true);
							Methods.sendMessage(player, "&f/Ping &2 [player] &e- Shows your current ping.", true);
							Methods.sendMessage(player, "&f/Reply &6<message> &e- Quickly reply to the last player to message you.", true);
							Methods.sendMessage(player, "&f/Rules &e- Shows a list of the server rules.", true);
							Methods.sendMessage(player, "&f/StaffChat &2[message] &e- Talk secretly to other staff members online.", true);
							Methods.sendMessage(player, "&f/Togglechat &e- Blocks a player from receiving chat messages.", true);
							Methods.sendMessage(player, "&f/Togglementions &e- Blocks a player from receiving mention notifications.", true);
							Methods.sendMessage(player, "&f/Togglepm &e- Blocks players from sending private messages to you.", true);
							Methods.sendMessage(player, "&f/Warning &6<message> &e - Broadcasts a warning message to the server.", true);
							Methods.sendMessage(player, "", true);
							Methods.sendMessage(player, "&7Page 3/3. Type /Chatmanager help 2 to go to the previous page.", true);
							Methods.sendMessage(player, "", true);
						}
					}
				}
			}
		}

		return true;
	}

	private boolean sendJsonMessage(Player player) {
		Methods.sendMessage(player, "", true);
		Methods.sendMessage(player, "&3ChatManager &f(v" + plugin.getDescription().getVersion() + ")", true);
		Methods.sendMessage(player, "", true);
		Methods.sendMessage(player, "&6<> &f= Required Arguments", true);
		Methods.sendMessage(player, "", true);
		Methods.sendMessage(player, "&f/Chatmanager Help &e- Help menu for chat manager.", true);
		Methods.sendMessage(player, "&f/Chatmanager Reload &e- Reloads all the configuration files.", true);
		Methods.sendMessage(player, "&f/Chatmanager Debug &e- Debugs all the configuration files.", true);
		Methods.sendMessage(player, "&f/AntiSwear &6- Shows a list of commands for Anti Swear.", true);
		Methods.sendMessage(player, "&f/AutoBroadcast &e- Shows a list of commands for Auto-Broadcast.", true);
		Methods.sendMessage(player, "&f/BannedCommands &e- Shows a list of commands for Banned Commands.", true);
		Methods.sendMessage(player, "&f/ChatRadius &e- Shows a list of commands for Chat Radius.", true);
		Methods.sendMessage(player, "", true);
		Methods.sendMessage(player, "&7Page 1/3. Type /Chatmanager help 2 to go to the next page.", true);
		Methods.sendMessage(player, "", true);

		return true;
	}
}