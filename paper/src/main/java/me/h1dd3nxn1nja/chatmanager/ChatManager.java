package me.h1dd3nxn1nja.chatmanager;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.managers.ServerManager;
import com.ryderbelserion.fusion.kyori.mods.ModManager;
import com.ryderbelserion.fusion.paper.FusionPaper;
import com.ryderbelserion.fusion.paper.api.files.PaperFileManager;
import me.corecraft.chatmanager.paper.ChatManagerPlatform;
import me.h1dd3nxn1nja.chatmanager.support.PluginHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ChatManager extends JavaPlugin {

    public static ChatManager get() {
        return JavaPlugin.getPlugin(ChatManager.class);
    }

    private ChatManagerPlatform platform;

    @Override
    public void onEnable() {
        this.platform = new ChatManagerPlatform(this, new FusionPaper(getFile(), this));
        this.platform.start(getServer().getConsoleSender());
    }

    @Override
    public void onDisable() {
        if (this.platform != null) {
            this.platform.stop();
        }
    }

    public @NotNull final ServerManager getServerManager() {
        return this.platform.getServerManager();
    }

    public @NotNull final PluginHandler getPluginManager() {
        return this.platform.getPluginManager();
    }

    public @NotNull final ChatManagerPlatform getPlatform() {
        return this.platform;
    }

    public @NotNull final PaperFileManager getFileManager() {
        return this.platform.getLegacyFileManager();
    }

    public @NotNull final ModManager getModManager() {
        return this.platform.getModManager();
    }

    public @NotNull final ApiLoader api() {
        return this.platform.api();
    }
}