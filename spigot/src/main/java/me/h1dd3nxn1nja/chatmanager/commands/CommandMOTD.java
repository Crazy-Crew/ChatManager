package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandMOTD implements Listener {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	@EventHandler
	public void MOTD(PlayerCommandPreprocessEvent e) {
		Player player = e.getPlayer();

		FileConfiguration config = settingsManager.getConfig();

		String message = e.getMessage();

		if (config.getBoolean("MOTD.Enable")) {
			if (message.equalsIgnoreCase("/MOTD")) {
				for (String motd : config.getStringList("MOTD.Message")) {
					player.sendMessage(PlaceholderManager.setPlaceholders(player, motd));
				}

				e.setCancelled(true);
			}
		}
	}
}