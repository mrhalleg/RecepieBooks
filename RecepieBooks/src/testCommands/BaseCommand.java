package testCommands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public abstract class BaseCommand extends MyCommand implements CommandExecutor, TabCompleter {

	public BaseCommand(String name, boolean hasToBePlayer, boolean adminOnly, String usage,
			String description, ArgumentList arguments, String... aliases) {
		super(name, hasToBePlayer, adminOnly, usage, description, arguments, aliases);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return handle(sender, command, label, args);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias,
			String[] args) {
		return complete(sender, command, alias, args);
	}

}
