package plugin;

import org.bukkit.plugin.java.JavaPlugin;

import commandManagement.BaseSubCommandHandler;
import commandManagement.SubCommandHandler;
import generators.RecipeLoader;
import listener.FuraceShiftClickFix;
import mycommands.CopyCommand;
import mycommands.DeleteFileCommand;
import mycommands.DeletePageCommand;
import mycommands.EditLineCommand;
import mycommands.FromFileCommand;
import mycommands.HelpCommand;
import mycommands.InventoryCommand;
import mycommands.ListFilesCommand;
import mycommands.NewCommand;
import mycommands.UnsignCommand;
import testCommands.TestBase;

public class Plugin extends JavaPlugin {
	public static Plugin plugin;

	@Override
	public void onEnable() {
		plugin = this;
		RecepiMenuHandler.setup();
		ColorMenuHandler.setup();

		setupCommands();

		getServer().getPluginManager().registerEvents(new FuraceShiftClickFix(), this);

		RecipeLoader.enable();
	}

	public void setupCommands() {
		TestBase com = new TestBase();
		getCommand("test").setExecutor(com);

		BaseSubCommandHandler hrbBaseHandler = new BaseSubCommandHandler(this,
				"hallegsrecipebooks");
		SubCommandHandler editSubHandler = new SubCommandHandler("edit",
				"/hrb edit <clearwhitepages|copypages|unsign|setline>", hrbBaseHandler);
		SubCommandHandler recepieSubHandler = new SubCommandHandler("recepie",
				"/hrb recepie <new|delete>", hrbBaseHandler, "recepies");
		SubCommandHandler booksSubHandler = new SubCommandHandler("books",
				"/hrb books <inventory|file>", hrbBaseHandler, "book");
		new InventoryCommand(booksSubHandler);
		new NewCommand(recepieSubHandler);
		new DeleteFileCommand(recepieSubHandler);
		new ListFilesCommand(recepieSubHandler);
		new UnsignCommand(booksSubHandler);
		new CopyCommand(booksSubHandler);
		new DeletePageCommand(booksSubHandler);
		new EditLineCommand(booksSubHandler);
		new FromFileCommand(booksSubHandler);
		new HelpCommand(hrbBaseHandler);
	}

	public static void out(String s) {
		plugin.getServer().broadcastMessage(s);
	}
}