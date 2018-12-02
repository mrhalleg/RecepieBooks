package generators;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Recipe;

import plugin.Plugin;

public class RecipeLoader {
	static final String type = "type";
	static final String shaped = "shaped";
	static final String shapeless = "shapeless";
	static final String furnace = "furnace";
	static final String result = "result";
	static final String ingredient = "ingredient";
	public static final String line = "line";
	public static final String dataSeperator = "/";
	public static final String amountSeperator = "x";
	public static final char airIcon = '0';
	public static final char[] icons = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i' };

	public static String getFolderPath() {
		return Plugin.plugin.getDataFolder().getPath() + "/recipes/";
	}

	private static void createFolders() {
		new File(getFolderPath()).mkdirs();
	}

	private static String getFreeFileName() {
		String name = "";
		for (int i = 0; true; i++) {
			name = "recepie" + i + ".yml";
			if (canBeUsed(name)) {
				return name;
			}
		}
	}

	private static boolean canBeUsed(String name) {
		for (File f : new File(getFolderPath()).listFiles()) {
			if (f.getName().equals(name)) {
				return false;
			}
		}

		return true;
	}

	public static Recipe loadRecipe(String fileName) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(getFolderPath()
				+ fileName));
		try {

			return RecipeDeserializer.deserialize(config);
		} catch (Exception e) {
			return null;
		}
	}

	public static void enable() {
		for (Recipe r : loadRecipes()) {
			Plugin.plugin.getServer().addRecipe(r);
		}
	}

	public static List<Recipe> loadRecipes() {
		Plugin.plugin.getLogger().info("Loading recipes...");
		createFolders();
		List<Recipe> recipes = new ArrayList<Recipe>();

		File[] files = new File(getFolderPath()).listFiles();
		List<String> fileNamesToLoad = new ArrayList<String>();

		for (File f : files) {
			if (FilenameUtils.getExtension(f.getName()).equals("yml")) {
				fileNamesToLoad.add(f.getName());
			}
		}

		int unloadableRecipies = 0;
		int loadableRecipes = 0;
		for (String s : fileNamesToLoad) {
			Recipe r = loadRecipe(s);
			if (r == null) {
				Plugin.plugin.getLogger().warning(
						"The file \"" + s + "\" could not be loaded. It will be ignored.");
				unloadableRecipies++;
			} else {
				recipes.add(r);
				loadableRecipes++;
			}
		}
		Plugin.plugin.getLogger().info(
				"Done! Loaded " + loadableRecipes + " Recepies. " + unloadableRecipies
						+ " Files could not be loaded.");

		return recipes;
	}

	public static void saveRecipe(Recipe r) {
		createFolders();
		YamlConfiguration config = RecipeSerializer.serialize(r);
		try {
			config.save(new File(getFolderPath() + getFreeFileName()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
