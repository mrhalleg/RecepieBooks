package generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;

public class RecipeDeserializer {

	public static Recipe deserialize(YamlConfiguration c) {
		String t = c.getString(RecipeLoader.type);
		if (t.equals(RecipeLoader.shaped)) {
			return deserializeShaped(c);
		} else if (t.equals(RecipeLoader.shapeless)) {
			return deserializeShapeless(c);
		}
		if (t.equals(RecipeLoader.furnace)) {
			return deserializeFurnace(c);
		}
		return null;
	}

	// SHAPED-----------------------------------------------

	private static Recipe deserializeShaped(YamlConfiguration c) {
		ShapedRecipe r = new ShapedRecipe(deserializeResult(c));

		HashMap<Character, MaterialData> map = new HashMap<Character, MaterialData>();
		for (int i = 0; true; i++) {
			Character icon = RecipeLoader.icons[i];
			if (!c.contains(String.valueOf(icon))) {
				break;
			}
			map.put(icon, deserializeIngredientNoAmount(c.getString(String.valueOf(icon)))
					.getData());
		}

		List<String> lines = new ArrayList<String>();
		for (int i = 0; true; i++) {
			String line = RecipeLoader.line + i;
			if (!c.contains(line)) {
				break;
			}
			lines.add(c.getString(line));
		}

		int line = 0;
		int row = 0;
		String[] shape = new String[lines.size()];
		for (String string : lines) {
			shape[line] = "";
			for (Character cha : string.toCharArray()) {
				shape[line] = shape[line] + RecipeLoader.icons[row];
				row++;
			}
			line++;
		}

		r = r.shape(shape);

		int at = 0;
		for (String string : lines) {
			for (Character cha : string.toCharArray()) {
				if (!cha.equals(RecipeLoader.airIcon)) {
					r = r.setIngredient(RecipeLoader.icons[at], map.get(cha).getItemType());
				}
				at++;
			}
		}

		return r;
	}

	// SHAPELESS--------------------------------------------

	private static Recipe deserializeShapeless(YamlConfiguration c) {
		ShapelessRecipe r = new ShapelessRecipe(deserializeResult(c));
		for (int i = 0; true; i++) {
			String name = RecipeLoader.ingredient + i;
			if (!c.contains(name)) {
				break;
			}
			ItemStack ingredient = deserializeIngredient(c.getString(name));
			r.addIngredient(ingredient.getAmount(), ingredient.getData());
			i++;
		}

		return r;
	}

	// FURNACE---------------------------------------------

	private static Recipe deserializeFurnace(YamlConfiguration c) {
		return new FurnaceRecipe(deserializeResult(c), deserializeIngredientNoAmount(
				c.getString(RecipeLoader.ingredient)).getData());
	}

	// ITEMS------------------------------------------------

	@SuppressWarnings("deprecation")
	private static ItemStack deserializeIngredientNoAmount(String s) {
		String[] firstSplit = s.split(RecipeLoader.dataSeperator);
		return new ItemStack(Integer.parseInt(firstSplit[0]), (byte) 1, (byte) Integer
				.parseInt(firstSplit[1]));
	}

	@SuppressWarnings("deprecation")
	private static ItemStack deserializeIngredient(String s) {
		String[] firstSplit = s.split(RecipeLoader.dataSeperator);
		String[] secondSplit = firstSplit[0].split(RecipeLoader.amountSeperator);
		return new ItemStack(Integer.parseInt(secondSplit[1]), (byte) Integer
				.parseInt(secondSplit[0]), (byte) Integer.parseInt(firstSplit[1]));
	}

	private static ItemStack deserializeResult(YamlConfiguration c) {
		String s = c.getString(RecipeLoader.result);
		return deserializeIngredient(s);
	}
}
