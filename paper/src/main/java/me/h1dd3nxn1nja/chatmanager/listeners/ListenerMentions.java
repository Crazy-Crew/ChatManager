package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.EssentialsSupport;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import me.h1dd3nxn1nja.chatmanager.support.PluginSupport;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

public class ListenerMentions extends Global implements Listener {

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

		this.server.getOnlinePlayers().forEach(target -> {
			if (!player.hasPermission(Permissions.MENTION.getNode()) || !target.hasPermission(Permissions.RECEIVE_MENTION.getNode())) return;

			if (!event.getMessage().contains(tagSymbol + target.getName())) return;

			if (this.toggleMentionsData.containsUser(target.getUniqueId())) return;

			if (PluginSupport.ESSENTIALS.isPluginEnabled()) {
				if (essentialsSupport.isIgnored(target.getUniqueId(), player.getUniqueId()) || essentialsSupport.isMuted(player.getUniqueId())) return;
			}

			if (this.toggleChatData.containsUser(target.getUniqueId())) return;

			if (config.getBoolean("Chat_Radius.Enable", false)) {
				if ((!Methods.inRange(target.getUniqueId(), player.getUniqueId(), config.getInt("Chat_Radius.Block_Distance", 250))) || (!Methods.inWorld(target.getUniqueId(), player.getUniqueId()))) return;
			}

			if (!this.toggleMentionsData.containsUser(target.getUniqueId())) {
				final String path = "Mentions.sound";
				Methods.playSound(target, config, path);
			}

			if (config.getBoolean("Mentions.Title.Enable", false)) {
				target.sendTitle(
						Methods.placeholders(false, player, Methods.color(config.getString("Mentions.Title.Header", "&cMentioned"))),
						Methods.placeholders(false, player, Methods.color(config.getString("Mentions.Title.Footer", "&7You have been mentioned by {player}"))), 40, 20, 40);
			}

			if (!config.getString("Mentions.Mention_Color", "").equalsIgnoreCase("")) {
				final String modifiedMessage = message.replaceAll("@\\S+", Methods.color(mentionColor + "$0"));

				event.setMessage(modifiedMessage);
			}
		});

		if (event.getMessage().toLowerCase().contains(tagSymbol + "everyone")) {
			this.server.getOnlinePlayers().forEach(target -> {
				// We don't need to ping ourselves.
				if (player.getUniqueId() == target.getUniqueId()) return;

				if (player.hasPermission(Permissions.MENTION_EVERYONE.getNode()) && target.hasPermission(Permissions.RECEIVE_MENTION.getNode())) {
					if (!this.toggleMentionsData.containsUser(target.getUniqueId())) {
						String path = "Mentions.sound";
						Methods.playSound(target, config, path);
					}

					if (config.getBoolean("Mentions.Title.Enable", false)) {
						target.sendTitle(
								Methods.placeholders(false, player, Methods.color(config.getString("Mentions.Title.Header", "&cMentioned"))),
										Methods.placeholders(false, player, Methods.color(config.getString("Mentions.Title.Footer", "&7You have been mentioned by {player}"))), 40, 20, 40);
					}

					if (!config.getString("Mentions.Mention_Color", "").isBlank()) {
						String modifiedMessage = message.replaceAll("@\\S+", Methods.color(mentionColor + "$0"));

						event.setMessage(modifiedMessage);
					}
				}
			});
		}
	}
}