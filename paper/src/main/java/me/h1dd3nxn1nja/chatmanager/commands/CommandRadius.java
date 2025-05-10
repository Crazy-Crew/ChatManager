package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandRadius extends Global implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (!(sender instanceof Player player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);

			return true;
		}

		if (cmd.getName().equalsIgnoreCase("chatradius")) {
			if (!Files.CONFIG.getConfiguration().getBoolean("Chat_Radius.Enable", false)) {
				Methods.sendMessage(sender, "&cError: Chat Radius is currently disabled. Please enable it in the config.yml", true);

				return true;
			}

			if (player.hasPermission(Permissions.CHAT_RADIUS_HELP.getNode())) {
				if (args.length == 0) {
					Methods.sendMessage(player, "", true);
					Methods.sendMessage(player, (" &3Chat Radius Help Menu &f(v" + plugin.getDescription().getVersion() + ")"), true);
					Methods.sendMessage(player, "", true);
					Methods.sendMessage(player, (" &f/chatradius help &e- Shows a list of commands for chat radius."), true);
					Methods.sendMessage(player, (" &f/chatradius local &e- Enables local chat."), true);
					Methods.sendMessage(player, (" &f/chatradius global &e- Enables global chat."), true);
					Methods.sendMessage(player, (" &f/chatradius world &e- Enables world chat."), true);
					Methods.sendMessage(player, "", true);

					return true;
				}
			} else {
				Messages.NO_PERMISSION.sendMessage(player);

				return true;
			}

			if (args[0].equalsIgnoreCase("help")) {
				if (player.hasPermission(Permissions.CHAT_RADIUS_HELP.getNode())) {
					if (args.length == 1) {
						Methods.sendMessage(player, "", true);
						Methods.sendMessage(player, (" &3Chat Radius Help Menu &f(v" + plugin.getDescription().getVersion() + ")"), true);
						Methods.sendMessage(player, "", true);
						Methods.sendMessage(player, (" &f/chatradius help &e- Shows a list of commands for chat radius."), true);
						Methods.sendMessage(player, (" &f/chatradius local &e- Enables local chat."), true);
						Methods.sendMessage(player, (" &f/chatradius global &e- Enables global chat."), true);
						Methods.sendMessage(player, (" &f/chatradius world &e- Enables world chat."), true);
						Methods.sendMessage(player, (" &f/chatradius spy &e- Enables chat radius spy. Players will be able to see all messages sent."), true);
						Methods.sendMessage(player, "", true);
					}
				} else {
					Messages.NO_PERMISSION.sendMessage(player);

					return true;
				}
			}

			if (args[0].equalsIgnoreCase("Local")) {
				if (player.hasPermission(Permissions.COMMAND_CHATRADIUS_LOCAL.getNode())) {
					if (args.length == 1) {

						boolean isValid = this.plugin.api().getLocalChatData().containsUser(player.getUniqueId());

						if (isValid) {
							Messages.CHAT_RADIUS_LOCAL_CHAT_ALREADY_ENABLED.sendMessage(player);

							return true;
						}

						this.plugin.api().getGlobalChatData().removeUser(player.getUniqueId());
						this.plugin.api().getWorldChatData().removeUser(player.getUniqueId());
						this.plugin.api().getLocalChatData().addUser(player.getUniqueId());

						Messages.CHAT_RADIUS_LOCAL_CHAT_ENABLED.sendMessage(player);
					} else {
						Messages.INVALID_USAGE.sendMessage(player, "{usage}", "/chatradius local");
					}
				} else {
					Messages.NO_PERMISSION.sendMessage(player);
				}
			}

			if (args[0].equalsIgnoreCase("global")) {
				if (player.hasPermission(Permissions.COMMAND_CHATRADIUS_GLOBAL.getNode())) {
					if (args.length == 1) {
						boolean isValid = this.plugin.api().getGlobalChatData().containsUser(player.getUniqueId());

						if (isValid) {
							Messages.CHAT_RADIUS_GLOBAL_CHAT_ALREADY_ENABLED.sendMessage(player);

							return true;
						}

						this.plugin.api().getLocalChatData().removeUser(player.getUniqueId());
						this.plugin.api().getWorldChatData().removeUser(player.getUniqueId());
						this.plugin.api().getGlobalChatData().addUser(player.getUniqueId());

						Messages.CHAT_RADIUS_GLOBAL_CHAT_ENABLED.sendMessage(player);
					} else {
						Messages.INVALID_USAGE.sendMessage(player, "{usage}", "/chatradius global");
					}
				} else {
					Messages.NO_PERMISSION.sendMessage(player);
				}
			}

			if (args[0].equalsIgnoreCase("world")) {
				if (player.hasPermission(Permissions.COMMAND_CHATRADIUS_WORLD.getNode())) {
					if (args.length == 1) {
						boolean isValid = this.plugin.api().getWorldChatData().containsUser(player.getUniqueId());

						if (isValid) {
							Messages.CHAT_RADIUS_WORLD_CHAT_ALREADY_ENABLED.sendMessage(player);

							return true;
						}

						this.plugin.api().getLocalChatData().removeUser(player.getUniqueId());
						this.plugin.api().getGlobalChatData().removeUser(player.getUniqueId());
						this.plugin.api().getWorldChatData().addUser(player.getUniqueId());

						Messages.CHAT_RADIUS_WORLD_CHAT_ENABLED.sendMessage(player);
					} else {
						Messages.INVALID_USAGE.sendMessage(player, "{usage}", "/chatradius world");
					}
				} else {
					Messages.NO_PERMISSION.sendMessage(player);
				}
			}

			if (args[0].equalsIgnoreCase("spy")) {
				if (player.hasPermission(Permissions.COMMAND_CHATRADIUS_SPY.getNode())) {
					if (args.length == 1) {
						boolean isValid = this.plugin.api().getSpyChatData().containsUser(player.getUniqueId());

						if (isValid) {
							this.plugin.api().getSpyChatData().removeUser(player.getUniqueId());

							Messages.CHAT_RADIUS_SPY_DISABLED.sendMessage(player);

							return true;
						}

						this.plugin.api().getSpyChatData().addUser(player.getUniqueId());

						Messages.CHAT_RADIUS_SPY_ENABLED.sendMessage(player);
					} else {
						Messages.INVALID_USAGE.sendMessage(player, "{usage}", "/chatradius spy");
					}
				} else {
					Messages.NO_PERMISSION.sendMessage(player);
				}
			}
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		List<String> completions = new ArrayList<>();

		if (args.length == 1) {
			completions.add("help");

			completions.add("local");
			completions.add("global");
			completions.add("world");
			completions.add("spy");
		}

		return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
	}
}