package com.ryderbelserion.chatmanager.api;

import com.ryderbelserion.chatmanager.api.users.UserManager;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import java.util.Map;

/**
 * ChatManagers' API
 *
 * @author ryderbelserion
 * @version 0.0.1
 * @since 0.0.1
 */
public interface ChatManager {

    /**
     * Gets the instance of the user manager.
     *
     * @return {@link UserManager}
     * @since 0.0.1
     */
    @NotNull UserManager getUserManager();

    /**
     * Parses a value with platform specific placeholder handling
     *
     * @param value the value to parse
     * @return the parsed string
     */
    @NotNull String parse(@NotNull final Audience audience, @NotNull final String value, @NotNull final Map<String, String> placeholders);

}