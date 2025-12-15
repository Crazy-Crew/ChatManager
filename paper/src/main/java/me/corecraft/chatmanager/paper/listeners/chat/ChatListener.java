package me.corecraft.chatmanager.paper.listeners.chat;

import com.ryderbelserion.chatmanager.common.enums.Files;
import com.ryderbelserion.fusion.paper.FusionPaper;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.corecraft.chatmanager.paper.ChatManagerPlatform;
import me.corecraft.chatmanager.paper.listeners.chat.renderers.ChatRender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;

public class ChatListener implements Listener {

    private final FusionPaper fusion;

    public ChatListener(@NotNull final ChatManagerPlatform platform) {
        this.fusion = platform.getFusion();
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerChat(AsyncChatEvent event) {
        final CommentedConfigurationNode config = Files.chat.getYamlConfig();

        if (!config.node("chat", "format", "toggle").getBoolean(false)) return;

        event.renderer(new ChatRender(this.fusion, event.getPlayer(), config.node("chat", "format", "default").getString("%luckperms_prefix% {player} <gold>-> <reset>{message}"), event.signedMessage()));
    }
}