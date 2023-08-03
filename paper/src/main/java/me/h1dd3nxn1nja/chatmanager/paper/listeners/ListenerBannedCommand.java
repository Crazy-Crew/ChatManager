package me.h1dd3nxn1nja.chatmanager.paper.listeners;

import com.ryderbelserion.chatmanager.paper.api.Universal;
import me.h1dd3nxn1nja.chatmanager.paper.Methods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.List;

public class ListenerBannedCommand implements Listener, Universal {

	@EventHandler(ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();

		List<String> cmd = bannedCommands.getStringList("Banned-Commands");

		if (config.getBoolean("Banned_Commands.Enable")) return;

		if (!player.hasPermission("chatmanager.bypass.bannedcommands")) {
			if (!config.getBoolean("Banned_Commands.Increase_Sensitivity")) {
				for (String command : cmd) {
					if (event.getMessage().toLowerCase().equals("/" + command)) {
						event.setCancelled(true);
						Methods.sendMessage(player, messages.getString("Banned_Commands.Message").replace("{command}", command), true);
						notifyStaff(player, command);
						tellConsole(player, command);
						executeCommand(player);
					}
				}
			} else {
				for (String command : cmd) {
					if (event.getMessage().toLowerCase().contains("/" + command)) {
						event.setCancelled(true);
						Methods.sendMessage(player, messages.getString("Banned_Commands.Message").replace("{command}", command), true);
						notifyStaff(player, command);
						tellConsole(player, command);
						executeCommand(player);
					}
				}
			}
		}

		if (!player.hasPermission("chatmanager.bypass.coloncommands")) {
			if (event.getMessage().split(" ")[0].contains(":")) {
				event.setCancelled(true);
				Methods.sendMessage(player, messages.getString("Banned_Commands.Message").replace("{command}", event.getMessage().replace("/", "")), true);
				notifyStaff(player, event.getMessage().replace("/", ""));
				tellConsole(player, event.getMessage().replace("/", ""));
				executeCommand(player);
			}
		}
	}

	public void notifyStaff(Player player, String message) {
		if (!config.getBoolean("Banned_Commands.Notify_Staff")) return;

		for (Player staff : plugin.getServer().getOnlinePlayers()) {
			if (staff.hasPermission("chatmanager.notify.bannedcommands")) {
				Methods.sendMessage(staff, messages.getString("Banned_Commands.Message").replace("{player}", player.getName()).replace("{command}", message), true);
			}
		}
	}

	public void tellConsole(Player player, String message) {
		if (!config.getBoolean("Banned_Commands.Notify_Staff")) return;

		Methods.tellConsole(messages.getString("Banned_Commands.Notify_Staff_Format").replace("{player}", player.getName()).replace("{command}", message), true);
	}

	public void executeCommand(Player player) {
		if (!config.getBoolean("Banned_Commands.Execute_Command")) return;

		if (!config.contains("Banned_Commands.Executed_Command")) return;

		String command = config.getString("Banned_Commands.Executed_Command").replace("{player}", player.getName());
		List<String> commands = config.getStringList("Banned_Commands.Executed_Command");

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