package me.h1dd3nxn1nja.chatmanager.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import me.h1dd3nxn1nja.chatmanager.utils.JSONMessage;
import me.h1dd3nxn1nja.chatmanager.utils.UpdateChecker;
import me.h1dd3nxn1nja.chatmanager.utils.Version;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class ListenerPlayerJoin implements Listener {

	private ChatManager plugin;

	public ListenerPlayerJoin(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void firstJoinMessage(PlayerJoinEvent event) {
		
		FileConfiguration config = ChatManager.settings.getConfig();
		
		Player player = event.getPlayer();
		
		if (!player.hasPlayedBefore()) {
			if (config.getBoolean("Messages.First_Join.Welcome_Message.Enable")) {
				String message = config.getString("Messages.First_Join.Welcome_Message.First_Join_Message");
				event.setJoinMessage(PlaceholderManager.setPlaceholders(player, message));
				for (Player online : Bukkit.getServer().getOnlinePlayers()) {
					try {
						online.playSound(online.getLocation(), Sound.valueOf(config.getString("Messages.First_Join.Welcome_Message.Sound")), 10, 1);
					} catch (IllegalArgumentException ignored) {
					}
				}
			}
			if (config.getBoolean("Messages.First_Join.Actionbar_Message.Enable")) {
				String message = config.getString("Messages.First_Join.Actionbar_Message.First_Join_Message");
				if (Version.getCurrentVersion().isNewer(Version.v1_15_R2)) {
					player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(PlaceholderManager.setPlaceholders(player, message)));
				} else {
					JSONMessage.create().actionbar(PlaceholderManager.setPlaceholders(player, message), player);
				}
			}
			if (Version.getCurrentVersion().isNewer(Version.v1_8_R2)) {
				if (config.getBoolean("Messages.First_Join.Title_Message.Enable")) {
					int fadeIn = config.getInt("Messages.First_Join.Title_Message.Fade_In");
					int stay = config.getInt("Messages.First_Join.Title_Message.Stay");
					int fadeOut = config.getInt("Messages.First_Join.Title_Message.Fade_Out");
					String header = PlaceholderManager.setPlaceholders(player, config.getString("Messages.First_Join.Title_Message.First_Join_Message.Header"));
					String footer = PlaceholderManager.setPlaceholders(player, config.getString("Messages.First_Join.Title_Message.First_Join_Message.Footer"));
					if (Version.getCurrentVersion().isNewer(Version.v1_15_R2)) {
						player.sendTitle(header, footer, fadeIn, stay, fadeOut);
					} else {
						JSONMessage.create(header).title(fadeIn, stay, fadeOut, player);
						JSONMessage.create(footer).subtitle(player);
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void JoinMessage(PlayerJoinEvent event) {
		
		FileConfiguration config = ChatManager.settings.getConfig();
		
		Player player = event.getPlayer();
		
		if (player.hasPlayedBefore()) {
			if ((config.getBoolean("Messages.Join_Quit_Messages.Join_Message.Enable") == true) 
					&& (config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable")) == false) {
				String message = config.getString("Messages.Join_Quit_Messages.Join_Message.Message");
				event.setJoinMessage(PlaceholderManager.setPlaceholders(player, message));
				for (Player online : Bukkit.getServer().getOnlinePlayers()) {
					try {
						online.playSound(online.getLocation(), Sound.valueOf(config.getString("Messages.Join_Quit_Messages.Join_Message.Sound")), 10, 1);
					} catch (IllegalArgumentException ignored) {
					}
				}
			}
			if ((config.getBoolean("Messages.Join_Quit_Messages.Actionbar_Message.Enable") == true)
					&& (config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable")) == false) {
				String message = config.getString("Messages.Join_Quit_Messages.Actionbar_Message.Message");
				if (Version.getCurrentVersion().isNewer(Version.v1_15_R2)) {
					player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(PlaceholderManager.setPlaceholders(player, message)));
				} else {
					JSONMessage.create().actionbar(PlaceholderManager.setPlaceholders(player, message), player);
				}
			}
			if (Version.getCurrentVersion().isNewer(Version.v1_8_R2)) {
				if ((config.getBoolean("Messages.Join_Quit_Messages.Title_Message.Enable") == true)
						&& (config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable")) == false) {
					int fadeIn = config.getInt("Messages.Join_Quit_Messages.Title_Message.Fade_In");
					int stay = config.getInt("Messages.Join_Quit_Messages.Title_Message.Stay");
					int fadeOut = config.getInt("Messages.Join_Quit_Messages.Title_Message.Fade_Out");
					String header = PlaceholderManager.setPlaceholders(player, config.getString("Messages.Join_Quit_Messages.Title_Message.Message.Header"));
					String footer = PlaceholderManager.setPlaceholders(player, config.getString("Messages.Join_Quit_Messages.Title_Message.Message.Footer"));
					if (Version.getCurrentVersion().isNewer(Version.v1_15_R2)) {
						player.sendTitle(header, footer, fadeIn, stay, fadeOut);
					} else {
						JSONMessage.create(header).title(fadeIn, stay, fadeOut, player);
						JSONMessage.create(footer).subtitle(player);
					}
				}
			}
			if (config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable")) {
				for (String key : config.getConfigurationSection("Messages.Join_Quit_Messages.Group_Messages").getKeys(false)) {
					String permission = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Permission");
					String joinMessage = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Join_Message");
					String actionbarMessage = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Actionbar");
					String titleHeader = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Title.Header");
					String titleFooter = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Title.Footer");
					int fadeIn = config.getInt("Messages.Join_Quit_Messages.Title_Message.Fade_In");
					int stay = config.getInt("Messages.Join_Quit_Messages.Title_Message.Stay");
					int fadeOut = config.getInt("Messages.Join_Quit_Messages.Title_Message.Fade_Out");
					String sound = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Sound");
					if (permission != null && player.hasPermission(permission)) {
						if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Join_Message")) {
							try {
							event.setJoinMessage(PlaceholderManager.setPlaceholders(player, joinMessage));
							} catch (NullPointerException ex) {
								ex.printStackTrace();
							}
						}
						if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Actionbar")) {
							try {
								if (Version.getCurrentVersion().isNewer(Version.v1_15_R2)) {
									player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(PlaceholderManager.setPlaceholders(player, actionbarMessage)));
								} else {
									JSONMessage.create().actionbar(PlaceholderManager.setPlaceholders(player, actionbarMessage), player);
								}
							} catch (NullPointerException ex) {
								ex.printStackTrace();
							}
						}
						if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Title")) {
							try {
								if (Version.getCurrentVersion().isNewer(Version.v1_15_R2)) {
									player.sendTitle(PlaceholderManager.setPlaceholders(player, titleHeader), PlaceholderManager.setPlaceholders(player, titleFooter), fadeIn, stay, fadeOut);
								} else {
									JSONMessage.create(PlaceholderManager.setPlaceholders(player, titleHeader)).title(fadeIn, stay, fadeOut, player);
									JSONMessage.create(PlaceholderManager.setPlaceholders(player, titleFooter)).subtitle(player);
								}
							} catch (NullPointerException ex) {
								ex.printStackTrace();
							}
						}
						if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Sound")) {
							for (Player online : Bukkit.getServer().getOnlinePlayers()) {
								try {
									online.playSound(online.getLocation(), Sound.valueOf(sound), 10, 1);
								} catch (IllegalArgumentException ignored) {
								}
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent event) {

		FileConfiguration config = ChatManager.settings.getConfig();

		Player player = event.getPlayer();
		
		if ((config.getBoolean("Messages.Join_Quit_Messages.Quit_Message.Enable") == true) 
				&& (config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable")) == false) {
			String message = config.getString("Messages.Join_Quit_Messages.Quit_Message.Message");
			event.setQuitMessage(PlaceholderManager.setPlaceholders(player, message));
			for (Player online : Bukkit.getServer().getOnlinePlayers()) {
				try {
					online.playSound(online.getLocation(), Sound.valueOf(config.getString("Messages.Join_Quit_Messages.Quit_Message.Sound")), 10, 1);
				} catch (IllegalArgumentException ignored) {
				}
			}
		}
		if (config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable")) {
			for (String key : config.getConfigurationSection("Messages.Join_Quit_Messages.Group_Messages").getKeys(false)) {
				String permission = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Permission");
				String quitMessage = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Quit_Message");
				if (permission != null && player.hasPermission(permission)) {
					if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Quit_Message")) {
						event.setQuitMessage(PlaceholderManager.setPlaceholders(player, quitMessage));
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {

		FileConfiguration config = ChatManager.settings.getConfig();

		Player player = event.getPlayer();
		int lines = config.getInt("Clear_Chat.Broadcasted_Lines");
		int delay = config.getInt("MOTD.Delay");
		String version = Methods.getPlugin().getDescription().getVersion();
		
		if (config.getBoolean("Clear_Chat.Clear_On_Join")) {
			if (!player.hasPermission("chatmanager.bypass.clearchat.onjoin")) {
				for (int i = 0; i < lines; i++) {
					player.sendMessage("");
				}
			}
		}
		
		if (config.getBoolean("Social_Spy.Enable_On_Join")) {
			if (player.hasPermission("chatmanager.socialspy")) {
				Methods.cm_socialSpy.add(player.getUniqueId());
			}
		}
		
		if (config.getBoolean("Command_Spy.Enable_On_Join")) {
			if (player.hasPermission("chatmanager.commandspy")) {
				Methods.cm_commandSpy.add(player.getUniqueId());
			}
		}
		
		if (config.getBoolean("Chat_Radius.Enable")) {
			if (config.getString("Chat_Radius.Default_Channel").equals("Local")) {
				Methods.cm_localChat.add(player.getUniqueId());
			}
			if (config.getString("Chat_Radius.Default_Channel").equals("Global")) {
				Methods.cm_globalChat.add(player.getUniqueId());
			}
			if (config.getString("Chat_Radius.Default_Channel").equals("World")) {
				Methods.cm_worldChat.add(player.getUniqueId());
			}
		}
		
		if (config.getBoolean("Chat_Radius.Enable")) {
			if (config.getBoolean("Chat_Radius.Enable_Spy_On_Join")) {
				if (player.hasPermission("chatmanager.chatradius.spy")) {
					Methods.cm_spyChat.add(player.getUniqueId());
				}
			}
		}
		
		new BukkitRunnable() {
			public void run() {
				if (config.getBoolean("MOTD.Enable")) {
					for (String motd : config.getStringList("MOTD.Message")) {
						player.sendMessage(PlaceholderManager.setPlaceholders(player, motd));
					}
				}
				if (player.getName().equals("H1DD3NxNINJA") || player.getName().equals("H1DD3NxN1NJ4")) {
					player.sendMessage("");
					player.sendMessage(Methods.color(Methods.getPrefix() + " &7This server is using the plugin &cChat Manager &7version &c" + version + "&7."));
					player.sendMessage("");
				}
				if (ChatManager.settings.getConfig().getBoolean("Update_Checker")) {
					if (player.isOp()) {
						UpdateChecker updater = new UpdateChecker(plugin, 52245);
						try {
							if (updater.checkForUpdates()) {
								player.sendMessage("");
								player.sendMessage(Methods.color(Methods.getPrefix() + " &7An update is available for &cChatManager&7!"));
								player.sendMessage(Methods.color("&7Your server is running &cv" + version + "&7 and the newest version is &cv" + UpdateChecker.getLatestVersion() + "&7!"));
								player.sendMessage(Methods.color("&7Download: &c" + UpdateChecker.getResourceURL()));
								player.sendMessage("");
							}
						} catch (Exception ex) {
						}
					}
				}
			}
		}.runTaskLaterAsynchronously(plugin, 20L * delay);
	}
}