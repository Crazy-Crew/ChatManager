package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.paper.enums.Scheduler;
import com.ryderbelserion.paper.util.scheduler.FoliaScheduler;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ListenerSwear implements Listener {

	//TODO() Add a way so that chat manager highlights the swear word in the notify feature.

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@EventHandler(ignoreCancelled = true)
	public void onSwear(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		Date time = Calendar.getInstance().getTime();

		FileConfiguration bannedWords = Files.BANNED_WORDS.getConfiguration();
		FileConfiguration config = Files.CONFIG.getConfiguration();

		List<String> whitelisted = bannedWords.getStringList("Whitelisted_Words");
		List<String> blockedWordsList = bannedWords.getStringList("Banned-Words");
		String sensitiveMessage = event.getMessage().toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").replaceAll("\\s+", "");
		String curseMessage = event.getMessage().toLowerCase();

		if (this.plugin.api().getStaffChatData().containsUser(player.getUniqueId()) || !config.getBoolean("Anti_Swear.Chat.Enable")) return;

		if (player.hasPermission(Permissions.BYPASS_ANTI_SWEAR.getNode())) return;

		if (config.getBoolean("Anti_Swear.Chat.Increase_Sensitivity", false)) {
			for (String blockedWord : blockedWordsList) {
				for (String allowed : whitelisted) {
					if (event.getMessage().contains(allowed.toLowerCase())) return;
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
			for (String blockedWord : blockedWordsList) {
				for (String allowed : whitelisted) {
					if (event.getMessage().contains(allowed.toLowerCase())) return;
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

	private boolean curseMessageContains(Player player, String message, Date time, String curseMessage, String blockedWords) {
		if (!curseMessage.contains(blockedWords)) return false;

		FileConfiguration config = Files.CONFIG.getConfiguration();

		if (config.getBoolean("Anti_Swear.Chat.Log_Swearing", false)) {
			try {
				FileWriter fw = new FileWriter(new File(new File(this.plugin.getDataFolder(), "Logs"), "Swears.txt"), true);
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
				String command = config.getString("Anti_Swear.Chat.Executed_Command").replace("{player}", player.getName());
				List<String> commands = config.getStringList("Anti_Swear.Chat.Executed_Command");

				new FoliaScheduler(Scheduler.global_scheduler) {
					@Override
					public void run() {
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);

						for (String cmd : commands) {
							plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), cmd.replace("{player}", player.getName()));
						}
					}
				}.run();
			}
		}

		return true;
	}

	@EventHandler(ignoreCancelled = true)
	public void onSwearCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		Date time = Calendar.getInstance().getTime();

		FileConfiguration bannedWords = Files.BANNED_WORDS.getConfiguration();
		FileConfiguration config = Files.CONFIG.getConfiguration();

		List<String> whitelisted = bannedWords.getStringList("Whitelisted_Words");
		List<String> whitelistedCommands = config.getStringList("Anti_Swear.Commands.Whitelisted_Commands");
		List<String> blockedWordsList = bannedWords.getStringList("Banned-Words");
		String sensitiveMessage = event.getMessage().toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").replaceAll("\\s+", "");
		String curseMessage = event.getMessage().toLowerCase();

		if (config.getBoolean("Anti_Swear.Commands.Enable", false)) {
			if (!player.hasPermission(Permissions.BYPASS_ANTI_SWEAR.getNode())) {
				if (config.getBoolean("Anti_Swear.Commands.Increase_Sensitivity", false)) {
					for (String blockedWords : blockedWordsList) {
						for (String allowed : whitelisted) {
							if (event.getMessage().contains(allowed.toLowerCase())) return;
						}

						if (sensitiveMessage.contains(blockedWords)) {
							Messages.ANTI_SWEAR_COMMAND_MESSAGE.sendMessage(player);

							if (config.getBoolean("Anti_Swear.Commands.Block_Command", false)) event.setCancelled(true);

							if (config.getBoolean("Anti_Swear.Commands.Notify_Staff", false)) {
								for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
									if (staff.hasPermission(Permissions.NOTIFY_ANTI_SWEAR.getNode())) {
										Messages.ANTI_SWEAR_COMMAND_NOTIFY_STAFF_FORMAT.sendMessage(staff, new HashMap<>() {{
											put("{player}", player.getName());
											put("{message}", message);
										}});
									}
								}

								Methods.tellConsole(Messages.ANTI_SWEAR_COMMAND_NOTIFY_STAFF_FORMAT.getMessage(this.plugin.getServer().getConsoleSender(), new HashMap<>() {{
									put("{player}", player.getName());
									put("{message}", message);
								}}), false);

								commandSwearCheck(config, player, message, time);
							}

							for (String command : whitelistedCommands) {
								if (message.toLowerCase().startsWith(command)) return;
							}
						}
					}

					if (!config.getBoolean("Anti_Swear.Commands.Increase_Sensitivity", false)) {
						for (String blockedWords : blockedWordsList) {
							if (curseMessage.contains(blockedWords)) {
								Messages.ANTI_SWEAR_COMMAND_MESSAGE.sendMessage(player);

								if (config.getBoolean("Anti_Swear.Commands.Block_Command", false)) event.setCancelled(true);

								if (config.getBoolean("Anti_Swear.Commands.Notify_Staff", false)) {
									checkOnlineStaff(player, message);

									commandSwearCheck(config, player, message, time);

									break;
								}

								for (String command : whitelistedCommands) {
									if (message.toLowerCase().startsWith(command)) return;
								}
							}
						}
					}
				}
			}
		}
	}

	private void checkOnlineStaff(Player player, String message) {
		for (Player staff : plugin.getServer().getOnlinePlayers()) {
			if (staff.hasPermission(Permissions.NOTIFY_ANTI_SWEAR.getNode())) {
				Messages.ANTI_SWEAR_CHAT_NOTIFY_STAFF_FORMAT.sendMessage(staff, new HashMap<>() {{
					put("{player}", player.getName());
					put("{message}", message);
				}});
			}
		}

		Methods.tellConsole(Messages.ANTI_SWEAR_CHAT_NOTIFY_STAFF_FORMAT.getMessage(this.plugin.getServer().getConsoleSender(), new HashMap<>() {{
			put("{player}", player.getName());
			put("{message}", message);
		}}), false);
	}

	private void commandSwearCheck(FileConfiguration config, Player player, String message, Date time) {
		if (config.getBoolean("Anti_Swear.Commands.Log_Swearing", false)) {
			try {
				FileWriter fw = new FileWriter(new File(new File(this.plugin.getDataFolder(), "Logs"), "Swears.txt"), true);
				BufferedWriter bw = new BufferedWriter(fw);
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
				String command = config.getString("Anti_Swear.Commands.Executed_Command").replace("{player}", player.getName());

				List<String> commands = config.getStringList("Anti_Swear.Commands.Executed_Command");

				dispatchCommandRunnable(player, command, commands);
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onSwearSign(SignChangeEvent event) {
		Player player = event.getPlayer();
		Date time = Calendar.getInstance().getTime();

		FileConfiguration bannedWords = Files.BANNED_WORDS.getConfiguration();
		FileConfiguration config = Files.CONFIG.getConfiguration();

		List<String> whitelisted = bannedWords.getStringList("Whitelisted_Words");
		List<String> blockedWordsList = bannedWords.getStringList("Banned-Words");

		if (config.getBoolean("Anti_Swear.Signs.Enable", false)) {
			if (!player.hasPermission(Permissions.BYPASS_ANTI_SWEAR.getNode())) {
				if (config.getBoolean("Anti_Swear.Signs.Increase_Sensitivity", false)) {
					for (int line = 0; line < 4; line++) {
						String message = event.getLine(line);
						assert message != null;
						String curseMessage = message.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").replaceAll("\\s+", "");

						for (String blockedWords : blockedWordsList) {
							for (String allowed : whitelisted) {
								if (message.contains(allowed.toLowerCase())) return;
							}

							if (curseMessage.contains(blockedWords)) {
								Messages.ANTI_SWEAR_SIGNS_MESSAGE.sendMessage(player);

								if (config.getBoolean("Anti_Swear.Signs.Block_Sign", false)) event.setCancelled(true);

								if (config.getBoolean("Anti_Swear.Signs.Notify_Staff", false)) {
									if (config.getBoolean("Anti_Swear.Signs.Notify_Staff")) {
										for (Player staff : plugin.getServer().getOnlinePlayers()) {
											if (staff.hasPermission(Permissions.NOTIFY_ANTI_SWEAR.getNode())) {
												Messages.ANTI_SWEAR_SIGNS_NOTIFY_STAFF_FORMAT.sendMessage(staff, new HashMap<>() {{
													put("{player}", player.getName());
													put("{message}", message);
												}});
											}
										}
									}

									Methods.tellConsole(Messages.ANTI_SWEAR_SIGNS_NOTIFY_STAFF_FORMAT.getMessage(this.plugin.getServer().getConsoleSender(), new HashMap<>() {{
										put("{player}", player.getName());
										put("{message}", message);
									}}), false);

									checkSwear(config, player, time, line, message);
								}
							}
						}
					}

					if (!config.getBoolean("Anti_Swear.Signs.Increase_Sensitivity", false)) {
						for (int line = 0; line < 4; line++) {
							String message = event.getLine(line);

							assert message != null;
							for (String curseMessages : message.toLowerCase().split(" ")) {
								if (!player.hasPermission(Permissions.BYPASS_ANTI_SWEAR.getNode())) {
									if (bannedWords.getStringList("Banned-Words").contains(curseMessages)) {
										Messages.ANTI_SWEAR_SIGNS_MESSAGE.sendMessage(player);

										if (config.getBoolean("Anti_Swear.Signs.Block_Sign", false)) event.setCancelled(true);

										if (config.getBoolean("Anti_Swear.Signs.Notify_Staff", false)) {
											if (config.getBoolean("Anti_Swear.Signs.Notify_Staff")) {
												for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
													if (staff.hasPermission(Permissions.NOTIFY_ANTI_SWEAR.getNode())) {
														Messages.ANTI_SWEAR_SIGNS_NOTIFY_STAFF_FORMAT.sendMessage(staff, new HashMap<>() {{
															put("{player}", player.getName());
															put("{message}", message);
														}});
													}
												}
											}

											Methods.tellConsole(Messages.ANTI_SWEAR_SIGNS_NOTIFY_STAFF_FORMAT.getMessage(this.plugin.getServer().getConsoleSender(), new HashMap<>() {{
												put("{player}", player.getName());
												put("{message}", message);
											}}), false);

											checkSwear(config, player, time, line, message);

											break;
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void checkSwear(FileConfiguration config, Player player, Date time, int line, String message) {
		if (config.getBoolean("Anti_Swear.Signs.Log_Swearing", false)) {
			try {
				FileWriter fw = new FileWriter(new File(new File(this.plugin.getDataFolder(), "Logs"), "Swears.txt"), true);
				BufferedWriter bw = new BufferedWriter(fw);
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
				String command = config.getString("Anti_Swear.Signs.Executed_Command").replace("{player}", player.getName());
				List<String> commands = config.getStringList("Anti_Swear.Signs.Executed_Command");

				dispatchCommandRunnable(player, command, commands);
			}
		}
	}

	private void dispatchCommandRunnable(Player player, String command, List<String> commands) {
		new FoliaScheduler(Scheduler.global_scheduler) {
			@Override
			public void run() {
				plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);

				for (String cmd : commands) {
					plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), cmd.replace("{player}", player.getName()));
				}
			}
		}.run();
	}
}