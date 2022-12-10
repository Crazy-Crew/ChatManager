package me.h1dd3nxn1nja.chatmanager;

import org.bukkit.Bukkit;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.h1dd3nxn1nja.chatmanager.commands.CommandAntiSwear;
import me.h1dd3nxn1nja.chatmanager.commands.CommandAutoBroadcast;
import me.h1dd3nxn1nja.chatmanager.commands.CommandBannedCommands;
import me.h1dd3nxn1nja.chatmanager.commands.CommandBroadcast;
import me.h1dd3nxn1nja.chatmanager.commands.CommandChatManager;
import me.h1dd3nxn1nja.chatmanager.commands.CommandClearChat;
import me.h1dd3nxn1nja.chatmanager.commands.CommandColor;
import me.h1dd3nxn1nja.chatmanager.commands.CommandLists;
import me.h1dd3nxn1nja.chatmanager.commands.CommandMOTD;
import me.h1dd3nxn1nja.chatmanager.commands.CommandMessage;
import me.h1dd3nxn1nja.chatmanager.commands.CommandMuteChat;
import me.h1dd3nxn1nja.chatmanager.commands.CommandPerWorldChat;
import me.h1dd3nxn1nja.chatmanager.commands.CommandPing;
import me.h1dd3nxn1nja.chatmanager.commands.CommandRadius;
import me.h1dd3nxn1nja.chatmanager.commands.CommandRules;
import me.h1dd3nxn1nja.chatmanager.commands.CommandSpy;
import me.h1dd3nxn1nja.chatmanager.commands.CommandStaffChat;
import me.h1dd3nxn1nja.chatmanager.commands.CommandToggleChat;
import me.h1dd3nxn1nja.chatmanager.commands.CommandToggleMentions;
import me.h1dd3nxn1nja.chatmanager.hooks.HookManager;
import me.h1dd3nxn1nja.chatmanager.hooks.VaultHook;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerAntiAdvertising;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerAntiBot;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerAntiSpam;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerAntiUnicode;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerBannedCommand;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerCaps;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerColor;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerChatFormat;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerGrammar;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerLogs;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerMentions;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerMuteChat;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerPerWorldChat;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerPlayerJoin;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerRadius;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerSpy;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerStaffChat;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerSwear;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerToggleChat;
import me.h1dd3nxn1nja.chatmanager.managers.AutoBroadcastManager;
import me.h1dd3nxn1nja.chatmanager.tabcompleter.TabCompleteAntiSwear;
import me.h1dd3nxn1nja.chatmanager.tabcompleter.TabCompleteAutoBroadcast;
import me.h1dd3nxn1nja.chatmanager.tabcompleter.TabCompleteBannedCommands;
import me.h1dd3nxn1nja.chatmanager.tabcompleter.TabCompleteChatManager;
import me.h1dd3nxn1nja.chatmanager.tabcompleter.TabCompleteMessage;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import me.h1dd3nxn1nja.chatmanager.utils.Metrics;
import me.h1dd3nxn1nja.chatmanager.utils.UpdateChecker;
import me.h1dd3nxn1nja.chatmanager.utils.Version;

public class ChatManager extends JavaPlugin {
	
  public static SettingsManager settings = SettingsManager.getInstance();
  
  private Metrics metrics;
  
	public void onEnable() {

		settings.setup(this);
		
		HookManager.loadDependencies();
		if (!setupVault()) return;
		setupPermissionsPlugin();
		registerCommands();
		registerTabCompleter();
		registerEvents();
		setupAutoBroadcast();
		setupChatRadius();
		updateChecker();
		setupMetrics();
		
		Bukkit.getConsoleSender().sendMessage("=========================");
		Bukkit.getConsoleSender().sendMessage("Chat Manager");
		Bukkit.getConsoleSender().sendMessage("Version " + this.getDescription().getVersion());
		Bukkit.getConsoleSender().sendMessage("Author: H1DD3NxN1NJA");
		Bukkit.getConsoleSender().sendMessage("=========================");
		
	}

	public void onDisable() {

		getServer().getScheduler().cancelTasks(this);

		for (Player all : Bukkit.getOnlinePlayers()) {
			Methods.cm_chatCooldown.remove(all);
			Methods.cm_cooldownTask.remove(all);
			Methods.cm_commandCooldown.remove(all);
			Methods.cm_cooldownTask.remove(all);
			if (Version.getCurrentVersion().isNewer(Version.v1_8_R3)) {
				BossBarUtil bossBar = new BossBarUtil();
				bossBar.removeAllBossBars(all);
			}
		}
	}

	public void registerCommands() {
		getCommand("Announcement").setExecutor(new CommandBroadcast(this));
		getCommand("AntiSwear").setExecutor(new CommandAntiSwear(this));
		getCommand("AutoBroadcast").setExecutor(new CommandAutoBroadcast(this));
		getCommand("BannedCommands").setExecutor(new CommandBannedCommands(this));
		getCommand("Broadcast").setExecutor(new CommandBroadcast(this));
		getCommand("cc").setExecutor(new CommandClearChat(this));
		getCommand("ChatRadius").setExecutor(new CommandRadius(this));
		getCommand("ClearChat").setExecutor(new CommandClearChat(this));
		getCommand("Colors").setExecutor(new CommandColor(this));
		getCommand("ChatManager").setExecutor(new CommandChatManager(this));
		getCommand("CommandSpy").setExecutor(new CommandSpy(this));
		getCommand("Formats").setExecutor(new CommandColor(this));
		getCommand("List").setExecutor(new CommandLists(this));
		getCommand("Message").setExecutor(new CommandMessage(this));
		getCommand("Message").setTabCompleter((TabCompleter) new TabCompleteMessage(this));
		getCommand("MuteChat").setExecutor(new CommandMuteChat(this));
		getCommand("PerWorldChat").setExecutor(new CommandPerWorldChat(this));
		getCommand("Ping").setExecutor(new CommandPing(this));
		getCommand("Reply").setExecutor(new CommandMessage(this));
		getCommand("Rules").setExecutor(new CommandRules(this));
		getCommand("Staff").setExecutor(new CommandLists(this));
		getCommand("StaffChat").setExecutor(new CommandStaffChat(this));
		getCommand("SocialSpy").setExecutor(new CommandSpy(this));
		getCommand("ToggleChat").setExecutor(new CommandToggleChat(this));
		getCommand("ToggleMentions").setExecutor(new CommandToggleMentions(this));
		getCommand("TogglePM").setExecutor(new CommandMessage(this));
		getCommand("Warning").setExecutor(new CommandBroadcast(this));
	}
	
	public void registerTabCompleter() {
		Bukkit.getServer().getPluginCommand("AntiSwear").setTabCompleter(new TabCompleteAntiSwear());
		Bukkit.getServer().getPluginCommand("AutoBroadcast").setTabCompleter(new TabCompleteAutoBroadcast());
		Bukkit.getServer().getPluginCommand("BannedCommands").setTabCompleter(new TabCompleteBannedCommands());
		Bukkit.getServer().getPluginCommand("ChatManager").setTabCompleter(new TabCompleteChatManager());
	}

	public void registerEvents() {

		getServer().getPluginManager().registerEvents(new ListenerAntiAdvertising(this), this);
		getServer().getPluginManager().registerEvents(new ListenerAntiBot(this), this);
		getServer().getPluginManager().registerEvents(new ListenerAntiSpam(this), this);
		getServer().getPluginManager().registerEvents(new ListenerAntiUnicode(this), this);
		getServer().getPluginManager().registerEvents(new ListenerBannedCommand(this), this);
		getServer().getPluginManager().registerEvents(new ListenerCaps(this), this);
		getServer().getPluginManager().registerEvents(new ListenerChatFormat(this), this);
		getServer().getPluginManager().registerEvents(new ListenerColor(this), this);
		getServer().getPluginManager().registerEvents(new ListenerRadius(this), this);
		getServer().getPluginManager().registerEvents(new ListenerGrammar(this), this);
		getServer().getPluginManager().registerEvents(new ListenerLogs(this), this);
		getServer().getPluginManager().registerEvents(new CommandMOTD(this), this);
		getServer().getPluginManager().registerEvents(new ListenerMentions(this), this);
		getServer().getPluginManager().registerEvents(new ListenerMuteChat(this), this);
		getServer().getPluginManager().registerEvents(new ListenerPerWorldChat(this), this);
		getServer().getPluginManager().registerEvents(new ListenerPlayerJoin(this), this);
		getServer().getPluginManager().registerEvents(new ListenerSpy(this), this);
		getServer().getPluginManager().registerEvents(new ListenerStaffChat(this), this);
		getServer().getPluginManager().registerEvents(new ListenerSwear(this), this);
		getServer().getPluginManager().registerEvents(new ListenerToggleChat(this), this);
	}
	
	public void setupChatRadius() {
		if (settings.getConfig().getBoolean("Chat_Radius.Enable")) {
			for (Player all : Bukkit.getOnlinePlayers()) {
				if (settings.getConfig().getString("Chat_Radius.Default_Channel").equalsIgnoreCase("Local")) {
					Methods.cm_localChat.add(all.getUniqueId());
				} else if (settings.getConfig().getString("Chat_Radius.Default_Channel").equalsIgnoreCase("Global")) {
					Methods.cm_globalChat.add(all.getUniqueId());
				} else if (settings.getConfig().getString("Chat_Radius.Default_Channel").equalsIgnoreCase("World")) {
					Methods.cm_worldChat.add(all.getUniqueId());
				}
			}
		}
	}
	
	public void setupAutoBroadcast() {
		try {
			if (settings.getAutoBroadcast().getBoolean("Auto_Broadcast.Actionbar_Messages.Enable")) {
				AutoBroadcastManager.actionbarMessages(this);
			}
			if (settings.getAutoBroadcast().getBoolean("Auto_Broadcast.Global_Messages.Enable")) {
				AutoBroadcastManager.globalMessages(this);
			}
			if (settings.getAutoBroadcast().getBoolean("Auto_Broadcast.Per_World_Messages.Enable")) {
				AutoBroadcastManager.perWorldMessages(this);
			}
			if (settings.getAutoBroadcast().getBoolean("Auto_Broadcast.Title_Messages.Enable")) {
				AutoBroadcastManager.titleMessages(this);
			}
			if (settings.getAutoBroadcast().getBoolean("Auto_Broadcast.Bossbar_Messages.Enable")) {
				AutoBroadcastManager.bossBarMessages(this);
			}
		} catch (Exception ex) {
			getLogger().info("There has been an error setting up auto broadcast. Stacktrace...");
			ex.printStackTrace();
		}
	}
	
	public boolean setupVault() {
		if (HookManager.isVaultLoaded()) {
			VaultHook.hook();
			return true;
		} else {
			getLogger().info("Vault is required to use chat manager, disabling plugin!");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		return false;
	}
	
	public boolean setupPermissionsPlugin() {
		if (Methods.doesPluginExist("LuckPerms") || 
				Methods.doesPluginExist("PermissionsEx") || 
				Methods.doesPluginExist("GroupManager") || 
				Methods.doesPluginExist("UltraPermissions")) {
			return true;
		} else {
			getLogger().info("A permissions plugin is required to use Chat Manager, otherwise errors will occur!");
		}
		return false;
	}
	
	public void updateChecker() {
		if (settings.getConfig().getBoolean("Update_Checker")) {
			UpdateChecker updater = new UpdateChecker(this, 52245);
			try {
				if (updater.checkForUpdates()) {
					getLogger().info("ChatManager has a new update available! New version: " + UpdateChecker.getLatestVersion());
					getLogger().info("Download: " + UpdateChecker.getResourceURL());
				} else {
					getLogger().info("Plugin is up to date - v" + this.getDescription().getVersion());
				}
			} catch (Exception e) {
				getLogger().info("Could not check for updates! Stacktrace:");
				e.printStackTrace();
			}
		}
	}
	
	public Metrics getMetrics() {
		return metrics;
	}

	public void setMetrics(Metrics metrics) {
		this.metrics = metrics;
	}
	
	public void setupMetrics() {
		metrics = new Metrics(this, 3291);
		metrics.addCustomChart(new Metrics.SimplePie("chat_format", () -> {
			if (settings.getConfig().getBoolean("Chat_Format.Enable") == true) {
				return "True";
			}
			return "False";
		}));
		metrics.addCustomChart(new Metrics.SimplePie("chat_radius", () -> {
			if (settings.getConfig().getBoolean("Chat_Radius.Enable") == true) {
				return "True";
			}
			return "False";
		}));
		metrics.addCustomChart(new Metrics.SimplePie("per_world_chat", () -> {
			if (settings.getConfig().getBoolean("Per_World_Chat.Enable") == true) {
				return "True";
			}
			return "False";
		}));
		metrics.addCustomChart(new Metrics.SimplePie("update_checker", () -> {
			if (settings.getConfig().getBoolean("Update_Checker") == true) {
				return "True";
			}
			return "False";
		}));
	}
}