package me.h1dd3nxn1nja.chatmanager.paper.commands;

import com.ryderbelserion.chatmanager.paper.files.enums.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class CommandBannedCommands implements CommandExecutor {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (!(sender instanceof Player player)) {
			this.plugin.getMethods().sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("BannedCommands")) {
			if (player.hasPermission("chatmanager.bannedcommands")) {
				if (args.length == 0) {
					this.plugin.getMethods().sendMessage(player, "", true);
					this.plugin.getMethods().sendMessage(player, " &3Banned Commands Help Menu &f(v" + plugin.getDescription().getVersion() + ")", true);
					this.plugin.getMethods().sendMessage(player, "", true);
					this.plugin.getMethods().sendMessage(player, " &6<> &f= Required Arguments", true);
					this.plugin.getMethods().sendMessage(player, "", true);
					this.plugin.getMethods().sendMessage(player, " &f/BannedCommands Help &e- Shows the banned commands help menu.", true);
					this.plugin.getMethods().sendMessage(player, " &f/BannedCommands Add &6<command> &e- Add a command to the banned command list.", true);
					this.plugin.getMethods().sendMessage(player, " &f/BannedCommands Remove &6<command> &e- Remove a command from the banned command list.", true);
					this.plugin.getMethods().sendMessage(player, " &f/BannedCommands List &e- Shows a list of the servers banned commands.", true);
					this.plugin.getMethods().sendMessage(player, "", true);
				}
			} else {
				this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
				return true;
			}

			if (args[0].equalsIgnoreCase("help")) {
				if (player.hasPermission("chatmanager.bannedcommands.help")) {
					if (args.length == 1) {
						this.plugin.getMethods().sendMessage(player, "", true);
						this.plugin.getMethods().sendMessage(player, " &3Banned Commands Help Menu &f(v" + plugin.getDescription().getVersion() + ")", true);
						this.plugin.getMethods().sendMessage(player, "", true);
						this.plugin.getMethods().sendMessage(player, " &6<> &f= Required Arguments", true);
						this.plugin.getMethods().sendMessage(player, "", true);
						this.plugin.getMethods().sendMessage(player, " &f/BannedCommands Help &e- Shows the banned commands help menu.", true);
						this.plugin.getMethods().sendMessage(player, " &f/BannedCommands Add &6<command> &e- Add a command to the banned command list.", true);
						this.plugin.getMethods().sendMessage(player, " &f/BannedCommands Remove &6<command> &e- Remove a command from the banned command list.", true);
						this.plugin.getMethods().sendMessage(player, " &f/BannedCommands List &e- Shows a list of the servers banned commands.", true);
						this.plugin.getMethods().sendMessage(player, "", true);
					}
				} else {
					this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
					return true;
				}
			}

			FileConfiguration bannedCommands = Files.BANNED_COMMANDS.getFile();
			FileConfiguration messages = Files.MESSAGES.getFile();

			if (args[0].equalsIgnoreCase("add")) {
				if (player.hasPermission("chatmanager.bannedcommands.add")) {
					if (args.length == 2) {
						if (!bannedCommands.getStringList("Banned-Commands").contains(args[1])) {
							List<String> list = bannedCommands.getStringList("Banned-Commands");
							list.add(args[1].toLowerCase());
							bannedCommands.set("Banned-Commands", list);
							Files.BANNED_COMMANDS.saveFile();
							Files.BANNED_COMMANDS.reloadFile();
							this.plugin.getMethods().sendMessage(player, messages.getString("Banned_Commands.Command_Added").replace("{command}", args[1]), true);
						} else {
							this.plugin.getMethods().sendMessage(player, messages.getString("Banned_Commands.Command_Exists").replace("{command}", args[1]), true);
						}
					} else {
						this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/Bannedcommands add <command>", true);
					}
				} else {
					this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
				}
			}

			if (args[0].equalsIgnoreCase("remove")) {
				if (player.hasPermission("chatmanager.bannedcommands.remove")) {
					if (args.length == 2) {
						if (bannedCommands.getStringList("Banned-Commands").contains(args[1])) {
							List<String> list = bannedCommands.getStringList("Banned-Commands");
							list.remove(args[1].toLowerCase());
							bannedCommands.set("Banned-Commands", list);
							Files.BANNED_COMMANDS.saveFile();
							Files.BANNED_COMMANDS.reloadFile();
							this.plugin.getMethods().sendMessage(player, messages.getString("Banned_Commands.Command_Removed").replace("{command}", args[1]), true);
						} else {
							this.plugin.getMethods().sendMessage(player, messages.getString("Banned_Commands.Command_Not_Found").replace("{command}", args[1]), true);
						}
					} else {
						this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/Bannedcommands remove <command>", true);
					}
				} else {
					this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
				}
			}

			if (args[0].equalsIgnoreCase("list")) {
				if (player.hasPermission("chatmanager.bannedcommands.list")) {
					if (args.length == 1) {
						String list = bannedCommands.getStringList("Banned-Commands").toString().replace("[", "").replace("]", "");
						this.plugin.getMethods().sendMessage(player, "", true);
						this.plugin.getMethods().sendMessage(player, "&cCommands: &7" + list, true);
						player.sendMessage(" ");
					} else {
						this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/Bannedcommands list", true);
					}
				} else {
					this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
				}
			}
		}

		return true;
	}
}