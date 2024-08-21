package com.ryderbelserion.chatmanager.commands.subs.simple;

import ch.jalu.configme.SettingsManager;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.types.rules.RuleKeys;
import com.ryderbelserion.vital.paper.api.commands.Command;
import com.ryderbelserion.vital.paper.api.commands.CommandData;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.Server;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import static io.papermc.paper.command.brigadier.Commands.argument;

public class CommandRules extends Command {

    private final ChatManager plugin = ChatManager.get();
    private final Server server = this.plugin.getServer();

    private final SettingsManager rules = ConfigManager.getRules();

    @Override
    public void execute(final CommandData data) {
        final int page = data.getIntegerArgument("page");

        this.rules.getProperty(RuleKeys.rules).getRules().get(page).forEach(line -> Methods.sendMessage(data.getCommandSender(), line, true));
    }

    @Override
    public @NotNull final String getPermission() {
        return "chatmanager.rules";
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("rules").requires(source -> source.getSender().hasPermission(getPermission()));

        final RequiredArgumentBuilder<CommandSourceStack, Integer> arg1 = argument("min", IntegerArgumentType.integer()).suggests((ctx, builder) -> {
            this.rules.getProperty(RuleKeys.rules).getRules().keySet().forEach(builder::suggest);

            return builder.buildFuture();
        }).executes(context -> {
            execute(new CommandData(context));

            return com.mojang.brigadier.Command.SINGLE_SUCCESS;
        });

        return root.then(arg1).build();
    }

    @Override
    public @NotNull final Command registerPermission() {
        final Permission permission = this.server.getPluginManager().getPermission(getPermission());

        if (permission == null) {
            this.server.getPluginManager().addPermission(new Permission(getPermission(), PermissionDefault.OP));
        }

        return this;
    }
}