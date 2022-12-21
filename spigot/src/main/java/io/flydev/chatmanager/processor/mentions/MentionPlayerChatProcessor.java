package io.flydev.chatmanager.processor.mentions;

import io.flydev.chatmanager.processor.ChatProcessor;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.configuration.ConfigurationKey;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import me.h1dd3nxn1nja.chatmanager.utils.JSONMessage;
import me.h1dd3nxn1nja.chatmanager.utils.ServerProtocol;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Niels Warrens - 21/12/2022
 */
public class MentionPlayerChatProcessor implements ChatProcessor {

    private final PlaceholderManager placeholderManager;

    public MentionPlayerChatProcessor() {
        this.placeholderManager = ChatManager.getPlugin().getCrazyManager().getPlaceholderManager();
    }

    @Override
    public void process(AsyncPlayerChatEvent event) {
        // Check if the player has the permissions to mention players
        if (!event.getPlayer().hasPermission("chatmanager.mention")) return;

        String eventMessage = event.getMessage();

        // Get the configuration keys
        String mentionSymbol = ConfigurationKey.MENTIONS_TAG_SYMBOL.getValue(String.class),
                mentionColor = ConfigurationKey.MENTIONS_TAG_COLOR.getValue(String.class);

        // Find all the players that are mentioned
        Collection<Player> eligiblePlayers = event.getRecipients().stream()
                // Make sure the recipient has not disabled mentions
                .filter(player -> !Methods.cm_toggleMentions.contains(player.getUniqueId()))
                // Make sure the recipient has not disabled chat
                .filter(player -> !Methods.cm_toggleChat.contains(player.getUniqueId()))
                // Make sure the mention is completed
                .filter(player -> eventMessage.contains(mentionSymbol + player.getName()))
                .collect(Collectors.toSet());

        // For all the mentions, replace the mention with the colored mention
        for (Player player : eligiblePlayers) {
            String partBeforeMention = eventMessage.substring(0, eventMessage.indexOf(mentionSymbol + player.getName()));

            event.setMessage(eventMessage.replace(mentionSymbol + player.getName(),
                    ChatColor.translateAlternateColorCodes('&',
                            mentionColor + mentionSymbol + player.getName())
                            + ChatColor.RESET + ChatColor.getLastColors(partBeforeMention)));

            // Play the mention sound
            String mentionSound = ConfigurationKey.MENTIONS_SOUND.getValue(String.class);
            if (!mentionSound.isEmpty()) player.playSound(player.getLocation(), Sound.valueOf(mentionSound),
                    10, 1);

            // Construct the title
            String titleHeader = ChatColor.translateAlternateColorCodes('&',
                    this.placeholderManager.setPlaceholders(player, ConfigurationKey.MENTIONS_TITLE_HEADER.getValue(String.class)));
            String titleFooter = ChatColor.translateAlternateColorCodes('&',
                    this.placeholderManager.setPlaceholders(player, ConfigurationKey.MENTIONS_TITLE_FOOTER.getValue(String.class)));

            // Send the title
            if ((ServerProtocol.isAtLeast(ServerProtocol.v1_16_R1))) {
                player.sendTitle(titleHeader, titleFooter, 40, 20, 40);
            } else {
                JSONMessage.create(titleHeader).title(40, 20, 40, player);
                JSONMessage.create(titleFooter).subtitle(player);
            }
        }
    }

}
