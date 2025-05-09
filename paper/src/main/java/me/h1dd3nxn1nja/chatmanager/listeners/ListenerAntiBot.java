package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.api.objects.PaperUser;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.core.PlayerState;
import com.ryderbelserion.chatmanager.utils.UserUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import com.ryderbelserion.chatmanager.enums.Permissions;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class ListenerAntiBot implements Listener {

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();

		final PaperUser user = UserUtils.getUser(player);

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Anti_Bot.Block_Chat_Until_Moved", false) || Permissions.BYPASS_ANTI_BOT.hasPermission(player)) return;

		user.addState(PlayerState.FLAGGED);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onMove(PlayerMoveEvent event) {
		final Player player = event.getPlayer();

		final PaperUser user = UserUtils.getUser(player);

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Anti_Bot.Block_Chat_Until_Moved", false) || Permissions.BYPASS_ANTI_BOT.hasPermission(player)) return;

		user.removeState(PlayerState.FLAGGED);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onChat(AsyncChatEvent event) {
		final Player player = event.getPlayer();

		final PaperUser user = UserUtils.getUser(player);

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Anti_Bot.Block_Chat_Until_Moved", false) || Permissions.BYPASS_ANTI_BOT.hasPermission(player)) return;

		if (!user.hasState(PlayerState.FLAGGED)) return;

		event.setCancelled(true);

		Messages.ANTI_BOT_DENY_CHAT_MESSAGE.sendMessage(player);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		final Player player = event.getPlayer();

		final PaperUser user = UserUtils.getUser(player);

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Anti_Bot.Block_Commands_Until_Moved", false) || Permissions.BYPASS_ANTI_BOT.hasPermission(player)) return;

		if (!user.hasState(PlayerState.FLAGGED)) return;

		event.setCancelled(true);

		Messages.ANTI_BOT_DENY_COMMAND_MESSAGE.sendMessage(player);
	}
}