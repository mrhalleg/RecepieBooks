package mycommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import commandManagement.BaseSubCommandHandler;
import commandManagement.SubCommand;

public class HelpCommand extends SubCommand {

	public HelpCommand(BaseSubCommandHandler hrbBaseHandler) {
		super("help", "/hrb help", hrbBaseHandler, "?");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args,
			int depth) {
		// TODO Auto-generated method stub
		return false;
	}

}
