package me.h1dd3nxn1nja.chatmanager.listeners;

import java.util.List;

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
	
	public ChatManager plugin;
	
	public ListenerAntiSpam(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void antiSpamChat(AsyncPlayerChatEvent event) {
		
		FileConfiguration config = ChatManager.settings.getConfig();
		FileConfiguration messages = ChatManager.settings.getMessages();

		Player player = event.getPlayer();
		String message = event.getMessage();
		
		if (!Methods.cm_staffChat.contains(player.getUniqueId())) {
			if (config.getBoolean("Anti_Spam.Chat.Block_Repetitive_Messages")) {
				if (!player.hasPermission("chatmanager.bypass.dupe.chat")) {
					if (Methods.cm_previousMessages.containsKey(player)) {
						if (message.equalsIgnoreCase(Methods.cm_previousMessages.get(player))) {
							player.sendMessage(Methods.color(player, messages.getString("Anti_Spam.Chat.Repetitive_Message")
									.replace("{Prefix}", messages.getString("Message.Prefix"))));
							event.setCancelled(true);
						}
					}
					Methods.cm_previousMessages.put(player, message);
				}
			}
			if (config.getInt("Anti_Spam.Chat.Chat_Delay") != 0) {
				if (!player.hasPermission("chatmanager.bypass.chatdelay")) {
					if (Methods.cm_chatCooldown.containsKey(player)) {
						player.sendMessage(Methods.color(player, messages.getString("Anti_Spam.Chat.Delay_Message")
								.replace("{Prefix}", messages.getString("Message.Prefix"))
								.replace("{Time}", String.valueOf(Methods.cm_chatCooldown.get(player)))));
						event.setCancelled(true);
						return;
					}

					Methods.cm_chatCooldown.put(player, Integer.valueOf(config.getInt("Anti_Spam.Chat.Chat_Delay")));
					Methods.cm_cooldownTask.put(player, new BukkitRunnable() {
						public void run() {
							Methods.cm_chatCooldown.put(player, Integer.valueOf((Integer) Methods.cm_chatCooldown.get(player).intValue() - 1));
							if ((Integer) Methods.cm_chatCooldown.get(player).intValue() == 0) {
								Methods.cm_chatCooldown.remove(player);
								Methods.cm_cooldownTask.remove(player);
								cancel();
							}
						}
					});
					((BukkitRunnable) Methods.cm_cooldownTask.get(player)).runTaskTimer(plugin, 20L, 20L);
				}
			} else {
				return;
			}
			/*if (config.getBoolean("Anti_Spam.Chat.Anti_Flood.Enable")) {
				if (!player.hasPermission("chatmanager.bypass.antiflood")) {
					
				}
			}*/
		}
	}
	
	@EventHandler
	public void onSpamCommand(PlayerCommandPreprocessEvent event) {
		
		FileConfiguration config = ChatManager.settings.getConfig();
		FileConfiguration messages = ChatManager.settings.getMessages();

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
						player.sendMessage(Methods.color(player, messages.getString("Anti_Spam.Command.Repetitive_Message")
								.replace("{Prefix}", messages.getString("Message.Prefix"))));
						event.setCancelled(true);
					}
				}
				Methods.cm_previousCommand.put(player, command);
			}
			if (config.getInt("Anti_Spam.Command.Command_Delay") != 0) {
				if (!player.hasPermission("chatmanager.bypass.commanddelay")) {
					if (Methods.cm_commandCooldown.containsKey(player)) {
						player.sendMessage(Methods.color(player, messages.getString("Anti_Spam.Command.Delay_Message")
								.replace("{Prefix}", messages.getString("Message.Prefix"))
								.replace("{Time}", String.valueOf(Methods.cm_commandCooldown.get(player)))));
						event.setCancelled(true);
						return;
					}
					for (String commands : whitelistedCommands) {
						if (event.getMessage().contains(commands)) {
							return;
						}
					}
					Methods.cm_commandCooldown.put(player, Integer.valueOf(config.getInt("Anti_Spam.Command.Command_Delay")));
					Methods.cm_cooldownTask.put(player, new BukkitRunnable() {
						public void run() {
							Methods.cm_commandCooldown.put(player, Integer.valueOf((Integer) Methods.cm_commandCooldown.get(player).intValue() - 1));
							if ((Integer) Methods.cm_commandCooldown.get(player).intValue() == 0) {
								Methods.cm_commandCooldown.remove(player);
								Methods.cm_cooldownTask.remove(player);
								cancel();
							}
						}
					});
					((BukkitRunnable) Methods.cm_cooldownTask.get(player)).runTaskTimer(plugin, 20L, 20L);
				}
			} else {
				return;
			}
		}
	}
}