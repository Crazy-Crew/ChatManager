package me.h1dd3nxn1nja.chatmanager.listeners;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;

public class ListenerPerWorldChat implements Listener {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	@EventHandler
	public void onWorldChat(AsyncPlayerChatEvent event) {
		FileConfiguration config = settingsManager.getConfig();

		Player player = event.getPlayer();
		String world = player.getWorld().getName();
		UUID worldUID = event.getPlayer().getWorld().getUID();
		Set<Player> recipients = event.getRecipients();

		List<String> playerGroup = null;

		if (!config.getBoolean("Per_World_Chat.Enable") || plugin.api().getStaffChatData().containsUser(player.getUniqueId()))
			return;

		if (Methods.cm_pwcGlobal.contains(player.getUniqueId())) return;

		if (config.getBoolean("Per_World_Chat.Group_Worlds.Enable")) {
			for (String key : config.getConfigurationSection("Per_World_Chat.Group_Worlds.Worlds").getKeys(false)) {
				List<String> group = config.getStringList("Per_World_Chat.Group_Worlds.Worlds." + key);
				if (group.contains(world)) playerGroup = group;
			}

			for (Player player2 : plugin.getServer().getOnlinePlayers()) {
				String world2 = player2.getWorld().getName();
				if (playerGroup != null) {
					if (!playerGroup.contains(world2)) {
						if (!Methods.cm_pwcGlobal.contains(player2.getUniqueId())) recipients.remove(player2);
					}
				} else {
					if (!world.equals(world2)) {
						if (!Methods.cm_pwcGlobal.contains(player2.getUniqueId())) recipients.remove(player2);
					}
				}
			}

			return;
		}

		recipients.removeIf(players -> !worldUID.equals(players.getWorld().getUID()) && !Methods.cm_pwcGlobal.contains(players.getUniqueId()));
	}
}