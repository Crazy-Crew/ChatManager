package com.ryderbelserion.chatmanager.api.support.types.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;
import java.text.DecimalFormat;

public class PlaceholderAPISupport extends PlaceholderExpansion {

	public @Nullable String onRequest(OfflinePlayer player, @NotNull String identifier) {
		if (!player.isOnline()) return "";

		Player playerOnline = (Player) player;

		String lower = identifier.toLowerCase();
		DecimalFormat format = new DecimalFormat("#,###");

		switch (lower) { // Returns which chat radius channel the player is in.
			if (plugin.getCrazyManager().api().getLocalChatData().containsUser(player.getUniqueId())) {
				return settingsManager.getConfig().getString("Chat_Radius.Local_Chat.Prefix");
			} else if (plugin.getCrazyManager().api().getGlobalChatData().containsUser(player.getUniqueId())) {
				return settingsManager.getConfig().getString("Chat_Radius.Global_Chat.Prefix");
			} else if (plugin.getCrazyManager().api().getWorldChatData().containsUser(player.getUniqueId())) {
				return settingsManager.getConfig().getString("Chat_Radius.World_Chat.Prefix");
			}

			case "radius", "toggle_pm" -> { // Returns if the toggle pm is enabled/disabled for a player.
				return plugin.getCrazyManager().api().getToggleMessageData().containsUser(player.getUniqueId()) ? "Enabled" : "Disabled";
			}

			case "toggle_chat" -> { // Returns if the toggle chat is enabled/disabled for a player.
				return plugin.getCrazyManager().api().getToggleChatData().containsUser(player.getUniqueId()) ? "Enabled" : "Disabled";
			}

			case "command_spy" -> { // Returns if the command spy is enabled/disabled for a player.
				return plugin.getCrazyManager().api().getCommandSpyData().containsUser(player.getUniqueId()) ? "Enabled" : "Disabled";
			}

			case "social_spy" -> { // Returns if the social spy is enabled/disabled for a player.
				return plugin.getCrazyManager().api().getSocialSpyData().containsUser(player.getUniqueId()) ? "Enabled" : "Disabled";
			}

			case "staff_chat" -> { // Returns if the staff chat is enabled/disabled for a player.
				return plugin.getCrazyManager().api().getStaffChatData().containsUser(player.getUniqueId()) ? "Enabled" : "Disabled";
			}

			case "mute_chat" -> { // Returns if mute chat is enabled/disabled.
				return Methods.isMuted() ? "Enabled" : "Disabled";
			}

			case "ping" -> {
				CraftPlayer craftPlayer = (CraftPlayer) playerOnline;
				return format.format(craftPlayer.getPing());
			}

			default -> {
				return "";
			}
		}
	}

	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public @NotNull String getIdentifier() {
		return "chatmanager";
	}

	@Override
	public @NotNull String getAuthor() {
		return "H1DD3NxN1NJA";
	}


	@Override
	public @NotNull String getVersion() {
		return "";
	}
}