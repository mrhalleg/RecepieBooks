package testCommands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

abstract class MyCommand {

	protected Map<String, SubCommand> subCommands;
	protected ArgumentList arguments;
	protected boolean hasToBePlayer, adminOnly;
	protected String permissions;
	protected String name;
	protected String usage;
	protected String description;
	protected String[] aliases;

	public MyCommand(String name, boolean hasToBePlayer, boolean adminOnly, String usage,
			String description, ArgumentList arguments, String... aliases) {
		this.subCommands = new HashMap<>();
		this.name = name;
		this.hasToBePlayer = hasToBePlayer;
		this.adminOnly = adminOnly;
		this.usage = usage;
		this.description = description;
		this.aliases = aliases;
		this.permissions = "";
	}

	protected boolean handle(CommandSender sender, Command command, String label, String[] args) {
		if (args.length > 0 && this.subCommands.containsKey(args[0])) {
			return this.subCommands.get(args[0]).handle(sender, command, args[0], Arrays
					.copyOfRange(args, 1, args.length));
		}

		if (check(sender, args)) {
			return execute(sender, command, label, args);
		}

		return false;
	}

	protected boolean check(CommandSender sender, String[] args) {
		if (!((this.hasToBePlayer && sender instanceof Player) || !this.hasToBePlayer)) {
			return false;
		} else if (!((this.adminOnly && sender.isOp()) || !this.adminOnly)) {
			return false;
		} else if (this.arguments != null && this.arguments.match(args)) {
			return false;
		} else {
			return sender.hasPermission(this.permissions);
		}
	}

	protected List<String> complete(CommandSender sender, Command command, String label,
			String[] args) {
		List<String> list = new LinkedList<>();
		sender.sendMessage("label: " + label);
		for (String s : args) {
			sender.sendMessage("args: " + s);
		}
		return list;
	}

	protected List<String> list(CommandSender sender, Command command, String label,
			String[] args) {
		List<String> list = new LinkedList<>();
		sender.sendMessage("label: " + label);
		for (String s : args) {
			sender.sendMessage("args: " + s);
		}
		if (args.length == 0) {
			list.addAll(Arrays.asList(this.aliases));
		} else {
			list.addAll(this.subCommands.keySet());
		}
		return list;
	}

	protected void addSubCommand(SubCommand command) {
		this.subCommands.put(command.name, command);
		for (String s : command.aliases) {
			this.subCommands.put(s, command);
		}
	}

	public boolean isHasToBePlayer() {
		return this.hasToBePlayer;
	}

	public boolean isAdminOnly() {
		return this.adminOnly;
	}

	public String getPermissions() {
		return this.permissions;
	}

	public boolean execute(CommandSender sender, Command command, String label, String[] args) {
		return false;
	}

}
