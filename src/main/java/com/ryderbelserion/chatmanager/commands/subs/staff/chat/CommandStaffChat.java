package com.ryderbelserion.chatmanager.commands.subs.staff.chat;

import ch.jalu.configme.SettingsManager;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.cache.UserManager;
import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.types.ConfigKeys;
import com.ryderbelserion.vital.paper.api.commands.Command;
import com.ryderbelserion.vital.paper.api.commands.CommandData;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.Server;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import static io.papermc.paper.command.brigadier.Commands.argument;

public class CommandStaffChat extends Command {

    private final ChatManager plugin = ChatManager.get();
    private final UserManager userManager = this.plugin.getUserManager();
    private final Server server = this.plugin.getServer();

    private final SettingsManager config = ConfigManager.getConfig();

    @Override
    public void execute(final CommandData data) {
        if (!this.config.getProperty(ConfigKeys.staff_chat_toggle)) return;

        if (data.isPlayer()) {
            final User user = this.userManager.getUser(data.getPlayer());

            if (user.isStaffChat) {
                user.isStaffChat = false;

                //todo() remove bossbar if it exists.

                Messages.STAFF_CHAT_DISABLED.sendMessage(user.player);

                return;
            }

            user.isStaffChat = true;

            //todo() add boss bar

            if (this.config.getProperty(ConfigKeys.staff_bossbar_toggle)) {
                final String title = this.config.getProperty(ConfigKeys.staff_bossbar_title);

                //todo() show boss bar
            }

            Messages.STAFF_CHAT_ENABLED.sendMessage(user.player);

            return;
        }

        final String message = data.getStringArgument("name");
    }

    @Override
    public @NotNull
    final String getPermission() {
        return "chatmanager.staff.chat";
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("chat").requires(source -> source.getSender().hasPermission(getPermission()));

        final RequiredArgumentBuilder<CommandSourceStack, String> arg1 = argument("name", StringArgumentType.string()).executes(context -> {
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