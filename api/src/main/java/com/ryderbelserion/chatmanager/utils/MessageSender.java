package com.ryderbelserion.chatmanager.utils;

import com.ryderbelserion.chatmanager.api.configs.types.PluginSettings;
import com.ryderbelserion.chatmanager.api.interfaces.Universal;
import com.ryderbelserion.stick.paper.StickLogger;
import com.ryderbelserion.stick.paper.utils.AdventureUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public class MessageSender implements Universal {

    public static void send(Audience audience, String component) {
        String messagePrefix = pluginSettings.getProperty(PluginSettings.COMMAND_PREFIX);
        boolean prefix = pluginSettings.getProperty(PluginSettings.COMMAND_PREFIX_TOGGLE);
        audience.sendMessage(prefix ? AdventureUtils.parse(messagePrefix, false).append(Component.text(component)) : Component.text(component));
    }

    public static void send(String component) {
        boolean isLogging = pluginSettings.getProperty(PluginSettings.VERBOSE_LOGGING);

        if (isLogging) StickLogger.info(component);
    }
}