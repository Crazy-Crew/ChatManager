package me.h1dd3nxn1nja.chatmanager.managers;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import me.h1dd3nxn1nja.chatmanager.support.EssentialsSupport;
import me.h1dd3nxn1nja.chatmanager.support.PluginSupport;
import me.h1dd3nxn1nja.chatmanager.support.misc.VaultSupport;
import java.text.DecimalFormat;
import org.bukkit.entity.Player;

public class PlaceholderManager {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	private final VaultSupport vaultSupport = plugin.getPluginManager().getVaultSupport();

	private final EssentialsSupport essentialsSupport = plugin.getPluginManager().getEssentialsSupport();

	public String setPlaceholders(Player player, String placeholders) {
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

		if (PluginSupport.VAULT.isPluginEnabled()) {
			placeholders = Methods.color(player, placeholders.replace("{vault_prefix}", vaultSupport.getPlayerPrefix(player)));
			placeholders = Methods.color(player, placeholders.replace("{vault_suffix}", vaultSupport.getPlayerSuffix(player)));
		}

		if (PluginSupport.ESSENTIALS.isPluginEnabled()) {
			placeholders = Methods.color(player, placeholders.replace("{ess_player_balance}", essentialsSupport.getPlayerBalance(player)));
			placeholders = Methods.color(player, placeholders.replace("{ess_player_nickname}", essentialsSupport.getPlayerNickname(player)));
		}

		return placeholders;
	}
}