package com.ryderbelserion.chatmanager.api.exceptions;

import com.ryderbelserion.vital.utils.Methods;
import java.util.List;

public final class UnavailableException extends IllegalStateException {

    private static final List<String> message = List.of(
            "The ChatManager API isn't loaded yet.",
            "",
            "A list of potential reasons:",
            " 1) ChatManager plugin did not enable, or is not installed.",
            " 2) The plugin using ChatManager does not include it as a softdepend.",
            " 3) The plugin is trying to use the api before the api enables."
    );

    public UnavailableException() {
        super(Methods.convertList(message));
    }
}