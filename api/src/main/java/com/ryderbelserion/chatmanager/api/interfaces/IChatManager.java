package com.ryderbelserion.chatmanager.api.interfaces;

import com.ryderbelserion.fusion.core.files.FileManager;
import org.jetbrains.annotations.NotNull;

public interface IChatManager {

    @NotNull IUserManager getUserManager();

    @NotNull FileManager getFileManager();

}