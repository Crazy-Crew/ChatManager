package com.ryderbelserion.chatmanager.core.api;

public interface IChatManager {

    void start();

    void refresh();

    void stop();

    //FileManager getManager();

    IUserManager getUserManager();

}