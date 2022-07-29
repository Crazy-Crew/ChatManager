package me.h1dd3nxn1nja.chatmanager.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.h1dd3nxn1nja.chatmanager.Main;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import me.h1dd3nxn1nja.chatmanager.utils.JSONMessage;
import me.h1dd3nxn1nja.chatmanager.utils.Version;
import me.h1dd3nxn1nja.chatmanager.utils.World;

public class CommandAutoBroadcast implements CommandExecutor {
	
	public Main plugin;
	
	public CommandAutoBroadcast(Main plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		FileConfiguration autobroadcast = Main.settings.getAutoBroadcast();
		FileConfiguration messages = Main.settings.getMessages();
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("Error: You can only use that command in game");
			return true;
		}
		
		Player player = (Player) sender;
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("autobroadcast")) {
				if (player.hasPermission("chatmanager.autobroadcast")) {
					if (args.length == 0) {
						if (Version.getCurrentVersion().isNewer(Version.v1_8_R2) && Version.getCurrentVersion().isOlder(Version.v1_17_R1)) {
							JSONMessage.create("").send(player);
							JSONMessage.create(" &3Auto-Broadcast Help Menu &f(v" + plugin.getDescription().getVersion() + ")").send(player);
							JSONMessage.create("").send(player);
							JSONMessage.create(" &6<> &f= Required Arguments").send(player);
							JSONMessage.create(" &2[] &f= Optional Arguments").send(player);
							JSONMessage.create("").send(player);
							JSONMessage.create(" &f/AutoBroadcast Help &e- Shows a list of commands for Auto-Broadcast.").tooltip(Methods.color("&f/AutoBroadcast Help \n&7Shows a list of commands for Auto-Broadcast.")).suggestCommand("/autobroadcast help").send(player);
							JSONMessage.create(" &f/AutoBroadcast Add &6<Global|World|Actionbar|Title|Bossbar> &2[World] &6<message> &e- Add a message to the Auto-Broadcast to a specific world.").tooltip(Methods.color("&f/AutoBroadcast Add &6<global|world|actionbar|title|bossbar> &2[world] &6<message> \n&7Add a message to the Auto-Broadcast to a specific world.")).suggestCommand("/autobroadcast add <global|world|actionbar|title|bossbar> [world] <message>").send(player);
							JSONMessage.create(" &f/AutoBroadcast Create &6<world> <message> &e- Create a new world in the \n &eAuto-Broadcast file.").tooltip(Methods.color("&f/AutoBroadcast Create &6<world> <message> \n&7Create a new world in the Auto-Broadcast file.")).suggestCommand("/autobroadcast create <world> <message>").send(player);
							JSONMessage.create(" &f/AutoBroadcast List &6<Global|World|Actionbar|Title|Bossbar> &2[World] &e- Shows a \n&elist of all the broadcast messages in a world.").tooltip(Methods.color("&f/AutoBroadcast List &6<global|world|actionbar|title|bossbar> &2[world] \n&7Shows a list of all the broadcast messages in a world.")).suggestCommand("/autobroadcast list <global|world|actionbar|title|bossbar> [World]").send(player);
							JSONMessage.create("").send(player);
							JSONMessage.create(" &e&lTIP &7Try to hover or click the command!").tooltip(Methods.color("&7Hover over the commands to get information about them. \n&7Click on the commands to insert them in the chat.")).send(player);
							JSONMessage.create("").send(player);
							return true;
						} else {
							player.sendMessage("");
							player.sendMessage(Methods.color(" &3Auto-Broadcast Help Menu &f(v" + plugin.getDescription().getVersion() + ")"));
							player.sendMessage("");
							player.sendMessage(Methods.color(" &6<> &f= Required Arguments"));
							player.sendMessage(Methods.color(" &2[] &f= Optional Arguments"));
							player.sendMessage("");
							player.sendMessage(Methods.color(" &f/AutoBroadcast Help &e- Shows a list of commands for Auto-Broadcast."));
							player.sendMessage(Methods.color(" &f/AutoBroadcast Add &6<Global|World|Actionbar|Title|Bossbar> &2[World] &6<message> &e- Add a message to the Auto-Broadcast to a specific world."));
							player.sendMessage(Methods.color(" &f/AutoBroadcast Create &6<world> <message> &e- Create a new world in the Auto-Broadcast file."));
							player.sendMessage(Methods.color(" &f/AutoBroadcast List &6<Global|World|Actionbar|Title|Bossbar> &2[World] &e- Shows a list of all the broadcast messages in a world."));
							player.sendMessage("");
							return true;
						}
					}
				} else {
					player.sendMessage(Methods.noPermission());
					return true;
				}
				
				if (args[0].equalsIgnoreCase("help")) {
					if (player.hasPermission("chatmanager.autobroadcast.help")) {
						if (args.length == 1) {
							if (Version.getCurrentVersion().isNewer(Version.v1_8_R2) && Version.getCurrentVersion().isOlder(Version.v1_17_R1)) {
								JSONMessage.create("").send(player);
								JSONMessage.create(" &3Auto-Broadcast Help Menu &f(v" + plugin.getDescription().getVersion() + ")").send(player);
								JSONMessage.create("").send(player);
								JSONMessage.create(" &6<> &f= Required Arguments").send(player);
								JSONMessage.create(" &2[] &f= Optional Arguments").send(player);
								JSONMessage.create("").send(player);
								JSONMessage.create(" &f/AutoBroadcast Help &e- Shows a list of commands for Auto-Broadcast.").tooltip(Methods.color("&f/AutoBroadcast Help \n&7Shows a list of commands for Auto-Broadcast.")).suggestCommand("/autobroadcast help").send(player);
								JSONMessage.create(" &f/AutoBroadcast Add &6<Global|World|Actionbar|Title|Bossbar> &2[World] &6<message> &e- Add a message to the Auto-Broadcast to a specific world.").tooltip(Methods.color("&f/AutoBroadcast Add &6<global|world|actionbar|title|bossbar> &2[world] &6<message> \n&7Add a message to the Auto-Broadcast to a specific world.")).suggestCommand("/autobroadcast add <global|world|actionbar|title|bossbar> [world] <message>").send(player);
								JSONMessage.create(" &f/AutoBroadcast Create &6<world> <message> &e- Create a new world in the \n &eAuto-Broadcast file.").tooltip(Methods.color("&f/AutoBroadcast Create &6<world> <message> \n&7Create a new world in the Auto-Broadcast file.")).suggestCommand("/autobroadcast create <world> <message>").send(player);
								JSONMessage.create(" &f/AutoBroadcast List &6<Global|World|Actionbar|Title|Bossbar> &2[World] &e- Shows a \n&elist of all the broadcast messages in a world.").tooltip(Methods.color("&f/AutoBroadcast List &6<global|world|actionbar|title|bossbar> &2[world] \n&7Shows a list of all the broadcast messages in a world.")).suggestCommand("/autobroadcast list <global|world|actionbar|title|bossbar> [World]").send(player);
								JSONMessage.create("").send(player);
								JSONMessage.create(" &e&lTIP &7Try to hover or click the command!").tooltip(Methods.color("&7Hover over the commands to get information about them. \n&7Click on the commands to insert them in the chat.")).send(player);
								JSONMessage.create("").send(player);
								return true;
							} else {
								player.sendMessage("");
								player.sendMessage(Methods.color(" &3Auto-Broadcast Help Menu &f(v" + plugin.getDescription().getVersion() + ")"));
								player.sendMessage("");
								player.sendMessage(Methods.color(" &6<> &f= Required Arguments"));
								player.sendMessage(Methods.color(" &2[] &f= Optional Arguments"));
								player.sendMessage("");
								player.sendMessage(Methods.color(" &f/AutoBroadcast Help &e- Shows a list of commands for Auto-Broadcast."));
								player.sendMessage(Methods.color(" &f/AutoBroadcast Add &6<Global|World|Actionbar|Title|Bossbar> &2[World] &6<message> &e- Add a message to the Auto-Broadcast to a specific world."));
								player.sendMessage(Methods.color(" &f/AutoBroadcast Create &6<world> <message> &e- Create a new world in the Auto-Broadcast file."));
								player.sendMessage(Methods.color(" &f/AutoBroadcast List &6<Global|World|Actionbar|Title|Bossbar> &2[World] &e- Shows a list of all the broadcast messages in a world."));
								player.sendMessage("");
								return true;
							}
						}
					} else {
						player.sendMessage(Methods.noPermission());
						return true;
					}
				}
				if (args[0].equalsIgnoreCase("list")) {
					if (player.hasPermission("chatmanager.autobroadcast.list")) {
						if (args.length == 1) {
							player.sendMessage(Methods.color("&cCommand Usage: &7/AutoBroadcast list [Global|World|Actionbar|Title|Bossbar] [World]"));
							return true;
						}
					} else {
						player.sendMessage(Methods.noPermission());
						return true;
					}
					if (args[1].equalsIgnoreCase("global")) {
						if (player.hasPermission("chatmanager.autobroadcast.list")) {
							if (args.length == 2) {
								player.sendMessage("");
								player.sendMessage(Methods.color(player, messages.getString("Auto_Broadcast.List").replace("{section}", "Global").replace("{Prefix}", messages.getString("Message.Prefix"))));
								for (String global : autobroadcast.getStringList("Auto_Broadcast.Global_Messages.Messages")) {
									player.sendMessage(PlaceholderManager.setPlaceholders(player, "&7 - " + global.toString().replace("{Prefix}", autobroadcast.getString("Auto_Broadcast.Global_Messages.Prefix"))));
								}
								player.sendMessage("");
							} else {
								player.sendMessage(Methods.color("&cCommand Usage: &7/AutoBroadcast list global"));
							}
						} else {
							player.sendMessage(Methods.noPermission());
						}
					}
					if (args[1].equalsIgnoreCase("actionbar")) {
						if (player.hasPermission("chatmanager.autobroadcast.list")) {
							if (args.length == 2) {
								player.sendMessage("");
								player.sendMessage(Methods.color(player, messages.getString("Auto_Broadcast.List").replace("{section}", "Actionbar").replace("{Prefix}", messages.getString("Message.Prefix"))));
								for (String actionbar : autobroadcast.getStringList("Auto_Broadcast.Actionbar_Messages.Messages")) {
									player.sendMessage(PlaceholderManager.setPlaceholders(player, "&7 - " + actionbar.toString().replace("{Prefix}", autobroadcast.getString("Auto_Broadcast.Actionbar_Messages.Prefix"))));
								}
								player.sendMessage("");
							} else {
								player.sendMessage(Methods.color("&cCommand Usage: &7/AutoBroadcast list actionbar"));
							}
						} else {
							player.sendMessage(Methods.noPermission());
						}
					}
					if (args[1].equalsIgnoreCase("title")) {
						if (player.hasPermission("chatmanager.autobroadcast.list")) {
							if (args.length == 2) {
								player.sendMessage("");
								player.sendMessage(Methods.color(player, messages.getString("Auto_Broadcast.List").replace("{section}", "Title").replace("{Prefix}", messages.getString("Message.Prefix"))));
								for (String title : autobroadcast.getStringList("Auto_Broadcast.Title_Messages.Messages")) {
									player.sendMessage(PlaceholderManager.setPlaceholders(player, "&7 - " + title.toString()));
								}
								player.sendMessage("");
							} else {
								player.sendMessage(Methods.color("&cCommand Usage: &7/AutoBroadcast list title"));
							}
						} else {
							player.sendMessage(Methods.noPermission());
						}
					}
					if (args[1].equalsIgnoreCase("bossbar")) {
						if (player.hasPermission("chatmanager.autobroadcast.list")) {
							if (args.length == 2) {
								player.sendMessage("");
								player.sendMessage(Methods.color(player, messages.getString("Auto_Broadcast.List").replace("{section}", "Bossbar").replace("{Prefix}", messages.getString("Message.Prefix"))));
								for (String bossbar : autobroadcast.getStringList("Auto_Broadcast.Bossbar_Messages.Messages")) {
									player.sendMessage(PlaceholderManager.setPlaceholders(player, "&7 - " + bossbar.toString()));
								}
								player.sendMessage("");
							} else {
								player.sendMessage(Methods.color("&cCommand Usage: &7/AutoBroadcast list bossbar"));
							}
						} else {
							player.sendMessage(Methods.noPermission());
						}
					}
					if (args[1].equalsIgnoreCase("world")) {
						if (player.hasPermission("chatmanager.autobroadcast.list")) {
							if (args.length == 3) {
								List<World> worlds = new ArrayList<>();
								for (String key : autobroadcast.getConfigurationSection("Auto_Broadcast.Per_World_Messages.Messages").getKeys(false)) {
									World w = new World(key, autobroadcast.getStringList("Auto_Broadcast.Per_World_Messages.Messages." + key), 0);
									worlds.add(w);
									if (args[2].equals(key)) {
											player.sendMessage("");
											player.sendMessage(Methods.color(player, messages.getString("Auto_Broadcast.List").replace("{section}", key).replace("{Prefix}", messages.getString("Message.Prefix"))));
											for (String bcmsg : autobroadcast.getStringList("Auto_Broadcast.Per_World_Messages.Messages." + key)) {
												player.sendMessage(Methods.color(player, "&7 - " + bcmsg.toString().replace("{Prefix}", autobroadcast.getString("Auto_Broadcast.Per_World_Messages.Prefix"))));
											}
										player.sendMessage("");
									}
								}
							} else {
								player.sendMessage(Methods.color("&cCommand Usage: &7/AutoBroadcast list world <world>"));
							}
						} else {
							player.sendMessage(Methods.noPermission());
						}
					}
				}
				if (args[0].equalsIgnoreCase("add")) {
					if (player.hasPermission("chatmanager.autobroadcast.add")) {
						if (args.length == 1) {
							player.sendMessage(Methods.color("&cCommand Usage: &7/AutoBroadcast add [Global|World|Actionbar|Title|Bossbar] <world> <message>"));
							return true;
						}
					} else {
						player.sendMessage(Methods.noPermission());
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
								Main.settings.saveAutoBroadcast();
								Main.settings.reloadAutoBroadcast();
								player.sendMessage(Methods.color(player, messages.getString("Auto_Broadcast.Added")
										.replace("{Prefix}", messages.getString("Message.Prefix"))
										.replace("{message}", msg)
										.replace("{section}", "Global")));
								return true;
							} else {
								player.sendMessage(Methods.color("&cCommand Usage: &7/AutoBroadcast add global <message>"));
							}
						} else {
							player.sendMessage(Methods.noPermission());
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
								Main.settings.saveAutoBroadcast();
								Main.settings.reloadAutoBroadcast();
								player.sendMessage(Methods.color(player, messages.getString("Auto_Broadcast.Added")
										.replace("{Prefix}", messages.getString("Message.Prefix"))
										.replace("{message}", msg)
										.replace("{section}", "Actionbar")));
								return true;
							} else {
								player.sendMessage(Methods.color("&cCommand Usage: &7/AutoBroadcast add actionbar <message>"));
							}
						} else {
							player.sendMessage(Methods.noPermission());
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
								Main.settings.saveAutoBroadcast();
								Main.settings.reloadAutoBroadcast();
								player.sendMessage(Methods.color(player, messages.getString("Auto_Broadcast.Added")
										.replace("{Prefix}", messages.getString("Message.Prefix"))
										.replace("{message}", msg)
										.replace("{section}", "Title")));
								return true;
							} else {
								player.sendMessage(Methods.color("&cCommand Usage: &7/AutoBroadcast add title <message>"));
							}
						} else {
							player.sendMessage(Methods.noPermission());
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
								Main.settings.saveAutoBroadcast();
								Main.settings.reloadAutoBroadcast();
								player.sendMessage(Methods.color(player, messages.getString("Auto_Broadcast.Added")
										.replace("{Prefix}", messages.getString("Message.Prefix"))
										.replace("{message}", msg)
										.replace("{section}", "Bossbar")));
								return true;
							} else {
								player.sendMessage(Methods.color("&cCommand Usage: &7/AutoBroadcast add bossbar <message>"));
							}
						} else {
							player.sendMessage(Methods.noPermission());
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
											player.sendMessage(Methods.noPermission());
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
										Main.settings.saveAutoBroadcast();
										Main.settings.reloadAutoBroadcast();
										player.sendMessage(Methods.color(player, messages.getString("Auto_Broadcast.Added")
												.replace("{Prefix}", messages.getString("Message.Prefix"))
												.replace("{message}", msg)
												.replace("{section}", key)));
										return true;
									}
								}
							} else {
								player.sendMessage(Methods.color("&cCommand Usage: &7/Autobroadcast add world <world> <message>"));
							}
						} else {
							player.sendMessage(Methods.noPermission());
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
							Main.settings.saveAutoBroadcast();
							Main.settings.reloadAutoBroadcast();
							player.sendMessage(Methods.color(player, messages.getString("Auto_Broadcast.Created")
									.replace("{Prefix}", messages.getString("Message.Prefix"))
									.replace("{world}", args[1])
									.replace("{message}", msg)));
							return true;
						} else {
							player.sendMessage(Methods.color("&cCommand Usage: &7/Autobroadcast create <world> <message>"));
						}
					} else {
						player.sendMessage(Methods.noPermission());
					}
				}
			}
		}
		return true;
	}
}