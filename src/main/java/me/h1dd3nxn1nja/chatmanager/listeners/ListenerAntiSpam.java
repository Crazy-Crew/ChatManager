package me.h1dd3nxn1nja.chatmanager.listeners;

import java.util.List;
import java.util.UUID;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.interfaces.Universal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;
import me.h1dd3nxn1nja.chatmanager.Methods;

public class ListenerAntiSpam implements Listener, Universal {

	private final ChatManager plugin = ChatManager.getPlugin();

	@EventHandler(ignoreCancelled = true)
	public void antiSpamChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();

		UUID uuid = player.getUniqueId();

		boolean isValid = cacheManager.getStaffChatDataSet().containsUser(uuid);

		if (isValid) return;

		if (config.getBoolean("Anti_Spam.Chat.Block_Repetitive_Messages")) {
			if (player.hasPermission("chatmanager.bypass.dupe.chat")) return;

			if (cacheManager.getPreviousMsgDataMap().containsUser(uuid)) {

				String msg = cacheManager.getPreviousMsgDataMap().getMessage(player.getUniqueId());

				if (message.equalsIgnoreCase(msg)) {
					//player.sendMessage(Methods.color(player.getUniqueId(), messages.getString("Anti_Spam.Chat.Repetitive_Message").replace("{Prefix}", messages.getString("Message.Prefix"))));
					event.setCancelled(true);
				}
			}

			cacheManager.getPreviousMsgDataMap().addUser(uuid, message);
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onChatCooldown(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();

		boolean isValid = cacheManager.getStaffChatDataSet().containsUser(uuid);

		if (isValid) return;

		int delay = config.getInt("Anti_Spam.Chat.Chat_Delay");

		if (delay == 0 || player.hasPermission("chatmanager.bypass.chatdelay")) return;

		if (cacheManager.getChatCoolDowns().containsUser(uuid)) {
			int time = cacheManager.getChatCoolDowns().getTime(uuid);

			//Methods.sendMessage(player, messages.getString("Anti_Spam.Chat.Delay_Message").replace("{Time}", String.valueOf(time)), true);
			event.setCancelled(true);
			return;
		}

		cacheManager.getChatCoolDowns().addUser(uuid, config.getInt("Anti_Spam.Chat.Chat_Delay"));

		cacheManager.getCoolDownTask().addUser(uuid, new BukkitRunnable() {
			@Override
			public void run() {
				int time = cacheManager.getChatCoolDowns().getTime(uuid);

				cacheManager.getChatCoolDowns().subtract(uuid);

				if (time == 0) {
					cacheManager.getChatCoolDowns().removeUser(uuid);
					cacheManager.getCoolDownTask().removeUser(uuid);
					cancel();
				}
			}
		});

		cacheManager.getCoolDownTask().getUsers().get(player.getUniqueId()).runTaskTimer(plugin, 20L, 20L);
	}

	@EventHandler(ignoreCancelled = true)
	public void onSpamCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String command = event.getMessage();

		UUID uuid = player.getUniqueId();

		List<String> whitelistedCommands = config.getStringList("Anti_Spam.Command.Whitelist");

		if (config.getBoolean("Anti_Spam.Command.Block_Repetitive_Commands")) {
			if (!player.hasPermission("chatmanager.bypass.dupe.command")) {
				for (String commands : whitelistedCommands) {
					if (event.getMessage().contains(commands)) {
						cacheManager.getPreviousCmdDataMap().removeUser(uuid);
						return;
					}
				}

				if (cacheManager.getPreviousCmdDataMap().containsUser(uuid)) {
					String cmd = cacheManager.getPreviousCmdDataMap().getMessage(uuid);
					if (command.equalsIgnoreCase(cmd)) {
						Methods.sendMessage(player, messages.getString("Anti_Spam.Command.Repetitive_Message"), true);

						event.setCancelled(true);
					}
				}

				cacheManager.getPreviousCmdDataMap().addUser(player.getUniqueId(), command);
			}

			if (config.getInt("Anti_Spam.Command.Command_Delay") != 0) {
				if (!player.hasPermission("chatmanager.bypass.commanddelay")) {
					if (cacheManager.getCmdCoolDowns().containsUser(uuid)) {
						//Methods.sendMessage(player, messages.getString("Anti_Spam.Command.Delay_Message").replace("{Time}", String.valueOf(plugin.getCrazyManager().api().getCmdCooldowns().getTime(uuid))), true);
						event.setCancelled(true);
						return;
					}

					for (String commands : whitelistedCommands) {
						if (event.getMessage().contains(commands)) return;
					}

					cacheManager.getCmdCoolDowns().addUser(uuid, config.getInt("Anti_Spam.Command.Command_Delay"));

					cacheManager.getCoolDownTask().addUser(uuid, new BukkitRunnable() {
						@Override
						public void run() {
							int time = cacheManager.getCmdCoolDowns().getTime(uuid);

							cacheManager.getCmdCoolDowns().addUser(uuid, time--);

							if (time == 0) {
								cacheManager.getCmdCoolDowns().removeUser(uuid);
								cacheManager.getCoolDownTask().removeUser(uuid);
								cancel();
							}
						}
					});

					cacheManager.getCoolDownTask().getUsers().get(player.getUniqueId()).runTaskTimer(plugin, 20L, 20L);
				}
			}
		}
	}
}