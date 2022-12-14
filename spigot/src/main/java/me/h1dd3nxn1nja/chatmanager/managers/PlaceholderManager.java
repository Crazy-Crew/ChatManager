package me.h1dd3nxn1nja.chatmanager.managers;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import me.h1dd3nxn1nja.chatmanager.hooks.EssentialsHook;
import me.h1dd3nxn1nja.chatmanager.hooks.HookManager;
import me.h1dd3nxn1nja.chatmanager.hooks.VaultHook;
import java.text.DecimalFormat;
import org.bukkit.entity.Player;

public class PlaceholderManager {

	private static final ChatManager plugin = ChatManager.getPlugin();

	private static final SettingsManager settingsManager = plugin.getSettingsManager();

	public static String setPlaceholders(Player player, String placeholders) {
		DecimalFormat df = new DecimalFormat("#,###");
		
		placeholders = Methods.color(player, placeholders.replace("{player}", player.getName()));
		placeholders = Methods.color(player, placeholders.replace("{Prefix}", settingsManager.getMessages().getString("Message.Prefix")));
		placeholders = Methods.color(player, placeholders.replace("{prefix}", settingsManager.getMessages().getString("Message.Prefix")));
		placeholders = Methods.color(player, placeholders.replace("{display_name}", player.getDisplayName()));
		placeholders = Methods.color(player, placeholders.replace("{displayname}", player.getDisplayName()));
		placeholders = Methods.color(player, placeholders.replace("{world}", player.getLocation().getWorld().getName()));
		placeholders = Methods.color(player, placeholders.replace("{online}", df.format(plugin.getServer().getOnlinePlayers().size())));
		placeholders = Methods.color(player, placeholders.replace("{server_online}", df.format(plugin.getServer().getOnlinePlayers().size())));
		placeholders = Methods.color(player, placeholders.replace("{server_max_players}", df.format(plugin.getServer().getMaxPlayers())));
		placeholders = Methods.color(player, placeholders.replace("{server_name}", settingsManager.getConfig().getString("Server_Name")));
		
		if (HookManager.isVaultLoaded()) {
			placeholders = Methods.color(player, placeholders.replace("{vault_prefix}", VaultHook.getPlayerPrefix(player)));
			placeholders = Methods.color(player, placeholders.replace("{vault_suffix}", VaultHook.getPlayerSuffix(player)));
		}
		
		if (HookManager.isEssentialsLoaded()) {
			placeholders = Methods.color(player, placeholders.replace("{ess_player_balance}", EssentialsHook.getPlayersBalance(player)));
			placeholders = Methods.color(player, placeholders.replace("{ess_player_nickname}", EssentialsHook.getPlayersNickname(player)));
		}

		return placeholders;
	}
}