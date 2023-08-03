package me.h1dd3nxn1nja.chatmanager.paper.listeners;

import com.ryderbelserion.chatmanager.paper.files.Files;
import me.h1dd3nxn1nja.chatmanager.paper.Methods;
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

		FileConfiguration messages = Files.MESSAGES.getFile();

		if (player.hasPermission("chatmanager.bypass.mutechat") || !Methods.isMuted()) return;

		Methods.sendMessage(player, messages.getString("Mute_Chat.Denied_Message"), true);
		event.setCancelled(true);
	}

	@EventHandler(ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();

		FileConfiguration config = Files.CONFIG.getFile();
		FileConfiguration messages = Files.MESSAGES.getFile();

		if (!config.getBoolean("Mute_Chat.Disable_Commands") || player.hasPermission("chatmanager.bypass.mutechat") || !Methods.isMuted()) return;

		for (String command : config.getStringList("Mute_Chat.Disabled_Commands")) {
			if (event.getMessage().toLowerCase().contains(command)) {
				Methods.sendMessage(player, messages.getString("Mute_Chat.Blocked_Commands.Message"), true);
				event.setCancelled(true);
				return;
			}
		}
	}
}