package me.h1dd3nxn1nja.chatmanager.support;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import java.util.UUID;

public class EssentialsSupport {

	private Essentials essentials = null;

	public EssentialsSupport() {
		if (PluginSupport.ESSENTIALS.isPluginEnabled()) this.essentials = (Essentials) PluginSupport.ESSENTIALS.getPlugin();
	}

	public boolean isPlayerAFK(UUID uuid) {
		if (isEssentialsReady()) return false;

		User user = getUser(uuid);

		return user != null && user.isAfk();
	}
	
	public boolean isIgnored(UUID uuid, UUID uuid2) {
		if (isEssentialsReady()) return false;

		User user = this.essentials.getUser(uuid);

		if (user == null) return false;

		User user2 = this.essentials.getUser(uuid2);

        return user2 != null && user.isIgnoredPlayer(user2);
	}
	
	public String getPlayerNickname(UUID uuid) {
		if (isEssentialsReady()) return "Essentials is not enabled.";

		User user = this.essentials.getUser(uuid);

		if (user == null) return null;

		if (user.getNickname() != null) return user.getNickname();

		return user.getName();
	}
	
	public String getPlayerBalance(UUID uuid) {
		if (isEssentialsReady()) return "0";

		User user = this.essentials.getUser(uuid);

		if (user == null) return "0";

		String money = String.valueOf(user.getMoney());

		if (money == null) return "0";

		return money;
	}
	
	public boolean isMuted(UUID uuid) {
		if (isEssentialsReady()) return false;

		User user = this.essentials.getUser(uuid);

		if (user == null) return false;

		return user.isMuted();
	}

	public User getUser(UUID uuid) {
		return this.essentials.getUser(uuid);
	}

	public boolean isEssentialsReady() {
		return this.essentials == null;
	}

	public Essentials getEssentials() {
		return this.essentials;
	}
}