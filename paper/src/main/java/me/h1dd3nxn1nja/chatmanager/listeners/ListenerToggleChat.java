package me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import java.util.Set;

public class ListenerToggleChat implements Listener {

	private final ChatManager plugin = ChatManager.getPlugin();

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		if (plugin.api().getToggleChatData().containsUser(player.getUniqueId())) return;

		Set<Player> recipients = event.getRecipients();

		recipients.removeIf(cm -> plugin.api().getToggleChatData().containsUser(cm.getUniqueId()));
	}
}