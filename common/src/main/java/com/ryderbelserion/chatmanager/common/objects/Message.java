package com.ryderbelserion.chatmanager.common.objects;

import com.ryderbelserion.chatmanager.api.ChatManagerProvider;
import com.ryderbelserion.chatmanager.api.interfaces.IMessage;
import com.ryderbelserion.chatmanager.common.ChatManager;
import com.ryderbelserion.chatmanager.common.enums.Files;
import com.ryderbelserion.chatmanager.common.registry.UserRegistry;
import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.api.exceptions.FusionException;
import com.ryderbelserion.fusion.kyori.FusionKyori;
import com.ryderbelserion.fusion.kyori.utils.StringUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message implements IMessage {

    private final FusionKyori kyori = (FusionKyori) FusionCore.Provider.get();

    private final ChatManager chatManager = (ChatManager) ChatManagerProvider.getInstance();
    private final CommentedConfigurationNode config = Files.config.getConfig();
    private final CommentedConfigurationNode messages = Files.messages.getConfig();

    private final UserRegistry userRegistry;

    private final String defaultValue;
    private final Object[] path;

    public Message(@NotNull final UserRegistry userRegistry, @NotNull final String defaultValue, @NotNull final Object... path) {
        this.userRegistry = userRegistry;

        // config data
        this.defaultValue = defaultValue;
        this.path = path;
    }

    @Override
    public void broadcast(@NotNull final Audience audience, @NotNull final Map<String, String> placeholders) {
        final Component component = getComponent(audience, placeholders);

        if (component.equals(Component.empty())) return;

        this.chatManager.broadcast(component);
    }

    @Override
    public void broadcast(@NotNull final Audience audience) {
        broadcast(audience, new HashMap<>());
    }

    @Override
    public void send(@NotNull final Audience audience, @NotNull final Map<String, String> placeholders) {
        final Component component = getComponent(audience, placeholders);

        if (component.equals(Component.empty())) return;

        switch (this.config.node("root", "message-action").getString("send_message")) {
            case "send_actionbar" -> audience.sendActionBar(component);
            case "send_message" -> audience.sendMessage(component);
        }
    }

    @Override
    public void send(@NotNull final Audience audience) {
        send(audience, new HashMap<>());
    }

    @Override
    public Component getComponent(@NotNull final Audience audience, @NotNull final Map<String, String> placeholders) {
        if (this.chatManager.isConsoleSender(audience)) {
            final CommentedConfigurationNode config = this.messages.node(this.path);

            return parse(config.isList() ? StringUtils.toString(getStringList(config)) : config.getString(this.defaultValue), audience, placeholders);
        }

        final User user = this.userRegistry.getUser(audience);

        final CommentedConfigurationNode config = user.locale().node(this.path);

        return parse(config.isList() ? StringUtils.toString(getStringList(config)) : config.getString(this.defaultValue), audience, placeholders);
    }

    @Override
    public Component getComponent(@NotNull final Audience audience) {
        return getComponent(audience, new HashMap<>());
    }

    private @NotNull Component parse(@NotNull final String message, @NotNull final Audience audience, @NotNull final Map<String, String> placeholders) {
        placeholders.putIfAbsent("{prefix}", this.config.node("root", "prefix").getString("<blue>[<gold>ChatManager<blue>] <reset>"));

        return this.kyori.color(audience, message, placeholders);
    }

    private @NotNull List<String> getStringList(@NotNull final CommentedConfigurationNode node) {
        try {
            final List<String> list = node.getList(String.class);

            return list != null ? list : List.of(this.defaultValue);
        } catch (SerializationException exception) {
            throw new FusionException(String.format("Failed to serialize %s!", node.path()), exception);
        }
    }
}