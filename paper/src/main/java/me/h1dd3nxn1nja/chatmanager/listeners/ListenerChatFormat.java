package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.api.objects.PaperUser;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.plugins.VaultSupport;
import com.ryderbelserion.chatmanager.utils.UserUtils;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerChatFormat implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onChatFormat(AsyncPlayerChatEvent event) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final Player player = event.getPlayer();
		final String message = event.getMessage();

		String format;

		if (!config.getBoolean("Chat_Format.Enable", false)) return;

		if (VaultSupport.isChatReady()) {
			final String key = VaultSupport.getPermission().getPrimaryGroup(player);

			format = config.getConfigurationSection("Chat_Format.Groups." + key) != null ? config.getString("Chat_Format.Groups." + key + ".Format") : config.getString("Chat_Format.Default_Format");
		} else {
			format = config.getString("Chat_Format.Default_Format", "%luckperms_prefix% &7{player} &9> #32a87d{message}");
		}

		format = setupChatRadius(player, format, config);
		format = Methods.placeholders(false, player, Methods.color(format));
		format = format.replace("{message}", message);
		format = format.replaceAll("%", "%%");

		event.setFormat(format);
		event.setMessage(message);
	}

	private String setupChatRadius(final Player player, final String message, final FileConfiguration config) {
		String placeholders = message;

		final PaperUser user = UserUtils.getUser(player);

		if (config.getBoolean("Chat_Radius.Enable", false)) {
			switch (user.getRadius()) {
				case GLOBAL_CHAT -> placeholders = placeholders.replace("{radius}", config.getString("Chat_Radius.Global_Chat.Prefix", "&7[&bGlobal&7]"));
				case LOCAL_CHAT -> placeholders = placeholders.replace("{radius}", config.getString("Chat_Radius.Local_Chat.Prefix", "&7[&cLocal&7]"));
				case WORLD_CHAT -> placeholders = placeholders.replace("{radius}", config.getString("Chat_Radius.World_Chat.Prefix", "&7[&dWorld&7]"));
			}
		} else {
			placeholders = placeholders.replace("{radius}", "");
		}

		return placeholders;
	}
}