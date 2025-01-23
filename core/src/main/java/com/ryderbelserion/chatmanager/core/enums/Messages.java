package com.ryderbelserion.chatmanager.core.enums;

import ch.jalu.configme.properties.Property;
import com.ryderbelserion.chatmanager.core.ChatProvider;
import com.ryderbelserion.chatmanager.core.api.IChatManager;
import com.ryderbelserion.chatmanager.core.api.IUserManager;
import com.ryderbelserion.chatmanager.core.enums.other.Action;
import com.ryderbelserion.chatmanager.core.enums.other.Files;
import com.ryderbelserion.chatmanager.core.managers.configs.locale.RootKeys;
import com.ryderbelserion.chatmanager.core.objects.User;
import com.ryderbelserion.core.FusionProvider;
import com.ryderbelserion.core.files.types.YamlCustomFile;
import com.ryderbelserion.core.util.StringUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Messages {

    reload_plugin(RootKeys.reload_plugin);

    private Property<String> property;

    private Property<List<String>> properties;
    private boolean isList = false;

    Messages(@NotNull final Property<String> property) {
        this.property = property;
    }

    Messages(@NotNull final Property<List<String>> properties, final boolean isList) {
        this.properties = properties;
        this.isList = isList;
    }

    private static final IChatManager provider = ChatProvider.getChatManager();

    private static final IUserManager userManager = provider.getUserManager();

    private final YamlCustomFile config = Files.config.getCustomFile();

    public String getString(final Audience audience) {
        final @Nullable User user = userManager.getUser(audience);

        if (user == null) {
            throw new NullPointerException("User was not found in the cache when trying to send a message!");
        }

        return user.getLocale().getProperty(this.property);
    }

    public List<String> getList(final Audience audience) {
        final @Nullable User user = userManager.getUser(audience);

        if (user == null) {
            throw new NullPointerException("User was not found in the cache when trying to send a message!");
        }

        return user.getLocale().getProperty(this.properties);
    }

    public Component getMessage(@NotNull final Audience sender) {
        return getMessage(sender, new HashMap<>());
    }

    public Component getMessage(@NotNull final Audience sender, @NotNull final String placeholder, @NotNull final String replacement) {
        Map<String, String> placeholders = new HashMap<>() {{
            put(placeholder, replacement);
        }};

        return getMessage(sender, placeholders);
    }

    public Component getMessage(@NotNull final Audience sender, @NotNull final Map<String, String> placeholders) {
        placeholders.putIfAbsent("prefix", this.config.getStringValue("root", "prefix"));

        return parse(sender, placeholders);
    }

    public void sendMessage(final Audience sender, final String placeholder, final String replacement) {
        final Action action = Action.getAction(this.config.getStringValueWithDefault("send_message", "root", "message-action"));

        switch (action) {
            case send_message -> sendRichMessage(sender, placeholder, replacement);
            case send_actionbar -> sendActionBar(sender, placeholder, replacement);
        }
    }

    public void sendMessage(final Audience sender, final Map<String, String> placeholders) {
        final Action action = Action.getAction(this.config.getStringValueWithDefault("send_message", "root", "message-action"));

        switch (action) {
            case send_message -> sendRichMessage(sender, placeholders);
            case send_actionbar -> sendActionBar(sender, placeholders);
        }
    }

    public void sendMessage(final Audience sender) {
        final Action action = Action.getAction(this.config.getStringValueWithDefault("send_message", "root", "message-action"));

        switch (action) {
            case send_message -> sendRichMessage(sender);
            case send_actionbar -> sendActionBar(sender);
        }
    }

    public void sendActionBar(final Audience sender, final String placeholder, final String replacement) {
        final Component component = getMessage(sender, placeholder, replacement);

        if (component.equals(Component.empty())) return;

        sender.sendActionBar(component);
    }

    public void sendActionBar(final Audience sender, final Map<String, String> placeholders) {
        final Component component = getMessage(sender, placeholders);

        if (component.equals(Component.empty())) return;

        sender.sendActionBar(component);
    }

    public void sendActionBar(final Audience sender) {
        final Component component = getMessage(sender);

        if (component.equals(Component.empty())) return;

        sender.sendActionBar(component);
    }

    public void sendRichMessage(final Audience sender, final String placeholder, final String replacement) {
        final Component component = getMessage(sender, placeholder, replacement);

        if (component.equals(Component.empty())) return;

        sender.sendMessage(component);
    }

    public void sendRichMessage(final Audience sender, final Map<String, String> placeholders) {
        final Component component = getMessage(sender, placeholders);

        if (component.equals(Component.empty())) return;

        sender.sendMessage(component);
    }

    public void sendRichMessage(final Audience sender) {
        final Component component = getMessage(sender);

        if (component.equals(Component.empty())) return;

        sender.sendMessage(component);
    }

    public final boolean isList() {
        return this.isList;
    }

    private Component parse(final Audience audience, final Map<String, String> placeholders) {
        String message;

        if (this.isList) {
            message = StringUtils.toString(getList(audience));
        } else {
            message = getString(audience);
        }

        return FusionProvider.get().placeholders(audience, message, placeholders);
    }
}