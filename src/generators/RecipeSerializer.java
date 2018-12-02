package generators;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipeSerializer {

	public static YamlConfiguration serialize(Recipe r) {
		if (r instanceof ShapedRecipe) {
			return serializeShaped((ShapedRecipe) r);
		} else if (r instanceof ShapelessRecipe) {
			return serializeShapeless((ShapelessRecipe) r);
		} else if (r instanceof FurnaceRecipe) {
			return serializeFurnace((FurnaceRecipe) r);
		}
		return null;
	}

	// SHAPED-----------------------------------------------

	public static YamlConfiguration serializeShaped(ShapedRecipe r) {
		YamlConfiguration c = new YamlConfiguration();
		c.set(RecipeLoader.type, RecipeLoader.shaped);
		serializeResult(c, r.getResult());

		HashMap<Material, Character> map = generateMap(new HashSet<ItemStack>(r.getIngredientMap()
				.values()));

		int i = 0;
		for (String string : r.getShape()) {
			String line = "";
			for (Character cha : string.toCharArray()) {
				line = line + getIcon(map, r.getIngredientMap().get(cha));
			}
			c.set(RecipeLoader.line + i, line);
			i++;
		}

		int at = 0;
		for (Material m : map.keySet()) {
			c.set(String.valueOf(RecipeLoader.icons[at]),
					serializeIngredientNoAmount(new ItemStack(m)));
			at++;
		}

		return c;
	}

	// SHAPELESS--------------------------------------------

	public static YamlConfiguration serializeShapeless(ShapelessRecipe r) {
		YamlConfiguration c = new YamlConfiguration();
		c.set(RecipeLoader.type, RecipeLoader.shapeless);
		serializeResult(c, r.getResult());

		int i = 0;
		for (ItemStack item : r.getIngredientList()) {
			c.set(RecipeLoader.ingredient + i, serializeIngredient(item));
			i++;
		}
		return c;
	}

	// FURNACE---------------------------------------------

	public static YamlConfiguration serializeFurnace(FurnaceRecipe r) {
		YamlConfiguration c = new YamlConfiguration();
		c.set(RecipeLoader.type, RecipeLoader.furnace);
		serializeResult(c, r.getResult());
		c.set(RecipeLoader.ingredient, serializeIngredientNoAmount(r.getInput()));
		return c;
	}

	// ITEMS------------------------------------------------

	@SuppressWarnings("deprecation")
	private static String serializeIngredientNoAmount(ItemStack i) {
		String string = i.getTypeId() + RecipeLoader.dataSeperator + i.getData().getData();
		return string;
	}

	@SuppressWarnings("deprecation")
	private static String serializeIngredient(ItemStack i) {
		String string = i.getAmount() + RecipeLoader.amountSeperator + i.getTypeId()
				+ RecipeLoader.dataSeperator + i.getData().getData();
		return string;
	}

	private static void serializeResult(YamlConfiguration c, ItemStack i) {
		c.set(RecipeLoader.result, serializeIngredient(i));
	}

	// OTHER--------------------------------------------------

	private static HashMap<Material, Character> generateMap(Set<ItemStack> s) {

		Set<Material> set = new HashSet<Material>();
		for (ItemStack is : s) {
			if (is == null) {
				continue;
			}
			set.add(is.getType());
		}
		HashMap<Material, Character> map = new HashMap<Material, Character>();
		int i = 0;
		for (Material m : set) {
			map.put(m, RecipeLoader.icons[i]);
			i++;
		}
		return map;
	}

	private static char getIcon(HashMap<Material, Character> map, ItemStack is) {
		try {
			return map.get(is.getType());
		} catch (NullPointerException e) {
			return RecipeLoader.airIcon;
		}
	}
}
