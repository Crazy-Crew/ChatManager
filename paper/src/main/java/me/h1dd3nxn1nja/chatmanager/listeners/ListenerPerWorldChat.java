package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ListenerPerWorldChat extends Global implements Listener {

	@EventHandler
	public void onWorldChat(AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();
		final UUID uuid = player.getUniqueId();
		final String world = player.getWorld().getName();
		final UUID worldUID = event.getPlayer().getWorld().getUID();
		final Set<Player> recipients = event.getRecipients();

		List<String> playerGroup = null;

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Per_World_Chat.Enable", false) || this.staffChatData.containsUser(uuid)) return;

		if (this.perWorldChatData.containsUser(uuid)) return;

		if (config.getBoolean("Per_World_Chat.Group_Worlds.Enable", false)) {
			for (final String key : config.getConfigurationSection("Per_World_Chat.Group_Worlds.Worlds").getKeys(false)) {
				final List<String> group = config.getStringList("Per_World_Chat.Group_Worlds.Worlds." + key);

				if (group.contains(world)) playerGroup = group;
			}

			for (final Player player2 : this.server.getOnlinePlayers()) {
				final String world2 = player2.getWorld().getName();

				if (playerGroup != null) {
					if (!playerGroup.contains(world2)) {
						if (!this.perWorldChatData.containsUser(player2.getUniqueId())) recipients.remove(player2);
					}
				} else {
					if (!world.equals(world2)) {
						if (!this.perWorldChatData.containsUser(player2.getUniqueId())) recipients.remove(player2);
					}
				}
			}

			return;
		}

		recipients.removeIf(players -> !worldUID.equals(players.getWorld().getUID()) && !this.perWorldChatData.containsUser(players.getUniqueId()));
	}
}