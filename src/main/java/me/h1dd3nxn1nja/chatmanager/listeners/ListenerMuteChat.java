package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
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

		if (player.hasPermission(Permissions.BYPASS_MUTE_CHAT.getNode()) || !Methods.isMuted()) return;

		Messages.MUTE_CHAT_DENIED_MESSAGE.sendMessage(player);

		event.setCancelled(true);
	}

	@EventHandler(ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();

		FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Mute_Chat.Disable_Commands", false) ||
				player.hasPermission(Permissions.BYPASS_MUTE_CHAT.getNode()) || !Methods.isMuted()) return;

		for (String command : config.getStringList("Mute_Chat.Disabled_Commands")) {
			if (event.getMessage().toLowerCase().contains(command)) {
				Messages.MUTE_CHAT_BLOCKED_COMMANDS_MESSAGE.sendMessage(player);

				event.setCancelled(true);

				return;
			}
		}
	}
}