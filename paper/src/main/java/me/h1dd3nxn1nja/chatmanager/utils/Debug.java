package me.h1dd3nxn1nja.chatmanager.utils;

import com.ryderbelserion.chatmanager.enums.Files;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class Debug {

	@NotNull
	private static final ChatManager plugin = ChatManager.get();

	public static void debugAutoBroadcast() {
		FileConfiguration autobroadcast = Files.AUTO_BROADCAST.getConfiguration();

		plugin.getLogger().info("Auto-Broadcast Debug in progress...");

		// Global Messages

		if (!autobroadcast.contains("Auto_Broadcast.Global_Messages.Enable"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Global_Messages.Enable in the autobroadcast.yml file.");

		if (!autobroadcast.contains("Auto_Broadcast.Global_Messages.Header_And_Footer"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Global_Messages.Header_And_Footer in the autobroadcast.yml file.");

		if (!autobroadcast.contains("Auto_Broadcast.Global_Messages.Interval"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Global_Messages.Interval in the autobroadcast.yml file.");

		if (!autobroadcast.contains("Auto_Broadcast.Global_Messages.Prefix"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Global_Messages.Prefix in the autobroadcast.yml file.");

		if (!autobroadcast.contains("Auto_Broadcast.Global_Messages.Header"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Global_Messages.Header in the autobroadcast.yml file.");

		if (!autobroadcast.contains("Auto_Broadcast.Global_Messages.Footer"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Global_Messages.Footer in the autobroadcast.yml file.");

		if (!autobroadcast.contains("Auto_Broadcast.Global_Messages.sound.toggle")) {
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Global_Messages.sound.toggle in the config.yml file.");
			plugin.getLogger().warning("Defaulting to false");
		}

		if (!autobroadcast.contains("Auto_Broadcast.Global_Messages.sound.value")) {
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Global_Messages.sound.value in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined sound.");
		}

		if (!autobroadcast.contains("Auto_Broadcast.Global_Messages.sound.pitch")) {
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Global_Messages.sound.pitch in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined pitch.");
		}

		if (!autobroadcast.contains("Auto_Broadcast.Global_Messages.sound.volume")) {
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Global_Messages.sound.volume in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined volume.");
		}

		if (!autobroadcast.contains("Auto_Broadcast.Global_Messages.Messages"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Global_Messages.Messages in the autobroadcast.yml file.");

		//Per world messages

		if (!autobroadcast.contains("Auto_Broadcast.Per_World_Messages.Enable"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Per_World_Messages.Enable in the autobroadcast.yml file.");

		if (!autobroadcast.contains("Auto_Broadcast.Per_World_Messages.Header_And_Footer"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Per_World_Messages.Header_And_Footer in the autobroadcast.yml file.");

		if (!autobroadcast.contains("Auto_Broadcast.Per_World_Messages.Interval"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Per_World_Messages.Interval in the autobroadcast.yml file.");

		if (!autobroadcast.contains("Auto_Broadcast.Per_World_Messages.Prefix"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Per_World_Messages.Prefix in the autobroadcast.yml file.");

		if (!autobroadcast.contains("Auto_Broadcast.Per_World_Messages.Header"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Per_World_Messages.Header in the autobroadcast.yml file.");

		if (!autobroadcast.contains("Auto_Broadcast.Per_World_Messages.Footer"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Per_World_Messages.Footer in the autobroadcast.yml file.");

		if (!autobroadcast.contains("Auto_Broadcast.Per_World_Messages.sound.toggle")) {
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Per_World_Messages.sound.toggle in the config.yml file.");
			plugin.getLogger().warning("Defaulting to false");
		}

		if (!autobroadcast.contains("Auto_Broadcast.Per_World_Messages.sound.value")) {
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Per_World_Messages.sound.value in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined sound.");
		}

		if (!autobroadcast.contains("Auto_Broadcast.Per_World_Messages.sound.pitch")) {
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Per_World_Messages.sound.pitch in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined pitch.");
		}

		if (!autobroadcast.contains("Auto_Broadcast.Per_World_Messages.sound.volume")) {
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Per_World_Messages.sound.volume in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined volume.");
		}

		for (String key : autobroadcast.getConfigurationSection("Auto_Broadcast.Per_World_Messages.Messages").getKeys(false))
			if (!autobroadcast.contains("Auto_Broadcast.Per_World_Messages.Messages." + key))
				plugin.getLogger().warning("The section Auto_Broadcast.Per_World_Messages.Messages." + key + " is either missing or isn't formatted correctly.");


		//Actionbar messages

		if (!autobroadcast.contains("Auto_Broadcast.Actionbar_Messages.Enable"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Actionbar_Messages.Enable in the autobroadcast.yml file.");

		if (!autobroadcast.contains("Auto_Broadcast.Actionbar_Messages.Interval"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Actionbar_Messages.Interval in the autobroadcast.yml file.");

		if (!autobroadcast.contains("Auto_Broadcast.Actionbar_Messages.Prefix"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Actionbar_Messages.Prefix in the autobroadcast.yml file.");

		if (!autobroadcast.contains("Auto_Broadcast.Actionbar_Messages.sound.toggle")) {
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Actionbar_Messages.sound.toggle in the config.yml file.");
			plugin.getLogger().warning("Defaulting to false");
		}

		if (!autobroadcast.contains("Auto_Broadcast.Actionbar_Messages.sound.value")) {
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Actionbar_Messages.sound.value in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined sound.");
		}

		if (!autobroadcast.contains("Auto_Broadcast.Actionbar_Messages.sound.pitch")) {
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Actionbar_Messages.sound.pitch in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined pitch.");
		}

		if (!autobroadcast.contains("Auto_Broadcast.Actionbar_Messages.sound.volume")) {
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Actionbar_Messages.sound.volume in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined volume.");
		}

		if (!autobroadcast.contains("Auto_Broadcast.Actionbar_Messages.Messages"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Actionbar_Messages.Messages in the autobroadcast.yml file.");

		//Title messages

		if (!autobroadcast.contains("Auto_Broadcast.Title_Messages.Enable"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Title_Messages.Enable in the autobroadcast.yml file.");


		if (!autobroadcast.contains("Auto_Broadcast.Title_Messages.Interval"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Title_Messages.Interval in the autobroadcast.yml file.");

		if (!autobroadcast.contains("Auto_Broadcast.Title_Messages.sound.toggle")) {
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Title_Messages.sound.toggle in the config.yml file.");
			plugin.getLogger().warning("Defaulting to false");
		}

		if (!autobroadcast.contains("Auto_Broadcast.Title_Messages.sound.value")) {
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Title_Messages.sound.value in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined sound.");
		}

		if (!autobroadcast.contains("Auto_Broadcast.Title_Messages.sound.pitch")) {
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Title_Messages.sound.pitch in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined pitch.");
		}

		if (!autobroadcast.contains("Auto_Broadcast.Title_Messages.sound.volume")) {
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Title_Messages.sound.volume in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined volume.");
		}

		if (!autobroadcast.contains("Auto_Broadcast.Title_Messages.Title"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Title_Messages.Title in the autobroadcast.yml file.");


		if (!autobroadcast.contains("Auto_Broadcast.Title_Messages.Messages"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Title_Messages.Messages in the autobroadcast.yml file.");

		//Boss bar messages

		if (!autobroadcast.contains("Auto_Broadcast.Bossbar_Messages.Enable"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Bossbar_Messages.Enable in the autobroadcast.yml file.");

		if (!autobroadcast.contains("Auto_Broadcast.Bossbar_Messages.Interval"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Bossbar_Messages.Interval in the autobroadcast.yml file.");

		if (!autobroadcast.contains("Auto_Broadcast.Bossbar_Messages.sound.toggle")) {
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Bossbar_Messages.sound.toggle in the config.yml file.");
			plugin.getLogger().warning("Defaulting to false");
		}

		if (!autobroadcast.contains("Auto_Broadcast.Bossbar_Messages.sound.value")) {
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Bossbar_Messages.sound.value in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined sound.");
		}

		if (!autobroadcast.contains("Auto_Broadcast.Bossbar_Messages.sound.pitch")) {
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Bossbar_Messages.sound.pitch in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined pitch.");
		}

		if (!autobroadcast.contains("Auto_Broadcast.Bossbar_Messages.sound.volume")) {
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Bossbar_Messages.sound.volume in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined volume.");
		}

		if (!autobroadcast.contains("Auto_Broadcast.Bossbar_Messages.Bar_Time"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Bossbar_Messages.Bar_Time in the autobroadcast.yml file.");

		if (!autobroadcast.contains("Auto_Broadcast.Bossbar_Messages.Messages"))
			plugin.getLogger().warning("Missing the section Auto_Broadcast.Bossbar_Messages.Messages in the autobroadcast.yml file.");

		plugin.getLogger().warning("Debug Complete!");
		plugin.getLogger().warning("If any of the sections are missing in the autobroadcast.yml file please contact the developer.");
	}

	public static void debugConfig() {
		FileConfiguration config = Files.CONFIG.getConfiguration();

		plugin.getLogger().warning("Config Debug in progress...");

		//Anti Advertising Chat

		if (!config.contains("Anti_Advertising.Chat.Enable"))
			plugin.getLogger().warning("Missing the section Anti_Advertising.Chat.Enable in the config.yml file.");

		if (!config.contains("Anti_Advertising.Chat.Increase_Sensitivity"))
			plugin.getLogger().warning("Missing the section Anti_Advertising.Chat.Increase_Sensitivity in the config.yml file.");

		if (!config.contains("Anti_Advertising.Chat.Notify_Staff"))
			plugin.getLogger().warning("Missing the section Anti_Advertising.Chat.Notify_Staff in the config.yml file.");

		if (!config.contains("Anti_Advertising.Chat.Execute_Command"))
			plugin.getLogger().warning("Missing the section Anti_Advertising.Chat.Execute_Command in the config.yml file.");

		if (!config.contains("Anti_Advertising.Chat.Executed_Command"))
			plugin.getLogger().warning("Missing the section Anti_Advertising.Chat.Executed_Command in the config.yml file.");

		if (!config.contains("Anti_Advertising.Chat.Log_Advertisers"))
			plugin.getLogger().warning("Missing the section Anti_Advertising.Chat.Log_Advertisers in the config.yml file.");

		//Anti Advertising Commands

		if (!config.contains("Anti_Advertising.Commands.Enable"))
			plugin.getLogger().warning("Missing the section Anti_Advertising.Commands.Enable in the config.yml file.");

		if (!config.contains("Anti_Advertising.Commands.Increase_Sensitivity"))
			plugin.getLogger().warning("Missing the section Anti_Advertising.Commands.Increase_Sensitivity in the config.yml file.");

		if (!config.contains("Anti_Advertising.Commands.Notify_Staff"))
			plugin.getLogger().warning("Missing the section Anti_Advertising.Commands.Notify_Staff in the config.yml file.");

		if (!config.contains("Anti_Advertising.Commands.Execute_Command"))
			plugin.getLogger().warning("Missing the section Anti_Advertising.Commands.Execute_Command in the config.yml file.");

		if (!config.contains("Anti_Advertising.Commands.Executed_Command"))
			plugin.getLogger().warning("Missing the section Anti_Advertising.Commands.Executed_Command in the config.yml file.");

		if (!config.contains("Anti_Advertising.Commands.Log_Advertisers"))
			plugin.getLogger().warning("Missing the section Anti_Advertising.Commands.Log_Advertisers in the config.yml file.");

		//Anti Advertising Signs

		if (!config.contains("Anti_Advertising.Signs.Enable"))
			plugin.getLogger().warning("Missing the section Anti_Advertising.Signs.Enable in the config.yml file.");

		if (!config.contains("Anti_Advertising.Signs.Increase_Sensitivity"))
			plugin.getLogger().warning("Missing the section Anti_Advertising.Signs.Increase_Sensitivity in the config.yml file.");

		if (!config.contains("Anti_Advertising.Signs.Notify_Staff"))
			plugin.getLogger().warning("Missing the section Anti_Advertising.Signs.Notify_Staff in the config.yml file.");

		if (!config.contains("Anti_Advertising.Signs.Execute_Command"))
			plugin.getLogger().warning("Missing the section Anti_Advertising.Signs.Execute_Command in the config.yml file.");

		if (!config.contains("Anti_Advertising.Signs.Executed_Command"))
			plugin.getLogger().warning("Missing the section Anti_Advertising.Signs.Executed_Command in the config.yml file.");

		if (!config.contains("Anti_Advertising.Signs.Log_Advertisers"))
			plugin.getLogger().warning("Missing the section Anti_Advertising.Signs.Log_Advertisers in the config.yml file.");

		//Anti Advertising

		if (!config.contains("Anti_Advertising.Whitelist"))
			plugin.getLogger().warning("Missing the section Anti_Advertising.Whitelist in the config.yml file.");

		//Anti Bot

		if (!config.contains("Anti_Bot.Block_Chat_Until_Moved"))
			plugin.getLogger().warning("Missing the section Anti_Bot.Block_Chat_Until_Moved in the config.yml file.");

		if (!config.contains("Anti_Bot.Block_Commands_Until_Moved"))
			plugin.getLogger().warning("Missing the section Anti_Bot.Block_Commands_Until_Moved in the config.yml file.");

		//Anti Caps

		if (!config.contains("Anti_Caps.Enable"))
			plugin.getLogger().warning("Missing the section Anti_Caps.Enable in the config.yml file.");

		if (!config.contains("Anti_Caps.Enable_In_Commands"))
			plugin.getLogger().warning("Missing the section Anti_Caps.Enable_In_Commands in the config.yml file.");


		if (!config.contains("Anti_Caps.Min_Message_Length"))
			plugin.getLogger().warning("Missing the section Anti_Caps.Min_Message_Length in the config.yml file.");

		if (!config.contains("Anti_Caps.Required_Percentage"))
			plugin.getLogger().warning("Missing the section Anti_Caps.Required_Percentage in the config.yml file.");

		//Anti Spam Chat

		if (!config.contains("Anti_Spam.Chat.Chat_Delay"))
			plugin.getLogger().warning("Missing the section Anti_Spam.Chat.Chat_Delay in the config.yml file.");

		if (!config.contains("Anti_Spam.Chat.Block_Repetitive_Messages"))
			plugin.getLogger().warning("Missing the section Anti_Spam.Chat.Block_Repetitive_Messages in the config.yml file.");

		//Anti Spam Commands

		if (!config.contains("Anti_Spam.Command.Command_Delay"))
			plugin.getLogger().warning("Missing the section Anti_Spam.Command.Command_Delay in the config.yml file.");

		if (!config.contains("Anti_Spam.Command.Block_Repetitive_Commands"))
			plugin.getLogger().warning("Missing the section Anti_Spam.Command.Block_Repetitive_Commands in the config.yml file.");

		if (!config.contains("Anti_Spam.Command.Whitelist"))
			plugin.getLogger().warning("Missing the section Anti_Spam.Command.Whitelist in the config.yml file.");

		//Anti Swear Chat

		if (!config.contains("Anti_Swear.Chat.Enable"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Chat.Enable in the config.yml file.");

		if (!config.contains("Anti_Swear.Chat.Increase_Sensitivity"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Chat.Increase_Sensitivity in the config.yml file.");

		if (!config.contains("Anti_Swear.Chat.Block_Message"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Chat.Block_Message in the config.yml file.");

		if (!config.contains("Anti_Swear.Chat.Notify_Staff"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Chat.Notify_Staff in the config.yml file.");


		if (!config.contains("Anti_Swear.Chat.Execute_Command"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Chat.Execute_Command in the config.yml file.");

		if (!config.contains("Anti_Swear.Chat.Executed_Command"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Chat.Executed_Command in the config.yml file.");

		if (!config.contains("Anti_Swear.Chat.Log_Swearing"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Chat.Log_Swearing in the config.yml file.");

		//Anti Swear Commands

		if (!config.contains("Anti_Swear.Commands.Enable"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Commands.Enable in the config.yml file.");

		if (!config.contains("Anti_Swear.Commands.Increase_Sensitivity"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Commands.Increase_Sensitivity in the config.yml file.");

		if (!config.contains("Anti_Swear.Commands.Block_Command"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Commands.Block_Command in the config.yml file.");

		if (!config.contains("Anti_Swear.Commands.Whitelisted_Commands"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Commands.Whitelisted_Commands in the config.yml file.");

		if (!config.contains("Anti_Swear.Commands.Notify_Staff"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Commands.Notify_Staff in the config.yml file.");

		if (!config.contains("Anti_Swear.Commands.Execute_Command"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Commands.Execute_Command in the config.yml file.");

		if (!config.contains("Anti_Swear.Commands.Executed_Command"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Commands.Executed_Command in the config.yml file.");

		if (!config.contains("Anti_Swear.Commands.Log_Swearing"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Commands.Log_Swearing in the config.yml file.");

		//Anti Swear Signs

		if (!config.contains("Anti_Swear.Signs.Enable"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Signs.Enable in the config.yml file.");

		if (!config.contains("Anti_Swear.Signs.Increase_Sensitivity"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Signs.Increase_Sensitivity in the config.yml file.");

		if (!config.contains("Anti_Swear.Signs.Block_Sign"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Signs.Block_Sign in the config.yml file.");

		if (!config.contains("Anti_Swear.Signs.Notify_Staff"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Signs.Notify_Staff in the config.yml file.");

		if (!config.contains("Anti_Swear.Signs.Execute_Command"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Signs.Execute_Command in the config.yml file.");

		if (!config.contains("Anti_Swear.Signs.Executed_Command"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Signs.Executed_Command in the config.yml file.");

		if (!config.contains("Anti_Swear.Signs.Log_Swearing"))
			plugin.getLogger().warning("Missing the section Anti_Swear.Signs.Log_Swearing in the config.yml file.");

		//Anti Unicode

		if (!config.contains("Anti_Unicode.Enable"))
			plugin.getLogger().warning("Missing the section Anti_Unicode.Enable in the config.yml file.");

		if (!config.contains("Anti_Unicode.Notify_Staff"))
			plugin.getLogger().warning("Missing the section Anti_Unicode.Notify_Staff in the config.yml file.");

		if (!config.contains("Anti_Unicode.Execute_Command"))
			plugin.getLogger().warning("Missing the section Anti_Unicode.Execute_Command in the config.yml file.");

		if (!config.contains("Anti_Unicode.Executed_Command"))
			plugin.getLogger().warning("Missing the section Anti_Unicode.Executed_Command in the config.yml file.");

		if (!config.contains("Anti_Unicode.Whitelist"))
			plugin.getLogger().warning("Missing the section Anti_Unicode.Whitelist in the config.yml file.");

		//Banned Commands

		if (!config.contains("Banned_Commands.Enable"))
			plugin.getLogger().warning("Missing the section Banned_Commands.Enable in the config.yml file.");

		if (!config.contains("Banned_Commands.Increase_Sensitivity"))
			plugin.getLogger().warning("Missing the section Banned_Commands.Increase_Sensitivity in the config.yml file.");

		if (!config.contains("Banned_Commands.Notify_Staff"))
			plugin.getLogger().warning("Missing the section Banned_Commands.Notify_Staff in the config.yml file.");

		if (!config.contains("Banned_Commands.Execute_Command"))
			plugin.getLogger().warning("Missing the section Banned_Commands.Execute_Command in the config.yml file.");

		if (!config.contains("Banned_Commands.Executed_Command"))
			plugin.getLogger().warning("Missing the section Banned_Commands.Executed_Command in the config.yml file.");

		//Broadcast Commands

		if (!config.contains("Broadcast_Commands.Command.Broadcast.Prefix"))
			plugin.getLogger().warning("Missing the section Broadcast_Commands.Command.Broadcast.Prefix in the config.yml file.");

		if (!config.contains("Broadcast_Commands.Command.Broadcast.Default_Color"))
			plugin.getLogger().warning("Missing the section Broadcast_Commands.Command.Broadcast.Default_Color in the config.yml file.");

		if (!config.contains("Broadcast_Commands.Command.Broadcast.sound.toggle")) {
			plugin.getLogger().warning("Missing the section Broadcast_Commands.Command.Broadcast.sound.toggle in the config.yml file.");
			plugin.getLogger().warning("Defaulting to false");
		}

		if (!config.contains("Broadcast_Commands.Command.Broadcast.sound.value")) {
			plugin.getLogger().warning("Missing the section Broadcast_Commands.Command.Broadcast.sound.value in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined sound.");
		}

		if (!config.contains("Broadcast_Commands.Command.Broadcast.sound.pitch")) {
			plugin.getLogger().warning("Missing the section Broadcast_Commands.Command.Broadcast.sound.pitch in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined pitch.");
		}

		if (!config.contains("Broadcast_Commands.Command.Broadcast.sound.volume")) {
			plugin.getLogger().warning("Missing the section Broadcast_Commands.Command.Broadcast.sound.volume in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined volume.");
		}

		if (!config.contains("Broadcast_Commands.Command.Announcement.sound.toggle")) {
			plugin.getLogger().warning("Missing the section Broadcast_Commands.Command.Announcement.sound.toggle in the config.yml file.");
			plugin.getLogger().warning("Defaulting to false");
		}

		if (!config.contains("Broadcast_Commands.Command.Announcement.sound.value")) {
			plugin.getLogger().warning("Missing the section Broadcast_Commands.Command.Announcement.sound.value in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined sound.");
		}

		if (!config.contains("Broadcast_Commands.Command.Announcement.sound.pitch")) {
			plugin.getLogger().warning("Missing the section Broadcast_Commands.Command.Announcement.sound.pitch in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined pitch.");
		}

		if (!config.contains("Broadcast_Commands.Command.Announcement.sound.volume")) {
			plugin.getLogger().warning("Missing the section Broadcast_Commands.Command.Announcement.sound.volume in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined volume.");
		}

		if (!config.contains("Broadcast_Commands.Command.Announcement.Message"))
			plugin.getLogger().warning("Missing the section Broadcast_Commands.Command.Announcement.Message in the config.yml file.");

		if (!config.contains("Broadcast_Commands.Command.Warning.sound.toggle")) {
			plugin.getLogger().warning("Missing the section Broadcast_Commands.Command.Warning.sound.toggle in the config.yml file.");
			plugin.getLogger().warning("Defaulting to false");
		}

		if (!config.contains("Broadcast_Commands.Command.Warning.sound.value")) {
			plugin.getLogger().warning("Missing the section Broadcast_Commands.Command.Warning.sound.value in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined sound.");
		}

		if (!config.contains("Broadcast_Commands.Command.Warning.sound.pitch")) {
			plugin.getLogger().warning("Missing the section Broadcast_Commands.Command.Warning.sound.pitch in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined pitch.");
		}

		if (!config.contains("Broadcast_Commands.Command.Warning.sound.volume")) {
			plugin.getLogger().warning("Missing the section Broadcast_Commands.Command.Warning.sound.volume in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined volume.");
		}

		if (!config.contains("Broadcast_Commands.Command.Warning.Message"))
			plugin.getLogger().warning("Missing the section Broadcast_Commands.Command.Warning.Message in the config.yml file.");

		//Chat Format

		if (!config.contains("Chat_Format.Enable"))
			plugin.getLogger().warning("Missing the section Chat_Format.Enable in the config.yml file.");

		if (!config.contains("Chat_Format.Default_Format"))
			plugin.getLogger().warning("Missing the section Chat_Format.Default_Format in the config.yml file.");

		//Chat Radius

		if (!config.contains("Chat_Radius.Enable"))
			plugin.getLogger().warning("Missing the section Chat_Radius.Enable in the config.yml file.");

		if (!config.contains("Chat_Radius.Default_Channel"))
			plugin.getLogger().warning("Missing the section Chat_Radius.Default_Channel in the config.yml file.");

		if (!config.contains("Chat_Radius.Local_Chat"))
			plugin.getLogger().warning("Missing the section Chat_Radius.Local_Chat in the config.yml file.");


		if (!config.contains("Chat_Radius.Local_Chat.Prefix"))
			plugin.getLogger().warning("Missing the section Chat_Radius.Local_Chat.Prefix in the config.yml file.");

		if (!config.contains("Chat_Radius.Local_Chat.Override_Symbol"))
			plugin.getLogger().warning("Missing the section Chat_Radius.Local_Chat.Override_Symbol in the config.yml file.");

		if (!config.contains("Chat_Radius.Global_Chat"))
			plugin.getLogger().warning("Missing the section Chat_Radius.Global_Chat in the config.yml file.");

		if (!config.contains("Chat_Radius.Global_Chat.Prefix"))
			plugin.getLogger().warning("Missing the section Chat_Radius.Global_Chat.Prefix in the config.yml file.");

		if (!config.contains("Chat_Radius.Global_Chat.Override_Symbol"))
			plugin.getLogger().warning("Missing the section Chat_Radius.Global_Chat.Override_Symbol in the config.yml file.");

		if (!config.contains("Chat_Radius.World_Chat"))
			plugin.getLogger().warning("Missing the section Chat_Radius.World_Chat in the config.yml file.");

		if (!config.contains("Chat_Radius.World_Chat.Prefix"))
			plugin.getLogger().warning("Missing the section Chat_Radius.World_Chat.Prefix in the config.yml file.");

		if (!config.contains("Chat_Radius.World_Chat.Override_Symbol"))
			plugin.getLogger().warning("Missing the section Chat_Radius.World_Chat.Override_Symbol in the config.yml file.");

		if (!config.contains("Chat_Radius.Block_Distance"))
			plugin.getLogger().warning("Missing the section Chat_Radius.Block_Distance in the config.yml file.");

		if (!config.contains("Chat_Radius.Enable_Spy_On_Join"))
			plugin.getLogger().warning("Missing the section Chat_Radius.Enable_Spy_On_Join in the config.yml file.");

		//Clear Chat

		if (!config.contains("Clear_Chat.Clear_On_Join"))
			plugin.getLogger().warning("Missing the section Clear_Chat.Clear_On_Join in the config.yml file.");

		if (!config.contains("Clear_Chat.Broadcasted_Lines"))
			plugin.getLogger().warning("Missing the section Clear_Chat.Broadcasted_Lines in the config.yml file.");

		//Command Spy

		if (!config.contains("Command_Spy.Enable_On_Join"))
			plugin.getLogger().warning("Missing the section Command_Spy.Enable_On_Join in the config.yml file.");

		if (!config.contains("Command_Spy.Blacklist_Commands"))
			plugin.getLogger().warning("Missing the section Command_Spy.Blacklist_Commands in the config.yml file.");

		//Formatted Messages

		if (!config.contains("Formatted_Messages.Enable"))
			plugin.getLogger().warning("Missing the section Formatted_Messages.Enable in the config.yml file.");

		//Lists

		if (!config.contains("Lists.Staff_List"))
			plugin.getLogger().warning("Missing the section Lists.Staff_List in the config.yml file.");

		if (!config.contains("Lists.Player_List"))
			plugin.getLogger().warning("Missing the section Lists.Player_List in the config.yml file.");

		//Logs

		if (!config.contains("Logs.Log_Chat"))
			plugin.getLogger().warning("Missing the section Logs.Log_Chat in the config.yml file.");

		if (!config.contains("Logs.Log_Commands"))
			plugin.getLogger().warning("Missing the section Logs.Log_Commands in the config.yml file.");

		if (!config.contains("Logs.Log_Signs"))
			plugin.getLogger().warning("Missing the section Logs.Log_Signs in the config.yml file.");

		if (!config.contains("Logs.Blacklist_Commands"))
			plugin.getLogger().warning("Missing the section Logs.Blacklist_Commands in the config.yml file.");

		//First Join Messages

		if (!config.contains("Messages.First_Join.Welcome_Message.Enable"))
			plugin.getLogger().warning("Missing the section Messages.First_Join.Welcome_Message.Enable in the config.yml file.");

		if (!config.contains("Messages.Join_Quit_Messages.Welcome_Message.sound.toggle")) {
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Quit_Message.sound.toggle in the config.yml file.");
			plugin.getLogger().warning("Defaulting to false");
		}

		if (!config.contains("Messages.Join_Quit_Messages.Welcome_Message.sound.value")) {
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Quit_Message.sound.value in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined sound.");
		}

		if (!config.contains("Messages.Join_Quit_Messages.Welcome_Message.sound.pitch")) {
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Quit_Message.sound.pitch in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined pitch.");
		}

		if (!config.contains("Messages.Join_Quit_Messages.Welcome_Message.sound.volume")) {
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Quit_Message.sound.volume in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined volume.");
		}

		if (!config.contains("Messages.First_Join.Welcome_Message.First_Join_Message"))
			plugin.getLogger().warning("Missing the section Messages.First_Join.Welcome_Message.First_Join_Message in the config.yml file.");

		if (!config.contains("Messages.First_Join.Actionbar_Message.Enable"))
			plugin.getLogger().warning("Missing the section Messages.First_Join.Actionbar_Message.Enable in the config.yml file.");

		if (!config.contains("Messages.First_Join.Welcome_Message.First_Join_Message"))
			plugin.getLogger().warning("Missing the section Messages.First_Join.Welcome_Message.First_Join_Message in the config.yml file.");

		if (!config.contains("Messages.First_Join.Title_Message.Enable"))
			plugin.getLogger().warning("Missing the section Messages.First_Join.Title_Message.Enable in the config.yml file.");

		if (!config.contains("Messages.First_Join.Title_Message.Fade_In"))
			plugin.getLogger().warning("Missing the section Messages.First_Join.Title_Message.Fade_In in the config.yml file.");

		if (!config.contains("Messages.First_Join.Title_Message.Stay"))
			plugin.getLogger().warning("Missing the section Messages.First_Join.Title_Message.Stay in the config.yml file.");

		if (!config.contains("Messages.First_Join.Title_Message.Fade_Out"))
			plugin.getLogger().warning("Missing the section Messages.First_Join.Title_Message.Fade_Out in the config.yml file.");

		if (!config.contains("Messages.First_Join.Title_Message.First_Join_Message.Header"))
			plugin.getLogger().warning("Missing the section Messages.First_Join.Title_Message.First_Join_Message.Header in the config.yml file.");

		if (!config.contains("Messages.First_Join.Title_Message.First_Join_Message.Footer"))
			plugin.getLogger().warning("Missing the section Messages.First_Join.Title_Message.First_Join_Message.Footer in the config.yml file.");

		//Join & Quit Messages

		if (!config.contains("Messages.Join_Quit_Messages.Join_Message.Enable"))
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Join_Message.Enable in the config.yml file.");

		if (!config.contains("Messages.Join_Quit_Messages.Join_Message.sound.toggle")) {
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Join_Message.sound.toggle in the config.yml file.");
			plugin.getLogger().warning("Defaulting to false");
		}

		if (!config.contains("Messages.Join_Quit_Messages.Join_Message.sound.value")) {
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Join_Message.sound.value in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined sound.");
		}

		if (!config.contains("Messages.Join_Quit_Messages.Join_Message.sound.pitch")) {
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Join_Message.sound.pitch in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined pitch.");
		}

		if (!config.contains("Messages.Join_Quit_Messages.Join_Message.sound.volume")) {
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Join_Message.sound.volume in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined volume.");
		}

		if (!config.contains("Messages.Join_Quit_Messages.Join_Message.Message"))
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Join_Message.Message in the config.yml file.");

		if (!config.contains("Messages.Join_Quit_Messages.Quit_Message.Enable"))
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Quit_Message.Enable in the config.yml file.");

		if (!config.contains("Messages.Join_Quit_Messages.Quit_Message.sound.toggle")) {
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Quit_Message.sound.toggle in the config.yml file.");
			plugin.getLogger().warning("Defaulting to false");
		}

		if (!config.contains("Messages.Join_Quit_Messages.Quit_Message.sound.value")) {
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Quit_Message.sound.value in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined sound.");
		}

		if (!config.contains("Messages.Join_Quit_Messages.Quit_Message.sound.pitch")) {
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Quit_Message.sound.pitch in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined pitch.");
		}

		if (!config.contains("Messages.Join_Quit_Messages.Quit_Message.sound.volume")) {
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Quit_Message.sound.volume in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined volume.");
		}

		if (!config.contains("Messages.Join_Quit_Messages.Quit_Message.Message"))
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Quit_Message.Message in the config.yml file.");

		if (!config.contains("Messages.Join_Quit_Messages.Actionbar_Message.Enable"))
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Actionbar_Message.Enable in the config.yml file.");

		if (!config.contains("Messages.Join_Quit_Messages.Actionbar_Message.Message"))
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Actionbar_Message.Message in the config.yml file.");

		if (!config.contains("Messages.Join_Quit_Messages.Title_Message.Enable"))
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Title_Message.Enable in the config.yml file.");

		if (!config.contains("Messages.Join_Quit_Messages.Title_Message.Fade_In"))
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Title_Message.Fade_In in the config.yml file.");

		if (!config.contains("Messages.Join_Quit_Messages.Title_Message.Stay"))
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Title_Message.Stay in the config.yml file.");

		if (!config.contains("Messages.Join_Quit_Messages.Title_Message.Fade_Out"))
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Title_Message.Fade_Out in the config.yml file.");

		if (!config.contains("Messages.Join_Quit_Messages.Title_Message.Message"))
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Title_Message.Message in the config.yml file.");

		if (!config.contains("Messages.Join_Quit_Messages.Title_Message.Message.Header"))
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Title_Message.Message.Header in the config.yml file.");

		if (!config.contains("Messages.Join_Quit_Messages.Title_Message.Message.Footer"))
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Title_Message.Message.Footer in the config.yml file.");

		if (!config.contains("Messages.Join_Quit_Messages.Group_Messages.Enable"))
			plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Group_Messages.Enable in the config.yml file.");

		for (String key : config.getConfigurationSection("Messages.Join_Quit_Messages.Group_Messages").getKeys(false))
			if (!config.contains("Messages.Join_Quit_Messages.Group_Messages." + key))
				plugin.getLogger().warning("Missing the section Messages.Join_Quit_Messages.Group_Messages." + key + "in the config.yml file.");

		//Mentions

		if (!config.contains("Mentions.Enable"))
			plugin.getLogger().warning("Missing the section Mentions.Enable in the config.yml file.");

		if (!config.contains("Mentions.sound.toggle")) {
			plugin.getLogger().warning("Missing the section Mentions.sound.toggle in the config.yml file.");
			plugin.getLogger().warning("Defaulting to false");
		}

		if (!config.contains("Mentions.sound.value")) {
			plugin.getLogger().warning("Missing the section Mentions.sound.value in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined sound.");
		}

		if (!config.contains("Mentions.sound.pitch")) {
			plugin.getLogger().warning("Missing the section Mentions.sound.pitch in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined pitch.");
		}

		if (!config.contains("Mentions.sound.volume")) {
			plugin.getLogger().warning("Missing the section Mentions.sound.volume in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined volume.");
		}

		if (!config.contains("Mentions.Tag_Symbol"))
			plugin.getLogger().warning("Missing the section Mentions.Tag_Symbol in the config.yml file.");

		if (!config.contains("Mentions.Mention_Color"))
			plugin.getLogger().warning("Missing the section Mentions.Mention_Color in the config.yml file.");

		if (!config.contains("Mentions.Title.Enable"))
			plugin.getLogger().warning("Missing the section Mentions.Title.Enable in the config.yml file.");


		if (!config.contains("Mentions.Title.Header"))
			plugin.getLogger().warning("Missing the section Mentions.Title.Header in the config.yml file.");

		if (!config.contains("Mentions.Title.Footer"))
			plugin.getLogger().warning("Missing the section Mentions.Title.Footer in the config.yml file.");

		//MOTD

		if (!config.contains("MOTD.Enable"))
			plugin.getLogger().warning("Missing the section MOTD.Enable in the config.yml file.");

		if (!config.contains("MOTD.Delay"))
			plugin.getLogger().warning("Missing the section MOTD.Delay in the config.yml file.");


		if (!config.contains("MOTD.Message"))
			plugin.getLogger().warning("Missing the section MOTD.Message in the config.yml file.");

		//Mute Chat

		if (!config.contains("Mute_Chat.Disable_Commands"))
			plugin.getLogger().warning("Missing the section Mute_Chat.Disable_Commands in the config.yml file.");

		if (!config.contains("Mute_Chat.Disabled_Commands"))
			plugin.getLogger().warning("Missing the section Mute_Chat.Disabled_Commands in the config.yml file.");

		//Per world chat

		if (!config.contains("Per_World_Chat.Enable"))
			plugin.getLogger().warning("Missing the section Per_World_Chat.Enable in the config.yml file.");

		if (!config.contains("Per_World_Chat.Group_Worlds"))
			plugin.getLogger().warning("Missing the section Per_World_Chat.Group_Worlds in the config.yml file.");

		if (!config.contains("Per_World_Chat.Group_Worlds.Enable"))
			plugin.getLogger().warning("Missing the section Per_World_Chat.Group_Worlds.Enable in the config.yml file.");

		if (!config.contains("Per_World_Chat.Group_Worlds.Worlds"))
			plugin.getLogger().warning("Missing the section Per_World_Chat.Group_Worlds.Worlds in the config.yml file.");


		for (String key : config.getConfigurationSection("Per_World_Chat.Group_Worlds.Worlds").getKeys(false))
			if (!config.contains("Per_World_Chat.Group_Worlds.Worlds." + key))
				plugin.getLogger().warning("Missing the section Per_World_Chat.Group_Worlds.Worlds." + key + "in the config.yml file.");

		//Private Message

		if (!config.contains("Private_Messages.Sender.Format"))
			plugin.getLogger().warning("Missing the section Private_Messages.Sender.Format in the config.yml file.");

		if (!config.contains("Private_Messages.Receiver.Format"))
			plugin.getLogger().warning("Missing the section Private_Messages.Receiver.Format in the config.yml file.");

		if (!config.contains("Private_Messages.sound.toggle")) {
			plugin.getLogger().warning("Missing the section Private_Messages.sound.toggle in the config.yml file.");
			plugin.getLogger().warning("Defaulting to false");
		}

		if (!config.contains("Private_Messages.sound.value")) {
			plugin.getLogger().warning("Missing the section Private_Messages.sound.value in the config.yml file.");
			plugin.getLogger().warning("Defaulting to a pre-determined sound.");
		}

		//Server Rules

		if (!config.contains("Server_Rules.Rules"))
			plugin.getLogger().warning("Missing the section Server_Rules.Rules in the config.yml file.");

		for (String key : config.getConfigurationSection("Server_Rules.Rules").getKeys(false))
			if (!config.contains("Server_Rules.Rules." + key))
				plugin.getLogger().warning("Missing the section Server_Rules.Rules." + key + "in the config.yml file.");

		//Social Spy

		if (!config.contains("Social_Spy.Enable_On_Join"))
			plugin.getLogger().warning("Missing the section Social_Spy.Enable_On_Join in the config.yml file.");

		//Staff Chat

		if (!config.contains("Staff_Chat.Enable"))
			plugin.getLogger().warning("Missing the section Staff_Chat.Enable in the config.yml file.");

		if (!config.contains("Staff_Chat.Format"))
			plugin.getLogger().warning("Missing the section Staff_Chat.Format in the config.yml file.");

		if (!config.contains("Staff_Chat.Boss_Bar.Enable"))
			plugin.getLogger().warning("Missing the section Staff_Chat.Boss_Bar.Enable in the config.yml file.");

		if (!config.contains("Staff_Chat.Boss_Bar.Title"))
			plugin.getLogger().warning("Missing the section Staff_Chat.Boss_Bar.Title in the config.yml file.");

		plugin.getLogger().warning("Debug Complete!");
		plugin.getLogger().warning("If any of the sections are missing in the config.yml file please contact the developer.");

	}
}