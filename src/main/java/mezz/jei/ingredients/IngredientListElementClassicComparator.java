package mezz.jei.ingredients;

import java.util.Comparator;

import mezz.jei.config.Constants;
import mezz.jei.gui.ingredients.IIngredientListElement;
import net.minecraft.item.ItemStack;

/*
 * This is a reference comparator for performance and emergency use.
 */
public final class IngredientListElementClassicComparator implements Comparator<IIngredientListElement> {
	public static final IngredientListElementClassicComparator INSTANCE = new IngredientListElementClassicComparator();

	private IngredientListElementClassicComparator() {

	}

	@Override
	public int compare(IIngredientListElement o1, IIngredientListElement o2) {
		final String modName1 = o1.getModName();
		final String modName2 = o2.getModName();

		if (modName1.equals(modName2)) {
			boolean isItemStack1 = (o1.getIngredient() instanceof ItemStack);
			boolean isItemStack2 = (o2.getIngredient() instanceof ItemStack);
			if (isItemStack1 && !isItemStack2) {
				return -1;
			} else if (!isItemStack1 && isItemStack2) {
				return 1;
			}

			final int orderIndex1 = o1.getCreativeMenuOrder();
			final int orderIndex2 = o2.getCreativeMenuOrder();
			return Integer.compare(orderIndex1, orderIndex2);
		} else if (modName1.equals(Constants.MINECRAFT_NAME)) {
			return -1;
		} else if (modName2.equals(Constants.MINECRAFT_NAME)) {
			return 1;
		} else {
			return modName1.compareTo(modName2);
		}
	}
}
