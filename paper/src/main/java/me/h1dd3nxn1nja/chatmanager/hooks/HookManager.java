package me.h1dd3nxn1nja.chatmanager.hooks;

import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.Bukkit;

public class HookManager {

	protected static PlaceholderAPIHook placeholderApi;
	protected static VaultHook vault;
	protected static ProtocolLibHook protocolLib;
	protected static SuperVanishHook superVanish;
	protected static SuperVanishHook premiumVanish;

	public HookManager() {}

	public void hookManager() {
		if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			placeholderApi = new PlaceholderAPIHook();
		}
	}

	public static void loadDependencies() {
		if (Methods.doesPluginExist("PlaceholderAPI")) {
			placeholderApi = new PlaceholderAPIHook();
			new PlaceholderAPIHook(Methods.getPlugin()).register();
		}

		if (Methods.doesPluginExist("Vault")) {
			vault = new VaultHook();
		}

		if (Methods.doesPluginExist("SuperVanish")) {
			superVanish = new SuperVanishHook();
		}
		
		if (Methods.doesPluginExist("PremiumVanish")) {
			premiumVanish = new SuperVanishHook();
		}
	}

	public static boolean isSuperVanishLoaded() {
		return superVanish != null;
	}
	
	public static boolean isPremiumVanishLoaded() {
		return premiumVanish != null;
	}

	public static boolean isPlaceholderAPILoaded() {
		return placeholderApi != null;
	}
	
	public static boolean isProtocolLibLoaded() {
		return protocolLib != null;
	}

	public static boolean isVaultLoaded() {
		return vault != null;
	}
}