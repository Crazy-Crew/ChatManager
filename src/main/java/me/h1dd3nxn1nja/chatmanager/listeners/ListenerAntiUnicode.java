package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.api.Universal;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListenerAntiUnicode implements Listener, Universal {

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();

		List<String> whitelisted = config.getStringList("Anti_Unicode.Whitelist");

		Pattern pattern = Pattern.compile("^[A-Za-z0-9-~!@#$%^&*()<>_+=-{}|';:.,\\[\"\"]|';:.,/?><_.]+$");
		Matcher matcher = pattern.matcher(event.getMessage().toLowerCase().replaceAll("\\s+", ""));

		if (!config.getBoolean("Anti_Unicode.Enable") || plugin.getCrazyManager().api().getStaffChatData().containsUser(player.getUniqueId())) return;

		if (player.hasPermission("chatmanager.bypass.antiunicode")) return;

		for (String allowed : whitelisted) {
			if (event.getMessage().contains(allowed)) return;
		}

		if (matcher.find()) return;

		event.setCancelled(true);
		player.sendMessage(Methods.color(player.getUniqueId(), messages.getString("Anti_Unicode.Message").replace("{Prefix}", messages.getString("Message.Prefix"))));

		if (config.getBoolean("Anti_Unicode.Notify_Staff")) {
			for (Player staff : plugin.getServer().getOnlinePlayers()) {
				if (staff.hasPermission("chatmanager.notify.antiunicode")) {
					Methods.sendMessage(staff, messages.getString("Anti_Unicode.Notify_Staff_Format").replace("{player}", player.getName()).replace("{message}", message), true);
				}
			}

			Methods.tellConsole(messages.getString("Anti_Unicode.Notify_Staff_Format").replace("{player}", player.getName()).replace("{message}", message), true);
		}

		if (config.getBoolean("Anti_Unicode.Execute_Command")) {
			if (config.contains("Anti_Unicode.Executed_Command")) {
				String command = config.getString("Anti_Unicode.Executed_Command").replace("{player}", player.getName());
				List<String> commands = config.getStringList("Anti_Unicode.Executed_Command");

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
	}
}