package me.h1dd3nxn1nja.chatmanager.configuration;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.ChatColor;

import java.util.Optional;

/**
 * @author Niels Warrens - 21/12/2022
 */
public enum Message {

	PREFIX("Message.Prefix", "&7[&bChatManager&7]"),

	//	The spam processor(s)
	SPAM_BLOCK_REPETITIVE_MESSAGE("Anti_Spam.Chat.Repetitive_Message"),
	SPAM_BLOCK_REPETITIVE_COMMAND("Anti_Spam.Command.Repetitive_Command"),
	;

	private final String key;
	private final Object defaultValue;
	private Object value;

	Message(String key) {
		this(key, null);
	}

	Message(String key, Object defaultValue) {
		this.key = key;
		this.defaultValue = defaultValue;
		this.value = this.getValue();
	}

	public String string() {
		return this.getValue(String.class);
	}

	public int integer() {
		return this.getValue(Integer.class);
	}

	public boolean bool() {
		return this.getValue(Boolean.class);
	}

	public <T> T getValue(Class<T> type) {
		// If the type is string, parse the color codes and prefix
		if (type == String.class && this != PREFIX && this.value != null) {
			return type.cast(ChatColor.translateAlternateColorCodes('&',
				this.getValue().toString().replace("{Prefix}", PREFIX.string()))
			);
		}

		return type.cast(this.value);
	}

	private Object getValue() {
		return Optional.ofNullable(ChatManager.getPlugin().getSettingsManager().getMessages().get(this.key))
			.orElse(this.defaultValue);
	}

	public static void reload() {
		for (Message key : values()) {
			key.value = key.getValue();
		}
	}

	@Override
	public String toString() {
		return this.string();
	}

}
