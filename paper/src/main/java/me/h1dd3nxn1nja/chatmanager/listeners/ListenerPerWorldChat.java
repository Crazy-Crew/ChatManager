package me.h1dd3nxn1nja.chatmanager.listeners;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import com.ryderbelserion.chatmanager.api.objects.PaperUser;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.commands.RadiusType;
import com.ryderbelserion.chatmanager.enums.core.PlayerState;
import com.ryderbelserion.chatmanager.utils.UserUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class ListenerPerWorldChat implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	private final Server server = this.plugin.getServer();

	@EventHandler(ignoreCancelled = true)
	public void onWorldChat(AsyncChatEvent event) {
		final Player player = event.getPlayer();
		final World world = player.getWorld();
		final UUID id = world.getUID();
		final Set<Audience> recipients = event.viewers();

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final PaperUser user = UserUtils.getUser(player);

		if (!config.getBoolean("Per_World_Chat.Enable", false) || user.hasState(PlayerState.STAFF_CHAT) || user.getRadius() == RadiusType.WORLD_CHAT) return;
		if (config.getBoolean("Per_World_Chat.Group_Worlds.Enable", false)) return;

		recipients.removeIf(target -> {
			if (!(target instanceof Player instance)) {
				return false;
			}

			return UserUtils.getUser(instance.getUniqueId()).getRadius() != RadiusType.WORLD_CHAT && id.equals(instance.getWorld().getUID());
		});
	}

	@EventHandler(ignoreCancelled = true)
	public void onPerWorldChat(AsyncChatEvent event) {
		final Player player = event.getPlayer();
		final World world = player.getWorld();
		final String worldName = world.getName();
		final Set<Audience> recipients = event.viewers();

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Per_World_Chat.Group_Worlds.Enable", false)) return;

		final ConfigurationSection section = config.getConfigurationSection("Per_World_Chat.Group_Worlds.Worlds");

		List<String> playerGroup = null;

		if (section != null) {
			for (final String key : section.getKeys(false)) {
				final List<String> group = config.getStringList("Per_World_Chat.Group_Worlds.Worlds." + key);

				if (group.contains(worldName)) {
					playerGroup = group;

					break;
				}
			}
		}

		for (final Player target : this.server.getOnlinePlayers()) {
			final String targetWorld = target.getWorld().getName();

			final PaperUser user = UserUtils.getUser(target.getUniqueId());

			if (user.getRadius() == RadiusType.WORLD_CHAT) continue;

			if (playerGroup != null && !playerGroup.contains(targetWorld)) {
				recipients.remove(target);

				return;
			}

			if (!worldName.equals(targetWorld)) {
				recipients.remove(target);
			}
		}
	}
}