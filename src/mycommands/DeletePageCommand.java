package mycommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import commandManagement.SubCommand;
import commandManagement.SubCommandHandler;

public class DeletePageCommand extends SubCommand {

	public DeletePageCommand(SubCommandHandler parent) {
		super("deletepage", "/hrb books deletepage deletepage [page] ", parent, "removepage");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args,
			int depth) {
		// TODO Auto-generated method stub
		return false;
	}

}
