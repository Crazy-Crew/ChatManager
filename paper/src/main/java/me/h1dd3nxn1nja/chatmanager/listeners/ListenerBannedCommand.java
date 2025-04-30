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
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.List;

public class ListenerBannedCommand implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@EventHandler(ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		FileConfiguration config = Files.CONFIG.getConfiguration();

		Player player = event.getPlayer();

		List<String> cmd = Files.BANNED_COMMANDS.getConfiguration().getStringList("Banned-Commands");

		if (!config.getBoolean("Banned_Commands.Enable", false)) return;

		if (!player.hasPermission(Permissions.BYPASS_BANNED_COMMANDS.getNode())) {
			if (!config.getBoolean("Banned_Commands.Increase_Sensitivity", false)) {
				for (String command : cmd) {
					if (event.getMessage().toLowerCase().equals("/" + command)) {
						event.setCancelled(true);

						Messages.BANNED_COMMANDS_MESSAGE.sendMessage(player, "{command}", command);

						notifyStaff(player, command);
						tellConsole(player, command);

						executeCommand(player);
					}
				}
			} else {
				for (String command : cmd) {
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

	public void notifyStaff(Player player, String message) {
		FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Banned_Commands.Notify_Staff", false)) return;

		for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
			if (staff.hasPermission(Permissions.NOTIFY_BANNED_COMMANDS.getNode())) {
				Messages.BANNED_COMMANDS_MESSAGE.sendMessage(staff, new HashMap<>() {{
					put("{player}", player.getName());
					put("{command}", message);
				}});
			}
		}
	}

	public void tellConsole(Player player, String message) {
		FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Banned_Commands.Notify_Staff", false)) return;

		Methods.tellConsole(Messages.BANNED_COMMANDS_MESSAGE.getMessage(this.plugin.getServer().getConsoleSender(), new HashMap<>() {{
			put("{player}", player.getName());
			put("{command}", message);
		}}), false);
	}

	public void executeCommand(Player player) {
		FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Banned_Commands.Execute_Command", false)) return;

		if (!config.contains("Banned_Commands.Executed_Command")) return;

		String command = config.getString("Banned_Commands.Executed_Command", "").replace("{player}", player.getName());
		List<String> commands = config.getStringList("Banned_Commands.Executed_Command");

		if (command.isEmpty() || commands.isEmpty()) return;

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