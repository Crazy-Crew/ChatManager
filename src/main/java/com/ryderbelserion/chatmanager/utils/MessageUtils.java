package com.ryderbelserion.chatmanager.utils;

import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.configs.types.PluginSettings;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import us.crazycrew.crazycore.paper.CrazyLogger;
import us.crazycrew.crazycore.paper.utils.AdventureUtils;

public class MessageUtils {

    private static final ChatManager plugin = ChatManager.getPlugin();

    public static void send(Audience audience, String component) {
        String prefix = plugin.getConfigBuilder().getPluginSettings().getProperty(PluginSettings.COMMAND_PREFIX);
        boolean isEnabled = plugin.getConfigBuilder().getPluginSettings().getProperty(PluginSettings.COMMAND_PREFIX_TOGGLE);

        audience.sendMessage(isEnabled ? AdventureUtils.parse(prefix, false).append(AdventureUtils.parse(component, false)) : AdventureUtils.parse(component, false));
    }

    public static void hover(Audience audience, String message, String text, String value, ClickEvent.Action action) {
        Component textComponent = AdventureUtils.parse(message, false)
                .hoverEvent(HoverEvent.showText(AdventureUtils.parse(text, false)))
                .clickEvent(ClickEvent.clickEvent(action, value));

        audience.sendMessage(textComponent);
    }

    public static void hover(Audience audience, String message, String text, String button, String value, ClickEvent.Action action) {
        Component textComponent = AdventureUtils.parse(message, false)
                .append(AdventureUtils.parse(button, false).hoverEvent(HoverEvent.showText(AdventureUtils.parse(text, false))))
                .clickEvent(ClickEvent.clickEvent(action, value));

        audience.sendMessage(textComponent);
    }

    public static void send(Audience audience, String component, boolean isEnabled) {
        String prefix = plugin.getConfigBuilder().getPluginSettings().getProperty(PluginSettings.COMMAND_PREFIX);

        audience.sendMessage(isEnabled ? AdventureUtils.parse(prefix, false).append(AdventureUtils.parse(component, false)) : AdventureUtils.parse(component, false));
    }

    public static void send(String component) {
        if (plugin.getConfigBuilder().getPluginSettings().getProperty(PluginSettings.VERBOSE_LOGGING)) CrazyLogger.info(component);
    }
}