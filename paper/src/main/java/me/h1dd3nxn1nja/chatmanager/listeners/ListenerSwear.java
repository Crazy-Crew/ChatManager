package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.fusion.paper.api.enums.Scheduler;
import com.ryderbelserion.fusion.paper.api.scheduler.FoliaScheduler;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ListenerSwear extends Global implements Listener {

	//TODO() Add a way so that chat manager highlights the swear word in the notify feature.

	@EventHandler(ignoreCancelled = true)
	public void onSwear(AsyncChatEvent event) {
		final Player player = event.getPlayer();
		final String message = event.signedMessage().message();
		final Date time = Calendar.getInstance().getTime();

		final FileConfiguration bannedWords = Files.BANNED_WORDS.getConfiguration();
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final List<String> whitelisted = bannedWords.getStringList("Whitelisted_Words");
		final List<String> blockedWordsList = bannedWords.getStringList("Banned-Words");
		final String sensitiveMessage = message.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").replaceAll("\\s+", "");
		final String curseMessage = message.toLowerCase();

		if (this.staffChatData.containsUser(player.getUniqueId()) || !config.getBoolean("Anti_Swear.Chat.Enable", false)) return;

		if (player.hasPermission(Permissions.BYPASS_ANTI_SWEAR.getNode())) return;

		if (config.getBoolean("Anti_Swear.Chat.Increase_Sensitivity", false)) {
			for (final String blockedWord : blockedWordsList) {
				for (String allowed : whitelisted) {
					if (message.contains(allowed.toLowerCase())) return;
				}

				if (curseMessageContains(player, message, time, sensitiveMessage, blockedWord)) {
					Messages.ANTI_SWEAR_CHAT_MESSAGE.sendMessage(player);

					if (config.getBoolean("Anti_Swear.Chat.Block_Message", false)) event.setCancelled(true);

					if (config.getBoolean("Anti_Swear.Chat.Notify_Staff", false)) checkOnlineStaff(player, message);

					return;
				}
			}
		}

		if (!config.getBoolean("Anti_Swear.Chat.Increase_Sensitivity", false)) {
			for (final String blockedWord : blockedWordsList) {
				for (final String allowed : whitelisted) {
					if (message.contains(allowed.toLowerCase())) return;
				}
				
				if (curseMessageContains(player, message, time, curseMessage, blockedWord)) {
					Messages.ANTI_SWEAR_CHAT_MESSAGE.sendMessage(player);

					if (config.getBoolean("Anti_Swear.Chat.Block_Message", false)) event.setCancelled(true);

					if (config.getBoolean("Anti_Swear.Chat.Notify_Staff", false)) checkOnlineStaff(player, message);

					return;
				}
			}
		}
	}

	private boolean curseMessageContains(final Player player, final String message, final Date time, final String curseMessage, final String blockedWords) {
		if (!curseMessage.contains(blockedWords)) return false;

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (config.getBoolean("Anti_Swear.Chat.Log_Swearing", false)) {
			try {
				FileWriter fw = new FileWriter(new File(new File(this.dataFolder, "Logs"), "Swears.txt"), true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("[" + time + "] [Chat] " + player.getName() + ": " + message.replaceAll("§", "&"));
				bw.newLine();
				fw.flush();
				bw.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (config.getBoolean("Anti_Swear.Chat.Execute_Command", false)) {
			if (config.contains("Anti_Swear.Chat.Executed_Command")) {
				final String command = config.getString("Anti_Swear.Chat.Executed_Command").replace("{player}", player.getName());
				final List<String> commands = config.getStringList("Anti_Swear.Chat.Executed_Command");

				new FoliaScheduler(Scheduler.global_scheduler) {
					@Override
					public void run() {
						server.dispatchCommand(sender, command);

						for (final String cmd : commands) {
							server.dispatchCommand(sender, cmd.replace("{player}", player.getName()));
						}
					}
				}.runNow();
			}
		}

		return true;
	}

	@EventHandler(ignoreCancelled = true)
	public void onSwearCommand(PlayerCommandPreprocessEvent event) {
		final Player player = event.getPlayer();
		final String message = event.getMessage();
		final Date time = Calendar.getInstance().getTime();

		final FileConfiguration bannedWords = Files.BANNED_WORDS.getConfiguration();
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final List<String> whitelistedCommands = config.getStringList("Anti_Swear.Commands.Whitelisted_Commands");
		final List<String> blockedWordsList = bannedWords.getStringList("Banned-Words");
		final String sensitiveMessage = event.getMessage().toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").replaceAll("\\s+", "");
		final String curseMessage = event.getMessage().toLowerCase();

		if (config.getBoolean("Anti_Swear.Commands.Enable", false)) {
			if (!player.hasPermission(Permissions.BYPASS_ANTI_SWEAR.getNode())) {
				if (config.getBoolean("Anti_Swear.Commands.Increase_Sensitivity", false)) {
					for (final String blockedWords : blockedWordsList) {
						for (final String command : whitelistedCommands) {
							if (message.toLowerCase().startsWith(command)) return;
						}

						if (sensitiveMessage.contains(blockedWords)) {
							Messages.ANTI_SWEAR_COMMAND_MESSAGE.sendMessage(player);

							if (config.getBoolean("Anti_Swear.Commands.Block_Command", false)) event.setCancelled(true);

							if (config.getBoolean("Anti_Swear.Commands.Notify_Staff", false)) {
								for (final Player staff : this.server.getOnlinePlayers()) {
									if (staff.hasPermission(Permissions.NOTIFY_ANTI_SWEAR.getNode())) {
										Messages.ANTI_SWEAR_COMMAND_NOTIFY_STAFF_FORMAT.sendMessage(staff, new HashMap<>() {{
											put("{player}", player.getName());
											put("{message}", message);
										}});
									}
								}

								Methods.tellConsole(Messages.ANTI_SWEAR_COMMAND_NOTIFY_STAFF_FORMAT.getMessage(this.sender, new HashMap<>() {{
									put("{player}", player.getName());
									put("{message}", message);
								}}), false);
							}

							commandSwearCheck(config, player, message, time);
						}
					}

					if (!config.getBoolean("Anti_Swear.Commands.Increase_Sensitivity", false)) {
						for (final String blockedWords : blockedWordsList) {
							for (final String command : whitelistedCommands) {
								if (message.toLowerCase().startsWith(command)) return;
							}

							if (curseMessage.contains(blockedWords)) {
								Messages.ANTI_SWEAR_COMMAND_MESSAGE.sendMessage(player);

								if (config.getBoolean("Anti_Swear.Commands.Block_Command", false)) event.setCancelled(true);

								if (config.getBoolean("Anti_Swear.Commands.Notify_Staff", false)) {
									checkOnlineStaff(player, message);
								}

								commandSwearCheck(config, player, message, time);
							}
						}
					}
				}
			}
		}
	}

	private void checkOnlineStaff(final Player player, final String message) {
		for (final Player staff : this.server.getOnlinePlayers()) {
			if (staff.hasPermission(Permissions.NOTIFY_ANTI_SWEAR.getNode())) {
				Messages.ANTI_SWEAR_CHAT_NOTIFY_STAFF_FORMAT.sendMessage(staff, new HashMap<>() {{
					put("{player}", player.getName());
					put("{message}", message);
				}});
			}
		}

		Methods.tellConsole(Messages.ANTI_SWEAR_CHAT_NOTIFY_STAFF_FORMAT.getMessage(this.sender, new HashMap<>() {{
			put("{player}", player.getName());
			put("{message}", message);
		}}), false);
	}

	private void commandSwearCheck(final FileConfiguration config, final Player player, final String message, final Date time) {
		if (config.getBoolean("Anti_Swear.Commands.Log_Swearing")) {
			try {
				final FileWriter fw = new FileWriter(new File(new File(this.dataFolder, "Logs"), "Swears.txt"), true);
				final BufferedWriter bw = new BufferedWriter(fw);
				bw.write("[" + time + "] [Command] " + player.getName() + ": " + message.replaceAll("§", "&"));
				bw.newLine();
				fw.flush();
				bw.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (config.getBoolean("Anti_Swear.Commands.Execute_Command", false)) {
			if (config.contains("Anti_Swear.Commands.Executed_Command")) {
				final String command = config.getString("Anti_Swear.Commands.Executed_Command").replace("{player}", player.getName());

				final List<String> commands = config.getStringList("Anti_Swear.Commands.Executed_Command");

				dispatchCommandRunnable(player, command, commands);
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onSwearSign(SignChangeEvent event) {
		final Player player = event.getPlayer();
		final Date time = Calendar.getInstance().getTime();

		final FileConfiguration bannedWords = Files.BANNED_WORDS.getConfiguration();
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final List<String> whitelisted = bannedWords.getStringList("Whitelisted_Words");
		final List<String> blockedWordsList = bannedWords.getStringList("Banned-Words");

		if (config.getBoolean("Anti_Swear.Signs.Enable")) {
			if (!player.hasPermission(Permissions.BYPASS_ANTI_SWEAR.getNode())) {
				if (config.getBoolean("Anti_Swear.Signs.Increase_Sensitivity")) {
					for (int line = 0; line < 4; line++) {
						final String message = event.getLine(line);
						assert message != null;
						final String curseMessage = message.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").replaceAll("\\s+", "");

						for (final String blockedWords : blockedWordsList) {
							for (final String allowed : whitelisted) {
								if (message.contains(allowed.toLowerCase())) return;
							}

							if (curseMessage.contains(blockedWords)) {
								Messages.ANTI_SWEAR_SIGNS_MESSAGE.sendMessage(player);

								if (config.getBoolean("Anti_Swear.Signs.Block_Sign", false)) event.setCancelled(true);

								if (config.getBoolean("Anti_Swear.Signs.Notify_Staff", false)) {
									if (config.getBoolean("Anti_Swear.Signs.Notify_Staff")) {
										for (final Player staff : this.server.getOnlinePlayers()) {
											if (staff.hasPermission(Permissions.NOTIFY_ANTI_SWEAR.getNode())) {
												Messages.ANTI_SWEAR_SIGNS_NOTIFY_STAFF_FORMAT.sendMessage(staff, new HashMap<>() {{
													put("{player}", player.getName());
													put("{message}", message);
												}});
											}
										}
									}

									Methods.tellConsole(Messages.ANTI_SWEAR_SIGNS_NOTIFY_STAFF_FORMAT.getMessage(this.sender, new HashMap<>() {{
										put("{player}", player.getName());
										put("{message}", message);
									}}), false);
								}

								checkSwear(config, player, time, line, message);
							}
						}
					}

					if (!config.getBoolean("Anti_Swear.Signs.Increase_Sensitivity", false)) {
						for (int line = 0; line < 4; line++) {
							final String message = event.getLine(line);

							assert message != null;
							for (final String curseMessages : message.toLowerCase().split(" ")) {
								if (!player.hasPermission(Permissions.BYPASS_ANTI_SWEAR.getNode())) {
									if (bannedWords.getStringList("Banned-Words").contains(curseMessages)) {
										Messages.ANTI_SWEAR_SIGNS_MESSAGE.sendMessage(player);

										if (config.getBoolean("Anti_Swear.Signs.Block_Sign", false)) event.setCancelled(true);

										if (config.getBoolean("Anti_Swear.Signs.Notify_Staff", false)) {
											if (config.getBoolean("Anti_Swear.Signs.Notify_Staff")) {
												for (final Player staff : this.server.getOnlinePlayers()) {
													if (staff.hasPermission(Permissions.NOTIFY_ANTI_SWEAR.getNode())) {
														Messages.ANTI_SWEAR_SIGNS_NOTIFY_STAFF_FORMAT.sendMessage(staff, new HashMap<>() {{
															put("{player}", player.getName());
															put("{message}", message);
														}});
													}
												}
											}

											Methods.tellConsole(Messages.ANTI_SWEAR_SIGNS_NOTIFY_STAFF_FORMAT.getMessage(this.sender, new HashMap<>() {{
												put("{player}", player.getName());
												put("{message}", message);
											}}), false);
										}

										checkSwear(config, player, time, line, message);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void checkSwear(final FileConfiguration config, final Player player, final Date time, final int line, final String message) {
		if (config.getBoolean("Anti_Swear.Signs.Log_Swearing", false)) {
			try {
				final FileWriter fw = new FileWriter(new File(new File(this.dataFolder, "Logs"), "Swears.txt"), true);
				final BufferedWriter bw = new BufferedWriter(fw);
				bw.write("[" + time + "] [Sign] " + player.getName() + ": Line: " + line + " Text: " + message.replaceAll("§", "&"));
				bw.newLine();
				fw.flush();
				bw.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (config.getBoolean("Anti_Swear.Signs.Execute_Command", false)) {
			if (config.contains("Anti_Swear.Signs.Executed_Command")) {
				final String command = config.getString("Anti_Swear.Signs.Executed_Command").replace("{player}", player.getName());
				final List<String> commands = config.getStringList("Anti_Swear.Signs.Executed_Command");

				dispatchCommandRunnable(player, command, commands);
			}
		}
	}

	private void dispatchCommandRunnable(final Player player, final String command, final List<String> commands) {
		new FoliaScheduler(Scheduler.global_scheduler) {
			@Override
			public void run() {
				server.dispatchCommand(sender, command);

				for (final String cmd : commands) {
					server.dispatchCommand(sender, cmd.replace("{player}", player.getName()));
				}
			}
		}.runNow();
	}
}