package com.ryderbelserion.chatmanager.paper;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatManager extends JavaPlugin {

    @Override
    public void onEnable() {
        getComponentLogger().warn(MiniMessage.miniMessage().deserialize("<red>ChatManager {} is ready to go!".replace("{}", getPluginMeta().getVersion())));
    }
}