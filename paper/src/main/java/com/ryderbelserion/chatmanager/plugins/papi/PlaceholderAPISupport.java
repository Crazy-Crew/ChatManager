package com.ryderbelserion.chatmanager.plugins.papi;

import com.ryderbelserion.fusion.kyori.mods.objects.Mod;
import com.ryderbelserion.fusion.paper.FusionPaper;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPISupport extends Mod {

    private PlaceholderAPIExpansion expansion;

    public PlaceholderAPISupport(@NotNull final FusionPaper fusion) {
        super(fusion);
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
}