package me.h1dd3nxn1nja.chatmanager.listeners;

import java.util.List;
import java.util.UUID;

import com.ryderbelserion.chatmanager.api.interfaces.Universal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;
import me.h1dd3nxn1nja.chatmanager.Methods;

public class ListenerAntiSpam implements Listener, Universal {

	@EventHandler(ignoreCancelled = true)
	public void antiSpamChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();

		UUID uuid = player.getUniqueId();

		boolean isValid = cacheManager.getStaffChatDataSet().containsUser(uuid);

		if (isValid) return;

		configSettings.getProperty(ConfigSettings)

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

		boolean isValid = plugin.getCrazyManager().api().getStaffChatData().containsUser(uuid);

		if (isValid) return;

		int delay = config.getInt("Anti_Spam.Chat.Chat_Delay");

		if (delay == 0 || player.hasPermission("chatmanager.bypass.chatdelay")) return;

		if (plugin.getCrazyManager().api().getChatCooldowns().containsUser(uuid)) {
			int time = plugin.getCrazyManager().api().getChatCooldowns().getTime(uuid);

			Methods.sendMessage(player, messages.getString("Anti_Spam.Chat.Delay_Message").replace("{Time}", String.valueOf(time)), true);
			event.setCancelled(true);
			return;
		}

		plugin.getCrazyManager().api().getChatCooldowns().addUser(uuid, config.getInt("Anti_Spam.Chat.Chat_Delay"));

		plugin.getCrazyManager().api().getCooldownTask().addUser(uuid, new BukkitRunnable() {
			@Override
			public void run() {
				int time = plugin.getCrazyManager().api().getChatCooldowns().getTime(uuid);

				plugin.getCrazyManager().api().getChatCooldowns().subtract(uuid);

				if (time == 0) {
					plugin.getCrazyManager().api().getChatCooldowns().removeUser(uuid);
					plugin.getCrazyManager().api().getCooldownTask().removeUser(uuid);
					cancel();
				}
			}
		});

		plugin.getCrazyManager().api().getCooldownTask().getUsers().get(player.getUniqueId()).runTaskTimer(plugin, 20L, 20L);
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
						plugin.getCrazyManager().api().getPreviousCmdData().removeUser(uuid);
						return;
					}
				}

				if (plugin.getCrazyManager().api().getPreviousCmdData().containsUser(uuid)) {
					String cmd = plugin.getCrazyManager().api().getPreviousCmdData().getMessage(uuid);
					if (command.equalsIgnoreCase(cmd)) {
						Methods.sendMessage(player, messages.getString("Anti_Spam.Command.Repetitive_Message"), true);

						event.setCancelled(true);
					}
				}

				plugin.getCrazyManager().api().getPreviousCmdData().addUser(player.getUniqueId(), command);
			}

			if (config.getInt("Anti_Spam.Command.Command_Delay") != 0) {
				if (!player.hasPermission("chatmanager.bypass.commanddelay")) {
					if (plugin.getCrazyManager().api().getCmdCooldowns().containsUser(uuid)) {
						Methods.sendMessage(player, messages.getString("Anti_Spam.Command.Delay_Message").replace("{Time}", String.valueOf(plugin.getCrazyManager().api().getCmdCooldowns().getTime(uuid))), true);
						event.setCancelled(true);
						return;
					}

					for (String commands : whitelistedCommands) {
						if (event.getMessage().contains(commands)) return;
					}

					plugin.getCrazyManager().api().getCmdCooldowns().addUser(uuid, config.getInt("Anti_Spam.Command.Command_Delay"));

					plugin.getCrazyManager().api().getCooldownTask().addUser(uuid, new BukkitRunnable() {
						@Override
						public void run() {
							int time = plugin.getCrazyManager().api().getCmdCooldowns().getTime(uuid);

							plugin.getCrazyManager().api().getCmdCooldowns().addUser(uuid, time--);

							if (time == 0) {
								plugin.getCrazyManager().api().getCmdCooldowns().removeUser(uuid);
								plugin.getCrazyManager().api().getCooldownTask().removeUser(uuid);
								cancel();
							}
						}
					});

					plugin.getCrazyManager().api().getCooldownTask().getUsers().get(player.getUniqueId()).runTaskTimer(plugin, 20L, 20L);
				}
			}
		}
	}
}