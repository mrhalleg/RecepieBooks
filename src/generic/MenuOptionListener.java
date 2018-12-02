package generic;

import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class MenuOptionListener implements Runnable {
	protected String name;

	public MenuOptionListener(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract void click(InventoryClickEvent e);

	@Override
	public void run() {}
}
