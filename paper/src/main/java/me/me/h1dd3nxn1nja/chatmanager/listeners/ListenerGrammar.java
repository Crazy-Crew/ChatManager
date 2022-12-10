package me.me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerGrammar implements Listener {

	public ListenerGrammar(ChatManager plugin) {}

	@EventHandler(priority = EventPriority.LOW)
	public void grammarCheck(AsyncPlayerChatEvent event) {

		FileConfiguration config = ChatManager.settings.getConfig();

		Player player = event.getPlayer();

		if (event.getMessage().toCharArray().length < config.getInt("Grammar.Min_Message_Length")) {
			return;
		}

		if (config.getBoolean("Grammar.Enable")) {
			if (!Methods.cm_staffChat.contains(player.getUniqueId())) {
				if (!player.hasPermission("chatmanager.bypass.grammar")) {
					char[] listChar = event.getMessage().toCharArray();
					String message = event.getMessage();
					try {
						message = message.replaceFirst(message.charAt(0) + "",
								StringUtils.capitalize(message.charAt(0) + ""));
					} catch (Exception ex) {}
					if ((!(message.charAt(listChar.length - 1) + "").equals("!"))
							&& (!(message.charAt(listChar.length - 1) + "").equals("."))
							&& (!(message.charAt(listChar.length - 1) + "").equals(","))
							&& (!(message.charAt(listChar.length - 1) + "").equals("?"))) {
						message = message + ".";
					}

					String[] messageSplit = message.split(" ");
					StringBuilder sb = new StringBuilder();

					if (config.getBoolean("Grammar.Autocorrect.Enable")) {
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
					} else {
						return;
					}
				}
			}
		}
	}
}