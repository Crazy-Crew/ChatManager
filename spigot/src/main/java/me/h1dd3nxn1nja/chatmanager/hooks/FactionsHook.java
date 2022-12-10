package me.h1dd3nxn1nja.chatmanager.hooks;

import com.massivecraft.factions.entity.MPlayer;

import org.bukkit.entity.Player;

public class FactionsHook {

	FactionsHook() {}
	
	public String getPlugin() {
		return "Factions";
	}
	
	public static String getFactionName(Player player) {
		return MPlayer.get(player.getUniqueId()).getFactionName();
	}
	
	public static String getFactionRolePrefix(Player player) {
		return MPlayer.get(player.getUniqueId()).getRole().getPrefix();
	}
	
	public static String getFactionRoleName(Player player) {
		return MPlayer.get(player.getUniqueId()).getRole().getName();
	}
	
	public static String getFactionDescription(Player player) {
		return MPlayer.get(player.getUniqueId()).getFaction().getDescription();
	}
	
	public static String getFactionMOTD(Player player) {
		return MPlayer.get(player.getUniqueId()).getFaction().getMotd();
	}
	
	public static String getFactionId(Player player) {
		return MPlayer.get(player.getUniqueId()).getFaction().getId();
	}
	
	public static double getPlayerPower(Player player) {
		return MPlayer.get(player.getUniqueId()).getPowerRounded();
	}
	
	public static double getPlayerMaxPower(Player player) {
		return MPlayer.get(player.getUniqueId()).getPowerMaxRounded();
	}
	
	public static double getFactionPower(Player player) {
		return MPlayer.get(player.getUniqueId()).getFaction().getPowerRounded();
	}
	
	public static double getFactionMaxPower(Player player) {
		return MPlayer.get(player.getUniqueId()).getFaction().getPowerMaxRounded();
	}
	
	public static long getFactionAge(Player player) {
		return MPlayer.get(player.getUniqueId()).getFaction().getAge();
	}
	
	public static int getFactionClaims(Player player) {
		return MPlayer.get(player.getUniqueId()).getFaction().getLandCount();
	}
}