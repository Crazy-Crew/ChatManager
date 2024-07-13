package me.h1dd3nxn1nja.chatmanager.listeners;

import java.util.List;
import java.util.UUID;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class ListenerAntiSpam implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@EventHandler(ignoreCancelled = true)
	public void antiSpamChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();

		UUID uuid = player.getUniqueId();

		boolean isValid = this.plugin.api().getStaffChatData().containsUser(uuid);

		if (isValid) return;

		FileConfiguration config = Files.CONFIG.getConfiguration();

		if (config.getBoolean("Anti_Spam.Chat.Block_Repetitive_Messages", false)) {
			if (player.hasPermission(Permissions.BYPASS_DUPE_CHAT.getNode())) return;

			if (this.plugin.api().getPreviousMsgData().containsUser(uuid) && !this.plugin.api().getChatCooldowns().containsUser(uuid)) {
				String msg = this.plugin.api().getPreviousMsgData().getMessage(player.getUniqueId());

				if (message.equalsIgnoreCase(msg)) {
					Messages.ANTI_SPAM_CHAT_REPETITIVE_MESSAGE.sendMessage(player);

					event.setCancelled(true);
				}
			}

			this.plugin.api().getPreviousMsgData().addUser(uuid, message);
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onChatCoolDown(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();

		boolean isValid = this.plugin.api().getStaffChatData().containsUser(uuid);

		if (isValid) return;

		FileConfiguration config = Files.CONFIG.getConfiguration();

		int delay = config.getInt("Anti_Spam.Chat.Chat_Delay", 3);

		if (delay == 0 || player.hasPermission(Permissions.BYPASS_CHAT_DELAY.getNode())) return;

		if (this.plugin.api().getChatCooldowns().containsUser(uuid)) {
			int time = this.plugin.api().getChatCooldowns().getTime(uuid);

			Messages.ANTI_SPAM_CHAT_DELAY_MESSAGE.sendMessage(player, "{Time}", String.valueOf(time));

			event.setCancelled(true);

			return;
		}

		this.plugin.api().getChatCooldowns().addUser(uuid, delay);

		this.plugin.api().getCooldownTask().addUser(uuid, new BukkitRunnable() {
			@Override
			public void run() {
				int time = plugin.api().getChatCooldowns().getTime(uuid);

				plugin.api().getChatCooldowns().subtract(uuid);

				if (time == 0) {
					plugin.api().getChatCooldowns().removeUser(uuid);
					plugin.api().getCooldownTask().removeUser(uuid);

					cancel();
				}
			}
		});

		this.plugin.api().getCooldownTask().getUsers().get(player.getUniqueId()).runTaskTimer(this.plugin, 20L, 20L);
	}

	@EventHandler(ignoreCancelled = true)
	public void onSpamCommand(PlayerCommandPreprocessEvent event) {
		FileConfiguration config = Files.CONFIG.getConfiguration();

		if (config.getBoolean("Anti_Spam.Command.Block_Repetitive_Commands", false)) {
			Player player = event.getPlayer();
			String command = event.getMessage();

			UUID uuid = player.getUniqueId();

			List<String> whitelistedCommands = config.getStringList("Anti_Spam.Command.Whitelist");

			if (!player.hasPermission(Permissions.BYPASS_DUPE_COMMAND.getNode())) {
				for (String commands : whitelistedCommands) {
					if (event.getMessage().contains(commands)) {
						this.plugin.api().getPreviousCmdData().removeUser(uuid);

						return;
					}
				}

				if (this.plugin.api().getPreviousCmdData().containsUser(uuid) && !this.plugin.api().getCmdCooldowns().containsUser(uuid)) {
					String cmd = this.plugin.api().getPreviousCmdData().getMessage(uuid);

					if (command.equalsIgnoreCase(cmd)) {
						Messages.ANTI_SPAM_COMMAND_REPETITIVE_MESSAGE.sendMessage(player);

						event.setCancelled(true);
					}
				}

				this.plugin.api().getPreviousCmdData().addUser(player.getUniqueId(), command);
			}

			int delay = config.getInt("Anti_Spam.Command.Chat_Delay", 3);

			if (delay != 0) {
				if (!player.hasPermission(Permissions.BYPASS_COMMAND_DELAY.getNode())) {
					if (this.plugin.api().getCmdCooldowns().containsUser(uuid)) {
						Messages.ANTI_SPAM_COMMAND_DELAY_MESSAGE.sendMessage(player, "{Time}", String.valueOf(this.plugin.api().getCmdCooldowns().getTime(uuid)));

						event.setCancelled(true);

						return;
					}

					for (String commands : whitelistedCommands) {
						if (event.getMessage().contains(commands)) return;
					}

					this.plugin.api().getCmdCooldowns().addUser(uuid, delay);

					this.plugin.api().getCooldownTask().addUser(uuid, new BukkitRunnable() {
						@Override
						public void run() {
							int time = plugin.api().getCmdCooldowns().getTime(uuid);

							plugin.api().getCmdCooldowns().subtract(uuid);

							if (time == 0) {
								plugin.api().getCmdCooldowns().removeUser(uuid);
								plugin.api().getCooldownTask().removeUser(uuid);

								cancel();
							}
						}
					});

					this.plugin.api().getCooldownTask().getUsers().get(player.getUniqueId()).runTaskTimer(this.plugin, 20L, 20L);
				}
			}
		}
	}
}