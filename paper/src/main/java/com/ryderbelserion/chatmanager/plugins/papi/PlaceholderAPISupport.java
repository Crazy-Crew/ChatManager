package com.ryderbelserion.chatmanager.plugins.papi;

import com.ryderbelserion.fusion.core.api.FusionKey;
import com.ryderbelserion.fusion.kyori.mods.ModManager;
import com.ryderbelserion.fusion.kyori.mods.ModSupport;
import com.ryderbelserion.fusion.kyori.mods.objects.Mod;
import com.ryderbelserion.fusion.paper.FusionPaper;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPISupport extends Mod {

    private final ChatManager plugin = ChatManager.get();

    private final ModManager modManager = this.plugin.getModManager();

    private PlaceholderAPIExpansion expansion;

    public PlaceholderAPISupport(@NotNull final FusionPaper fusion) {
        super(fusion);

        setKey(new FusionKey("chatmanager", "Vault"));
    }

    public Mod start() {
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