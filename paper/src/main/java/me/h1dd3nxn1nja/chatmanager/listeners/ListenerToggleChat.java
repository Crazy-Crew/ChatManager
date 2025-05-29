package me.h1dd3nxn1nja.chatmanager.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class ListenerToggleChat extends Global implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncChatEvent event) {
		final Player player = event.getPlayer();

		if (this.toggleChatData.containsUser(player.getUniqueId())) return;

		final Set<Audience> recipients = event.viewers();

		recipients.removeIf(recipient -> {
			final Optional<UUID> uuid = recipient.get(Identity.UUID);

			return uuid.isPresent() && this.toggleChatData.containsUser(uuid.get());
		});
	}
}