package me.h1dd3nxn1nja.chatmanager.listeners;

import java.util.Set;
import java.util.UUID;
import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.chat.GlobalChatData;
import com.ryderbelserion.chatmanager.api.chat.LocalChatData;
import com.ryderbelserion.chatmanager.api.chat.SpyChatData;
import com.ryderbelserion.chatmanager.api.chat.StaffChatData;
import com.ryderbelserion.chatmanager.api.chat.WorldChatData;
import com.ryderbelserion.chatmanager.enums.Files;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

public class ListenerRadius implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	private final Server server = this.plugin.getServer();

	private final ApiLoader api = this.plugin.api();

	private final LocalChatData localChatData = this.api.getLocalChatData();

	private final GlobalChatData globalChatData = this.api.getGlobalChatData();

	private final WorldChatData worldChatData = this.api.getWorldChatData();

	private final SpyChatData spyChatData = this.api.getSpyChatData();

	private final StaffChatData staffChatData = this.api.getStaffChatData();

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

		if (!globalOverrideChar.isEmpty() && player.hasPermission(Permissions.CHAT_RADIUS_GLOBAL_OVERRIDE.getNode())) {
            if (ChatColor.stripColor(message).charAt(0) == globalOverrideChar.charAt(0)) {
                this.worldChatData.removeUser(uuid);
                this.localChatData.removeUser(uuid);
                this.globalChatData.addUser(uuid);

                return;
            }
        }

		if (!localOverrideChar.isEmpty() && player.hasPermission(Permissions.CHAT_RADIUS_LOCAL_OVERRIDE.getNode())) {
            if (ChatColor.stripColor(message).charAt(0) == localOverrideChar.charAt(0)) {
                this.worldChatData.removeUser(uuid);
                this.globalChatData.removeUser(uuid);
                this.localChatData.addUser(uuid);

                event.setMessage(message.replace(localOverrideChar, ""));

                return;
            }
        }

		if (!worldOverrideChar.isEmpty() && player.hasPermission(Permissions.CHAT_RADIUS_WORLD_OVERRIDE.getNode())) {
			if (ChatColor.stripColor(message).charAt(0) == worldOverrideChar.charAt(0)) {
				this.globalChatData.removeUser(uuid);
				this.localChatData.removeUser(uuid);
				this.worldChatData.addUser(uuid);

				event.setMessage(message.replace(worldOverrideChar, ""));

				return;
			}
		}

		if (this.localChatData.containsUser(uuid)) {
			recipients.clear();

			for (final Player receiver : this.server.getOnlinePlayers()) {
				if (Methods.inRange(uuid, receiver.getUniqueId(), radius)) {
					recipients.add(player);
					recipients.add(receiver);
				}

				if (this.spyChatData.containsUser(receiver.getUniqueId())) recipients.add(receiver);
			}
		}

		if (this.worldChatData.containsUser(uuid)) {
			recipients.clear();

			for (final Player receiver : this.server.getOnlinePlayers()) {
				if (Methods.inWorld(uuid, receiver.getUniqueId())) {
					recipients.add(player);
					recipients.add(receiver);
				}

				if (this.spyChatData.containsUser(receiver.getUniqueId())) recipients.add(receiver);
			}
		}
	}
}