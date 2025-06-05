package com.ryderbelserion.chatmanager.paper.api.runtime.enums;

import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;

public enum Plugins {

    fusion_paper("com.ryderbelserion.fusion", "fusion-paper", "1.14.2"),

    cloud_extras("org.incendo", "cloud-minecraft-extras", "2.0.0-beta.10"),
    cloud_annotations("org.incendo", "cloud-annotations", "2.0.0"),
    cloud_paper("org.incendo", "cloud-paper", "2.0.0-beta.10"),

    config_me("ch.jalu", "configme", "1.4.3");

    private final String group;
    private final String id;
    private final String version;

    Plugins(final String group, final String id, final String version) {
        this.group = group;
        this.id = id;
        this.version = version;
    }

    public Dependency asDependency() {
        return new Dependency(new DefaultArtifact(String.format("%s:%s:%s", this.group, this.id, this.version)), null);
    }
}