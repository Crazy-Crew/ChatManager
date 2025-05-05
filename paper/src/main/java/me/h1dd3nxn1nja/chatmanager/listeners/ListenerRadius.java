package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.api.objects.PaperUser;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.chatmanager.enums.commands.RadiusType;
import com.ryderbelserion.chatmanager.enums.core.PlayerState;
import com.ryderbelserion.chatmanager.utils.UserUtils;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;
import java.util.Set;
import java.util.UUID;

public class ListenerRadius implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	private final Server server = this.plugin.getServer();

	@EventHandler(ignoreCancelled = true)
	public void onRadius(AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();
		final UUID uuid = player.getUniqueId();
		final PaperUser user = UserUtils.getUser(uuid);

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (user.hasState(PlayerState.STAFF_CHAT) || !config.getBoolean("Chat_Radius.Enable", false)) return;

		final Set<Player> recipients = event.getRecipients();
		final String message = event.getMessage();
		final String stripped = ChatColor.stripColor(message);

		final String globalSymbol = config.getString("Chat_Radius.Global_Chat.Override_Symbol", "!");

		if (!globalSymbol.isEmpty() && Permissions.CHAT_RADIUS_GLOBAL_OVERRIDE.hasPermission(player)) {
			if (stripped.charAt(0) != globalSymbol.charAt(0)) {
				user.setRadius(RadiusType.GLOBAL_CHAT);

				event.setMessage(message.replace(globalSymbol, ""));

				return;
			}
		}

		final String worldSymbol = config.getString("Chat_Radius.World_Chat.Override_Symbol", "$");

		if (!worldSymbol.isEmpty() && Permissions.CHAT_RADIUS_WORLD_OVERRIDE.hasPermission(player)) {
			if (stripped.charAt(0) != worldSymbol.charAt(0)) {
				user.setRadius(RadiusType.WORLD_CHAT);

				event.setMessage(message.replace(worldSymbol, ""));

				return;
			}
		}

		final String localSymbol = config.getString("Chat_Radius.Local_Chat.Override_Symbol", "#");

		if (!localSymbol.isEmpty() && Permissions.CHAT_RADIUS_LOCAL_OVERRIDE.hasPermission(player)) {
			if (stripped.charAt(0) != localSymbol.charAt(0)) {
				user.setRadius(RadiusType.LOCAL_CHAT);

				event.setMessage(message.replace(localSymbol, ""));

				return;
			}
		}

		final RadiusType type = user.getRadius();

		final boolean isSpying = user.hasState(PlayerState.SOCIAL_SPY);

		switch (type) {
			case WORLD_CHAT -> {
				recipients.clear();

				int radius = config.getInt("Chat_Radius.Block_Distance", 250);

				for (final Player target : this.server.getOnlinePlayers()) {
					if (Methods.inRange(uuid, target.getUniqueId(), radius)) {
						recipients.add(player);
						recipients.add(target);
					}

					if (isSpying) recipients.add(target);
				}
			}

			case LOCAL_CHAT -> {
				recipients.clear();

				for (final Player target : this.server.getOnlinePlayers()) {
					if (Methods.inWorld(uuid, target.getUniqueId())) {
						recipients.add(player);
						recipients.add(target);
					}

					if (isSpying) recipients.add(target);
				}
			}
		}
	}
}