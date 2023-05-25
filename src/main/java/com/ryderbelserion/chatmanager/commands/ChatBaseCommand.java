package com.ryderbelserion.chatmanager.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.HelpEntry;
import co.aikar.commands.PaperCommandManager;
import co.aikar.commands.annotation.*;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.configs.types.LocaleSettings;
import com.ryderbelserion.chatmanager.api.configs.types.PluginSettings;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.List;
import static com.ryderbelserion.chatmanager.utils.MessageUtils.*;

@CommandAlias("chatmanager")
@Description("Manage or use the ChatManager plugin.")
public class ChatBaseCommand extends BaseCommand {

    private final ChatManager plugin = ChatManager.getPlugin();

    @Subcommand("help")
    @Description("The base help command for the plugin.")
    @CommandPermission("chatmanager.help")
    public void help(CommandSender sender, @Syntax("[page]") CommandHelp help) {
        help.setPerPage(plugin.getConfigBuilder().getPluginSettings().getProperty(PluginSettings.MAX_HELP_PAGE_ENTRIES));

        generateHelp(help.getPerPage(), help.getPage(), help.getHelpEntries(), sender);
    }

    public static void setup(PaperCommandManager manager) {
        manager.enableUnstableAPI("help");

        registerCompletions(manager);
    }

    private static void registerCompletions(PaperCommandManager manager) {

    }

    private void generateHelp(int maxPage, int page, List<HelpEntry> entries, CommandSender sender) {
        int pageStartEntry = maxPage * (page - 1);

        String invalidPage = plugin.getConfigBuilder().getLocaleSettings().getProperty(LocaleSettings.INVALID_PAGE).replaceAll("%page%", String.valueOf(page));

        if (page <= 0 || pageStartEntry >= entries.size()) {
            send((Audience) sender, invalidPage);
            return;
        }

        String hoverFormat = plugin.getConfigBuilder().getLocaleSettings().getProperty(LocaleSettings.HOVER_FORMAT);
        String hoverAction = plugin.getConfigBuilder().getLocaleSettings().getProperty(LocaleSettings.HOVER_ACTION);

        String header = plugin.getConfigBuilder().getLocaleSettings().getProperty(LocaleSettings.HELP_HEADER).replaceAll("%page%", String.valueOf(page));

        send((Audience) sender, header, false);

        for (int i = pageStartEntry; i < (pageStartEntry + maxPage); i++) {
            if (entries.size()-1 < i) continue;

            HelpEntry command = entries.get(i);

            if (!command.shouldShow()) continue;

            String name = command.getCommandPrefix() + command.getCommand();
            String desc = command.getDescription();

            String format = plugin.getConfigBuilder().getLocaleSettings().getProperty(LocaleSettings.PAGE_FORMAT)
                    .replaceAll("%command%", name)
                    .replaceAll("%description%", desc);

            String builtCommand = command.getParameters().length > 0 ? format.replaceAll("%args%", command.getParameterSyntax()) : format;

            if (sender instanceof Player player) {
                hover(
                        (Audience) player,
                        builtCommand,
                        hoverFormat.replaceAll("%command%", name).replaceAll("%args%", command.getParameterSyntax()),
                        name,
                        ClickEvent.Action.valueOf(hoverAction.toUpperCase()));
            } else {
                send((Audience) sender, format, false);
            }
        }

        String pageTag = plugin.getConfigBuilder().getLocaleSettings().getProperty(LocaleSettings.GO_TO_PAGE);

        String footer = plugin.getConfigBuilder().getLocaleSettings().getProperty(LocaleSettings.HELP_FOOTER);

        String back = plugin.getConfigBuilder().getLocaleSettings().getProperty(LocaleSettings.PAGE_BACK);

        String next = plugin.getConfigBuilder().getLocaleSettings().getProperty(LocaleSettings.PAGE_NEXT);

        if (sender instanceof Player player) {
            if (page > 1) {
                int number = page-1;

                hover((Audience) player, footer.replaceAll("%page%", String.valueOf(page)),  pageTag.replaceAll("%page%", String.valueOf(number)), back,"/chatmanager help " + number, ClickEvent.Action.RUN_COMMAND);
            } else if (page < entries.size()) {
                int number = page+1;

                hover((Audience) player, footer.replaceAll("%page%", String.valueOf(page)),  pageTag.replaceAll("%page%", String.valueOf(number)), next,"/chatmanager help " + number, ClickEvent.Action.RUN_COMMAND);
            }
        } else {
            send((Audience) sender, footer.replaceAll("%page%", String.valueOf(page)), false);
        }
    }
}