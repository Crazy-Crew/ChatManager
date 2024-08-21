package me.h1dd3nxn1nja.chatmanager.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.ryderbelserion.vital.paper.util.scheduler.FoliaRunnable;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.jetbrains.annotations.NotNull;

public class BossBarUtil {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	private String title;
	private BarColor color;
	private BarStyle style;
	private double progress;
	private boolean isVisible;
	private BossBar bar;
	private BossBar staffBar;
	private static final Map<UUID, BossBarUtil> playerBars = new HashMap<>();
	private static final Map<UUID, BossBarUtil> staffBars = new HashMap<>();

	private final Map<UUID, BossBar> bossBars = new HashMap<>();

	public BossBarUtil() {
		this.title = Methods.color("&bStaff Chat");
		this.color = BarColor.PINK;
		this.style = BarStyle.SOLID;
		this.bar = org.bukkit.Bukkit.getServer().createBossBar(title, color, style);
	}

	public BossBarUtil(String title, BarColor color, BarStyle style) {
		this.title = Methods.color(title);
		this.color = color;
		this.style = style;
		this.bar = plugin.getServer().createBossBar(this.title, color, style);
	}

	public BossBarUtil(String title) {
		this.title = Methods.color(title);
		this.color = BarColor.PINK;
		this.style = BarStyle.SOLID;
		this.staffBar = plugin.getServer().createBossBar(this.title, color, style);
	}

	public String getTitle() {
		return title;
	}

	public BossBarUtil setTitle(String title) {
		this.title = Methods.color(title);

		this.bar.setTitle(this.title);

		return this;
	}

	public BarColor getColor() {
		return this.color;
	}

	public BossBarUtil setColor(String stringColor) {
		for (BarColor color : BarColor.values()) {
			if (color.name().equalsIgnoreCase(stringColor)) return setColor(color);
		}

		return this;
	}

	public BossBarUtil setColor(BarColor color) {
		this.color = color;
		this.bar.setColor(color);

		return this;
	}

	public BarStyle getStyle() {
		return this.style;
	}

	public BossBarUtil setStyle(String stringStyle) {
		for (BarStyle style : BarStyle.values()) {
			if (style.name().equalsIgnoreCase(stringStyle)) return setStyle(style);
		}

		return this;
	}

	public BossBarUtil setStyle(BarStyle style) {
		this.style = style;

		this.bar.setStyle(style);

		return this;
	}

	public double getProgress() {
		return this.progress;
	}

	public BossBarUtil setProgress(double progress) {
		this.progress = progress;

		this.bar.setProgress(progress);

		return this;
	}

	public boolean isVisible() {
		return this.isVisible;
	}

	public BossBarUtil setVisible(boolean visible) {
		this.isVisible = visible;

		this.bar.setVisible(visible);

		return this;
	}

	public BossBarUtil setBossBar(Player player) {
		this.bar.addPlayer(player);

		playerBars.put(player.getUniqueId(), this);

		return this;
	}

	public BossBarUtil setStaffBossBar(Player player) {
		this.staffBar.addPlayer(player);

		staffBars.put(player.getUniqueId(), this);

		return this;
	}

	public BossBarUtil setBossBarTime(Player player, int time) {
		this.bar.addPlayer(player);

		playerBars.put(player.getUniqueId(), this);

		new FoliaRunnable(this.plugin.getServer().getGlobalRegionScheduler()) {
			@Override
			public void run() {
				if (playerBars.containsKey(player.getUniqueId())) playerBars.get(player.getUniqueId()).bar.removePlayer(player);
			}
		}.runDelayed(this.plugin, 20L * time);

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

	public BossBarUtil setBossBarAnimation(Player player, List<String> titles, int time) {
		BossBar bossBar = this.plugin.getServer().createBossBar(titles.getFirst(), color, BarStyle.SOLID, BarFlag.CREATE_FOG);
		bossBar.addPlayer(player);

		this.bossBars.put(player.getUniqueId(), bossBar);

		new FoliaRunnable(this.plugin.getServer().getGlobalRegionScheduler()) {
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
		}.runAtFixedRate(this.plugin, 1, 1);

		return this;
	}
}