package com.ryderbelserion.chatmanager.common.constants;

import net.kyori.adventure.key.Key;

public class MessageKeys {

    private static final String namespace = "chatmanager";

    public static final Key reload_plugin = Key.key(namespace, "reload_plugin");
    public static final Key feature_disabled = Key.key(namespace, "feature_disabled");
    public static final Key must_be_player = Key.key(namespace, "must_be_player");
    public static final Key must_be_console_sender = Key.key(namespace, "must_be_console_sender");
    public static final Key target_not_online = Key.key(namespace, "target_not_online");
    public static final Key target_same_player = Key.key(namespace, "target_same_player");
    public static final Key no_permission = Key.key(namespace, "no_permission");
    public static final Key inventory_not_empty = Key.key(namespace, "inventory_not_empty");
    public static final Key internal_error = Key.key(namespace, "internal_error");
    public static final Key message_empty = Key.key(namespace, "message_empty");

    public static final Key join_message = Key.key(namespace, "join_message");
    public static final Key quit_message = Key.key(namespace, "quit_message");

    public static final Key message_of_the_day = Key.key(namespace, "message_of_the_day");

}