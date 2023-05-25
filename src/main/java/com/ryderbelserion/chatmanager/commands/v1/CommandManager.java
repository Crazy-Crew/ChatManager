package com.ryderbelserion.chatmanager.commands.v1;

/*
import com.ryderbelserion.chatmanager.v1.api.Universal;
import com.ryderbelserion.chatmanager.v1.api.enums.ConvertOptions;
import com.ryderbelserion.chatmanager.v1.api.enums.DebugOptions;
import com.ryderbelserion.chatmanager.v1.api.enums.ToggleOptions;
import com.ryderbelserion.chatmanager.commands.v1.subcommands.admin.CommandConvert;
import com.ryderbelserion.chatmanager.commands.v1.subcommands.admin.CommandDebug;
import com.ryderbelserion.chatmanager.commands.v1.subcommands.admin.CommandReload;
import com.ryderbelserion.chatmanager.commands.v1.subcommands.admin.CommandStaffChat;
import com.ryderbelserion.chatmanager.commands.v1.subcommands.player.CommandList;
import com.ryderbelserion.chatmanager.commands.v1.subcommands.player.CommandMOTD;
import com.ryderbelserion.chatmanager.commands.v1.subcommands.player.CommandToggle;
import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.bukkit.message.BukkitMessageKey;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Suggestion;
import dev.triumphteam.cmd.core.message.MessageKey;
import dev.triumphteam.cmd.core.suggestion.SuggestionKey;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import java.util.ArrayList;
import java.util.List;

@Command("chatmanager")
public class CommandManager implements Universal {

    private final String pageOne = """
                &6<> &f= Required Arguments
                &2[] &f= Optional Arguments
                &r
                &7Commands: &c/chatmanager help
                &r
                &f/chatmanager reload &e- Reloads all the configuration files.
                &f/chatmanager debug &e- Debugs all the configuration files.
                &f/chatmanager antiswear &6- Shows a list of commands for anti swear.
                &f/chatmanager autobroadcast &e- Shows a list of commands for auto broadcast.
                &f/chatmanager bannedcommands &e- Shows a list of commands for banned commands.
                &f/chatmanager chatradius &e- Shows a list of commands for chat radius.
                &r
                &7Page 1/3. Type &c/chatmanager help 2 &7to go to the next page.
                """;

    @Command
    @Permission(value = "chatmanager.command.help", def = PermissionDefault.TRUE)
    public void help(CommandSender sender) {
        Methods.sendMessage(sender, pageOne, true);
    }

    @Command("help")
    @Permission(value = "chatmanager.command.help", def = PermissionDefault.TRUE)
    public void help(CommandSender sender, @Suggestion("pages") String page) {
        String pageTwo = """
                &r
                &f/chatmanager announcement &6<message> &e- Broadcasts an announcement message to the server.
                &f/chatmanager broadcast &6<message> &e- Broadcasts a message to the server.
                &f/chatmanager clear-chat &e- Clears global chat.
                &f/chatmanager colors &e- Shows a list of color codes.
                &f/chatmanager command-spy &e- Staff can see what commands every player types on the server.
                &f/chatmanager formats &e- Shows a list of format codes.
                &f/chatmanager message &6<player> <message> &e- Sends a player a private message.
                &f/chatmanager motd &e- Shows the servers MOTD.
                &f/chatmanager mute-chat &e- Mutes the server chat preventing players from talking in chat.
                &r
                &7Page 2/3. Type &c/chatmanager help 3 &7to go to the next page.
                """;

        String pageThree = """
                &r
                &f/chatmanager perworldchat bypass &e- Bypass the perworld chat feature.
                &f/chatmanager ping &e- Shows your current ping.
                &f/chatmanager reply &e- Quickly reply to the last player to message you.
                &f/chatmanager rules &e- Shows a list of the server rules.
                &f/chatmanager staffchat &e- Talk secretly to other staff members online.
                &f/chatmanager toggle &e- Toggles receiving chat messages, private messages or mentions.
                &f/chatmanager warning &6<message> &e- Broadcasts a warning message to the server.
                &r
                &7Page 3/3. Type &c/chatmanager help 2 &7to go to the previous page.
                """;

        switch (page) {
            case "1" -> Methods.sendMessage(sender, pageOne, true);
            case "2" -> Methods.sendMessage(sender, pageTwo, true);
            case "3" -> Methods.sendMessage(sender, pageThree, true);
            default -> Methods.sendMessage(sender, "The only valid options are 3, 2 and 1.", true);
        }
    }

    private static final BukkitCommandManager<CommandSender> commandManager = BukkitCommandManager.create(plugin);

    public static void setup() {
        commandManager.registerMessage(MessageKey.UNKNOWN_COMMAND, ((sender, context) -> Methods.sendMessage(sender, "&cUnknown Command", true)));

        commandManager.registerMessage(MessageKey.INVALID_ARGUMENT, (((sender, context) -> Methods.sendMessage(sender, "&c " + context.getArgumentName() + "&e is invalid.", true))));

        commandManager.registerMessage(MessageKey.NOT_ENOUGH_ARGUMENTS, (((sender, context) -> {

        })));

        //commandManager.registerMessage(MessageKey.NOT_ENOUGH_ARGUMENTS, ((sender, context) -> {
            //String command = context.getCommand();
            //String subCommand = context.getSubCommand();

            //String correctUsage = switchCommands(command, subCommand);

            //if (correctUsage != null) sender.sendMessage(correctUsage);
        //}));

        //commandManager.registerMessage(MessageKey.TOO_MANY_ARGUMENTS, ((sender, context) -> {
            //String command = context.getCommand();
            //String subCommand = context.getSubCommand();

            //String correctUsage = switchCommands(command, subCommand);

            //if (correctUsage != null) sender.sendMessage(correctUsage);
        //}));

        commandManager.registerMessage(BukkitMessageKey.NO_PERMISSION, ((sender, context) -> Methods.sendMessage(sender, Methods.noPermission(), true)));

        commandManager.registerMessage(BukkitMessageKey.PLAYER_ONLY, (sender, context) -> Methods.sendMessage(sender, "&cOnly a player can use this command.", true));

        commandManager.registerMessage(BukkitMessageKey.CONSOLE_ONLY, (sender, context) -> Methods.sendMessage(sender, "&eOnly the console can use this command.", true));

        commandManager.registerSuggestion(SuggestionKey.of("pages"), (sender, context) -> List.of("1", "2", "3"));

        commandManager.registerSuggestion(SuggestionKey.of("debugs"), (sender, context) -> {
            ArrayList<String> values = new ArrayList<>();

            for (DebugOptions value : DebugOptions.values()) {
                values.add(value.getName());
            }

            return values;
        });

        commandManager.registerSuggestion(SuggestionKey.of("toggles"), (sender, context) -> {
            ArrayList<String> values = new ArrayList<>();

            for (ToggleOptions value : ToggleOptions.values()) {
                values.add(value.getName());
            }

            return values;
        });

        commandManager.registerSuggestion(SuggestionKey.of("convert-options"), (sender, context) -> {
            ArrayList<String> values = new ArrayList<>();

            for (ConvertOptions value : ConvertOptions.values()) {
                values.add(value.getName());
            }

            return values;
        });

        commandManager.registerSuggestion(SuggestionKey.of("booleans"), ((sender, context) -> List.of("true", "false")));

        commandManager.registerCommand(new CommandManager());

        // Admin Commands.
        commandManager.registerCommand(new CommandReload());
        commandManager.registerCommand(new CommandDebug());
        commandManager.registerCommand(new CommandStaffChat());
        commandManager.registerCommand(new CommandConvert());

        // Player Commands.
        commandManager.registerCommand(new CommandToggle());

        commandManager.registerCommand(new CommandList());

        commandManager.registerCommand(new CommandMOTD());
    }

    private static String switchCommands(String command, String subCommand) {
        String commandOrder = "/" + command + " " + subCommand + " ";

        String correctUsage = null;

        if (command.equals("chatmanager")) {
            switch (subCommand) {
                case "help" -> correctUsage = commandOrder + "<page>";
                case "debug" -> correctUsage = commandOrder + "<name>";
                case "toggle" -> correctUsage = commandOrder + "<type>";
                case "convert" -> correctUsage = commandOrder + "<rename_files/convert_old_files> <true/false>";
            }
        }

        return correctUsage;
    }
}
 */