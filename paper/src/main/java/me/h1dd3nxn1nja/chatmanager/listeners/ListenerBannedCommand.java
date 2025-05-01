package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.utils.DispatchUtils;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListenerBannedCommand implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	private final Server server = this.plugin.getServer();

	private final ConsoleCommandSender console = this.server.getConsoleSender();

	@EventHandler(ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final Player player = event.getPlayer();

		final List<String> cmd = Files.BANNED_COMMANDS.getConfiguration().getStringList("Banned-Commands");

		final String message = event.getMessage();

		if (!config.getBoolean("Banned_Commands.Enable", false)) return;

		if (!player.hasPermission(Permissions.BYPASS_BANNED_COMMANDS.getNode())) {
			if (!config.getBoolean("Banned_Commands.Increase_Sensitivity", false)) {
				for (final String command : cmd) {
					if (message.toLowerCase().equals("/" + command)) {
						event.setCancelled(true);

						Messages.BANNED_COMMANDS_MESSAGE.sendMessage(player, "{command}", command);

						notifyStaff(player, command);
						tellConsole(player, command);

						executeCommand(player);
					}
				}
			} else {
				for (final String command : cmd) {
					if (message.toLowerCase().contains("/" + command)) {
						event.setCancelled(true);

						notifyStaff(player, command);
						tellConsole(player, command);

						executeCommand(player);
					}
				}
			}
		}

		if (!player.hasPermission(Permissions.BYPASS_COLON_COMMANDS.getNode())) {
			if (message.split(" ")[0].contains(":")) {
				event.setCancelled(true);

				Messages.BANNED_COMMANDS_MESSAGE.sendMessage(player, "{command}", message.replaceAll("/", ""));

				notifyStaff(player, message.replace("/", ""));
				tellConsole(player, message.replace("/", ""));

				executeCommand(player);
			}
		}
	}

	public void notifyStaff(final Player player, final String message) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Banned_Commands.Notify_Staff", false)) return;

		for (final Player staff : this.server.getOnlinePlayers()) {
			if (staff.hasPermission(Permissions.NOTIFY_BANNED_COMMANDS.getNode())) {
				Messages.BANNED_COMMANDS_MESSAGE.sendMessage(staff, new HashMap<>() {{
					put("{player}", player.getName());
					put("{command}", message);
				}});
			}
		}
	}

	public void tellConsole(final Player player, final String message) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Banned_Commands.Notify_Staff", false)) return;

		Methods.tellConsole(Messages.BANNED_COMMANDS_MESSAGE.getMessage(this.console, new HashMap<>() {{
			put("{player}", player.getName());
			put("{command}", message);
		}}), false);
	}

	public void executeCommand(final Player player) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Banned_Commands.Execute_Command", false)) return;

		DispatchUtils.dispatchCommand(player, new ArrayList<>() {{
			addAll(config.getStringList("Banned_Commands.Executed_Command"));
			add(config.getString("Banned_Commands.Executed_Command", ""));
		}});
	}
}