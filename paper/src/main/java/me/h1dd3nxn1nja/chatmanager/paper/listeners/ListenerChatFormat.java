package me.h1dd3nxn1nja.chatmanager.paper.listeners;

import com.ryderbelserion.chatmanager.paper.files.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.managers.PlaceholderManager;
import me.h1dd3nxn1nja.chatmanager.paper.support.PluginSupport;
import me.h1dd3nxn1nja.chatmanager.paper.support.misc.VaultSupport;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import me.h1dd3nxn1nja.chatmanager.paper.Methods;

public class ListenerChatFormat implements Listener {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final VaultSupport vaultSupport = plugin.getPluginManager().getVaultSupport();
	private final PlaceholderManager placeholderManager = plugin.getCrazyManager().getPlaceholderManager();


	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onChatFormat(AsyncPlayerChatEvent event) {
		FileConfiguration config = Files.CONFIG.getFile();

		Player player = event.getPlayer();
		String message = event.getMessage();
		String key = vaultSupport.getPermission().getPrimaryGroup(player);
		String format;

		if (!config.getBoolean("Chat_Format.Enable")) return;

		format = config.getConfigurationSection("Chat_Format.Groups." + key) != null ? config.getString("Chat_Format.Groups." + key + ".Format") : config.getString("Chat_Format.Default_Format");

		format = setupPlaceholderAPI(player, format);
		format = placeholderManager.setPlaceholders(player, format);
		format = setupChatRadius(player, format);
		format = Methods.color(format);
		format = format.replace("{message}", message);
		format = format.replaceAll("%", "%%");

		event.setFormat(format);
		event.setMessage(message);
	}

	public String setupChatRadius(Player player, String message) {
		FileConfiguration config = Files.CONFIG.getFile();

		String placeholders = message;

		if (config.getBoolean("Chat_Radius.Enable")) {
			if (plugin.api().getGlobalChatData().containsUser(player.getUniqueId())) placeholders = placeholders.replace("{radius}", config.getString("Chat_Radius.Global_Chat.Prefix"));

			if (plugin.api().getLocalChatData().containsUser(player.getUniqueId())) placeholders = placeholders.replace("{radius}", config.getString("Chat_Radius.Local_Chat.Prefix"));
			if (plugin.api().getWorldChatData().containsUser(player.getUniqueId())) placeholders = placeholders.replace("{radius}", config.getString("Chat_Radius.World_Chat.Prefix"));
		} else {
			placeholders = placeholders.replace("{radius}", "");
		}

		return placeholders;
	}
	
	public String setupPlaceholderAPI(Player player, String message) {
		String placeholders = message;

		if ((PluginSupport.PLACEHOLDERAPI.isPluginEnabled()) && (PlaceholderAPI.containsPlaceholders(placeholders))) placeholders = PlaceholderAPI.setPlaceholders(player, placeholders);

		return placeholders;
	}
}