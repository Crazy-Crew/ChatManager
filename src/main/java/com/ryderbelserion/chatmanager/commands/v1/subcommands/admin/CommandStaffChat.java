package com.ryderbelserion.chatmanager.commands.v1.subcommands.admin;

/*
import com.ryderbelserion.chatmanager.commands.v1.CommandManager;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Optional;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.permissions.PermissionDefault;
import java.util.UUID;

@Command("staffchat")
@Permission(value = "chatmanager.command.staffchat", def = PermissionDefault.OP)
public class CommandStaffChat extends CommandManager implements Listener {

    @Command()
    public void execute(CommandSender sender, @Optional String message) {
        if (!config.getBoolean("Staff_Chat.Enable")) {
            Methods.sendMessage(sender, "&4Error: &cStaff Chat is currently disabled & cannot be used at this time.", true);
            return;
        }

        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();
            boolean isValid = plugin.getCrazyManager().api().getStaffChatData().containsUser(uuid);

            if (message != null && !isValid) {
                sendStaffMessage(sender, message);

                return;
            }

            if (isValid) {
                plugin.getCrazyManager().api().getStaffChatData().removeUser(uuid);

                BossBarUtil bossBar = new BossBarUtil(Methods.color(config.getString("Staff_Chat.Boss_Bar.Title")));
                bossBar.removeStaffBossBar(player);

                Methods.sendMessage(player, messages.getString("Staff_Chat.Disabled"), true);

                return;
            }

            plugin.getCrazyManager().api().getStaffChatData().addUser(uuid);

            BossBarUtil bossBar = new BossBarUtil(Methods.color(config.getString("Staff_Chat.Boss_Bar.Title")));
            bossBar.setStaffBossBar(player);

            Methods.sendMessage(player, messages.getString("Staff_Chat.Enabled"), true);
            return;
        }

        if (message != null) sendStaffMessage(sender, message);
    }

    private void sendStaffMessage(CommandSender sender, @Optional String message) {
        for (Player staff : plugin.getServer().getOnlinePlayers()) {
            if (staff.hasPermission("chatmanager.staffchat") && staff != sender) Methods.sendMessage(staff, config.getString("Staff_Chat.Format").replace("{player}", sender.getName()).replace("{message}", message), true);
        }

        Methods.sendMessage(sender, config.getString("Staff_Chat.Format").replace("{player}", sender.getName()).replace("{message}", message), true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        boolean isValid = plugin.getCrazyManager().api().getStaffChatData().containsUser(uuid);

        if (!isValid || player.hasPermission("chatmanager.staffchat")) return;

        event.setCancelled(true);

        plugin.getServer().getOnlinePlayers().forEach(staff -> {
            if (staff.hasPermission("chatmanager.staffchat")) Methods.sendMessage(staff, config.getString("Staff_Chat.Format").replace("{player}", player.getName()).replace("{message}", event.getMessage()), true);
        });
    }
}*/