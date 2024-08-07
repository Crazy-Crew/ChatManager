package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.NotNull;

public class ListenerAntiBot implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Anti_Bot.Block_Chat_Until_Moved", false) || player.hasPermission(Permissions.BYPASS_ANTI_BOT.getNode())) return;

		this.plugin.api().getAntiBotData().addUser(player.getUniqueId());
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Anti_Bot.Block_Chat_Until_Moved", false) || player.hasPermission(Permissions.BYPASS_ANTI_BOT.getNode())) return;

		this.plugin.api().getAntiBotData().removeUser(player.getUniqueId());
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();

		FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Anti_Bot.Block_Chat_Until_Moved", false) || player.hasPermission(Permissions.BYPASS_ANTI_BOT.getNode())) return;

		this.plugin.api().getAntiBotData().removeUser(player.getUniqueId());
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Anti_Bot.Block_Chat_Until_Moved", false) || player.hasPermission(Permissions.BYPASS_ANTI_BOT.getNode())) return;

		if (!this.plugin.api().getAntiBotData().containsUser(player.getUniqueId())) return;

		event.setCancelled(true);

		Messages.ANTI_BOT_DENY_CHAT_MESSAGE.sendMessage(player);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();

		FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Anti_Bot.Block_Commands_Until_Moved", false) || player.hasPermission(Permissions.BYPASS_ANTI_BOT.getNode())) return;

		if (!this.plugin.api().getAntiBotData().containsUser(player.getUniqueId())) return;

		event.setCancelled(true);

		Messages.ANTI_BOT_DENY_COMMAND_MESSAGE.sendMessage(player);
	}
}