package testCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class MTest extends SubCommand {

	public MTest() {
		super("mtest", false, false, "lalala", "123", new ArgumentList(), "middle");
		addSubCommand(new SubTest());
	}

	@Override
	public boolean execute(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage("Mtest");
		return false;
	}

}
