package generic;

import org.apache.commons.lang.WordUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ItemNameGenerator {
	@SuppressWarnings("deprecation")
	public static String name(ItemStack i) {
		if (i == null) {
			return "Air";
		}
		String name = "";
		if (!i.getItemMeta().hasDisplayName()) {
			name = i.getType().name().toLowerCase();
			name = name.replaceAll("_", " ");
			name = WordUtils.capitalizeFully(name);

			if (i.getData().getData() != 0) {
				name = name + " (" + i.getData().getData() + ")";
			}
		} else {
			name = i.getItemMeta().getDisplayName();
		}

		for (Enchantment e : i.getEnchantments().keySet()) {
			String enchantmentName = e.getName().toLowerCase();
			enchantmentName = enchantmentName.replaceAll("_", " ");
			name = WordUtils.capitalizeFully(enchantmentName);

			name = name + "\n" + enchantmentName;
		}

		return name;
	}
}
