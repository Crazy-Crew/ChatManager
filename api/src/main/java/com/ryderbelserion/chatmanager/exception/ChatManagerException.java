package com.ryderbelserion.chatmanager.exception;

public class ChatManagerException extends IllegalStateException {

    public ChatManagerException(final String message, final Exception exception) {
        super(message, exception);
    }

    public ChatManagerException(final String message) {
        super(message);
    }
}