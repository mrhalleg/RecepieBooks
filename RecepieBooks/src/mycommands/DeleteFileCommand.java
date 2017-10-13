package mycommands;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import commandManagement.SubCommand;
import commandManagement.SubCommandHandler;
import generators.RecipeLoader;

public class DeleteFileCommand extends SubCommand {

	public DeleteFileCommand(SubCommandHandler parent) {
		super("delete", "/hrb recepies delete [filename]", parent, "remove", "del");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args,
			int depth) {
		File f = new File(RecipeLoader.getFolderPath() + args[depth]);
		if (!f.exists()) {
			sender.sendMessage("The file \"" + args[depth] + "\" does not exist.");
			return true;
		}

		f.delete();
		sender.sendMessage("File deleted.");
		return true;
	}

}
