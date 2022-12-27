package me.h1dd3nxn1nja.chatmanager.configuration;

import me.h1dd3nxn1nja.chatmanager.ChatManager;

import java.util.Optional;

/**
 * @author Niels Warrens - 21/12/2022
 */
public enum Configuration {

	//	The mention processor(s)
	MENTIONS_ENABLED("Mentions.Enable"),
	MENTIONS_SOUND("Mentions.Sound"),
	MENTIONS_TITLE_HEADER("Mentions.Title.Header"),
	MENTIONS_TITLE_FOOTER("Mentions.Title.Footer"),
	MENTIONS_TAG_SYMBOL("Mentions.Tag_Symbol"),
	MENTIONS_TAG_COLOR("Mentions.Mention_Color"),

	//	The spam processor(s)
	SPAM_BLOCK_REPEATING_MESSAGES_ENABLED("Anti_Spam.Chat.Block_Repetitive_Messages"),
	SPAM_BLOCK_REPEATING_COMMANDS_ENABLED("Anti_Spam.Command.Block_Repetitive_Commands"),
	;

	private final String key;
	private final Object defaultValue;
	private Object value;

	Configuration(String key) {
		this(key, null);
	}

	Configuration(String key, Object defaultValue) {
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
		return type.cast(this.value);
	}

	private Object getValue() {
		return Optional.ofNullable(ChatManager.getPlugin().getSettingsManager().getConfig().get(this.key))
			.orElse(this.defaultValue);
	}

	public static void reload() {
		for (Configuration key : values()) {
			key.value = key.getValue();
		}
	}

	@Override
	public String toString() {
		return this.string();
	}

}
