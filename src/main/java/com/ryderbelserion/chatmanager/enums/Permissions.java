package com.ryderbelserion.chatmanager.enums;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import java.util.HashMap;

public enum Permissions {

    FORMATTING_ALL("formatting.all", "Allows a player to use formatting for messages.", PermissionDefault.OP, new HashMap<>() {{
        put("chatmanager.chat-color.all", true);
        put("chatmanager.chat-format.all", true);
    }}),
    CHAT_COLOR_ALL("chat-color.all", "Allows a player to use all color formatting.", PermissionDefault.OP, new HashMap<>() {{
        put("chatmanager.color.green", true);
        put("chatmanager.color.aqua", true);
        put("chatmanager.color.red", true);
        put("chatmanager.color.light-purple", true);
        put("chatmanager.color.yellow", true);
        put("chatmanager.color.white", true);
        put("chatmanager.color.black", true);
        put("chatmanager.color.dark-blue", true);
        put("chatmanager.color.dark-green", true);
        put("chatmanager.color.dark-aqua", true);
        put("chatmanager.color.dark-red", true);
        put("chatmanager.color.dark-purple", true);
        put("chatmanager.color.gold", true);
        put("chatmanager.color.gray", true);
        put("chatmanager.color.dark-gray", true);
        put("chatmanager.color.blue", true);
    }}),
    CHAT_FORMAT_ALL("chat-format.all", "Allows a player to use all message formatting.", PermissionDefault.OP, new HashMap<>() {{
        put("chatmanager.format.obfuscated", true);
        put("chatmanager.format.bold", true);
        put("chatmanager.format.strikethrough", true);
        put("chatmanager.format.underline", true);
        put("chatmanager.format.italic", true);
        put("chatmanager.format.reset", true);
    }}),
    SIGN_COLOR_ALL("sign.color", "Allows players to use colors in signs.", PermissionDefault.OP),
    SIGN_FORMAT_ALL("sign.format", "Allows players to use formatting in signs.", PermissionDefault.OP),
    COLOR_GREEN("color.green", "Allows players to use &a", PermissionDefault.OP),
    COLOR_AQUA("color.aqua", "Allows players to use &b", PermissionDefault.OP),
    COLOR_RED("color.red", "Allows players to use &c", PermissionDefault.OP),
    COLOR_LIGHT_PURPLE("color.light-purple", "Allows players to use &d", PermissionDefault.OP),
    COLOR_YELLOW("color.yellow", "Allows players to use &e", PermissionDefault.OP),
    COLOR_WHITE("color.white", "Allows players to use &f", PermissionDefault.OP),
    COLOR_BLACK("color.black", "Allows players to use &0", PermissionDefault.OP),
    COLOR_DARK_BLUE("color.dark-blue", "Allows players to use &1", PermissionDefault.OP),
    COLOR_DARK_GREEN("color.dark-green", "Allows players to use &2", PermissionDefault.OP),
    COLOR_DARK_AQUA("color.dark-aqua", "Allows players to use &3", PermissionDefault.OP),
    COLOR_DARK_RED("color.dark-red", "Allows players to use &4", PermissionDefault.OP),
    COLOR_DARK_PURPLE("color.dark-purple", "Allows players to use &5", PermissionDefault.OP),
    COLOR_GOLD("color.gold", "Allows players to use &6", PermissionDefault.OP),
    COLOR_GRAY("color.gray", "Allows players to use &7", PermissionDefault.OP),
    COLOR_DARK_GRAY("color.dark-gray", "Allows players to use &8", PermissionDefault.OP),
    COLOR_BLUE("color.blue", "Allows players to use &9", PermissionDefault.OP),
    FORMAT_OBFUSCATED("format.obfuscated", "Allows players to use &k", PermissionDefault.OP),
    FORMAT_BOLD("format.bold", "Allows players to use &l", PermissionDefault.OP),
    FORMAT_STRIKETHROUGH("format.strikethrough", "Allows players to use &m", PermissionDefault.OP),
    FORMAT_UNDERLINE("format.underline", "Allows players to use &n", PermissionDefault.OP),
    FORMAT_ITALIC("format.italic", "Allows players to use &o", PermissionDefault.OP),
    FORMAT_RESET("format.reset", "Allows players to use &r", PermissionDefault.OP),
    PREVIEW_ACTIONBAR("preview.actionbar", "Shows action bar on join", PermissionDefault.OP),
    PREVIEW_TITLE("preview.title", "Shows title on join", PermissionDefault.OP),
    BYPASS_DUPE_CHAT("bypass.dupe.chat", "Permission to use repetitive messages.", PermissionDefault.OP),
    BYPASS_DUPE_COMMAND("bypass.dupe.command", "Permission to use repetitive commands", PermissionDefault.OP),
    BYPASS_GRAMMAR("bypass.grammar", "Permission to bypass the grammar checks.", PermissionDefault.OP),
    BYPASS_MUTE_CHAT("bypass.mutechat", "Permission to bypass muted chats", PermissionDefault.OP),
    BYPASS_CAPS("bypass.caps", "Permission to bypass caps checker.", PermissionDefault.OP),
    BYPASS_CLEAR_CHAT("bypass.clearchat", "Ability to bypass clear chat.", PermissionDefault.OP),
    BYPASS_COMMAND_SPY("bypass.commandspy", "Ability to bypass command spy.", PermissionDefault.OP),
    BYPASS_SOCIAL_SPY("bypass.socialspy", "Ability to prevent messages being seen by social spy", PermissionDefault.OP),
    BYPASS_IGNORED("bypass.ignored", "Ability to send private messages to players that are ignoring you.", PermissionDefault.OP),
    BYPASS_CLEAR_CHAT_ON_JOIN("bypass.clearchat.onjoin", "Ability to bypass clearchat on join", PermissionDefault.OP),
    BYPASS_BANNED_COMMANDS("bypass.bannedcommands", "Permission to bypass the banned commands checker.", PermissionDefault.OP),
    NOTIFY_BANNED_COMMANDS("notify.bannedcommands", "Permission to get notified when a player uses a banned command.", PermissionDefault.OP),
    BYPASS_COLON_COMMANDS("bypass.coloncommands", "Permission to use colons in commands.", PermissionDefault.OP),
    BYPASS_CHAT_DELAY("bypass.chatdelay", "Permission to bypass the chat delay.", PermissionDefault.OP),
    BYPASS_COMMAND_DELAY("bypass.commanddelay", "Permission to bypass the command delay.", PermissionDefault.OP),
    BYPASS_ANTI_BOT("bypass.antibot", "Permission to talk in chat without moving on join.", PermissionDefault.OP),
    BYPASS_ANTI_UNICODE("bypass.antiunicode", "Permission to bypass the anti-unicode checker.", PermissionDefault.OP),
    NOTIFY_ANTI_UNICODE("notify.antiunicode", "Permission to get notified when a player uses special characters in chat.", PermissionDefault.OP),
    BYPASS_ANTI_SWEAR("bypass.antiswear", "Permission to bypass the anti-swear checker", PermissionDefault.OP),
    NOTIFY_ANTI_SWEAR("notify.antiswear", "Permission to get notified when a player swears in chat.", PermissionDefault.OP),
    BYPASS_ANTI_ADVERTISING("bypass.antiadvertising", "Permission to bypass the anti-advertising checker.", PermissionDefault.OP),
    NOTIFY_ANTI_ADVERTISING("notify.antiadvertising", "Permission to get notified when players advertise in chat.", PermissionDefault.OP),
    BYPASS_AFK("bypass.afk", "Ability to send private messages to players that are afk.", PermissionDefault.OP),
    BYPASS_CHATRADIUS("bypass.chatradius", "Ability to bypass the chat radius", PermissionDefault.OP),
    BYPASS_SPECTATOR("bypass.spectator", "Ability to send messages to staff in spectator mode", PermissionDefault.OP),
    BYPASS_VANISH("bypass.vanish", "Ability to send messages to players in vanish", PermissionDefault.OP),
    BYPASS_TOGGLE_PM("bypass.togglepm", "Ability to send private messages to players even if they have them turned off", PermissionDefault.OP),
    BYPASS_TOGGLE_MENTIONS("bypass.togglementions", "Ability to send private messages to players even if they have them turned off", PermissionDefault.OP),
    CHAT_RADIUS_HELP("chatradius.help", "Shows chatradius help commands", PermissionDefault.OP),
    CHAT_RADIUS_GLOBAL_OVERRIDE("chatradius.global.override", "Ability to override all chat radius", PermissionDefault.OP),
    CHAT_RADIUS_LOCAL_OVERRIDE("chatradius.local.override", "Ability to override local chat radius", PermissionDefault.OP),
    CHAT_RADIUS_WORLD_OVERRIDE("chatradius.world.override", "Ability to override world chat radius", PermissionDefault.OP),
    MENTION("mention", "Ability to send mention notifications to players", PermissionDefault.OP),
    RECEIVE_MENTION("mention.receive", "Ability to receive mention notifications", PermissionDefault.OP),
    MENTION_EVERYONE("mention.everyone", "Send everyone mention notifications", PermissionDefault.OP),
    COMMAND_ANTISWEAR_HELP("antiswear.help", "Permission to view all the commands for the anti swear.", PermissionDefault.OP),
    COMMAND_ANTISWEAR_ADD("antiswear.add", "Permission to add a word to the bannedwords.yml file.", PermissionDefault.OP),
    COMMAND_ANTISWEAR_LIST("antiswear.list", "Permission to view a list of all the banned words.", PermissionDefault.OP),
    COMMAND_ANTISWEAR_REMOVE("antiswear.remove", "Permission to remove a word from the bannedwords.yml file.", PermissionDefault.OP),
    COMMAND_AUTOBROADCAST_HELP("autobroadcast.help", "Permission to view all the commands for auto broadcast.", PermissionDefault.OP),
    COMMAND_AUTOBROADCAST_LIST("autobroadcast.list", "Permission to view a list of all the auto broadcasts in a world.", PermissionDefault.OP),
    COMMAND_AUTOBROADCAST_ADD("autobroadcast.add", "Permission to add new auto broadcast messages to the game.", PermissionDefault.OP),
    COMMAND_AUTOBROADCAST_CREATE("autobroadcast.create", "Permission to create new auto broadcast world to the game.", PermissionDefault.OP),
    COMMAND_BANNEDCOMMANDS_HELP("bannedcommands.help", "Permission to view all the commands for banned commands.", PermissionDefault.OP),
    COMMAND_BANNEDCOMMANDS_ADD("bannedcommands.add", "Permission to add a command to the bannedcommands.yml file.", PermissionDefault.OP),
    COMMAND_BANNEDCOMMANDS_LIST("bannedcommands.list", "Permission to view a list of all the banned commands.", PermissionDefault.OP),
    COMMAND_BANNEDCOMMANDS_REMOVE("bannedcommands.remove", "Permission to remove a command from the bannedcommands.yml file.", PermissionDefault.OP),
    COMMAND_ANNOUNCEMENT("announcement", "Ability to use /announcement", PermissionDefault.OP),
    COMMAND_BROADCAST("broadcast", "Ability to use /broadcast", PermissionDefault.OP),
    COMMAND_CHATRADIUS("chatradius", "Ability to use chat radius", PermissionDefault.OP),
    COMMAND_CHATRADIUS_LOCAL("chatradius.local", "Ability to local chat radius", PermissionDefault.OP),
    COMMAND_CHATRADIUS_GLOBAL("chatradius.global", "Ability to use global chat radius", PermissionDefault.OP),
    COMMAND_CHATRADIUS_WORLD("chatradius.world", "Ability to use world chat radius", PermissionDefault.OP),
    COMMAND_CHATRADIUS_SPY("chatradius.spy", "Ability to spy on chat radius messages", PermissionDefault.OP),
    COMMAND_CLEARCHAT("clearchat", "Ability to use the command /clearchat", PermissionDefault.OP),
    COMMAND_DEBUG("debug", "Permission to debug the plugin.", PermissionDefault.OP),
    COMMAND_LISTS_PLAYERS("lists.players", "Ability to use /list", PermissionDefault.OP),
    COMMAND_STAFF("chatmanager.staff", "Ability to do something", PermissionDefault.OP),
    COMMAND_LISTS_STAFF("lists.staff", "Ability to use /staff", PermissionDefault.OP),
    COMMAND_MESSAGE("message", "Send messages to other players", PermissionDefault.OP),
    COMMAND_MESSAGE_SELF("message.self", "Send messages to yourself because you have no friends.", PermissionDefault.OP),
    COMMAND_MUTECHAT("mutechat", "/mutechat", PermissionDefault.OP),
    COMMAND_MUTECHAT_SILENT("mutechat.silent", "/mutechat -s", PermissionDefault.OP),
    COMMAND_PERWORLDCHAT("perworldchat", "/perworldchat", PermissionDefault.OP),
    COMMAND_PING("ping", "/ping", PermissionDefault.OP),
    COMMAND_PING_OTHERS("ping.others", "/ping <player>", PermissionDefault.OP),
    COMMAND_RELOAD("reload", "Reloads the plugin", PermissionDefault.OP),
    COMMAND_REPLY("reply", "/reply <message>", PermissionDefault.OP),
    COMMAND_RULES("rules", "/rules", PermissionDefault.OP),
    COMMAND_WARNING("warning", "/warning", PermissionDefault.OP),
    TOGGLE_MENTIONS("toggle.togglementions", "Ability to use the command /togglementions", PermissionDefault.OP),
    TOGGLE_CHAT("toggle.chat", "Ability to use the command /togglechat", PermissionDefault.OP),
    TOGGLE_STAFF_CHAT("staffchat", "Ability to use the command /staffchat", PermissionDefault.OP),
    SOCIAL_SPY("socialspy", "Ability to use social spy", PermissionDefault.OP),
    COMMAND_SPY("commandspy", "Ability to use command spy", PermissionDefault.OP),
    TOGGLE_PM("toggle.pm", "Ability to use the command /togglepm", PermissionDefault.OP),
    COMMAND_CHATRADIUS_ALL("chatradius.all", "Permission for all radius commands", PermissionDefault.FALSE, new HashMap<>() {{
        put("chatmanager.chatradius", true);
        put("chatmanager.chatradius.spy", true);
        put("chatmanager.chatradius.local", true);
        put("chatmanager.chatradius.global", true);
        put("chatmanager.chatradius.world", true);
        put("chatmanager.chatradius.global.override", true);
        put("chatmanager.chatradius.local.override", true);
        put("chatmanager.chatradius.world.override", true);
    }}),
    BYPASS_ALL("bypass.all", "Bypass all checks", PermissionDefault.FALSE, new HashMap<>() {{
        put("chatmanager.bypass.afk", true);
        put("chatmanager.bypass.antiadvertising", true);
        put("chatmanager.bypass.antibot", true);
        put("chatmanager.bypass.antiswear", true);
        put("chatmanager.bypass.antiunicode", true);
        put("chatmanager.bypass.bannedcommands", true);
        put("chatmanager.bypass.caps", true);
        put("chatmanager.bypass.chatdelay", true);
        put("chatmanager.bypass.chatradius", true);
        put("chatmanager.bypass.clearchat", true);
        put("chatmanager.bypass.clearchat.onjoin", true);
        put("chatmanager.bypass.coloncommands", true);
        put("chatmanager.bypass.commanddelay", true);
        put("chatmanager.bypass.commandspy", true);
        put("chatmanager.bypass.dupe.command", true);
        put("chatmanager.bypass.dupe.chat", true);
        put("chatmanager.bypass.grammar", true);
        put("chatmanager.bypass.ignored", true);
        put("chatmanager.bypass.mutechat", true);
        put("chatmanager.bypass.socialspy", true);
        put("chatmanager.bypass.spectator", true);
        put("chatmanager.bypass.togglepm", true);
        put("chatmanager.bypass.vanish", true);
    }}),
    COMMANDS_ALL("commands.all", "Gives all commands", PermissionDefault.FALSE, new HashMap<>() {{
        put("chatmanager.antiswear.help", true);
        put("chatmanager.antiswear.add", true);
        put("chatmanager.antiswear.list", true);
        put("chatmanager.antiswear.remove", true);
        put("chatmanager.autobroadcast.help", true);
        put("chatmanager.autobroadcast.add", true);
        put("chatmanager.autobroadcast.create", true);
        put("chatmanager.autobroadcast.list", true);
        put("chatmanager.bannedcommands.help", true);
        put("chatmanager.bannedcommands.add", true);
        put("chatmanager.bannedcommands.list", true);
        put("chatmanager.bannedcommands.remove", true);
        put("chatmanager.announcement", true);
        put("chatmanager.broadcast", true);
        put("chatmanager.chatradius.all", true);
        put("chatmanager.clearchat", true);
        put("chatmanager.commandspy", true);
        put("chatmanager.debug", true);
        put("chatmanager.lists.players", true);
        put("chatmanager.lists.staff", true);
        put("chatmanager.message", true);
        put("chatmanager.message.self", true);
        put("chatmanager.mutechat.silent", true);
        put("chatmanager.mutechat", true);
        put("chatmanager.perworldchat", true);
        put("chatmanager.ping", true);
        put("chatmanager.ping.others", true);
        put("chatmanager.preview.title", true);
        put("chatmanager.preview.actionbar", true);
        put("chatmanager.reload", true);
        put("chatmanager.reply", true);
        put("chatmanager.rules", true);
        put("chatmanager.socialspy", true);
        put("chatmanager.staffchat", true);
        put("chatmanager.toggle.chat", true);
        put("chatmanager.toggle.pm", true);
        put("chatmanager.warning", true);
    }}),
    NOTIFY_ALL("notify.all", "Notified for everything", PermissionDefault.FALSE, new HashMap<>() {{
        put("chatmanager.notify.antiadvertising", true);
        put("chatmanager.notify.antiswear", true);
        put("chatmanager.notify.antiunicode", true);
        put("chatmanager.notify.bannedcommands", true);
    }}),
    ALL("*", "Gives everything", PermissionDefault.OP, new HashMap<>() {{
        put("chatmanager.bypass.all", true);
        put("chatmanager.commands.all", true);
        put("chatmanager.chatradius.all", true);
        put("chatmanager.notify.all", true);
    }});

    private final String node;
    private final String description;
    private final PermissionDefault isDefault;
    private final HashMap<String, Boolean> children;

    Permissions(String node, String description, PermissionDefault isDefault, HashMap<String, Boolean> children) {
        this.node = node;
        this.description = description;

        this.isDefault = isDefault;

        this.children = children;
    }

    Permissions(String node, String description, PermissionDefault isDefault) {
        this.node = node;
        this.description = description;

        this.isDefault = isDefault;
        this.children = new HashMap<>();
    }

    public String getNode() {
        return "chatmanager." + this.node;
    }

    public String getDescription() {
        return this.description;
    }

    public PermissionDefault isDefault() {
        return this.isDefault;
    }

    public HashMap<String, Boolean> getChildren() {
        return this.children;
    }

    public boolean hasPermission(Player player) {
        return player.hasPermission(getNode());
    }
}