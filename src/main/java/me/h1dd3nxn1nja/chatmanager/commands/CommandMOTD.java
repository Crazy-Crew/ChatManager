package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import com.ryderbelserion.chatmanager.enums.Files;

public class CommandMOTD implements Listener {

	@EventHandler
	public void onMotd(PlayerCommandPreprocessEvent e) {
		FileConfiguration config = Files.CONFIG.getConfiguration();

		Player player = e.getPlayer();
		String message = e.getMessage();

		if (config.getBoolean("MOTD.Enable", false)) {
			if (message.equalsIgnoreCase("/motd")) {
				for (String motd : config.getStringList("MOTD.Message")) {
					Methods.sendMessage(player, motd, true);
				}

				e.setCancelled(true);
			}
		}
	}
}