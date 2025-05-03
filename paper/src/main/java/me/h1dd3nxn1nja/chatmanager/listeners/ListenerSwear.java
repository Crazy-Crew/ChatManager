package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.chat.StaffChatData;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.utils.DispatchUtils;
import com.ryderbelserion.fusion.core.utils.AdvUtils;
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
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ListenerSwear implements Listener {

	//TODO() Add a way so that chat manager highlights the swear word in the notify feature.

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	private final File dataFolder = this.plugin.getDataFolder();

	private final Server server = this.plugin.getServer();

	private final ConsoleCommandSender console = this.server.getConsoleSender();

	private final ApiLoader api = this.plugin.api();

	private final StaffChatData data = this.api.getStaffChatData();

	@EventHandler(ignoreCancelled = true)
	public void onSwear(AsyncChatEvent event) {
		final Player player = event.getPlayer();
		final String message = event.signedMessage().message();

		final FileConfiguration bannedWords = Files.BANNED_WORDS.getConfiguration();
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (this.data.containsUser(player.getUniqueId()) || !config.getBoolean("Anti_Swear.Chat.Enable", false)) return;

		final List<String> words = bannedWords.getStringList("Banned-Words");

		if (words.isEmpty() || player.hasPermission(Permissions.BYPASS_ANTI_SWEAR.getNode())) return;

		final List<String> whitelisted = bannedWords.getStringList("Whitelisted_Words");
		final String curseMessage = message.toLowerCase();

		final Date time = Calendar.getInstance().getTime();

		if (config.getBoolean("Anti_Swear.Chat.Increase_Sensitivity", false)) {
			final String sensitiveMessage = message.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").replaceAll("\\s+", "");

			validateChat(event, player, message, config, words, whitelisted, time, sensitiveMessage);

			return;
		}

		validateChat(event, player, message, config, words, whitelisted, time, curseMessage);
	}

	public void validateChat(@NotNull final AsyncChatEvent event, @NotNull final Player player, @NotNull final String message, @NotNull final FileConfiguration config, @NotNull final List<String> words, @NotNull final List<String> whitelisted, @NotNull final Date time, @NotNull final String sensitiveMessage) {
		for (final String word : words) {
			for (final String allowed : whitelisted) {
				if (message.contains(allowed.toLowerCase())) return;
			}

			if (logChatSwearing(player, message, time, sensitiveMessage, word)) {
				Messages.ANTI_SWEAR_CHAT_MESSAGE.sendMessage(player);

				if (config.getBoolean("Anti_Swear.Chat.Block_Message", false)) event.setCancelled(true);

				notifyStaff(player, config.getBoolean("Anti_Swear.Chat.Notify_Staff", false), message, Messages.ANTI_SWEAR_CHAT_NOTIFY_STAFF_FORMAT, Permissions.NOTIFY_ANTI_SWEAR);

				return;
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onSwearCommand(PlayerCommandPreprocessEvent event) {
		final Player player = event.getPlayer();
		final String message = event.getMessage();
		final Date time = Calendar.getInstance().getTime();

		final FileConfiguration bannedWords = Files.BANNED_WORDS.getConfiguration();
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final List<String> whitelisted = bannedWords.getStringList("Whitelisted_Words");
		final List<String> whitelistedCommands = config.getStringList("Anti_Swear.Commands.Whitelisted_Commands");
		final List<String> blockedWordsList = bannedWords.getStringList("Banned-Words");
		final String sensitiveMessage = message.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").replaceAll("\\s+", "");
		final String curseMessage = message.toLowerCase();

		if (!config.getBoolean("Anti_Swear.Commands.Enable", false) || Permissions.BYPASS_ANTI_SWEAR.hasPermission(player)) return;

		if (config.getBoolean("Anti_Swear.Commands.Increase_Sensitivity", false)) {
			for (final String word : blockedWordsList) {
				for (final String allowed : whitelisted) {
					if (message.contains(allowed.toLowerCase())) return;
				}

				if (sensitiveMessage.contains(word)) {
					Messages.ANTI_SWEAR_COMMAND_MESSAGE.sendMessage(player);

					if (config.getBoolean("Anti_Swear.Commands.Block_Command", false)) event.setCancelled(true);

					notifyStaff(player, config.getBoolean("Anti_Swear.Commands.Notify_Staff", false), message, Messages.ANTI_SWEAR_COMMAND_NOTIFY_STAFF_FORMAT, Permissions.NOTIFY_ANTI_SWEAR);

					logCommandSwearing(config, player, message, time);

					for (final String command : whitelistedCommands) {
						if (message.toLowerCase().startsWith(command)) return;
					}
				}
			}

			return;
		}

		for (final String word : blockedWordsList) {
			if (curseMessage.contains(word)) {
				Messages.ANTI_SWEAR_COMMAND_MESSAGE.sendMessage(player);

				if (config.getBoolean("Anti_Swear.Commands.Block_Command", false)) event.setCancelled(true);

				notifyStaff(player, config.getBoolean("Anti_Swear.Commands.Notify_Staff", false), message, Messages.ANTI_SWEAR_CHAT_NOTIFY_STAFF_FORMAT, Permissions.NOTIFY_ANTI_SWEAR);

				logCommandSwearing(config, player, message, time);

				for (final String command : whitelistedCommands) {
					if (message.toLowerCase().startsWith(command)) return;
				}
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
		final List<String> words = bannedWords.getStringList("Banned-Words");

		if (words.isEmpty()) return;

		if (!config.getBoolean("Anti_Swear.Signs.Enable", false) || player.hasPermission(Permissions.BYPASS_ANTI_SWEAR.getNode())) return;

		if (config.getBoolean("Anti_Swear.Signs.Increase_Sensitivity", false)) {
			for (int line = 0; line < 4; line++) {
				final Component component = event.line(line);

				if (component == null || component.equals(Component.empty())) continue;

				final String message = PlainTextComponentSerializer.plainText().serialize(component);

				if (message.isEmpty()) return;

				final String curseMessage = message.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").replaceAll("\\s+", "");

				for (final String word : words) {
					for (final String allowed : whitelisted) {
						if (message.contains(allowed.toLowerCase())) return;
					}

					if (curseMessage.contains(word)) {
						Messages.ANTI_SWEAR_SIGNS_MESSAGE.sendMessage(player);

						if (config.getBoolean("Anti_Swear.Signs.Block_Sign", false)) event.setCancelled(true);

						notifyStaff(player, config.getBoolean("Anti_Swear.Signs.Notify_Staff", false), message, Messages.ANTI_SWEAR_SIGNS_NOTIFY_STAFF_FORMAT, Permissions.NOTIFY_ANTI_SWEAR);

						logSignSwearing(config, player, time, line, message);
					}
				}
			}

			return;
		}

		for (int line = 0; line < 4; line++) {
			final Component component = event.line(line);

			if (component == null || component.equals(Component.empty())) continue;

			final String message = PlainTextComponentSerializer.plainText().serialize(component);

			if (message.isEmpty()) return;

			for (final String curseMessages : message.toLowerCase().split(" ")) {
				if (Permissions.BYPASS_ANTI_SWEAR.hasPermission(player) || !words.contains(curseMessages)) continue;

				Messages.ANTI_SWEAR_SIGNS_MESSAGE.sendMessage(player);

				if (config.getBoolean("Anti_Swear.Signs.Block_Sign", false)) event.setCancelled(true);

				notifyStaff(player, config.getBoolean("Anti_Swear.Signs.Notify_Staff", false), message, Messages.ANTI_SWEAR_SIGNS_NOTIFY_STAFF_FORMAT, Permissions.NOTIFY_ANTI_SWEAR);

				logSignSwearing(config, player, time, line, message);
			}
		}
	}

	private void notifyStaff(@NotNull final Player target, final boolean notify, @NotNull final String value, @NotNull final Messages message, @NotNull final Permissions permission) {
		if (!notify) return;

		for (final Player player : this.server.getOnlinePlayers()) {
			if (!permission.hasPermission(player)) continue;

			message.sendMessage(player, new HashMap<>() {{
				put("{player}", target.getName());
				put("{message}", value);
			}});
		}

		Methods.tellConsole(message.getMessage(this.console, new HashMap<>() {{
			put("{player}", target.getName());
			put("{message}", value);
		}}), false);
	}

	private boolean logChatSwearing(@NotNull final Player player, @NotNull final String message, @NotNull final Date time, @NotNull final String value, @NotNull final String word) {
		if (!value.contains(word)) return false;

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
			DispatchUtils.dispatchCommand(player, new ArrayList<>() {{
				addAll(config.getStringList("Anti_Swear.Chat.Executed_Command"));
				add(config.getString("Anti_Swear.Chat.Executed_Command", ""));
			}});
		}

		return true;
	}

	private void logCommandSwearing(@NotNull final FileConfiguration config, @NotNull final Player player, @NotNull final String message, @NotNull final Date time) {
		if (config.getBoolean("Anti_Swear.Commands.Log_Swearing", false)) {
			try {
				FileWriter fw = new FileWriter(new File(new File(this.dataFolder, "Logs"), "Swears.txt"), true);
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
			DispatchUtils.dispatchCommand(player, new ArrayList<>() {{
				addAll(config.getStringList("Anti_Swear.Commands.Executed_Command"));
				add(config.getString("Anti_Swear.Commands.Executed_Command", ""));
			}});
		}
	}

	private void logSignSwearing(@NotNull final FileConfiguration config, @NotNull final Player player, @NotNull final Date time, final int line, @NotNull final String message) {
		if (config.getBoolean("Anti_Swear.Signs.Log_Swearing", false)) {
			try {
				FileWriter fw = new FileWriter(new File(new File(this.dataFolder, "Logs"), "Swears.txt"), true);
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
			DispatchUtils.dispatchCommand(player, new ArrayList<>() {{
				addAll(config.getStringList("Anti_Swear.Signs.Executed_Command"));
				add(config.getString("Anti_Swear.Signs.Executed_Command", ""));
			}});
		}
	}
}