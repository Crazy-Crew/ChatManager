package me.h1dd3nxn1nja.chatmanager.paper.support.placeholders;

import java.text.DecimalFormat;
import me.h1dd3nxn1nja.chatmanager.paper.SettingsManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.Methods;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;

public class PlaceholderAPISupport extends PlaceholderExpansion {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	@Override
	public @Nullable String onRequest(OfflinePlayer player, @NotNull String identifier) {
		if (player.isOnline()) {
			Player playerOnline = (Player) player;

			String lower = identifier.toLowerCase();
			DecimalFormat df = new DecimalFormat("#,###");

			switch (lower) {
				case "radius": //Returns which chat radius channel the player is in.
					if (plugin.api().getLocalChatData().containsUser(player.getUniqueId())) {
						return settingsManager.getConfig().getString("Chat_Radius.Local_Chat.Prefix");
					} else if (plugin.api().getGlobalChatData().containsUser(player.getUniqueId())) {
						return settingsManager.getConfig().getString("Chat_Radius.Global_Chat.Prefix");
					} else if (plugin.api().getWorldChatData().containsUser(player.getUniqueId())) {
						return settingsManager.getConfig().getString("Chat_Radius.World_Chat.Prefix");
					}
				case "toggle_pm": // Returns if the toggle pm is enabled/disabled for a player.
					return plugin.api().getToggleMessageData().containsUser(player.getUniqueId()) ? "Enabled" : "Disabled";
				case "toggle_chat": // Returns if the toggle chat is enabled/disabled for a player.
					return plugin.api().getToggleChatData().containsUser(player.getUniqueId()) ? "Enabled" : "Disabled";
				case "command_spy": // Returns if the command spy is enabled/disabled for a player.
					return plugin.api().getCommandSpyData().containsUser(player.getUniqueId()) ? "Enabled" : "Disabled";
				case "social_spy": // Returns if the social spy is enabled/disabled for a player.
					return plugin.api().getSocialSpyData().containsUser(player.getUniqueId()) ? "Enabled" : "Disabled";
				case "staff_chat": // Returns if the staff chat is enabled/disabled for a player.
					return plugin.api().getStaffChatData().containsUser(player.getUniqueId()) ? "Enabled" : "Disabled";
				case "mute_chat": // Returns if mute chat is enabled/disabled.
					return Methods.isMuted() ? "Enabled" : "Disabled";
				case "ping":
					CraftPlayer craftPlayer = (CraftPlayer) playerOnline;
					return df.format(craftPlayer.getPing());
				default:
					return "";
			}
		}

		return "";
	}

	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public @NotNull String getIdentifier() {
		return plugin.getName().toLowerCase();
	}

	@Override
	public @NotNull String getAuthor() {
		return "H1DD3NxN1NJA";
	}


	@Override
	public @NotNull String getVersion() {
		return plugin.getDescription().getVersion();
	}
}