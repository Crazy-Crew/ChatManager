package me.me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.hooks.EssentialsHook;
import me.h1dd3nxn1nja.chatmanager.hooks.HookManager;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import me.h1dd3nxn1nja.chatmanager.utils.JSONMessage;
import me.h1dd3nxn1nja.chatmanager.utils.Version;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerMentions implements Listener {
	
	public ListenerMentions(ChatManager plguin) {}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		
		FileConfiguration config = ChatManager.settings.getConfig();
		
		Player player = event.getPlayer();
		
		String tagSymbol = config.getString("Mentions.Tag_Symbol");
		String mentionColor = config.getString("Mentions.Mention_Color");
		
		if (config.getBoolean("Mentions.Enable")) {
			event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
			Bukkit.getServer().getOnlinePlayers().forEach(target -> {
				if (player.hasPermission("chatmanager.mention")) {
					if (event.getMessage().contains(tagSymbol + target.getName())) {
						if (Methods.cm_toggleMentions.contains(target.getUniqueId())) {
							return;
						}
						if (HookManager.isEssentialsLoaded()) {
							if ((EssentialsHook.isIgnored(target, player)) || (EssentialsHook.isMuted(player))) {
								return;
							}
						}
						if (Methods.cm_toggleChat.contains(target.getUniqueId())) {
							return;
						}
						if (config.getBoolean("Chat_Radius.Enable")) {
							if ((!Methods.inRange(target, player, config.getInt("Chat_Radius.Block_Distance"))) || (!Methods.inWorld(target, player))) {
								return;
							}
						}
						try {
							target.playSound(target.getLocation(), Sound.valueOf(config.getString("Mentions.Sound")), 10, 1);
						} catch (IllegalArgumentException ignored) {}
						if (Version.getCurrentVersion().isNewer(Version.v1_8_R2)) {
							if (config.getBoolean("Mentions.Title.Enable")) {
								String header = PlaceholderManager.setPlaceholders(player, config.getString("Mentions.Title.Header"));
								String footer = PlaceholderManager.setPlaceholders(player, config.getString("Mentions.Title.Footer"));
								if (Version.getCurrentVersion().isNewer(Version.v1_15_R2)) {
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
							//event.setMessage(event.getMessage().replace("@" + ChatColor.stripColor(player.getName()), Methods.color("&b@" + ChatColor.stripColor(player.getName())) + lastColor));
							event.setMessage(event.getMessage().replace(tagSymbol + ChatColor.stripColor(target.getName()), Methods.color(mentionColor + tagSymbol + ChatColor.stripColor(target.getName())) + lastColor));
						}
					}
				}
			});
			
			if (event.getMessage().toLowerCase().contains(tagSymbol + "everyone")) {
				Bukkit.getOnlinePlayers().forEach(target -> {
					if (player.hasPermission("chatmanager.mention.everyone")) {
						try {
							target.playSound(target.getLocation(), Sound.valueOf(config.getString("Mentions.Sound")), 10, 1);
						} catch (IllegalArgumentException ignored) {}
						if (Version.getCurrentVersion().isNewer(Version.v1_8_R2)) {
							if (config.getBoolean("Mentions.Title.Enable")) {
								String header = PlaceholderManager.setPlaceholders(player, config.getString("Mentions.Title.Header"));
								String footer = PlaceholderManager.setPlaceholders(player, config.getString("Mentions.Title.Footer"));
								if (Version.getCurrentVersion().isNewer(Version.v1_15_R2)) {
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
		                	//event.setMessage(event.getMessage().replace("@" + ChatColor.stripColor(player.getName()), Methods.color("&b@" + ChatColor.stripColor(player.getName())) + lastColor));
							event.setMessage(event.getMessage().replace(tagSymbol + ChatColor.stripColor("everyone"), Methods.color(mentionColor + tagSymbol + ChatColor.stripColor("everyone")) + lastColor));
						}
					}
				});
			}
		}
	}
}