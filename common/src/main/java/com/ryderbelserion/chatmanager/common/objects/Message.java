package com.ryderbelserion.chatmanager.common.objects;

import com.ryderbelserion.chatmanager.api.interfaces.IMessage;
import com.ryderbelserion.chatmanager.api.interfaces.platform.IChatManager;
import com.ryderbelserion.chatmanager.common.enums.Files;
import com.ryderbelserion.fusion.core.api.FusionProvider;
import com.ryderbelserion.fusion.core.utils.StringUtils;
import com.ryderbelserion.fusion.kyori.FusionKyori;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message implements IMessage {

    private final CommentedConfigurationNode customFile = Files.config.getYamlConfig();
    private final FusionKyori fusion;

    private final String defaultValue;
    private final String value;

    public Message(@NotNull final FusionKyori fusion, @NotNull final CommentedConfigurationNode configuration, @NotNull final String defaultValue, @NotNull final Object... path) {
        this.fusion = fusion;

        this.defaultValue = defaultValue;

        final CommentedConfigurationNode root = configuration.node(path);

        this.value = root.isList() ? StringUtils.toString(getStringList(root)) : root.getString(this.defaultValue); // store pre-fetch the value from the default Messages.yml
    }

    @Override
    public void broadcast(@NotNull final IChatManager instance, @NotNull final Audience audience, @NotNull final Map<String, String> placeholders) {
        final Component component = getComponent(audience, placeholders);

        if (component.equals(Component.empty())) return;

        instance.broadcast(component);
    }

    @Override
    public void send(@NotNull final Audience audience, @NotNull final Map<String, String> placeholders) {
        final Component component = getComponent(audience, placeholders);

        if (component.equals(Component.empty())) return;

        switch (this.customFile.node("message-action").getString("send_message")) {
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
        return parse(this.value, audience, placeholders);
    }

    @Override
    public Component getComponent(@NotNull final Audience audience) {
        return getComponent(audience, new HashMap<>());
    }

    @Override
    public final String getString(@NotNull final Audience audience) {
        return this.fusion.papi(audience, this.value);
    }

    private @NotNull Component parse(@NotNull final String message, @NotNull final Audience audience, @NotNull final Map<String, String> placeholders) {
        final Map<String, String> map = new HashMap<>(placeholders);

        final String prefix = this.customFile.node("root", "prefix").getString("<dark_gray>[<red>Core<white>Craft<dark_gray>] <reset>");

        if (!prefix.isEmpty()) {
            map.putIfAbsent("{prefix}", prefix);
        }

        return this.fusion.parse(audience, message, map);
    }

    private @NotNull List<String> getStringList(@NotNull final CommentedConfigurationNode node) {
        try {
            final List<String> list = node.getList(String.class);

            return list != null ? list : List.of(this.defaultValue);
        } catch (SerializationException exception) {
            throw new com.ryderbelserion.fusion.core.api.exceptions.FusionException(String.format("Failed to serialize %s!", node.path()), exception);
        }
    }
}