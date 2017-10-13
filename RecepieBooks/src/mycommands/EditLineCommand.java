package mycommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import commandManagement.SubCommand;
import commandManagement.SubCommandHandler;

public class EditLineCommand extends SubCommand {

	public EditLineCommand(SubCommandHandler parent) {
		super("setline", "/hrb books edit [line] [text]", parent, "line");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args,
			int depth) {
		// TODO Auto-generated method stub
		return false;
	}

}
