package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import me.h1dd3nxn1nja.chatmanager.ChatManagerMercurioMC;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

public class ListenerStaffChat implements Listener {

	@NotNull
	private final ChatManagerMercurioMC plugin = ChatManagerMercurioMC.get();

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();

		FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!this.plugin.api().getStaffChatData().containsUser(player.getUniqueId())) return;

		event.setCancelled(true);

		for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
			if (staff.hasPermission(Permissions.TOGGLE_STAFF_CHAT.getNode())) {
				Methods.sendMessage(staff, config.getString("Staff_Chat.Format", "&e[&bStaffChat&e] &a{player} &7> &b{message}").replace("{player}", player.getName()).replace("{message}", message));
			}
		}

		Methods.tellConsole(config.getString("Staff_Chat.Format", "&e[&bStaffChat&e] &a{player} &7> &b{message}").replace("{player}", player.getName()).replace("{message}", message), false);
	}
}