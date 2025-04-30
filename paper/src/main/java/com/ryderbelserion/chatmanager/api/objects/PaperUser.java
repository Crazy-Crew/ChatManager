package com.ryderbelserion.chatmanager.api.objects;

import com.ryderbelserion.chatmanager.enums.core.PlayerState;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class PaperUser {

    private final Audience audience;

    private final List<PlayerState> states = new ArrayList<>();

    public PaperUser(@NotNull final Audience audience) {
        this.audience = audience;
    }

    public void addState(@NotNull final PlayerState state) {
        this.states.add(state);
    }

    public void removeState(@NotNull final PlayerState state) {
        this.states.remove(state);
    }

    public boolean hasState(@NotNull final PlayerState state) {
        return this.states.contains(state);
    }

    public final Audience getAudience() {
        return this.audience;
    }
}