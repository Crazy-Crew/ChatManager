package com.ryderbelserion.chatmanager.commands.types.basic;

import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.chatmanager.enums.commands.ListType;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.List;

public class CommandList extends AnnotationFeature {

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command("chatmanager staff")
    @CommandDescription("Shows a list of online staff!")
    @Permission(value = "chatmanager.staff", mode = Permission.Mode.ANY_OF)
    public void staff(final CommandSender sender) {
        list(sender, ListType.STAFF_LIST);
    }

    @Command("chatmanager list <type>")
    @CommandDescription("Shows a list of online players or staff!")
    @Permission(value = "chatmanager.list", mode = Permission.Mode.ANY_OF)
    public void list(final CommandSender sender, @Argument("type") final ListType type) {
        final FileConfiguration config = Files.CONFIG.getConfiguration();

        final List<String> list = type == ListType.PLAYER_LIST ? config.getStringList("Lists.Player_List") : config.getStringList("Lists.Staff_List");

        if (list.isEmpty()) {
            return;
        }

        final String node = type == ListType.PLAYER_LIST ? Permissions.COMMAND_LISTS_PLAYERS.getNode() : Permissions.COMMAND_LISTS_STAFF.getNode();

        if (!sender.hasPermission(node)) {
            Messages.NO_PERMISSION.sendMessage(sender);

            return;
        }

        final Collection<? extends Player> players = this.server.getOnlinePlayers();
        final String online = String.valueOf(players.size());
        final String max = String.valueOf(this.server.getMaxPlayers());

        final StringBuilder builder = new StringBuilder();

        switch (type) {
            case PLAYER_LIST -> {
                for (final Player player : players) {
                    if (!builder.isEmpty()) builder.append(", ");

                    builder.append("&a").append(player.getName()).append("&8");
                }
            }

            case STAFF_LIST -> {
                final String staff_node = Permissions.COMMAND_STAFF.getNode();

                for (final Player player : players) {
                    if (!player.hasPermission(staff_node) || !player.isOp()) continue;

                    if (!builder.isEmpty()) builder.append(", ");

                    builder.append("&a").append(player.getName()).append("&8");
                }
            }
        }

        for (final String line : list) {
            Methods.sendMessage(sender, line.replace("{staff}", builder.toString()).replace("{server_online}", online).replace("{server_max_players}", max), true);
        }
    }
}