package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.api.objects.PaperUser;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.core.PlayerState;
import com.ryderbelserion.chatmanager.utils.UserUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class ListenerStaffChat implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	private final Server server = this.plugin.getServer();

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncChatEvent event) {
		final Player player = event.getPlayer();
		final String message = event.signedMessage().message();
		final PaperUser user = UserUtils.getUser(player);

		if (!user.hasState(PlayerState.STAFF_CHAT)) return;

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final String name = player.getName();

		event.setCancelled(true);

		for (final Player target : this.server.getOnlinePlayers()) {
			if (!Permissions.TOGGLE_STAFF_CHAT.hasPermission(target)) continue;

			Methods.sendMessage(target, config.getString("Staff_Chat.Format", "&e[&bStaffChat&e] &a{player} &7> &b{message}").replace("{player}", name).replace("{message}", message));
		}

		Methods.tellConsole(config.getString("Staff_Chat.Format", "&e[&bStaffChat&e] &a{player} &7> &b{message}").replace("{player}", name).replace("{message}", message), false);
	}
}