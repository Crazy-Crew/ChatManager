package me.h1dd3nxn1nja.chatmanager.paper.commands;

import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.managers.PlaceholderManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import com.ryderbelserion.chatmanager.paper.files.Files;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CommandMOTD implements Listener {

	@NotNull
	private final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

	@NotNull
	private final PlaceholderManager placeholderManager = this.plugin.getCrazyManager().getPlaceholderManager();

	@EventHandler
	public void onMotd(PlayerCommandPreprocessEvent e) {
		FileConfiguration config = Files.CONFIG.getFile();

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