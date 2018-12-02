package listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import plugin.Plugin;

public class FuraceShiftClickFix implements Listener {

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e) {
		if (e.getClick().isShiftClick()) {
			Plugin.plugin.getServer().getScheduler().runTask(Plugin.plugin,
					new Updater((Player) e.getWhoClicked()));
		}
	}

	private class Updater implements Runnable {
		private Player player;

		public Updater(Player player) {
			this.player = player;
		}

		@Override
		public void run() {
			player.updateInventory();
		}
	}
}
