package me.h1dd3nxn1nja.chatmanager.paper.commands;

import java.util.ArrayList;
import java.util.List;
import me.h1dd3nxn1nja.chatmanager.paper.SettingsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.Methods;
import me.h1dd3nxn1nja.chatmanager.paper.managers.PlaceholderManager;
import me.h1dd3nxn1nja.chatmanager.paper.utils.World;
import org.jetbrains.annotations.NotNull;

public class CommandAutoBroadcast implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	private final PlaceholderManager placeholderManager = plugin.getCrazyManager().getPlaceholderManager();

	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		FileConfiguration autobroadcast = settingsManager.getAutoBroadcast();
		FileConfiguration messages = settingsManager.getMessages();

		if (!(sender instanceof Player player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("autobroadcast")) {
			if (player.hasPermission("chatmanager.autobroadcast")) {
				if (args.length == 0) return checkProtocol(player);
			} else {
				player.sendMessage(Methods.noPermission());
				return true;
			}

			if (args[0].equalsIgnoreCase("help")) {
				if (player.hasPermission("chatmanager.autobroadcast.help")) {
					if (args.length == 1) return checkProtocol(player);
				} else {
					player.sendMessage(Methods.noPermission());
					return true;
				}
			}

			if (args[0].equalsIgnoreCase("list")) {
				if (player.hasPermission("chatmanager.autobroadcast.list")) {
					if (args.length == 1) {
						Methods.sendMessage(player, "&cCommand Usage: &7/AutoBroadcast list [Global|World|Actionbar|Title|Bossbar] [World]", true);
						return true;
					}
				} else {
					Methods.sendMessage(player, Methods.noPermission(), true);
					return true;
				}

				if (args[1].equalsIgnoreCase("global")) {
					if (player.hasPermission("chatmanager.autobroadcast.list")) {
						if (args.length == 2) {
							Methods.sendMessage(player, "", true);
							Methods.sendMessage(player, messages.getString("Auto_Broadcast.List").replace("{section}", "Global"), true);
							Methods.sendMessage(player, "", true);

							String string = autobroadcast.getString("Auto_Broadcast.Global_Messages.Prefix");

							for (String global : autobroadcast.getStringList("Auto_Broadcast.Global_Messages.Messages")) {
								if (string != null)
									player.sendMessage(placeholderManager.setPlaceholders(player, "&7 - " + global.replace("{Prefix}", string)));
							}

							Methods.sendMessage(player, "", true);
						} else {
							Methods.sendMessage(player, "&cCommand Usage: &7/AutoBroadcast list global", true);
						}
					} else {
						Methods.sendMessage(player, Methods.noPermission(), true);
					}
				}

				if (args[1].equalsIgnoreCase("actionbar")) {
					if (player.hasPermission("chatmanager.autobroadcast.list")) {
						if (args.length == 2) {
							Methods.sendMessage(player, "", true);
							Methods.sendMessage(player, messages.getString("Auto_Broadcast.List").replace("{section}", "Actionbar"), true);

							String string = autobroadcast.getString("Auto_Broadcast.Actionbar_Messages.Prefix");

							for (String actionbar : autobroadcast.getStringList("Auto_Broadcast.Actionbar_Messages.Messages")) {
								if (string != null)
									player.sendMessage(placeholderManager.setPlaceholders(player, "&7 - " + actionbar.replace("{Prefix}", string)));
							}

							Methods.sendMessage(player, "", true);
						} else {
							Methods.sendMessage(player, "&cCommand Usage: &7/AutoBroadcast list actionbar", true);
						}
					} else {
						Methods.sendMessage(player, Methods.noPermission(), true);
					}
				}

				if (args[1].equalsIgnoreCase("title")) {
					if (player.hasPermission("chatmanager.autobroadcast.list")) {
						if (args.length == 2) {
							Methods.sendMessage(player, "", true);
							Methods.sendMessage(player, messages.getString("Auto_Broadcast.List").replace("{section}", "Title"), true);

							for (String title : autobroadcast.getStringList("Auto_Broadcast.Title_Messages.Messages")) {
								player.sendMessage(placeholderManager.setPlaceholders(player, "&7 - " + title));
							}

							Methods.sendMessage(player, "", true);
						} else {
							Methods.sendMessage(player, "&cCommand Usage: &7/AutoBroadcast list title", true);
						}
					} else {
						Methods.sendMessage(player, Methods.noPermission(), true);
					}
				}

				if (args[1].equalsIgnoreCase("bossbar")) {
					if (player.hasPermission("chatmanager.autobroadcast.list")) {
						if (args.length == 2) {
							Methods.sendMessage(player, "", true);
							Methods.sendMessage(player, messages.getString("Auto_Broadcast.List").replace("{section}", "Bossbar"), true);

							for (String bossbar : autobroadcast.getStringList("Auto_Broadcast.Bossbar_Messages.Messages")) {
								player.sendMessage(placeholderManager.setPlaceholders(player, "&7 - " + bossbar));
							}

							Methods.sendMessage(player, "", true);
						} else {
							Methods.sendMessage(player, "&cCommand Usage: &7/AutoBroadcast list bossbar", true);
						}
					} else {
						Methods.sendMessage(player, Methods.noPermission(), true);
					}
				}

				if (args[1].equalsIgnoreCase("world")) {
					if (player.hasPermission("chatmanager.autobroadcast.list")) {
						if (args.length == 3) {
							List<World> worlds = new ArrayList<>();

							String broadcast = autobroadcast.getString("Auto_Broadcast.Per_World_Messages.Prefix");

							for (String key : autobroadcast.getConfigurationSection("Auto_Broadcast.Per_World_Messages.Messages").getKeys(false)) {
								World w = new World(key, autobroadcast.getStringList("Auto_Broadcast.Per_World_Messages.Messages." + key), 0);
								worlds.add(w);

								if (args[2].equals(key)) {
									Methods.sendMessage(player, "", true);
									Methods.sendMessage(player, messages.getString("Auto_Broadcast.List").replace("{section}", key), true);

									for (String bcmsg : autobroadcast.getStringList("Auto_Broadcast.Per_World_Messages.Messages." + key)) {
										if (broadcast != null) player.sendMessage(Methods.color(player.getUniqueId(), "&7 - " + bcmsg.replace("{Prefix}", broadcast)));
									}

									Methods.sendMessage(player, "", true);
								}
							}
						} else {
							Methods.sendMessage(player, "&cCommand Usage: &7/AutoBroadcast list world <world>", true);
						}
					} else {
						Methods.sendMessage(player, Methods.noPermission(), true);
					}
				}
			}

			if (args[0].equalsIgnoreCase("add")) {
				if (player.hasPermission("chatmanager.autobroadcast.add")) {
					if (args.length == 1) {
						Methods.sendMessage(player, "&cCommand Usage: &7/AutoBroadcast add [Global|World|Actionbar|Title|Bossbar] <world> <message>", true);
						return true;
					}
				} else {
					Methods.sendMessage(player, Methods.noPermission(), true);
					return true;
				}

				if (args[1].equalsIgnoreCase("global")) {
					if (player.hasPermission("chatmanager.autobroadcast.add")) {
						if (args.length != 2) {
							StringBuilder builder = new StringBuilder();

							for (int i = 2; i < args.length; i++) {
								builder.append(args[i] + " ");
							}

							String msg = builder.toString();
							List<String> msgs = autobroadcast.getStringList("Auto_Broadcast.Global_Messages.Messages");
							msgs.add(msg);
							autobroadcast.set("Auto_Broadcast.Global_Messages.Messages", msgs);
							settingsManager.saveAutoBroadcast();
							settingsManager.reloadAutoBroadcast();
							Methods.sendMessage(player, messages.getString("Auto_Broadcast.Added").replace("{message}", msg).replace("{section}", "Global"), true);
							return true;
						} else {
							Methods.sendMessage(player, Methods.color("&cCommand Usage: &7/AutoBroadcast add global <message>"), true);
						}
					} else
						player.sendMessage(Methods.noPermission());
					Methods.sendMessage(player, Methods.noPermission(), true);
				}
			}

			if (args[1].equalsIgnoreCase("actionbar")) {
				if (player.hasPermission("chatmanager.autobroadcast.add")) {
					if (args.length != 2) {
						StringBuilder builder = new StringBuilder();

						for (int i = 2; i < args.length; i++) {
							builder.append(args[i] + " ");
						}

						String msg = builder.toString();
						List<String> msgs = autobroadcast.getStringList("Auto_Broadcast.Actionbar_Messages.Messages");
						msgs.add(msg);
						autobroadcast.set("Auto_Broadcast.Actionbar_Messages.Messages", msgs);
						settingsManager.saveAutoBroadcast();
						settingsManager.reloadAutoBroadcast();
						Methods.sendMessage(player, messages.getString("Auto_Broadcast.Added").replace("{message}", msg).replace("{section}", "Actionbar"), true);
						return true;
					} else {
						Methods.sendMessage(player, "&cCommand Usage: &7/AutoBroadcast add actionbar <message.", true);
					}
				} else {
					Methods.sendMessage(player, Methods.noPermission(), true);
				}
			}

			if (args[1].equalsIgnoreCase("title")) {
				if (player.hasPermission("chatmanager.autobroadcast.add")) {
					if (args.length != 2) {
						StringBuilder builder = new StringBuilder();

						for (int i = 2; i < args.length; i++) {
							builder.append(args[i] + " ");
						}

						String msg = builder.toString();
						List<String> msgs = autobroadcast.getStringList("Auto_Broadcast.Title_Messages.Messages");
						msgs.add(msg);
						autobroadcast.set("Auto_Broadcast.Title_Messages.Messages", msgs);
						settingsManager.saveAutoBroadcast();
						settingsManager.reloadAutoBroadcast();
						Methods.sendMessage(player, messages.getString("Auto_Broadcast.Added").replace("{message}", msg).replace("{section}", "Title"), true);
						return true;
					} else {
						Methods.sendMessage(player, "&cCommand Usage: &7/AutoBroadcast add title <message>", true);
					}
				} else {
					Methods.sendMessage(player, Methods.noPermission(), true);
				}
			}

			if (args[1].equalsIgnoreCase("bossbar")) {
				if (player.hasPermission("chatmanager.autobroadcast.add")) {
					if (args.length != 2) {
						StringBuilder builder = new StringBuilder();

						for (int i = 2; i < args.length; i++) {
							builder.append(args[i] + " ");
						}

						String msg = builder.toString();
						List<String> msgs = autobroadcast.getStringList("Auto_Broadcast.Bossbar_Messages.Messages");
						msgs.add(msg);
						autobroadcast.set("Auto_Broadcast.Bossbar_Messages.Messages", msgs);
						settingsManager.saveAutoBroadcast();
						settingsManager.reloadAutoBroadcast();
						Methods.sendMessage(player, messages.getString("Auto_Broadcast.Added").replace("{message}", msg).replace("{section}", "Bossbar"), true);
						return true;
					} else {
						Methods.sendMessage(player, "&cCommand Usage: &7/AutoBroadcast add bossbar <message>", true);
					}
				} else {
					Methods.sendMessage(player, Methods.noPermission(), true);
				}
			}

			if (args[1].equalsIgnoreCase("world")) {
				if (player.hasPermission("chatmanager.autobroadcast.add")) {
					if (args.length != 2) {
						List<World> worlds = new ArrayList<>();

						for (String key : autobroadcast.getConfigurationSection("Auto_Broadcast.Per_World_Messages.Messages").getKeys(false)) {
							World w = new World(key, autobroadcast.getStringList("Auto_Broadcast.Per_World_Messages.Messages." + key), 0);
							worlds.add(w);

							if (args[2].equals(key)) {
								if (!player.hasPermission("chatmanager.autobroadcast.add")) {
									Methods.sendMessage(player, Methods.noPermission(), true);
									return true;
								}

								StringBuilder builder = new StringBuilder();

								for (int i = 3; i < args.length; i++) {
									builder.append(args[i] + " ");
								}

								String msg = builder.toString();
								List<String> msgs = autobroadcast.getStringList("Auto_Broadcast.Per_World_Messages.Messages." + key);
								msgs.add(msg);
								autobroadcast.set("Auto_Broadcast.Per_World_Messages.Messages." + key, msgs);
								settingsManager.saveAutoBroadcast();
								settingsManager.reloadAutoBroadcast();
								Methods.sendMessage(player, messages.getString("Auto_Broadcast.Added").replace("{message}", msg).replace("{section}", key), true);
								return true;
							}
						}
					} else {
						Methods.sendMessage(player, "&cCommand Usage: &7/Autobroadcast add world <world> <message>", true);
					}
				} else {
					Methods.sendMessage(player, Methods.noPermission(), true);
				}
			}
		}

		if (args[0].equalsIgnoreCase("Create")) {
			if (player.hasPermission("chatmanager.autobroadcast.create")) {
				if (args.length >= 2) {
					StringBuilder builder = new StringBuilder();

					for (int i = 2; i < args.length; i++) {
						builder.append(args[i] + " ");
					}

					String msg = builder.toString();
					List<String> msgs = autobroadcast.getStringList("Auto_Broadcast.Per_World_Messages.Messages");
					msgs.add(msg);
					autobroadcast.set("Auto_Broadcast.Per_World_Messages.Messages." + args[1], msgs);
					settingsManager.saveAutoBroadcast();
					settingsManager.reloadAutoBroadcast();
					Methods.sendMessage(player, messages.getString("Auto_Broadcast.Created").replace("{world}", args[1]).replace("{message}", msg), true);
					return true;
				} else {
					Methods.sendMessage(player, "&cCommand Usage: &7/Autobroadcast create <world> <message>", true);
				}
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
			}
		}
		return true;
	}

	private boolean checkProtocol(Player player) {
		Methods.sendMessage(player, "", false);
		Methods.sendMessage(player, " &3Auto-Broadcast Help Menu &f(v" + plugin.getDescription().getVersion() + ")", false);
		Methods.sendMessage(player,"", false);
		Methods.sendMessage(player, " &6<> &f= Required Arguments", false);
		Methods.sendMessage(player, " &2[] &f= Optional Arguments", false);
		Methods.sendMessage(player, " ", false);
		Methods.sendMessage(player, " &f/AutoBroadcast Help &e- Shows a list of commands for Auto-Broadcast.", false);
		Methods.sendMessage(player, "  &f/AutoBroadcast Add &6<Global|World|Actionbar|Title|Bossbar> &2[World] &6<message> &e- Add a message to the Auto-Broadcast to a specific world.", false);
		Methods.sendMessage(player, " &f/AutoBroadcast Create &6<world> <message> &e- Create a new world in the Auto-Broadcast file.", false);
		Methods.sendMessage(player, " &f/AutoBroadcast List &6<Global|World|Actionbar|Title|Bossbar> &2[World] &e- Shows a list of all the broadcast messages in a world.", false);
		Methods.sendMessage(player, "", false);

		return true;
	}
}