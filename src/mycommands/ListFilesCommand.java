package mycommands;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import commandManagement.SubCommand;
import commandManagement.SubCommandHandler;
import generators.RecipeLoader;

public class ListFilesCommand extends SubCommand {

	public ListFilesCommand(SubCommandHandler recepieSubHandler) {
		super("list", "/hrb recepies list", recepieSubHandler);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args,
			int depth) {
		File[] files = new File(RecipeLoader.getFolderPath()).listFiles();
		sender.sendMessage("Files found:");
		if (files.length == 0) {
			sender.sendMessage("none");
			return true;
		}

		for (File f : files) {
			if (FilenameUtils.getExtension(f.getName()).equals("yml")) {
				sender.sendMessage(f.getName());
			}
		}

		return true;
	}

}
