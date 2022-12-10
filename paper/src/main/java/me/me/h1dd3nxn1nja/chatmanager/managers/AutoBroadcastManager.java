package me.me.h1dd3nxn1nja.chatmanager.managers;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import me.h1dd3nxn1nja.chatmanager.utils.JSONMessage;
import me.h1dd3nxn1nja.chatmanager.utils.Version;
import me.h1dd3nxn1nja.chatmanager.utils.World;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class AutoBroadcastManager {
	
	public static List<World> worlds = new ArrayList<>();
	
	public static void globalMessages(ChatManager chatManager) {
		
		FileConfiguration autobroadcast = ChatManager.settings.getAutoBroadcast();
		
		String sound = autobroadcast.getString("Auto_Broadcast.Global_Messages.Sound");
		String prefix = autobroadcast.getString("Auto_Broadcast.Global_Messages.Prefix");
		int interval = autobroadcast.getInt("Auto_Broadcast.Global_Messages.Interval");
		List<String> messages = autobroadcast.getStringList("Auto_Broadcast.Global_Messages.Messages");
		
		new BukkitRunnable() {
			int line = 0;
			public void run() {
				if (autobroadcast.getBoolean("Auto_Broadcast.Global_Messages.Enable")) {
					for (Player player : Bukkit.getOnlinePlayers()) {
						if (autobroadcast.getBoolean("Auto_Broadcast.Global_Messages.Header_And_Footer")) {
							player.sendMessage(Methods.color(player, autobroadcast.getString("Auto_Broadcast.Global_Messages.Header")));
							player.sendMessage(me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager.setPlaceholders(player, messages.get(line).replace("{Prefix}", prefix).replace("\\n", "\n")));
							player.sendMessage(Methods.color(player, autobroadcast.getString("Auto_Broadcast.Global_Messages.Footer")));	
						} else {
							player.sendMessage(me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager.setPlaceholders(player, messages.get(line).replace("{Prefix}", prefix).replace("\\n", "\n")));
						}
						try {
							player.playSound(player.getLocation(), Sound.valueOf(sound), 10, 1);
						} catch (IllegalArgumentException ignored) {}
					}
				}
				line++;
				if (line >= messages.size()) {
                    line = 0;
				}
			}
		}.runTaskTimer(chatManager, 0L, 20L * interval);
	}
	
	public static void perWorldMessages(ChatManager chatManager) {

		FileConfiguration autobroadcast = ChatManager.settings.getAutoBroadcast();
		
		String sound = autobroadcast.getString("Auto_Broadcast.Per_World_Messages.Sound");
		String prefix = autobroadcast.getString("Auto_Broadcast.Per_World_Messages.Prefix");
		int interval = autobroadcast.getInt("Auto_Broadcast.Per_World_Messages.Interval");
		worlds.clear();

		for (String key : autobroadcast.getConfigurationSection("Auto_Broadcast.Per_World_Messages.Messages").getKeys(false)) {
			World w = new World(key, autobroadcast.getStringList("Auto_Broadcast.Per_World_Messages.Messages." + key), 0);
			worlds.add(w);
		}

		new BukkitRunnable() {
			public void run() {
				for (World world : getWorld()) {
					if (autobroadcast.getBoolean("Auto_Broadcast.Per_World_Messages.Enable")) {
						for (Player player : Bukkit.getOnlinePlayers()) {
							if (player.getWorld().getName().equals(world.getName())) {
								if (autobroadcast.getBoolean("Auto_Broadcast.Per_World_Messages.Header_And_Footer")) {
									player.sendMessage(Methods.color(player, autobroadcast.getString("Auto_Broadcast.Per_World_Messages.Header")));
									player.sendMessage(me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager.setPlaceholders(player, ((String) world.getMessages().get(world.getIndex())).replace("{Prefix}", prefix).replace("\\n", "\n")));
									player.sendMessage(Methods.color(player, autobroadcast.getString("Auto_Broadcast.Per_World_Messages.Footer")));
								} else {
									player.sendMessage(me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager.setPlaceholders(player, ((String) world.getMessages().get(world.getIndex())).replace("{Prefix}", prefix).replace("\\n", "\n")));
								}
								try {
									player.playSound(player.getLocation(), Sound.valueOf(sound), 10, 1);
								} catch (IllegalArgumentException ignored) {
								}
							}
						}
					}
					int index = world.getIndex();
					if (index + 1 < world.getMessages().size())
						world.setIndex(index + 1);
					else {
						world.setIndex(0);
					}
				}
			}
		}.runTaskTimer(chatManager, 0L, 20L * interval);
	}
	
	public static void actionbarMessages(ChatManager chatManager) {
		
		FileConfiguration autobroadcast = ChatManager.settings.getAutoBroadcast();
		
		String sound = autobroadcast.getString("Auto_Broadcast.Actionbar_Messages.Sound");
		String prefix = autobroadcast.getString("Auto_Broadcast.Actionbar_Messages.Prefix");
		int interval = autobroadcast.getInt("Auto_Broadcast.Actionbar_Messages.Interval");
		List<String> messages = autobroadcast.getStringList("Auto_Broadcast.Actionbar_Messages.Messages");
		
		new BukkitRunnable() {
			int line = 0;
			public void run() {
				if (autobroadcast.getBoolean("Auto_Broadcast.Actionbar_Messages.Enable")) {
					for (Player player : Bukkit.getOnlinePlayers()) {
						if (Version.getCurrentVersion().isNewer(Version.v1_15_R2)) {
							player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager.setPlaceholders(player, messages.get(line).replace("{Prefix}", prefix))));
						} else {
							JSONMessage.create().actionbar(me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager.setPlaceholders(player, messages.get(line).replace("{Prefix}", prefix)), player);
						}
						try {
							player.playSound(player.getLocation(), Sound.valueOf(sound), 10, 1);
						} catch (IllegalArgumentException ignored) {
						}
					}
				}
				line++;
				if (line >= messages.size() ) {
                    line = 0;
				}
			}
		}.runTaskTimer(chatManager, 0L, 20L * interval);
		
	}
	
	public static void titleMessages(ChatManager chatManager) {
		
		FileConfiguration autobroadcast = ChatManager.settings.getAutoBroadcast();
		
		String sound = autobroadcast.getString("Auto_Broadcast.Title_Messages.Sound");
		int interval = autobroadcast.getInt("Auto_Broadcast.Title_Messages.Interval");
		List<String> messages = autobroadcast.getStringList("Auto_Broadcast.Title_Messages.Messages");
		
		new BukkitRunnable() {
			int line = 0;
			public void run() {
				if (autobroadcast.getBoolean("Auto_Broadcast.Actionbar_Messages.Enable")) {
					for (Player player : Bukkit.getOnlinePlayers()) {
						String title = me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager.setPlaceholders(player, autobroadcast.getString("Auto_Broadcast.Title_Messages.Title"));
						if (Version.getCurrentVersion().isNewer(Version.v1_8_R2)) {
							if (Version.getCurrentVersion().isNewer(Version.v1_15_R2)) {
								player.sendTitle(title, me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager.setPlaceholders(player, messages.get(line)), 40, 20, 40);
							} else {
								JSONMessage.create(title).title(40, 20, 40, player);
								JSONMessage.create(me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager.setPlaceholders(player, messages.get(line))).subtitle(player);
							}
						}
						try {
							player.playSound(player.getLocation(), Sound.valueOf(sound), 10, 1);
						} catch (IllegalArgumentException ignored) {
						}
					}
				}
				line++;
				if (line >= messages.size() ) {
                    line = 0;
				}
			}
		}.runTaskTimer(chatManager, 0L, 20L * interval);
	}
	
	public static void bossBarMessages(ChatManager chatManager) {
		
		FileConfiguration autobroadcast = ChatManager.settings.getAutoBroadcast();
		
		String sound = autobroadcast.getString("Auto_Broadcast.Bossbar_Messages.Sound");
		int interval = autobroadcast.getInt("Auto_Broadcast.Bossbar_Messages.Interval");
		int time = autobroadcast.getInt("Auto_Broadcast.Bossbar_Messages.Bar_Time");
		List<String> messages = autobroadcast.getStringList("Auto_Broadcast.Bossbar_Messages.Messages");
		
		new BukkitRunnable() {
			int line = 0;
			public void run() {
				if (autobroadcast.getBoolean("Auto_Broadcast.Bossbar_Messages.Enable")) {
					for (Player player : Bukkit.getOnlinePlayers()) {
						if (Version.getCurrentVersion().isNewer(Version.v1_8_R3)) {
							BossBarUtil bossBar = new BossBarUtil(PlaceholderManager.setPlaceholders(player, messages.get(line)), org.bukkit.boss.BarColor.PINK, org.bukkit.boss.BarStyle.SOLID);
							if (autobroadcast.getInt("Auto_Broadcast.Bossbar_Messages.Bar_Time") == -1) {
								bossBar.removeBossBar(player);
								bossBar.setBossBar(player);
							} else if (autobroadcast.getInt("Auto_Broadcast.Bossbar_Messages.Bar_Time") >= 0) {
								bossBar.setBossBarTime(player, time, chatManager);
							}
						}
						try {
							player.playSound(player.getLocation(), Sound.valueOf(sound), 10, 1);
						} catch (IllegalArgumentException ignored) {
						}
					}
				}
				line++;
				if (line >= messages.size() ) {
                    line = 0;
				}
			}
		}.runTaskTimer(chatManager, 0L, 20L * interval);
	}
	
	public static List<World> getWorld() {
		return worlds;
	}
}