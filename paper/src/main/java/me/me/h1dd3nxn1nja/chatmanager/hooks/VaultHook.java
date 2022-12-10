package me.me.h1dd3nxn1nja.chatmanager.hooks;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook {

	public static Permission permission;
	public static Chat chat;

	public VaultHook() {
		permission = null;
		chat = null;
	}

	public static boolean hook() {
		setupChat();
		setupPermissions();
		return true;
	}

	public static String getPlayerPrefix(Player player) {
		if (chat.getPlayerPrefix(player) == null) {
			return "";
		}
		return chat.getPlayerPrefix(player);
	}

	public static String getPlayerSuffix(Player player) {
		if (chat.getPlayerSuffix(player) == null) {
			return "";
		}
		return chat.getPlayerSuffix(player);
	}

	public static String getPlayerGroup(Player player) {
		if (chat.getPrimaryGroup(player) == null) {
			return "";
		}
		return chat.getPrimaryGroup(player);
	}

	public static boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager()
				.getRegistration(Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

	public static boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager()
				.getRegistration(Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}

		return (chat != null);
	}
}