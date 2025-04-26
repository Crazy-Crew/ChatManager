package com.ryderbelserion.chatmanager.commands.types.toggles;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.chat.PerWorldChatData;
import com.ryderbelserion.chatmanager.api.cmds.ToggleChatData;
import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Messages;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Flag;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CommandPerWorldChat extends AnnotationFeature {

    private final ApiLoader api = this.plugin.api();

    private final PerWorldChatData data = this.api.getPerWorldChatData();

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command("chatmanager perworldchat")
    @CommandDescription("Allows the sender to enable per world chat!")
    @Permission(value = "chatmanager.perworldchat", mode = Permission.Mode.ANY_OF)
    public void togglechat(final Player player, @Flag(value = "silent", aliases = {"s"}, permission = "perworldchat.silent", description = "Silences the pesky messages") boolean isSilent) {
        final UUID uuid = player.getUniqueId();

        final boolean hasUser = this.data.containsUser(uuid);

        if (hasUser) {
            this.data.removeUser(uuid);

            if (!isSilent) {
                Messages.PER_WORLD_CHAT_BYPASS_DISABLED.sendMessage(player);
            }

            return;
        }

        this.data.addUser(uuid);

        if (!isSilent) {
            Messages.PER_WORLD_CHAT_BYPASS_ENABLED.sendMessage(player);
        }
    }
}