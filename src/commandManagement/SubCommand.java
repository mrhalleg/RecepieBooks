package commandManagement;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {
	String name;
	String usage;
	List<String> aliases;

	public SubCommand(String name, String usage, SubCommandHandler parent, String... aliases) {
		this.usage = usage;
		this.aliases = new ArrayList<>();
		this.name = name;
		this.aliases.add(name);
		for (int i = 0; i < aliases.length; i++) {
			this.aliases.add(aliases[i]);
		}
		parent.addSubCommand(this);
	}

	public SubCommand(String name, String usage, BaseSubCommandHandler parent, String... aliases) {
		this.usage = usage;
		this.aliases = new ArrayList<>();
		this.name = name;
		this.aliases.add(name);
		for (int i = 0; i < aliases.length; i++) {
			this.aliases.add(aliases[i]);
		}
		parent.addSubCommand(this);
	}

	public String getUsage() {
		return this.usage;
	}

	public abstract boolean onCommand(CommandSender sender, Command command, String label,
			String[] args, int depth);

}
