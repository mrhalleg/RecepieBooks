package plugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import generators.InventoryReader;
import generators.PageGenerator;
import generators.RecipeLoader;
import generic.Menu;
import generic.MenuOptionListener;

public class RecepiMenuHandler {
	public static Menu menu;
	public static Map<Integer, ItemStack> shaped;
	public static Map<Integer, ItemStack> shapeless;
	public static Map<Integer, ItemStack> furnace;
	public static Map<Integer, ItemStack> colors;

	public static final String textShapedRecipe = "Shaped Recipe";
	public static final String textShapelessRecipe = "Shapeless Recipe";
	public static final String textFurnaceRecipe = "Furnace Recipe";
	public static final String textGeneratePages = "Generate Pages";
	public static final String textSaveRecipe = "Save Recipe";
	public static final String textCraftingFieldBorder = "Crafting";
	public static final String textResultBorder = "Result";
	private static final String selectColor = "Select Color";;

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

	public static final byte[] colorData = new byte[] { 15, 11, 13, 9, 14, 10, 1, 8, 7, 11, 5, 3, 1,
			6, 4, 0 };

	public static final String name = "Create a new Recepie";
	public static final int size = 45;
	protected static final String metadata = "generatepagesrecipe";

	public static void setup() {
		shaped = getShaped();
		shapeless = getShapeless();
		furnace = getFurnace();
		colors = getColors();
		menu = getMenu();
	}

	private static Menu getMenu() {
		return new Menu(name, new MenuOptionListener[] { new MenuOptionListener(textShapedRecipe) {

			@Override
			public void click(InventoryClickEvent e) {
				Menu.setContent(e.getInventory(), shapeless);
			}
		}, new MenuOptionListener(textShapelessRecipe) {

			@Override
			public void click(InventoryClickEvent e) {
				Menu.setContent(e.getInventory(), furnace);
			}
		}, new MenuOptionListener(textFurnaceRecipe) {

			@Override
			public void click(InventoryClickEvent e) {
				Menu.setContent(e.getInventory(), shaped);
			}
		}, new MenuOptionListener(textGeneratePages) {

			@Override
			public void click(InventoryClickEvent e) {
				Recipe r = null;
				try {
					r = InventoryReader.getRecipe(e.getInventory());
				} catch (Exception x) {
					x.printStackTrace();
					e.getWhoClicked().sendMessage("Could not create recipe!");
					return;
				}
				e.getWhoClicked().setMetadata(metadata, new FixedMetadataValue(Plugin.plugin, r));
				Menu.setContent(e.getInventory(), colors);
			}
		}, new MenuOptionListener(textSaveRecipe) {

			@Override
			public void click(InventoryClickEvent e) {
				Recipe r = null;
				try {
					r = InventoryReader.getRecipe(e.getInventory());
				} catch (Exception x) {
					x.printStackTrace();
					e.getWhoClicked().sendMessage("Could not create recipe!");
					return;
				}
				e.getWhoClicked().sendMessage("Recipe created (reload to activate it).");
				RecipeLoader.saveRecipe(r);
			}
		}, new MenuOptionListener(textCraftingFieldBorder) {

			@Override
			public void click(InventoryClickEvent e) {}
		}, new MenuOptionListener(textResultBorder) {

			@Override
			public void click(InventoryClickEvent e) {}
		}, new MenuOptionListener(selectColor) {

			@Override
			public void click(InventoryClickEvent e) {
				ItemStack book = new ItemStack(Material.BOOK_AND_QUILL);
				BookMeta meta = (BookMeta) book.getItemMeta();
				meta.setPages(PageGenerator.generateRecipies(e.getCurrentItem().getItemMeta()
						.getLore().get(0).substring(0, 2), (Recipe) (e.getWhoClicked().getMetadata(
								metadata).get(0).value())));
				book.setItemMeta(meta);
				e.getWhoClicked().getWorld().dropItem(e.getWhoClicked().getLocation(), book);
			}
		} }, size, Plugin.plugin, shaped);
	}

	private static Map<Integer, ItemStack> getShaped() {
		HashMap<Integer, ItemStack> map = new HashMap<>();
		map.put(0, newItemStack(Material.WORKBENCH, textShapedRecipe, "This is a shaped Recipe.",
				"Change to Shapeless."));
		addDefaults(map);
		addTableDefaults(map);
		return map;
	}

	private static Map<Integer, ItemStack> getShapeless() {
		HashMap<Integer, ItemStack> map = new HashMap<>();
		map.put(0, newItemStack(Material.MUSHROOM_SOUP, textShapelessRecipe,
				"This is a shapeless Recipe.", "Change to Furnace."));
		addDefaults(map);
		addTableDefaults(map);
		return map;
	}

	private static Map<Integer, ItemStack> getFurnace() {
		HashMap<Integer, ItemStack> map = new HashMap<>();
		map.put(0, newItemStack(Material.FURNACE, textFurnaceRecipe, "This is a furnace Recipe.",
				"Change to Shaped."));
		addDefaults(map);
		map.put(35, resultBorder());
		map.put(34, resultBorder());
		map.put(43, resultBorder());

		map.put(27, craftBorder());
		map.put(28, craftBorder());
		map.put(37, craftBorder());
		return map;
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

	@SuppressWarnings("unchecked")
	private static void addDefaults(Map<Integer, ItemStack> map) {
		map.put(1, newItemStack(Material.BOOK_AND_QUILL, textGeneratePages,
				"Generate a bookpage for this recipe"));
		map.put(2, newItemStack(Material.DIAMOND, textSaveRecipe, "Save this Recipe",
				"(You will have to reload to activate it)"));
	}

	private static void addTableDefaults(Map<Integer, ItemStack> map) {
		map.put(9, craftBorder());
		map.put(10, craftBorder());
		map.put(11, craftBorder());
		map.put(12, craftBorder());
		map.put(21, craftBorder());
		map.put(30, craftBorder());
		map.put(39, craftBorder());

		map.put(35, resultBorder());
		map.put(34, resultBorder());
		map.put(43, resultBorder());
	}

	private static ItemStack craftBorder() {
		return newItemStack(Material.STAINED_GLASS_PANE, 15, textCraftingFieldBorder);
	}

	private static ItemStack resultBorder() {
		return newItemStack(Material.STAINED_GLASS_PANE, 0, textResultBorder);
	}

	private static ItemStack newItemStack(Material material, String name, String... lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
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
