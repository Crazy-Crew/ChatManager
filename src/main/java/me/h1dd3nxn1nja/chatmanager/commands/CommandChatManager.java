package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.enums.Files;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
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
				this.plugin.getMethods().sendMessage(sender, "&7This server is using the plugin &cChatManager &7version " + plugin.getDescription().getVersion() + " by &cH1DD3NxN1NJA.", true);
				this.plugin.getMethods().sendMessage(sender, "&7Commands: &c/chatmanager help", true);

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

							BossBarUtil bossBarStaff = new BossBarUtil(this.plugin.getMethods().color(config.getString("Staff_Chat.Boss_Bar.Title")));

							if (this.plugin.api().getStaffChatData().containsUser(player.getUniqueId()) && player.hasPermission("chatmanager.staffchat")) {
								bossBarStaff.removeStaffBossBar(player);
								bossBarStaff.setStaffBossBar(player);
							}
						}

						this.plugin.getFileManager().reloadFiles();

						this.plugin.getServer().getScheduler().cancelTasks(this.plugin);
						this.plugin.check();

						this.plugin.getMethods().sendMessage(sender, Files.MESSAGES.getConfiguration().getString("Message.Reload"), true);

					} else {
						this.plugin.getMethods().sendMessage(sender, "&cCommand Usage: &7/chatmanager reload", true);
					}
				} else {
					this.plugin.getMethods().sendMessage(sender, this.plugin.getMethods().noPermission(), true);
				}
			}

			if (args[0].equalsIgnoreCase("debug")) {
				if (!sender.hasPermission(Permissions.COMMAND_DEBUG.getNode())) {
					this.plugin.getMethods().sendMessage(sender, this.plugin.getMethods().noPermission(), true);
					return true;
				}

				if (args.length == 1) {
					this.plugin.getMethods().sendMessage(sender, "", true);
					this.plugin.getMethods().sendMessage(sender, "&3ChatManager Debug Help Menu &f(v" + plugin.getDescription().getVersion() + ")", true);
					this.plugin.getMethods().sendMessage(sender, "", true);
					this.plugin.getMethods().sendMessage(sender, " &f/chatmanager debug &e- Shows a list of commands to debug.", true);
					this.plugin.getMethods().sendMessage(sender, " &f/chatmanager debug all &e- Debugs all configuration files.", true);
					this.plugin.getMethods().sendMessage(sender, " &f/chatmanager debug autobroadcast &e- Debugs the autobroadcast.yml file.", true);
					this.plugin.getMethods().sendMessage(sender, " &f/chatmanager debug config &e- Debugs the config.yml file.", true);
					this.plugin.getMethods().sendMessage(sender, " &f/chatmanager debug messages &e- Debugs the messages.yml file", true);
					this.plugin.getMethods().sendMessage(sender, "", true);

					return true;
				}

				if (args[1].equalsIgnoreCase("all")) {
					if (sender.hasPermission(Permissions.COMMAND_DEBUG.getNode())) {
						if (args.length == 2) {
							this.plugin.getMethods().sendMessage(sender, "&7Debugging all configuration files, Please go to your console to see the debug low.", true);
							Debug.debugAutoBroadcast();
							Debug.debugConfig();
							Debug.debugMessages();
						} else {
							this.plugin.getMethods().sendMessage(sender, "&cCommand Usage: &7/chatmanager debug all", true);
						}
					} else {
						this.plugin.getMethods().sendMessage(sender, this.plugin.getMethods().noPermission(), true);
					}
				}

				if (args[1].equalsIgnoreCase("autobroadcast")) {
					if (sender.hasPermission(Permissions.COMMAND_DEBUG.getNode())) {
						if (args.length == 2) {
							this.plugin.getMethods().sendMessage(sender, "&7Debugging autobroadcast, Please go to your console to see the debug log.", true);
							Debug.debugAutoBroadcast();
						} else {
							this.plugin.getMethods().sendMessage(sender, "&cCommand Usage: &7/chatmanager debug autobroadcast", true);
						}
					} else {
						this.plugin.getMethods().sendMessage(sender, this.plugin.getMethods().noPermission(), true);
					}
				}

				if (args[1].equalsIgnoreCase("config")) {
					if (sender.hasPermission(Permissions.COMMAND_DEBUG.getNode())) {
						if (args.length == 2) {
							this.plugin.getMethods().sendMessage(sender, "&7Debugging config, Please go to your console to see the debug log.", true);
							Debug.debugConfig();
						} else {
							this.plugin.getMethods().sendMessage(sender, "&cCommand Usage: &7/chatmanager debug config", true);
						}
					} else {
						this.plugin.getMethods().sendMessage(sender, this.plugin.getMethods().noPermission(), true);
					}
				}

				if (args[1].equalsIgnoreCase("messages")) {
					if (sender.hasPermission(Permissions.COMMAND_DEBUG.getNode())) {
						if (args.length == 2) {
							this.plugin.getMethods().sendMessage(sender, "&7Debugging config, Please go to your console to see the debug log.", true);
							Debug.debugMessages();
						} else {
							this.plugin.getMethods().sendMessage(sender, "&cCommand Usage: &7/chatmanager debug messages", true);
						}
					} else {
						this.plugin.getMethods().sendMessage(sender, this.plugin.getMethods().noPermission(), true);
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
							this.plugin.getMethods().sendMessage(player, "", true);
							this.plugin.getMethods().sendMessage(player, "&3ChatManager &f(v" + plugin.getDescription().getVersion() + ")", true);
							this.plugin.getMethods().sendMessage(player, "", true);
							this.plugin.getMethods().sendMessage(player, "&6<> &f= Required Arguments", true);
							this.plugin.getMethods().sendMessage(player, "&2[] &f= Optional Arguments", true);
							this.plugin.getMethods().sendMessage(player, "", true);
							this.plugin.getMethods().sendMessage(player, "&f/announcement &6<message> &e- Broadcasts an announcement message to the server.", true);
							this.plugin.getMethods().sendMessage(player, "&f/broadcast &6<message> &e- Broadcasts a message to the server.", true);
							this.plugin.getMethods().sendMessage(player, "&f/clearchat &e- Clears global chat.", true);
							this.plugin.getMethods().sendMessage(player, "&f/colors &e- Shows a list of color codes.", true);
							this.plugin.getMethods().sendMessage(player, "&f/commandspy &e- Staff can see what commands every player types on the server.", true);
							this.plugin.getMethods().sendMessage(player, "&f/formats &e- Shows a list of format codes.", true);
							this.plugin.getMethods().sendMessage(player, "&f/message &6<player> <message> &e- Sends a player a private message.", true);
							this.plugin.getMethods().sendMessage(player, "&f/motd &e- Shows the servers MOTD.", true);
							this.plugin.getMethods().sendMessage(player, "&f/mutechat &e- Mutes the server chat preventing players from talking in chat.", true);
							this.plugin.getMethods().sendMessage(player, "", true);
							this.plugin.getMethods().sendMessage(player, "&7Page 2/3. Type /chatmanager help 3 to go to the next page.", true);
							this.plugin.getMethods().sendMessage(player, "", true);
							return true;
						}
					}

					if (args[1].equalsIgnoreCase("3")) {
						if (args.length == 2) {
							this.plugin.getMethods().sendMessage(player, "", true);
							this.plugin.getMethods().sendMessage(player, "&3ChatManager &f(v" + plugin.getDescription().getVersion() + ")", true);
							this.plugin.getMethods().sendMessage(player, "", true);
							this.plugin.getMethods().sendMessage(player, "&6<> &f= Required Arguments", true);
							this.plugin.getMethods().sendMessage(player, "&2[] &f= Optional Arguments", true);
							this.plugin.getMethods().sendMessage(player, "", true);
							this.plugin.getMethods().sendMessage(player, "&f/perworldchat bypass &e- Bypass the perworld chat feature.", true);
							this.plugin.getMethods().sendMessage(player, "&f/ping &2 [player] &e- Shows your current ping.", true);
							this.plugin.getMethods().sendMessage(player, "&f/reply &6<message> &e- Quickly reply to the last player to message you.", true);
							this.plugin.getMethods().sendMessage(player, "&f/rules &e- Shows a list of the server rules.", true);
							this.plugin.getMethods().sendMessage(player, "&f/staffchat &2[message] &e- Talk secretly to other staff members online.", true);
							this.plugin.getMethods().sendMessage(player, "&f/togglechat &e- Blocks a player from receiving chat messages.", true);
							this.plugin.getMethods().sendMessage(player, "&f/togglementions &e- Blocks a player from receiving mention notifications.", true);
							this.plugin.getMethods().sendMessage(player, "&f/togglepm &e- Blocks players from sending private messages to you.", true);
							this.plugin.getMethods().sendMessage(player, "&f/warning &6<message> &e - Broadcasts a warning message to the server.", true);
							this.plugin.getMethods().sendMessage(player, "", true);
							this.plugin.getMethods().sendMessage(player, "&7Page 3/3. Type /chatmanager help 2 to go to the previous page.", true);
							this.plugin.getMethods().sendMessage(player, "", true);
						}
					}
				}
			}
		}

		return true;
	}

	private boolean sendJsonMessage(Player player) {
		this.plugin.getMethods().sendMessage(player, "", true);
		this.plugin.getMethods().sendMessage(player, "&3ChatManager &f(v" + plugin.getDescription().getVersion() + ")", true);
		this.plugin.getMethods().sendMessage(player, "", true);
		this.plugin.getMethods().sendMessage(player, "&6<> &f= Required Arguments", true);
		this.plugin.getMethods().sendMessage(player, "", true);
		this.plugin.getMethods().sendMessage(player, "&f/chatmanager help &e- Help menu for chat manager.", true);
		this.plugin.getMethods().sendMessage(player, "&f/chatmanager reload &e- Reloads all the configuration files.", true);
		this.plugin.getMethods().sendMessage(player, "&f/chatmanager debug &e- Debugs all the configuration files.", true);
		this.plugin.getMethods().sendMessage(player, "&f/antiswear &6- Shows a list of commands for Anti Swear.", true);
		this.plugin.getMethods().sendMessage(player, "&f/autobroadcast &e- Shows a list of commands for Auto-Broadcast.", true);
		this.plugin.getMethods().sendMessage(player, "&f/bannedcommands &e- Shows a list of commands for Banned Commands.", true);
		this.plugin.getMethods().sendMessage(player, "&f/chatradius &e- Shows a list of commands for Chat Radius.", true);
		this.plugin.getMethods().sendMessage(player, "", true);
		this.plugin.getMethods().sendMessage(player, "&7Page 1/3. Type /chatmanager help 2 to go to the next page.", true);
		this.plugin.getMethods().sendMessage(player, "", true);

		return true;
	}
}