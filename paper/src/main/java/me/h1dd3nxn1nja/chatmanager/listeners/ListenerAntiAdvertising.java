package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.api.objects.PaperUser;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.core.PlayerState;
import com.ryderbelserion.chatmanager.utils.DispatchUtils;
import com.ryderbelserion.chatmanager.utils.UserUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListenerAntiAdvertising implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	private final Server server = this.plugin.getServer();

	private final File dataFolder = this.plugin.getDataFolder();

	private final ConsoleCommandSender console = this.server.getConsoleSender();

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onChat(AsyncChatEvent event) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Anti_Advertising.Chat.Enable", false)) return;

		final Player player = event.getPlayer();
		final UUID uuid = player.getUniqueId();

		final PaperUser user = UserUtils.getUser(uuid);

		if (user.hasState(PlayerState.STAFF_CHAT) || Permissions.BYPASS_ANTI_ADVERTISING.hasPermission(player)) return;

		final String playerName = player.getName();
		final String message = event.signedMessage().message();
		final Date time = Calendar.getInstance().getTime();

		final List<String> whitelisted = config.getStringList("Anti_Advertising.Whitelist");

		final Pattern firstPattern = Pattern.compile("[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[0o]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}");
		final Pattern secondPattern = Pattern.compile("[a-zA-Z0-9\\-.]+(\\.|d[o0]t|\\(d[o0]t\\)|-|,)+(com|org|net|co|uk|sk|biz|mobi|xxx|io|ts|adv|de|eu|noip|gs|au|pl|cz|ru)");

		final Matcher firstMatchIncrease = firstPattern.matcher(message.replaceAll("\\s+", ""));
		final Matcher secondMatchIncrease = secondPattern.matcher(message.replaceAll("\\s+", ""));

		final Matcher firstMatch = firstPattern.matcher(message);
		final Matcher secondMatch = secondPattern.matcher(message);

		for (final String allowed : whitelisted) {
			if (message.contains(allowed.toLowerCase())) return;
		}

		if (config.getBoolean("Anti_Advertising.Chat.Increase_Sensitivity", false)) {
			match(event, player, playerName, message, time, firstMatchIncrease, secondMatchIncrease);

			return;
		}

		match(event, player, playerName, message, time, firstMatch, secondMatch);
	}

	private void match(final AsyncChatEvent event, final Player player, final String playerName, final String message, final Date time, final Matcher firstMatch, final Matcher secondMatch) {
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

			Methods.tellConsole(Messages.ANTI_ADVERTISING_CHAT_NOTIFY_STAFF.getMessage(this.console, new HashMap<>() {{
				put("{player}", player.getName());
				put("{message}", message);
			}}), false);
		}

		if (config.getBoolean("Anti_Advertising.Chat.Execute_Command", false)) {
			DispatchUtils.dispatchCommand(player, new ArrayList<>() {{
				addAll(config.getStringList("Anti_Advertising.Chat.Executed_Command"));
				add(config.getString("Anti_Advertising.Chat.Executed_Command", ""));
			}});
		}

		if (!config.getBoolean("Anti_Advertising.Chat.Log_Advertisers", false)) return;

		try {
			FileWriter fw = new FileWriter(new File(new File(this.dataFolder, "Logs"), "Advertisements.txt"), true);
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
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Anti_Advertising.Commands.Enable", false)) return;

		final Player player = event.getPlayer();
		final UUID uuid = player.getUniqueId();

		final PaperUser user = UserUtils.getUser(uuid);

		if (user.hasState(PlayerState.STAFF_CHAT) || Permissions.BYPASS_ANTI_ADVERTISING.hasPermission(player)) return;

		final String playerName = player.getName();
		final String message = event.getMessage();
		final Date time = Calendar.getInstance().getTime();

		final List<String> whitelisted = config.getStringList("Anti_Advertising.Whitelist");
		final List<String> whitelist = config.getStringList("Anti_Advertising.Commands.Whitelist");

		final Pattern firstPattern = Pattern.compile("[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[0o]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}");
		final Pattern secondPattern = Pattern.compile("[a-zA-Z0-9\\-.]+(\\.|d[o0]t|\\(d[o0]t\\)|-|,)+(com|org|net|co|uk|sk|biz|mobi|xxx|io|ts|adv|de|eu|noip|gs|au|pl|cz|ru)");

		final Matcher firstMatchIncrease = firstPattern.matcher(message.toLowerCase().replaceAll("\\s+", ""));
		final Matcher secondMatchIncrease = secondPattern.matcher(message.toLowerCase().replaceAll("\\s+", ""));

		final Matcher firstMatch = firstPattern.matcher(message.toLowerCase());
		final Matcher secondMatch = secondPattern.matcher(message.toLowerCase());

		for (final String allowed : whitelisted) {
			if (message.contains(allowed.toLowerCase())) return;
		}

		for (final String allowed : whitelist) {
			if (message.toLowerCase().contains(allowed)) return;
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

			Methods.tellConsole(Messages.ANTI_ADVERTISING_COMMANDS_NOTIFY_STAFF.getMessage(console, new HashMap<>() {{
				put("{player}", player.getName());
				put("{message}", message);
			}}), false);
		}

		if (config.getBoolean("Anti_Advertising.Commands.Execute_Command", false)) {
			DispatchUtils.dispatchCommand(player, new ArrayList<>() {{
				addAll(config.getStringList("Anti_Advertising.Commands.Executed_Command"));
				add(config.getString("Anti_Advertising.Commands.Executed_Command", ""));
			}});
		}

		if (!config.getBoolean("Anti_Advertising.Commands.Log_Advertisers", false)) return;

		try {
			FileWriter fw = new FileWriter(new File(new File(this.dataFolder, "Logs"), "Advertisements.txt"), true);
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
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Anti_Advertising.Signs.Enable", false)) return;

		final Player player = event.getPlayer();
		final UUID uuid = player.getUniqueId();

		final PaperUser user = UserUtils.getUser(uuid);

		final String playerName = player.getName();
		final Date time = Calendar.getInstance().getTime();

		final List<String> whitelisted = config.getStringList("Anti_Advertising.Whitelist");

		final Pattern firstPattern = Pattern.compile("[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[o0]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}(\\.|d[o0]t|\\(d[0o]t\\)|-|,|(\\W|\\d|_)*\\s)+[0-9]{1,3}");
		final Pattern secondPattern = Pattern.compile("[a-zA-Z0-9\\-.]+(\\.|d[o0]t|\\(d[o0]t\\)|-|,)+(com|org|net|co|uk|sk|biz|mobi|xxx|io|ts|adv|de|eu|noip|gs|au|pl|cz|ru)");

		for (int line = 0; line < 4; line++) {
			final Component component = event.line(line);

			if (component == null) continue;

			final String message = PlainTextComponentSerializer.plainText().serialize(component);

			if (user.hasState(PlayerState.STAFF_CHAT) || Permissions.BYPASS_ANTI_ADVERTISING.hasPermission(player)) continue;

			for (final String allowed : whitelisted) {
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

			Methods.tellConsole(Messages.ANTI_ADVERTISING_SIGNS_NOTIFY_STAFF.getMessage(console, new HashMap<>() {{
				put("{player}", player.getName());
				put("{message}", message);
			}}), false);
		}

		if (config.getBoolean("Anti_Advertising.Signs.Execute_Command", false)) {
			DispatchUtils.dispatchCommand(player, new ArrayList<>() {{
				addAll(config.getStringList("Anti_Advertising.Signs.Executed_Command"));
				add(config.getString("Anti_Advertising.Signs.Executed_Command", ""));
			}});
		}

		if (config.getBoolean("Anti_Advertising.Signs.Log_Advertisers", false)) {
			try {
				FileWriter fw = new FileWriter(new File(new File(this.dataFolder, "Logs"), "Advertisements.txt"), true);
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