package me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import me.h1dd3nxn1nja.chatmanager.support.EssentialsSupport;
import me.h1dd3nxn1nja.chatmanager.support.PluginManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerMentions implements Listener {

    private final ChatManager plugin = ChatManager.getPlugin();

    private final SettingsManager settingsManager = plugin.getSettingsManager();

    private final PlaceholderManager placeholderManager = plugin.getCrazyManager().getPlaceholderManager();

    private final PluginManager pluginManager = plugin.getPluginManager();

    private final EssentialsSupport essentialsSupport = pluginManager.getEssentialsSupport();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
//        FileConfiguration config = settingsManager.getConfig();
//
//        if (config.getBoolean("Mentions.Enable")) {
//			if (event.getMessage().toLowerCase().contains(tagSymbol + "everyone")) {
//				plugin.getServer().getOnlinePlayers().forEach(target -> {
//					if (player.hasPermission("chatmanager.mention.everyone")) {
//						try {
//							target.playSound(target.getLocation(), Sound.valueOf(config.getString("Mentions.Sound")), 10, 1);
//						} catch (IllegalArgumentException ignored) {}
//						if ((ServerProtocol.isAtLeast(ServerProtocol.v1_9_R1))) {
//							if (config.getBoolean("Mentions.Title.Enable")) {
//								String header = placeholderManager.setPlaceholders(player, config.getString("Mentions.Title.Header"));
//								String footer = placeholderManager.setPlaceholders(player, config.getString("Mentions.Title.Footer"));
//
//								if ((ServerProtocol.isAtLeast(ServerProtocol.v1_16_R1))) {
//									target.sendTitle(header, footer, 40, 20, 40);
//								} else {
//									JSONMessage.create(header).title(40, 20, 40, target);
//									JSONMessage.create(footer).subtitle(target);
//								}
//							}
//						}
//
//						if (!config.getString("Mentions.Mention_Color").equals("")) {
//							String before = event.getMessage();
//							String lastColor = ChatColor.getLastColors(before).equals("") ? ChatColor.WHITE.toString() : ChatColor.getLastColors(before);
//							event.setMessage(event.getMessage().replace(tagSymbol + ChatColor.stripColor("everyone"), Methods.color(mentionColor + tagSymbol + ChatColor.stripColor("everyone")) + lastColor));
//						}
//					}
//				});
//			}
//    }
    }
}