package me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import me.h1dd3nxn1nja.chatmanager.support.PluginManager;
import me.h1dd3nxn1nja.chatmanager.support.PluginSupport;
import me.h1dd3nxn1nja.chatmanager.utils.ServerProtocol;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.EssentialsSupport;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import me.h1dd3nxn1nja.chatmanager.utils.JSONMessage;

import java.util.Objects;

public class ListenerMentions implements Listener {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	private final PlaceholderManager placeholderManager = plugin.getCrazyManager().getPlaceholderManager();

	private final PluginManager pluginManager = plugin.getPluginManager();

	private final EssentialsSupport essentialsSupport = pluginManager.getEssentialsSupport();

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		FileConfiguration config = settingsManager.getConfig();

		Player player = event.getPlayer();

		String tagSymbol = config.getString("Mentions.Tag_Symbol");
		String mentionColor = config.getString("Mentions.Mention_Color");

		if (!config.getBoolean("Mentions.Enable")) return;

		event.setMessage(Methods.color(event.getMessage()));

		plugin.getServer().getOnlinePlayers().forEach(target -> {
			if (player.hasPermission("chatmanager.mention") && target.hasPermission("chatmanager.mention.receive")) {
				if (event.getMessage().contains(tagSymbol + target.getName())) {
					if (Methods.cm_toggleMentions.contains(target.getUniqueId())) return;

					if (PluginSupport.ESSENTIALS.isPluginEnabled()) {
						if (essentialsSupport.isIgnored(target, player) || essentialsSupport.isMuted(player)) return;
					}

					if (Methods.cm_toggleChat.contains(target.getUniqueId())) return;

					if (config.getBoolean("Chat_Radius.Enable")) {
						if ((!Methods.inRange(target, player, config.getInt("Chat_Radius.Block_Distance"))) || (!Methods.inWorld(target, player))) {
							return;
						}
					}

					if (!Methods.cm_toggleMentions.contains(target.getUniqueId())) {
						try {
							target.playSound(target.getLocation(), Sound.valueOf(config.getString("Mentions.Sound")), 10, 1);
						} catch (IllegalArgumentException ignored) {}
					}

					if ((ServerProtocol.isAtLeast(ServerProtocol.v1_9_R1))) {
						if (config.getBoolean("Mentions.Title.Enable")) {
							String header = placeholderManager.setPlaceholders(player, config.getString("Mentions.Title.Header"));
							String footer = placeholderManager.setPlaceholders(player, config.getString("Mentions.Title.Footer"));

							if ((ServerProtocol.isAtLeast(ServerProtocol.v1_16_R1))) {
								target.sendTitle(header, footer, 40, 20, 40);
							} else {
								JSONMessage.create(header).title(40, 20, 40, target);
								JSONMessage.create(footer).subtitle(target);
							}
						}
					}

					if (!config.getString("Mentions.Mention_Color").equals("")) {
						String before = event.getMessage();
						String lastColor = ChatColor.getLastColors(before).equals("") ? ChatColor.WHITE.toString() : ChatColor.getLastColors(before);
						event.setMessage(event.getMessage().replace(tagSymbol + ChatColor.stripColor("everyone"), Methods.color(mentionColor + tagSymbol + ChatColor.stripColor("everyone")) + lastColor));
					}
				}
			}
		});

		if (event.getMessage().toLowerCase().contains(tagSymbol + "everyone")) {
			plugin.getServer().getOnlinePlayers().forEach(target -> {
				if (player.hasPermission("chatmanager.mention.everyone") && target.hasPermission("chatmanager.mentions.receive")) {

					if (!Methods.cm_toggleMentions.contains(target.getUniqueId())) {
						try {
							target.playSound(target.getLocation(), Sound.valueOf(config.getString("Mentions.Sound")), 10, 1);
						} catch (IllegalArgumentException ignored) {}
					}

					if ((ServerProtocol.isAtLeast(ServerProtocol.v1_9_R1))) {
						if (config.getBoolean("Mentions.Title.Enable")) {
							String header = placeholderManager.setPlaceholders(player, config.getString("Mentions.Title.Header"));
							String footer = placeholderManager.setPlaceholders(player, config.getString("Mentions.Title.Footer"));

							if ((ServerProtocol.isAtLeast(ServerProtocol.v1_16_R1))) {
								target.sendTitle(header, footer, 40, 20, 40);
							} else {
								JSONMessage.create(header).title(40, 20, 40, target);
								JSONMessage.create(footer).subtitle(target);
							}
						}
					}

					if (!config.getString("Mentions.Mention_Color").equals("")) {
						String before = event.getMessage();
						String lastColor = ChatColor.getLastColors(before).equals("") ? ChatColor.WHITE.toString() : ChatColor.getLastColors(before);
						event.setMessage(event.getMessage().replace(tagSymbol + ChatColor.stripColor("everyone"), Methods.color(mentionColor + tagSymbol + ChatColor.stripColor("everyone")) + lastColor));
					}
				}
			});
		}
	}
}