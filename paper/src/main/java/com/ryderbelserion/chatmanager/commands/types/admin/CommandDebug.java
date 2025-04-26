package com.ryderbelserion.chatmanager.commands.types.admin;

import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.commands.DebugType;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.utils.Debug;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;

public class CommandDebug extends AnnotationFeature {

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command("chatmanager debug <type>")
    @CommandDescription("Debugs the plugin!")
    @Permission(value = "chatmanager.debug", mode = Permission.Mode.ANY_OF)
    public void debug(final CommandSender sender, @Argument("type") final @NotNull DebugType type) {
        switch (type) {
            case AUTO_BROADCAST -> {
                Methods.sendMessage(sender, "&7Debugging autobroadcast, Please go to your console to see the debug log.", true);

                Debug.debugAutoBroadcast();
            }

            case CONFIG -> {
                Methods.sendMessage(sender, "&7Debugging config, Please go to your console to see the debug log.", true);

                Debug.debugConfig();
            }

            case ALL -> {
                Methods.sendMessage(sender, "&7Debugging all configuration files, Please go to your console to see the debug low.", true);

                Debug.debugAutoBroadcast();
                Debug.debugConfig();
            }
        }
    }
}