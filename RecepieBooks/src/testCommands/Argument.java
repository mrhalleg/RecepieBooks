package testCommands;

import java.util.List;

public abstract class Argument {

	public Argument() {
		// TODO Auto-generated constructor stub
	}

	public boolean valid(String arg) {
		return list().contains(arg);
	}

	public abstract List<String> list();

}
