package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerGrammar extends Global implements Listener {

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void grammarCheck(AsyncPlayerChatEvent event) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final Player player = event.getPlayer();

		if (event.getMessage().toCharArray().length < config.getInt("Grammar.Min_Message_Length", 5) ||
				!config.getBoolean("Grammar.Enable", false) || plugin.api().getStaffChatData().containsUser(player.getUniqueId())) return;

		if (player.hasPermission(Permissions.BYPASS_GRAMMAR.getNode())) return;

		final char[] listChar = event.getMessage().toCharArray();
		String message = event.getMessage();

		try {
			message = message.replaceFirst(message.charAt(0) + "", StringUtils.capitalize(message.charAt(0) + ""));
		} catch (Exception ignored) {}

		if ((!(message.charAt(listChar.length - 1) + "").equals("!"))
				&& (!(message.charAt(listChar.length - 1) + "").equals("."))
				&& (!(message.charAt(listChar.length - 1) + "").equals(","))
				&& (!(message.charAt(listChar.length - 1) + "").equals("?"))) {
			message = message + ".";
		}

		String[] messageSplit = message.split(" ");
		StringBuilder sb = new StringBuilder();

		if (config.getBoolean("Grammar.Autocorrect.Enable", false)) {
			for (String word : messageSplit) {
				if (word.equals("i")) {
					sb.append("I").append(" ");
				} else if ((word.equalsIgnoreCase("i'm")) || (word.equalsIgnoreCase("im"))) {
					sb.append("I'm").append(" ");
				} else if ((word.equalsIgnoreCase("i'll")) || (word.equalsIgnoreCase("ill"))) {
					sb.append("I'll").append(" ");
				} else if ((word.equals("cant"))) {
					sb.append("can't").append(" ");
				} else if ((word.equals("youre"))) {
					sb.append("you're").append(" ");
				} else if ((word.equals("dont"))) {
					sb.append("don't").append(" ");
				} else if ((word.equals("theyre"))) {
					sb.append("they're").append(" ");
				} else if ((word.equals("couldnt"))) {
					sb.append("couldn't").append(" ");
				} else if ((word.equals("whos"))) {
					sb.append("who's").append(" ");
				} else if ((word.equals("alot"))) {
					sb.append("a lot").append(" ");
				} else if ((word.equals("nor"))) {
					sb.append("nor,").append(" ");
				} else if ((word.equals("yet"))) {
					sb.append("yet,").append(" ");
				} else if ((word.equals("or"))) {
					sb.append("or,").append(" ");
				} else if ((word.equals("and"))) {
					sb.append("and,").append(" ");
				} else
					sb.append(word).append(" ");
			}

			event.setMessage(sb.toString());
		}
	}
}