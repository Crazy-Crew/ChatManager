package me.h1dd3nxn1nja.chatmanager.managers;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.fusion.paper.api.enums.Scheduler;
import com.ryderbelserion.fusion.paper.api.scheduler.FoliaScheduler;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import me.h1dd3nxn1nja.chatmanager.utils.World;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AutoBroadcastManager {

	private static final ChatManager plugin = ChatManager.get();

	private static final List<World> worlds = new ArrayList<>();

	private static final Server server = plugin.getServer();
	
	public static void globalMessages() {
		final FileConfiguration autobroadcast = Files.AUTO_BROADCAST.getConfiguration();

		final String prefix = autobroadcast.getString("Auto_Broadcast.Global_Messages.Prefix", "&7[&6AutoBroadcast&7] &r");
		final int interval = autobroadcast.getInt("Auto_Broadcast.Global_Messages.Interval", 30);
		final List<String> messages = autobroadcast.getStringList("Auto_Broadcast.Global_Messages.Messages");

		new FoliaScheduler(Scheduler.global_scheduler) {
			int line = 0;

			@Override
			public void run() {
				if (autobroadcast.getBoolean("Auto_Broadcast.Global_Messages.Enable", false)) {
					for (Player player : server.getOnlinePlayers()) {
						if (autobroadcast.getBoolean("Auto_Broadcast.Global_Messages.Header_And_Footer")) {
							Methods.sendMessage(player, prefix, autobroadcast.getString("Auto_Broadcast.Global_Messages.Header"), false);
							Methods.sendMessage(player, prefix, messages.get(line).replace("{prefix}", prefix).replace("\\n", "\n"), false);
							Methods.sendMessage(player, prefix, autobroadcast.getString("Auto_Broadcast.Global_Messages.Footer"), false);
						} else {
							Methods.sendMessage(player, prefix, messages.get(line).replace("{prefix}", prefix).replace("\\n", "\n"), false);
						}

						Methods.playSound(player, autobroadcast, "Auto_Broadcast.Global_Messages.sound");
					}
				}

				line++;

				if (line >= messages.size()) line = 0;
			}
		}.runAtFixedRate(0L, 20L * interval);
	}
	
	public static void perWorldMessages() {
		final FileConfiguration autobroadcast = Files.AUTO_BROADCAST.getConfiguration();

		final String prefix = autobroadcast.getString("Auto_Broadcast.Per_World_Messages.Prefix", "&7[&6AutoBroadcast&7] &r");
		final int interval = autobroadcast.getInt("Auto_Broadcast.Per_World_Messages.Interval", 60);

		worlds.clear();

		for (final String key : autobroadcast.getConfigurationSection("Auto_Broadcast.Per_World_Messages.Messages").getKeys(false)) {
			World w = new World(key, autobroadcast.getStringList("Auto_Broadcast.Per_World_Messages.Messages." + key), 0);

			worlds.add(w);
		}

		new FoliaScheduler(Scheduler.global_scheduler) {

			@Override
			public void run() {
				for (final World world : getWorld()) {
					if (autobroadcast.getBoolean("Auto_Broadcast.Per_World_Messages.Enable", false)) {
						for (final Player player : server.getOnlinePlayers()) {
							if (player.getWorld().getName().equals(world.getName())) {
								if (autobroadcast.getBoolean("Auto_Broadcast.Per_World_Messages.Header_And_Footer", false)) {
									Methods.sendMessage(player, prefix, autobroadcast.getString("Auto_Broadcast.Per_World_Messages.Header", "&7*&7&m--------------------------------&7*"), false);
									Methods.sendMessage(player, prefix, world.getMessages().get(world.getIndex()), false);
									Methods.sendMessage(player, prefix, autobroadcast.getString("Auto_Broadcast.Per_World_Messages.Footer", "&7*&7&m--------------------------------&7*"), false);
								} else {
									Methods.sendMessage(player, prefix, world.getMessages().get(world.getIndex()).replaceAll("\\n", "\n"), false);
								}

								Methods.playSound(player, autobroadcast, "Auto_Broadcast.Per_World_Messages.sound");
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
		}.runAtFixedRate(0L, 20L * interval);
	}
	
	public static void actionbarMessages() {
		final FileConfiguration autobroadcast = Files.AUTO_BROADCAST.getConfiguration();

		final String prefix = autobroadcast.getString("Auto_Broadcast.Actionbar_Messages.Prefix", "&7[&6AutoBroadcast&7] &r");
		final int interval = autobroadcast.getInt("Auto_Broadcast.Actionbar_Messages.Interval", 60);
		final List<String> messages = autobroadcast.getStringList("Auto_Broadcast.Actionbar_Messages.Messages");

		new FoliaScheduler(Scheduler.global_scheduler) {
			int line = 0;

			@Override
			public void run() {
				if (autobroadcast.getBoolean("Auto_Broadcast.Actionbar_Messages.Enable", false)) {
					for (final Player player : server.getOnlinePlayers()) {
						player.sendActionBar(Methods.placeholders(true, player, messages.get(line).replace("{prefix}", prefix).replace("{Prefix}", prefix)));

						Methods.playSound(player, autobroadcast, "Auto_Broadcast.Actionbar_Messages.sound");
					}
				}

				line++;

				if (line >= messages.size() ) line = 0;
			}
		}.runAtFixedRate(0L, 20L * interval);
	}
	
	public static void titleMessages() {
		final FileConfiguration autobroadcast = Files.AUTO_BROADCAST.getConfiguration();

		final int interval = autobroadcast.getInt("Auto_Broadcast.Title_Messages.Interval", 60);
		final List<String> messages = autobroadcast.getStringList("Auto_Broadcast.Title_Messages.Messages");

		new FoliaScheduler(Scheduler.global_scheduler) {
			int line = 0;

			@Override
			public void run() {
				if (autobroadcast.getBoolean("Auto_Broadcast.Title_Messages.Enable", false)) {
					for (final Player player : server.getOnlinePlayers()) {
						final String title = Methods.placeholders(true, player, autobroadcast.getString("Auto_Broadcast.Title_Messages.Title"));

						player.sendTitle(title, Methods.placeholders(true, player, messages.get(line)), 40, 20, 40);

						Methods.playSound(player, autobroadcast, "Auto_Broadcast.Title_Messages.sound");
					}
				}

				line++;

				if (line >= messages.size() ) line = 0;
			}
		}.runAtFixedRate(0L, 20L * interval);
	}
	
	public static void bossBarMessages() {
		final FileConfiguration autobroadcast = Files.AUTO_BROADCAST.getConfiguration();

		final int interval = autobroadcast.getInt("Auto_Broadcast.Bossbar_Messages.Interval", 60);
		final int time = autobroadcast.getInt("Auto_Broadcast.Bossbar_Messages.Bar_Time", 10);
		final List<String> messages = autobroadcast.getStringList("Auto_Broadcast.Bossbar_Messages.Messages");

		new FoliaScheduler(Scheduler.global_scheduler) {
			int line = 0;

			@Override
			public void run() {
				if (autobroadcast.getBoolean("Auto_Broadcast.Bossbar_Messages.Enable", false)) {
					for (final Player player : plugin.getServer().getOnlinePlayers()) {
						final BossBarUtil bossBar = new BossBarUtil(Methods.placeholders(true, player, messages.get(line)), org.bukkit.boss.BarColor.PINK, org.bukkit.boss.BarStyle.SOLID);

						if (autobroadcast.getInt("Auto_Broadcast.Bossbar_Messages.Bar_Time") == -1) {
							bossBar.removeBossBar(player);
							bossBar.setBossBar(player);
						} else if (autobroadcast.getInt("Auto_Broadcast.Bossbar_Messages.Bar_Time") >= 0) {
							bossBar.setBossBarTime(player, time);
						}

						Methods.playSound(player, autobroadcast, "Auto_Broadcast.Bossbar_Messages.sound");
					}
				}

				line++;

				if (line >= messages.size() ) line = 0;
			}
		}.runAtFixedRate(0L, 20L * interval);
	}
	
	private static List<World> getWorld() {
		return worlds;
	}
}