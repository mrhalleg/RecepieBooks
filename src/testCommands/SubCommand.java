package testCommands;

public abstract class SubCommand extends MyCommand {

	public SubCommand(String name, boolean hasToBePlayer, boolean adminOnly, String usage,
			String description, ArgumentList arguments, String... aliases) {
		super(name, hasToBePlayer, adminOnly, usage, description, arguments, aliases);
		// TODO Auto-generated constructor stub
	}
}
