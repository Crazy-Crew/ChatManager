package com.ryderbelserion.chatmanager.common.registry;

import com.ryderbelserion.chatmanager.api.interfaces.IMessage;
import com.ryderbelserion.chatmanager.api.interfaces.registry.IMessageRegistry;
import com.ryderbelserion.chatmanager.common.constants.MessageKeys;
import com.ryderbelserion.chatmanager.common.objects.Message;
import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.api.interfaces.ILogger;
import com.ryderbelserion.fusion.kyori.FusionKyori;
import com.ryderbelserion.fusion.kyori.utils.StringUtils;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageRegistry implements IMessageRegistry {

    private final FusionKyori kyori = (FusionKyori) FusionCore.Provider.get();

    private final ILogger logger = this.kyori.getLogger();

    private final Map<Key, IMessage> messages = new HashMap<>();

    private final UserRegistry userRegistry;

    public MessageRegistry(@NotNull final UserRegistry userRegistry) {
        this.userRegistry = userRegistry;
    }

    @Override
    public void addMessage(@NotNull final Key key, @NotNull final IMessage message) {
        this.logger.safe("Registering the message {}", key.asString());

        this.messages.put(key, message);
    }

    @Override
    public void removeMessage(@NotNull final Key key) {
        if (!this.messages.containsKey(key)) {
            this.logger.warn("No message with key {}", key.asString());

            return;
        }

        this.logger.safe("Unregistering the message {}", key.asString());

        this.messages.remove(key);
    }

    @Override
    public @NotNull final IMessage getMessage(@NotNull final Key key) {
        return this.messages.get(key);
    }

    @Override
    public void init() {
        this.messages.clear();

        addMessage(MessageKeys.reload_plugin, new Message(this.userRegistry, "{prefix}<yellow>You have reloaded the plugin!", "messages", "reload-plugin"));
        addMessage(MessageKeys.feature_disabled, new Message(this.userRegistry, "{prefix}<red>This feature is disabled.", "messages", "feature-disabled"));
        addMessage(MessageKeys.must_be_console_sender, new Message(this.userRegistry, "{prefix}<red>You must be using console to use this command.", "messages", "must_be_console_sender"));
        addMessage(MessageKeys.must_be_player, new Message(this.userRegistry, "{prefix}<red>You must be a player to use this command.", "messages", "must_be_player"));
        addMessage(MessageKeys.target_not_online, new Message(this.userRegistry, "{prefix}<red>This feature is disabled.", "messages", "target_not_online"));
        addMessage(MessageKeys.target_same_player, new Message(this.userRegistry, "{prefix}<red>You cannot use this command on yourself.", "messages", "target_same_player"));
        addMessage(MessageKeys.no_permission, new Message(this.userRegistry, "{prefix}<red>You do not have permission to use that command!", "messages", "no_permission"));
        addMessage(MessageKeys.inventory_not_empty, new Message(this.userRegistry, "{prefix}<red>Inventory is not empty, Please clear up some room.", "messages", "inventory_not_empty"));

        addMessage(MessageKeys.join_message, new Message(this.userRegistry, " <dark_gray>[<green>+</green>]</dark_gray> {player}", "messages", "traffic", "join_message"));
        addMessage(MessageKeys.quit_message, new Message(this.userRegistry, " <dark_gray>[<red>-</red>]</dark_gray> {player}", "messages", "traffic", "quit_message"));

        addMessage(MessageKeys.message_of_the_day, new Message(this.userRegistry, StringUtils.toString(List.of(
                "<gray>------------------------------------",
                "",
                "<green>Welcome to the server <blue>{player}</blue>!",
                "",
                "<green>If you need any help, Please message online staff!",
                "",
                "<green>You can change this message in the messages.yml or the locale folder.",
                "<gray>------------------------------------"
        )), "messages", "motd"));
    }
}