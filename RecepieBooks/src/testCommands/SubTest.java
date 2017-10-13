package testCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SubTest extends SubCommand {

	public SubTest() {
		super("sub", false, false, "desc", "use", new ArgumentList(), "subcom");
	}

	@Override
	public boolean execute(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage("sub executed");
		return false;
	}

}
