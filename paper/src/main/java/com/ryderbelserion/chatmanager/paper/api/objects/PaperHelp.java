package com.ryderbelserion.chatmanager.paper.api.objects;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.api.configs.ConfigKeys;
import com.ryderbelserion.chatmanager.api.configs.ConfigManager;
import com.ryderbelserion.chatmanager.api.configs.objects.help.ColorProperty;
import com.ryderbelserion.chatmanager.api.configs.objects.help.HelpProperty;
import com.ryderbelserion.chatmanager.api.configs.objects.help.HighlightProperty;
import com.ryderbelserion.chatmanager.paper.ChatManager;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.incendo.cloud.minecraft.extras.MinecraftHelp;
import org.incendo.cloud.paper.PaperCommandManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PaperHelp {

    private final ChatManager plugin = ChatManager.get();

    private final PaperCommandManager<CommandSourceStack> manager = this.plugin.getManager();

    private final SettingsManager config = ConfigManager.getConfig();

    private MinecraftHelp<CommandSourceStack> help;

    public void init() {
        final HelpProperty property = this.config.getProperty(ConfigKeys.help_property);

        final ColorProperty color = property.getColor();

        final TextColor primaryColor = getColor(color.getPrimaryColor());

        final HighlightProperty highlightProperty = color.getHighlight();

        final TextColor highlightColor = getColor(highlightProperty.getHighlightColor());
        final TextColor highlightColorAlternative = getColor(highlightProperty.getHighlightColorAlternative());
        final TextColor textColor = getColor(color.getTextColor());
        final TextColor accentColor = getColor(color.getAccentColor());

        this.help = MinecraftHelp.<CommandSourceStack>builder()
                .commandManager(this.manager)
                .audienceProvider(CommandSourceStack::getSender)
                .commandPrefix("/chatmanager help")
                .colors(MinecraftHelp.helpColors(
                        primaryColor != null ? primaryColor : NamedTextColor.GOLD,
                        highlightColor != null ? highlightColor : NamedTextColor.GREEN,
                        highlightColorAlternative != null ? highlightColorAlternative : NamedTextColor.YELLOW,
                        textColor != null ? textColor : NamedTextColor.GRAY,
                        accentColor != null ? accentColor : NamedTextColor.DARK_GRAY
                ))
                .maxResultsPerPage(property.getResultsPerPage())
                .build();
    }

    public final MinecraftHelp<CommandSourceStack> getHelp() {
        return this.help;
    }

    public @Nullable TextColor getColor(@NotNull final String color) {
        if (color.isEmpty()) return null;

        final String[] rgb = color.split(",");

        if (rgb.length != 3) {
            return null;
        }

        return TextColor.color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
    }
}