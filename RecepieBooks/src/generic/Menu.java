package generic;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Menu implements Listener {
	private String name;
	private MenuOptionListener[] options;
	private Map<Integer, ItemStack> startContents;
	private JavaPlugin plugin;
	private int size;

	public Menu(String name, MenuOptionListener[] menuOptionListeners, int size, JavaPlugin plugin,
			Map<Integer, ItemStack> startContents) {
		this.options = menuOptionListeners;
		this.name = name;
		this.size = size;
		this.startContents = startContents;
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public void open(Player player) {
		Inventory inv = Bukkit.createInventory(player, size, name);
		setContent(inv, startContents);
		player.openInventory(inv);
	}

	@EventHandler
	public void onIventoryClickEvent(InventoryClickEvent e) {
		if (e.getInventory().getName().equals(name)) {
			if ((e.getCurrentItem() == null) || (e.getCurrentItem().getItemMeta() == null)) {
				return;
			}
			String clicked = e.getCurrentItem().getItemMeta().getDisplayName();
			for (MenuOptionListener option : options) {
				if (option.getName().equals(clicked)) {
					e.setCancelled(true);
					option.click(e);
					plugin.getServer().getScheduler().runTask(plugin, option);
				}
			}
		}
	}

	public static void setContent(Inventory inv, Map<Integer, ItemStack> map) {
		inv.clear();
		for (Integer i : map.keySet()) {
			inv.setItem(i, map.get(i));
		}
	}
}
