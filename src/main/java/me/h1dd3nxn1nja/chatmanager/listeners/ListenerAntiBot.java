package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.api.configs.types.ConfigSettings;
import com.ryderbelserion.chatmanager.api.interfaces.Universal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class ListenerAntiBot implements Listener, Universal {

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		if (!configSettings.getProperty(ConfigSettings.BLOCK_CHAT_UNTIL_MOVED) || player.hasPermission("chatmanager.bypass.antibot")) return;

		cacheManager.getAntiBotCache().addUser(player.getUniqueId());
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		if (!configSettings.getProperty(ConfigSettings.BLOCK_CHAT_UNTIL_MOVED) || player.hasPermission("chatmanager.bypass.antibot")) return;

		cacheManager.getAntiBotCache().removeUser(player.getUniqueId());
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();

		if (!configSettings.getProperty(ConfigSettings.BLOCK_CHAT_UNTIL_MOVED) || player.hasPermission("chatmanager.bypass.antibot")) return;

		cacheManager.getAntiBotCache().removeUser(player.getUniqueId());
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		if (!configSettings.getProperty(ConfigSettings.BLOCK_CHAT_UNTIL_MOVED) || player.hasPermission("chatmanager.bypass.antibot")) return;

		if (!cacheManager.getAntiBotCache().containsUser(player.getUniqueId())) return;

		event.setCancelled(true);
		//Methods.sendMessage(player, messages.getString("Anti_Bot.Deny_Chat_Message"), true);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();

		if (!configSettings.getProperty(ConfigSettings.BLOCK_COMMANDS_UNTIL_MOVED) || player.hasPermission("chatmanager.bypass.antibot")) return;

		if (!cacheManager.getAntiBotCache().containsUser(player.getUniqueId())) return;

		event.setCancelled(true);
		//Methods.sendMessage(player, messages.getString("Anti_Bot.Deny_Command_Message"), true);
	}
}