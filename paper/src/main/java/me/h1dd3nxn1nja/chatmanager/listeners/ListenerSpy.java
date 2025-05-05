package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.api.objects.PaperUser;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.core.PlayerState;
import com.ryderbelserion.chatmanager.utils.UserUtils;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.List;

public class ListenerSpy implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	private final Server server = this.plugin.getServer();

	@EventHandler(ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final List<String> blacklist = config.getStringList("Command_Spy.Blacklist_Commands");

		final Player player = event.getPlayer();

		if (Permissions.BYPASS_COMMAND_SPY.hasPermission(player)) return;

		final String message = event.getMessage();

		boolean hasMatch = false;

		for (final String command : blacklist) {
			if (message.toLowerCase().startsWith(command)) {
				hasMatch = true;

				break;
			}
		}

		if (hasMatch) return;

		for (final Player target : this.server.getOnlinePlayers()) {
			final PaperUser user = UserUtils.getUser(target.getUniqueId());

			if (user.hasState(PlayerState.COMMAND_SPY) || !Permissions.COMMAND_SPY.hasPermission(target)) continue;

			Messages.COMMAND_SPY_FORMAT.sendMessage(target, new HashMap<>() {{
				put("{player}", player.getName());
				put("{command}", message);
			}});
		}
	}
}