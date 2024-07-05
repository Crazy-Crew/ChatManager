package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;

public class ListenerCaps implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		FileConfiguration config = Files.CONFIG.getConfiguration();
		FileConfiguration messages = Files.MESSAGES.getConfiguration();

		Player player = event.getPlayer();
		String message = event.getMessage();

		if (!config.getBoolean("Anti_Caps.Enable") || plugin.api().getStaffChatData().containsUser(player.getUniqueId())) return;

		if (player.hasPermission(Permissions.BYPASS_CAPS.getNode())) return;

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
					Methods.sendMessage(player, messages.getString("Anti_Caps.Message_Chat"), true);
					event.setMessage(message.toLowerCase());
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onCapsCommands(PlayerCommandPreprocessEvent event) {
		FileConfiguration config = Files.CONFIG.getConfiguration();
		FileConfiguration messages = Files.MESSAGES.getConfiguration();

		Player player = event.getPlayer();
		String message = event.getMessage();

		if (!config.getBoolean("Anti_Caps.Enable") && !config.getBoolean("Anti_Caps.Enable_In_Commands") || plugin.api().getStaffChatData().containsUser(player.getUniqueId())) return;

		if (player.hasPermission(Permissions.BYPASS_CAPS.getNode())) return;

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
					Methods.sendMessage(player, messages.getString("Anti_Caps.Message_Commands"), true);
					event.setMessage(message.toLowerCase());
				}
			}
		}
	}
}