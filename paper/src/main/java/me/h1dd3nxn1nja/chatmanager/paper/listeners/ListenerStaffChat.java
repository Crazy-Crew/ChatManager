package me.h1dd3nxn1nja.chatmanager.paper.listeners;

import me.h1dd3nxn1nja.chatmanager.paper.Methods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerStaffChat implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();

		if (!plugin.api().getStaffChatData().containsUser(player.getUniqueId())) return;

		event.setCancelled(true);

		for (Player staff : plugin.getServer().getOnlinePlayers()) {
			if (staff.hasPermission("chatmanager.staffchat")) staff.sendMessage(placeholderManager.setPlaceholders(player, config.getString("Staff_Chat.Format").replace("{message}", message)));
		}

		Methods.tellConsole(placeholderManager.setPlaceholders(player, config.getString("Staff_Chat.Format").replace("{message}", message)), false);
	}
}