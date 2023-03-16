package com.ryderbelserion;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.api.CrazyManager;

public interface Universal {

    ChatManager plugin = ChatManager.getPlugin();

    CrazyManager crazyManager = plugin.getCrazyManager();

}