package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import me.h1dd3nxn1nja.chatmanager.utils.Debug;
import org.jetbrains.annotations.NotNull;

public class CommandChatManager implements CommandExecutor {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		FileConfiguration config = Files.CONFIG.getConfiguration();
		if (cmd.getName().equalsIgnoreCase("ChatManager")) {
			if (args.length == 0) {
				Methods.sendMessage(sender, "&7This server is using the plugin &cChatManager &7version " + plugin.getDescription().getVersion() + " by &cH1DD3NxN1NJA.", true);
				Methods.sendMessage(sender, "&7Commands: &c/chatmanager help", true);

				return true;
			}

			if (args[0].equalsIgnoreCase("reload")) {
				if (sender.hasPermission(Permissions.COMMAND_RELOAD.getNode())) {
					if (args.length == 1) {
						for (Player player : this.plugin.getServer().getOnlinePlayers()) {
							this.plugin.api().getChatCooldowns().removeUser(player.getUniqueId());
							this.plugin.api().getCooldownTask().removeUser(player.getUniqueId());
							this.plugin.api().getCmdCooldowns().removeUser(player.getUniqueId());

							BossBarUtil bossBar = new BossBarUtil();
							bossBar.removeAllBossBars(player);

							BossBarUtil bossBarStaff = new BossBarUtil(Methods.placeholders(true, player, Methods.color(config.getString("Staff_Chat.Boss_Bar.Title", "&eStaff Chat"))));

							if (this.plugin.api().getStaffChatData().containsUser(player.getUniqueId()) && player.hasPermission("chatmanager.staffchat")) {
								bossBarStaff.removeStaffBossBar(player);
								bossBarStaff.setStaffBossBar(player);
							}
						}

						this.plugin.getFileManager().init();

						Files.CONFIG.reload();
						Files.MESSAGES.reload();
						Files.BANNED_COMMANDS.reload();
						Files.BANNED_WORDS.reload();
						Files.AUTO_BROADCAST.reload();

						this.plugin.getServer().getGlobalRegionScheduler().cancelTasks(this.plugin);
						this.plugin.getServer().getAsyncScheduler().cancelTasks(this.plugin);
						this.plugin.check();

						Messages.PLUGIN_RELOAD.sendMessage(sender);
					} else {
						Methods.sendMessage(sender, "&cCommand Usage: &7/chatmanager reload", true);
					}
				} else {
					Messages.NO_PERMISSION.sendMessage(sender);
				}
			}

			if (args[0].equalsIgnoreCase("debug")) {
				if (!sender.hasPermission(Permissions.COMMAND_DEBUG.getNode())) {
					Messages.NO_PERMISSION.sendMessage(sender);

					return true;
				}

				if (args.length == 1) {
					Methods.sendMessage(sender, "", true);
					Methods.sendMessage(sender, "&3ChatManager Debug Help Menu &f(v" + plugin.getDescription().getVersion() + ")", true);
					Methods.sendMessage(sender, "", true);
					Methods.sendMessage(sender, " &f/chatmanager debug &e- Shows a list of commands to debug.", true);
					Methods.sendMessage(sender, " &f/chatmanager debug all &e- Debugs all configuration files.", true);
					Methods.sendMessage(sender, " &f/chatmanager debug autobroadcast &e- Debugs the autobroadcast.yml file.", true);
					Methods.sendMessage(sender, " &f/chatmanager debug config &e- Debugs the config.yml file.", true);
					Methods.sendMessage(sender, " &f/chatmanager debug messages &e- Debugs the messages.yml file", true);
					Methods.sendMessage(sender, "", true);

					return true;
				}

				if (args[1].equalsIgnoreCase("all")) {
					if (sender.hasPermission(Permissions.COMMAND_DEBUG.getNode())) {
						if (args.length == 2) {
							Methods.sendMessage(sender, "&7Debugging all configuration files, Please go to your console to see the debug low.", true);

							Debug.debugAutoBroadcast();
							Debug.debugConfig();
							Debug.debugMessages();
						} else {
							Methods.sendMessage(sender, "&cCommand Usage: &7/chatmanager debug all", true);
						}
					} else {
						Messages.NO_PERMISSION.sendMessage(sender);
					}
				}

				if (args[1].equalsIgnoreCase("autobroadcast")) {
					if (sender.hasPermission(Permissions.COMMAND_DEBUG.getNode())) {
						if (args.length == 2) {
							Methods.sendMessage(sender, "&7Debugging autobroadcast, Please go to your console to see the debug log.", true);

							Debug.debugAutoBroadcast();
						} else {
							Methods.sendMessage(sender, "&cCommand Usage: &7/chatmanager debug autobroadcast", true);
						}
					} else {
						Messages.NO_PERMISSION.sendMessage(sender);
					}
				}

				if (args[1].equalsIgnoreCase("config")) {
					if (sender.hasPermission(Permissions.COMMAND_DEBUG.getNode())) {
						if (args.length == 2) {
							Methods.sendMessage(sender, "&7Debugging config, Please go to your console to see the debug log.", true);

							Debug.debugConfig();
						} else {
							Methods.sendMessage(sender, "&cCommand Usage: &7/chatmanager debug config", true);
						}
					} else {
						Messages.NO_PERMISSION.sendMessage(sender);
					}
				}

				if (args[1].equalsIgnoreCase("messages")) {
					if (sender.hasPermission(Permissions.COMMAND_DEBUG.getNode())) {
						if (args.length == 2) {
							Methods.sendMessage(sender, "&7Debugging config, Please go to your console to see the debug log.", true);

							Debug.debugMessages();
						} else {
							Methods.sendMessage(sender, "&cCommand Usage: &7/chatmanager debug messages", true);
						}
					} else {
						Messages.NO_PERMISSION.sendMessage(sender);
					}
				}
			}

			if (sender instanceof Player player) {
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
							Methods.sendMessage(player, "&f/announcement &6<message> &e- Broadcasts an announcement message to the server.", true);
							Methods.sendMessage(player, "&f/broadcast &6<message> &e- Broadcasts a message to the server.", true);
							Methods.sendMessage(player, "&f/clearchat &e- Clears global chat.", true);
							Methods.sendMessage(player, "&f/colors &e- Shows a list of color codes.", true);
							Methods.sendMessage(player, "&f/commandspy &e- Staff can see what commands every player types on the server.", true);
							Methods.sendMessage(player, "&f/formats &e- Shows a list of format codes.", true);
							Methods.sendMessage(player, "&f/message &6<player> <message> &e- Sends a player a private message.", true);
							Methods.sendMessage(player, "&f/motd &e- Shows the servers MOTD.", true);
							Methods.sendMessage(player, "&f/mutechat &e- Mutes the server chat preventing players from talking in chat.", true);
							Methods.sendMessage(player, "", true);
							Methods.sendMessage(player, "&7Page 2/3. Type /chatmanager help 3 to go to the next page.", true);
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
							Methods.sendMessage(player, "&f/perworldchat bypass &e- Bypass the perworld chat feature.", true);
							Methods.sendMessage(player, "&f/ping &2 [player] &e- Shows your current ping.", true);
							Methods.sendMessage(player, "&f/reply &6<message> &e- Quickly reply to the last player to message you.", true);
							Methods.sendMessage(player, "&f/rules &e- Shows a list of the server rules.", true);
							Methods.sendMessage(player, "&f/staffchat &2[message] &e- Talk secretly to other staff members online.", true);
							Methods.sendMessage(player, "&f/togglechat &e- Blocks a player from receiving chat messages.", true);
							Methods.sendMessage(player, "&f/togglementions &e- Blocks a player from receiving mention notifications.", true);
							Methods.sendMessage(player, "&f/togglepm &e- Blocks players from sending private messages to you.", true);
							Methods.sendMessage(player, "&f/warning &6<message> &e - Broadcasts a warning message to the server.", true);
							Methods.sendMessage(player, "", true);
							Methods.sendMessage(player, "&7Page 3/3. Type /chatmanager help 2 to go to the previous page.", true);
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
		Methods.sendMessage(player, "&f/chatmanager help &e- Help menu for chat manager.", true);
		Methods.sendMessage(player, "&f/chatmanager reload &e- Reloads all the configuration files.", true);
		Methods.sendMessage(player, "&f/chatmanager debug &e- Debugs all the configuration files.", true);
		Methods.sendMessage(player, "&f/antiswear &6- Shows a list of commands for Anti Swear.", true);
		Methods.sendMessage(player, "&f/autobroadcast &e- Shows a list of commands for Auto-Broadcast.", true);
		Methods.sendMessage(player, "&f/bannedcommands &e- Shows a list of commands for Banned Commands.", true);
		Methods.sendMessage(player, "&f/chatradius &e- Shows a list of commands for Chat Radius.", true);
		Methods.sendMessage(player, "", true);
		Methods.sendMessage(player, "&7Page 1/3. Type /chatmanager help 2 to go to the next page.", true);
		Methods.sendMessage(player, "", true);

		return true;
	}
}