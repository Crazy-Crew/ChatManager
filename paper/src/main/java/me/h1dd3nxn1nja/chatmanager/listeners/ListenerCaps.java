package me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ListenerCaps implements Listener {

	public ListenerCaps(ChatManager plugin) {}

	public static String upperCase;

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {

		FileConfiguration config = ChatManager.settings.getConfig();
		FileConfiguration messages = ChatManager.settings.getMessages();

		Player player = event.getPlayer();
		String message = event.getMessage();

		if (config.getBoolean("Anti_Caps.Enable")) {
			if (!Methods.cm_staffChat.contains(player.getUniqueId())) {
				if (!player.hasPermission("chatmanager.bypass.caps")) {

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
								player.sendMessage(Methods.color(player, messages.getString("Anti_Caps.Message_Chat")
										.replace("{Prefix}", messages.getString("Message.Prefix"))));
								event.setMessage(message.toLowerCase());
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onCapsCommands(PlayerCommandPreprocessEvent event) {

		FileConfiguration config = ChatManager.settings.getConfig();
		FileConfiguration messages = ChatManager.settings.getMessages();

		Player player = event.getPlayer();
		String message = event.getMessage();

		if (config.getBoolean("Anti_Caps.Enable")) {
			if (config.getBoolean("Anti_Caps.Enable_In_Commands")) {
				if (!Methods.cm_staffChat.contains(player.getUniqueId())) {
					if (!player.hasPermission("chatmanager.bypass.caps")) {

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
									player.sendMessage(Methods.color(player, messages.getString("Anti_Caps.Message_Commands")
											.replace("{Prefix}", messages.getString("Message.Prefix"))));
									event.setMessage(message.toLowerCase());
								}
							}
						}
					}
				}
			}
		}
	}
}