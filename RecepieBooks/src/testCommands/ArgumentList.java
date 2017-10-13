package testCommands;

import java.util.Arrays;
import java.util.List;

public class ArgumentList {

	private List<Argument> arguments;

	public ArgumentList(Argument... args) {
		this.arguments = Arrays.asList(args);
	}

	public boolean match(String[] args) {
		if (this.arguments.size() > args.length) {
			return false;
		}
		for (int i = 0; i < args.length; i++) {
			if (!this.arguments.get(i).valid(args[i])) {
				return false;
			}
		}
		return true;
	}

}
