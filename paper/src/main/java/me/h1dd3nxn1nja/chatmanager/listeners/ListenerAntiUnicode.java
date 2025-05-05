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
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListenerAntiUnicode implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();


	private final Server server = this.plugin.getServer();

	private final ConsoleCommandSender console = this.server.getConsoleSender();

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncChatEvent event) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final Player player = event.getPlayer();
		final String playerName = player.getName();
		final String message = event.signedMessage().message();

		final PaperUser user = UserUtils.getUser(player.getUniqueId());

		if (!config.getBoolean("Anti_Unicode.Enable", false) || user.hasState(PlayerState.STAFF_CHAT)) return;

		if (Permissions.BYPASS_ANTI_UNICODE.hasPermission(player)) return;

		final List<String> whitelisted = config.getStringList("Anti_Unicode.Whitelist");

		for (final String allowed : whitelisted) {
			if (message.contains(allowed)) return;
		}

		final Pattern pattern = Pattern.compile("^[A-Za-z0-9-~!@#$%^&*()<>_+=-{}|';:.,\\[\"\"]|';:.,/?><_.]+$");
		final Matcher matcher = pattern.matcher(message.toLowerCase().replaceAll("\\s+", ""));

		if (matcher.find()) return;

		event.setCancelled(true);

		Messages.ANTI_UNICODE_MESSAGE.sendMessage(player);

		if (config.getBoolean("Anti_Unicode.Notify_Staff", false)) {
			for (final Player staff : this.server.getOnlinePlayers()) {
				if (staff.hasPermission(Permissions.NOTIFY_ANTI_UNICODE.getNode())) {
					Messages.ANTI_UNICODE_NOTIFY_STAFF_FORMAT.sendMessage(staff, new HashMap<>() {{
						put("{player}", playerName);
						put("{message}", message);
					}});
				}
			}

			Methods.tellConsole(Messages.ANTI_UNICODE_NOTIFY_STAFF_FORMAT.getMessage(this.console, new HashMap<>() {{
				put("{player}", playerName);
				put("{message}", message);
			}}), false);
		}

		if (config.getBoolean("Anti_Unicode.Execute_Command", false)) {
			DispatchUtils.dispatchCommand(player, new ArrayList<>() {{
				addAll(config.getStringList("Anti_Unicode.Executed_Command"));
				add(config.getString("Anti_Unicode.Executed_Command", ""));
			}});
		}
	}
}