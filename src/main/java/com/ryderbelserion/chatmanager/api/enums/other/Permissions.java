package com.ryderbelserion.chatmanager.api.enums.other;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import java.util.HashMap;

public enum Permissions {

    // chatmanager.staff.chat.receive
    STAFF_CHAT_RECEIVE("staff.chat.receive", "Ability to receive messages sent through StaffChat.", PermissionDefault.OP),

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
    BYPASS_COLON_COMMANDS("bypass.coloncommands", "Permission to use colons in commands.", PermissionDefault.OP),
    BYPASS_CHAT_DELAY("bypass.chatdelay", "Permission to bypass the chat delay.", PermissionDefault.OP),

    NOTIFY_BANNED_COMMANDS("notify.bannedcommands", "Permission to get notified when a player uses a banned command.", PermissionDefault.OP),

    BYPASS_ANTI_BOT("bypass.antibot", "Permission to talk in chat without moving on join.", PermissionDefault.OP),

    BYPASS_ANTI_UNICODE("bypass.antiunicode", "Permission to bypass the anti-unicode checker.", PermissionDefault.OP),
    NOTIFY_ANTI_UNICODE("notify.antiunicode", "Permission to get notified when a player uses special characters in chat.", PermissionDefault.OP),

    BYPASS_ANTI_SWEAR("bypass.antiswear", "Permission to bypass the anti-swear checker", PermissionDefault.OP),
    NOTIFY_ANTI_SWEAR("notify.antiswear", "Permission to get notified when a player swears in chat.", PermissionDefault.OP),

    BYPASS_ANTI_ADVERTISING("bypass.antiadvertising", "Permission to bypass the anti-advertising checker.", PermissionDefault.OP),
    NOTIFY_ANTI_ADVERTISING("notify.antiadvertising", "Permission to get notified when players advertise in chat.", PermissionDefault.OP),

    BYPASS_SPECTATOR("bypass.spectator", "Ability to send messages to staff in spectator mode", PermissionDefault.OP),

    BYPASS_TOGGLE_PM("bypass.togglepm", "Ability to send private messages to players even if they have them turned off", PermissionDefault.OP),

    CHAT_RADIUS_GLOBAL_OVERRIDE("chatradius.global.override", "Ability to override all chat radius", PermissionDefault.OP),
    CHAT_RADIUS_LOCAL_OVERRIDE("chatradius.local.override", "Ability to override local chat radius", PermissionDefault.OP),
    CHAT_RADIUS_WORLD_OVERRIDE("chatradius.world.override", "Ability to override world chat radius", PermissionDefault.OP),

    COMMAND_STAFF("staff", "Ability to do something", PermissionDefault.OP),

    SOCIAL_SPY("socialspy", "Ability to use social spy", PermissionDefault.OP),
    COMMAND_SPY("commandspy", "Ability to use command spy", PermissionDefault.OP);

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