package me.h1dd3nxn1nja.chatmanager.paper.managers;

import java.util.ArrayList;
import java.util.List;

import com.ryderbelserion.chatmanager.paper.api.CrazyManager;
import com.ryderbelserion.chatmanager.paper.files.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import me.h1dd3nxn1nja.chatmanager.paper.Methods;
import me.h1dd3nxn1nja.chatmanager.paper.utils.BossBarUtil;
import me.h1dd3nxn1nja.chatmanager.paper.utils.World;

public class AutoBroadcastManager {

	private static final ChatManager plugin = ChatManager.getPlugin();
	private static final CrazyManager crazyManager = plugin.getCrazyManager();

	private static final List<World> worlds = new ArrayList<>();
	
	public static void globalMessages() {
		FileConfiguration autobroadcast = Files.AUTO_BROADCAST.getFile();

		String sound = autobroadcast.getString("Auto_Broadcast.Global_Messages.Sound");
		String prefix = autobroadcast.getString("Auto_Broadcast.Global_Messages.Prefix");
		int interval = autobroadcast.getInt("Auto_Broadcast.Global_Messages.Interval");
		List<String> messages = autobroadcast.getStringList("Auto_Broadcast.Global_Messages.Messages");
		
		new BukkitRunnable() {
			int line = 0;
			public void run() {
				if (autobroadcast.getBoolean("Auto_Broadcast.Global_Messages.Enable")) {
					for (Player player : plugin.getServer().getOnlinePlayers()) {
						if (autobroadcast.getBoolean("Auto_Broadcast.Global_Messages.Header_And_Footer")) {
							player.sendMessage(Methods.color(player.getUniqueId(), autobroadcast.getString("Auto_Broadcast.Global_Messages.Header")));
							player.sendMessage(crazyManager.getPlaceholderManager().setPlaceholders(player, messages.get(line).replace("{Prefix}", prefix).replace("\\n", "\n")));
							player.sendMessage(Methods.color(player.getUniqueId(), autobroadcast.getString("Auto_Broadcast.Global_Messages.Footer")));
						} else {
							player.sendMessage(crazyManager.getPlaceholderManager().setPlaceholders(player, messages.get(line).replace("{Prefix}", prefix).replace("\\n", "\n")));
						}

						try {
							player.playSound(player.getLocation(), Sound.valueOf(sound), 10, 1);
						} catch (IllegalArgumentException ignored) {}
					}
				}
				line++;

				if (line >= messages.size()) line = 0;
			}
		}.runTaskTimer(plugin, 0L, 20L * interval);
	}
	
	public static void perWorldMessages() {
		FileConfiguration autobroadcast = Files.AUTO_BROADCAST.getFile();

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
						for (Player player : plugin.getServer().getOnlinePlayers()) {
							if (player.getWorld().getName().equals(world.getName())) {
								if (autobroadcast.getBoolean("Auto_Broadcast.Per_World_Messages.Header_And_Footer")) {
									player.sendMessage(Methods.color(player.getUniqueId(), autobroadcast.getString("Auto_Broadcast.Per_World_Messages.Header")));
									player.sendMessage(crazyManager.getPlaceholderManager().setPlaceholders(player, world.getMessages().get(world.getIndex()).replace("{Prefix}", prefix).replace("\\n", "\n")));
									player.sendMessage(Methods.color(player.getUniqueId(), autobroadcast.getString("Auto_Broadcast.Per_World_Messages.Footer")));
								} else {
									player.sendMessage(crazyManager.getPlaceholderManager().setPlaceholders(player, world.getMessages().get(world.getIndex()).replace("{Prefix}", prefix).replace("\\n", "\n")));
								}
								try {
									player.playSound(player.getLocation(), Sound.valueOf(sound), 10, 1);
								} catch (IllegalArgumentException ignored) {}
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
		}.runTaskTimer(plugin, 0L, 20L * interval);
	}
	
	public static void actionbarMessages() {
		FileConfiguration autobroadcast = Files.AUTO_BROADCAST.getFile();

		String sound = autobroadcast.getString("Auto_Broadcast.Actionbar_Messages.Sound");
		String prefix = autobroadcast.getString("Auto_Broadcast.Actionbar_Messages.Prefix");
		int interval = autobroadcast.getInt("Auto_Broadcast.Actionbar_Messages.Interval");
		List<String> messages = autobroadcast.getStringList("Auto_Broadcast.Actionbar_Messages.Messages");
		
		new BukkitRunnable() {
			int line = 0;

			public void run() {
				if (autobroadcast.getBoolean("Auto_Broadcast.Actionbar_Messages.Enable")) {
					for (Player player : plugin.getServer().getOnlinePlayers()) {

						CraftPlayer craftPlayer = (CraftPlayer) player;

						craftPlayer.sendActionBar(crazyManager.getPlaceholderManager().setPlaceholders(player, messages.get(line).replace("{Prefix}", prefix)));

						try {
							player.playSound(player.getLocation(), Sound.valueOf(sound), 10, 1);
						} catch (IllegalArgumentException ignored) {}
					}
				}

				line++;
				if (line >= messages.size() ) line = 0;
			}
		}.runTaskTimer(plugin, 0L, 20L * interval);
	}
	
	public static void titleMessages() {
		FileConfiguration autobroadcast = Files.AUTO_BROADCAST.getFile();

		String sound = autobroadcast.getString("Auto_Broadcast.Title_Messages.Sound");
		int interval = autobroadcast.getInt("Auto_Broadcast.Title_Messages.Interval");
		List<String> messages = autobroadcast.getStringList("Auto_Broadcast.Title_Messages.Messages");
		
		new BukkitRunnable() {
			int line = 0;
			public void run() {
				if (autobroadcast.getBoolean("Auto_Broadcast.Actionbar_Messages.Enable")) {
					for (Player player : plugin.getServer().getOnlinePlayers()) {
						String title = crazyManager.getPlaceholderManager().setPlaceholders(player, autobroadcast.getString("Auto_Broadcast.Title_Messages.Title"));

						player.sendTitle(title, crazyManager.getPlaceholderManager().setPlaceholders(player, messages.get(line)), 40, 20, 40);

						try {
							player.playSound(player.getLocation(), Sound.valueOf(sound), 10, 1);
						} catch (IllegalArgumentException ignored) {}
					}
				}

				line++;
				if (line >= messages.size() ) line = 0;
			}
		}.runTaskTimer(plugin, 0L, 20L * interval);
	}
	
	public static void bossBarMessages() {
		FileConfiguration autobroadcast = Files.AUTO_BROADCAST.getFile();

		String sound = autobroadcast.getString("Auto_Broadcast.Bossbar_Messages.Sound");
		int interval = autobroadcast.getInt("Auto_Broadcast.Bossbar_Messages.Interval");
		int time = autobroadcast.getInt("Auto_Broadcast.Bossbar_Messages.Bar_Time");
		List<String> messages = autobroadcast.getStringList("Auto_Broadcast.Bossbar_Messages.Messages");
		
		new BukkitRunnable() {
			int line = 0;
			public void run() {
				if (autobroadcast.getBoolean("Auto_Broadcast.Bossbar_Messages.Enable")) {
					for (Player player : plugin.getServer().getOnlinePlayers()) {
						BossBarUtil bossBar = new BossBarUtil(crazyManager.getPlaceholderManager().setPlaceholders(player, messages.get(line)), org.bukkit.boss.BarColor.PINK, org.bukkit.boss.BarStyle.SOLID);

						if (autobroadcast.getInt("Auto_Broadcast.Bossbar_Messages.Bar_Time") == -1) {
							bossBar.removeBossBar(player);
							bossBar.setBossBar(player);
						} else if (autobroadcast.getInt("Auto_Broadcast.Bossbar_Messages.Bar_Time") >= 0) {
							bossBar.setBossBarTime(player, time, plugin);
						}

						try {
							player.playSound(player.getLocation(), Sound.valueOf(sound), 10, 1);
						} catch (IllegalArgumentException ignored) {}
					}
				}

				line++;
				if (line >= messages.size() ) line = 0;
			}
		}.runTaskTimer(plugin, 0L, 20L * interval);
	}
	
	private static List<World> getWorld() {
		return worlds;
	}
}