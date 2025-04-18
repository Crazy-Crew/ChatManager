package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import me.h1dd3nxn1nja.chatmanager.ChatManagerMercurioMC;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.support.EssentialsSupport;
import me.h1dd3nxn1nja.chatmanager.support.PluginSupport;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.jetbrains.annotations.NotNull;

public class ListenerMentions implements Listener {

	@NotNull
	private final ChatManagerMercurioMC plugin = ChatManagerMercurioMC.get();

	@NotNull
	private final EssentialsSupport essentialsSupport = this.plugin.getPluginManager().getEssentialsSupport();

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		FileConfiguration config = Files.CONFIG.getConfiguration();

		String tagSymbol = config.getString("Mentions.Tag_Symbol", "@");
		String mentionColor = config.getString("Mentions.Mention_Color", "");

		if (!config.getBoolean("Mentions.Enable", false)) return;

		String message = event.getMessage();

		this.plugin.getServer().getOnlinePlayers().forEach(target -> {
			if (!player.hasPermission(Permissions.MENTION.getNode()) || !target.hasPermission(Permissions.RECEIVE_MENTION.getNode())) return;

			if (!event.getMessage().contains(tagSymbol + target.getName())) return;

			if (this.plugin.api().getToggleMentionsData().containsUser(target.getUniqueId())) return;

			if (PluginSupport.ESSENTIALS.isPluginEnabled()) {
				if (essentialsSupport.isIgnored(target.getUniqueId(), player.getUniqueId()) || essentialsSupport.isMuted(player.getUniqueId())) return;
			}

			if (this.plugin.api().getToggleChatData().containsUser(target.getUniqueId())) return;

			if (config.getBoolean("Chat_Radius.Enable", false)) {
				if ((!Methods.inRange(target.getUniqueId(), player.getUniqueId(), config.getInt("Chat_Radius.Block_Distance", 250))) || (!Methods.inWorld(target.getUniqueId(), player.getUniqueId()))) return;
			}

			if (!this.plugin.api().getToggleMentionsData().containsUser(target.getUniqueId())) {
				String path = "Mentions.sound";
				Methods.playSound(target, config, path);
			}

			if (config.getBoolean("Mentions.Title.Enable", false)) {
				target.sendTitle(
						Methods.placeholders(false, player, Methods.color(config.getString("Mentions.Title.Header", "&cMentioned"))),
						Methods.placeholders(false, player, Methods.color(config.getString("Mentions.Title.Footer", "&7You have been mentioned by {player}"))), 40, 20, 40);
			}

			if (!config.getString("Mentions.Mention_Color", "").equalsIgnoreCase("")) {
				String modifiedMessage = message.replaceAll("@\\S+", Methods.color(mentionColor + "$0"));

				event.setMessage(modifiedMessage);
			}
		});

		if (event.getMessage().toLowerCase().contains(tagSymbol + "everyone")) {
			this.plugin.getServer().getOnlinePlayers().forEach(target -> {
				// We don't need to ping ourselves.
				if (player.getUniqueId() == target.getUniqueId()) return;

				if (player.hasPermission(Permissions.MENTION_EVERYONE.getNode()) && target.hasPermission(Permissions.RECEIVE_MENTION.getNode())) {
					if (!this.plugin.api().getToggleMentionsData().containsUser(target.getUniqueId())) {
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