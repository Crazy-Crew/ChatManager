package com.ryderbelserion.chatmanager.api.configs.types.sections;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;

import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class PluginSupportSection implements SettingsHolder {

    public PluginSupportSection() {}

    private static final String settings = "settings.";

    @Override
    public void registerComments(CommentsConfiguration conf) {
        String[] header = {
                "=========================================================",
                "",
                "This section is solely related to plugin dependencies.",
                "You can now have easy control over what gets enabled or not",
                "",
                "========================================================="
        };

        conf.setComment("support", header);
    }

    @Comment({
            "PlaceholderAPI is required for this option to be true!",
            "https://www.spigotmc.org/resources/placeholderapi.6245/"
    })
    public static final Property<Boolean> PLACEHOLDER_API_SUPPORT = newProperty("support.placeholderapi", false);

    @Comment({
            "EssentialsX is required for this option to be true!",
            "https://modrinth.com/plugin/essentialsx"
    })
    public static final Property<Boolean> ESSENTIALS_SUPPORT = newProperty("support.essentials", false);

    @Comment({
            "Vault is required for this option to be true!",
            "https://www.spigotmc.org/resources/vault.34315/"
    })
    public static final Property<Boolean> VAULT_SUPPORT = newProperty("support.vault", false);

    @Comment({
            "SuperVanish is required for this option to be true!",
            "https://www.spigotmc.org/resources/supervanish-be-invisible.1331/"
    })
    public static final Property<Boolean> SUPER_VANISH_SUPPORT = newProperty("support.supervanish", false);

    @Comment({
            "PremiumVanish is required for this option to be true!",
            "https://www.spigotmc.org/resources/premiumvanish-stay-hidden-bungee-support.14404/"
    })
    public static final Property<Boolean> PREMIUM_VANISH_SUPPORT = newProperty("support.premiumvanish", false);

    @Comment({
            "LuckPerms is required for this option to be true!",
            "https://modrinth.com/mod/luckperms"
    })
    public static final Property<Boolean> LUCK_PERMS_SUPPORT = newProperty("support.luckperms", false);
}