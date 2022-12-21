package me.h1dd3nxn1nja.chatmanager.configuration;

import me.h1dd3nxn1nja.chatmanager.ChatManager;

/**
 * @author Niels Warrens - 21/12/2022
 */
public enum ConfigurationKey {

    MENTIONS_ENABLED("Mentions.Enable"),
    MENTIONS_SOUND("Mentions.Sound"),
    MENTIONS_TITLE_HEADER("Mentions.Title.Header"),
    MENTIONS_TITLE_FOOTER("Mentions.Title.Footer"),
    MENTIONS_TAG_SYMBOL("Mentions.Tag_Symbol"),
    MENTIONS_TAG_COLOR("Mentions.Mention_Color"),
    ;

    private final String key;
    private Object value;

    ConfigurationKey(String key) {
        this.key = key;
        this.value = this.getValue();
    }

    public <T> T getValue(Class<T> type) {
        System.out.println("Getting value for " + this.key);
        return type.cast(this.value);
    }

    private Object getValue() {
        return ChatManager.getPlugin().getSettingsManager().getConfig()
                .get(this.key);
    }

    public static void reload() {
        for (ConfigurationKey key : values()) {
            key.value = key.getValue();
        }
    }

}
