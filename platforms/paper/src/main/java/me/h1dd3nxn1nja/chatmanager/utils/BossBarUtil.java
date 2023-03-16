package me.h1dd3nxn1nja.chatmanager.utils;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import me.h1dd3nxn1nja.chatmanager.ChatManager;

public class BossBarUtil {

	private final ChatManager plugin = ChatManager.getPlugin();

	private String title;
	private BarColor color;
	private BarStyle style;
	private double progress;
	private boolean isVisible;
	private BossBar bar;
	private BossBar staffBar;
	private static final HashMap<UUID, BossBarUtil> playerBars = new HashMap<>();
	private static final HashMap<UUID, BossBarUtil> staffBars = new HashMap<>();
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
			this.bar = plugin.getServer().createBossBar(this.title, color, style);
	}
	
	public BossBarUtil(String title) {
			this.title = ChatColor.translateAlternateColorCodes('&', title);
			this.color = BarColor.PINK;
			this.style = BarStyle.SOLID;
			this.staffBar = plugin.getServer().createBossBar(this.title, color, style);
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
			if (color.name().equalsIgnoreCase(stringColor)) return setColor(color);
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
			if (style.name().equalsIgnoreCase(stringStyle)) return setStyle(style);
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

	public boolean isVisible() {
		return isVisible;
	}

	public BossBarUtil setVisible(boolean visible) {
		isVisible = visible;

		bar.setVisible(visible);

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
	
	public BossBarUtil setBossBarTime(Player player, int time, ChatManager chatManager) {
		bar.addPlayer(player);

		playerBars.put(player.getUniqueId(), this);

		new BukkitRunnable() {
			public void run() {
				if (playerBars.containsKey(player.getUniqueId())) playerBars.get(player.getUniqueId()).bar.removePlayer(player);
			}
		}.runTaskLater(chatManager, 20L * time);

		return this;
	}

	public BossBarUtil removeBossBar(Player player) {
		if (playerBars.containsKey(player.getUniqueId())) playerBars.get(player.getUniqueId()).bar.removePlayer(player);

		return this;
	}
	
	public BossBarUtil removeStaffBossBar(Player player) {
		if (staffBars.containsKey(player.getUniqueId())) staffBars.get(player.getUniqueId()).staffBar.removePlayer(player);

		return this;
	}
	
	public BossBarUtil removeAllBossBars(Player player) {
		if (playerBars.containsKey(player.getUniqueId())) playerBars.get(player.getUniqueId()).bar.removePlayer(player);

		if (staffBars.containsKey(player.getUniqueId())) staffBars.get(player.getUniqueId()).staffBar.removePlayer(player);

		return this;
	}
	
	public BossBarUtil setBossBarAnimation(Player player, List<String> titles, int time, ChatManager chatManager) {
		BossBar bossBar = plugin.getServer().createBossBar(titles.get(0), color, BarStyle.SOLID, BarFlag.CREATE_FOG);
		bossBar.addPlayer(player);
		bossBars.put(player.getUniqueId(), bossBar);

		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(chatManager, new Runnable() {
			int i = 1;
			int ticksRan = 0;

			@Override
			public void run() {
				ticksRan++;
				bossBar.setTitle(titles.get(i));
				i++;

				if (i >= titles.size()) i = 0;

				if (ticksRan >= time) {
					bossBar.removePlayer(player);
                    bossBar.setVisible(false);
                    bossBars.remove(player.getUniqueId());
				}
			}
		}, 1, 1);
		return this;
	}
}