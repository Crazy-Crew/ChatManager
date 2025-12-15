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
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import java.util.HashMap;
import java.util.List;

public class ListenerBannedCommand extends Global implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final Player player = event.getPlayer();

		final List<String> cmd = Files.BANNED_COMMANDS.getConfiguration().getStringList("Banned-Commands");

		if (!config.getBoolean("Banned_Commands.Enable", false)) return;

		if (!player.hasPermission(Permissions.BYPASS_BANNED_COMMANDS.getNode())) {
			if (!config.getBoolean("Banned_Commands.Increase_Sensitivity", false)) {
				for (final String command : cmd) {
					if (event.getMessage().toLowerCase().equals("/" + command)) {
						event.setCancelled(true);

						Messages.BANNED_COMMANDS_MESSAGE.sendMessage(player, "{command}", command);

						notifyStaff(player, command);
						tellConsole(player, command);

						executeCommand(player);
					}
				}
			} else {
				for (final String command : cmd) {
					if (event.getMessage().toLowerCase().contains("/" + command)) {
						event.setCancelled(true);

						notifyStaff(player, command);
						tellConsole(player, command);

						executeCommand(player);
					}
				}
			}
		}

		if (!player.hasPermission(Permissions.BYPASS_COLON_COMMANDS.getNode())) {
			if (event.getMessage().split(" ")[0].contains(":")) {
				event.setCancelled(true);

				Messages.BANNED_COMMANDS_MESSAGE.sendMessage(player, "{command}", event.getMessage().replaceAll("/", ""));

				notifyStaff(player, event.getMessage().replace("/", ""));
				tellConsole(player, event.getMessage().replace("/", ""));

				executeCommand(player);
			}
		}
	}

	public void notifyStaff(final Player player, final String message) {
		FileConfiguration config = Files.CONFIG.getConfiguration();

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
		FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Banned_Commands.Notify_Staff", false)) return;

		Methods.tellConsole(Messages.BANNED_COMMANDS_MESSAGE.getMessage(this.sender, new HashMap<>() {{
			put("{player}", player.getName());
			put("{command}", message);
		}}), false);
	}

	public void executeCommand(final Player player) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Banned_Commands.Execute_Command", false)) return;

		if (!config.contains("Banned_Commands.Executed_Command")) return;

		final String command = config.getString("Banned_Commands.Executed_Command", "").replace("{player}", player.getName());
		final List<String> commands = config.getStringList("Banned_Commands.Executed_Command");

		new FoliaScheduler(plugin, Scheduler.global_scheduler) {
			@Override
			public void run() {
				if (!command.isEmpty()) server.dispatchCommand(sender, command);

				for (final String cmd : commands) {
					server.dispatchCommand(sender, cmd.replace("{player}", player.getName()));
				}
			}
		}.runNow();
	}
}