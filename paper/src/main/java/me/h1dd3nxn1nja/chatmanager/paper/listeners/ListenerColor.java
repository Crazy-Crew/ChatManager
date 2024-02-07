package me.h1dd3nxn1nja.chatmanager.paper.listeners;

import com.ryderbelserion.chatmanager.paper.files.enums.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import me.h1dd3nxn1nja.chatmanager.paper.utils.Format;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

public class ListenerColor implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onColorChat(AsyncPlayerChatEvent event) {
		FileConfiguration config = Files.CONFIG.getFile();

		Player player = event.getPlayer();
		String message = event.getMessage();

		if (!config.getBoolean("Formatted_Messages.Enable")) return;

		String format = formatChat(player, message);
		event.setMessage(format);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onSignChange(SignChangeEvent event) {
		Player player = event.getPlayer();
		
		for (int i = 0; i < event.getLines().length; i++) {
			String line = event.getLine(i);

			if (player.hasPermission("chatmanager.sign.color")) line = Format.formatStringColor(line);

			if (player.hasPermission("chatmanager.sign.format")) line = Format.formatString(line);

			event.setLine(i, line);
		}
	}
	
	public char getColorCharacter() {
		String characterString = "&";
		
		char[] charArray = characterString.toCharArray();

		return charArray[0];
	}
	
	public boolean hasAllPermissions(Player player) {
		if (player.hasPermission("chatmanager.formats.all")) return true;

		return (player.hasPermission("chatmanager.color.all") && player.hasPermission("chatmanager.format.all"));
	}
	
	public String formatChat(Player player, String string) {
		char colorChar = getColorCharacter();
		if (hasAllPermissions(player)) return this.plugin.getMethods().color(string);
		boolean ignoreColorCheck = false, ignoreFormatCheck = false;
		
		if (player.hasPermission("chatmanager.color.all")) {
			string = replaceColor(colorChar, string);
			ignoreColorCheck = true;
		}
		
		if (player.hasPermission("chatmanager.format.all")) {
			string = replaceFormat(colorChar, string);
			ignoreFormatCheck = true;
		}
		
		if(!ignoreColorCheck) string = replaceColors(player, string);
		if(!ignoreFormatCheck) string = replaceFormats(player, string);
		
		return string;
	}
	
	public String replaceColors(Player player, String string) {
		char colorChar = getColorCharacter();
		String[] validColorArray = {"a", "b", "c", "d", "e", "f", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		
		for (String code : validColorArray) {
			String permission = ("chatmanager.color." + code);
			if (!player.hasPermission(permission)) continue;
			
			String caps = code.toUpperCase();
			string = replaceSpecific(colorChar, caps + code, string);
		}

		return string;
	}
	
	public String replaceFormats(Player player, String string) {
		char colorChar = getColorCharacter();
		String[] validFormatArray = {"k", "l", "m", "n", "o", "r"};
		
		for (String code : validFormatArray) {
			String permission = ("chatmanager.format." + code);
			if (!player.hasPermission(permission)) continue;
			
			String caps = code.toUpperCase();
			string = replaceSpecific(colorChar, caps + code, string);
		}

		return string;
	}
	
	public String replaceColor(char colorChar, String string) {
        String specific = "0123456789AaBbCcDdEeFf";

        return replaceSpecific(colorChar, specific, string);
    }

    public String replaceFormat(char colorChar, String string) {
        String specific = "KkLlMmNnOoRr";

        return replaceSpecific(colorChar, specific, string);
    }
    
    public String replaceSpecific(char colorChar, String specific, String string) {
		char[] charArray = string.toCharArray();

		for (int i = 0; i < (charArray.length - 1); i++) {
			boolean hasColor1 = (colorChar == charArray[i]);
			boolean hasColor2 = (specific.indexOf(charArray[i + 1]) > -1);

			if (hasColor1 && hasColor2) {
				charArray[i] = ChatColor.COLOR_CHAR;
				charArray[i + 1] = Character.toLowerCase(charArray[i + 1]);
			}
		}

		return new String(charArray);
	}
}