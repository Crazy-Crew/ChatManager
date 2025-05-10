package me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.support.Global;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

public class ListenerToggleChat extends Global implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();

		if (this.toggleChatData.containsUser(player.getUniqueId())) return;

		final Set<Player> recipients = event.getRecipients();

		recipients.removeIf(cm -> this.toggleChatData.containsUser(cm.getUniqueId()));
	}
}