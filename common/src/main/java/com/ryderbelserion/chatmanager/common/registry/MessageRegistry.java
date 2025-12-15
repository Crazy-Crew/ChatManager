package com.ryderbelserion.chatmanager.common.registry;

import com.ryderbelserion.chatmanager.api.interfaces.IMessage;
import com.ryderbelserion.chatmanager.api.interfaces.registry.IMessageRegistry;
import com.ryderbelserion.chatmanager.common.ChatManager;
import com.ryderbelserion.chatmanager.common.constants.Messages;
import com.ryderbelserion.chatmanager.common.objects.Message;
import com.ryderbelserion.fusion.core.utils.StringUtils;
import com.ryderbelserion.fusion.files.FileManager;
import com.ryderbelserion.fusion.kyori.FusionKyori;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageRegistry implements IMessageRegistry {

    private final Map<Key, Map<Key, IMessage>> messages = new HashMap<>(); // locale key, (message key, message context)

    private final FileManager fileManager;
    private final FusionKyori fusion;
    private final Path path;

    public MessageRegistry(@NotNull final Path path, @NotNull final FusionKyori fusion) {
        this.fusion = fusion;
        this.fileManager = fusion.getFileManager();
        this.path = path;
    }

    @Override
    public void addMessage(@NotNull final Key locale, @NotNull final Key key, @NotNull final IMessage message) {
        this.fusion.log("info", "Registering the message @ {} for {}", locale.asString(), key.asString());

        final Map<Key, IMessage> keys = this.messages.getOrDefault(locale, new HashMap<>());

        keys.put(key, message);

        this.messages.put(locale, keys);
    }

    @Override
    public void removeMessage(@NotNull final Key key) {
        if (!this.messages.containsKey(key)) {
            this.fusion.log("warn", "No message with key {}", key.asString());

            return;
        }

        this.fusion.log("info", "Unregistering the message {}", key.asString());

        this.messages.remove(key);
    }

    @Override
    public @NotNull final IMessage getMessage(@NotNull final Key locale, @NotNull final Key key) {
        return this.messages.getOrDefault(locale, this.messages.get(Messages.default_locale)).get(key);
    }

    @Override
    public @NotNull final IMessage getMessage(@NotNull final Key key) { // only used for console command sender
        return this.messages.get(Messages.default_locale).get(key);
    }

    public @NotNull final Map<Key, Map<Key, IMessage>> getMessages() {
        return Collections.unmodifiableMap(this.messages);
    }

    @Override
    public void init() {
        this.messages.clear();

        final List<Path> paths = this.fusion.getFiles(this.path.resolve("locale"), ".yml");

        paths.add(this.path.resolve("messages.yml")); // add to list

        for (final Path path : paths) {
            this.fileManager.getYamlFile(path).ifPresentOrElse(file -> {
                final String fileName = file.getFileName();

                final Key key = Key.key(ChatManager.namespace, fileName.equalsIgnoreCase("messages.yml") ? "default" : fileName.toLowerCase());

                final CommentedConfigurationNode configuration = file.getConfiguration();

                addMessage(key, Messages.reload_plugin, new Message(this.fusion, configuration, "{prefix}<yellow>You have reloaded the plugin!", "messages", "reload-plugin"));
                addMessage(key, Messages.feature_disabled, new Message(this.fusion, configuration, "{prefix}<red>This feature is disabled.", "messages", "feature-disabled"));
                addMessage(key, Messages.must_be_console_sender, new Message(this.fusion, configuration, "{prefix}<red>You must be using console to use this command.", "messages", "player", "requirements", "must-be-console-sender"));
                addMessage(key, Messages.must_be_player, new Message(this.fusion, configuration, "{prefix}<red>You must be a player to use this command.", "messages", "player", "requirements", "must-be-player"));
                addMessage(key, Messages.target_not_online, new Message(this.fusion, configuration, "{prefix}<red>This feature is disabled.", "messages", "player", "target-not-online"));
                addMessage(key, Messages.target_same_player, new Message(this.fusion, configuration, "{prefix}<red>You cannot use this command on yourself.", "messages", "player", "target-same-player"));
                addMessage(key, Messages.no_permission, new Message(this.fusion, configuration, "{prefix}<red>You do not have permission to use that command!", "messages", "player", "no-permission"));
                addMessage(key, Messages.inventory_not_empty, new Message(this.fusion, configuration, "{prefix}<red>Inventory is not empty, Please clear up some room.", "messages", "player", "inventory-not-empty"));

                addMessage(key, Messages.join_message, new Message(this.fusion, configuration, " <dark_gray>[<green>+</green>]</dark_gray> {player}", "messages", "traffic", "join-message"));
                addMessage(key, Messages.quit_message, new Message(this.fusion, configuration, " <dark_gray>[<red>-</red>]</dark_gray> {player}", "messages", "traffic", "quit-message"));

                addMessage(key, Messages.message_of_the_day, new Message(this.fusion, configuration, StringUtils.toString(List.of(
                        "<gray>------------------------------------",
                        "",
                        "<green>Welcome to the server <blue>{player}</blue>!",
                        "",
                        "<green>If you need any help, Please message online staff!",
                        "",
                        "<green>You can change this message in the messages.yml or the locale folder.",
                        "<gray>------------------------------------"
                )), "messages", "motd"));
            }, () -> this.fusion.log("warn", "Path %s not found in cache.".formatted(path)));
        }
    }
}