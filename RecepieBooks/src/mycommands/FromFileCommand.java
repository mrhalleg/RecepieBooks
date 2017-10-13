package mycommands;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.BookMeta;

import commandManagement.SubCommand;
import commandManagement.SubCommandHandler;
import generators.PageGenerator;
import generators.RecipeLoader;

public class FromFileCommand extends SubCommand {

	public FromFileCommand(SubCommandHandler booksSubHandler) {
		super("fromfile", "/hrb books fromfile [filename]", booksSubHandler, "file");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args,
			int depth) {
		File f = new File(RecipeLoader.getFolderPath() + args[0]);
		if (!f.exists()) {
			sender.sendMessage("The file \"" + args[0] + "\" does not exist.");
			return true;
		}

		Recipe r = RecipeLoader.loadRecipe(f.getName());

		if (r == null) {
			sender.sendMessage("The file \"" + args[0] + "\" can not be loaded.");
			return true;
		}

		String color = "";
		boolean match = false;
		for (int i = 0; i < PageGenerator.colors.length; i++) {
			if (args[1].equals(PageGenerator.colors[i])) {
				color = args[1];
				match = true;
				break;
			}
		}
		if (!match) {
			sender.sendMessage("Unknown Color. Set color to black.");
			color = "0";
		}

		ItemStack book = new ItemStack(Material.BOOK_AND_QUILL);
		BookMeta meta = (BookMeta) book.getItemMeta();
		meta.setPages(PageGenerator.generateRecipies("§" + color, r));
		book.setItemMeta(meta);
		Player player = (Player) sender;
		player.getWorld().dropItem(player.getLocation(), book);

		return true;
	}

}
