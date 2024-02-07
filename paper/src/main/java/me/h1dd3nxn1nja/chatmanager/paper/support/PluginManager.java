package me.h1dd3nxn1nja.chatmanager.paper.support;

import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.support.misc.VaultSupport;
import me.h1dd3nxn1nja.chatmanager.paper.support.placeholders.PlaceholderAPISupport;
import me.h1dd3nxn1nja.chatmanager.paper.support.vanish.EssentialsVanishSupport;
import me.h1dd3nxn1nja.chatmanager.paper.support.vanish.GenericVanishSupport;
import org.jetbrains.annotations.NotNull;

public class PluginManager {

    @NotNull
    private final ChatManager plugin = ChatManager.get();

    private EssentialsSupport essentialsSupport;

    private EssentialsVanishSupport essentialsVanishSupport;

    private GenericVanishSupport genericVanishSupport;

    private VaultSupport vaultSupport;

    public void load() {
        this.vaultSupport = new VaultSupport();
        this.vaultSupport.configure();

        // Load EssentialsX support if plugin is enabled.
        if (PluginSupport.ESSENTIALS.isPluginEnabled()) this.essentialsSupport = new EssentialsSupport();

        // Switch vanish controller depending on plugin enabled.
        if (PluginSupport.ESSENTIALS.isPluginEnabled()) this.essentialsVanishSupport = new EssentialsVanishSupport();

        if (PluginSupport.PREMIUM_VANISH.isPluginEnabled() || PluginSupport.SUPER_VANISH.isPluginEnabled()) this.genericVanishSupport = new GenericVanishSupport();

        if (PluginSupport.PLACEHOLDERAPI.isPluginEnabled()) new PlaceholderAPISupport().register();

        // Print hooks on load.
        printHooks();
    }

    public EssentialsSupport getEssentialsSupport() {
        return this.essentialsSupport;
    }

    public EssentialsVanishSupport getEssentialsVanishSupport() {
        return this.essentialsVanishSupport;
    }

    public GenericVanishSupport getGenericVanishSupport() {
        return this.genericVanishSupport;
    }

    public VaultSupport getVaultSupport() {
        return this.vaultSupport;
    }

    private void printHooks() {
        for (PluginSupport value : PluginSupport.values()) {
            if (value.isPluginEnabled()) {
                this.plugin.getServer().getConsoleSender().sendMessage(this.plugin.getMethods().color("&6&l" + value.name() + " &a&lFOUND"));
            } else {
                this.plugin.getServer().getConsoleSender().sendMessage(this.plugin.getMethods().color("&6&l" + value.name() + " &c&lNOT FOUND"));
            }
        }
    }
}