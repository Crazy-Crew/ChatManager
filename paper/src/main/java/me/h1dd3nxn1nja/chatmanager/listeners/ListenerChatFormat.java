package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.chat.GlobalChatData;
import com.ryderbelserion.chatmanager.api.chat.LocalChatData;
import com.ryderbelserion.chatmanager.api.chat.WorldChatData;
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
import java.util.UUID;

public class ListenerChatFormat implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	private final ApiLoader api = this.plugin.api();

	private final GlobalChatData global = this.api.getGlobalChatData();

	private final LocalChatData local = this.api.getLocalChatData();

	private final WorldChatData world = this.api.getWorldChatData();

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

		format = setupChatRadius(player, format);
		format = Methods.placeholders(false, player, Methods.color(format));
		format = format.replace("{message}", message);
		format = format.replaceAll("%", "%%");

		event.setFormat(format);
		event.setMessage(message);
	}

	public String setupChatRadius(final Player player, final String message) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		String placeholders = message;

		final UUID uuid = player.getUniqueId();

		if (config.getBoolean("Chat_Radius.Enable", false)) {
			if (this.global.containsUser(uuid)) placeholders = placeholders.replace("{radius}", config.getString("Chat_Radius.Global_Chat.Prefix", "&7[&bGlobal&7]"));

			if (this.local.containsUser(uuid)) placeholders = placeholders.replace("{radius}", config.getString("Chat_Radius.Local_Chat.Prefix", "&7[&cLocal&7]"));
			if (this.world.containsUser(uuid)) placeholders = placeholders.replace("{radius}", config.getString("Chat_Radius.World_Chat.Prefix", "&7[&dWorld&7]"));
		} else {
			placeholders = placeholders.replace("{radius}", "");
		}

		return placeholders;
	}
}