package com.ryderbelserion.chatmanager.api.objects;

import com.ryderbelserion.chatmanager.enums.core.ServerState;
import java.util.ArrayList;
import java.util.List;

public class PaperServer {

    private final List<ServerState> states = new ArrayList<>();

    public void addState(final ServerState state) {
        this.states.add(state);
    }

    public void removeState(final ServerState state) {
        this.states.remove(state);
    }

    public boolean hasState(final ServerState state) {
        return this.states.contains(state);
    }
}