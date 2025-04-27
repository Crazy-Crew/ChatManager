package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.cmds.ToggleChatData;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class ListenerToggleChat implements Listener {

	private final ChatManager plugin = ChatManager.get();

	private final ApiLoader api = this.plugin.api();

	private final ToggleChatData data = this.api.getToggleChatData();

	@EventHandler(ignoreCancelled = true)
	public void onChatEvent(AsyncChatEvent event) {
		final Player player = event.getPlayer();

		final UUID uuid = player.getUniqueId();

		if (this.data.containsUser(uuid)) return;

		final Set<Audience> viewers = event.viewers();

		viewers.removeIf(viewer -> {
			final Optional<UUID> optional = viewer.get(Identity.UUID);

            return optional.filter(this.data::containsUser).isPresent();
        });
	}
}