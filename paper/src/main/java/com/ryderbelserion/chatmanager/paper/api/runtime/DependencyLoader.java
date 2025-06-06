package com.ryderbelserion.chatmanager.paper.api.runtime;

import com.ryderbelserion.chatmanager.paper.api.runtime.enums.Plugins;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

public class DependencyLoader implements PluginLoader {

    @Override
    public void classloader(@NotNull final PluginClasspathBuilder classpathBuilder) {
        final MavenLibraryResolver resolver = new MavenLibraryResolver();

        resolver.addRepository(new RemoteRepository.Builder("crazycrewReleases", "default", "https://repo.crazycrew.us/releases/").build());

        resolver.addRepository(new RemoteRepository.Builder("paper", "default", "https://repo.papermc.io/repository/maven-public/").build());

        // Add configme
        resolver.addDependency(Plugins.config_me.asDependency()); // core, i.e. when we focus on other modules, we'll shade this.

        // Add fusion paper api
        resolver.addDependency(Plugins.fusion_paper.asDependency()); // paper only, so we don't need to shade this.

        // Populate resolvers
        classpathBuilder.addLibrary(resolver);
    }
}