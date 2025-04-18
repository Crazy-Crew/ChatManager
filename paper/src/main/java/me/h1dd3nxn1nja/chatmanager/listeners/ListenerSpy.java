package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import me.h1dd3nxn1nja.chatmanager.ChatManagerMercurioMC;
import com.ryderbelserion.chatmanager.enums.Permissions;
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
	private final ChatManagerMercurioMC plugin = ChatManagerMercurioMC.get();

	@EventHandler(ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		FileConfiguration config = Files.CONFIG.getConfiguration();
		//FileConfiguration messages = Files.MESSAGES.getConfiguration();

		List<String> blacklist = config.getStringList("Command_Spy.Blacklist_Commands");

		Player player = event.getPlayer();
		String message = event.getMessage();

		if (!player.hasPermission(Permissions.BYPASS_COMMAND_SPY.getNode())) {
			for (String command : blacklist) {
				if (message.toLowerCase().startsWith(command)) return;
			}

			for (Player staff : this.plugin.getServer().getOnlinePlayers()) {

				boolean isValid = this.plugin.api().getCommandSpyData().containsUser(staff.getUniqueId());

				if (!isValid || !staff.hasPermission(Permissions.COMMAND_SPY.getNode())) return;

				Messages.COMMAND_SPY_FORMAT.sendMessage(staff, new HashMap<>() {{
					put("{player}", player.getName());
					put("{command}", message);
				}});
			}
		}
	}
}