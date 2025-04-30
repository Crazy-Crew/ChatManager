package com.ryderbelserion.chatmanager.managers;

import com.ryderbelserion.chatmanager.api.objects.PaperServer;

public class ServerManager {

    private final PaperServer server;

    public ServerManager() {
        this.server = new PaperServer();
    }

    public final PaperServer getServer() {
        return this.server;
    }
}