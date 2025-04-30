package com.ryderbelserion.chatmanager.plugins.papi;

import com.ryderbelserion.chatmanager.api.objects.PaperServer;
import com.ryderbelserion.chatmanager.api.objects.PaperUser;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.core.PlayerState;
import com.ryderbelserion.chatmanager.enums.core.ServerState;
import com.ryderbelserion.chatmanager.managers.ServerManager;
import com.ryderbelserion.chatmanager.managers.UserManager;
import io.papermc.paper.plugin.configuration.PluginMeta;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class PlaceholderAPIExpansion extends PlaceholderExpansion {

    private final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

    private final PluginMeta pluginMeta = this.plugin.getPluginMeta();

    private final ServerManager serverManager = this.plugin.getServerManager();

    private final UserManager userManager = this.plugin.getUserManager();

    @Override
    public @Nullable String onRequest(@NotNull final OfflinePlayer player, @NotNull final String identifier) {
        if (!player.isOnline()) return "N/A";

        final UUID uuid = player.getUniqueId();

        final Optional<PaperUser> user = this.userManager.getUser(uuid);

        if (user.isEmpty()) return "N/A";

        final PaperUser output = user.get();

        return switch (identifier.toLowerCase()) {
            case "direct_messages" -> !output.hasState(PlayerState.DIRECT_MESSAGES) ? "Enabled" : "Disabled";
            case "command_spy" -> output.hasState(PlayerState.COMMAND_SPY) ? "Enabled" : "Disabled";
            case "social_spy" -> output.hasState(PlayerState.SOCIAL_SPY) ? "Enabled" : "Disabled";
            case "staff_chat" -> output.hasState(PlayerState.STAFF_CHAT) ? "Enabled" : "Disabled";
            case "mute_chat" -> {
                final PaperServer server = this.serverManager.getServer();

                yield server.hasState(ServerState.MUTED) ? "Enabled" : "Disabled";
            }
            case "radius" -> {
                final FileConfiguration config = Files.CONFIG.getConfiguration();

                if (this.plugin.api().getLocalChatData().containsUser(player.getUniqueId())) {
                    yield config.getString("Chat_Radius.Local_Chat.Prefix", "Local chat section not found in config.yml");
                } else if (this.plugin.api().getGlobalChatData().containsUser(player.getUniqueId())) {
                    yield config.getString("Chat_Radius.Global_Chat.Prefix", "Global chat section not found in config.yml");
                } else if (this.plugin.api().getWorldChatData().containsUser(player.getUniqueId())) {
                    yield config.getString("Chat_Radius.World_Chat.Prefix", "World chat section not found in config.yml");
                }

                yield "N/A";
            }
            case "chat" -> !output.hasState(PlayerState.CHAT) ? "Enabled" : "Disabled";
            default -> "N/A";
        };
    }

    @Override
    public @NotNull String getIdentifier() {
        return this.pluginMeta.getName();
    }

    @Override
    public @NotNull String getAuthor() {
        return this.pluginMeta.getAuthors().toString();
    }


    @Override
    public @NotNull String getVersion() {
        return this.pluginMeta.getVersion();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }
}