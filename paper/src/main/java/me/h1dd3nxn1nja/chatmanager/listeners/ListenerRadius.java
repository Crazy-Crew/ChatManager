package me.h1dd3nxn1nja.chatmanager.listeners;

import java.util.Set;
import java.util.UUID;
import com.ryderbelserion.chatmanager.enums.Files;
import me.h1dd3nxn1nja.chatmanager.ChatManagerMercurioMC;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

public class ListenerRadius implements Listener {

	@NotNull
	private final ChatManagerMercurioMC plugin = ChatManagerMercurioMC.get();

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		Set<Player> recipients = event.getRecipients();

		UUID uuid = player.getUniqueId();

		FileConfiguration config = Files.CONFIG.getConfiguration();

		String localOverrideChar = config.getString("Chat_Radius.Local_Chat.Override_Symbol", "#");
		String globalOverrideChar = config.getString("Chat_Radius.Global_Chat.Override_Symbol", "!");
		String worldOverrideChar = config.getString("Chat_Radius.World_Chat.Override_Symbol", "$");

		int radius = config.getInt("Chat_Radius.Block_Distance");

		if (!config.getBoolean("Chat_Radius.Enable") || this.plugin.api().getStaffChatData().containsUser(uuid)) return;

		if (player.hasPermission(Permissions.CHAT_RADIUS_GLOBAL_OVERRIDE.getNode())) {
			if (!globalOverrideChar.isEmpty()) {
				if (ChatColor.stripColor(message).charAt(0) == globalOverrideChar.charAt(0)) {
					this.plugin.api().getWorldChatData().removeUser(uuid);
					this.plugin.api().getLocalChatData().removeUser(uuid);
					this.plugin.api().getGlobalChatData().addUser(uuid);

					return;
				}
			}
		}

		if (player.hasPermission(Permissions.CHAT_RADIUS_LOCAL_OVERRIDE.getNode())) {
			if (!localOverrideChar.isEmpty()) {
				if (ChatColor.stripColor(message).charAt(0) == localOverrideChar.charAt(0)) {
					this.plugin.api().getWorldChatData().removeUser(uuid);
					this.plugin.api().getGlobalChatData().removeUser(uuid);
					this.plugin.api().getLocalChatData().addUser(uuid);

					event.setMessage(message.replace(localOverrideChar, ""));

					return;
				}
			}
		}

		if (player.hasPermission(Permissions.CHAT_RADIUS_WORLD_OVERRIDE.getNode())) {
			if (!worldOverrideChar.isEmpty()) {
				if (ChatColor.stripColor(message).charAt(0) == worldOverrideChar.charAt(0)) {
					this.plugin.api().getGlobalChatData().removeUser(uuid);
					this.plugin.api().getLocalChatData().removeUser(uuid);
					this.plugin.api().getWorldChatData().addUser(uuid);

					event.setMessage(message.replace(worldOverrideChar, ""));

					return;
				}
			}
		}

		if (this.plugin.api().getLocalChatData().containsUser(uuid)) {
			recipients.clear();

			for (Player receiver : this.plugin.getServer().getOnlinePlayers()) {
				if (Methods.inRange(uuid, receiver.getUniqueId(), radius)) {
					recipients.add(player);
					recipients.add(receiver);
				}

				if (plugin.api().getSpyChatData().containsUser(receiver.getUniqueId())) recipients.add(receiver);
			}
		}

		if (this.plugin.api().getWorldChatData().containsUser(uuid)) {
			recipients.clear();

			for (Player receiver : this.plugin.getServer().getOnlinePlayers()) {
				if (Methods.inWorld(uuid, receiver.getUniqueId())) {
					recipients.add(player);
					recipients.add(receiver);
				}

				if (this.plugin.api().getSpyChatData().containsUser(receiver.getUniqueId())) recipients.add(receiver);
			}
		}
	}
}