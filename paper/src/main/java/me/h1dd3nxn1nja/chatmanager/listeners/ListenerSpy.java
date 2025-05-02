package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.cmds.CommandSpyData;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
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

	private final ApiLoader api = this.plugin.api();

	private final CommandSpyData data = this.api.getCommandSpyData();

	@EventHandler(ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final List<String> blacklist = config.getStringList("Command_Spy.Blacklist_Commands");

		final Player player = event.getPlayer();
		final String message = event.getMessage();

		if (!player.hasPermission(Permissions.BYPASS_COMMAND_SPY.getNode())) {
			for (final String command : blacklist) {
				if (message.toLowerCase().startsWith(command)) return;
			}

			for (final Player staff : this.server.getOnlinePlayers()) {
				boolean isValid = this.data.containsUser(staff.getUniqueId());

				if (!isValid || !staff.hasPermission(Permissions.COMMAND_SPY.getNode())) return;

				Messages.COMMAND_SPY_FORMAT.sendMessage(staff, new HashMap<>() {{
					put("{player}", player.getName());
					put("{command}", message);
				}});
			}
		}
	}
}