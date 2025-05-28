package com.ryderbelserion.chatmanager.api.interfaces;

import com.ryderbelserion.fusion.core.files.FileManager;

public interface IChatManager {

    IUserManager getUserManager();

    FileManager getFileManager();

}