package me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import java.util.List;

public class ListenerSpy implements Listener {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	@EventHandler(ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		FileConfiguration messages = settingsManager.getMessages();
		FileConfiguration config = settingsManager.getConfig();

		List<String> blacklist = config.getStringList("Command_Spy.Blacklist_Commands");

		Player player = event.getPlayer();
		String message = event.getMessage();

		if (!player.hasPermission("chatmanager.bypass.commandspy")) {
			for (String command : blacklist) {
				if (message.toLowerCase().startsWith(command)) return;
			}

			if ((!message.equals("/")) || (!message.equals("//"))) {
				for (Player staff : plugin.getServer().getOnlinePlayers()) {
					if (Methods.cm_commandSpy.contains(staff.getUniqueId())) {
						if (staff.hasPermission("chatmanager.commandspy")) {
							Methods.sendMessage(staff, messages.getString("Command_Spy.Format")
									.replace("{player}", player.getName()).replace("{command}", message), true);
						}
					}
				}
			}
		}
	}
}