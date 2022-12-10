package me.h1dd3nxn1nja.chatmanager.hooks;

import java.text.DecimalFormat;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.utils.Ping;

public class PlaceholderAPIHook extends PlaceholderExpansion {
	
	public PlaceholderAPIHook() {}
	
	private Plugin plugin;
	
	public PlaceholderAPIHook(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public String getIdentifier() {
		return "chatmanager";
	}
	
	@Override
	public String getPlugin() {
		return plugin.getDescription().getName();
	}
	
	@Override
	public String getAuthor() {
		return "H1DD3NxN1NJA";
	}
	
	@Override
	public String getVersion() {
		return plugin.getDescription().getVersion();
	}
	
	@Override
	public boolean persist() {
		return true;
	}
	
	@Override
	public String onPlaceholderRequest(Player player, String identifier) {

		String lower = identifier.toLowerCase();
		DecimalFormat df = new DecimalFormat("#,###");

		switch (lower) {
		case "radius": //Returns which chat radius channel the player is in.
			if (Methods.cm_localChat.contains(player.getUniqueId())) {
				return ChatManager.settings.getConfig().getString("Chat_Radius.Local_Chat.Prefix");
			} else if (Methods.cm_globalChat.contains(player.getUniqueId())) {
				return ChatManager.settings.getConfig().getString("Chat_Radius.Global_Chat.Prefix");
			} else if (Methods.cm_worldChat.contains(player.getUniqueId())) {
				return ChatManager.settings.getConfig().getString("Chat_Radius.World_Chat.Prefix");
			}
		case "toggle_pm": //Returns if the toggle pm is enabled/disabled for a player.
			if (Methods.cm_togglePM.contains(player.getUniqueId())) {
				return "Enabled";
			} else {
				return "Disabled";
			}
		case "toggle_chat": //Returns if the toggle chat is enabled/disabled for a player.
			if (Methods.cm_toggleChat.contains(player.getUniqueId())) {
				return "Enabled";
			} else {
				return "Disabled";
			}
		case "command_spy": //Returns if the command spy is enabled/disabled for a player.
			if (Methods.cm_commandSpy.contains(player.getUniqueId())) {
				return "Enabled";
			} else {
				return "Disabled";
			}
		case "social_spy": //Returns if the social spy is enabled/disabled for a player.
			if (Methods.cm_socialSpy.contains(player.getUniqueId())) {
				return "Enabled";
			} else {
				return "Disabled";
			}
		case "staff_chat": //Returns if the staff chat is enabled/disabled for a player.
			if (Methods.cm_staffChat.contains(player.getUniqueId())) {
				return "Enabled";
			} else {
				return "Disabled";
			}
		case "mute_chat": //Returns if mute chat is enabled/disabled.
			if (Methods.getMuted()) {
				return "Enabled";
			} else {
				return "Disabled";
			}
		case "ping":
			return df.format(Ping.getPing(player));
		default:
			return "";
		}
	}	
}