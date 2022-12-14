package me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ListenerMuteChat implements Listener {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();
	
	@EventHandler
	public void muteChat(AsyncPlayerChatEvent event) {
		FileConfiguration messages = settingsManager.getMessages();
		
		Player player = event.getPlayer();
		
		if (!player.hasPermission("chatmanager.bypass.mutechat")) {
			if (Methods.getMuted()) {
				player.sendMessage(Methods.color(player, messages.getString("Mute_Chat.Denied_Message").replace("{Prefix}", messages.getString("Message.Prefix"))));
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		FileConfiguration config = settingsManager.getConfig();
		FileConfiguration messages = settingsManager.getMessages();
		
		Player player = event.getPlayer();
		
		if (config.getBoolean("Mute_Chat.Disable_Commands")) {
			if (!player.hasPermission("chatmanager.bypass.mutechat")) {
				if (Methods.getMuted()) {
					for (String command : config.getStringList("Mute_Chat.Disabled_Commands")) {
						if (event.getMessage().toLowerCase().contains(command)) {
							player.sendMessage(Methods.color(player, messages.getString("Mute_Chat.Blocked_Commands.Message").replace("{Prefix}", messages.getString("Message.Prefix"))));
							event.setCancelled(true);
							return;
						}
					}
				}
			}
		}
	}
}