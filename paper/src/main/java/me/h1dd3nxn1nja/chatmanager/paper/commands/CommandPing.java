package me.h1dd3nxn1nja.chatmanager.paper.commands;

import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.enums.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.text.DecimalFormat;
import com.ryderbelserion.chatmanager.paper.files.enums.Files;

public class CommandPing implements CommandExecutor {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		DecimalFormat df = new DecimalFormat("#,###");

		FileConfiguration messages = Files.MESSAGES.getFile();
		
		if (!(sender instanceof Player player)) {
			this.plugin.getMethods().sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("ping")) {
			if (!player.hasPermission(Permissions.COMMAND_PING.getNode())) {
				this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
				return true;
			}

			if (args.length == 0) {
				this.plugin.getMethods().sendMessage(player, messages.getString("Ping.Players_Ping").replace("{ping}", df.format(player.getPing())), true);
				return true;
			}

			if (!player.hasPermission(Permissions.COMMAND_PING_OTHERS.getNode())) {
				this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
				return true;
			}

			if (args.length >= 2) {
				this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/ping [player]", true);
				return true;
			}

			Player target = this.plugin.getServer().getPlayer(args[0]);

			if (target == null || !target.isOnline()) {
				this.plugin.getMethods().sendMessage(player, messages.getString("Message.Player_Not_Found").replace("{target}", args[0]), true);
				return true;
			}

			this.plugin.getMethods().sendMessage(player, messages.getString("Ping.Targets_Ping").replace("{target}", target.getName()).replace("{ping}", df.format(target.getPing())), true);
			return true;
		}

		return true;
	}
}