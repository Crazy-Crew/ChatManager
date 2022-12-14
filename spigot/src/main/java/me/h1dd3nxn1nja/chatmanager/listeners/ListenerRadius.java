package me.h1dd3nxn1nja.chatmanager.listeners;

import java.util.Set;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;

public class ListenerRadius implements Listener {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		FileConfiguration config = settingsManager.getConfig();
		Player player = event.getPlayer();
		String message = event.getMessage();
		Set<Player> recipients = event.getRecipients();

		String localOverrideChar = config.getString("Chat_Radius.Local_Chat.Override_Symbol");
		String globalOverrideChar = config.getString("Chat_Radius.Global_Chat.Override_Symbol");
		String worldOverrideChar = config.getString("Chat_Radius.World_Chat.Override_Symbol");

		int radius = settingsManager.getConfig().getInt("Chat_Radius.Block_Distance");

		if (config.getBoolean("Chat_Radius.Enable")) {
			if (!Methods.cm_staffChat.contains(player.getUniqueId())) {
				if (player.hasPermission("chatmanager.chatradius.global.override")) {
					assert globalOverrideChar != null;
					if (!globalOverrideChar.equals("")) {
						if (ChatColor.stripColor(message).charAt(0) == globalOverrideChar.charAt(0)) {
								Methods.cm_worldChat.remove(player.getUniqueId());
								Methods.cm_localChat.remove(player.getUniqueId());
								Methods.cm_globalChat.add(player.getUniqueId());
							return;
						}
					}
				}

				if (player.hasPermission("chatmanager.chatradius.local.override")) {
					assert localOverrideChar != null;
					if (!localOverrideChar.equals("")) {
						if (ChatColor.stripColor(message).charAt(0) == localOverrideChar.charAt(0)) {
								Methods.cm_worldChat.remove(player.getUniqueId());
								Methods.cm_globalChat.remove(player.getUniqueId());
								Methods.cm_localChat.add(player.getUniqueId());
								event.setMessage(message);
							return;
						}
					}
				}

				if (player.hasPermission("chatmanager.chatradius.world.override")) {
					assert worldOverrideChar != null;
					if (!worldOverrideChar.equals("")) {
						if (ChatColor.stripColor(message).charAt(0) == worldOverrideChar.charAt(0)) {
								Methods.cm_localChat.remove(player.getUniqueId());
								Methods.cm_globalChat.remove(player.getUniqueId());
								Methods.cm_worldChat.add(player.getUniqueId());
								event.setMessage(message);
							return;
						}
					}
				}

				if (Methods.cm_localChat.contains(player.getUniqueId())) {
					for (Player receiver : plugin.getServer().getOnlinePlayers()) {
						recipients.remove(receiver);

						if (Methods.inRange(player, receiver, radius)) {
							recipients.add(player);
							recipients.add(receiver);
						}

						if (Methods.cm_spyChat.contains(receiver.getUniqueId())) recipients.add(receiver);
					}
				}

				if (Methods.cm_worldChat.contains(player.getUniqueId())) {
					for (Player receiver : plugin.getServer().getOnlinePlayers()) {
						recipients.remove(receiver);

						if (Methods.inWorld(player, receiver)) {
							recipients.add(player);
							recipients.add(receiver);
						}

						if (Methods.cm_spyChat.contains(receiver.getUniqueId())) recipients.add(receiver);
					}
				}
			}
		}
	}
}