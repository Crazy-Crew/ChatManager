package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import java.util.Set;
import java.util.UUID;

public class ListenerRadius extends Global implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();
		final String message = event.getMessage();
		final Set<Player> recipients = event.getRecipients();

		final UUID uuid = player.getUniqueId();

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final String localOverrideChar = config.getString("Chat_Radius.Local_Chat.Override_Symbol", "#");
		final String globalOverrideChar = config.getString("Chat_Radius.Global_Chat.Override_Symbol", "!");
		final String worldOverrideChar = config.getString("Chat_Radius.World_Chat.Override_Symbol", "$");

		final int radius = config.getInt("Chat_Radius.Block_Distance", 250);

		if (!config.getBoolean("Chat_Radius.Enable", false) || this.staffChatData.containsUser(uuid)) return;

		if (player.hasPermission(Permissions.CHAT_RADIUS_GLOBAL_OVERRIDE.getNode())) {
			if (!globalOverrideChar.isEmpty()) {
				if (ChatColor.stripColor(message).charAt(0) == globalOverrideChar.charAt(0)) {
					this.worldChatData.removeUser(uuid);
					this.localChatData.removeUser(uuid);
					this.globalChatData.addUser(uuid);

					return;
				}
			}
		}

		if (player.hasPermission(Permissions.CHAT_RADIUS_LOCAL_OVERRIDE.getNode())) {
			if (!localOverrideChar.isEmpty()) {
				if (ChatColor.stripColor(message).charAt(0) == localOverrideChar.charAt(0)) {
					this.plugin.api().getWorldChatData().removeUser(uuid);
					this.globalChatData.removeUser(uuid);
					this.localChatData.addUser(uuid);

					event.setMessage(message.replace(localOverrideChar, ""));

					return;
				}
			}
		}

		if (player.hasPermission(Permissions.CHAT_RADIUS_WORLD_OVERRIDE.getNode())) {
			if (!worldOverrideChar.isEmpty()) {
				if (ChatColor.stripColor(message).charAt(0) == worldOverrideChar.charAt(0)) {
					this.globalChatData.removeUser(uuid);
					this.localChatData.removeUser(uuid);
					this.worldChatData.addUser(uuid);

					event.setMessage(message.replace(worldOverrideChar, ""));

					return;
				}
			}
		}

		if (this.localChatData.containsUser(uuid)) {
			recipients.clear();

			for (Player receiver : this.server.getOnlinePlayers()) {
				if (Methods.inRange(uuid, receiver.getUniqueId(), radius)) {
					recipients.add(player);
					recipients.add(receiver);
				}

				if (this.spyChatData.containsUser(receiver.getUniqueId())) recipients.add(receiver);
			}
		}

		if (this.worldChatData.containsUser(uuid)) {
			recipients.clear();

			for (Player receiver : this.server.getOnlinePlayers()) {
				if (Methods.inWorld(uuid, receiver.getUniqueId())) {
					recipients.add(player);
					recipients.add(receiver);
				}

				if (this.spyChatData.containsUser(receiver.getUniqueId())) recipients.add(receiver);
			}
		}
	}
}