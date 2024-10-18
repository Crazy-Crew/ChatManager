package com.ryderbelserion.chatmanager.loader;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

public class ChatManagerLoader implements PluginLoader {

    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        //classpathBuilder.addLibrary(new JarLibrary(Path.of("dependency.jar")));

        MavenLibraryResolver resolver = new MavenLibraryResolver();

        //final String incendo_version = "2.0.0-beta.10";

        //resolver.addDependency(new Dependency(new DefaultArtifact("org.incendo:cloud-minecraft-extras:" + incendo_version), null));
        //resolver.addDependency(new Dependency(new DefaultArtifact("org.incendo:cloud-paper:" + incendo_version), null));

        resolver.addDependency(new Dependency(new DefaultArtifact("com.ryderbelserion.vital:paper:1.0.6"), null));

        resolver.addRepository(new RemoteRepository.Builder("paper", "default", "https://repo.papermc.io/repository/maven-public").build());

        resolver.addRepository(new RemoteRepository.Builder("crazycrew", "default", "https://repo.crazycrew.us/libraries").build());

        classpathBuilder.addLibrary(resolver);
    }
}