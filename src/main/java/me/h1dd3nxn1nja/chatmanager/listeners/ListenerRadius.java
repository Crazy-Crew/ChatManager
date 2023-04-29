package me.h1dd3nxn1nja.chatmanager.listeners;

import java.util.Set;
import java.util.UUID;
import com.ryderbelserion.chatmanager.v1.api.Universal;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import me.h1dd3nxn1nja.chatmanager.Methods;

public class ListenerRadius implements Listener, Universal {

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		Set<Player> recipients = event.getRecipients();

		UUID uuid = player.getUniqueId();

		String localOverrideChar = config.getString("Chat_Radius.Local_Chat.Override_Symbol");
		String globalOverrideChar = config.getString("Chat_Radius.Global_Chat.Override_Symbol");
		String worldOverrideChar = config.getString("Chat_Radius.World_Chat.Override_Symbol");

		int radius = settingsManager.getConfig().getInt("Chat_Radius.Block_Distance");

		if (!config.getBoolean("Chat_Radius.Enable") || plugin.getCrazyManager().api().getStaffChatData().containsUser(uuid)) return;

		if (player.hasPermission("chatmanager.chatradius.global.override")) {
			assert globalOverrideChar != null;

			if (!globalOverrideChar.equals("")) {
				if (ChatColor.stripColor(message).charAt(0) == globalOverrideChar.charAt(0)) {
					plugin.getCrazyManager().api().getWorldChatData().removeUser(uuid);
					plugin.getCrazyManager().api().getLocalChatData().removeUser(uuid);
					plugin.getCrazyManager().api().getGlobalChatData().addUser(uuid);
					return;
				}
			}
		}

		if (player.hasPermission("chatmanager.chatradius.local.override")) {
			assert localOverrideChar != null;

			if (!localOverrideChar.equals("")) {
				if (ChatColor.stripColor(message).charAt(0) == localOverrideChar.charAt(0)) {
					plugin.getCrazyManager().api().getWorldChatData().removeUser(uuid);
					plugin.getCrazyManager().api().getGlobalChatData().removeUser(uuid);
					plugin.getCrazyManager().api().getLocalChatData().addUser(uuid);
					event.setMessage(message);
					return;
				}
			}
		}

		if (player.hasPermission("chatmanager.chatradius.world.override")) {
			assert worldOverrideChar != null;
			if (!worldOverrideChar.equals("")) {
				if (ChatColor.stripColor(message).charAt(0) == worldOverrideChar.charAt(0)) {
					plugin.getCrazyManager().api().getGlobalChatData().removeUser(uuid);
					plugin.getCrazyManager().api().getLocalChatData().removeUser(uuid);
					plugin.getCrazyManager().api().getWorldChatData().addUser(uuid);
					event.setMessage(message);
					return;
				}
			}
		}

		if (plugin.getCrazyManager().api().getLocalChatData().containsUser(uuid)) {
			for (Player receiver : plugin.getServer().getOnlinePlayers()) {
				recipients.remove(receiver);

				if (Methods.inRange(uuid, receiver.getUniqueId(), radius)) {
					recipients.add(player);
					recipients.add(receiver);
				}

				if (plugin.getCrazyManager().api().getSpyChatData().containsUser(receiver.getUniqueId())) recipients.add(receiver);
			}
		}

		if (plugin.getCrazyManager().api().getWorldChatData().containsUser(uuid)) {
			for (Player receiver : plugin.getServer().getOnlinePlayers()) {
				recipients.remove(receiver);

				if (Methods.inWorld(uuid, receiver.getUniqueId())) {
					recipients.add(player);
					recipients.add(receiver);
				}

				if (plugin.getCrazyManager().api().getSpyChatData().containsUser(receiver.getUniqueId())) recipients.add(receiver);
			}
		}
	}
}