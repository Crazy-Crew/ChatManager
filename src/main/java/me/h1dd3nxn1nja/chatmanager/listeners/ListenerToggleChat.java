package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.v1.api.Universal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import java.util.Set;

public class ListenerToggleChat implements Listener, Universal {

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		if (plugin.getCrazyManager().api().getToggleChatData().containsUser(player.getUniqueId())) return;

		Set<Player> recipients = event.getRecipients();

		recipients.removeIf(cm -> plugin.getCrazyManager().api().getToggleChatData().containsUser(cm.getUniqueId()));
	}
}