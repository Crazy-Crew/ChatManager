package me.h1dd3nxn1nja.chatmanager.listeners;

import java.util.List;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;

public class ListenerAntiSpam implements Listener {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	@EventHandler(ignoreCancelled = true)
	public void antiChatSpam(AsyncPlayerChatEvent event) {
		FileConfiguration config = settingsManager.getConfig();
		FileConfiguration messages = settingsManager.getMessages();

		Player player = event.getPlayer();
		String message = event.getMessage();

		if (!Methods.cm_staffChat.contains(player.getUniqueId())) {
			if (config.getBoolean("Anti_Spam.Chat.Block_Repetitive_Messages")) {
				if (!player.hasPermission("chatmanager.bypass.dupe.chat")) {
					if (Methods.cm_previousMessages.containsKey(player)) {
						if (message.equalsIgnoreCase(Methods.cm_previousMessages.get(player))) {
							player.sendMessage(Methods.color(player, messages.getString("Anti_Spam.Chat.Repetitive_Message").replace("{Prefix}", messages.getString("Message.Prefix"))));
							event.setCancelled(true);
						}
					}

					Methods.cm_previousMessages.put(player, message);
				}
			}
			if (config.getInt("Anti_Spam.Chat.Chat_Delay") != 0) {
				if (!player.hasPermission("chatmanager.bypass.chatdelay")) {
					if (Methods.cm_chatCooldown.containsKey(player)) {
						Methods.sendMessage(player, messages.getString("Anti_Spam.Chat.Delay_Message").replace("{Time}", String.valueOf(Methods.cm_chatCooldown.get(player))), true);
						event.setCancelled(true);
						return;
					}

					Methods.cm_chatCooldown.put(player, config.getInt("Anti_Spam.Chat.Chat_Delay"));

					Methods.cm_cooldownTask.put(player, new BukkitRunnable() {
						public void run() {
							Methods.cm_chatCooldown.put(player, Methods.cm_chatCooldown.get(player) - 1);

							if (Methods.cm_chatCooldown.get(player) == 0) {
								Methods.cm_chatCooldown.remove(player);
								Methods.cm_cooldownTask.remove(player);
								cancel();
							}
						}
					});

					Methods.cm_cooldownTask.get(player).runTaskTimer(plugin, 20L, 20L);
				}
			}

			// TODO() I don't fucking know.
			/*if (config.getBoolean("Anti_Spam.Chat.Anti_Flood.Enable")) {
				if (!player.hasPermission("chatmanager.bypass.antiflood")) {

				}
			}*/
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onCommandSpam(PlayerCommandPreprocessEvent event) {
		FileConfiguration config = settingsManager.getConfig();
		FileConfiguration messages = settingsManager.getMessages();

		Player player = event.getPlayer();
		String command = event.getMessage();

		List<String> whitelistedCommands = config.getStringList("Anti_Spam.Command.Whitelist");

		if (config.getBoolean("Anti_Spam.Command.Block_Repetitive_Commands")) {
			if (!player.hasPermission("chatmanager.bypass.dupe.command")) {
				for (String commands : whitelistedCommands) {
					if (event.getMessage().contains(commands)) {
						Methods.cm_previousCommand.remove(player);
						return;
					}
				}

				if (Methods.cm_previousCommand.containsKey(player)) {
					if (command.equalsIgnoreCase(Methods.cm_previousCommand.get(player))) {
						Methods.sendMessage(player, messages.getString("Anti_Spam.Command.Repetitive_Message"), true);

						event.setCancelled(true);
					}
				}

				Methods.cm_previousCommand.put(player, command);
			}

			if (config.getInt("Anti_Spam.Command.Command_Delay") != 0) {
				if (!player.hasPermission("chatmanager.bypass.commanddelay")) {
					if (Methods.cm_commandCooldown.containsKey(player)) {
						Methods.sendMessage(player, messages.getString("Anti_Spam.Command.Delay_Message").replace("{Time}", String.valueOf(Methods.cm_commandCooldown.get(player))), true);
						event.setCancelled(true);
						return;
					}

					for (String commands : whitelistedCommands) {
						if (event.getMessage().contains(commands)) return;
					}

					Methods.cm_commandCooldown.put(player, config.getInt("Anti_Spam.Command.Command_Delay"));

					Methods.cm_cooldownTask.put(player, new BukkitRunnable() {
						public void run() {
							Methods.cm_commandCooldown.put(player, Methods.cm_commandCooldown.get(player) - 1);

							if (Methods.cm_commandCooldown.get(player) == 0) {
								Methods.cm_commandCooldown.remove(player);
								Methods.cm_cooldownTask.remove(player);
								cancel();
							}
						}
					});

					Methods.cm_cooldownTask.get(player).runTaskTimer(plugin, 20L, 20L);
				}
			}
		}
	}
}