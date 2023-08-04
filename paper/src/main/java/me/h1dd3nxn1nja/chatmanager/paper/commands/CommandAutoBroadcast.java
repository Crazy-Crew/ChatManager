package me.h1dd3nxn1nja.chatmanager.paper.commands;

import java.util.ArrayList;
import java.util.List;
import com.ryderbelserion.chatmanager.paper.files.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.managers.PlaceholderManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.h1dd3nxn1nja.chatmanager.paper.utils.World;
import org.jetbrains.annotations.NotNull;

public class CommandAutoBroadcast implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final PlaceholderManager placeholderManager = plugin.getCrazyManager().getPlaceholderManager();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (!(sender instanceof Player player)) {
			this.plugin.getMethods().sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		FileConfiguration messages = Files.MESSAGES.getFile();
		FileConfiguration autobroadcast = Files.AUTO_BROADCAST.getFile();

		if (cmd.getName().equalsIgnoreCase("autobroadcast")) {
			if (player.hasPermission("chatmanager.autobroadcast")) {
				if (args.length == 0) return checkProtocol(player);
			} else {
				player.sendMessage(this.plugin.getMethods().noPermission());
				return true;
			}

			if (args[0].equalsIgnoreCase("help")) {
				if (player.hasPermission("chatmanager.autobroadcast.help")) {
					if (args.length == 1) return checkProtocol(player);
				} else {
					player.sendMessage(this.plugin.getMethods().noPermission());
					return true;
				}
			}

			if (args[0].equalsIgnoreCase("list")) {
				if (player.hasPermission("chatmanager.autobroadcast.list")) {
					if (args.length == 1) {
						this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/AutoBroadcast list [Global|World|Actionbar|Title|Bossbar] [World]", true);
						return true;
					}
				} else {
					this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
					return true;
				}

				if (args[1].equalsIgnoreCase("global")) {
					if (player.hasPermission("chatmanager.autobroadcast.list")) {
						if (args.length == 2) {
							this.plugin.getMethods().sendMessage(player, "", true);
							this.plugin.getMethods().sendMessage(player, messages.getString("Auto_Broadcast.List").replace("{section}", "Global"), true);
							this.plugin.getMethods().sendMessage(player, "", true);

							String string = autobroadcast.getString("Auto_Broadcast.Global_Messages.Prefix");

							for (String global : autobroadcast.getStringList("Auto_Broadcast.Global_Messages.Messages")) {
								if (string != null) player.sendMessage(placeholderManager.setPlaceholders(player, "&7 - " + global.replace("{Prefix}", string)));
							}

							this.plugin.getMethods().sendMessage(player, "", true);
						} else {
							this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/AutoBroadcast list global", true);
						}
					} else {
						this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
					}
				}

				if (args[1].equalsIgnoreCase("actionbar")) {
					if (player.hasPermission("chatmanager.autobroadcast.list")) {
						if (args.length == 2) {
							this.plugin.getMethods().sendMessage(player, "", true);
							this.plugin.getMethods().sendMessage(player, messages.getString("Auto_Broadcast.List").replace("{section}", "Actionbar"), true);

							String string = autobroadcast.getString("Auto_Broadcast.Actionbar_Messages.Prefix");

							for (String actionbar : autobroadcast.getStringList("Auto_Broadcast.Actionbar_Messages.Messages")) {
								if (string != null) player.sendMessage(placeholderManager.setPlaceholders(player, "&7 - " + actionbar.replace("{Prefix}", string)));
							}

							this.plugin.getMethods().sendMessage(player, "", true);
						} else {
							this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/AutoBroadcast list actionbar", true);
						}
					} else {
						this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
					}
				}

				if (args[1].equalsIgnoreCase("title")) {
					if (player.hasPermission("chatmanager.autobroadcast.list")) {
						if (args.length == 2) {
							this.plugin.getMethods().sendMessage(player, "", true);
							this.plugin.getMethods().sendMessage(player, messages.getString("Auto_Broadcast.List").replace("{section}", "Title"), true);

							for (String title : autobroadcast.getStringList("Auto_Broadcast.Title_Messages.Messages")) {
								player.sendMessage(placeholderManager.setPlaceholders(player, "&7 - " + title));
							}

							this.plugin.getMethods().sendMessage(player, "", true);
						} else {
							this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/AutoBroadcast list title", true);
						}
					} else {
						this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
					}
				}

				if (args[1].equalsIgnoreCase("bossbar")) {
					if (player.hasPermission("chatmanager.autobroadcast.list")) {
						if (args.length == 2) {
							this.plugin.getMethods().sendMessage(player, "", true);
							this.plugin.getMethods().sendMessage(player, messages.getString("Auto_Broadcast.List").replace("{section}", "Bossbar"), true);

							for (String bossbar : autobroadcast.getStringList("Auto_Broadcast.Bossbar_Messages.Messages")) {
								player.sendMessage(placeholderManager.setPlaceholders(player, "&7 - " + bossbar));
							}

							this.plugin.getMethods().sendMessage(player, "", true);
						} else {
							this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/AutoBroadcast list bossbar", true);
						}
					} else {
						this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
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
									this.plugin.getMethods().sendMessage(player, "", true);
									this.plugin.getMethods().sendMessage(player, messages.getString("Auto_Broadcast.List").replace("{section}", key), true);

									for (String bcmsg : autobroadcast.getStringList("Auto_Broadcast.Per_World_Messages.Messages." + key)) {
										if (broadcast != null) player.sendMessage(this.plugin.getMethods().color(player.getUniqueId(), "&7 - " + bcmsg.replace("{Prefix}", broadcast)));
									}

									this.plugin.getMethods().sendMessage(player, "", true);
								}
							}
						} else {
							this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/AutoBroadcast list world <world>", true);
						}
					} else {
						this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
					}
				}
			}

			if (args[0].equalsIgnoreCase("add")) {
				if (player.hasPermission("chatmanager.autobroadcast.add")) {
					if (args.length == 1) {
						this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/AutoBroadcast add [Global|World|Actionbar|Title|Bossbar] <world> <message>", true);
						return true;
					}
				} else {
					this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
					return true;
				}

				if (args[1].equalsIgnoreCase("global")) {
					if (player.hasPermission("chatmanager.autobroadcast.add")) {
						if (args.length != 2) {
							StringBuilder builder = new StringBuilder();

							for (int i = 2; i < args.length; i++) {
								builder.append(args[i]).append(" ");
							}

							String msg = builder.toString();
							List<String> msgs = autobroadcast.getStringList("Auto_Broadcast.Global_Messages.Messages");
							msgs.add(msg);
							autobroadcast.set("Auto_Broadcast.Global_Messages.Messages", msgs);
							Files.AUTO_BROADCAST.saveFile();
							Files.AUTO_BROADCAST.reloadFile();
							this.plugin.getMethods().sendMessage(player, messages.getString("Auto_Broadcast.Added").replace("{message}", msg).replace("{section}", "Global"), true);
							return true;
						} else {
							this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().color("&cCommand Usage: &7/AutoBroadcast add global <message>"), true);
						}
					} else
						player.sendMessage(this.plugin.getMethods().noPermission());
					this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
				}
			}

			if (args[1].equalsIgnoreCase("actionbar")) {
				if (player.hasPermission("chatmanager.autobroadcast.add")) {
					if (args.length != 2) {
						StringBuilder builder = new StringBuilder();

						for (int i = 2; i < args.length; i++) {
							builder.append(args[i]).append(" ");
						}

						String msg = builder.toString();
						List<String> msgs = autobroadcast.getStringList("Auto_Broadcast.Actionbar_Messages.Messages");
						msgs.add(msg);
						autobroadcast.set("Auto_Broadcast.Actionbar_Messages.Messages", msgs);
						Files.AUTO_BROADCAST.saveFile();
						Files.AUTO_BROADCAST.reloadFile();
						this.plugin.getMethods().sendMessage(player, messages.getString("Auto_Broadcast.Added").replace("{message}", msg).replace("{section}", "Actionbar"), true);
						return true;
					} else {
						this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/AutoBroadcast add actionbar <message.", true);
					}
				} else {
					this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
				}
			}

			if (args[1].equalsIgnoreCase("title")) {
				if (player.hasPermission("chatmanager.autobroadcast.add")) {
					if (args.length != 2) {
						StringBuilder builder = new StringBuilder();

						for (int i = 2; i < args.length; i++) {
							builder.append(args[i]).append(" ");
						}

						String msg = builder.toString();
						List<String> msgs = autobroadcast.getStringList("Auto_Broadcast.Title_Messages.Messages");
						msgs.add(msg);
						autobroadcast.set("Auto_Broadcast.Title_Messages.Messages", msgs);
						Files.AUTO_BROADCAST.saveFile();
						Files.AUTO_BROADCAST.reloadFile();
						this.plugin.getMethods().sendMessage(player, messages.getString("Auto_Broadcast.Added").replace("{message}", msg).replace("{section}", "Title"), true);
						return true;
					} else {
						this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/AutoBroadcast add title <message>", true);
					}
				} else {
					this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
				}
			}

			if (args[1].equalsIgnoreCase("bossbar")) {
				if (player.hasPermission("chatmanager.autobroadcast.add")) {
					if (args.length != 2) {
						StringBuilder builder = new StringBuilder();

						for (int i = 2; i < args.length; i++) {
							builder.append(args[i]).append(" ");
						}

						String msg = builder.toString();
						List<String> msgs = autobroadcast.getStringList("Auto_Broadcast.Bossbar_Messages.Messages");
						msgs.add(msg);
						autobroadcast.set("Auto_Broadcast.Bossbar_Messages.Messages", msgs);
						Files.AUTO_BROADCAST.saveFile();
						Files.AUTO_BROADCAST.reloadFile();
						this.plugin.getMethods().sendMessage(player, messages.getString("Auto_Broadcast.Added").replace("{message}", msg).replace("{section}", "Bossbar"), true);
						return true;
					} else {
						this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/AutoBroadcast add bossbar <message>", true);
					}
				} else {
					this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
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
									this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
									return true;
								}

								StringBuilder builder = new StringBuilder();

								for (int i = 3; i < args.length; i++) {
									builder.append(args[i]).append(" ");
								}

								String msg = builder.toString();
								List<String> msgs = autobroadcast.getStringList("Auto_Broadcast.Per_World_Messages.Messages." + key);
								msgs.add(msg);
								autobroadcast.set("Auto_Broadcast.Per_World_Messages.Messages." + key, msgs);
								Files.AUTO_BROADCAST.saveFile();
								Files.AUTO_BROADCAST.reloadFile();
								this.plugin.getMethods().sendMessage(player, messages.getString("Auto_Broadcast.Added").replace("{message}", msg).replace("{section}", key), true);
								return true;
							}
						}
					} else {
						this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/Autobroadcast add world <world> <message>", true);
					}
				} else {
					this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
				}
			}
		}

		if (args[0].equalsIgnoreCase("create")) {
			if (player.hasPermission("chatmanager.autobroadcast.create")) {
				if (args.length >= 2) {
					StringBuilder builder = new StringBuilder();

					for (int i = 2; i < args.length; i++) {
						builder.append(args[i]).append(" ");
					}

					String msg = builder.toString();
					List<String> msgs = autobroadcast.getStringList("Auto_Broadcast.Per_World_Messages.Messages");
					msgs.add(msg);
					autobroadcast.set("Auto_Broadcast.Per_World_Messages.Messages." + args[1], msgs);
					Files.AUTO_BROADCAST.saveFile();
					Files.AUTO_BROADCAST.reloadFile();
					this.plugin.getMethods().sendMessage(player, messages.getString("Auto_Broadcast.Created").replace("{world}", args[1]).replace("{message}", msg), true);
					return true;
				} else {
					this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/Autobroadcast create <world> <message>", true);
				}
			} else {
				this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
			}
		}

		return true;
	}

	private boolean checkProtocol(Player player) {
		this.plugin.getMethods().sendMessage(player, "", false);
		this.plugin.getMethods().sendMessage(player, " &3Auto-Broadcast Help Menu &f(v" + plugin.getDescription().getVersion() + ")", false);
		this.plugin.getMethods().sendMessage(player,"", false);
		this.plugin.getMethods().sendMessage(player, " &6<> &f= Required Arguments", false);
		this.plugin.getMethods().sendMessage(player, " &2[] &f= Optional Arguments", false);
		this.plugin.getMethods().sendMessage(player, " ", false);
		this.plugin.getMethods().sendMessage(player, " &f/AutoBroadcast Help &e- Shows a list of commands for Auto-Broadcast.", false);
		this.plugin.getMethods().sendMessage(player, "  &f/AutoBroadcast Add &6<Global|World|Actionbar|Title|Bossbar> &2[World] &6<message> &e- Add a message to the Auto-Broadcast to a specific world.", false);
		this.plugin.getMethods().sendMessage(player, " &f/AutoBroadcast Create &6<world> <message> &e- Create a new world in the Auto-Broadcast file.", false);
		this.plugin.getMethods().sendMessage(player, " &f/AutoBroadcast List &6<Global|World|Actionbar|Title|Bossbar> &2[World] &e- Shows a list of all the broadcast messages in a world.", false);
		this.plugin.getMethods().sendMessage(player, "", false);

		return true;
	}
}