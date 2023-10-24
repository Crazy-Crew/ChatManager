package me.h1dd3nxn1nja.chatmanager.paper.listeners;

import com.ryderbelserion.chatmanager.paper.files.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.managers.PlaceholderManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ListenerStaffChat implements Listener {

	@NotNull
	private final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

	@NotNull
	private final PlaceholderManager placeholderManager = this.plugin.getCrazyManager().getPlaceholderManager();

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();

		FileConfiguration config = Files.CONFIG.getFile();

		if (!this.plugin.api().getStaffChatData().containsUser(player.getUniqueId())) return;

		event.setCancelled(true);

		for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
			if (staff.hasPermission("chatmanager.staffchat")) staff.sendMessage(this.placeholderManager.setPlaceholders(player, config.getString("Staff_Chat.Format").replace("{message}", message)));
		}

		this.plugin.getMethods().tellConsole(this.placeholderManager.setPlaceholders(player, config.getString("Staff_Chat.Format").replace("{message}", message)), false);
	}
}