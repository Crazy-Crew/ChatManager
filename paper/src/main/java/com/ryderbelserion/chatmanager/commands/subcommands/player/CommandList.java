package com.ryderbelserion.chatmanager.commands.subcommands.player;

import com.ryderbelserion.chatmanager.commands.CommandManager;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import java.text.DecimalFormat;

public class CommandList extends CommandManager {

    private final DecimalFormat decimalFormat = new DecimalFormat("#,###");

    @SubCommand("list")
    @Permission(value = "chatmanager.command.list", def = PermissionDefault.TRUE)
    public void list(CommandSender sender) {
        //String online = decimalFormat.format(plugin.getServer().getOnlinePlayers().size());
        //String max = decimalFormat.format(plugin.getServer().getMaxPlayers());

        //StringBuilder builder = new StringBuilder();
        //StringBuilder secondBuilder = new StringBuilder();

        //if (!sender.hasPermission("chatmanager.lists.staff") && !(sender instanceof ConsoleCommandSender)) return;

        //if (sender instanceof Player player) {
        //    for (String list : config.getStringList("Lists.Staff_List")) {
        //        Methods.sendMessage(player, placeholderManager.setPlaceholders(player, list.replace("{players}", secondBuilder.toString()).replace("{server_online}", online).replace("{server_max_players}", max)), true);
        //    }

        //    return;
        //}

        //if (player.hasPermission("chatmanager.staff") || player.isOp()) {
        //    if (secondBuilder.length() > 0) secondBuilder.append(", ");

        //    secondBuilder.append("&a").append(player.getName()).append("&8");

       //     return;
        //}

        //if (!sender.hasPermission("chatmanager.lists.players") && !(sender instanceof ConsoleCommandSender)) return;

        //if (sender instanceof Player player) {
        //   for (String list : config.getStringList("Lists.Player_List")) {
        //        Methods.sendMessage(player, placeholderManager.setPlaceholders(player, list.replace("{players}", builder.toString()).replace("{server_online}", online).replace("{server_max_players}", max)), true);
        //    }

        //    return;
        //}

        //for (Player player : plugin.getServer().getOnlinePlayers()) {
        //    if (builder.length() > 0) builder.append(", ");

        //    builder.append("&a").append(player.getName()).append("&8");
        //}

        //switch (listOptions.getName()) {
        //    case "staff" -> {
        //        for (String list : config.getStringList("Lists.Staff_List")) {
        //            Methods.sendMessage(sender, list.replace("{players}", builder.toString()).replace("{server_online}", online).replace("{server_max_players}", max), true);
        //        }
        //    }

        //    case "players" -> {
        //        for (String list : config.getStringList("Lists.Player_List")) {
        //            Methods.sendMessage(sender, list.replace("{players}", builder.toString()).replace("{server_online}", online).replace("{server_max_players}", max), true);
        //        }
        //    }
        //}
    }
}