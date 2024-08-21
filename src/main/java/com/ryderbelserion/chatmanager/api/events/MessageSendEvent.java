package com.ryderbelserion.chatmanager.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MessageSendEvent extends Event implements Cancellable {

    private final Player player;
    private final Player target;

    private final String message;

    public MessageSendEvent(final Player player, final Player target, final String message) {
        this.player = player;
        this.target = target;
        this.message = message;
    }

    public final String getMessage() {
        return this.message;
    }

    public final Player getPlayer() {
        return this.player;
    }

    public final Player getTarget() {
        return this.target;
    }

    // event specific classes
    private boolean isCancelled = false;

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public final boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(final boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}