package me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class ListenerAntiBot implements Listener {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		FileConfiguration config = settingsManager.getConfig();

		if (config.getBoolean("Anti_Bot.Block_Chat_Until_Moved")) {
			if (!player.hasPermission("chatmanager.bypass.antibot")) Methods.cm_antiBot.add(player.getUniqueId());
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		FileConfiguration config = settingsManager.getConfig();

		if (config.getBoolean("Anti_Bot.Block_Chat_Until_Moved")) {
			if (!player.hasPermission("chatmanager.bypass.antibot")) {
				if (Methods.cm_antiBot.contains(player.getUniqueId())) Methods.cm_antiBot.remove(player.getUniqueId());
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();

		FileConfiguration config = settingsManager.getConfig();

		if (config.getBoolean("Anti_Bot.Block_Chat_Until_Moved")) {
			if (!player.hasPermission("chatmanager.bypass.antibot")) {
				if (Methods.cm_antiBot.contains(player.getUniqueId())) Methods.cm_antiBot.remove(player.getUniqueId());
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		FileConfiguration config = settingsManager.getConfig();
		FileConfiguration messages = settingsManager.getMessages();

		if (config.getBoolean("Anti_Bot.Block_Chat_Until_Moved")) {
			if (!player.hasPermission("chatmanager.bypass.antibot")) {
				if (Methods.cm_antiBot.contains(player.getUniqueId())) {
					event.setCancelled(true);
					player.sendMessage(Methods.color(player, messages.getString("Anti_Bot.Deny_Chat_Message").replace("{Prefix}", messages.getString("Message.Prefix"))));
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();

		FileConfiguration config = settingsManager.getConfig();
		FileConfiguration messages = settingsManager.getMessages();

		if (config.getBoolean("Anti_Bot.Block_Commands_Until_Moved")) {
			if (!player.hasPermission("chatmanager.bypass.antibot")) {
				if (Methods.cm_antiBot.contains(player.getUniqueId())) {
					event.setCancelled(true);
					player.sendMessage(Methods.color(player, messages.getString("Anti_Bot.Deny_Command_Message").replace("{Prefix}", messages.getString("Message.Prefix"))));
				}
			}
		}
	}
}