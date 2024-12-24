package com.ryderbelserion.chatmanager.api;

import com.ryderbelserion.chatmanager.api.users.UserManager;
import org.jetbrains.annotations.NotNull;
import java.io.File;

public interface ChatManager {

    @NotNull UserManager getUserManager();

    File getDataFolder();

}