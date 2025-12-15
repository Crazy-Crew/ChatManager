package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.fusion.paper.scheduler.FoliaScheduler;
import com.ryderbelserion.fusion.paper.scheduler.Scheduler;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
public class ListenerAntiAdvertising extends Global implements Listener {

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final Player player = event.getPlayer();
		final String playerName = event.getPlayer().getName();
		final String message = event.getMessage();
		final Date time = Calendar.getInstance().getTime();

		final List<String> whitelisted = config.getStringList("Anti_Advertising.Whitelist");

		final Pattern firstPattern = Pattern.compile("[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[0o]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}");
		final Pattern secondPattern = Pattern.compile("[a-zA-Z0-9\\-.]+(\\.|d[o0]t|\\(d[o0]t\\)|-|,)+(com|org|net|co|uk|sk|biz|mobi|xxx|io|ts|adv|de|eu|noip|gs|au|pl|cz|ru)");

		final Matcher firstMatchIncrease = firstPattern.matcher(event.getMessage().toLowerCase().replaceAll("\\s+", ""));
		final Matcher secondMatchIncrease = secondPattern.matcher(event.getMessage().toLowerCase().replaceAll("\\s+", ""));

		final Matcher firstMatch = firstPattern.matcher(event.getMessage().toLowerCase());
		final Matcher secondMatch = secondPattern.matcher(event.getMessage().toLowerCase());

		if (!config.getBoolean("Anti_Advertising.Chat.Enable", false)) return;

		final boolean isValid = this.staffChatData.containsUser(player.getUniqueId());

		if (isValid && player.hasPermission(Permissions.BYPASS_ANTI_ADVERTISING.getNode())) return;

		for (final String allowed : whitelisted) {
			if (event.getMessage().contains(allowed.toLowerCase())) return;
		}

		if (config.getBoolean("Anti_Advertising.Chat.Increase_Sensitivity", false)) {
			chatMatch(event, player, playerName, message, time, firstMatchIncrease, secondMatchIncrease);

			return;
		}

		chatMatch(event, player, playerName, message, time, firstMatch, secondMatch);
	}

	private void chatMatch(final AsyncPlayerChatEvent event, final Player player, final String playerName, final String message, final Date time, final Matcher firstMatch, final Matcher secondMatch) {
		if (!firstMatch.find() || !secondMatch.find()) return;

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		event.setCancelled(true);

		Messages.ANTI_ADVERTISING_CHAT_MESSAGE.sendMessage(player);

		if (config.getBoolean("Anti_Advertising.Chat.Notify_Staff", false)) {
			for (final Player staff : this.server.getOnlinePlayers()) {
				if (staff.hasPermission(Permissions.NOTIFY_ANTI_ADVERTISING.getNode())) {
					Messages.ANTI_ADVERTISING_CHAT_NOTIFY_STAFF.sendMessage(staff, new HashMap<>() {{
						put("{player}", player.getName());
						put("{message}", message);
					}});
				}
			}

			final String msg = Messages.ANTI_ADVERTISING_CHAT_NOTIFY_STAFF.getMessage(this.sender, new HashMap<>() {{
				put("{player}", player.getName());
				put("{message}", message);
			}});

			Methods.tellConsole(msg, false);
		}

		if (config.getBoolean("Anti_Advertising.Chat.Execute_Command", false)) {
			if (config.contains("Anti_Advertising.Chat.Executed_Command")) {
				final String command = config.getString("Anti_Advertising.Chat.Executed_Command").replace("{player}", player.getName());
				final List<String> commands = config.getStringList("Anti_Advertising.Chat.Executed_Command");

				new FoliaScheduler(plugin, Scheduler.global_scheduler) {
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

		if (!config.getBoolean("Anti_Advertising.Chat.Log_Advertisers", false)) return;

		try {
			final FileWriter fw = new FileWriter(new File(new File(this.dataFolder, "Logs"), "Advertisements.txt"), true);
			final BufferedWriter bw2 = new BufferedWriter(fw);
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
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final Player player = event.getPlayer();
		final String playerName = event.getPlayer().getName();
		final String message = event.getMessage();
		final Date time = Calendar.getInstance().getTime();

		final List<String> whitelisted = config.getStringList("Anti_Advertising.Whitelist");
		final List<String> whitelist = config.getStringList("Anti_Advertising.Commands.Whitelist");

		final Pattern firstPattern = Pattern.compile("[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[0o]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}");
		final Pattern secondPattern = Pattern.compile("[a-zA-Z0-9\\-.]+(\\.|d[o0]t|\\(d[o0]t\\)|-|,)+(com|org|net|co|uk|sk|biz|mobi|xxx|io|ts|adv|de|eu|noip|gs|au|pl|cz|ru)");

		final Matcher firstMatchIncrease = firstPattern.matcher(event.getMessage().toLowerCase().replaceAll("\\s+", ""));
		final Matcher secondMatchIncrease = secondPattern.matcher(event.getMessage().toLowerCase().replaceAll("\\s+", ""));

		final Matcher firstMatch = firstPattern.matcher(event.getMessage().toLowerCase());
		final Matcher secondMatch = secondPattern.matcher(event.getMessage().toLowerCase());

		if (!config.getBoolean("Anti_Advertising.Commands.Enable", false)) return;

		final boolean isValid = this.staffChatData.containsUser(player.getUniqueId());

		if (isValid && player.hasPermission(Permissions.BYPASS_ANTI_ADVERTISING.getNode())) return;

		for (final String allowed : whitelisted) {
			if (event.getMessage().contains(allowed.toLowerCase())) return;
		}

		for (final String allowed : whitelist) {
			if (event.getMessage().toLowerCase().contains(allowed)) return;
		}

		if (config.getBoolean("Anti_Advertising.Commands.Increase_Sensitivity", false)) {
			increasedSensitivity(event, player, playerName, message, time, firstMatchIncrease, secondMatchIncrease);

			return;
		}

		increasedSensitivity(event, player, playerName, message, time, firstMatch, secondMatch);
	}

	private void increasedSensitivity(final PlayerCommandPreprocessEvent event, final Player player, final String playerName, final String message, final Date time, final Matcher firstMatch, final Matcher secondMatch) {
		if (!firstMatch.find() || !secondMatch.find()) return;

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		event.setCancelled(true);

		Messages.ANTI_ADVERTISING_COMMANDS_MESSAGE.sendMessage(player);

		if (config.getBoolean("Anti_Advertising.Commands.Notify_Staff", false)) {
			for (final Player staff : this.server.getOnlinePlayers()) {
				if (staff.hasPermission(Permissions.NOTIFY_ANTI_ADVERTISING.getNode())) {
					Messages.ANTI_ADVERTISING_COMMANDS_NOTIFY_STAFF.sendMessage(staff, new HashMap<>() {{
						put("{player}", player.getName());
						put("{message}", message);
					}});
				}
			}

			final String msg = Messages.ANTI_ADVERTISING_COMMANDS_NOTIFY_STAFF.getMessage(this.sender, new HashMap<>() {{
				put("{player}", player.getName());
				put("{message}", message);
			}});

			Methods.tellConsole(msg, false);
		}

		if (config.getBoolean("Anti_Advertising.Commands.Execute_Command", false)) {
			if (config.contains("Anti_Advertising.Commands.Executed_Command")) {
				final String command = config.getString("Anti_Advertising.Commands.Executed_Command").replace("{player}", player.getName());
				final List<String> commands = config.getStringList("Anti_Advertising.Commands.Executed_Command");

				new FoliaScheduler(plugin, Scheduler.global_scheduler) {
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

		if (!config.getBoolean("Anti_Advertising.Commands.Log_Advertisers", false)) return;

		try {
			final FileWriter fw = new FileWriter(new File(new File(this.dataFolder, "Logs"), "Advertisements.txt"), true);
			final BufferedWriter bw2 = new BufferedWriter(fw);
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
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final Player player = event.getPlayer();
		final String playerName = event.getPlayer().getName();
		final Date time = Calendar.getInstance().getTime();

		final List<String> whitelisted = config.getStringList("Anti_Advertising.Whitelist");

		final Pattern firstPattern = Pattern.compile("[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[0o]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}");
		final Pattern secondPattern = Pattern.compile("[a-zA-Z0-9\\-.]+(\\.|d[o0]t|\\(d[o0]t\\)|-|,)+(com|org|net|co|uk|sk|biz|mobi|xxx|io|ts|adv|de|eu|noip|gs|au|pl|cz|ru)");

		if (!config.getBoolean("Anti_Advertising.Signs.Enable", false)) return;

		for (int line = 0; line < 4; line++) {
			final String message = event.getLine(line);

			final boolean isValid = this.staffChatData.containsUser(player.getUniqueId());

			if (!isValid && !player.hasPermission(Permissions.BYPASS_ANTI_ADVERTISING.getNode())) continue;

			for (final String allowed : whitelisted) {
				assert message != null;
				if (message.toLowerCase().contains(allowed)) return;
			}

			final String str = "[" + time + "] [Sign] " + playerName + ": Line: " + line + " Text: " + message.replaceAll("§", "&");

			if (config.getBoolean("Anti_Advertising.Signs.Increase_Sensitivity", false)) {
				final Matcher firstMatchIncrease = firstPattern.matcher(message.toLowerCase().replaceAll("\\s+", ""));
				final Matcher secondMatchIncrease = secondPattern.matcher(message.toLowerCase().replaceAll("\\s+", ""));

				if (findMatches(event, player, message, str, firstMatchIncrease, secondMatchIncrease)) return;

				return;
			}

			final Matcher firstMatch = firstPattern.matcher(message.toLowerCase());
			final Matcher secondMatch = secondPattern.matcher(message.toLowerCase());

			if (findMatches(event, player, message, str, firstMatch, secondMatch)) return;
		}
	}

	private boolean findMatches(final SignChangeEvent event, final Player player, final String message, final String str, final Matcher firstMatch, final Matcher secondMatch) {
		if (!firstMatch.find() || !secondMatch.find()) return false;

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		event.setCancelled(true);

		Messages.ANTI_ADVERTISING_SIGNS_MESSAGE.sendMessage(player);

		if (config.getBoolean("Anti_Advertising.Signs.Notify_Staff", false)) {
			for (final Player staff : this.server.getOnlinePlayers()) {
				if (staff.hasPermission(Permissions.NOTIFY_ANTI_ADVERTISING.getNode())) {
					Messages.ANTI_ADVERTISING_SIGNS_NOTIFY_STAFF.sendMessage(staff, new HashMap<>() {{
						put("{player}", player.getName());
						put("{message}", message);
					}});
				}
			}

			final String msg = Messages.ANTI_ADVERTISING_SIGNS_NOTIFY_STAFF.getMessage(this.sender, new HashMap<>() {{
				put("{player}", player.getName());
				put("{message}", message);
			}});

			Methods.tellConsole(msg, false);
		}

		if (config.getBoolean("Anti_Advertising.Signs.Execute_Command", false)) {
			if (config.contains("Anti_Advertising.Signs.Executed_Command")) {
				final String command = config.getString("Anti_Advertising.Signs.Executed_Command").replace("{player}", player.getName());
				final List<String> commands = config.getStringList("Anti_Advertising.Signs.Executed_Command");

				new FoliaScheduler(plugin, Scheduler.global_scheduler) {
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

		if (config.getBoolean("Anti_Advertising.Signs.Log_Advertisers", false)) {
			try {
				final FileWriter fw = new FileWriter(new File(new File(this.dataFolder, "Logs"), "Advertisements.txt"), true);
				final BufferedWriter bw2 = new BufferedWriter(fw);
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