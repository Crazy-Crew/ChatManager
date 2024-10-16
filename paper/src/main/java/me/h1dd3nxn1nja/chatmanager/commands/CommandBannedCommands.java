package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
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
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);

			return true;
		}

		if (cmd.getName().equalsIgnoreCase("BannedCommands")) {
			if (player.hasPermission(Permissions.COMMAND_BANNEDCOMMANDS_HELP.getNode())) {
				if (args.length == 0) {
					Methods.sendMessage(player, "", true);
					Methods.sendMessage(player, " &3Banned Commands Help Menu &f(v" + plugin.getDescription().getVersion() + ")", true);
					Methods.sendMessage(player, "", true);
					Methods.sendMessage(player, " &6<> &f= Required Arguments", true);
					Methods.sendMessage(player, "", true);
					Methods.sendMessage(player, " &f/bannedCommands help &e- Shows the banned commands help menu.", true);
					Methods.sendMessage(player, " &f/bannedCommands add &6<command> &e- Add a command to the banned command list.", true);
					Methods.sendMessage(player, " &f/bannedCommands remove &6<command> &e- Remove a command from the banned command list.", true);
					Methods.sendMessage(player, " &f/bannedCommands list &e- Shows a list of the servers banned commands.", true);
					Methods.sendMessage(player, "", true);
				}
			} else {
				Messages.NO_PERMISSION.sendMessage(player);

				return true;
			}

			if (args[0].equalsIgnoreCase("help")) {
				if (player.hasPermission(Permissions.COMMAND_BANNEDCOMMANDS_HELP.getNode())) {
					if (args.length == 1) {
						Methods.sendMessage(player, "", true);
						Methods.sendMessage(player, " &3Banned Commands Help Menu &f(v" + plugin.getDescription().getVersion() + ")", true);
						Methods.sendMessage(player, "", true);
						Methods.sendMessage(player, " &6<> &f= Required Arguments", true);
						Methods.sendMessage(player, "", true);
						Methods.sendMessage(player, " &f/bannedCommands help &e- Shows the banned commands help menu.", true);
						Methods.sendMessage(player, " &f/bannedCommands add &6<command> &e- Add a command to the banned command list.", true);
						Methods.sendMessage(player, " &f/bannedCommands remove &6<command> &e- Remove a command from the banned command list.", true);
						Methods.sendMessage(player, " &f/bannedCommands list &e- Shows a list of the servers banned commands.", true);
						Methods.sendMessage(player, "", true);
					}
				} else {
					Messages.NO_PERMISSION.sendMessage(player);

					return true;
				}
			}

			FileConfiguration bannedCommands = Files.BANNED_COMMANDS.getConfiguration();

			if (args[0].equalsIgnoreCase("add")) {
				if (player.hasPermission(Permissions.COMMAND_BANNEDCOMMANDS_ADD.getNode())) {
					if (args.length == 2) {
						if (!bannedCommands.getStringList("Banned-Commands").contains(args[1])) {
							List<String> list = bannedCommands.getStringList("Banned-Commands");
							list.add(args[1].toLowerCase());

							bannedCommands.set("Banned-Commands", list);

							Files.BANNED_COMMANDS.save();

							Messages.BANNED_COMMANDS_ADDED.sendMessage(player, "{command}", args[1]);
						} else {
							Messages.BANNED_COMMANDS_EXISTS.sendMessage(player, "{command}", args[1]);
						}
					} else {
						Methods.sendMessage(player, "&cCommand Usage: &7/bannedcommands add <command>", true);
					}
				} else {
					Messages.NO_PERMISSION.sendMessage(player);
				}
			}

			if (args[0].equalsIgnoreCase("remove")) {
				if (player.hasPermission(Permissions.COMMAND_BANNEDCOMMANDS_REMOVE.getNode())) {
					if (args.length == 2) {
						if (bannedCommands.getStringList("Banned-Commands").contains(args[1])) {
							List<String> list = bannedCommands.getStringList("Banned-Commands");
							list.remove(args[1].toLowerCase());

							bannedCommands.set("Banned-Commands", list);

							Files.BANNED_COMMANDS.save();

							Messages.BANNED_COMMANDS_REMOVED.sendMessage(player, "{command}", args[1]);
						} else {
							Messages.BANNED_COMMANDS_NOT_FOUND.sendMessage(player, "{command}", args[1]);
						}
					} else {
						Methods.sendMessage(player, "&cCommand Usage: &7/bannedcommands remove <command>", true);
					}
				} else {
					Messages.NO_PERMISSION.sendMessage(player);
				}
			}

			if (args[0].equalsIgnoreCase("list")) {
				if (player.hasPermission(Permissions.COMMAND_BANNEDCOMMANDS_LIST.getNode())) {
					if (args.length == 1) {
						String list = bannedCommands.getStringList("Banned-Commands").toString().replace("[", "").replace("]", "");

						Methods.sendMessage(player, "", true);
						Methods.sendMessage(player, "&cCommands: &7" + list, true);

						player.sendMessage(" ");
					} else {
						Methods.sendMessage(player, "&cCommand Usage: &7/bannedcommands list", true);
					}
				} else {
					Messages.NO_PERMISSION.sendMessage(player);
				}
			}
		}

		return true;
	}
}