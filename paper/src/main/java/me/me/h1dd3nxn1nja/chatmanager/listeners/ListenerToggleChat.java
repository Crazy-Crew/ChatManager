package me.me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

public class ListenerToggleChat implements Listener {

	public ListenerToggleChat(ChatManager plugin) {}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();

		if (Methods.cm_toggleChat.contains(player.getUniqueId())) {
			return;
		}

		Set<Player> recipients = event.getRecipients();

		recipients.removeIf(cm -> Methods.cm_toggleChat.contains(cm.getUniqueId()));
	}
}