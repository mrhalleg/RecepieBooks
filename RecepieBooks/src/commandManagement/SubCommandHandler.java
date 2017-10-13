package commandManagement;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SubCommandHandler {
	List<SubCommandHandler> subHandler;
	List<SubCommand> subCommands;
	String name;
	List<String> aliases;
	String usage;

	public SubCommandHandler(String name, String usage, BaseSubCommandHandler parent,
			String... aliases) {
		this.aliases = new ArrayList<>();
		this.usage = usage;
		this.name = name;
		this.aliases.add(name);
		this.subHandler = new ArrayList<>();
		this.subCommands = new ArrayList<>();
		for (int i = 0; i < aliases.length; i++) {
			this.aliases.add(aliases[i]);
		}
		parent.addSubCommandHandler(this);
	}

	public SubCommandHandler(String name, String usage, SubCommandHandler parent,
			String... aliases) {
		this.aliases = new ArrayList<>();
		this.usage = usage;
		this.name = name;
		this.aliases.add(name);
		this.subHandler = new ArrayList<>();
		this.subCommands = new ArrayList<>();
		for (int i = 0; i < aliases.length; i++) {
			this.aliases.add(aliases[i]);
		}
		parent.addSubCommandHandler(this);
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args,
			int depth) {
		if (args.length <= depth) {
			sender.sendMessage(ChatColor.RED + "Usage: " + this.usage);
			return true;
		}
		for (SubCommandHandler subHandler : this.subHandler) {
			for (String s : subHandler.aliases) {
				if (args[depth].equalsIgnoreCase(s)) {
					if (subHandler.onCommand(sender, command, label, args, depth + 1)) {
						return true;
					} else {
						sender.sendMessage(ChatColor.RED + "Usage: " + subHandler.getUsage());
						return true;
					}
				}
			}
		}
		for (SubCommand subCommand : this.subCommands) {
			for (String s : subCommand.aliases) {
				if (args[depth].equalsIgnoreCase(s)) {
					if (subCommand.onCommand(sender, command, label, args, depth + 1)) {
						return true;
					} else {
						sender.sendMessage(ChatColor.RED + "Usage: " + subCommand.getUsage());
						return true;
					}
				}
			}
		}
		sender.sendMessage(ChatColor.RED + "Usage: " + this.getUsage());
		return true;
	}

	public boolean addSubCommandHandler(SubCommandHandler newHandler) {
		this.subHandler.add(newHandler);
		return true;
	}

	public boolean addSubCommand(SubCommand newCommand) {
		this.subCommands.add(newCommand);
		return true;
	}

	public String getUsage() {
		return this.usage;
	}
}
