package me.h1dd3nxn1nja.chatmanager.paper.listeners;

import com.ryderbelserion.chatmanager.paper.files.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ListenerCaps implements Listener {

	private final ChatManager plugin = ChatManager.getPlugin();

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		FileConfiguration config = Files.CONFIG.getFile();
		FileConfiguration messages = Files.MESSAGES.getFile();

		Player player = event.getPlayer();
		String message = event.getMessage();

		if (!config.getBoolean("Anti_Caps.Enable") || plugin.api().getStaffChatData().containsUser(player.getUniqueId())) return;

		if (player.hasPermission("chatmanager.bypass.caps")) return;

		int upperChar = 0;
		int lowerChar = 0;

		if (event.getMessage().toLowerCase().length() >= config.getInt("Anti_Caps.Min_Message_Length")) {
			for (int number = 0; number < message.length(); number++) {
				if (Character.isLetter(message.charAt(number))) {
					if (Character.isUpperCase(event.getMessage().charAt(number))) {
						upperChar++;
					} else {
						lowerChar++;
					}
				}
			}

			if (upperChar + lowerChar != 0) {
				if (1.0D * upperChar / (upperChar + lowerChar) * 100.0D >= config.getInt("Anti_Caps.Required_Percentage")) {
					this.plugin.getMethods().sendMessage(player, messages.getString("Anti_Caps.Message_Chat"), true);
					event.setMessage(message.toLowerCase());
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onCapsCommands(PlayerCommandPreprocessEvent event) {
		FileConfiguration config = Files.CONFIG.getFile();
		FileConfiguration messages = Files.MESSAGES.getFile();

		Player player = event.getPlayer();
		String message = event.getMessage();

		if (!config.getBoolean("Anti_Caps.Enable") && !config.getBoolean("Anti_Caps.Enable_In_Commands") || plugin.api().getStaffChatData().containsUser(player.getUniqueId())) return;

		if (player.hasPermission("chatmanager.bypass.caps")) return;

		int upperChar = 0;
		int lowerChar = 0;

		if (event.getMessage().toLowerCase().length() >= config.getInt("Anti_Caps.Min_Message_Length")) {
			for (int number = 0; number < message.length(); number++) {
				if (Character.isLetter(message.charAt(number))) {
					if (Character.isUpperCase(event.getMessage().charAt(number))) {
						upperChar++;
					} else {
						lowerChar++;
					}
				}
			}

			if (upperChar + lowerChar != 0) {
				if (1.0D * upperChar / (upperChar + lowerChar) * 100.0D >= config.getInt("Anti_Caps.Required_Percentage")) {
					this.plugin.getMethods().sendMessage(player, messages.getString("Anti_Caps.Message_Commands"), true);
					event.setMessage(message.toLowerCase());
				}
			}
		}
	}
}