package me.corecraft.chatmanager.paper.commands.player;

import com.ryderbelserion.chatmanager.common.constants.Messages;
import com.ryderbelserion.chatmanager.common.enums.Files;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.corecraft.chatmanager.paper.commands.AnnotationFeature;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.util.Map;

public class CommandMotd extends AnnotationFeature {

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command("chatmanager motd")
    @CommandDescription("Shows the message of the day!")
    @Permission(value = "chatmanager.motd", mode = Permission.Mode.ALL_OF)
    public void motd(final CommandSender sender) {
        final CommentedConfigurationNode config = Files.config.getYamlConfig();

        if (config.node("root", "motd", "toggle").getBoolean(false)) {
            this.registry.getMessage(Messages.message_of_the_day).send(sender, Map.of(
                    "{player}", sender.getName()
            ));

            return;
        }

        this.registry.getMessage(Messages.feature_disabled).send(sender);
    }
}