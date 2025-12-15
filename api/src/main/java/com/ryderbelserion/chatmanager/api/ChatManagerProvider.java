package com.ryderbelserion.chatmanager.api;

import com.ryderbelserion.chatmanager.api.interfaces.platform.IChatManager;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public class ChatManagerProvider {

    @ApiStatus.Internal
    private ChatManagerProvider() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static IChatManager instance;

    @ApiStatus.Internal
    public static IChatManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ChatManager API has not been initialized yet");
        }

        return instance;
    }

    @ApiStatus.Internal
    public static void register(@NotNull final IChatManager instance) {
        ChatManagerProvider.instance = instance;
    }

    @ApiStatus.Internal
    public static void unregister() {
        ChatManagerProvider.instance = null;
    }
}