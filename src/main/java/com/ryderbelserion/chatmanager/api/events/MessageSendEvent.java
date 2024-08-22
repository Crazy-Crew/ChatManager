package com.ryderbelserion.chatmanager.api.events;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MessageSendEvent extends Event implements Cancellable {

    private final CommandSender sender;
    private final Player player;
    private final Player target;

    private final String message;

    public MessageSendEvent(final CommandSender sender, final Player target, final String message) {
        this.sender = sender;

        this.player = this.sender instanceof Player human ? human : null;

        this.target = target;
        this.message = message;
    }

    public MessageSendEvent(final CommandSender sender, final String message) {
        this.sender = sender;

        this.player = this.sender instanceof Player human ? human : null;
        this.target = this.player;

        this.message = message;
    }

    public @NotNull final String getMessage() {
        return this.message;
    }

    public @NotNull final CommandSender getSender() {
        return this.sender;
    }

    public @Nullable final Player getPlayer() {
        return this.player;
    }

    public @Nullable final Player getTarget() {
        return this.target;
    }

    public final boolean isPlayer() {
        return this.player != null;
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