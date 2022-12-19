package me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListenerAntiAdvertising implements Listener {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		FileConfiguration config = settingsManager.getConfig();
		FileConfiguration messages = settingsManager.getMessages();

		Player player = event.getPlayer();
		String playername = event.getPlayer().getName();
		String message = event.getMessage();
		Date time = Calendar.getInstance().getTime();

		List<String> whitelisted = config.getStringList("Anti_Advertising.Whitelist");

		Pattern firstPattern = Pattern.compile("[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[0o]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}");
		Pattern secondPattern = Pattern.compile("[a-zA-Z0-9\\-.]+(\\.|d[o0]t|\\(d[o0]t\\)|-|,)+(com|org|net|co|uk|sk|biz|mobi|xxx|io|ts|adv|de|eu|noip|gs|au|pl|cz|ru)");

		Matcher firstMatchIncrease = firstPattern.matcher(event.getMessage().toLowerCase().replaceAll("\\s+", ""));
		Matcher secondMatchIncrease = secondPattern.matcher(event.getMessage().toLowerCase().replaceAll("\\s+", ""));

		Matcher firstMatch = firstPattern.matcher(event.getMessage().toLowerCase());
		Matcher secondMatch = secondPattern.matcher(event.getMessage().toLowerCase());

		if (config.getBoolean("Anti_Advertising.Chat.Enable")) {
			if (!Methods.cm_staffChat.contains(player.getUniqueId())) {
				if (!player.hasPermission("chatmanager.bypass.antiadvertising")) {
					for (String allowed : whitelisted) {
						if (event.getMessage().contains(allowed.toLowerCase())) return;
					}

					if (config.getBoolean("Anti_Advertising.Chat.Increase_Sensitivity")) {
						chatMatch(event, config, messages, player, playername, message, time, firstMatchIncrease, secondMatchIncrease);
					} else {
						chatMatch(event, config, messages, player, playername, message, time, firstMatch, secondMatch);
					}
				}
			}
		}
	}

	private void chatMatch(AsyncPlayerChatEvent event, FileConfiguration config, FileConfiguration messages, Player player, String playername, String message, Date time, Matcher firstMatch, Matcher secondMatch) {
		if (firstMatch.find() || secondMatch.find()) {
			event.setCancelled(true);
			Methods.sendMessage(player, messages.getString("Anti_Advertising.Chat.Message"), true);

			if (config.getBoolean("Anti_Advertising.Chat.Notify_Staff")) {
				for (Player staff : plugin.getServer().getOnlinePlayers()) {
					if (staff.hasPermission("chatmanager.notify.antiadvertising")) {
						Methods.sendMessage(staff, messages.getString("Anti_Advertising.Chat.Notify_Staff_Format").replace("{player}", player.getName()).replace("{message}", message), true);
					}
				}

				Methods.tellConsole(messages.getString("Anti_Advertising.Chat.Notify_Staff_Format").replace("{player}" , player.getName()).replace("{message}", message), true);
			}

			if (config.getBoolean("Anti_Advertising.Chat.Execute_Command")) {
				if (config.contains("Anti_Advertising.Chat.Executed_Command")) {
					String command = config.getString("Anti_Advertising.Chat.Executed_Command").replace("{player}", player.getName());
					List<String> commands = config.getStringList("Anti_Advertising.Chat.Executed_Command");

					new BukkitRunnable() {
						public void run() {
							plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);

							for (String cmd : commands) {
								plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), cmd.replace("{player}", player.getName()));
							}
						}
					}.runTask(plugin);
				}
			}

			if (config.getBoolean("Anti_Advertising.Chat.Log_Advertisers")) {
				try {
					FileWriter fw = new FileWriter(settingsManager.getAdvertisementLogs(), true);
					BufferedWriter bw2 = new BufferedWriter(fw);
					bw2.write("[" + time + "] [Chat] " + playername + ": " + message.replaceAll("§", "&"));
					bw2.newLine();
					fw.flush();
					bw2.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		FileConfiguration config = settingsManager.getConfig();
		FileConfiguration messages = settingsManager.getMessages();

		Player player = event.getPlayer();
		String playerName = event.getPlayer().getName();
		String message = event.getMessage();
		Date time = Calendar.getInstance().getTime();

		List<String> whitelisted = config.getStringList("Anti_Advertising.Whitelist");
		List<String> whitelist = config.getStringList("Anti_Advertising.Commands.Whitelist");

		Pattern firstPattern = Pattern.compile("[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[0o]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}");
		Pattern secondPattern = Pattern.compile("[a-zA-Z0-9\\-.]+(\\.|d[o0]t|\\(d[o0]t\\)|-|,)+(com|org|net|co|uk|sk|biz|mobi|xxx|io|ts|adv|de|eu|noip|gs|au|pl|cz|ru)");

		Matcher firstMatchIncrease = firstPattern.matcher(event.getMessage().toLowerCase().replaceAll("\\s+", ""));
		Matcher secondMatchIncrease = secondPattern.matcher(event.getMessage().toLowerCase().replaceAll("\\s+", ""));

		Matcher firstMatch = firstPattern.matcher(event.getMessage().toLowerCase());
		Matcher secondMatch = secondPattern.matcher(event.getMessage().toLowerCase());

		if (config.getBoolean("Anti_Advertising.Commands.Enable")) {
			if (!Methods.cm_staffChat.contains(player.getUniqueId())) {
				if (!player.hasPermission("chatmanager.bypass.antiadvertising")) {
					for (String allowed : whitelisted) {
						if (event.getMessage().contains(allowed.toLowerCase())) return;
					}

					for (String allowed : whitelist) {
						if (event.getMessage().toLowerCase().contains(allowed)) return;
					}

					if (config.getBoolean("Anti_Advertising.Commands.Increase_Sensitivity")) {
						increasedSensitivity(event, config, messages, player, playerName, message, time, firstMatchIncrease, secondMatchIncrease);
					} else {
						increasedSensitivity(event, config, messages, player, playerName, message, time, firstMatch, secondMatch);
					}
				}
			}
		}
	}

	private void increasedSensitivity(PlayerCommandPreprocessEvent event, FileConfiguration config, FileConfiguration messages, Player player, String playername, String message, Date time, Matcher firstMatch, Matcher secondMatch) {
		if (firstMatch.find() || secondMatch.find()) {
			event.setCancelled(true);
			Methods.sendMessage(player, messages.getString("Anti_Advertising.Commands.Message"), true);

			if (config.getBoolean("Anti_Advertising.Commands.Notify_Staff")) {
				for (Player staff : plugin.getServer().getOnlinePlayers()) {
					if (staff.hasPermission("chatmanager.notify.antiadvertising")) {
						Methods.sendMessage(staff, messages.getString("Anti_Advertising.Commands.Notify_Staff_Format").replace("{player}", player.getName()).replace("{message}", message), true);
					}
				}

				Methods.tellConsole(messages.getString("Anti_Advertising.Commands.Notify_Staff_Format").replace("{player}" , player.getName()).replace("{message}", message), true);
			}

			if (config.getBoolean("Anti_Advertising.Commands.Execute_Command")) {
				if (config.contains("Anti_Advertising.Commands.Executed_Command")) {
					String command = config.getString("Anti_Advertising.Commands.Executed_Command").replace("{player}", player.getName());
					List<String> commands = config.getStringList("Anti_Advertising.Commands.Executed_Command");
					new BukkitRunnable() {
						public void run() {
							plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);

							for (String cmd : commands) {
								plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), cmd.replace("{player}", player.getName()));
							}
						}
					}.runTask(plugin);
				}
			}

			if (config.getBoolean("Anti_Advertising.Commands.Log_Advertisers")) {
				try {
					FileWriter fw = new FileWriter(settingsManager.getAdvertisementLogs(), true);
					BufferedWriter bw2 = new BufferedWriter(fw);
					bw2.write("[" + time + "] [Command] " + playername + ": " + message.replaceAll("§", "&"));
					bw2.newLine();
					fw.flush();
					bw2.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onSign(SignChangeEvent event) {

		FileConfiguration config = settingsManager.getConfig();
		FileConfiguration messages = settingsManager.getMessages();

		Player player = event.getPlayer();
		String playername = event.getPlayer().getName();
		Date time = Calendar.getInstance().getTime();

		List<String> whitelisted = config.getStringList("Anti_Advertising.Whitelist");

		Pattern firstPattern = Pattern.compile("[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[0o]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}");
		Pattern secondPattern = Pattern.compile("[a-zA-Z0-9\\-.]+(\\.|d[o0]t|\\(d[o0]t\\)|-|,)+(com|org|net|co|uk|sk|biz|mobi|xxx|io|ts|adv|de|eu|noip|gs|au|pl|cz|ru)");

		for (int line = 0; line < 4; line++) {
			String message = event.getLine(line);
			if (config.getBoolean("Anti_Advertising.Signs.Enable")) {
				if (!Methods.cm_staffChat.contains(player.getUniqueId())) {
					if (!player.hasPermission("chatmanager.bypass.antiadvertising")) {
						for (String allowed : whitelisted) {
							assert message != null;
							if (message.toLowerCase().contains(allowed)) return;
						}

						String str = "[" + time + "] [Sign] " + playername + ": Line: " + line + " Text: " + message.replaceAll("§", "&");
						if (config.getBoolean("Anti_Advertising.Signs.Increase_Sensitivity")) {
							Matcher firstMatchIncrease = firstPattern.matcher(message.toLowerCase().replaceAll("\\s+", ""));
							Matcher secondMatchIncrease = secondPattern.matcher(message.toLowerCase().replaceAll("\\s+", ""));

							if (findMatches(event, config, messages, player, message, str, firstMatchIncrease, secondMatchIncrease)) return;
						} else {
							Matcher firstMatch = firstPattern.matcher(message.toLowerCase());
							Matcher secondMatch = secondPattern.matcher(message.toLowerCase());

							if (findMatches(event, config, messages, player, message, str, firstMatch, secondMatch)) return;
						}
					}
				}
			}
		}
	}

	private boolean findMatches(SignChangeEvent event, FileConfiguration config, FileConfiguration messages, Player player, String message, String str, Matcher firstMatch, Matcher secondMatch) {
		if (firstMatch.find() || secondMatch.find()) {
			event.setCancelled(true);
			Methods.sendMessage(player, messages.getString("Anti_Advertising.Signs.Message"), true);

			if (config.getBoolean("Anti_Advertising.Signs.Notify_Staff")) {
				for (Player staff : plugin.getServer().getOnlinePlayers()) {
					if (staff.hasPermission("chatmanager.notify.antiadvertising")) {
						Methods.sendMessage(staff, messages.getString("Anti_Advertising.Signs.Notify_Staff_Format").replace("{player}", player.getName()).replace("{message}", message), true);
					}
				}

				Methods.tellConsole(messages.getString("Anti_Advertising.Signs.Notify_Staff_Format").replace("{player}" , player.getName()).replace("{message}", message), true);
			}

			if (config.getBoolean("Anti_Advertising.Signs.Execute_Command")) {
				if (config.contains("Anti_Advertising.Signs.Executed_Command")) {
					String command = config.getString("Anti_Advertising.Signs.Executed_Command").replace("{player}", player.getName());
					List<String> commands = config.getStringList("Anti_Advertising.Signs.Executed_Command");
					new BukkitRunnable() {
						public void run() {
							plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);

							for (String cmd : commands) {
								plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(),
										cmd.replace("{player}", player.getName()));
							}
						}
					}.runTask(plugin);
				}
			}

			if (config.getBoolean("Anti_Advertising.Signs.Log_Advertisers")) {
				try {
					FileWriter fw = new FileWriter(settingsManager.getAdvertisementLogs(), true);
					BufferedWriter bw2 = new BufferedWriter(fw);
					bw2.write(str);
					bw2.newLine();
					fw.flush();
					bw2.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			return true;
		}

		return false;
	}
}