package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.api.objects.PaperUser;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.core.PlayerState;
import com.ryderbelserion.chatmanager.utils.UserUtils;
import com.ryderbelserion.chatmanager.enums.Permissions;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ListenerCaps implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final Player player = event.getPlayer();
		final String message = event.getMessage();

		final PaperUser user = UserUtils.getUser(player);

		if (!config.getBoolean("Anti_Caps.Enable", false) || user.hasState(PlayerState.STAFF_CHAT)) return;

		if (Permissions.BYPASS_CAPS.hasPermission(player)) return;

		int upperChar = 0;
		int lowerChar = 0;

		if (message.toLowerCase().length() >= config.getInt("Anti_Caps.Min_Message_Length", 5)) {
			for (int number = 0; number < message.length(); number++) {
				if (Character.isLetter(message.charAt(number))) {
					if (Character.isUpperCase(message.charAt(number))) {
						upperChar++;
					} else {
						lowerChar++;
					}
				}
			}

			if (upperChar + lowerChar != 0) {
				if (1.0D * upperChar / (upperChar + lowerChar) * 100.0D >= config.getInt("Anti_Caps.Required_Percentage", 70)) {
					Messages.ANTI_CAPS_MESSAGE_CHAT.sendMessage(player);

					event.setMessage(message.toLowerCase());
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onCapsCommands(PlayerCommandPreprocessEvent event) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final Player player = event.getPlayer();
		final String message = event.getMessage();

		final PaperUser user = UserUtils.getUser(player);

		if (!config.getBoolean("Anti_Caps.Enable", false) && !config.getBoolean("Anti_Caps.Enable_In_Commands", false) || user.hasState(PlayerState.STAFF_CHAT)) return;

		if (Permissions.BYPASS_CAPS.hasPermission(player)) return;

		int upperChar = 0;
		int lowerChar = 0;

		if (message.toLowerCase().length() >= config.getInt("Anti_Caps.Min_Message_Length", 5)) {
			for (int number = 0; number < message.length(); number++) {
				if (Character.isLetter(message.charAt(number))) {
					if (Character.isUpperCase(message.charAt(number))) {
						upperChar++;
					} else {
						lowerChar++;
					}
				}
			}

			if (upperChar + lowerChar != 0) {
				if (1.0D * upperChar / (upperChar + lowerChar) * 100.0D >= config.getInt("Anti_Caps.Required_Percentage", 70)) {
					Messages.ANTI_CAPS_MESSAGE_COMMANDS.sendMessage(player);

					event.setMessage(message.toLowerCase());
				}
			}
		}
	}
}