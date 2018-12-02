package generators;

import generic.ItemNameGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import plugin.Entry;

public class PageGenerator {

	public static final String[] icons = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i" };
	public static final String table = "Crafting Table";
	private static final String furnace = "Furnace";
	public static final String[] colors = new String[] { "0", "1", "2", "3", "4", "5", "6", "7",
			"8", "9", "a", "b", "c", "d", "e", "f" };

	public static String[] generateRecipies(String color, Recipe... recipes) {
		return generateRecipies(Arrays.asList(recipes), color);
	}

	public static String[] generateRecipies(List<Recipe> recipes, String color) {
		HashMap<ItemStack, List<Recipe>> map = new HashMap<ItemStack, List<Recipe>>();
		for (Recipe recipe : recipes) {
			boolean found = false;
			for (ItemStack result : map.keySet()) {
				if (result.isSimilar(recipe.getResult())) {
					map.get(result).add(recipe);
					found = true;
				}
			}

			if (!found) {
				List<Recipe> list = new ArrayList<Recipe>();
				list.add(recipe);
				map.put(recipe.getResult(), list);
			}
		}

		List<String> pages = new ArrayList<String>();

		for (List<Recipe> list : map.values()) {
			for (String s : generateRecepie(list, color))
				pages.add(s);
		}

		return pages.toArray(new String[1]);
	}

	private static String[] generateRecepie(List<Recipe> rs, String color) {
		Entry e = new Entry(color + "§l" + ItemNameGenerator.name(rs.get(0).getResult()) + "§0");
		for (Recipe r : rs) {
			if (r instanceof ShapelessRecipe) {

				e.addRecepie("\n\n§7§l" + r.getResult().getAmount() + "§lx " + table + "§0\n"
						+ generateShapelessRecepie((ShapelessRecipe) r, color));
			} else if (r instanceof ShapedRecipe) {

				e.addRecepie("\n\n§7§l" + r.getResult().getAmount() + "§lx " + table + "§0\n"
						+ generateShapedRecepie((ShapedRecipe) r, color));
			} else if (r instanceof FurnaceRecipe) {

				e.addRecepie("\n\n§7§l" + r.getResult().getAmount() + "§lx " + furnace + "§0\n"
						+ generateFurnaceRecepie((FurnaceRecipe) r, color));
			}
		}

		return e.getPages();
	}

	private static String generateShapelessRecepie(ShapelessRecipe r, String color) {
		String ingreediens = "";
		boolean first = true;
		for (ItemStack i : r.getIngredientList()) {
			if (first) {
				first = false;
				ingreediens = ingreediens + color + "+§0 " + ItemNameGenerator.name(i);
			} else {
				ingreediens = ingreediens + "\n" + color + "+§0 " + ItemNameGenerator.name(i);
			}

		}

		return ingreediens;
	}

	private static String generateShapedRecepie(ShapedRecipe r, String color) {
		String ingreediens = "";
		HashMap<Material, String> map = generateMap(new HashSet<ItemStack>(r.getIngredientMap()
				.values()));
		for (String s : r.getShape()) {
			for (Character c : s.toCharArray()) {
				ingreediens = ingreediens + " " + color + getIcon(map, r.getIngredientMap().get(c));
			}
			ingreediens = ingreediens + "\n";
		}

		boolean first = true;
		for (Material m : map.keySet()) {
			if (first) {
				first = false;

				ingreediens = ingreediens + color + getIcon(map, new ItemStack(m)) + "§0 = "
						+ ItemNameGenerator.name(new ItemStack(m));
			} else {

				ingreediens = ingreediens + "\n" + color + getIcon(map, new ItemStack(m)) + "§0 = "
						+ ItemNameGenerator.name(new ItemStack(m));
			}
		}

		return ingreediens;
	}

	private static String generateFurnaceRecepie(FurnaceRecipe r, String color) {
		return color + "+§0 " + ItemNameGenerator.name(r.getInput());
	}

	private static HashMap<Material, String> generateMap(Set<ItemStack> s) {

		Set<Material> set = new HashSet<Material>();
		for (ItemStack is : s) {
			if (is == null) {
				continue;
			}
			set.add(is.getType());
		}
		HashMap<Material, String> map = new HashMap<Material, String>();
		int i = 0;
		for (Material m : set) {
			map.put(m, icons[i]);
			i++;
		}
		return map;
	}

	private static String getIcon(HashMap<Material, String> map, ItemStack is) {
		try {
			return map.get(is.getType());
		} catch (NullPointerException e) {
			return "§7#";
		}
	}

}
