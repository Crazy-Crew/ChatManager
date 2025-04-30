package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.api.objects.PaperServer;
import com.ryderbelserion.chatmanager.api.objects.PaperUser;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.chatmanager.enums.core.PlayerState;
import com.ryderbelserion.chatmanager.enums.core.ServerState;
import com.ryderbelserion.chatmanager.managers.ServerManager;
import com.ryderbelserion.chatmanager.managers.UserManager;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import java.util.Optional;

public class ChatListener implements Listener {

	private final ChatManager plugin = ChatManager.get();

	private final UserManager userManager = this.plugin.getUserManager();

	private final ServerManager serverManager = this.plugin.getServerManager();

	@EventHandler(ignoreCancelled = true)
	public void mute(AsyncChatEvent event) {
		final PaperServer server = this.serverManager.getServer();

		final Player player = event.getPlayer();

		if (!server.hasState(ServerState.MUTED) || Permissions.BYPASS_MUTE_CHAT.hasPermission(player)) return;

		Messages.MUTE_CHAT_DENIED_MESSAGE.sendMessage(player);

		event.setCancelled(true);
	}

	@EventHandler(ignoreCancelled = true)
	public void onMuteCommand(PlayerCommandPreprocessEvent event) {
		final Player player = event.getPlayer();

		final PaperServer server = this.serverManager.getServer();

		if (!server.hasState(ServerState.MUTED)) return;

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Mute_Chat.Disable_Commands", false) || !Permissions.BYPASS_MUTE_CHAT.hasPermission(player)) return;

		final String message = event.getMessage().toLowerCase();

		for (final String command : config.getStringList("Mute_Chat.Disabled_Commands")) {
			if (!message.contains(command)) continue;

			Messages.MUTE_CHAT_BLOCKED_COMMANDS_MESSAGE.sendMessage(player);

			event.setCancelled(true);

			break;
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void chat(AsyncChatEvent event) {
		final Player player = event.getPlayer();

		final Optional<PaperUser> user = this.userManager.getUser(event.getPlayer());

		if (user.isEmpty()) return;

		final PaperUser output = user.get();

		if (!output.hasState(PlayerState.CHAT)) return;

		event.viewers().remove(player);
	}
}