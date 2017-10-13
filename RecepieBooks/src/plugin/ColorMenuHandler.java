package plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import generators.PageGenerator;
import generic.Menu;
import generic.MenuOptionListener;

public class ColorMenuHandler {
	public static Menu menu;
	public static Map<Integer, ItemStack> colors;
	private static final String selectColor = "Select Color";;

	public static final byte[] colorData = new byte[] { 15, 11, 13, 9, 14, 10, 1, 8, 7, 11, 5, 3, 1,
			6, 4, 0 };

	public static HashMap getMap() {
		HashMap<String, String> colorNames = new HashMap<>();
		colorNames.put("0", "Black");
		colorNames.put("1", "Dark Blue");
		colorNames.put("2", "Dark Green");
		colorNames.put("3", "Cyan");
		colorNames.put("4", "Dark Red");
		colorNames.put("5", "Violett");
		colorNames.put("6", "Orange");
		colorNames.put("7", "Light Gray");
		colorNames.put("8", "Gray");
		colorNames.put("9", "Blue");
		colorNames.put("a", "Light Green");
		colorNames.put("b", "Light Blue");
		colorNames.put("c", "Light Red");
		colorNames.put("d", "Pink");
		colorNames.put("e", "Yellow");
		colorNames.put("f", "White");
		return colorNames;
	}

	public static final int size = 45;
	protected static final String metadata = "generatepagesinventory";

	public static void setup() {
		colors = getColors();
		menu = getMenu();
	}

	private static Menu getMenu() {
		return new Menu("Generate a new Recipebook.", new MenuOptionListener[] {
				new MenuOptionListener(selectColor) {

					@Override
					public void click(InventoryClickEvent e) {
						HumanEntity player = e.getWhoClicked();

						String color = e.getCurrentItem().getItemMeta().getLore().get(0).substring(
								0, 2);
						e.getWhoClicked().sendMessage(color + "test");

						ItemStack book = new ItemStack(Material.BOOK_AND_QUILL);
						BookMeta meta = (BookMeta) book.getItemMeta();

						for (ItemStack is : player.getInventory().getContents()) {
							if (is == null) {
								continue;
							}
							List<Recipe> rs = Plugin.plugin.getServer().getRecipesFor(is);
							List<Recipe> recepies = new ArrayList<>();
							for (Recipe r : rs) {
								if (r.getResult().isSimilar(is)) {
									recepies.add(r);
								}
							}

							if (recepies.size() == 0) {
								continue;
							}

							meta.addPage(PageGenerator.generateRecipies(recepies, color));
						}
						book.setItemMeta(meta);
						player.getWorld().dropItem(player.getLocation(), book);
					}
				} }, size, Plugin.plugin, colors);

	}

	private static Map<Integer, ItemStack> getColors() {
		HashMap<Integer, ItemStack> map = new HashMap<>();

		String[] names = new String[16];
		int e = 0;
		HashMap<String, String> colorMap = getMap();
		for (String s : PageGenerator.colors) {
			names[e] = colorMap.get(s);
			e++;
		}

		for (int i = 0; i < 15; i++) {
			map.put(i, newItemStack(Material.STAINED_GLASS_PANE, colorData[i], selectColor, "§"
					+ PageGenerator.colors[i] + names[i]));
		}
		return map;
	}

	private static ItemStack newItemStack(Material material, int data, String name,
			String... lore) {
		ItemStack item = new ItemStack(material, 1, (byte) data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
	}
}
