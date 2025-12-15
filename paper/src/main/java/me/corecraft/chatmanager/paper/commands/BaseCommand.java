package me.corecraft.chatmanager.paper.commands;

import com.ryderbelserion.chatmanager.common.constants.Messages;
import com.ryderbelserion.chatmanager.common.registry.MessageRegistry;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.corecraft.chatmanager.paper.ChatManagerPlatform;
import me.corecraft.chatmanager.paper.commands.admin.CommandReload;
import me.corecraft.chatmanager.paper.commands.player.CommandMotd;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.injection.ParameterInjectorRegistry;
import org.incendo.cloud.paper.PaperCommandManager;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class BaseCommand {

    protected final ChatManager plugin = ChatManager.get();

    protected final ChatManagerPlatform platform = this.plugin.getPlatform();

    protected final MessageRegistry registry = this.platform.getMessageRegistry();

    private final AnnotationParser<CommandSourceStack> parser;

    public BaseCommand(@NotNull final PaperCommandManager<CommandSourceStack> manager) {
        final ParameterInjectorRegistry<CommandSourceStack> injector = manager.parameterInjectorRegistry();

        injector.registerInjector(CommandSender.class, (context, accessor) -> context.sender().getSender());
        injector.registerInjector(Player.class, (context, accessor) -> {
            final CommandSender sender = context.sender().getSender();

            if (sender instanceof Player player) {
                return player;
            }

            this.registry.getMessage(Messages.must_be_player).send(sender);

            return null;
        });

        this.parser = new AnnotationParser<>(manager, CommandSourceStack.class);

        register();
    }

    private void register() { // register our universal commands
        List.of(
                new CommandReload(),

                new CommandMotd()
        ).forEach(command -> command.registerFeature(this.parser));
    }

    public @NotNull final AnnotationParser<CommandSourceStack> getParser() {
        return this.parser;
    }
}