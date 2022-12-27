package io.flydev.chatmanager.processors.mentions;

import io.flydev.chatmanager.processors.FormattedChatProcessor;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.configuration.Configuration;
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
public class MentionPlayerChatProcessor extends FormattedChatProcessor<AsyncPlayerChatEvent> {

	private static final String MENTION_PLAYER_PERMISSION = "chatmanager.mention";

	private final PlaceholderManager placeholderManager;

	public MentionPlayerChatProcessor() {
		this.placeholderManager = ChatManager.getPlugin().getCrazyManager().getPlaceholderManager();
	}

	@Override
	public void process(AsyncPlayerChatEvent event) {
		// Check if the player has the permissions to mention players
		if (!event.getPlayer().hasPermission(MENTION_PLAYER_PERMISSION)) return;

		String eventMessage = event.getMessage();

		// Get the configuration keys
		String mentionSymbol = Configuration.MENTIONS_TAG_SYMBOL.string(),
			mentionColor = Configuration.MENTIONS_TAG_COLOR.string();

		// Find all the players that are mentioned
		Collection<Player> eligiblePlayers = event.getRecipients().stream()
			// Make sure the recipient has not disabled mentions
			.filter(player -> !Methods.cm_toggleMentions.contains(player.getUniqueId()))
			// Make sure the recipient has not disabled chat
			.filter(player -> !Methods.cm_toggleChat.contains(player.getUniqueId()))
			// Make sure the mention is completed
			.filter(player -> eventMessage.contains(mentionSymbol + player.getName())).collect(Collectors.toSet());

		// For all the mentions, replace the mention with the colored mention
		for (Player player : eligiblePlayers) {
			String partBeforeMention = eventMessage.substring(0, eventMessage.indexOf(mentionSymbol + player.getName()));

			event.setMessage(eventMessage.replace(mentionSymbol + player.getName(),
				this.format(player, mentionColor + mentionSymbol + player.getName())
					+ ChatColor.RESET + ChatColor.getLastColors(partBeforeMention)));

			// Play the mention sound
			String mentionSound = Configuration.MENTIONS_SOUND.string();
			if (!mentionSound.isEmpty()) player.playSound(player.getLocation(), Sound.valueOf(mentionSound), 10, 1);

			// Construct the title
			String titleHeader = this.format(player, this.placeholderManager.setPlaceholders(player, Configuration.MENTIONS_TITLE_HEADER.getValue(String.class)));
			String titleFooter = this.format(player, this.placeholderManager.setPlaceholders(player, Configuration.MENTIONS_TITLE_FOOTER.getValue(String.class)));

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
