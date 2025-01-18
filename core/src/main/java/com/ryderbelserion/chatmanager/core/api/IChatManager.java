package com.ryderbelserion.chatmanager.core.api;

import com.ryderbelserion.core.files.FileManager;

public interface IChatManager {

    void start();

    void refresh();

    void stop();

    FileManager getManager();

    UserManager getUserManager();

}