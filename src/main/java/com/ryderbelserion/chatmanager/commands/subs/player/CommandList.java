package com.ryderbelserion.chatmanager.commands.subs.player;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.commands.AbstractCommand;
import com.ryderbelserion.vital.paper.commands.context.PaperCommandInfo;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandList extends AbstractCommand {

    @Override
    public void execute(final PaperCommandInfo info) {
        final CommandSender sender = info.getCommandSender();

        /*final Map<String, List<String>> map = this.config.getProperty(ConfigKeys.list).getEntry();

        final Map<String, StringBuilder> players = new HashMap<>();

        this.plugin.getServer().getOnlinePlayers().forEach(player -> {
            if (Support.vault.isEnabled()) {
                final String group = VaultSupport.getPlayerGroup(player);

                map.forEach((key, groups) -> {
                    if (groups.contains(group)) {
                        if (!players.containsKey(group)) {
                            players.put(group, new StringBuilder().append(", ").append(player.getName()));
                        } else {
                            players.get(group).append(", ").append(player.getName());
                        }
                    }
                });
            }
        });

        final StringBuilder builder = new StringBuilder();

        if (Support.vault.isEnabled()) {

        }

        List.of(
                "<gray>---------------<dark_gray>[ <green>Player List <dark_gray>]<gray>---------------",
                "",
                "{groups}",
                "",
                "",
                "",
                "",
                "<gray>--------------------------------------------------------------------------------"
        ).forEach(line -> {


            final String key = line
                    .replace("{players}", "");

            MsgUtils.sendMessage(sender, line);
        });*/
    }

    @Override
    public @NotNull final String getPermission() {
        return Permissions.list.getNode();
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("list").requires(source -> source.getSender().hasPermission(getPermission()));

        return root.executes(context -> {
            execute(new PaperCommandInfo((context)));

            return com.mojang.brigadier.Command.SINGLE_SUCCESS;
        }).build();
    }

    @Override
    public @NotNull final AbstractCommand registerPermission() {
        Permissions.list.registerPermission();

        return this;
    }

    /**
     * Lists:
     *   Staff_List:
     *     - "&7&m---------------&8[ &aStaff List &8]&7&m---------------"
     *     - "&7Online Staff Members&7: {staff}"
     *
     *   Player_List:
     *     - "&7&m---------------&8[ &aPlayer List &8]&7&m---------------"
     *     - "&7Online Players &a{server_online}&8/&a{server_max_players}&7: {players}"
     *
     */
}