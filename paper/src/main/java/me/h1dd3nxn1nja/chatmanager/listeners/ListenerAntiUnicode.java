package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.fusion.paper.api.enums.Scheduler;
import com.ryderbelserion.fusion.paper.api.scheduler.FoliaScheduler;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListenerAntiUnicode extends Global implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncChatEvent event) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final Player player = event.getPlayer();
		final String message = event.signedMessage().message();

		final List<String> whitelisted = config.getStringList("Anti_Unicode.Whitelist");

		final Pattern pattern = Pattern.compile("^[A-Za-z0-9-~!@#$%^&*()<>_+=-{}|';:.,\\[\"\"]|';:.,/?><_.]+$");
		final Matcher matcher = pattern.matcher(message.toLowerCase().replaceAll("\\s+", ""));

		if (!config.getBoolean("Anti_Unicode.Enable", false) || this.staffChatData.containsUser(player.getUniqueId())) return;

		if (player.hasPermission(Permissions.BYPASS_ANTI_UNICODE.getNode())) return;

		for (final String allowed : whitelisted) {
			if (message.contains(allowed)) return;
		}

		if (matcher.find()) return;

		event.setCancelled(true);

		Messages.ANTI_UNICODE_MESSAGE.sendMessage(player);

		if (config.getBoolean("Anti_Unicode.Notify_Staff", false)) {
			for (final Player staff : this.server.getOnlinePlayers()) {
				if (staff.hasPermission(Permissions.NOTIFY_ANTI_UNICODE.getNode())) {
					Messages.ANTI_UNICODE_NOTIFY_STAFF_FORMAT.sendMessage(staff, new HashMap<>() {{
						put("{player}", player.getName());
						put("{message}", message);
					}});
				}
			}

			Methods.tellConsole(Messages.ANTI_UNICODE_NOTIFY_STAFF_FORMAT.getMessage(this.sender, new HashMap<>() {{
				put("{player}", player.getName());
				put("{message}", message);
			}}), false);
		}

		if (config.getBoolean("Anti_Unicode.Execute_Command", false)) {
			if (config.contains("Anti_Unicode.Executed_Command")) {
				final String command = config.getString("Anti_Unicode.Executed_Command").replace("{player}", player.getName());
				final List<String> commands = config.getStringList("Anti_Unicode.Executed_Command");

				new FoliaScheduler(Scheduler.global_scheduler) {
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
	}
}