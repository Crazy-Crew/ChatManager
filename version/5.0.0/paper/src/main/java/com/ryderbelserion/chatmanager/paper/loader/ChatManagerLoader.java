package com.ryderbelserion.chatmanager.paper.loader;

import com.ryderbelserion.chatmanager.paper.api.enums.Plugins;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

public class ChatManagerLoader implements PluginLoader {

    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        final MavenLibraryResolver resolver = new MavenLibraryResolver();

        resolver.addRepository(new RemoteRepository.Builder("crazycrewReleases", "default", "https://repo.crazycrew.us/releases/").build());

        // Add fusion api
        //resolver.addDependency(Plugins.fusion_paper.asDependency());

        resolver.addRepository(new RemoteRepository.Builder("paper", "default", "https://repo.papermc.io/repository/maven-public").build());

        // Add configme
        resolver.addDependency(Plugins.configme.asDependency());

        // Add cloud api
        resolver.addDependency(Plugins.cloud_annotations.asDependency());
        resolver.addDependency(Plugins.cloud_extras.asDependency());
        resolver.addDependency(Plugins.cloud_paper.asDependency());

        classpathBuilder.addLibrary(resolver);
    }
}