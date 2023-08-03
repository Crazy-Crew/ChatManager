package me.h1dd3nxn1nja.chatmanager.paper.listeners;

import java.util.Set;
import java.util.UUID;

import com.ryderbelserion.chatmanager.paper.files.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import me.h1dd3nxn1nja.chatmanager.paper.Methods;

public class ListenerRadius implements Listener {

	private final ChatManager plugin = ChatManager.getPlugin();

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		Set<Player> recipients = event.getRecipients();

		UUID uuid = player.getUniqueId();

		FileConfiguration config = Files.CONFIG.getFile();

		String localOverrideChar = config.getString("Chat_Radius.Local_Chat.Override_Symbol");
		String globalOverrideChar = config.getString("Chat_Radius.Global_Chat.Override_Symbol");
		String worldOverrideChar = config.getString("Chat_Radius.World_Chat.Override_Symbol");

		int radius = config.getInt("Chat_Radius.Block_Distance");

		if (!config.getBoolean("Chat_Radius.Enable") || plugin.api().getStaffChatData().containsUser(uuid)) return;

		if (player.hasPermission("chatmanager.chatradius.global.override")) {
			assert globalOverrideChar != null;
			if (!globalOverrideChar.equals("")) {
				if (ChatColor.stripColor(message).charAt(0) == globalOverrideChar.charAt(0)) {
					plugin.api().getWorldChatData().removeUser(uuid);
					plugin.api().getLocalChatData().removeUser(uuid);
					plugin.api().getGlobalChatData().addUser(uuid);
					return;
				}
			}
		}

		if (player.hasPermission("chatmanager.chatradius.local.override")) {
			assert localOverrideChar != null;

			if (!localOverrideChar.equals("")) {
				if (ChatColor.stripColor(message).charAt(0) == localOverrideChar.charAt(0)) {
					plugin.api().getWorldChatData().removeUser(uuid);
					plugin.api().getGlobalChatData().removeUser(uuid);
					plugin.api().getLocalChatData().addUser(uuid);
					event.setMessage(message);
					return;
				}
			}
		}

		if (player.hasPermission("chatmanager.chatradius.world.override")) {
			assert worldOverrideChar != null;
			if (!worldOverrideChar.equals("")) {
				if (ChatColor.stripColor(message).charAt(0) == worldOverrideChar.charAt(0)) {
					plugin.api().getGlobalChatData().removeUser(uuid);
					plugin.api().getLocalChatData().removeUser(uuid);
					plugin.api().getWorldChatData().addUser(uuid);
					event.setMessage(message);
					return;
				}
			}
		}

		if (plugin.api().getLocalChatData().containsUser(uuid)) {
			for (Player receiver : plugin.getServer().getOnlinePlayers()) {
				recipients.remove(receiver);

				if (this.plugin.getMethods().inRange(uuid, receiver.getUniqueId(), radius)) {
					recipients.add(player);
					recipients.add(receiver);
				}

				if (plugin.api().getSpyChatData().containsUser(receiver.getUniqueId())) recipients.add(receiver);
			}
		}

		if (plugin.api().getWorldChatData().containsUser(uuid)) {
			for (Player receiver : plugin.getServer().getOnlinePlayers()) {
				recipients.remove(receiver);

				if (this.plugin.getMethods().inWorld(uuid, receiver.getUniqueId())) {
					recipients.add(player);
					recipients.add(receiver);
				}

				if (plugin.api().getSpyChatData().containsUser(receiver.getUniqueId())) recipients.add(receiver);
			}
		}
	}
}