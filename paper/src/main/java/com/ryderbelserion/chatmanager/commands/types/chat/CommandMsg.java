package com.ryderbelserion.chatmanager.commands.types.chat;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.chat.UserRepliedData;
import com.ryderbelserion.chatmanager.api.cmds.ToggleMessageData;
import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.core.api.support.PluginManager;
import com.ryderbelserion.core.api.support.interfaces.Plugin;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.EssentialsSupport;
import me.h1dd3nxn1nja.chatmanager.support.PluginSupport;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class CommandMsg extends AnnotationFeature {

    private final ApiLoader api = this.plugin.api();

    private final ToggleMessageData messageData = this.api.getToggleMessageData();

    private final UserRepliedData userRepliedData = this.api.getUserRepliedData();

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command(value = "chatmanager msg <player> <message>", requiredSender = Player.class)
    @CommandDescription("Access the ability to enter different chat modes.")
    @Permission(value = "chatmanager.message", mode = Permission.Mode.ANY_OF)
    public void message(final Player sender, @Argument("player") final Player player, @Argument("message") final String message) {
        final UUID uuid = player.getUniqueId();

        if (sender.getUniqueId().equals(uuid)) {
            Messages.PRIVATE_MESSAGE_SELF.sendMessage(player);

            return;
        }

        if (!sender.canSee(player) && !player.hasPermission(Permissions.BYPASS_SPECTATOR.getNode()) || player.getGameMode().equals(GameMode.SPECTATOR) && !player.hasPermission(Permissions.BYPASS_VANISH.getNode())) {
            Messages.PLAYER_NOT_FOUND.sendMessage(player, "{target}", player.getName());

            return;
        }

        if (this.messageData.containsUser(uuid) && !player.hasPermission(Permissions.BYPASS_TOGGLE_PM.getNode())) {
            Messages.PRIVATE_MESSAGE_TOGGLED.sendMessage(sender);

            return;
        }

        handleMessage(message, sender, player);
    }

    @Command(value = "chatmanager reply <message>", requiredSender = Player.class)
    @CommandDescription("Reply to the previous user!")
    @Permission(value = "chatmanager.reply", mode = Permission.Mode.ANY_OF)
    public void reply(final Player sender, @Argument("message") final String message) {
        final UUID uuid = sender.getUniqueId();

        if (!this.userRepliedData.containsUser(uuid)) {
            Messages.PRIVATE_MESSAGE_RECIPIENT_NOT_FOUND.sendMessage(sender);

            return;
        }

        final UUID target = this.userRepliedData.getUser(uuid);

        final Player player = this.plugin.getServer().getPlayer(target);

        if (player == null) {
            Messages.PRIVATE_MESSAGE_RECIPIENT_NOT_FOUND.sendMessage(sender);

            return;
        }

        if (!sender.canSee(player) && !player.hasPermission(Permissions.BYPASS_SPECTATOR.getNode()) || player.getGameMode().equals(GameMode.SPECTATOR) && !player.hasPermission(Permissions.BYPASS_VANISH.getNode())) {
            Messages.PLAYER_NOT_FOUND.sendMessage(player, "{target}", player.getName());

            return;
        }

        handleMessage(message, sender, player);
    }

    private void handleMessage(final String arg, final Player sender, final Player player) {
        final FileConfiguration config = Files.CONFIG.getConfiguration();

        if (arg.isEmpty()) {
            sender.sendMessage(Methods.color("You need to supply a message in order to reply/send to " + player.getName()));

            return;
        }

        if (essentialsCheck(sender, player)) return;

        final Plugin genericVanish = PluginManager.getPlugin("GenericVanish");

        if (genericVanish != null && genericVanish.isEnabled() && genericVanish.isVanished(sender.getUniqueId()) && !sender.hasPermission(Permissions.BYPASS_VANISH.getNode())) {
            Messages.PLAYER_NOT_FOUND.sendMessage(sender, "{target}", player.getName());

            return;
        }

        final String sender_format = config.getString("Private_Messages.Sender.Format", "&c&l(!) &f&l[&e&lYou &d-> &e{receiver}&f&l] &b")
                .replace("{receiver}", player.getName())
                .replace("{receiver_displayname}", player.getDisplayName());

        final String receiver_format = config.getString("Private_Messages.Receiver.Format", "&c&l(!) &f&l[&e{player} &d-> &e&lYou&f&l] &b")
                .replace("{receiver}", player.getName())
                .replace("{receiver_displayname}", sender.getDisplayName());

        Methods.sendMessage(sender, "", Methods.placeholders(false, player, sender_format) + arg, true, false, false);

        Methods.sendMessage(player, "", Methods.placeholders(false, sender, receiver_format) + arg, true, false, false);

        Methods.playSound(player, config, "Private_Messages.sound");

        final UserRepliedData data = this.plugin.api().getUserRepliedData();

        data.addUser(sender.getUniqueId(), player.getUniqueId());
        data.addUser(player.getUniqueId(), sender.getUniqueId());

        for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
            if ((staff != sender) && (staff != player)) {
                if ((!sender.hasPermission(Permissions.BYPASS_SOCIAL_SPY.getNode())) && (!player.hasPermission(Permissions.BYPASS_SOCIAL_SPY.getNode()))) {
                    boolean contains = this.plugin.api().getSocialSpyData().containsUser(staff.getUniqueId());

                    if (contains) {
                        Messages.SOCIAL_SPY_FORMAT.sendMessage(staff, new HashMap<>() {{
                            put("{player}", sender.getName());
                            put("{receiver", player.getName());
                            put("{message}", arg);
                        }});
                    }
                }
            }
        }
    }

    private final EssentialsSupport essentialsSupport = this.plugin.getPluginManager().getEssentialsSupport();

    private boolean essentialsCheck(Player sender, Player player) {
        if (PluginSupport.ESSENTIALS.isPluginEnabled() && this.essentialsSupport != null) {
            if (this.essentialsSupport.getUser(player.getUniqueId()).isAfk() && (!sender.hasPermission(Permissions.BYPASS_AFK.getNode()))) {
                Messages.PRIVATE_MESSAGE_AFK.sendMessage(sender, "{player}", player.getName());

                return true;
            }

            if (this.essentialsSupport.isIgnored(player.getUniqueId(), sender.getUniqueId()) && (!sender.hasPermission(Permissions.BYPASS_IGNORED.getNode()))) {
                Messages.PRIVATE_MESSAGE_IGNORED.sendMessage(sender, "{player}", player.getName());

                return true;
            }

            return this.essentialsSupport.isMuted(sender.getUniqueId());
        }

        return false;
    }
}