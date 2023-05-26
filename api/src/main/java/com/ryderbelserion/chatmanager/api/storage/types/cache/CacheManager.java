package com.ryderbelserion.chatmanager.api.storage.types.cache;

import com.ryderbelserion.chatmanager.api.storage.types.cache.chat.*;
import com.ryderbelserion.chatmanager.api.storage.types.cache.chat.other.PreviousCmdDataMap;
import com.ryderbelserion.chatmanager.api.storage.types.cache.chat.other.PreviousMsgDataMap;
import com.ryderbelserion.chatmanager.api.storage.types.cache.chat.other.UserReplyDataSet;
import com.ryderbelserion.chatmanager.api.storage.types.cache.commands.CommandSpyData;
import com.ryderbelserion.chatmanager.api.storage.types.cache.commands.SocialSpyData;
import com.ryderbelserion.chatmanager.api.storage.types.cache.commands.toggles.ToggleChatData;
import com.ryderbelserion.chatmanager.api.storage.types.cache.commands.toggles.ToggleMentionsData;
import com.ryderbelserion.chatmanager.api.storage.types.cache.commands.toggles.ToggleMessageData;
import com.ryderbelserion.chatmanager.api.storage.types.cache.cooldowns.ChatCoolDowns;
import com.ryderbelserion.chatmanager.api.storage.types.cache.cooldowns.CmdCoolDowns;
import com.ryderbelserion.chatmanager.api.storage.types.cache.cooldowns.CoolDownTask;
import com.ryderbelserion.chatmanager.api.storage.types.cache.other.AntiBotCache;
import com.ryderbelserion.chatmanager.api.storage.types.cache.other.ChatMentionsCache;
import com.ryderbelserion.chatmanager.api.storage.types.cache.other.ChatUserCache;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.UUID;

public class CacheManager {

    // Chat Types
    private GlobalChatDataSet globalChatDataSet;
    private LocalChatDataSet localChatDataSet;
    private PerWorldChatDataSet perWorldChatDataSet;
    private SpyChatDataSet spyChatDataSet;
    private StaffChatDataSet staffChatDataSet;
    private WorldChatDataSet worldChatDataSet;

    private PreviousCmdDataMap previousCmdDataMap;
    private PreviousMsgDataMap previousMsgDataMap;
    private UserReplyDataSet userReplyDataSet;

    // Command Types
    private ToggleChatData toggleChatData;
    private ToggleMentionsData toggleMentionsData;
    private ToggleMessageData toggleMessageData;

    private CommandSpyData commandSpyData;
    private SocialSpyData socialSpyData;

    // Cooldown Types
    private ChatCoolDowns chatCoolDowns;
    private CmdCoolDowns cmdCoolDowns;
    private CoolDownTask coolDownTask;

    // Other
    private AntiBotCache antiBotCache;
    private ChatMentionsCache chatMentionsCache;
    private ChatUserCache chatUserCache;

    public void load() {
        this.globalChatDataSet = new GlobalChatDataSet();
        this.localChatDataSet = new LocalChatDataSet();
        this.perWorldChatDataSet = new PerWorldChatDataSet();
        this.spyChatDataSet = new SpyChatDataSet();
        this.staffChatDataSet = new StaffChatDataSet();
        this.worldChatDataSet = new WorldChatDataSet();

        this.previousCmdDataMap = new PreviousCmdDataMap();
        this.previousMsgDataMap = new PreviousMsgDataMap();

        this.userReplyDataSet = new UserReplyDataSet();

        // Command Types
        this.toggleChatData = new ToggleChatData();
        this.toggleMentionsData = new ToggleMentionsData();
        this.toggleMessageData = new ToggleMessageData();

        this.commandSpyData = new CommandSpyData();
        this.socialSpyData = new SocialSpyData();

        // Cooldown Types
        this.chatCoolDowns = new ChatCoolDowns();
        this.cmdCoolDowns = new CmdCoolDowns();
        this.coolDownTask = new CoolDownTask();

        // Other
        this.antiBotCache = new AntiBotCache();
        this.chatMentionsCache = new ChatMentionsCache();
        this.chatUserCache = new ChatUserCache();
    }

    public void stop(JavaPlugin plugin) {
        plugin.getServer().getScheduler().cancelTasks(plugin);

        plugin.getServer().getOnlinePlayers().forEach(player -> {
            UUID uuid = player.getUniqueId();

            getChatCoolDowns().removeUser(uuid);
            getCoolDownTask().removeUser(uuid);
            getCmdCoolDowns().removeUser(uuid);
        });
    }

    public GlobalChatDataSet getGlobalChatDataSet() {
        return this.globalChatDataSet;
    }

    public LocalChatDataSet getLocalChatDataSet() {
        return this.localChatDataSet;
    }

    public PerWorldChatDataSet getPerWorldChatDataSet() {
        return this.perWorldChatDataSet;
    }

    public WorldChatDataSet getWorldChatDataSet() {
        return this.worldChatDataSet;
    }

    public SpyChatDataSet getSpyChatDataSet() {
        return this.spyChatDataSet;
    }

    public StaffChatDataSet getStaffChatDataSet() {
        return this.staffChatDataSet;
    }

    public PreviousCmdDataMap getPreviousCmdDataMap() {
        return this.previousCmdDataMap;
    }

    public PreviousMsgDataMap getPreviousMsgDataMap() {
        return this.previousMsgDataMap;
    }

    public UserReplyDataSet getUserReplyDataSet() {
        return this.userReplyDataSet;
    }

    // Command Types
    public CommandSpyData getCommandSpyData() {
        return this.commandSpyData;
    }

    public SocialSpyData getSocialSpyData() {
        return this.socialSpyData;
    }

    public ToggleChatData getToggleChatData() {
        return this.toggleChatData;
    }

    public ToggleMentionsData getToggleMentionsData() {
        return this.toggleMentionsData;
    }

    public ToggleMessageData getToggleMessageData() {
        return this.toggleMessageData;
    }

    // Cooldowns
    public ChatCoolDowns getChatCoolDowns() {
        return this.chatCoolDowns;
    }

    public CmdCoolDowns getCmdCoolDowns() {
        return this.cmdCoolDowns;
    }

    public CoolDownTask getCoolDownTask() {
        return this.coolDownTask;
    }

    // Other
    public AntiBotCache getAntiBotCache() {
        return this.antiBotCache;
    }

    public ChatMentionsCache getChatMentionsCache() {
        return this.chatMentionsCache;
    }

    public ChatUserCache getChatUserCache() {
        return this.chatUserCache;
    }
}