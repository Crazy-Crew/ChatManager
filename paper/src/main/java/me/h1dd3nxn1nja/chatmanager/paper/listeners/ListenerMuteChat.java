package me.h1dd3nxn1nja.chatmanager.paper.listeners;

import com.ryderbelserion.chatmanager.paper.enums.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import com.ryderbelserion.chatmanager.paper.enums.Permissions;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;

public class ListenerMuteChat implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@EventHandler(ignoreCancelled = true)
	public void muteChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		FileConfiguration messages = Files.MESSAGES.getFile();

		if (player.hasPermission(Permissions.BYPASS_MUTE_CHAT.getNode()) || !this.plugin.getMethods().isMuted()) return;

		this.plugin.getMethods().sendMessage(player, messages.getString("Mute_Chat.Denied_Message"), true);
		event.setCancelled(true);
	}

	@EventHandler(ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();

		FileConfiguration config = Files.CONFIG.getFile();
		FileConfiguration messages = Files.MESSAGES.getFile();

		if (!config.getBoolean("Mute_Chat.Disable_Commands") || player.hasPermission(Permissions.BYPASS_MUTE_CHAT.getNode()) || !this.plugin.getMethods().isMuted()) return;

		for (String command : config.getStringList("Mute_Chat.Disabled_Commands")) {
			if (event.getMessage().toLowerCase().contains(command)) {
				this.plugin.getMethods().sendMessage(player, messages.getString("Mute_Chat.Blocked_Commands.Message"), true);
				event.setCancelled(true);
				return;
			}
		}
	}
}