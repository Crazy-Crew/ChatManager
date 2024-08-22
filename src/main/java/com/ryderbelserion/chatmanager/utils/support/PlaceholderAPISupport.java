package com.ryderbelserion.chatmanager.utils.support;

import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.vital.common.api.interfaces.IPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceholderAPISupport implements IPlugin {

    private final ChatManager plugin = ChatManager.get();

    private Expansion expansion;

    @Override
    public void init() {
        if (!isEnabled()) return;

        this.expansion = new Expansion();
        this.expansion.register();
    }

    @Override
    public void stop() {
        if (!isEnabled() || this.expansion == null) return;

        this.expansion.unregister();
    }

    @Override
    public @NotNull final String getName() {
        return "PlaceholderAPI";
    }

    @Override
    public final boolean isEnabled() {
        return this.plugin.getServer().getPluginManager().isPluginEnabled(getName());
    }

    @SuppressWarnings("deprecation")
    public static class Expansion extends PlaceholderExpansion {

        private final ChatManager plugin = ChatManager.get();

        @Override
        public @Nullable String onRequest(final OfflinePlayer player, @NotNull final String identifier) {
            /*if (player.isOnline()) {
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
            }*/

            return "";
        }

        @Override
        public @NotNull final String getIdentifier() {
            return this.plugin.getName().toLowerCase();
        }

        @Override
        public @NotNull final String getAuthor() {
            return this.plugin.getDescription().getAuthors().getFirst();
        }

        @Override
        public @NotNull final String getVersion() {
            return this.plugin.getDescription().getVersion();
        }
    }
}