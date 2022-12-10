package me.h1dd3nxn1nja.chatmanager.managers;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.hooks.HookManager;
import me.h1dd3nxn1nja.chatmanager.hooks.VaultHook;
import java.text.DecimalFormat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholderManager {
	
	public static String setPlaceholders(Player player, String placeholders) {
		
		DecimalFormat df = new DecimalFormat("#,###");
		
		placeholders = Methods.color(player, placeholders.replace("{player}", player.getName()));
		placeholders = Methods.color(player, placeholders.replace("{Prefix}", ChatManager.settings.getMessages().getString("Message.Prefix")));
		placeholders = Methods.color(player, placeholders.replace("{prefix}", ChatManager.settings.getMessages().getString("Message.Prefix")));
		placeholders = Methods.color(player, placeholders.replace("{display_name}", player.getDisplayName()));
		placeholders = Methods.color(player, placeholders.replace("{displayname}", player.getDisplayName()));
		placeholders = Methods.color(player, placeholders.replace("{world}", player.getLocation().getWorld().getName()));
		placeholders = Methods.color(player, placeholders.replace("{online}", df.format(Bukkit.getServer().getOnlinePlayers().size())));
		placeholders = Methods.color(player, placeholders.replace("{server_online}", df.format(Bukkit.getServer().getOnlinePlayers().size())));
		placeholders = Methods.color(player, placeholders.replace("{server_max_players}", df.format(Bukkit.getServer().getMaxPlayers())));
		placeholders = Methods.color(player, placeholders.replace("{server_name}", ChatManager.settings.getConfig().getString("Server_Name")));
		
		if (HookManager.isVaultLoaded()) {
			placeholders = Methods.color(player, placeholders.replace("{vault_prefix}", VaultHook.getPlayerPrefix(player)));
			placeholders = Methods.color(player, placeholders.replace("{vault_suffix}", VaultHook.getPlayerSuffix(player)));
		}

		return placeholders;
	}
}