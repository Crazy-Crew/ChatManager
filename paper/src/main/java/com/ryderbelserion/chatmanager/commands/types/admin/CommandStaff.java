package com.ryderbelserion.chatmanager.commands.types.admin;

import com.ryderbelserion.chatmanager.api.objects.PaperUser;
import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.chatmanager.enums.core.PlayerState;
import com.ryderbelserion.chatmanager.utils.UserUtils;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
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

public class CommandStaff extends AnnotationFeature {

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

        if (sender instanceof Player player) {
            final PaperUser user = UserUtils.getUser(player);

            if (arg == null) {
                if (user.hasState(PlayerState.STAFF_CHAT)) {
                    user.removeState(PlayerState.STAFF_CHAT);

                    final BossBarUtil bossBar = new BossBarUtil(Methods.placeholders(true, player, Methods.color(config.getString("Staff_Chat.Boss_Bar.Title", "&eStaff Chat"))));
                    bossBar.removeStaffBossBar(player);

                    Messages.STAFF_CHAT_DISABLED.sendMessage(player);

                    return;
                }

                user.addState(PlayerState.STAFF_CHAT);

                boolean isBossBarEnabled = config.getBoolean("Staff_Chat.Boss_Bar.Enable", false);

                if (isBossBarEnabled) {
                    BossBarUtil bossBar = new BossBarUtil(Methods.placeholders(true, player, Methods.color(config.getString("Staff_Chat.Boss_Bar.Title", "&eStaff Chat"))));

                    bossBar.setStaffBossBar(player);
                }

                Messages.STAFF_CHAT_ENABLED.sendMessage(player);

                return;
            }

            for (final Player staff : this.server.getOnlinePlayers()) {
                if (Permissions.TOGGLE_STAFF_CHAT.hasPermission(staff)) {
                    Methods.sendMessage(staff, config.getString("Staff_Chat.Format", "&e[&bStaffChat&e] &a{player} &7> &b{message}").replace("{player}", player.getName()).replace("{message}", arg));
                }
            }

            Methods.tellConsole(config.getString("Staff_Chat.Format", "&e[&bStaffChat&e] &a{player} &7> &b{message}").replace("{player}", player.getName()).replace("{message}", arg), false);

            return;
        }

        if (arg == null) return;

        for (final Player staff : this.server.getOnlinePlayers()) {
            if (staff.hasPermission(Permissions.TOGGLE_STAFF_CHAT.getNode())) {
                Methods.sendMessage(staff, config.getString("Staff_Chat.Format", "&e[&bStaffChat&e] &a{player} &7> &b{message}").replace("{player}", sender.getName()).replace("{message}", arg), true);
            }
        }

        Methods.sendMessage(sender, config.getString("Staff_Chat.Format", "&e[&bStaffChat&e] &a{player} &7> &b{message}").replace("{player}", sender.getName()).replace("{message}", arg), true);
    }
}