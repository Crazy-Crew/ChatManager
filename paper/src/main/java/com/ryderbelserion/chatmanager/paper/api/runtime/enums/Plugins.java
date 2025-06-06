package com.ryderbelserion.chatmanager.paper.api.runtime.enums;

import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.jetbrains.annotations.NotNull;

public enum Plugins {

    fusion_paper("com.ryderbelserion.fusion", "fusion-paper", "1.16.1"),

    config_me("ch.jalu", "configme", "1.4.3");

    private final String group;
    private final String id;
    private final String version;

    Plugins(@NotNull final String group, @NotNull final String id, @NotNull final String version) {
        this.group = group;
        this.id = id;
        this.version = version;
    }

    public @NotNull final Dependency asDependency() {
        return new Dependency(new DefaultArtifact(String.format("%s:%s:%s", this.group, this.id, this.version)), null);
    }
}