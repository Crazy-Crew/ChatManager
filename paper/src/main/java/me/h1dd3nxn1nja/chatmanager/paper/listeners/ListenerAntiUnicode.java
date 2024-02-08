package me.h1dd3nxn1nja.chatmanager.paper.listeners;

import com.ryderbelserion.chatmanager.paper.files.enums.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.enums.Permissions;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("deprecation")
public class ListenerAntiUnicode implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		FileConfiguration config = Files.CONFIG.getFile();
		FileConfiguration messages = Files.MESSAGES.getFile();

		Player player = event.getPlayer();
		String message = event.getMessage();

		List<String> whitelisted = config.getStringList("Anti_Unicode.Whitelist");

		Pattern pattern = Pattern.compile("^[A-Za-z0-9-~!@#$%^&*()<>_+=-{}|';:.,\\[\"\"]|';:.,/?><_.]+$");
		Matcher matcher = pattern.matcher(event.getMessage().toLowerCase().replaceAll("\\s+", ""));

		if (!config.getBoolean("Anti_Unicode.Enable") || plugin.api().getStaffChatData().containsUser(player.getUniqueId())) return;

		if (player.hasPermission(Permissions.BYPASS_ANTI_UNICODE.getNode())) return;

		for (String allowed : whitelisted) {
			if (event.getMessage().contains(allowed)) return;
		}

		if (matcher.find()) return;

		event.setCancelled(true);
		player.sendMessage(this.plugin.getMethods().color(player.getUniqueId(), messages.getString("Anti_Unicode.Message").replace("{Prefix}", messages.getString("Message.Prefix"))));

		if (config.getBoolean("Anti_Unicode.Notify_Staff")) {
			for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
				if (staff.hasPermission(Permissions.NOTIFY_ANTI_UNICODE.getNode())) {
					this.plugin.getMethods().sendMessage(staff, messages.getString("Anti_Unicode.Notify_Staff_Format").replace("{player}", player.getName()).replace("{message}", message), true);
				}
			}

			this.plugin.getMethods().tellConsole(messages.getString("Anti_Unicode.Notify_Staff_Format").replace("{player}", player.getName()).replace("{message}", message), true);
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
				}.runTask(this.plugin);
			}
		}
	}
}