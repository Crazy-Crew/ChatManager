package me.h1dd3nxn1nja.chatmanager.paper.listeners;

import com.ryderbelserion.chatmanager.paper.api.Universal;
import me.h1dd3nxn1nja.chatmanager.paper.Methods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import java.util.List;

public class ListenerSpy implements Listener, Universal {

	@EventHandler(ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		List<String> blacklist = config.getStringList("Command_Spy.Blacklist_Commands");

		Player player = event.getPlayer();
		String message = event.getMessage();

		if (!player.hasPermission("chatmanager.bypass.commandspy")) {
			for (String command : blacklist) {
				if (message.toLowerCase().startsWith(command)) return;
			}

			for (Player staff : plugin.getServer().getOnlinePlayers()) {

				boolean isValid = plugin.api().getCommandSpyData().containsUser(staff.getUniqueId());

				if (!isValid || !staff.hasPermission("chatmanager.commandspy")) return;

				Methods.sendMessage(staff, messages.getString("Command_Spy.Format").replace("{player}", player.getName()).replace("{command}", message), true);
			}
		}
	}
}