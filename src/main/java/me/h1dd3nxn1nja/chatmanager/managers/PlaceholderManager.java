package me.h1dd3nxn1nja.chatmanager.managers;

import com.ryderbelserion.chatmanager.paper.enums.Files;
import me.clip.placeholderapi.PlaceholderAPI;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.support.EssentialsSupport;
import me.h1dd3nxn1nja.chatmanager.support.PluginSupport;
import me.h1dd3nxn1nja.chatmanager.support.misc.VaultSupport;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.UUID;

public class PlaceholderManager {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@NotNull
	private final VaultSupport vaultSupport = this.plugin.getPluginManager().getVaultSupport();

	@NotNull
	private final EssentialsSupport essentialsSupport = this.plugin.getPluginManager().getEssentialsSupport();

	public String setPlaceholders(Player player, String placeholders) {
		FileConfiguration messages = Files.MESSAGES.getFile();
		FileConfiguration config = Files.CONFIG.getFile();

		DecimalFormat df = new DecimalFormat("#,###");
		
		UUID uuid = player.getUniqueId();

		placeholders = this.plugin.getMethods().color(uuid, placeholders.replace("{player}", player.getName()));
		placeholders = this.plugin.getMethods().color(uuid, placeholders.replace("{Prefix}", messages.getString("Message.Prefix")));
		placeholders = this.plugin.getMethods().color(uuid, placeholders.replace("{prefix}", messages.getString("Message.Prefix")));
		placeholders = this.plugin.getMethods().color(uuid, placeholders.replace("{display_name}", player.getDisplayName()));
		placeholders = this.plugin.getMethods().color(uuid, placeholders.replace("{displayname}", player.getDisplayName()));
		placeholders = this.plugin.getMethods().color(uuid, placeholders.replace("{world}", player.getLocation().getWorld().getName()));
		placeholders = this.plugin.getMethods().color(uuid, placeholders.replace("{online}", df.format(this.plugin.getServer().getOnlinePlayers().size())));
		placeholders = this.plugin.getMethods().color(uuid, placeholders.replace("{server_online}", df.format(this.plugin.getServer().getOnlinePlayers().size())));
		placeholders = this.plugin.getMethods().color(uuid, placeholders.replace("{server_max_players}", df.format(this.plugin.getServer().getMaxPlayers())));
		placeholders = this.plugin.getMethods().color(uuid, placeholders.replace("{server_name}", config.getString("Server_Name")));

		if (PluginSupport.VAULT.isPluginEnabled()) {
			placeholders = this.plugin.getMethods().color(uuid, placeholders.replace("{vault_prefix}", this.vaultSupport.getPlayerPrefix(player)));
			placeholders = this.plugin.getMethods().color(uuid, placeholders.replace("{vault_suffix}", this.vaultSupport.getPlayerSuffix(player)));
		}

		if (PluginSupport.ESSENTIALS.isPluginEnabled()) {
			placeholders = this.plugin.getMethods().color(uuid, placeholders.replace("{ess_player_balance}", this.essentialsSupport.getPlayerBalance(player.getUniqueId())));
			placeholders = this.plugin.getMethods().color(uuid, placeholders.replace("{ess_player_nickname}", this.essentialsSupport.getPlayerNickname(player.getUniqueId())));
		}

		if (PluginSupport.PLACEHOLDERAPI.isPluginEnabled()) placeholders = PlaceholderAPI.setPlaceholders(player, placeholders);

		return placeholders;
	}
}