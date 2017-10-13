package generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;

import plugin.RecepiMenuHandler;

public class InventoryReader {
	public static final int[] slots = new int[] { 18, 19, 20, 27, 28, 29, 36, 37, 38 };

	public static final int[][] lineSlots = new int[][] { { 18, 19, 20 }, { 27, 28, 29 },
			{ 36, 37, 38 } };

	public static Recipe getRecipe(Inventory inv) {
		ItemStack i = inv.getItem(0);
		if (i == null) {
			return null;
		}
		String name = i.getItemMeta().getDisplayName();
		if (name.equals(RecepiMenuHandler.textShapedRecipe)) {
			return getShaped(inv);
		}
		if (name.equals(RecepiMenuHandler.textShapelessRecipe)) {
			return getShapeless(inv);
		}
		if (name.equals(RecepiMenuHandler.textFurnaceRecipe)) {
			return getFurnace(inv);
		}
		return null;
	}

	private static Recipe getFurnace(Inventory i) {
		return new FurnaceRecipe(i.getItem(44), i.getItem(36).getData());
	}

	private static Recipe getShapeless(Inventory i) {
		ShapelessRecipe r = new ShapelessRecipe(i.getItem(44));

		for (int nr : slots) {
			if ((i.getItem(nr) != null) && (i.getItem(nr).getData() != null)) {
				r.addIngredient(i.getItem(nr).getData());
			}
		}
		return r;
	}

	private static Recipe getShaped(Inventory inv) {
		ShapedRecipe r = new ShapedRecipe(inv.getItem(44));

		Set<MaterialData> set = new HashSet<MaterialData>();
		for (int nr : slots) {
			if (inv.getItem(nr) != null) {
				set.add(inv.getItem(nr).getData());
			}
		}

		HashMap<MaterialData, Character> map = generateMap(set);

		String[] lines = new String[3];

		int line = 0;
		for (int[] array : lineSlots) {
			lines[line] = "";
			for (int i : array) {
				lines[line] = lines[line] + getIcon(map, inv.getItem(i));
			}
			line++;
		}

		List<String> usableLines = new ArrayList<String>();
		for (String s : lines) {
			usableLines.add(s);
		}

		boolean changed = true;
		while (changed) {
			changed = false;
			if (needToCutRow(usableLines, 0)) {
				usableLines = cutRow(usableLines, 0);
				changed = true;
			}

			if (needToCutRow(usableLines, usableLines.get(0).length() - 1)) {
				usableLines = cutRow(usableLines, usableLines.get(0).length() - 1);
				changed = true;
			}

			if (needToCutLine(usableLines, 0)) {
				usableLines = cutLine(usableLines, 0);
				changed = true;
			}

			if (needToCutLine(usableLines, usableLines.size() - 1)) {
				usableLines = cutLine(usableLines, usableLines.size() - 1);
				changed = true;
			}
		}

		lines = new String[usableLines.size()];

		int atChar = 0;
		for (int i = 0; i < usableLines.size(); i++) {
			lines[i] = "";
			for (char c : usableLines.get(i).toCharArray()) {
				lines[i] = lines[i] + RecipeLoader.icons[atChar];
				atChar++;
			}
		}

		r = r.shape(lines);

		atChar = 0;
		for (String s : usableLines) {
			for (char c : s.toCharArray()) {
				if (getMaterialData(map, c) != null) {
					r = r.setIngredient(RecipeLoader.icons[atChar], getMaterialData(map, c));
				}
				atChar++;
			}
		}

		return r;
	}

	private static MaterialData getMaterialData(HashMap<MaterialData, Character> map, char c) {
		for (MaterialData d : map.keySet()) {
			if (map.get(d) == c) {
				return d;
			}
		}
		return null;
	}

	private static boolean needToCutRow(List<String> list, int i) {
		for (String s : list) {
			if (s.charAt(i) != RecipeLoader.airIcon) {
				return false;
			}
		}
		return true;
	}

	private static boolean needToCutLine(List<String> list, int i) {
		return list.get(i).replace(RecipeLoader.airIcon, ' ').trim().isEmpty();
	}

	private static List<String> cutRow(List<String> list, int i) {
		for (int e = 0; e < list.size(); e++) {
			String string = "";
			for (int j = 0; j < list.get(e).toCharArray().length; j++) {
				if (i != j) {
					string = string + list.get(e).toCharArray()[j];
				}
			}
			list.set(e, string);
		}
		return list;
	}

	private static List<String> cutLine(List<String> list, int i) {
		list.remove(i);
		return list;
	}

	private static HashMap<MaterialData, Character> generateMap(Set<MaterialData> s) {

		HashMap<MaterialData, Character> map = new HashMap<MaterialData, Character>();
		int i = 0;
		for (MaterialData m : s) {
			map.put(m, RecipeLoader.icons[i]);
			i++;
		}
		return map;
	}

	private static char getIcon(HashMap<MaterialData, Character> map, ItemStack is) {
		try {

			return map.get(is.getData());
		} catch (NullPointerException e) {
			return RecipeLoader.airIcon;
		}
	}
}
