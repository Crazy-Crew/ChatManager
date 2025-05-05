package com.ryderbelserion.chatmanager.commands.types.chat;

import com.ryderbelserion.chatmanager.api.objects.PaperUser;
import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.chatmanager.enums.core.PlayerState;
import com.ryderbelserion.chatmanager.utils.UserUtils;
import com.ryderbelserion.fusion.core.managers.PluginExtension;
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

    private final PluginExtension extension = this.plugin.getPluginExtension();

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

        if (!sender.canSee(player) && !Permissions.BYPASS_SPECTATOR.hasPermission(sender) || player.getGameMode().equals(GameMode.SPECTATOR) && !Permissions.BYPASS_VANISH.hasPermission(sender)) { //todo() wtf
            Messages.PLAYER_NOT_FOUND.sendMessage(player, "{target}", player.getName());
            return;
        }

        final PaperUser user = UserUtils.getUser(uuid);

        if (user.hasState(PlayerState.DIRECT_MESSAGES) && !sender.hasPermission(Permissions.BYPASS_TOGGLE_PM.getNode())) {
            Messages.PRIVATE_MESSAGE_TOGGLED.sendMessage(sender);

            return;
        }

        perform(message, user, player);
    }

    @Command(value = "chatmanager reply <message>", requiredSender = Player.class)
    @CommandDescription("Reply to the previous user!")
    @Permission(value = "chatmanager.reply", mode = Permission.Mode.ANY_OF)
    public void reply(final Player sender, @Argument("message") final String message) {
        final UUID uuid = sender.getUniqueId();

        final PaperUser user = UserUtils.getUser(uuid);

        if (!user.hasLastReply()) {
            Messages.PRIVATE_MESSAGE_RECIPIENT_NOT_FOUND.sendMessage(sender);

            return;
        }

        final Player player = this.plugin.getServer().getPlayer(user.getLastReply());

        if (player == null) {
            Messages.PRIVATE_MESSAGE_RECIPIENT_NOT_FOUND.sendMessage(sender);

            return;
        }

        if (!sender.canSee(player) && !Permissions.BYPASS_SPECTATOR.hasPermission(player) || player.getGameMode().equals(GameMode.SPECTATOR) && !Permissions.BYPASS_VANISH.hasPermission(player)) { //todo() wtf
            Messages.PLAYER_NOT_FOUND.sendMessage(player, "{target}", player.getName());

            return;
        }

        perform(message, user, player);
    }

    private void perform(final String arg, final PaperUser user, final Player player) {
        final Player sender = (Player) user.getAudience();

        final FileConfiguration config = Files.CONFIG.getConfiguration();

        if (arg.isEmpty()) {
            sender.sendMessage(Methods.color("You need to supply a message in order to reply/send to " + player.getName()));

            return;
        }

        if (essentialsCheck(sender, player)) return;

        /*final IPlugin genericVanish = this.extension.getPlugin("GenericVanish"); //todo() redo

        if (genericVanish != null && genericVanish.isEnabled() && genericVanish.isVanished(sender.getUniqueId()) && !sender.hasPermission(Permissions.BYPASS_VANISH.getNode())) {
            Messages.PLAYER_NOT_FOUND.sendMessage(sender, "{target}", player.getName());

            return;
        }*/

        final String sender_format = config.getString("Private_Messages.Sender.Format", "&c&l(!) &f&l[&e&lYou &d-> &e{receiver}&f&l] &b")
                .replace("{receiver}", player.getName())
                .replace("{receiver_displayname}", player.getDisplayName());

        final String receiver_format = config.getString("Private_Messages.Receiver.Format", "&c&l(!) &f&l[&e{player} &d-> &e&lYou&f&l] &b")
                .replace("{receiver}", player.getName())
                .replace("{receiver_displayname}", sender.getDisplayName());

        Methods.sendMessage(sender, "", Methods.placeholders(false, player, sender_format) + arg, true, false, false);

        Methods.sendMessage(player, "", Methods.placeholders(false, sender, receiver_format) + arg, true, false, false);

        Methods.playSound(player, config, "Private_Messages.sound");

        final UUID player_uuid = player.getUniqueId();
        final UUID sender_uuid = sender.getUniqueId();

        user.setLastReply(player_uuid);

        final PaperUser player_target = UserUtils.getUser(player_uuid);

        player_target.setLastReply(sender_uuid);

        for (final Player target : this.server.getOnlinePlayers()) {
            final UUID id = target.getUniqueId();

            if (id.equals(sender_uuid) || id.equals(player_uuid)) continue;

            if (Permissions.BYPASS_SOCIAL_SPY.hasPermission(sender) || Permissions.BYPASS_SOCIAL_SPY.hasPermission(player)) continue;

            final PaperUser target_user = UserUtils.getUser(id);

            if (!target_user.hasState(PlayerState.SOCIAL_SPY)) continue;

            Messages.SOCIAL_SPY_FORMAT.sendMessage(target, new HashMap<>() {{
                put("{player}", sender.getName());
                put("{receiver", player.getName());
                put("{message}", arg);
            }});
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