package testCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class Executor {

	private boolean hasToBePlayer, adminOnly;
	private String[] permissions;

	public Executor(boolean hasToBePlayer, boolean adminOnly, String... permissions) {
		this.hasToBePlayer = hasToBePlayer;
		this.adminOnly = adminOnly;
		this.permissions = permissions;
	}

	public abstract boolean execute(CommandSender sender, Command command, String[] args);

	public boolean isHasToBePlayer() {
		return this.hasToBePlayer;
	}

	public boolean isAdminOnly() {
		return this.adminOnly;
	}

	public String[] getPermissions() {
		return this.permissions;
	}
}
