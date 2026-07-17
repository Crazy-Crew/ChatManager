package com.ryderbelserion.chatmanager.plugins.papi;

import com.ryderbelserion.fusion.core.api.FusionKey;
import com.ryderbelserion.fusion.core.api.constants.ModSupport;
import com.ryderbelserion.fusion.core.api.registry.mods.ModRegistry;
import com.ryderbelserion.fusion.core.api.registry.mods.objects.Mod;
import me.h1dd3nxn1nja.chatmanager.ChatManager;

public class PlaceholderAPISupport extends Mod {

    private final ChatManager plugin = ChatManager.get();

    private final ModRegistry modManager = this.plugin.getModManager();

    private PlaceholderAPIExpansion expansion;

    public PlaceholderAPISupport() {
        super(new FusionKey("chatmanager", "Vault"));
    }

    @Override
    public Mod init() {
        if (isEnabled()) {
            this.expansion = new PlaceholderAPIExpansion();
            this.expansion.register();
        }

        return this;
    }

    public Mod stop() {
        if (this.expansion != null) {
            this.expansion.unregister();
        }

        return this;
    }

    @Override
    public boolean isEnabled() {
        return this.modManager.getMod(ModSupport.placeholder_api).isEnabled();
    }
}