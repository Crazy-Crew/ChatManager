package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerStaffChat extends Global implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();
		final String message = event.getMessage();

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!this.staffChatData.containsUser(player.getUniqueId())) return;

		event.setCancelled(true);

		for (final Player staff : this.server.getOnlinePlayers()) {
			if (staff.hasPermission(Permissions.TOGGLE_STAFF_CHAT.getNode())) {
				Methods.sendMessage(staff, config.getString("Staff_Chat.Format", "&e[&bStaffChat&e] &a{player} &7> &b{message}").replace("{player}", player.getName()).replace("{message}", message));
			}
		}

		Methods.tellConsole(config.getString("Staff_Chat.Format", "&e[&bStaffChat&e] &a{player} &7> &b{message}").replace("{player}", player.getName()).replace("{message}", message), false);
	}
}