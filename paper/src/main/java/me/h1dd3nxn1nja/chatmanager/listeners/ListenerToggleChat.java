package me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;
import java.util.Set;

public class ListenerToggleChat implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		if (this.plugin.api().getToggleChatData().containsUser(player.getUniqueId())) return;

		Set<Player> recipients = event.getRecipients();

		recipients.removeIf(cm -> this.plugin.api().getToggleChatData().containsUser(cm.getUniqueId()));
	}
}