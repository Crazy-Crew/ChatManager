package me.me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class ListenerSpy implements Listener {

	public ListenerSpy(ChatManager plugin) {}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {

		FileConfiguration messages = ChatManager.settings.getMessages();
		FileConfiguration config = ChatManager.settings.getConfig();

		List<String> blacklist = config.getStringList("Command_Spy.Blacklist_Commands");

		Player player = event.getPlayer();
		String message = event.getMessage();

		if (!player.hasPermission("chatmanager.bypass.commandspy")) {
			for (String command : blacklist) {
				if (message.toLowerCase().startsWith(command)) {
					return;
				}
			}
			if ((!message.equals("/")) || (!message.equals("//"))) {
				for (Player staff : Bukkit.getOnlinePlayers()) {
					if (Methods.cm_commandSpy.contains(staff.getUniqueId())) {
						if (staff.hasPermission("chatmanager.commandspy")) {
							staff.sendMessage(Methods.color(staff, messages.getString("Command_Spy.Format")
									.replace("{player}", player.getName())
									.replace("{command}", message)
									.replace("{Prefix}", messages.getString("Message.Prefix"))));
						}
					}
				}
			}
		}
	}
}