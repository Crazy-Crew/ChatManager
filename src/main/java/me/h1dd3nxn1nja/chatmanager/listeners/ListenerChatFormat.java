package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.plugins.VaultSupport;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

public class ListenerChatFormat implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onChatFormat(AsyncPlayerChatEvent event) {
		FileConfiguration config = Files.CONFIG.getConfiguration();

		Player player = event.getPlayer();
		String message = event.getMessage();
		String format;

		if (!config.getBoolean("Chat_Format.Enable")) return;

		if (VaultSupport.isChatReady()) {
			String key = VaultSupport.getPermission().getPrimaryGroup(player);

			format = config.getConfigurationSection("Chat_Format.Groups." + key) != null ? config.getString("Chat_Format.Groups." + key + ".Format") : config.getString("Chat_Format.Default_Format");
		} else {
			format = config.getString("Chat_Format.Default_Format");
		}

		format = setupChatRadius(player, format);
		format = Methods.placeholders(false, player, Methods.color(format));
		format = format.replace("{message}", message);
		format = format.replaceAll("%", "%%");

		event.setFormat(format);
		event.setMessage(message);
	}

	public String setupChatRadius(Player player, String message) {
		FileConfiguration config = Files.CONFIG.getConfiguration();

		String placeholders = message;

		if (config.getBoolean("Chat_Radius.Enable")) {
			if (this.plugin.api().getGlobalChatData().containsUser(player.getUniqueId())) placeholders = placeholders.replace("{radius}", config.getString("Chat_Radius.Global_Chat.Prefix"));

			if (this.plugin.api().getLocalChatData().containsUser(player.getUniqueId())) placeholders = placeholders.replace("{radius}", config.getString("Chat_Radius.Local_Chat.Prefix"));
			if (this.plugin.api().getWorldChatData().containsUser(player.getUniqueId())) placeholders = placeholders.replace("{radius}", config.getString("Chat_Radius.World_Chat.Prefix"));
		} else {
			placeholders = placeholders.replace("{radius}", "");
		}

		return placeholders;
	}
}