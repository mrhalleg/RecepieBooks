package mycommands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import commandManagement.SubCommand;
import commandManagement.SubCommandHandler;

public class CopyCommand extends SubCommand {

	public CopyCommand(SubCommandHandler booksSubHandler) {
		super("copy", "/hrb books copy", booksSubHandler);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args,
			int depth) {
		Player player = (Player) sender;
		ItemStack from = player.getInventory().getItem(0);
		ItemStack to = player.getInventory().getItem(1);
		if (from == null || to == null) {
			sender.sendMessage("The items in the firs and second hotbarslot have to be books. ");
			return false;
		}

		if (!(from.getItemMeta() instanceof BookMeta)) {
			sender.sendMessage("The items in the firs and second hotbarslot have to be books. ");
			return false;
		}

		if (!(to.getItemMeta() instanceof BookMeta)) {
			sender.sendMessage("The items in the firs and second hotbarslot have to be books. ");
			return false;
		}

		if (args.length != 1 && args.length != 0) {
			sender.sendMessage("No or one argumend expected.");
			return false;
		}

		if (args.length == 0) {
			return copyPages(from, to);
		}

		if (args.length == 1) {
			try {
				return copyPage(Integer.parseInt(args[0]), from, to);
			} catch (NumberFormatException e) {
				sender.sendMessage("The argument needs to be a number.");
				return false;
			}
		}
		return false;
	}

	private boolean copyPages(ItemStack from, ItemStack to) {
		BookMeta meta = (BookMeta) from.getItemMeta();
		List<String> copy = ((BookMeta) to.getItemMeta()).getPages();
		String[] pages = new String[copy.size()];
		for (int i = 0; i < copy.size(); i++) {
			pages[i] = copy.get(i);
		}
		meta.addPage(pages);
		from.setItemMeta(meta);
		return true;
	}

	private boolean copyPage(int i, ItemStack from, ItemStack to) {
		BookMeta meta = (BookMeta) from.getItemMeta();
		meta.addPage(((BookMeta) to.getItemMeta()).getPage(i));
		from.setItemMeta(meta);
		return true;
	}

}
