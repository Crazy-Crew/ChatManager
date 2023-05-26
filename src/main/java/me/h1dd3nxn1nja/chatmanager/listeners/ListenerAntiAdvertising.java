package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.configs.builder.enums.Files;
import com.ryderbelserion.chatmanager.api.configs.types.FilterSettings;
import com.ryderbelserion.chatmanager.api.configs.types.sections.AdvertisementSection;
import com.ryderbelserion.chatmanager.api.interfaces.Universal;
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

public class ListenerAntiAdvertising implements Listener, Universal {

	private final ChatManager plugin = ChatManager.getPlugin();

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String playerName = event.getPlayer().getName();
		String message = event.getMessage();
		Date time = Calendar.getInstance().getTime();

		List<String> whitelisted = configSettings.getProperty(AdvertisementSection.WEBSITE_WHITELIST);

		Pattern firstPattern = Pattern.compile("[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[0o]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}");
		Pattern secondPattern = Pattern.compile("[a-zA-Z0-9\\-.]+(\\.|d[o0]t|\\(d[o0]t\\)|-|,)+(com|org|net|co|uk|sk|biz|mobi|xxx|io|ts|adv|de|eu|noip|gs|au|pl|cz|ru)");

		Matcher firstMatchIncrease = firstPattern.matcher(event.getMessage().toLowerCase().replaceAll("\\s+", ""));
		Matcher secondMatchIncrease = secondPattern.matcher(event.getMessage().toLowerCase().replaceAll("\\s+", ""));

		Matcher firstMatch = firstPattern.matcher(event.getMessage().toLowerCase());
		Matcher secondMatch = secondPattern.matcher(event.getMessage().toLowerCase());

		if (!configSettings.getProperty(AdvertisementSection.BLOCK_ADVERTISING_CHAT)) return;

		boolean isValid = cacheManager.getStaffChatDataSet().containsUser(player.getUniqueId());

		if (isValid && player.hasPermission("chatmanager.bypass.antiadvertising")) return;

		for (String allowed : whitelisted) {
			if (event.getMessage().contains(allowed.toLowerCase())) return;
		}

		if (configSettings.getProperty(AdvertisementSection.INCREASE_SENSITIVITY_CHAT)) {
			chatMatch(event, player, playerName, message, time, firstMatchIncrease, secondMatchIncrease);
			return;
		}

		chatMatch(event, player, playerName, message, time, firstMatch, secondMatch);
	}

	private void chatMatch(AsyncPlayerChatEvent event, Player player, String playerName, String message, Date time, Matcher firstMatch, Matcher secondMatch) {
		if (!firstMatch.find() || !secondMatch.find()) return;

		event.setCancelled(true);
		//Methods.sendMessage(player, messages.getString("Anti_Advertising.Chat.Message"), true);

		if (configSettings.getProperty(AdvertisementSection.NOTIFY_STAFF_CHAT)) {
			for (Player staff : plugin.getServer().getOnlinePlayers()) {
				if (staff.hasPermission("chatmanager.notify.antiadvertising")) {
					//Methods.sendMessage(staff, messages.getString("Anti_Advertising.Chat.Notify_Staff_Format").replace("{player}", player.getName()).replace("{message}", message), true);
				}
			}

			//Methods.tellConsole(messages.getString("Anti_Advertising.Chat.Notify_Staff_Format").replace("{player}", player.getName()).replace("{message}", message), true);
		}

		if (configSettings.getProperty(AdvertisementSection.EXECUTE_CHAT_ADVERTS)) {
			List<String> commands = configSettings.getProperty(AdvertisementSection.EXECUTED_CHAT_ADVERTS);

			new BukkitRunnable() {
				public void run() {
					for (String cmd : commands) {
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), cmd.replace("{player}", player.getName()));
					}
				}
			}.runTask(plugin);
		}

		if (!configSettings.getProperty(AdvertisementSection.LOG_ADVERTISEMENTS_CHAT)) return;

		try {
			FileWriter fw = new FileWriter(Files.advertisement_logs.getFile(), true);
			BufferedWriter bw2 = new BufferedWriter(fw);
			bw2.write("[" + time + "] [Chat] " + playerName + ": " + message.replaceAll("§", "&"));
			bw2.newLine();
			fw.flush();
			bw2.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String playerName = event.getPlayer().getName();
		String message = event.getMessage();
		Date time = Calendar.getInstance().getTime();

		List<String> allowedWebsites = configSettings.getProperty(AdvertisementSection.WEBSITE_WHITELIST);
		List<String> allowedCommands = wordFilterSettings.getProperty(FilterSettings.WHITELISTED_COMMANDS);

		Pattern firstPattern = Pattern.compile("[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[0o]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}");
		Pattern secondPattern = Pattern.compile("[a-zA-Z0-9\\-.]+(\\.|d[o0]t|\\(d[o0]t\\)|-|,)+(com|org|net|co|uk|sk|biz|mobi|xxx|io|ts|adv|de|eu|noip|gs|au|pl|cz|ru)");

		Matcher firstMatchIncrease = firstPattern.matcher(event.getMessage().toLowerCase().replaceAll("\\s+", ""));
		Matcher secondMatchIncrease = secondPattern.matcher(event.getMessage().toLowerCase().replaceAll("\\s+", ""));

		Matcher firstMatch = firstPattern.matcher(event.getMessage().toLowerCase());
		Matcher secondMatch = secondPattern.matcher(event.getMessage().toLowerCase());

		if (!configSettings.getProperty(AdvertisementSection.BLOCK_ADVERTISING_COMMANDS)) return;

		boolean isValid = cacheManager.getStaffChatDataSet().containsUser(player.getUniqueId());

		if (isValid && player.hasPermission("chatmanager.bypass.antiadvertising")) return;

		for (String link : allowedWebsites) {
			if (event.getMessage().contains(link.toLowerCase())) return;
		}

		for (String cmd : allowedCommands) {
			if (event.getMessage().toLowerCase().contains(cmd)) return;
		}

		if (configSettings.getProperty(AdvertisementSection.INCREASE_SENSITIVITY_COMMANDS)) {
			checkAntiAdvertising(event, player, playerName, message, time, firstMatchIncrease, secondMatchIncrease);
			return;
		}

		checkAntiAdvertising(event, player, playerName, message, time, firstMatch, secondMatch);
	}

	private void checkAntiAdvertising(PlayerCommandPreprocessEvent event, Player player, String playerName, String message, Date time, Matcher firstMatch, Matcher secondMatch) {
		if (!firstMatch.find() || !secondMatch.find()) return;

		event.setCancelled(true);
		//Methods.sendMessage(player, messages.getString("Anti_Advertising.Commands.Message"), true);

		if (configSettings.getProperty(AdvertisementSection.NOTIFY_STAFF_COMMANDS)) {
			for (Player staff : plugin.getServer().getOnlinePlayers()) {
				if (staff.hasPermission("chatmanager.notify.antiadvertising")) {
					//Methods.sendMessage(staff, messages.getString("Anti_Advertising.Commands.Notify_Staff_Format").replace("{player}", player.getName()).replace("{message}", message), true);
				}
			}

			//Methods.tellConsole(messages.getString("Anti_Advertising.Commands.Notify_Staff_Format").replace("{player}", player.getName()).replace("{message}", message), true);
		}

		if (configSettings.getProperty(AdvertisementSection.EXECUTE_COMMANDS_ADVERTS)) {
			List<String> commands = configSettings.getProperty(AdvertisementSection.EXECUTED_COMMANDS_ADVERTS);
			new BukkitRunnable() {
				public void run() {
					for (String cmd : commands) {
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), cmd.replace("{player}", player.getName()));
					}
				}
			}.runTask(plugin);
		}

		if (!configSettings.getProperty(AdvertisementSection.LOG_ADVERTISEMENTS_COMMANDS)) return;

		try {
			FileWriter fw = new FileWriter(Files.advertisement_logs.getFile(), true);
			BufferedWriter bw2 = new BufferedWriter(fw);
			bw2.write("[" + time + "] [Command] " + playerName + ": " + message.replaceAll("§", "&"));
			bw2.newLine();
			fw.flush();
			bw2.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onSign(SignChangeEvent event) {
		Player player = event.getPlayer();
		String playerName = event.getPlayer().getName();
		Date time = Calendar.getInstance().getTime();

		List<String> whitelisted = configSettings.getProperty(AdvertisementSection.WEBSITE_WHITELIST);

		Pattern firstPattern = Pattern.compile("[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[0o]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}");
		Pattern secondPattern = Pattern.compile("[a-zA-Z0-9\\-.]+(\\.|d[o0]t|\\(d[o0]t\\)|-|,)+(com|org|net|co|uk|sk|biz|mobi|xxx|io|ts|adv|de|eu|noip|gs|au|pl|cz|ru)");

		if (!configSettings.getProperty(AdvertisementSection.BLOCK_ADVERTISING_SIGNS)) return;

		for (int line = 0; line < 4; line++) {
			String message = event.getLine(line);

			boolean isValid = cacheManager.getStaffChatDataSet().containsUser(player.getUniqueId());

			if (!isValid && !player.hasPermission("chatmanager.bypass.antiadvertising")) continue;

			for (String allowed : whitelisted) {
				assert message != null;
				if (message.toLowerCase().contains(allowed)) return;
			}

			String str = "[" + time + "] [Sign] " + playerName + ": Line: " + line + " Text: " + message.replaceAll("§", "&");

			if (configSettings.getProperty(AdvertisementSection.INCREASE_SENSITIVITY_SIGNS)) {
				Matcher firstMatchIncrease = firstPattern.matcher(message.toLowerCase().replaceAll("\\s+", ""));
				Matcher secondMatchIncrease = secondPattern.matcher(message.toLowerCase().replaceAll("\\s+", ""));

				if (findMatches(event, player, message, str, firstMatchIncrease, secondMatchIncrease)) return;

				return;
			}

			Matcher firstMatch = firstPattern.matcher(message.toLowerCase());
			Matcher secondMatch = secondPattern.matcher(message.toLowerCase());

			if (findMatches(event, player, message, str, firstMatch, secondMatch)) return;
		}
	}

	private boolean findMatches(SignChangeEvent event, Player player, String message, String str, Matcher firstMatch, Matcher secondMatch) {
		if (!firstMatch.find() || !secondMatch.find()) return false;

		event.setCancelled(true);
		//Methods.sendMessage(player, messages.getString("Anti_Advertising.Signs.Message"), true);

		if (configSettings.getProperty(AdvertisementSection.NOTIFY_STAFF_SIGNS)) {
			for (Player staff : plugin.getServer().getOnlinePlayers()) {
				if (staff.hasPermission("chatmanager.notify.antiadvertising")) {
					//Methods.sendMessage(staff, messages.getString("Anti_Advertising.Signs.Notify_Staff_Format").replace("{player}", player.getName()).replace("{message}", message), true);
				}
			}

			//Methods.tellConsole(messages.getString("Anti_Advertising.Signs.Notify_Staff_Format").replace("{player}", player.getName()).replace("{message}", message), true);
		}

		if (configSettings.getProperty(AdvertisementSection.EXECUTE_SIGNS_ADVERTS)) {
			List<String> commands = configSettings.getProperty(AdvertisementSection.EXECUTED_SIGNS_ADVERTS);

			new BukkitRunnable() {
				public void run() {
					for (String cmd : commands) {
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), cmd.replace("{player}", player.getName()));
					}
				}
			}.runTask(plugin);
		}

		if (configSettings.getProperty(AdvertisementSection.LOG_ADVERTISEMENTS_COMMANDS)) {
			try {
				FileWriter fw = new FileWriter(Files.advertisement_logs.getFile(), true);
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
}