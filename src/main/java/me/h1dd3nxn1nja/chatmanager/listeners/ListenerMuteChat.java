package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ListenerMuteChat implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void muteChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		FileConfiguration messages = Files.MESSAGES.getConfiguration();

		if (player.hasPermission(Permissions.BYPASS_MUTE_CHAT.getNode()) || !Methods.isMuted()) return;

		Methods.sendMessage(player, messages.getString("Mute_Chat.Denied_Message"), true);
		event.setCancelled(true);
	}

	@EventHandler(ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();

		FileConfiguration config = Files.CONFIG.getConfiguration();
		FileConfiguration messages = Files.MESSAGES.getConfiguration();

		if (!config.getBoolean("Mute_Chat.Disable_Commands") || player.hasPermission(Permissions.BYPASS_MUTE_CHAT.getNode()) || !Methods.isMuted()) return;

		for (String command : config.getStringList("Mute_Chat.Disabled_Commands")) {
			if (event.getMessage().toLowerCase().contains(command)) {
				Methods.sendMessage(player, messages.getString("Mute_Chat.Blocked_Commands.Message"), true);
				event.setCancelled(true);
				return;
			}
		}
	}
}