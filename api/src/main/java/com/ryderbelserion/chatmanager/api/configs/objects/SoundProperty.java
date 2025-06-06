package com.ryderbelserion.chatmanager.api.configs.objects;

import org.jetbrains.annotations.NotNull;

public class SoundProperty {

    public String value = "entity.villager.no";

    public double volume = 1.0;

    public float pitch = 1.0f;

    public void setValue(@NotNull final String value) {
        this.value = value;
    }

    public void setVolume(final double volume) {
        this.volume = volume;
    }

    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }

    public @NotNull final String getValue() {
        return this.value;
    }

    public final double getVolume() {
        return this.volume;
    }

    public final float getPitch() {
        return this.pitch;
    }
}