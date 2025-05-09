package me.h1dd3nxn1nja.chatmanager.listeners;

import java.util.List;
import java.util.UUID;
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
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ListenerAntiSpam implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void antiSpamChat(AsyncChatEvent event) {
		final Player player = event.getPlayer();
		final String message = event.signedMessage().message();

		final UUID uuid = player.getUniqueId();

		final PaperUser user = UserUtils.getUser(uuid);

		if (user.hasState(PlayerState.STAFF_CHAT)) return;

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Anti_Spam.Chat.Block_Repetitive_Messages", false) || Permissions.BYPASS_DUPE_CHAT.hasPermission(player)) return;

		if (user.hasSpam("chat") && !user.hasDelay("chat")) {
			if (message.equalsIgnoreCase(user.getSpam("chat"))) {
				Messages.ANTI_SPAM_CHAT_REPETITIVE_MESSAGE.sendMessage(player);

				event.setCancelled(true);

				return;
			}
		}

		user.addSpam("chat", message);
	}

	@EventHandler(ignoreCancelled = true)
	public void onChatCoolDown(AsyncChatEvent event) {
		final Player player = event.getPlayer();
		final UUID uuid = player.getUniqueId();

		final PaperUser user = UserUtils.getUser(uuid);

		if (user.hasState(PlayerState.STAFF_CHAT)) return;

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final int delay = config.getInt("Anti_Spam.Chat.Chat_Delay", 3);

		if (delay == 0 || Permissions.BYPASS_CHAT_DELAY.hasPermission(player)) return;

		if (user.hasDelay("chat")) {
			Messages.ANTI_SPAM_CHAT_DELAY_MESSAGE.sendMessage(player, "{Time}", String.valueOf(user.getDelay("chat")));

			event.setCancelled(true);

			return;
		}

		user.addDelay("chat", delay);

		user.runTaskInterval(player.getLocation(), 20, 20, (task) -> {
			final int time = user.getDelay("chat");

			user.addDelay("chat", time - 1);

			if (time == 0) {
				user.removeDelay("chat");
				user.stopTask();
			}
		});
	}

	@EventHandler(ignoreCancelled = true)
	public void onSpamCommand(PlayerCommandPreprocessEvent event) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Anti_Spam.Command.Block_Repetitive_Commands", false)) return;

		final Player player = event.getPlayer();
		final String message = event.getMessage();

		final UUID uuid = player.getUniqueId();

		final PaperUser user = UserUtils.getUser(uuid);

		final List<String> whitelistedCommands = config.getStringList("Anti_Spam.Command.Whitelist");

		if (!Permissions.BYPASS_DUPE_COMMAND.hasPermission(player)) {
			boolean hasMatch = false;

			for (final String command : whitelistedCommands) {
				if (!message.contains(command)) continue;

				hasMatch = true;

				break;
			}

			if (hasMatch) {
				user.removeSpam("command");

				return;
			}

			if (user.hasSpam("command") && !user.hasDelay("command")) {
				if (message.equalsIgnoreCase(user.getSpam("command"))) {
					Messages.ANTI_SPAM_COMMAND_REPETITIVE_MESSAGE.sendMessage(player);

					event.setCancelled(true);

					return;
				}
			}

			user.addSpam("command", message);
		}

		int delay = config.getInt("Anti_Spam.Command.Chat_Delay", 3);

		if (delay == 0 || Permissions.BYPASS_COMMAND_DELAY.hasPermission(player)) return;

		if (user.hasDelay("command")) {
			Messages.ANTI_SPAM_COMMAND_DELAY_MESSAGE.sendMessage(player, "{Time}", String.valueOf(user.getDelay("command")));

			event.setCancelled(true);

			return;
		}

		boolean hasMatch = false;

		for (final String command : whitelistedCommands) {
			if (!message.contains(command)) continue;

			hasMatch = true;
		}

		if (hasMatch) return;

		user.addDelay("command", delay);

		user.runTaskInterval(player.getLocation(), 20, 20, (task) -> {
			final int time = user.getDelay("command");

			user.addDelay("command", time - 1);

			if (time == 0) {
				user.removeDelay("command");
				user.stopTask();
			}
		});
	}
}