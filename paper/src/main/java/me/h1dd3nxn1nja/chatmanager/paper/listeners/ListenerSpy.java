package me.h1dd3nxn1nja.chatmanager.paper.listeners;

import com.ryderbelserion.chatmanager.paper.files.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import java.util.List;

public class ListenerSpy implements Listener {

	private final ChatManager plugin = ChatManager.getPlugin();

	@EventHandler(ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		FileConfiguration config = Files.CONFIG.getFile();
		FileConfiguration messages = Files.MESSAGES.getFile();

		List<String> blacklist = config.getStringList("Command_Spy.Blacklist_Commands");

		Player player = event.getPlayer();
		String message = event.getMessage();

		if (!player.hasPermission("chatmanager.bypass.commandspy")) {
			for (String command : blacklist) {
				if (message.toLowerCase().startsWith(command)) return;
			}

			for (Player staff : plugin.getServer().getOnlinePlayers()) {

				boolean isValid = plugin.api().getCommandSpyData().containsUser(staff.getUniqueId());

				if (!isValid || !staff.hasPermission("chatmanager.commandspy")) return;

				this.plugin.getMethods().sendMessage(staff, messages.getString("Command_Spy.Format").replace("{player}", player.getName()).replace("{command}", message), true);
			}
		}
	}
}