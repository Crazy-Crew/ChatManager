package com.ryderbelserion.chatmanager.v1.api;

import com.ryderbelserion.chatmanager.v1.api.chat.*;
import com.ryderbelserion.chatmanager.v1.api.cmds.*;
import com.ryderbelserion.chatmanager.v1.api.misc.AntiBotData;
import com.ryderbelserion.chatmanager.api.chat.*;
import com.ryderbelserion.chatmanager.v1.api.chat.logging.PreviousCmdData;
import com.ryderbelserion.chatmanager.v1.api.chat.logging.PreviousMsgData;
import com.ryderbelserion.chatmanager.api.cmds.*;
import com.ryderbelserion.chatmanager.v1.api.cooldowns.ChatCooldowns;
import com.ryderbelserion.chatmanager.v1.api.cooldowns.CmdCooldowns;
import com.ryderbelserion.chatmanager.v1.api.cooldowns.CooldownTask;

public class ApiLoader {

    private SocialSpyData socialSpyData;
    private CommandSpyData commandSpyData;

    private AntiBotData antiBotData;

    // Chat
    private SpyChatData spyChatData;
    private WorldChatData worldChatData;
    private GlobalChatData globalChatData;
    private LocalChatData localChatData;
    private StaffChatData staffChatData;
    private PerWorldChatData perWorldChatData;

    // Toggles
    private ToggleChatData toggleChatData;
    private ToggleMentionsData toggleMentionsData;
    private ToggleMessageData toggleMessageData;

    private PreviousCmdData previousCmdData;
    private PreviousMsgData previousMsgData;

    private ChatCooldowns chatCooldowns;
    private CmdCooldowns cmdCooldowns;
    private CooldownTask cooldownTask;

    private UserRepliedData userRepliedData;

    public void load() {
        this.socialSpyData = new SocialSpyData();

        this.commandSpyData = new CommandSpyData();

        this.staffChatData = new StaffChatData();
        this.antiBotData = new AntiBotData();

        this.spyChatData = new SpyChatData();
        this.localChatData = new LocalChatData();
        this.worldChatData = new WorldChatData();
        this.globalChatData = new GlobalChatData();
        this.perWorldChatData = new PerWorldChatData();

        this.toggleChatData = new ToggleChatData();
        this.toggleMentionsData = new ToggleMentionsData();
        this.toggleMessageData = new ToggleMessageData();

        this.previousCmdData = new PreviousCmdData();
        this.previousMsgData = new PreviousMsgData();

        this.chatCooldowns = new ChatCooldowns();
        this.cmdCooldowns = new CmdCooldowns();
        this.cooldownTask = new CooldownTask();

        this.userRepliedData = new UserRepliedData();
    }

    public SocialSpyData getSocialSpyData() {
        return socialSpyData;
    }

    public CommandSpyData getCommandSpyData() {
        return commandSpyData;
    }

    public AntiBotData getAntiBotData() {
        return antiBotData;
    }

    public StaffChatData getStaffChatData() {
        return staffChatData;
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

    public PerWorldChatData getPerWorldChatData() {
        return perWorldChatData;
    }

    public ToggleMessageData getToggleMessageData() {
        return toggleMessageData;
    }

    public ToggleChatData getToggleChatData() {
        return toggleChatData;
    }

    public ToggleMentionsData getToggleMentionsData() {
        return toggleMentionsData;
    }

    public PreviousCmdData getPreviousCmdData() {
        return previousCmdData;
    }

    public PreviousMsgData getPreviousMsgData() {
        return previousMsgData;
    }

    public ChatCooldowns getChatCooldowns() {
        return chatCooldowns;
    }

    public CmdCooldowns getCmdCooldowns() {
        return cmdCooldowns;
    }

    public CooldownTask getCooldownTask() {
        return cooldownTask;
    }

    public UserRepliedData getUserRepliedData() {
        return userRepliedData;
    }
}