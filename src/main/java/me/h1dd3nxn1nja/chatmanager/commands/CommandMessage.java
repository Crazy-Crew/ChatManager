package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import me.h1dd3nxn1nja.chatmanager.support.EssentialsSupport;
import me.h1dd3nxn1nja.chatmanager.support.PluginSupport;
import com.ryderbelserion.chatmanager.enums.Files;
import me.h1dd3nxn1nja.chatmanager.support.vanish.EssentialsVanishSupport;
import me.h1dd3nxn1nja.chatmanager.support.vanish.GenericVanishSupport;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public class CommandMessage implements CommandExecutor {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@NotNull
	private final GenericVanishSupport genericVanishSupport = this.plugin.getPluginManager().getGenericVanishSupport();

	@NotNull
	private final PlaceholderManager placeholderManager = this.plugin.getCrazyManager().getPlaceholderManager();

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
					this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/message <player> <message>", true);
					return true;
				}

				Player target = this.plugin.getServer().getPlayer(args[0]);

				if (target == null || !target.isOnline()) {
					if (playerNotFound != null) this.plugin.getMethods().sendMessage(player, playerNotFound.replace("{target}", args[0]), true);
					return true;
				}

				if ((target == player) && (!player.hasPermission(Permissions.COMMAND_MESSAGE_SELF.getNode()))) {
					this.plugin.getMethods().sendMessage(player, messages.getString("Private_Message.Self"), true);
					return true;
				}

				if ((target.getGameMode().equals(GameMode.SPECTATOR) && (!player.hasPermission(Permissions.BYPASS_SPECTATOR.getNode())))) {
					if (playerNotFound != null) this.plugin.getMethods().sendMessage(player, playerNotFound.replace("{target}", args[0]), true);
					return true;
				}

				if (plugin.api().getToggleMessageData().containsUser(target.getUniqueId()) && !player.hasPermission(Permissions.BYPASS_TOGGLE_PM.getNode())) {
					this.plugin.getMethods().sendMessage(player, messages.getString("Private_Message.Toggled"), true);
					return true;
				}

				if ((!player.canSee(target)) && (!player.hasPermission(Permissions.BYPASS_VANISH.getNode()))) {
					if (playerNotFound != null) this.plugin.getMethods().sendMessage(player, playerNotFound.replace("{target}", args[0]), true);
					return true;
				}

				if (duplicate(args, playerNotFound, player, message, target, player.getUniqueId(), target.getUniqueId())) return true;
			} else {
				player.sendMessage(this.plugin.getMethods().noPermission());
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
						this.plugin.getMethods().sendMessage(player, messages.getString("Private_Message.Recipient_Not_Found"), true);
						return true;
					}

					if (this.plugin.api().getToggleMessageData().containsUser(target.getUniqueId())) {
						this.plugin.getMethods().sendMessage(player, messages.getString("Private_Message.Toggled"), true);
						return true;
					}

					if (!player.canSee(target)) {
						if (playerNotFound != null) this.plugin.getMethods().sendMessage(player, playerNotFound.replace("{target}", args[0]), true);
						return true;
					}

					if (duplicate(args, playerNotFound, player, message, target, target.getUniqueId(), player.getUniqueId())) return true;
				} else {
					player.sendMessage(this.plugin.getMethods().color("&cCommand Usage: &7/reply <message>"));
				}
			} else {
				player.sendMessage(this.plugin.getMethods().noPermission());
			}
		}

		if (cmd.getName().equalsIgnoreCase("togglepm")) {
			if (player.hasPermission(Permissions.TOGGLE_PM.getNode())) {
				if (args.length == 0) {

					boolean isValid = plugin.api().getToggleMessageData().containsUser(player.getUniqueId());

					if (isValid) {
						this.plugin.api().getToggleMessageData().removeUser(player.getUniqueId());
						this.plugin.getMethods().sendMessage(player, messages.getString("TogglePM.Disabled"), true);
						return true;
					}

					this.plugin.api().getToggleMessageData().addUser(player.getUniqueId());
					this.plugin.getMethods().sendMessage(player, messages.getString("TogglePM.Enabled"), true);

					return true;
				} else {
					this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/togglepm", true);
				}
			} else {
				this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
			}
		}

		return true;
	}

	private boolean duplicate(String[] args, String playerNotFound, Player player, StringBuilder message, Player target, UUID uniqueId, UUID uniqueId2) {
		FileConfiguration config = Files.CONFIG.getConfiguration();
		FileConfiguration messages = Files.MESSAGES.getConfiguration();

		if (message.isEmpty()) {
			player.sendMessage(this.plugin.getMethods().color(this.plugin.getMethods().getPrefix() + "You need to supply a message in order to reply/send to " + target.getName()));
			return true;
		}

		if (essentialsCheck(args, playerNotFound, player, target)) return true;

		if (PluginSupport.PREMIUM_VANISH.isPluginEnabled() || PluginSupport.SUPER_VANISH.isPluginEnabled() && this.genericVanishSupport.isVanished(target) && !player.hasPermission(Permissions.BYPASS_VANISH.getNode())) {
			if (playerNotFound != null) this.plugin.getMethods().sendMessage(player, playerNotFound.replace("{target}", args[0]), true);
			return true;
		}

		this.plugin.getMethods().sendMessage(player, this.placeholderManager.setPlaceholders(target, config.getString("Private_Messages.Sender.Format")
				.replace("{receiver}", target.getName())
				.replace("{receiver_displayname}", target.getDisplayName()) + message), true);

		this.plugin.getMethods().sendMessage(target, this.placeholderManager.setPlaceholders(player, config.getString("Private_Messages.Receiver.Format")
				.replace("{receiver}", target.getName())
				.replace("{receiver_displayname}", player.getDisplayName()) + message), true);

		this.plugin.getMethods().playSound(target, config, "Private_Messages.sound");

		this.plugin.api().getUserRepliedData().addUser(uniqueId, uniqueId2);
		this.plugin.api().getUserRepliedData().addUser(uniqueId2, uniqueId);

		for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
			if ((staff != player) && (staff != target)) {
				if ((!player.hasPermission(Permissions.BYPASS_SOCIAL_SPY.getNode())) && (!target.hasPermission(Permissions.BYPASS_SOCIAL_SPY.getNode()))) {
					boolean contains = this.plugin.api().getSocialSpyData().containsUser(staff.getUniqueId());

					if (contains) this.plugin.getMethods().sendMessage(staff, messages.getString("Social_Spy.Format").replace("{player}", player.getName()).replace("{receiver}", target.getName()).replace("{message}", message), true);
				}
			}
		}
		return false;
	}

	private final EssentialsSupport essentialsSupport = this.plugin.getPluginManager().getEssentialsSupport();
	private final EssentialsVanishSupport essentialsVanishSupport = this.plugin.getPluginManager().getEssentialsVanishSupport();

	private boolean essentialsCheck(String[] args, String playerNotFound, Player player, Player target) {
		FileConfiguration messages = Files.MESSAGES.getConfiguration();

		if (PluginSupport.ESSENTIALS.isPluginEnabled()) {
			if (this.essentialsSupport.getUser(target.getUniqueId()).isAfk() && (!player.hasPermission(Permissions.BYPASS_AFK.getNode()))) {
				this.plugin.getMethods().sendMessage(player, messages.getString("Private_Message.AFK").replace("{target}", target.getName()), true);
				return true;
			}

			if (this.essentialsSupport.isIgnored(target.getUniqueId(), player.getUniqueId()) && (!player.hasPermission(Permissions.BYPASS_IGNORED.getNode()))) {
				this.plugin.getMethods().sendMessage(player, messages.getString("Private_Message.Ignored").replace("{target}", target.getName()), true);
				return true;
			}

			if (this.essentialsVanishSupport.isVanished(target) && (!player.hasPermission(Permissions.BYPASS_VANISH.getNode()))) {
				if (playerNotFound != null) this.plugin.getMethods().sendMessage(player, playerNotFound.replace("{target}", args[0]), true);
				return true;
			}

			return this.essentialsSupport.isMuted(player.getUniqueId());
		}

		return false;
	}
}