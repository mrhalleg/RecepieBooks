package testCommands;

public class TestBase extends BaseCommand {

	public TestBase() {
		super("test", false, false, "", "", null);
		addSubCommand(new MTest());
	}
}
