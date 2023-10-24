package com.ryderbelserion.chatmanager.paper;

import com.ryderbelserion.chatmanager.paper.api.misc.AntiBotData;
import com.ryderbelserion.chatmanager.paper.api.chat.*;
import com.ryderbelserion.chatmanager.paper.api.chat.logging.PreviousCmdData;
import com.ryderbelserion.chatmanager.paper.api.chat.logging.PreviousMsgData;
import com.ryderbelserion.chatmanager.paper.api.cmds.*;
import com.ryderbelserion.chatmanager.paper.api.cooldowns.ChatCooldowns;
import com.ryderbelserion.chatmanager.paper.api.cooldowns.CmdCooldowns;
import com.ryderbelserion.chatmanager.paper.api.cooldowns.CooldownTask;

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
        return this.socialSpyData;
    }

    public CommandSpyData getCommandSpyData() {
        return this.commandSpyData;
    }

    public AntiBotData getAntiBotData() {
        return this.antiBotData;
    }

    public StaffChatData getStaffChatData() {
        return this.staffChatData;
    }

    public SpyChatData getSpyChatData() {
        return this.spyChatData;
    }

    public LocalChatData getLocalChatData() {
        return this.localChatData;
    }

    public WorldChatData getWorldChatData() {
        return this.worldChatData;
    }

    public GlobalChatData getGlobalChatData() {
        return this.globalChatData;
    }

    public PerWorldChatData getPerWorldChatData() {
        return this.perWorldChatData;
    }

    public ToggleMessageData getToggleMessageData() {
        return this.toggleMessageData;
    }

    public ToggleChatData getToggleChatData() {
        return this.toggleChatData;
    }

    public ToggleMentionsData getToggleMentionsData() {
        return this.toggleMentionsData;
    }

    public PreviousCmdData getPreviousCmdData() {
        return this.previousCmdData;
    }

    public PreviousMsgData getPreviousMsgData() {
        return this.previousMsgData;
    }

    public ChatCooldowns getChatCooldowns() {
        return this.chatCooldowns;
    }

    public CmdCooldowns getCmdCooldowns() {
        return this.cmdCooldowns;
    }

    public CooldownTask getCooldownTask() {
        return this.cooldownTask;
    }

    public UserRepliedData getUserRepliedData() {
        return this.userRepliedData;
    }
}