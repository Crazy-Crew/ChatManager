package me.h1dd3nxn1nja.chatmanager.utils;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.h1dd3nxn1nja.chatmanager.Main;

public class BossBarUtil {

	private String title;
	private BarColor color;
	private BarStyle style;
	private double progress;
	private boolean isVisable;
	private BossBar bar;
	private BossBar staffBar;
	private static HashMap<UUID, BossBarUtil> playerBars = new HashMap<>();
	private static HashMap<UUID, BossBarUtil> staffBars = new HashMap<>();
	HashMap<UUID, BossBar> bossBars = new HashMap<>();

	public BossBarUtil() {
			this.title = ChatColor.translateAlternateColorCodes('&', "&bStaff Chat");
			this.color = BarColor.PINK;
			this.style = BarStyle.SOLID;
			this.bar = org.bukkit.Bukkit.getServer().createBossBar(title, color, style);
	}

	public BossBarUtil(String title, BarColor color, BarStyle style) {
			this.title = ChatColor.translateAlternateColorCodes('&', title);
			this.color = color;
			this.style = style;
			this.bar = Bukkit.getServer().createBossBar(this.title, color, style);
	}
	
	public BossBarUtil(String title) {
			this.title = ChatColor.translateAlternateColorCodes('&', title);
			this.color = BarColor.PINK;
			this.style = BarStyle.SOLID;
			this.staffBar = Bukkit.getServer().createBossBar(this.title, color, style);
	}

	public String getTitle() {
		return title;
	}

	public BossBarUtil setTitle(String title) {
		this.title = ChatColor.translateAlternateColorCodes('&', title);
		bar.setTitle(this.title);
		return this;
	}

	public BarColor getColor() {
		return color;
	}

	public BossBarUtil setColor(String stringColor) {
		for (BarColor color : BarColor.values()) {
			if (color.name().equalsIgnoreCase(stringColor)) {
				return setColor(color);
			}
		}
		return this;
	}

	public BossBarUtil setColor(BarColor color) {
		this.color = color;
		bar.setColor(color);
		return this;
	}

	public BarStyle getStyle() {
		return style;
	}

	public BossBarUtil setStyle(String stringStyle) {
		for (BarStyle style : BarStyle.values()) {
			if (style.name().equalsIgnoreCase(stringStyle)) {
				return setStyle(style);
			}
		}
		return this;
	}

	public BossBarUtil setStyle(BarStyle style) {
		this.style = style;
		bar.setStyle(style);
		return this;
	}

	public double getProgress() {
		return progress;
	}

	public BossBarUtil setProgress(double progress) {
		this.progress = progress;
		bar.setProgress(progress);
		return this;
	}

	public boolean isVisable() {
		return isVisable;
	}

	public BossBarUtil setVisable(boolean visable) {
		isVisable = visable;
		bar.setVisible(visable);
		return this;
	}

	public BossBarUtil setBossBar(Player player) {
		bar.addPlayer(player);
		playerBars.put(player.getUniqueId(), this);
		return this;
	}
	
	public BossBarUtil setStaffBossBar(Player player) {
		staffBar.addPlayer(player);
		staffBars.put(player.getUniqueId(), this);
		return this;
	}
	
	public BossBarUtil setBossBarTime(Player player, int time, Main main) {
		bar.addPlayer(player);
		playerBars.put(player.getUniqueId(), this);
		new BukkitRunnable() {
			public void run() {
				if (playerBars.containsKey(player.getUniqueId())) {
					playerBars.get(player.getUniqueId()).bar.removePlayer(player);
				}
			}
		}.runTaskLater(main, 20L * time);
		return this;
	}

	public BossBarUtil removeBossBar(Player player) {
		if (playerBars.containsKey(player.getUniqueId())) {
			playerBars.get(player.getUniqueId()).bar.removePlayer(player);
		}
		return this;
	}
	
	public BossBarUtil removeStaffBossBar(Player player) {
		if (staffBars.containsKey(player.getUniqueId())) {
			staffBars.get(player.getUniqueId()).staffBar.removePlayer(player);
		}
		return this;
	}
	
	public BossBarUtil removeAllBossBars(Player player) {
		if (playerBars.containsKey(player.getUniqueId())) {
			playerBars.get(player.getUniqueId()).bar.removePlayer(player);
		}
		if (staffBars.containsKey(player.getUniqueId())) {
			staffBars.get(player.getUniqueId()).staffBar.removePlayer(player);
		}
		return this;
	}
	
	public BossBarUtil setBossBarAnimation(Player player, List<String> titles, int time, Main main) {
		BossBar bossBar = Bukkit.createBossBar(titles.get(0), color, BarStyle.SOLID, BarFlag.CREATE_FOG);
		bossBar.addPlayer(player);
		bossBars.put(player.getUniqueId(), bossBar);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
			int i = 1;
			int ticksRunned = 0;

			@Override
			public void run() {
				ticksRunned++;
				bossBar.setTitle(titles.get(i));
				i++;
				if (i >= titles.size()) {
					i = 0;
				}
				if (ticksRunned >= time) {
					bossBar.removePlayer(player);
                    bossBar.setVisible(false);
                    bossBars.remove(player.getUniqueId());
				}
			}

		}, 1, 1);
		return this;
	}
}