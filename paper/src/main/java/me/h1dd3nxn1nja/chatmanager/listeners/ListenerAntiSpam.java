package me.h1dd3nxn1nja.chatmanager.listeners;

import java.util.List;
import java.util.UUID;
import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.chat.StaffChatData;
import com.ryderbelserion.chatmanager.api.chat.logging.PreviousCmdData;
import com.ryderbelserion.chatmanager.api.chat.logging.PreviousMsgData;
import com.ryderbelserion.chatmanager.api.cooldowns.ChatCooldowns;
import com.ryderbelserion.chatmanager.api.cooldowns.CmdCooldowns;
import com.ryderbelserion.chatmanager.api.cooldowns.CooldownTask;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.fusion.paper.api.scheduler.FoliaScheduler;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;

public class ListenerAntiSpam implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	private final ApiLoader api = this.plugin.api();

	private final StaffChatData staffChatData = this.api.getStaffChatData();

	private final PreviousMsgData msgData = this.api.getPreviousMsgData();

	private final CmdCooldowns cmdCooldowns = this.api.getCmdCooldowns();

	private final CooldownTask tasks = this.api.getCooldownTask();

	private final PreviousCmdData cmdData = this.api.getPreviousCmdData();

	private final ChatCooldowns chatCooldowns = this.api.getChatCooldowns();

	@EventHandler(ignoreCancelled = true)
	public void antiSpamChat(AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();
		final String message = event.getMessage();

		final UUID uuid = player.getUniqueId();

		final boolean isValid = this.staffChatData.containsUser(uuid);

		if (isValid) return;

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (config.getBoolean("Anti_Spam.Chat.Block_Repetitive_Messages", false)) {
			if (player.hasPermission(Permissions.BYPASS_DUPE_CHAT.getNode())) return;

			if (this.msgData.containsUser(uuid) && !this.chatCooldowns.containsUser(uuid)) {
				String msg = this.msgData.getMessage(uuid);

				if (message.equalsIgnoreCase(msg)) {
					Messages.ANTI_SPAM_CHAT_REPETITIVE_MESSAGE.sendMessage(player);

					event.setCancelled(true);
				}
			}

			this.msgData.addUser(uuid, message);
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onChatCoolDown(AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();
		final UUID uuid = player.getUniqueId();

		final boolean isValid = this.staffChatData.containsUser(uuid);

		if (isValid) return;

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final int delay = config.getInt("Anti_Spam.Chat.Chat_Delay", 3);

		if (delay == 0 || player.hasPermission(Permissions.BYPASS_CHAT_DELAY.getNode())) return;

		if (this.chatCooldowns.containsUser(uuid)) {
			final int time = this.chatCooldowns.getTime(uuid);

			Messages.ANTI_SPAM_CHAT_DELAY_MESSAGE.sendMessage(player, "{Time}", String.valueOf(time));

			event.setCancelled(true);

			return;
		}

		this.chatCooldowns.addUser(uuid, delay);

		this.tasks.addUser(uuid, new FoliaScheduler(player.getLocation()) {

			@Override
			public void run() {
				final int time = chatCooldowns.getTime(uuid);

				chatCooldowns.subtract(uuid);

				if (time == 0) {
					chatCooldowns.removeUser(uuid);
					tasks.removeUser(uuid);

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
					if (command.contains(commands)) {
						this.cmdData.removeUser(uuid);

						return;
					}
				}

				if (this.cmdData.containsUser(uuid) && !cmdCooldowns.containsUser(uuid)) {
					String cmd = this.cmdData.getMessage(uuid);

					if (command.equalsIgnoreCase(cmd)) {
						Messages.ANTI_SPAM_COMMAND_REPETITIVE_MESSAGE.sendMessage(player);

						event.setCancelled(true);
					}
				}

				this.cmdData.addUser(uuid, command);
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
						if (command.contains(commands)) return;
					}

					this.cmdCooldowns.addUser(uuid, delay);

					this.tasks.addUser(uuid, new FoliaScheduler(player.getLocation()) {

						@Override
						public void run() {
							int time = cmdCooldowns.getTime(uuid);

							cmdCooldowns.subtract(uuid);

							if (time == 0) {
								cmdCooldowns.removeUser(uuid);
								tasks.removeUser(uuid);

								cancel();
							}
						}
					}.runAtFixedRate(20L, 20L));
				}
			}
		}
	}
}