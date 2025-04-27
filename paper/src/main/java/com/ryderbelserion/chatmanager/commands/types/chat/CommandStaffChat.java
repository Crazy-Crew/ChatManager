package com.ryderbelserion.chatmanager.commands.types.chat;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.chat.StaffChatData;
import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.UUID;

public class CommandStaffChat extends AnnotationFeature {

    private final ApiLoader api = this.plugin.api();

    private final StaffChatData data = this.api.getStaffChatData();

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command("chatmanager staffchat <message>")
    @CommandDescription("Allows the sender to use staff chat!")
    @Permission(value = "chatmanager.staffchat", mode = Permission.Mode.ANY_OF)
    public void staffchat(final CommandSender sender, @Argument("message") @Nullable final String arg) {
        final FileConfiguration config = Files.CONFIG.getConfiguration();

        if (config.getBoolean("Staff-Chat.Enable", false)) {
            Methods.sendMessage(sender, "&4Error: &cStaff Chat is currently disabled & cannot be used at this time.", true);

            return;
        }

        final Server server = this.plugin.getServer();

        if (sender instanceof Player player) {
            if (arg == null) {
                final UUID uuid = player.getUniqueId();

                final boolean isValid = this.data.containsUser(uuid);

                if (isValid) {
                    this.data.removeUser(uuid);

                    final BossBarUtil bossBar = new BossBarUtil(Methods.placeholders(true, player, Methods.color(config.getString("Staff_Chat.Boss_Bar.Title", "&eStaff Chat"))));
                    bossBar.removeStaffBossBar(player);

                    Messages.STAFF_CHAT_DISABLED.sendMessage(player);

                    return;
                }

                this.data.addUser(uuid);

                boolean isBossBarEnabled = config.getBoolean("Staff_Chat.Boss_Bar.Enable", false);

                if (isBossBarEnabled) {
                    BossBarUtil bossBar = new BossBarUtil(Methods.placeholders(true, player, Methods.color(config.getString("Staff_Chat.Boss_Bar.Title", "&eStaff Chat"))));

                    bossBar.setStaffBossBar(player);
                }

                Messages.STAFF_CHAT_ENABLED.sendMessage(player);

                return;
            }

            for (final Player staff : server.getOnlinePlayers()) {
                if (Permissions.TOGGLE_STAFF_CHAT.hasPermission(staff)) {
                    Methods.sendMessage(staff, config.getString("Staff_Chat.Format", "&e[&bStaffChat&e] &a{player} &7> &b{message}").replace("{player}", player.getName()).replace("{message}", arg));
                }
            }

            Methods.tellConsole(config.getString("Staff_Chat.Format", "&e[&bStaffChat&e] &a{player} &7> &b{message}").replace("{player}", player.getName()).replace("{message}", arg), false);

            return;
        }

        if (arg == null) return;

        for (final Player staff : server.getOnlinePlayers()) {
            if (staff.hasPermission(Permissions.TOGGLE_STAFF_CHAT.getNode())) {
                Methods.sendMessage(staff, config.getString("Staff_Chat.Format", "&e[&bStaffChat&e] &a{player} &7> &b{message}").replace("{player}", sender.getName()).replace("{message}", arg), true);
            }
        }

        Methods.sendMessage(sender, config.getString("Staff_Chat.Format", "&e[&bStaffChat&e] &a{player} &7> &b{message}").replace("{player}", sender.getName()).replace("{message}", arg), true);
    }
}