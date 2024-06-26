package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import com.ryderbelserion.chatmanager.enums.Files;
import org.jetbrains.annotations.NotNull;

public class CommandMOTD implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@NotNull
	private final PlaceholderManager placeholderManager = this.plugin.getCrazyManager().getPlaceholderManager();

	@EventHandler
	public void onMotd(PlayerCommandPreprocessEvent e) {
		FileConfiguration config = Files.CONFIG.getConfiguration();

		Player player = e.getPlayer();
		String message = e.getMessage();

		if (config.getBoolean("MOTD.Enable")) {
			if (message.equalsIgnoreCase("/motd")) {
				for (String motd : config.getStringList("MOTD.Message")) {
					this.plugin.getMethods().sendMessage(player, this.placeholderManager.setPlaceholders(player, motd), true);
				}

				e.setCancelled(true);
			}
		}
	}
}