package me.h1dd3nxn1nja.chatmanager.hooks;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.IUser;
import com.earth2me.essentials.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EssentialsHook {

	public static Essentials essentials;

	EssentialsHook() {
		essentials = ((Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials"));
	}
	
	public String getPlugin() {
		return "Essentials";
	}

	public static boolean isPlayerAFK(String pl) {
		User user = getUser(pl);

		return user != null ? user.isAfk() : false;
	}
	
	public static boolean isHidden(Player player) {
		if (essentials == null) {
			return false;
		}
		User user = essentials.getUser(player);
		
		return user.isVanished();
	}
	
	public static boolean isIgnored(Player player, Player player2) {
		if (essentials == null) {
			return false;
		}
		User user = essentials.getUser(player);
		if (user == null) {
			return false;
		}
		User user2 = essentials.getUser(player2);
        return user2 != null && user.isIgnoredPlayer((IUser)user2);
	}
	
	public static String getPlayersNickname(Player player) {
		if (essentials == null) {
			return null;
		}
		User user = essentials.getUser(player);
		if (user == null) {
			return null;
		}
		if (user.getNickname() != null) {
			return user.getNickname();
		}
		return user.getName();
	}
	
	public static String getPlayersBalance(Player player) {
		if (essentials != null) {
			User user = essentials.getUser(player);
			if (user != null) {
				if (String.valueOf(user.getMoney()) != null) {
					return String.valueOf(user.getMoney());
				}
			}
		}
		return "0";
	}
	
	public static boolean isMuted(Player player) {
		if (essentials == null) {
			return false;
		}
		User user = essentials.getUser(player);
		return user != null && user.isMuted();
	}

	public static User getUser(String pl) {
		return essentials.getUserMap().getUser(pl);
	}

	public static User getUsers(Player player) {
		return essentials.getUser(player);
	}

	public static boolean setupEssentials() {
		Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
		essentials = ess;
		return essentials != null;
	}
}