package me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerStaffChat implements Listener {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {

		FileConfiguration config = settingsManager.getConfig();

		Player player = event.getPlayer();
		String message = event.getMessage();

		if (Methods.cm_staffChat.contains(player.getUniqueId())) {
			event.setCancelled(true);

			for (Player staff : plugin.getServer().getOnlinePlayers()) {
				if (staff.hasPermission("chatmanager.staffchat")) staff.sendMessage(PlaceholderManager.setPlaceholders(player, config.getString("Staff_Chat.Format").replace("{message}", message)));
			}

			Methods.tellConsole(PlaceholderManager.setPlaceholders(player, config.getString("Staff_Chat.Format").replace("{message}", message)));
		}
	}
}