package me.h1dd3nxn1nja.chatmanager.managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.fusion.paper.api.enums.Scheduler;
import com.ryderbelserion.fusion.paper.api.scheduler.FoliaScheduler;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import me.h1dd3nxn1nja.chatmanager.utils.World;

public class AutoBroadcastManager {

	private static final ChatManager plugin = ChatManager.get();

	private static final Server server = plugin.getServer();

	private static final List<World> worlds = new ArrayList<>();
	
	public static void globalMessages() {
		final FileConfiguration autobroadcast = Files.AUTO_BROADCAST.getConfiguration();

		final boolean header = autobroadcast.getBoolean("Auto_Broadcast.Global_Messages.Header_And_Footer", false);

		final String prefix = autobroadcast.getString("Auto_Broadcast.Global_Messages.Prefix", "&7[&6AutoBroadcast&7] &r");
		final int interval = autobroadcast.getInt("Auto_Broadcast.Global_Messages.Interval", 30);
		final List<String> messages = autobroadcast.getStringList("Auto_Broadcast.Global_Messages.Messages");

		new FoliaScheduler(Scheduler.global_scheduler) {
			int line = 0;

			@Override
			public void run() {
				if (autobroadcast.getBoolean("Auto_Broadcast.Global_Messages.Enable", false)) {
					line = 0;

					cancel();
				}

				for (final Player player : server.getOnlinePlayers()) {
					if (header) {
						Methods.sendMessage(player, prefix, autobroadcast.getString("Auto_Broadcast.Global_Messages.Header"), false);
						Methods.sendMessage(player, prefix, messages.get(line).replace("{prefix}", prefix).replace("\\n", "\n"), false);
						Methods.sendMessage(player, prefix, autobroadcast.getString("Auto_Broadcast.Global_Messages.Footer"), false);
					} else {
						Methods.sendMessage(player, prefix, messages.get(line).replace("{prefix}", prefix).replace("\\n", "\n"), false);
					}

					Methods.playSound(player, autobroadcast, "Auto_Broadcast.Global_Messages.sound");
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

		final ConfigurationSection section = autobroadcast.getConfigurationSection("Auto_Broadcast.Per_World_Messages.Messages");

		if (section != null) {
			for (final String key : section.getKeys(false)) {
				worlds.add(new World(key, autobroadcast.getStringList("Auto_Broadcast.Per_World_Messages.Messages." + key), 0));
			}
		}

		final boolean header = autobroadcast.getBoolean("Auto_Broadcast.Per_World_Messages.Header_And_Footer", false);

		new FoliaScheduler(Scheduler.global_scheduler) {

			@Override
			public void run() {
				if (autobroadcast.getBoolean("Auto_Broadcast.Per_World_Messages.Enable", false)) {
					worlds.clear();

					cancel();
				}

				final Collection<? extends Player> players = server.getOnlinePlayers();

				for (final World world : getWorld()) {
					final List<String> messages = world.getMessages();
					final int index = world.getIndex();

					final String message = messages.get(index);

					final String name = world.getName();

					for (final Player player : players) {
						final String worldName = player.getWorld().getName();

						if (!worldName.equals(name)) continue;

						if (header) {
							Methods.sendMessage(player, prefix, autobroadcast.getString("Auto_Broadcast.Per_World_Messages.Header", "&7*&7&m--------------------------------&7*"), false);
							Methods.sendMessage(player, prefix, message, false);
							Methods.sendMessage(player, prefix, autobroadcast.getString("Auto_Broadcast.Per_World_Messages.Footer", "&7*&7&m--------------------------------&7*"), false);
						} else {
							Methods.sendMessage(player, prefix, message.replaceAll("\\n", "\n"), false);
						}

						Methods.playSound(player, autobroadcast, "Auto_Broadcast.Per_World_Messages.sound");
					}

					if (index + 1 < messages.size())
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
					line = 0;

					cancel();
				}

				for (final Player player : server.getOnlinePlayers()) {
					player.sendActionBar(Methods.placeholders(true, player, messages.get(line).replace("{prefix}", prefix).replace("{Prefix}", prefix)));

					Methods.playSound(player, autobroadcast, "Auto_Broadcast.Actionbar_Messages.sound");
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
					line = 0;

					cancel();
				}

				for (final Player player : server.getOnlinePlayers()) {
					final String title = Methods.placeholders(true, player, autobroadcast.getString("Auto_Broadcast.Title_Messages.Title"));

					player.sendTitle(title, Methods.placeholders(true, player, messages.get(line)), 40, 20, 40);

					Methods.playSound(player, autobroadcast, "Auto_Broadcast.Title_Messages.sound");
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
				if (!autobroadcast.getBoolean("Auto_Broadcast.Bossbar_Messages.Enable", false)) {
					line = 0;

					cancel();
				}

				for (final Player player : server.getOnlinePlayers()) {
					final BossBarUtil bossBar = new BossBarUtil(Methods.placeholders(true, player, messages.get(line)), org.bukkit.boss.BarColor.PINK, org.bukkit.boss.BarStyle.SOLID);

					if (autobroadcast.getInt("Auto_Broadcast.Bossbar_Messages.Bar_Time") == -1) {
						bossBar.removeBossBar(player);
						bossBar.setBossBar(player);
					} else if (autobroadcast.getInt("Auto_Broadcast.Bossbar_Messages.Bar_Time") >= 0) {
						bossBar.setBossBarTime(player, time);
					}

					Methods.playSound(player, autobroadcast, "Auto_Broadcast.Bossbar_Messages.sound");
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