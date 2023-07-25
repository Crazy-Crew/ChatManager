package me.h1dd3nxn1nja.chatmanager.support;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.misc.VaultSupport;
import me.h1dd3nxn1nja.chatmanager.support.placeholders.PlaceholderAPISupport;
import me.h1dd3nxn1nja.chatmanager.support.vanish.EssentialsVanishSupport;
import me.h1dd3nxn1nja.chatmanager.support.vanish.GenericVanishSupport;
import org.bukkit.plugin.Plugin;

public class PluginManager {

    private final ChatManager plugin = ChatManager.getPlugin();

    private EssentialsSupport essentialsSupport;

    private EssentialsVanishSupport essentialsVanishSupport;

    private GenericVanishSupport genericVanishSupport;

    private VaultSupport vaultSupport;

    public void load() {
        vaultSupport = new VaultSupport();
        vaultSupport.configure();

        // Load EssentialsX support if plugin is enabled.
        if (PluginSupport.ESSENTIALS.isPluginEnabled()) essentialsSupport = new EssentialsSupport();

        // Switch vanish controller depending on plugin enabled.
        if (PluginSupport.ESSENTIALS.isPluginEnabled()) essentialsVanishSupport = new EssentialsVanishSupport();

        if (PluginSupport.PREMIUM_VANISH.isPluginEnabled() || PluginSupport.SUPER_VANISH.isPluginEnabled()) genericVanishSupport = new GenericVanishSupport();

        if (PluginSupport.PLACEHOLDERAPI.isPluginEnabled()) new PlaceholderAPISupport().register();

        // Print hooks on load.
        //printHooks();
    }

    public EssentialsSupport getEssentialsSupport() {
        return essentialsSupport;
    }

    public EssentialsVanishSupport getEssentialsVanishSupport() {
        return essentialsVanishSupport;
    }

    public GenericVanishSupport getGenericVanishSupport() {
        return genericVanishSupport;
    }

    public VaultSupport getVaultSupport() {
        return vaultSupport;
    }

    private void printHooks() {
        for (PluginSupport value : PluginSupport.values()) {
            if (value.isPluginEnabled()) {
                plugin.getLogger().info(Methods.color("&6&l" + value.name() + " &a&lFOUND"));
            } else {
                plugin.getLogger().info(Methods.color("&6&l" + value.name() + " &c&lNOT FOUND"));
            }
        }
    }
}