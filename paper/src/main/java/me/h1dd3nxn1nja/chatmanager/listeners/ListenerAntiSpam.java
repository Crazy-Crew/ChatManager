package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.fusion.paper.api.scheduler.FoliaScheduler;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import java.util.List;
import java.util.UUID;

public class ListenerAntiSpam extends Global implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void antiSpamChat(AsyncChatEvent event) {
		final Player player = event.getPlayer();
		final String message = event.signedMessage().message();

		final UUID uuid = player.getUniqueId();

		final boolean isValid = this.staffChatData.containsUser(uuid);

		if (isValid) return;

		FileConfiguration config = Files.CONFIG.getConfiguration();

		if (config.getBoolean("Anti_Spam.Chat.Block_Repetitive_Messages", false)) {
			if (player.hasPermission(Permissions.BYPASS_DUPE_CHAT.getNode())) return;

			if (this.previousMsgData.containsUser(uuid) && !this.chatCooldowns.containsUser(uuid)) {
				final String msg = this.previousMsgData.getMessage(uuid);

				if (message.equalsIgnoreCase(msg)) {
					Messages.ANTI_SPAM_CHAT_REPETITIVE_MESSAGE.sendMessage(player);

					event.setCancelled(true);
				}
			}

			this.previousMsgData.addUser(uuid, message);
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onChatCoolDown(AsyncChatEvent event) {
		final Player player = event.getPlayer();
		final UUID uuid = player.getUniqueId();

		final boolean isValid = this.staffChatData.containsUser(uuid);

		if (isValid) return;

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		int delay = config.getInt("Anti_Spam.Chat.Chat_Delay", 3);

		if (delay == 0 || player.hasPermission(Permissions.BYPASS_CHAT_DELAY.getNode())) return;

		if (this.chatCooldowns.containsUser(uuid)) {
			int time = this.chatCooldowns.getTime(uuid);

			Messages.ANTI_SPAM_CHAT_DELAY_MESSAGE.sendMessage(player, "{Time}", String.valueOf(time));

			event.setCancelled(true);

			return;
		}

		this.chatCooldowns.addUser(uuid, delay);

		this.cooldownTask.addUser(uuid, new FoliaScheduler(player.getLocation()) {

			@Override
			public void run() {
				int time = chatCooldowns.getTime(uuid);

				chatCooldowns.subtract(uuid);

				if (time <= 0) {
					chatCooldowns.removeUser(uuid);
					cooldownTask.removeUser(uuid);

					cancel();
				}
			}
		}.runAtFixedRate(20L, 20L));
	}

	@EventHandler(ignoreCancelled = true)
	public void onSpamCommand(PlayerCommandPreprocessEvent event) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (config.getBoolean("Anti_Spam.Command.Block_Repetitive_Commands", false)) {
			final Player player = event.getPlayer();
			final String command = event.getMessage();

			final UUID uuid = player.getUniqueId();

			final List<String> whitelistedCommands = config.getStringList("Anti_Spam.Command.Whitelist");

			if (!player.hasPermission(Permissions.BYPASS_DUPE_COMMAND.getNode())) {
				for (final String commands : whitelistedCommands) {
					if (event.getMessage().contains(commands)) {
						this.previousCmdData.removeUser(uuid);

						return;
					}
				}

				if (this.previousCmdData.containsUser(uuid) && !this.cmdCooldowns.containsUser(uuid)) {
					final String cmd = this.previousCmdData.getMessage(uuid);

					if (command.equalsIgnoreCase(cmd)) {
						Messages.ANTI_SPAM_COMMAND_REPETITIVE_MESSAGE.sendMessage(player);

						event.setCancelled(true);
					}
				}

				this.previousCmdData.addUser(player.getUniqueId(), command);
			}

			int delay = config.getInt("Anti_Spam.Command.Chat_Delay", 3);

			if (delay != 0) {
				if (!player.hasPermission(Permissions.BYPASS_COMMAND_DELAY.getNode())) {
					if (this.cmdCooldowns.containsUser(uuid)) {
						Messages.ANTI_SPAM_COMMAND_DELAY_MESSAGE.sendMessage(player, "{Time}", String.valueOf(this.cmdCooldowns.getTime(uuid)));

						event.setCancelled(true);

						return;
					}

					for (final String commands : whitelistedCommands) {
						if (event.getMessage().contains(commands)) return;
					}

					this.cmdCooldowns.addUser(uuid, delay);

					this.cooldownTask.addUser(uuid, new FoliaScheduler(player.getLocation()) {

						@Override
						public void run() {
							int time = cmdCooldowns.getTime(uuid);

							cmdCooldowns.subtract(uuid);

							if (time == 0) {
								cmdCooldowns.removeUser(uuid);
								cooldownTask.removeUser(uuid);

								cancel();
							}
						}
					}.runAtFixedRate(20L, 20L));
				}
			}
		}
	}
}