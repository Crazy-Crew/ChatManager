package me.h1dd3nxn1nja.chatmanager.managers;

import me.clip.deluxetags.tags.DeluxeTag;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.hooks.ASkyBlockHook;
import me.h1dd3nxn1nja.chatmanager.hooks.EssentialsHook;
import me.h1dd3nxn1nja.chatmanager.hooks.FactionsHook;
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
		
		if (HookManager.isEssentialsLoaded()) {
			placeholders = Methods.color(player, placeholders.replace("{ess_player_balance}", EssentialsHook.getPlayersBalance(player)));
			placeholders = Methods.color(player, placeholders.replace("{ess_player_nickname}", EssentialsHook.getPlayersNickname(player)));
		}
		
		if (HookManager.isDeluxeTagsLoaded() || HookManager.isDeluxeTagLoaded()) {
			placeholders = Methods.color(player, placeholders.replace("{deluxetag_tag}", DeluxeTag.getPlayerDisplayTag(player)));
		}
		
		if (HookManager.isFactionsLoaded()) {
			placeholders = Methods.color(player, placeholders.replace("{factions_faction}", FactionsHook.getFactionName(player)));
			placeholders = Methods.color(player, placeholders.replace("{factions_role_prefix}", FactionsHook.getFactionRolePrefix(player)));
			placeholders = Methods.color(player, placeholders.replace("{factions_role_name}", FactionsHook.getFactionRoleName(player)));
			placeholders = Methods.color(player, placeholders.replace("{factions_player_power}", String.valueOf(FactionsHook.getPlayerPower(player))));
			placeholders = Methods.color(player, placeholders.replace("{factions_player_max_power}", String.valueOf(FactionsHook.getPlayerMaxPower(player))));
			placeholders = Methods.color(player, placeholders.replace("{factions_faction_power}", String.valueOf(FactionsHook.getFactionPower(player))));
			placeholders = Methods.color(player, placeholders.replace("{factions_faction_max_power}", String.valueOf(FactionsHook.getFactionMaxPower(player))));
		}
		
		if (HookManager.isASkyBlockLoaded()) {
			placeholders = Methods.color(player, placeholders.replace("{askyblock_island_name}", ASkyBlockHook.getIslandName(player)));
			placeholders = Methods.color(player, placeholders.replace("{askyblock_island_level}", df.format(ASkyBlockHook.getIslandLevel(player))));
			placeholders = Methods.color(player, placeholders.replace("{askyblock_island_count}", df.format(ASkyBlockHook.getIslandCount(player))));
		}
		return placeholders;
	}
}