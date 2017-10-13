package commandManagement;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class BaseCommand implements CommandExecutor {
	private ArgumentList list;

	public BaseCommand(JavaPlugin plugin, String name) {
		plugin.getCommand(name).setExecutor(this);
	}
}
