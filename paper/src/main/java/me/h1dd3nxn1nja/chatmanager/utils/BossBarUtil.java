package me.h1dd3nxn1nja.chatmanager.utils;

import com.ryderbelserion.fusion.paper.api.enums.Scheduler;
import com.ryderbelserion.fusion.paper.api.scheduler.FoliaScheduler;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.Server;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BossBarUtil {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	private final Server server = this.plugin.getServer();

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

	public BossBarUtil(final String title, final BarColor color, final BarStyle style) {
		this.title = Methods.color(title);
		this.color = color;
		this.style = style;
		this.bar = plugin.getServer().createBossBar(this.title, color, style);
	}

	public BossBarUtil(final String title) {
		this.title = Methods.color(title);
		this.color = BarColor.PINK;
		this.style = BarStyle.SOLID;
		this.staffBar = plugin.getServer().createBossBar(this.title, color, style);
	}

	public String getTitle() {
		return title;
	}

	public BossBarUtil setTitle(final String title) {
		this.title = Methods.color(title);

		this.bar.setTitle(this.title);

		return this;
	}

	public BarColor getColor() {
		return this.color;
	}

	public BossBarUtil setColor(final String stringColor) {
		for (final BarColor color : BarColor.values()) {
			if (color.name().equalsIgnoreCase(stringColor)) return setColor(color);
		}

		return this;
	}

	public BossBarUtil setColor(final BarColor color) {
		this.color = color;
		this.bar.setColor(color);

		return this;
	}

	public BarStyle getStyle() {
		return this.style;
	}

	public BossBarUtil setStyle(final String stringStyle) {
		for (BarStyle style : BarStyle.values()) {
			if (style.name().equalsIgnoreCase(stringStyle)) return setStyle(style);
		}

		return this;
	}

	public BossBarUtil setStyle(final BarStyle style) {
		this.style = style;

		this.bar.setStyle(style);

		return this;
	}

	public double getProgress() {
		return this.progress;
	}

	public BossBarUtil setProgress(final double progress) {
		this.progress = progress;

		this.bar.setProgress(progress);

		return this;
	}

	public boolean isVisible() {
		return this.isVisible;
	}

	public BossBarUtil setVisible(final boolean visible) {
		this.isVisible = visible;

		this.bar.setVisible(visible);

		return this;
	}

	public BossBarUtil setBossBar(final Player player) {
		this.bar.addPlayer(player);

		playerBars.put(player.getUniqueId(), this);

		return this;
	}

	public BossBarUtil setStaffBossBar(final Player player) {
		this.staffBar.addPlayer(player);

		staffBars.put(player.getUniqueId(), this);

		return this;
	}

	public BossBarUtil setBossBarTime(final Player player, final int time) {
		this.bar.addPlayer(player);

		final UUID uuid = player.getUniqueId();

		playerBars.put(uuid, this);

		new FoliaScheduler(Scheduler.global_scheduler) {
			@Override
			public void run() {
				if (playerBars.containsKey(uuid)) playerBars.get(uuid).bar.removePlayer(player);
			}
		}.runDelayed(20L * time);

		return this;
	}

	public BossBarUtil removeBossBar(final Player player) {
		final UUID uuid = player.getUniqueId();

		if (playerBars.containsKey(uuid)) playerBars.get(uuid).bar.removePlayer(player);

		return this;
	}

	public BossBarUtil removeStaffBossBar(final Player player) {
		final UUID uuid = player.getUniqueId();

		if (staffBars.containsKey(uuid)) staffBars.get(uuid).staffBar.removePlayer(player);

		return this;
	}

	public BossBarUtil removeAllBossBars(final Player player) {
		final UUID uuid = player.getUniqueId();

		if (playerBars.containsKey(uuid)) playerBars.get(uuid).bar.removePlayer(player);

		if (staffBars.containsKey(uuid)) staffBars.get(uuid).staffBar.removePlayer(player);

		return this;
	}

	public BossBarUtil setBossBarAnimation(final Player player, final List<String> titles, final int time) {
		BossBar bossBar = this.server.createBossBar(titles.getFirst(), color, BarStyle.SOLID, BarFlag.CREATE_FOG);
		bossBar.addPlayer(player);

		final UUID uuid = player.getUniqueId();

		this.bossBars.put(uuid, bossBar);

		new FoliaScheduler(Scheduler.global_scheduler) {
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

					bossBars.remove(uuid);
				}
			}
		}.runAtFixedRate(1, 1);

		return this;
	}
}