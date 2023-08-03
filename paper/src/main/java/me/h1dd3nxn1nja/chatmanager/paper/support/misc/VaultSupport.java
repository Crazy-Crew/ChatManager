package me.h1dd3nxn1nja.chatmanager.paper.support.misc;

import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultSupport {

	private final ChatManager plugin = ChatManager.getPlugin();

	private Permission permission;
	private Chat chat;

	public void configure() {
		setupChat();
		setupPermissions();
	}

	public String getPlayerPrefix(Player player) {
		if (this.chat.getPlayerPrefix(player) == null) return "";

		return this.chat.getPlayerPrefix(player);
	}

	public String getPlayerSuffix(Player player) {
		if (this.chat.getPlayerSuffix(player) == null) return "";

		return this.chat.getPlayerSuffix(player);
	}

	public String getPlayerGroup(Player player) {
		if (this.chat.getPrimaryGroup(player) == null) return "";

		return this.chat.getPrimaryGroup(player);
	}

	private void setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = plugin.getServer().getServicesManager().getRegistration(Permission.class);

		if (permissionProvider != null) this.permission = permissionProvider.getProvider();

	}

	private void setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = plugin.getServer().getServicesManager().getRegistration(Chat.class);

		if (chatProvider != null) this.chat = chatProvider.getProvider();
	}

	public boolean isChatReady() {
		return this.chat != null;
	}

	public boolean isPermissionReady() {
		return this.permission != null;
	}

	public Permission getPermission() {
		return this.permission;
	}
}