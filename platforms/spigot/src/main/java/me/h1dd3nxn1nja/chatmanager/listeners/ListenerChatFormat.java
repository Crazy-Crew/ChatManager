package me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import me.h1dd3nxn1nja.chatmanager.support.PluginSupport;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.misc.VaultSupport;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;

public class ListenerChatFormat implements Listener {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	private final PlaceholderManager placeholderManager = plugin.getCrazyManager().getPlaceholderManager();

	private final VaultSupport vaultSupport = plugin.getPluginManager().getVaultSupport();

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onChatFormat(AsyncPlayerChatEvent event) {
		FileConfiguration config = settingsManager.getConfig();

		Player player = event.getPlayer();
		String message = event.getMessage();
		String key = vaultSupport.getPermission().getPrimaryGroup(player);
		String format;

		if (config.getBoolean("Chat_Format.Enable")) {
			if (config.getConfigurationSection("Chat_Format.Groups." + key) != null) {
				format = config.getString("Chat_Format.Groups." + key + ".Format");
			} else {
				format = config.getString("Chat_Format.Default_Format");
			}

			format = setupPlaceholderAPI(player, format);
			format = placeholderManager.setPlaceholders(player, format);
			format = setupChatRadius(player, format);
			format = Methods.color(format);
			format = format.replace("{message}", message);
			format = format.replaceAll("%", "%%");

			event.setFormat(format);
			event.setMessage(message);
		}
	}

	private String setupChatRadius(Player player, String message) {
		FileConfiguration config = settingsManager.getConfig();
		String placeholders = message;

		if (config.getBoolean("Chat_Radius.Enable")) {
			if (Methods.cm_globalChat.contains(player.getUniqueId())) placeholders = placeholders.replace("{radius}", config.getString("Chat_Radius.Global_Chat.Prefix"));

			if (Methods.cm_localChat.contains(player.getUniqueId())) placeholders = placeholders.replace("{radius}", config.getString("Chat_Radius.Local_Chat.Prefix"));
			if (Methods.cm_worldChat.contains(player.getUniqueId())) placeholders = placeholders.replace("{radius}", config.getString("Chat_Radius.World_Chat.Prefix"));
		} else {
			placeholders = placeholders.replace("{radius}", "");
		}

		return placeholders;
	}

	private String setupPlaceholderAPI(Player player, String message) {
		String placeholders = message;

		if ((PluginSupport.PLACEHOLDERAPI.isPluginEnabled()) && (PlaceholderAPI.containsPlaceholders(placeholders)))
			placeholders = PlaceholderAPI.setPlaceholders(player, placeholders);

		return placeholders;
	}
}