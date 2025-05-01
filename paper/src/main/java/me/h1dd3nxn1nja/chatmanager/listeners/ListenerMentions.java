package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.cmds.ToggleChatData;
import com.ryderbelserion.chatmanager.api.cmds.ToggleMentionsData;
import com.ryderbelserion.chatmanager.enums.Files;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.support.EssentialsSupport;
import me.h1dd3nxn1nja.chatmanager.support.PluginSupport;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.UUID;

public class ListenerMentions implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	private final Server server = this.plugin.getServer();

	private final ApiLoader api = this.plugin.api();

	private final ToggleChatData toggleChatData = this.api.getToggleChatData();

	private final ToggleMentionsData toggleMentionsData = this.api.getToggleMentionsData();

	@NotNull
	private final EssentialsSupport essentialsSupport = this.plugin.getPluginManager().getEssentialsSupport();

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();

		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final String tagSymbol = config.getString("Mentions.Tag_Symbol", "@");
		final String mentionColor = config.getString("Mentions.Mention_Color", "");

		if (!config.getBoolean("Mentions.Enable", false)) return;

		final String message = event.getMessage();
		
		final Collection<? extends Player> players = this.server.getOnlinePlayers();

		final UUID playerId = player.getUniqueId();

		final boolean isMentionsEnabled = config.getBoolean("Mentions.Title.Enable", false);

		String title;
		String footer;

		if (isMentionsEnabled) {
			title = Methods.placeholders(false, player, Methods.color(config.getString("Mentions.Title.Header", "&cMentioned")));
			footer = Methods.placeholders(false, player, Methods.color(config.getString("Mentions.Title.Footer", "&7You have been mentioned by {player}")));
		} else {
            footer = "";
            title = "";
        }

		if (!message.contains(tagSymbol)) {
			return;
		}

		final String[] mentions = message.split(tagSymbol);

		final String state = mentions[1];

        if (state.equalsIgnoreCase("everyone")) {
			players.forEach(target -> {
				final UUID uuid = target.getUniqueId();

				// We don't need to ping ourselves.
				if (player.getUniqueId() == uuid) return;

				if (player.hasPermission(Permissions.MENTION_EVERYONE.getNode()) && target.hasPermission(Permissions.RECEIVE_MENTION.getNode())) {
					if (!this.toggleMentionsData.containsUser(uuid)) {
						Methods.playSound(target, config, "Mentions.sound");
					}

					if (!title.isEmpty() && !footer.isEmpty()) {
						target.sendTitle(title, footer, 40, 20, 40);
					}

					if (!config.getString("Mentions.Mention_Color", "").isBlank()) {
						final String modifiedMessage = message.replaceAll("@\\S+", Methods.color(mentionColor + "$0"));

						event.setMessage(modifiedMessage);
					}
				}
			});

			return;
        }

		final Player target = this.server.getPlayer(mentions[1]);

		if (target == null) {
			return;
		}

		final UUID targetId = target.getUniqueId();

		if (this.toggleMentionsData.containsUser(targetId) || this.toggleChatData.containsUser(targetId)) return;

		if (PluginSupport.ESSENTIALS.isPluginEnabled()) {
			if (this.essentialsSupport.isIgnored(targetId, playerId) || this.essentialsSupport.isMuted(playerId))
				return;
		}

		if (!player.hasPermission(Permissions.MENTION.getNode()) || !target.hasPermission(Permissions.RECEIVE_MENTION.getNode()))
			return;

		if (config.getBoolean("Chat_Radius.Enable", false)) {
			if ((!Methods.inRange(targetId, playerId, config.getInt("Chat_Radius.Block_Distance", 250))) || (!Methods.inWorld(targetId, playerId)))
				return;
		}

		if (!this.toggleMentionsData.containsUser(targetId)) {
			Methods.playSound(target, config, "Mentions.sound");
		}

		if (!title.isEmpty() && !footer.isEmpty()) {
			target.sendTitle(title, footer, 40, 20, 40);
		}

		if (!config.getString("Mentions.Mention_Color", "").equalsIgnoreCase("")) {
			final String modifiedMessage = message.replaceAll("@\\S+", Methods.color(mentionColor + "$0"));

			event.setMessage(modifiedMessage);
		}
	}
}