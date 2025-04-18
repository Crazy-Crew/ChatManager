package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.api.chat.UserRepliedData;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.core.api.support.PluginManager;
import com.ryderbelserion.core.api.support.interfaces.Plugin;
import me.h1dd3nxn1nja.chatmanager.ChatManagerMercurioMC;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.EssentialsSupport;
import me.h1dd3nxn1nja.chatmanager.support.PluginSupport;
import com.ryderbelserion.chatmanager.enums.Files;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CommandMessage implements CommandExecutor, TabCompleter {

	@NotNull
	private final ChatManagerMercurioMC plugin = ChatManagerMercurioMC.get();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (sender instanceof ConsoleCommandSender) {
			this.plugin.getLogger().warning("This command can only be used by a player.");

			return true;
		}

		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("message")) {
			if (player.hasPermission(Permissions.COMMAND_MESSAGE.getNode())) {
				StringBuilder message = new StringBuilder();

				for (int i = 1; i < args.length; i++) {
					message.append(args[i]).append(" ");
				}

				if (args.length < 1) {
					Messages.INVALID_USAGE.sendMessage(player, "{usage}", "/message <player> <message>");

					return true;
				}

				Player target = this.plugin.getServer().getPlayer(args[0]);

				if (target == null || !target.isOnline()) {
					Messages.PLAYER_NOT_FOUND.sendMessage(player, "{target}", args[0]);

					return true;
				}

				if ((target == player) && (!player.hasPermission(Permissions.COMMAND_MESSAGE_SELF.getNode()))) {
					Messages.PRIVATE_MESSAGE_SELF.sendMessage(player);

					return true;
				}

				if ((target.getGameMode().equals(GameMode.SPECTATOR) && (!player.hasPermission(Permissions.BYPASS_SPECTATOR.getNode())))) {
					Messages.PLAYER_NOT_FOUND.sendMessage(player, "{target}", args[0]);

					return true;
				}

				if (plugin.api().getToggleMessageData().containsUser(target.getUniqueId()) && !player.hasPermission(Permissions.BYPASS_TOGGLE_PM.getNode())) {
					Messages.PRIVATE_MESSAGE_TOGGLED.sendMessage(player);

					return true;
				}

				if ((!player.canSee(target)) && (!player.hasPermission(Permissions.BYPASS_VANISH.getNode()))) {
					Messages.PLAYER_NOT_FOUND.sendMessage(player, "{target}", args[0]);

					return true;
				}

				if (handleMessage(args, player, message, target)) return true;
			} else {
				Messages.NO_PERMISSION.sendMessage(player);
			}
		}

		if (cmd.getName().equalsIgnoreCase("reply")) {
			if (player.hasPermission(Permissions.COMMAND_REPLY.getNode())) {
				if (args.length > 0) {
					StringBuilder message = new StringBuilder();

					for (String arg : args) {
						message.append(arg).append(" ");
					}

					UUID other = this.plugin.api().getUserRepliedData().getUser(player.getUniqueId());

					if (other == null) {
						Messages.PRIVATE_MESSAGE_RECIPIENT_NOT_FOUND.sendMessage(player);

						return true;
					}

					Player target = this.plugin.getServer().getPlayer(other);

					if (target == null || !target.isOnline()) {
						Messages.PRIVATE_MESSAGE_RECIPIENT_NOT_FOUND.sendMessage(player);

						return true;
					}

					if (this.plugin.api().getToggleMessageData().containsUser(target.getUniqueId())) {
						Messages.PRIVATE_MESSAGE_TOGGLED.sendMessage(player);

						return true;
					}

					if (!player.canSee(target)) {
						Messages.PLAYER_NOT_FOUND.sendMessage(player, "{target}", args[0]);

						return true;
					}

					if (handleMessage(args, player, message, target)) return true;
				} else {
					Messages.INVALID_USAGE.sendMessage(player, "{usage}", "/reply <message>");
				}
			} else {
				Messages.NO_PERMISSION.sendMessage(player);
			}
		}

		if (cmd.getName().equalsIgnoreCase("togglepm")) {
			if (player.hasPermission(Permissions.TOGGLE_PM.getNode())) {
				if (args.length == 0) {

					boolean isValid = plugin.api().getToggleMessageData().containsUser(player.getUniqueId());

					if (isValid) {
						this.plugin.api().getToggleMessageData().removeUser(player.getUniqueId());

						Messages.TOGGLE_PM_DISABLED.sendMessage(player);

						return true;
					}

					this.plugin.api().getToggleMessageData().addUser(player.getUniqueId());

					Messages.TOGGLE_PM_ENABLED.sendMessage(player);

					return true;
				} else {
					Messages.INVALID_USAGE.sendMessage(player, "{usage}", "/togglepm");
				}
			} else {
				Messages.NO_PERMISSION.sendMessage(player);
			}
		}

		return true;
	}

	private boolean handleMessage(String[] args, Player player, StringBuilder message, Player target) {
		FileConfiguration config = Files.CONFIG.getConfiguration();

		if (message.isEmpty()) {
			player.sendMessage(Methods.color("You need to supply a message in order to reply/send to " + target.getName()));

			return true;
		}

		if (essentialsCheck(player, target)) return true;

		final Plugin genericVanish = PluginManager.getPlugin("GenericVanish");

        if (genericVanish != null && genericVanish.isEnabled() && genericVanish.isVanished(player.getUniqueId()) && !player.hasPermission(Permissions.BYPASS_VANISH.getNode())) {
            Messages.PLAYER_NOT_FOUND.sendMessage(player, "{target}", args[0]);

            return true;
        }

		final String sender_format = config.getString("Private_Messages.Sender.Format", "&c&l(!) &f&l[&e&lYou &d-> &e{receiver}&f&l] &b")
				.replace("{receiver}", target.getName())
				.replace("{receiver_displayname}", target.getDisplayName());

		final String receiver_format = config.getString("Private_Messages.Receiver.Format", "&c&l(!) &f&l[&e{player} &d-> &e&lYou&f&l] &b")
				.replace("{receiver}", target.getName())
				.replace("{receiver_displayname}", player.getDisplayName());

        Methods.sendMessage(player, "", Methods.placeholders(false, target, sender_format) + message, true, false, false);

		Methods.sendMessage(target, "", Methods.placeholders(false, player, receiver_format) + message, true, false, false);

		Methods.playSound(target, config, "Private_Messages.sound");

		final UserRepliedData data = this.plugin.api().getUserRepliedData();

		data.addUser(player.getUniqueId(), target.getUniqueId());
		data.addUser(target.getUniqueId(), player.getUniqueId());

		for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
			if ((staff != player) && (staff != target)) {
				if ((!player.hasPermission(Permissions.BYPASS_SOCIAL_SPY.getNode())) && (!target.hasPermission(Permissions.BYPASS_SOCIAL_SPY.getNode()))) {
					boolean contains = this.plugin.api().getSocialSpyData().containsUser(staff.getUniqueId());

					if (contains) {
						Messages.SOCIAL_SPY_FORMAT.sendMessage(staff, new HashMap<>() {{
							put("{player}", player.getName());
							put("{receiver", target.getName());
							put("{message}", message.toString());
						}});
					}
				}
			}
		}
		return false;
	}

	private final EssentialsSupport essentialsSupport = this.plugin.getPluginManager().getEssentialsSupport();

	private boolean essentialsCheck(Player player, Player target) {
		if (PluginSupport.ESSENTIALS.isPluginEnabled() && this.essentialsSupport != null) {
			if (this.essentialsSupport.getUser(target.getUniqueId()).isAfk() && (!player.hasPermission(Permissions.BYPASS_AFK.getNode()))) {
				Messages.PRIVATE_MESSAGE_AFK.sendMessage(player, "{target}", target.getName());

				return true;
			}

			if (this.essentialsSupport.isIgnored(target.getUniqueId(), player.getUniqueId()) && (!player.hasPermission(Permissions.BYPASS_IGNORED.getNode()))) {
				Messages.PRIVATE_MESSAGE_IGNORED.sendMessage(player, "{target}", target.getName());

				return true;
			}

			return this.essentialsSupport.isMuted(player.getUniqueId());
		}

		return false;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		List<String> completions = new ArrayList<>();

		if (args.length == 2) { // /message <player>
            if (args[0].equalsIgnoreCase("message")) {
                if (sender.hasPermission(Permissions.COMMAND_MESSAGE.getNode())) {
                    this.plugin.getServer().getOnlinePlayers().forEach(player -> completions.add(player.getName()));

                    if (!sender.hasPermission(Permissions.COMMAND_MESSAGE_SELF.getNode())) {
                        completions.remove(sender.getName());
                    }
                }

                return StringUtil.copyPartialMatches(args[1], completions, new ArrayList<>());
            }
		}

		return new ArrayList<>();
	}
}