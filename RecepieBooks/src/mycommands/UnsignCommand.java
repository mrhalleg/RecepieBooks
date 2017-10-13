package mycommands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import commandManagement.SubCommand;
import commandManagement.SubCommandHandler;

public class UnsignCommand extends SubCommand {

	public UnsignCommand(SubCommandHandler booksSubHandler) {
		super("unsign", "hrb books unsign", booksSubHandler);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args,
			int depth) {
		Player player = (Player) sender;
		ItemStack book = player.getItemInHand();
		if (book == null) {
			sender.sendMessage("You are not holding a book!");
			return false;
		}

		if (!(book.getItemMeta() instanceof BookMeta)) {
			sender.sendMessage("You are not holding a book!");
			return false;
		}
		book.setType(Material.BOOK_AND_QUILL);
		return true;
	}

}
