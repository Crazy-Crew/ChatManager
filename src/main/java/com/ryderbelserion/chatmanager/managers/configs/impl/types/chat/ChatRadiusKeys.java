package com.ryderbelserion.chatmanager.managers.configs.impl.types.chat;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class ChatRadiusKeys implements SettingsHolder {

    @Comment("Should the chat radius feature be enabled?")
    public static final Property<Boolean> chat_radius_enabled = newProperty("features.chat_radius.enabled", false);

    @Comment({
            "A player joining the server, they will be automatically put into this channel.",
            "",
            "Available Channels:",
            " ⤷ Global",
            " ⤷ World",
            " ⤷ Local"
    })
    public static final Property<String> chat_radius_default_channel = newProperty("features.chat_radius.default_channel", "Global");

    @Comment("The prefix that's sent in local chat.")
    public static final Property<String> chat_radius_local_chat_prefix = newProperty("features.chat_radius.local_chat.prefix", "&7[&cLocal&7]");

    @Comment({
            "If you put this symbol in front of your message, it will override any channel which you are in,",
            "sending the message to all players in the server, then keeps you in local chat.",
            "",
            "If you do not want this override enabled, Make the message blank."
    })
    public static final Property<String> chat_radius_local_chat_override_symbol = newProperty("features.chat_radius.local_chat.override_symbol", "#");

    // Global Chat
    @Comment("The prefix that's sent in global chat.")
    public static final Property<String> chat_radius_global_chat_prefix = newProperty("features.chat_radius.global_chat.prefix", "&7[&bGlobal&7]");

    @Comment({
            "If you put this symbol in front of your message, it will override any channel which you are in,",
            "sending the message to all players in the server, then keeps you in global chat.",
            "",
            "If you do not want this override enabled, Make the message blank."
    })
    public static final Property<String> chat_radius_global_chat_override_symbol = newProperty("features.chat_radius.global_chat.override_symbol", "!");

    // World Chat
    @Comment("The prefix that's sent in world chat.")
    public static final Property<String> chat_radius_world_chat_prefix = newProperty("features.chat_radius.world_chat.prefix", "&7[&dWorld&7]");

    @Comment({
            "If you put this symbol in front of your message, it will override any channel which you are in,",
            "sending the message to all players in the server, then keeps you in world chat.",
            "",
            "If you do not want this override enabled, Make the message blank."
    })
    public static final Property<String> chat_radius_world_chat_override_symbol = newProperty("features.chat_radius.world_chat.override_symbol", "$");

    @Comment("The maximum distance players will receive the sender's messages.")
    public static final Property<Integer> chat_radius_block_distance = newProperty("features.chat_radius.block_distance", 250);

    @Comment("Enable chat radius spy on join.")
    public static final Property<Boolean> chat_radius_enable_spy_on_join = newProperty("features.chat_radius.enable_spy_on_join", false);

}