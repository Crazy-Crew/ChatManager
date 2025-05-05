package com.ryderbelserion.chatmanager.commands.suggestions;

import com.ryderbelserion.chatmanager.enums.core.PlayerState;
import org.incendo.cloud.annotations.processing.CommandContainer;
import org.incendo.cloud.annotations.suggestion.Suggestions;
import org.jetbrains.annotations.NotNull;
import java.util.stream.Stream;

@CommandContainer
public final class SuggestionsContainer {

    @Suggestions("spy-suggestions")
    public @NotNull Stream<@NotNull PlayerState> suggestions() {
        return Stream.of(PlayerState.COMMAND_SPY, PlayerState.SOCIAL_SPY);
    }
}