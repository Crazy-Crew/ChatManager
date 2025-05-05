package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import me.h1dd3nxn1nja.chatmanager.utils.Format;
import net.md_5.bungee.api.ChatColor;
import java.util.HashMap;
import java.util.Map;

public class ListenerColor implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onColorChat(AsyncPlayerChatEvent event) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final Player player = event.getPlayer();
		final String message = event.getMessage();

		if (!config.getBoolean("Formatted_Messages.Enable", false)) return;

		event.setMessage(formatChat(player, message));
	}

	@EventHandler(ignoreCancelled = true)
	public void onSignChange(SignChangeEvent event) {
		final Player player = event.getPlayer();

		for (int index = 0; index < event.getLines().length; index++) {
			String line = event.getLine(index);

			if (player.hasPermission(Permissions.SIGN_COLOR_ALL.getNode())) line = Format.formatStringColor(line);

			if (player.hasPermission(Permissions.SIGN_FORMAT_ALL.getNode())) line = Format.formatString(line);

			event.setLine(index, line);
		}
	}
	
	private char getColorCharacter() {
		final String characterString = "&";
		
		char[] charArray = characterString.toCharArray();

		return charArray[0];
	}

	private HashMap<String, String> colors() {
        return new HashMap<>() {{
			put(Permissions.COLOR_GREEN.getNode(), "a");
			put(Permissions.COLOR_AQUA.getNode(), "b");
			put(Permissions.COLOR_RED.getNode(), "c");
			put(Permissions.COLOR_LIGHT_PURPLE.getNode(), "d");
			put(Permissions.COLOR_YELLOW.getNode(), "e");
			put(Permissions.COLOR_WHITE.getNode(), "f");
			put(Permissions.COLOR_BLACK.getNode(), "0");
			put(Permissions.COLOR_DARK_BLUE.getNode(), "1");
			put(Permissions.COLOR_DARK_GREEN.getNode(), "2");
			put(Permissions.COLOR_DARK_AQUA.getNode(), "3");
			put(Permissions.COLOR_DARK_RED.getNode(), "4");
			put(Permissions.COLOR_DARK_PURPLE.getNode(), "5");
			put(Permissions.COLOR_GOLD.getNode(), "6");
			put(Permissions.COLOR_GRAY.getNode(), "7");
			put(Permissions.COLOR_DARK_GRAY.getNode(), "8");
			put(Permissions.COLOR_BLUE.getNode(), "9");
		}};
	}

	private HashMap<String, String> map() {
		return new HashMap<>() {{
			put(Permissions.FORMAT_OBFUSCATED.getNode(), "k");
			put(Permissions.FORMAT_BOLD.getNode(), "l");
			put(Permissions.FORMAT_STRIKETHROUGH.getNode(), "m");
			put(Permissions.FORMAT_UNDERLINE.getNode(), "n");
			put(Permissions.FORMAT_ITALIC.getNode(), "o");
			put(Permissions.FORMAT_RESET.getNode(), "r");
		}};
	}
	
	private String formatChat(final Player player, String msg) {
		final char colorChar = getColorCharacter();

		if (player.hasPermission(Permissions.FORMATTING_ALL.getNode())) {
			return Methods.color(msg);
		}

		boolean ignoreColorCheck = false, ignoreFormatCheck = false;

		if (player.hasPermission(Permissions.CHAT_COLOR_ALL.getNode())) {
			msg = replaceColor(colorChar, msg);

			ignoreColorCheck = true;
		}

		if (player.hasPermission(Permissions.CHAT_FORMAT_ALL.getNode())) {
			msg = replaceFormat(colorChar, msg);

			ignoreFormatCheck = true;
		}

		if (!ignoreColorCheck) msg = replaceColors(player, msg);
		if (!ignoreFormatCheck) msg = replaceFormats(player, msg);

		return msg;
	}

	private String replaceColors(Player player, String msg) {
		final char character = getColorCharacter();

		for (Map.Entry<String, String> entry : colors().entrySet()) {
			final String node = entry.getKey();

			if (player.hasPermission(node)) {
				final String symbol = entry.getValue().toUpperCase();

				msg = replaceSpecificSymbol(character, symbol + entry.getValue(), msg);
			}
		}

		return msg;
	}

	private String replaceFormats(final Player player, String msg) {
		final char character = getColorCharacter();

		for (Map.Entry<String, String> entry : map().entrySet()) {
			final String node = entry.getKey();

			if (player.hasPermission(node)) {
				final String symbol = entry.getValue().toUpperCase();

				msg = replaceSpecificSymbol(character, symbol + entry.getValue(), msg);
			}
		}

		return msg;
	}

	private String replaceColor(final char character, final String msg) {
        final String values = "0123456789AaBbCcDdEeFf";

        return replaceSpecificSymbol(character, values, msg);
    }

    private String replaceFormat(final char character, final String msg) {
        final String values = "KkLlMmNnOoRr";

        return replaceSpecificSymbol(character, values, msg);
    }
    
    private String replaceSpecificSymbol(final char colorChar, final String values, final String msg) {
		final char[] charArray = msg.toCharArray();

		for (int i = 0; i < (charArray.length - 1); i++) {
			final boolean hasColor1 = (colorChar == charArray[i]);
			final boolean hasColor2 = (values.indexOf(charArray[i + 1]) > -1);

			if (hasColor1 && hasColor2) {
				charArray[i] = ChatColor.COLOR_CHAR;
				charArray[i + 1] = Character.toLowerCase(charArray[i + 1]);
			}
		}

		return new String(charArray);
	}
}