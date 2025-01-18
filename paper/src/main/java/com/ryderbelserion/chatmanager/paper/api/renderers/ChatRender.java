package com.ryderbelserion.chatmanager.paper.api.renderers;

import com.ryderbelserion.core.FusionProvider;
import io.papermc.paper.chat.ChatRenderer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.chat.SignedMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatRender implements ChatRenderer {

    private final Component renderedMessage;

    public ChatRender(final Player player, final String format, final SignedMessage message) {
        final List<TagResolver> resolvers = new ArrayList<>();

        resolvers.add(StandardTags.defaults());

        this.renderedMessage = FusionProvider.get().placeholders(player, format, new HashMap<>() {{
            put("{player}", player.getName());
            put("{message}", message.message());
        }}, resolvers);
    }

    @Override
    public @NotNull Component render(
            @NotNull Player player,
            @NotNull Component displayName,
            @NotNull Component message,
            @NotNull Audience viewer
    ) {
        return this.renderedMessage;
    }
}