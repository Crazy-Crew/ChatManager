package com.ryderbelserion.chatmanager.commands.types.chat;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.chat.GlobalChatData;
import com.ryderbelserion.chatmanager.api.chat.LocalChatData;
import com.ryderbelserion.chatmanager.api.chat.SpyChatData;
import com.ryderbelserion.chatmanager.api.chat.WorldChatData;
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
import java.util.UUID;

public class CommandRadius extends AnnotationFeature {

    private final ApiLoader api = this.plugin.api();

    private final LocalChatData local = this.api.getLocalChatData();

    private final GlobalChatData global = this.api.getGlobalChatData();

    private final WorldChatData world = this.api.getWorldChatData();

    private final SpyChatData spy = this.api.getSpyChatData();

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

        switch (type) {
            case GLOBAL_CHAT -> {
                if (this.global.containsUser(uuid)) {
                    Messages.CHAT_RADIUS_GLOBAL_CHAT_ALREADY_ENABLED.sendMessage(player);

                    return;
                }

                this.local.removeUser(uuid);
                this.world.removeUser(uuid);
                this.global.addUser(uuid);

                Messages.CHAT_RADIUS_GLOBAL_CHAT_ENABLED.sendMessage(player);
            }

            case WORLD_CHAT -> {
                if (this.world.containsUser(uuid)) {
                    Messages.CHAT_RADIUS_WORLD_CHAT_ALREADY_ENABLED.sendMessage(player);

                    return;
                }

                this.global.removeUser(uuid);
                this.local.removeUser(uuid);
                this.world.addUser(uuid);

                Messages.CHAT_RADIUS_WORLD_CHAT_ENABLED.sendMessage(player);
            }

            case LOCAL_CHAT -> {
                if (this.local.containsUser(uuid)) {
                    Messages.CHAT_RADIUS_LOCAL_CHAT_ALREADY_ENABLED.sendMessage(player);

                    return;
                }

                this.global.removeUser(uuid);
                this.world.removeUser(uuid);
                this.local.addUser(uuid);

                Messages.CHAT_RADIUS_LOCAL_CHAT_ENABLED.sendMessage(player);
            }

            case SPY_CHAT -> {
                if (this.spy.containsUser(uuid)) {
                    this.spy.removeUser(uuid);

                    Messages.CHAT_RADIUS_SPY_DISABLED.sendMessage(player);

                    return;
                }

                this.spy.addUser(uuid);

                Messages.CHAT_RADIUS_SPY_ENABLED.sendMessage(player);
            }
        }
    }
}