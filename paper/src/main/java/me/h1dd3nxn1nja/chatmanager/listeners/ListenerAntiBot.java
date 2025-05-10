package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerAntiBot extends Global implements Listener {

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Anti_Bot.Block_Chat_Until_Moved", false) || player.hasPermission(Permissions.BYPASS_ANTI_BOT.getNode())) return;

		this.antiBotData.addUser(player.getUniqueId());
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onLeave(PlayerQuitEvent event) {
		final Player player = event.getPlayer();

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Anti_Bot.Block_Chat_Until_Moved", false) || player.hasPermission(Permissions.BYPASS_ANTI_BOT.getNode())) return;

		this.antiBotData.removeUser(player.getUniqueId());
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onMove(PlayerMoveEvent event) {
		final Player player = event.getPlayer();

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Anti_Bot.Block_Chat_Until_Moved", false) || player.hasPermission(Permissions.BYPASS_ANTI_BOT.getNode())) return;

		this.antiBotData.removeUser(player.getUniqueId());
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Anti_Bot.Block_Chat_Until_Moved", false) || player.hasPermission(Permissions.BYPASS_ANTI_BOT.getNode())) return;

		if (!this.antiBotData.containsUser(player.getUniqueId())) return;

		event.setCancelled(true);

		Messages.ANTI_BOT_DENY_CHAT_MESSAGE.sendMessage(player);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		final Player player = event.getPlayer();

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Anti_Bot.Block_Commands_Until_Moved", false) || player.hasPermission(Permissions.BYPASS_ANTI_BOT.getNode())) return;

		if (!this.antiBotData.containsUser(player.getUniqueId())) return;

		event.setCancelled(true);

		Messages.ANTI_BOT_DENY_COMMAND_MESSAGE.sendMessage(player);
	}
}