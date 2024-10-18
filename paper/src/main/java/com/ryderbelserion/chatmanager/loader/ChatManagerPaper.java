package com.ryderbelserion.chatmanager.loader;

import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.vital.paper.Vital;

public class ChatManagerPaper extends Vital {

    private final ChatManager chatManager;

    public ChatManagerPaper() {
        this.chatManager = new ChatManager(this);
    }

    @Override
    public void onLoad() {
        this.chatManager.onLoad();
    }

    @Override
    public void onEnable() {
        this.chatManager.onEnable();
    }

    @Override
    public void onDisable() {
        this.chatManager.onDisable();
    }
}