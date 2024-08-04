package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.api.chat.UserRepliedData;
import com.ryderbelserion.vital.paper.plugins.PluginManager;
import com.ryderbelserion.vital.paper.plugins.interfaces.Plugin;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
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
import java.util.List;
import java.util.UUID;

public class CommandMessage implements CommandExecutor, TabCompleter {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		FileConfiguration messages = Files.MESSAGES.getConfiguration();

		String playerNotFound = messages.getString("Message.Player_Not_Found");

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
					Methods.sendMessage(player, "&cCommand Usage: &7/message <player> <message>", true);
					return true;
				}

				Player target = this.plugin.getServer().getPlayer(args[0]);

				if (target == null || !target.isOnline()) {
					if (playerNotFound != null) Methods.sendMessage(player, playerNotFound.replace("{target}", args[0]), true);
					return true;
				}

				if ((target == player) && (!player.hasPermission(Permissions.COMMAND_MESSAGE_SELF.getNode()))) {
					Methods.sendMessage(player, messages.getString("Private_Message.Self"), true);
					return true;
				}

				if ((target.getGameMode().equals(GameMode.SPECTATOR) && (!player.hasPermission(Permissions.BYPASS_SPECTATOR.getNode())))) {
					if (playerNotFound != null) Methods.sendMessage(player, playerNotFound.replace("{target}", args[0]), true);
					return true;
				}

				if (plugin.api().getToggleMessageData().containsUser(target.getUniqueId()) && !player.hasPermission(Permissions.BYPASS_TOGGLE_PM.getNode())) {
					Methods.sendMessage(player, messages.getString("Private_Message.Toggled"), true);
					return true;
				}

				if ((!player.canSee(target)) && (!player.hasPermission(Permissions.BYPASS_VANISH.getNode()))) {
					if (playerNotFound != null) Methods.sendMessage(player, playerNotFound.replace("{target}", args[0]), true);
					return true;
				}

				if (handleMessage(args, playerNotFound, player, message, target)) return true;
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
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

					Player target = this.plugin.getServer().getPlayer(other);

					if (target == null || !target.isOnline()) {
						Methods.sendMessage(player, messages.getString("Private_Message.Recipient_Not_Found"), true);
						return true;
					}

					if (this.plugin.api().getToggleMessageData().containsUser(target.getUniqueId())) {
						Methods.sendMessage(player, messages.getString("Private_Message.Toggled"), true);
						return true;
					}

					if (!player.canSee(target)) {
						if (playerNotFound != null) Methods.sendMessage(player, playerNotFound.replace("{target}", args[0]), true);
						return true;
					}

					if (handleMessage(args, playerNotFound, player, message, target)) return true;
				} else {
					Methods.sendMessage(player, "&cCommand Usage: &7/reply <message>", true);
				}
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
			}
		}

		if (cmd.getName().equalsIgnoreCase("togglepm")) {
			if (player.hasPermission(Permissions.TOGGLE_PM.getNode())) {
				if (args.length == 0) {

					boolean isValid = plugin.api().getToggleMessageData().containsUser(player.getUniqueId());

					if (isValid) {
						this.plugin.api().getToggleMessageData().removeUser(player.getUniqueId());
						Methods.sendMessage(player, messages.getString("TogglePM.Disabled"), true);
						return true;
					}

					this.plugin.api().getToggleMessageData().addUser(player.getUniqueId());
					Methods.sendMessage(player, messages.getString("TogglePM.Enabled"), true);

					return true;
				} else {
					Methods.sendMessage(player, "&cCommand Usage: &7/togglepm", true);
				}
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
			}
		}

		return true;
	}

	private boolean handleMessage(String[] args, String playerNotFound, Player player, StringBuilder message, Player target) {
		FileConfiguration config = Files.CONFIG.getConfiguration();
		FileConfiguration messages = Files.MESSAGES.getConfiguration();

		if (message.isEmpty()) {
			player.sendMessage(Methods.color("You need to supply a message in order to reply/send to " + target.getName()));
			return true;
		}

		if (essentialsCheck(player, target)) return true;

		final Plugin genericVanish = PluginManager.getPlugin("GenericVanish");

		if (genericVanish.isEnabled() && genericVanish.isVanished(player.getUniqueId()) && !player.hasPermission(Permissions.BYPASS_VANISH.getNode())) {
			if (playerNotFound != null) Methods.sendMessage(player, playerNotFound.replace("{target}", args[0]), true);

			return true;
		}

		Methods.sendMessage(player, Methods.placeholders(false, target, config.getString("Private_Messages.Sender.Format")
				.replace("{receiver}", target.getName())
				.replace("{receiver_displayname}", target.getDisplayName()) + message), true);

		Methods.sendMessage(target, Methods.placeholders(false, player, config.getString("Private_Messages.Receiver.Format")
				.replace("{receiver}", target.getName())
				.replace("{receiver_displayname}", player.getDisplayName()) + message), true);

		Methods.playSound(target, config, "Private_Messages.sound");

		final UserRepliedData data = this.plugin.api().getUserRepliedData();

		data.addUser(player.getUniqueId(), target.getUniqueId());
		data.addUser(target.getUniqueId(), player.getUniqueId());

		for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
			if ((staff != player) && (staff != target)) {
				if ((!player.hasPermission(Permissions.BYPASS_SOCIAL_SPY.getNode())) && (!target.hasPermission(Permissions.BYPASS_SOCIAL_SPY.getNode()))) {
					boolean contains = this.plugin.api().getSocialSpyData().containsUser(staff.getUniqueId());

					if (contains) Methods.sendMessage(staff, messages.getString("Social_Spy.Format").replace("{player}", player.getName())
							.replace("{receiver}", target.getName())
							.replace("{message}", message), true);
				}
			}
		}
		return false;
	}

	private final EssentialsSupport essentialsSupport = this.plugin.getPluginManager().getEssentialsSupport();

	private boolean essentialsCheck(Player player, Player target) {
		FileConfiguration messages = Files.MESSAGES.getConfiguration();

		if (PluginSupport.ESSENTIALS.isPluginEnabled()) {
			if (this.essentialsSupport.getUser(target.getUniqueId()).isAfk() && (!player.hasPermission(Permissions.BYPASS_AFK.getNode()))) {
				Methods.sendMessage(player, messages.getString("Private_Message.AFK").replace("{target}", target.getName()), true);
				return true;
			}

			if (this.essentialsSupport.isIgnored(target.getUniqueId(), player.getUniqueId()) && (!player.hasPermission(Permissions.BYPASS_IGNORED.getNode()))) {
				Methods.sendMessage(player, messages.getString("Private_Message.Ignored").replace("{target}", target.getName()), true);
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