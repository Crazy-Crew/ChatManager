package com.ryderbelserion.chatmanager.commands.types.admin;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.cmds.CommandSpyData;
import com.ryderbelserion.chatmanager.api.cmds.SocialSpyData;
import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.chatmanager.enums.commands.SpyType;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CommandSpy extends AnnotationFeature {

    private final ApiLoader api = this.plugin.api();

    private final CommandSpyData data = this.api.getCommandSpyData();

    private final SocialSpyData social = this.api.getSocialSpyData();

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command(value = "chatmanager spy <type>", requiredSender = Player.class)
    @CommandDescription("Access the ability to spy on commands/messages!")
    @Permission(value = "chatmanager.spy", mode = Permission.Mode.ANY_OF)
    public void debug(final Player player, @Argument("type") final @NotNull SpyType type) {
        final UUID uuid = player.getUniqueId();

        final String node = type == SpyType.COMMAND_SPY ? Permissions.COMMAND_SPY.getNode() : Permissions.SOCIAL_SPY.getNode();

        if (!player.hasPermission(node)) return; // no permission

        switch (type) {
            case COMMAND_SPY -> {
                if (this.data.containsUser(uuid)) {
                    this.data.removeUser(uuid);

                    Messages.COMMAND_SPY_DISABLED.sendMessage(player);

                    return;
                }

                this.data.addUser(uuid);

                Messages.COMMAND_SPY_ENABLED.sendMessage(player);
            }

            case SOCIAL_SPY -> {
                if (this.social.containsUser(uuid)) {
                    this.social.removeUser(uuid);

                    Messages.SOCIAL_SPY_DISABLED.sendMessage(player);

                    return;
                }

                this.social.addUser(uuid);

                Messages.SOCIAL_SPY_ENABLED.sendMessage(player);
            }
        }
    }
}