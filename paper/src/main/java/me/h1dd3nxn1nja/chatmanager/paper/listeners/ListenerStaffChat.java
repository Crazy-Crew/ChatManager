package me.h1dd3nxn1nja.chatmanager.paper.listeners;

import com.ryderbelserion.chatmanager.paper.files.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.Methods;
import me.h1dd3nxn1nja.chatmanager.paper.managers.PlaceholderManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerStaffChat implements Listener {

	private final ChatManager plugin = ChatManager.getPlugin();
	private final PlaceholderManager placeholderManager = this.plugin.getCrazyManager().getPlaceholderManager();

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();

		FileConfiguration config = Files.CONFIG.getFile();

		if (!plugin.api().getStaffChatData().containsUser(player.getUniqueId())) return;

		event.setCancelled(true);

		for (Player staff : plugin.getServer().getOnlinePlayers()) {
			if (staff.hasPermission("chatmanager.staffchat")) staff.sendMessage(placeholderManager.setPlaceholders(player, config.getString("Staff_Chat.Format").replace("{message}", message)));
		}

		Methods.tellConsole(placeholderManager.setPlaceholders(player, config.getString("Staff_Chat.Format").replace("{message}", message)), false);
	}
}