package mycommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import commandManagement.SubCommand;
import commandManagement.SubCommandHandler;
import plugin.ColorMenuHandler;

public class InventoryCommand extends SubCommand {

	public InventoryCommand(SubCommandHandler parent) {
		super("inventory", "/hrb books inventory", parent, "inv");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args,
			int depth) {
		ColorMenuHandler.menu.open((Player) sender);
		return true;
	}

}
