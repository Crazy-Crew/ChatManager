package me.h1dd3nxn1nja.chatmanager.support;

public class PluginHandler {

    private EssentialsSupport essentialsSupport;

    public void load() {
        if (PluginSupport.ESSENTIALS.isPluginEnabled()) this.essentialsSupport = new EssentialsSupport();
    }

    public EssentialsSupport getEssentialsSupport() {
        return this.essentialsSupport;
    }
}