package me.h1dd3nxn1nja.chatmanager.support;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.chat.GlobalChatData;
import com.ryderbelserion.chatmanager.api.chat.LocalChatData;
import com.ryderbelserion.chatmanager.api.chat.PerWorldChatData;
import com.ryderbelserion.chatmanager.api.chat.SpyChatData;
import com.ryderbelserion.chatmanager.api.chat.StaffChatData;
import com.ryderbelserion.chatmanager.api.chat.UserRepliedData;
import com.ryderbelserion.chatmanager.api.chat.WorldChatData;
import com.ryderbelserion.chatmanager.api.chat.logging.PreviousCmdData;
import com.ryderbelserion.chatmanager.api.chat.logging.PreviousMsgData;
import com.ryderbelserion.chatmanager.api.cmds.CommandSpyData;
import com.ryderbelserion.chatmanager.api.cmds.SocialSpyData;
import com.ryderbelserion.chatmanager.api.cmds.ToggleChatData;
import com.ryderbelserion.chatmanager.api.cmds.ToggleMentionsData;
import com.ryderbelserion.chatmanager.api.cmds.ToggleMessageData;
import com.ryderbelserion.chatmanager.api.cooldowns.ChatCooldowns;
import com.ryderbelserion.chatmanager.api.cooldowns.CmdCooldowns;
import com.ryderbelserion.chatmanager.api.cooldowns.CooldownTask;
import com.ryderbelserion.chatmanager.api.misc.AntiBotData;
import com.ryderbelserion.chatmanager.managers.ServerManager;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;

import java.io.File;

public abstract class Global {

    protected final ChatManager plugin = ChatManager.get();

    protected final ServerManager serverManager = this.plugin.getServerManager();

    protected final Server server = this.plugin.getServer();

    protected final ConsoleCommandSender sender = this.server.getConsoleSender();

    protected final File dataFolder = this.plugin.getDataFolder();

    protected final ApiLoader apiLoader = this.plugin.api();

    protected final PreviousCmdData previousCmdData = this.apiLoader.getPreviousCmdData();

    protected final PreviousMsgData previousMsgData = this.apiLoader.getPreviousMsgData();

    protected final GlobalChatData globalChatData = this.apiLoader.getGlobalChatData();

    protected final LocalChatData localChatData = this.apiLoader.getLocalChatData();

    protected final PerWorldChatData perWorldChatData = this.apiLoader.getPerWorldChatData();

    protected final SpyChatData spyChatData = this.apiLoader.getSpyChatData();

    protected final StaffChatData staffChatData = this.apiLoader.getStaffChatData();

    protected final UserRepliedData userRepliedData = this.apiLoader.getUserRepliedData();

    protected final WorldChatData worldChatData = this.apiLoader.getWorldChatData();

    protected final CommandSpyData commandSpyData = this.apiLoader.getCommandSpyData();

    protected final SocialSpyData socialSpyData = this.apiLoader.getSocialSpyData();

    protected final ToggleChatData toggleChatData = this.apiLoader.getToggleChatData();

    protected final ToggleMentionsData toggleMentionsData = this.apiLoader.getToggleMentionsData();

    protected final ToggleMessageData toggleMessageData = this.apiLoader.getToggleMessageData();

    protected final ChatCooldowns chatCooldowns = this.apiLoader.getChatCooldowns();

    protected final CmdCooldowns cmdCooldowns = this.apiLoader.getCmdCooldowns();

    protected final CooldownTask cooldownTask = this.apiLoader.getCooldownTask();

    protected final AntiBotData antiBotData = this.apiLoader.getAntiBotData();

}