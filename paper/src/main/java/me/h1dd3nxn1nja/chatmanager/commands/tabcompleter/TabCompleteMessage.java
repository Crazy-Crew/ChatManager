package me.h1dd3nxn1nja.chatmanager.commands.tabcompleter;

import com.ryderbelserion.core.api.support.PluginManager;
import com.ryderbelserion.core.api.support.interfaces.Plugin;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.support.EssentialsSupport;
import me.h1dd3nxn1nja.chatmanager.support.PluginHandler;
import me.h1dd3nxn1nja.chatmanager.support.PluginSupport;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class TabCompleteMessage implements TabCompleter {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@NotNull
	private final PluginHandler pluginHandler = this.plugin.getPluginManager();

	@NotNull
	private final EssentialsSupport essentialsSupport = this.pluginHandler.getEssentialsSupport();

	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
		if (args.length == 0) return null;

		List<Player> matchPlayer = this.plugin.getServer().matchPlayer(args[args.length - 1]);
		ArrayList<String> list = new ArrayList<>();

		if (!(sender instanceof Player player2)) {
			matchPlayer.forEach(player -> list.add(player.getName()));
			return list;
		}

		boolean hasPermission = player2.hasPermission("chatmanager.bypass.ignored");
		boolean hasPermission2 = player2.hasPermission("chatmanager.bypass.vanish");
		boolean hasPermission3 = player2.hasPermission("chatmanager.bypass.togglepm");

        for (Player player3 : matchPlayer) {
            if (!hasPermission && PluginSupport.ESSENTIALS.isPluginEnabled() && this.essentialsSupport.isIgnored(player3.getUniqueId(), player2.getUniqueId())) continue;

            if (!hasPermission2) {
				final Plugin genericVanish = PluginManager.getPlugin("GenericVanish");

                if (genericVanish != null && genericVanish.isEnabled() && genericVanish.isVanished(player3.getUniqueId())) {
                    continue;
                }
            }

            if (!hasPermission3 && this.plugin.api().getToggleMessageData().containsUser(player3.getUniqueId())) continue;

            list.add(player3.getName());
        }

		return list;
	}
}