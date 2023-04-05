package com.ryderbelserion.chatmanager;

import com.ryderbelserion.chatmanager.api.AntiBotData;
import com.ryderbelserion.chatmanager.api.chat.*;
import com.ryderbelserion.chatmanager.api.cmds.CommandSpyData;
import com.ryderbelserion.chatmanager.api.cmds.SocialSpyData;
import com.ryderbelserion.chatmanager.api.cmds.ToggleMessageData;

public class ApiLoader {

    private SocialSpyData socialSpyData;
    private CommandSpyData commandSpyData;
    private ToggleMessageData toggleMessageData;
    private StaffChatData staffChatData;
    private AntiBotData antiBotData;
    private SpyChatData spyChatData;
    private WorldChatData worldChatData;
    private GlobalChatData globalChatData;
    private LocalChatData localChatData;

    public void load() {
        this.socialSpyData = new SocialSpyData();

        this.commandSpyData = new CommandSpyData();
        this.toggleMessageData = new ToggleMessageData();

        this.staffChatData = new StaffChatData();
        this.antiBotData = new AntiBotData();

        this.spyChatData = new SpyChatData();
        this.localChatData = new LocalChatData();
        this.worldChatData = new WorldChatData();
        this.globalChatData = new GlobalChatData();
    }

    public SocialSpyData getSocialSpyData() {
        return socialSpyData;
    }

    public CommandSpyData getCommandSpyData() {
        return commandSpyData;
    }

    public ToggleMessageData getToggleMessageData() {
        return toggleMessageData;
    }

    public StaffChatData getStaffChatData() {
        return staffChatData;
    }

    public AntiBotData getAntiBotData() {
        return antiBotData;
    }

    public SpyChatData getSpyChatData() {
        return spyChatData;
    }

    public LocalChatData getLocalChatData() {
        return localChatData;
    }

    public WorldChatData getWorldChatData() {
        return worldChatData;
    }

    public GlobalChatData getGlobalChatData() {
        return globalChatData;
    }
}