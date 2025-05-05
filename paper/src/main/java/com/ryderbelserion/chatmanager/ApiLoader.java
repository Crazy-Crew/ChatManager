package com.ryderbelserion.chatmanager;

import com.ryderbelserion.chatmanager.api.chat.*;
import com.ryderbelserion.chatmanager.api.chat.logging.PreviousCmdData;
import com.ryderbelserion.chatmanager.api.chat.logging.PreviousMsgData;
import com.ryderbelserion.chatmanager.api.cooldowns.ChatCooldowns;
import com.ryderbelserion.chatmanager.api.cooldowns.CmdCooldowns;
import com.ryderbelserion.chatmanager.api.cooldowns.CooldownTask;

public class ApiLoader {

    private PreviousCmdData previousCmdData;
    private PreviousMsgData previousMsgData;

    private ChatCooldowns chatCooldowns;
    private CmdCooldowns cmdCooldowns;
    private CooldownTask cooldownTask;

    private UserRepliedData userRepliedData;

    public void load() {
        this.previousCmdData = new PreviousCmdData();
        this.previousMsgData = new PreviousMsgData();

        this.chatCooldowns = new ChatCooldowns();
        this.cmdCooldowns = new CmdCooldowns();
        this.cooldownTask = new CooldownTask();

        this.userRepliedData = new UserRepliedData();
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