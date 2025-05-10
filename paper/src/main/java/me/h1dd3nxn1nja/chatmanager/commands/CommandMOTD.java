package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.enums.Files;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandMOTD extends Global implements Listener {

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