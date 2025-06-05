package com.ryderbelserion.chatmanager.api.configs.objects;

public class SoundProperty {

    public String value = "entity.villager.no";

    public double volume = 1.0;

    public float pitch = 1.0f;

    public void setValue(String value) {
        this.value = value;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public String getValue() {
        return this.value;
    }

    public double getVolume() {
        return this.volume;
    }

    public float getPitch() {
        return this.pitch;
    }
}