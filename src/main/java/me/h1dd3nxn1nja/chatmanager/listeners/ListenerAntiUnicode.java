package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.vital.paper.util.scheduler.FoliaRunnable;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("deprecation")
public class ListenerAntiUnicode implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		FileConfiguration config = Files.CONFIG.getConfiguration();

		Player player = event.getPlayer();
		String message = event.getMessage();

		List<String> whitelisted = config.getStringList("Anti_Unicode.Whitelist");

		Pattern pattern = Pattern.compile("^[A-Za-z0-9-~!@#$%^&*()<>_+=-{}|';:.,\\[\"\"]|';:.,/?><_.]+$");
		Matcher matcher = pattern.matcher(event.getMessage().toLowerCase().replaceAll("\\s+", ""));

		if (!config.getBoolean("Anti_Unicode.Enable", false) || this.plugin.api().getStaffChatData().containsUser(player.getUniqueId())) return;

		if (player.hasPermission(Permissions.BYPASS_ANTI_UNICODE.getNode())) return;

		for (String allowed : whitelisted) {
			if (event.getMessage().contains(allowed)) return;
		}

		if (matcher.find()) return;

		event.setCancelled(true);

		Messages.ANTI_UNICODE_MESSAGE.sendMessage(player);

		if (config.getBoolean("Anti_Unicode.Notify_Staff", false)) {
			for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
				if (staff.hasPermission(Permissions.NOTIFY_ANTI_UNICODE.getNode())) {
					Messages.ANTI_UNICODE_NOTIFY_STAFF_FORMAT.sendMessage(staff, new HashMap<>() {{
						put("{player}", player.getName());
						put("{message}", message);
					}});
				}
			}

			Methods.tellConsole(Messages.ANTI_UNICODE_NOTIFY_STAFF_FORMAT.getMessage(this.plugin.getServer().getConsoleSender(), new HashMap<>() {{
				put("{player}", player.getName());
				put("{message}", message);
			}}), false);
		}

		if (config.getBoolean("Anti_Unicode.Execute_Command", false)) {
			if (config.contains("Anti_Unicode.Executed_Command")) {
				String command = config.getString("Anti_Unicode.Executed_Command").replace("{player}", player.getName());
				List<String> commands = config.getStringList("Anti_Unicode.Executed_Command");

				new FoliaRunnable(this.plugin.getServer().getGlobalRegionScheduler()) {
					@Override
					public void run() {
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);

						for (String cmd : commands) {
							plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), cmd.replace("{player}", player.getName()));
						}
					}
				}.run(this.plugin);
			}
		}
	}
}