package mycommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import commandManagement.BaseSubCommandHandler;
import commandManagement.SubCommand;
import commandManagement.SubCommandHandler;
import plugin.RecepiMenuHandler;

public class NewCommand extends SubCommand {

	public NewCommand(SubCommandHandler recepieSubHandler) {
		super("new", "/hrb recepies new", recepieSubHandler);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args,
			int depth) {
		RecepiMenuHandler.menu.open((Player) sender);

		return true;
	}

}
