package me.h1dd3nxn1nja.chatmanager.paper.commands;

import com.ryderbelserion.chatmanager.paper.files.enums.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandRadius implements CommandExecutor {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (!(sender instanceof Player player)) {
			this.plugin.getMethods().sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		FileConfiguration messages = Files.MESSAGES.getFile();

		if (cmd.getName().equalsIgnoreCase("chatradius")) {
			if (player.hasPermission("chatmanager.chatradius")) {
				if (args.length == 0) {
					this.plugin.getMethods().sendMessage(player, "", true);
					this.plugin.getMethods().sendMessage(player, (" &3Chat Radius Help Menu &f(v" + plugin.getDescription().getVersion() + ")"), true);
					this.plugin.getMethods().sendMessage(player, "", true);
					this.plugin.getMethods().sendMessage(player, (" &f/ChatRadius Help &e- Shows a list of commands for chat radius."), true);
					this.plugin.getMethods().sendMessage(player, (" &f/ChatRadius Local &e- Enables local chat."), true);
					this.plugin.getMethods().sendMessage(player, (" &f/ChatRadius Global &e- Enables global chat."), true);
					this.plugin.getMethods().sendMessage(player, (" &f/ChatRadius World &e- Enables world chat."), true);
					this.plugin.getMethods().sendMessage(player, "", true);
				}
			} else {
				this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
				return true;
			}

			if (args[0].equalsIgnoreCase("help")) {
				if (player.hasPermission("chatmanager.chatradius")) {
					if (args.length == 1) {
						this.plugin.getMethods().sendMessage(player, "", true);
						this.plugin.getMethods().sendMessage(player, (" &3Chat Radius Help Menu &f(v" + plugin.getDescription().getVersion() + ")"), true);
						this.plugin.getMethods().sendMessage(player, "", true);
						this.plugin.getMethods().sendMessage(player, (" &f/ChatRadius Help &e- Shows a list of commands for chat radius."), true);
						this.plugin.getMethods().sendMessage(player, (" &f/ChatRadius Local &e- Enables local chat."), true);
						this.plugin.getMethods().sendMessage(player, (" &f/ChatRadius Global &e- Enables global chat."), true);
						this.plugin.getMethods().sendMessage(player, (" &f/ChatRadius World &e- Enables world chat."), true);
						this.plugin.getMethods().sendMessage(player, (" &f/ChatRadius Spy &e- Enables chat radius spy. Players will be able to see all messages sent."), true);
						this.plugin.getMethods().sendMessage(player, "", true);
					}
				} else {
					this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
					return true;
				}
			}

			if (args[0].equalsIgnoreCase("Local")) {
				if (player.hasPermission("chatmanager.chatradius.local")) {
					if (args.length == 1) {

						boolean isValid = this.plugin.api().getLocalChatData().containsUser(player.getUniqueId());

						if (isValid) {
							this.plugin.api().getGlobalChatData().removeUser(player.getUniqueId());
							this.plugin.api().getWorldChatData().removeUser(player.getUniqueId());
							this.plugin.api().getLocalChatData().addUser(player.getUniqueId());

							this.plugin.getMethods().sendMessage(player, messages.getString("Chat_Radius.Local_Chat.Enabled"), true);

							return true;
						}

						this.plugin.getMethods().sendMessage(player, messages.getString("Chat_Radius.Local_Chat.Already_Enabled"), true);
					} else {
						this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/ChatRadius Local", true);
					}
				} else {
					this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
				}
			}

			if (args[0].equalsIgnoreCase("Global")) {
				if (player.hasPermission("chatmanager.chatradius.global")) {
					if (args.length == 1) {
						boolean isValid = this.plugin.api().getGlobalChatData().containsUser(player.getUniqueId());

						if (isValid) {
							this.plugin.api().getLocalChatData().removeUser(player.getUniqueId());
							this.plugin.api().getWorldChatData().removeUser(player.getUniqueId());
							this.plugin.api().getGlobalChatData().addUser(player.getUniqueId());

							this.plugin.getMethods().sendMessage(player, messages.getString("Chat_Radius.Global_Chat.Enabled"), true);
							return true;
						}

						this.plugin.getMethods().sendMessage(player, messages.getString("Chat_Radius.Global_Chat.Already_Enabled"), true);
					} else {
						this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/ChatRadius Global", true);
					}
				} else {
					this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
				}
			}

			if (args[0].equalsIgnoreCase("World")) {
				if (player.hasPermission("chatmanager.chatradius.world")) {
					if (args.length == 1) {
						boolean isValid = this.plugin.api().getWorldChatData().containsUser(player.getUniqueId());

						if (isValid) {
							this.plugin.api().getLocalChatData().removeUser(player.getUniqueId());
							this.plugin.api().getGlobalChatData().removeUser(player.getUniqueId());
							this.plugin.api().getWorldChatData().addUser(player.getUniqueId());

							this.plugin.getMethods().sendMessage(player, messages.getString("Chat_Radius.World_Chat.Enabled"), true);
							return true;
						}

						this.plugin.getMethods().sendMessage(player, messages.getString("Chat_Radius.World_Chat.Already_Enabled"), true);
					} else {
						this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/ChatRadius World", true);
					}
				} else {
					this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
				}
			}

			if (args[0].equalsIgnoreCase("Spy")) {
				if (player.hasPermission("chatmanager.chatradius.spy")) {
					if (args.length == 1) {
						boolean isValid = this.plugin.api().getSpyChatData().containsUser(player.getUniqueId());

						if (isValid) {
							this.plugin.api().getSpyChatData().removeUser(player.getUniqueId());
							this.plugin.getMethods().sendMessage(player, messages.getString("Chat_Radius.Spy.Disabled"), true);

							return true;
						}

						this.plugin.api().getSpyChatData().addUser(player.getUniqueId());
						this.plugin.getMethods().sendMessage(player, messages.getString("Chat_Radius.Spy.Enabled"), true);
					} else {
						this.plugin.getMethods().sendMessage(player, ("&cCommand Usage: &7/ChatRadius Spy"), true);
					}
				} else {
					this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
				}
			}
		}

		return true;
	}
}