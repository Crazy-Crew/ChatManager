package com.ryderbelserion.chatmanager.plugins;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.vital.paper.plugins.interfaces.Plugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;

public class PlaceholderAPISupport extends PlaceholderExpansion implements Plugin {

    private final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

    @Override
    public boolean isEnabled() {
        return this.plugin.getServer().getPluginManager().isPluginEnabled(getName());
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String identifier) {
        if (player.isOnline()) {
            FileConfiguration config = Files.CONFIG.getConfiguration();
            String lower = identifier.toLowerCase();

            switch (lower) {
                case "radius": //Returns which chat radius channel the player is in.
                    if (this.plugin.api().getLocalChatData().containsUser(player.getUniqueId())) {
                        return config.getString("Chat_Radius.Local_Chat.Prefix");
                    } else if (this.plugin.api().getGlobalChatData().containsUser(player.getUniqueId())) {
                        return config.getString("Chat_Radius.Global_Chat.Prefix");
                    } else if (this.plugin.api().getWorldChatData().containsUser(player.getUniqueId())) {
                        return config.getString("Chat_Radius.World_Chat.Prefix");
                    }
                case "toggle_pm": // Returns if the toggle pm is enabled/disabled for a player.
                    return this.plugin.api().getToggleMessageData().containsUser(player.getUniqueId()) ? "Enabled" : "Disabled";
                case "toggle_chat": // Returns if the toggle chat is enabled/disabled for a player.
                    return this.plugin.api().getToggleChatData().containsUser(player.getUniqueId()) ? "Enabled" : "Disabled";
                case "command_spy": // Returns if the command spy is enabled/disabled for a player.
                    return this.plugin.api().getCommandSpyData().containsUser(player.getUniqueId()) ? "Enabled" : "Disabled";
                case "social_spy": // Returns if the social spy is enabled/disabled for a player.
                    return this.plugin.api().getSocialSpyData().containsUser(player.getUniqueId()) ? "Enabled" : "Disabled";
                case "staff_chat": // Returns if the staff chat is enabled/disabled for a player.
                    return this.plugin.api().getStaffChatData().containsUser(player.getUniqueId()) ? "Enabled" : "Disabled";
                case "mute_chat": // Returns if mute chat is enabled/disabled.
                    return Methods.isMuted() ? "Enabled" : "Disabled";
                default:
                    return "";
            }
        }

        return "";
    }

    @Override
    public @NotNull String getIdentifier() {
        return this.plugin.getName().toLowerCase();
    }

    @Override
    public @NotNull String getAuthor() {
        return this.plugin.getDescription().getAuthors().toString();
    }


    @Override
    public @NotNull String getVersion() {
        return this.plugin.getDescription().getVersion();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public void add() {
        register();
    }

    @Override
    public void remove() {
        unregister();
    }

    @Override
    public @NotNull String getName() {
        return "PlaceholderAPI";
    }
}