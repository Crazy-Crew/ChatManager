package com.ryderbelserion.chatmanager.commands.types.chat;

import com.ryderbelserion.chatmanager.api.objects.PaperUser;
import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.commands.RadiusType;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;
import java.util.Optional;
import java.util.UUID;

public class CommandRadius extends AnnotationFeature {

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command(value = "chatmanager chatradius <type>", requiredSender = Player.class)
    @CommandDescription("Access the ability to enter different chat modes.")
    @Permission(value = "chatmanager.chatradius", mode = Permission.Mode.ANY_OF)
    public void chatradius(final Player player, @Argument("type") final @NotNull RadiusType type) {
        final UUID uuid = player.getUniqueId();

        if (!type.hasPermission(player)) {
            Messages.NO_PERMISSION.sendMessage(player);

            return;
        }

        final YamlConfiguration configuration = Files.CONFIG.getConfiguration();

        if (configuration.getBoolean("Chat_Radius.Enable", false)) {
            Methods.sendMessage(player, "&cError: Chat Radius is currently disabled. Please enable it in the config.yml", true);

            return;
        }

        final Optional<PaperUser> user = this.userManager.getUser(uuid);

        if (user.isEmpty()) return;

        final PaperUser output = user.get();

        final RadiusType current = output.getRadius();

        if (current == type) {
            Messages.CHAT_RADIUS_DISABLED.sendMessage(player, "{chat-type}", current.getType());

            output.setRadius(RadiusType.GLOBAL_CHAT);

            return;
        }

        output.setRadius(type);

        Messages.CHAT_RADIUS_ENABLED.sendMessage(player, "{chat-type}", current.getType());
    }
}