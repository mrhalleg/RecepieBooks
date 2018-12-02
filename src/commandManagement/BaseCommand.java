package commandManagement;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class BaseSubCommandHandler implements CommandExecutor {
	List<SubCommandHandler> subHandler;
	List<SubCommand> subCommands;

	public BaseSubCommandHandler(JavaPlugin plugin, String name) {
		plugin.getCommand(name).setExecutor(this);
		this.subHandler = new ArrayList<>();
		this.subCommands = new ArrayList<>();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length < 1) {
			sender.sendMessage(ChatColor.RED + "Usage: " + command.getUsage());
			return true;
		}

		for (SubCommandHandler subHandler : this.subHandler) {
			for (String s : subHandler.aliases) {
				if (args[0].equalsIgnoreCase(s)) {
					if (subHandler.onCommand(sender, command, label, args, 1)) {
						return true;
					} else {
						sender.sendMessage(ChatColor.RED + "Usage: " + subHandler.getUsage());
					}
				}
			}
		}
		for (SubCommand subCommand : this.subCommands) {
			for (String s : subCommand.aliases) {
				if (args[0].equalsIgnoreCase(s)) {
					if (args[0].equalsIgnoreCase(s)) {
						if (subCommand.onCommand(sender, command, label, args, 1)) {
							return true;
						} else {
							sender.sendMessage(ChatColor.RED + "Usage: " + subCommand.getUsage());
							return true;
						}
					}
				}
			}
		}
		sender.sendMessage(ChatColor.RED + "Usage: " + command.getUsage());
		return true;
	}

	public boolean addSubCommandHandler(SubCommandHandler newHandler) {
		// for (SubCommandHandler subHandler : this.subHandler) {
		// for (String alias : subHandler.aliases) {
		// for (String newAlias : newHandler.aliases) {
		// if (alias.equalsIgnoreCase(newAlias)) {
		// return false;
		// }
		// }
		// }
		// }
		//
		// for (SubCommand subCommand : this.subCommands) {
		// for (String alias : subCommand.aliases) {
		// for (String newAlias : newHandler.aliases) {
		// if (alias.equalsIgnoreCase(newAlias)) {
		// return false;
		// }
		// }
		// }
		// }

		this.subHandler.add(newHandler);
		return true;
	}

	public boolean addSubCommand(SubCommand newCommand) {
		// for (SubCommandHandler subHandler : this.subHandler) {
		// for (String alias : subHandler.aliases) {
		// for (String newAlias : newCommand.aliases) {
		// if (alias.equalsIgnoreCase(newAlias)) {
		// return false;
		// }
		// }
		// }
		// }
		//
		// for (SubCommand subCommand : this.subCommands) {
		// for (String alias : subCommand.aliases) {
		// for (String newAlias : newCommand.aliases) {
		// if (alias.equalsIgnoreCase(newAlias)) {
		// return false;
		// }
		// }
		// }
		// }

		this.subCommands.add(newCommand);
		return true;
	}
}
