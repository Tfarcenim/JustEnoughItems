package mezz.jei.ingredients;

import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.recipe.IIngredientType;
import mezz.jei.config.Config;
import mezz.jei.gui.ingredients.IIngredientListElement;
import mezz.jei.startup.IModIdHelper;
import mezz.jei.util.Log;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.ProgressManager;

import javax.annotation.Nullable;
import java.util.Collection;

public final class IngredientListElementFactory {
	private static IngredientOrderTracker ORDER_TRACKER = new IngredientOrderTracker();
	private final IngredientListComparator ingredientListComparator;

	public IngredientListElementFactory(IngredientListComparator ingredientListComparator) {
		this.ingredientListComparator = ingredientListComparator;
	}

	public NonNullList<IIngredientListElement> createBaseList(IIngredientRegistry ingredientRegistry, IModIdHelper modIdHelper) {
		NonNullList<IIngredientListElement> ingredientListElements = NonNullList.create();

		for (IIngredientType<?> ingredientType : ingredientRegistry.getRegisteredIngredientTypes()) {
			addToBaseList(ingredientListElements, ingredientRegistry, ingredientType, modIdHelper);
		}

		try {
			ingredientListComparator.testSort(ingredientListElements);
			ingredientListElements.sort(ingredientListComparator);
		} catch (Exception ex) {
			if (Config.isDebugModeEnabled()) {
				//If you are developing a new sorting option, you probably want it to stay stopped to see what it did. 
				Log.get().error("Item sorting failed.  Aborting sort.", ex);
			} else {
				Log.get().error("Item sorting failed.  Using old method.", ex);
				try {
					ingredientListElements.sort(IngredientListElementClassicComparator.INSTANCE);
				} catch (Exception ex2) {
					Log.get().error("Classic Item sorting failed.  Aborting sort.", ex2);
				}					
			}
		}
		return ingredientListElements;
	}

	public static <V> NonNullList<IIngredientListElement<V>> createList(IIngredientRegistry ingredientRegistry, IIngredientType<V> ingredientType, Collection<V> ingredients, IModIdHelper modIdHelper) {
		IIngredientHelper<V> ingredientHelper = ingredientRegistry.getIngredientHelper(ingredientType);
		IIngredientRenderer<V> ingredientRenderer = ingredientRegistry.getIngredientRenderer(ingredientType);
		
		NonNullList<IIngredientListElement<V>> list = NonNullList.create();
		for (V ingredient : ingredients) {
			if (ingredient != null) {
				int orderIndex = ORDER_TRACKER.getOrderIndex(ingredient, ingredientHelper);
				IngredientListElement<V> ingredientListElement = IngredientListElement.create(ingredient, ingredientType, ingredientHelper, ingredientRenderer, modIdHelper, orderIndex);
				if (ingredientListElement != null) {
					list.add(ingredientListElement);
				}
			}
		}
		return list;
	}

	@Nullable
	public static <V> IIngredientListElement<V> createUnorderedElement(IIngredientRegistry ingredientRegistry, IIngredientType<V> ingredientType, V ingredient, IModIdHelper modIdHelper) {
		IIngredientHelper<V> ingredientHelper = ingredientRegistry.getIngredientHelper(ingredientType);
		IIngredientRenderer<V> ingredientRenderer = ingredientRegistry.getIngredientRenderer(ingredientType);
		return IngredientListElement.create(ingredient, ingredientType, ingredientHelper, ingredientRenderer, modIdHelper, 0);
	}

	private static <V> void addToBaseList(NonNullList<IIngredientListElement> baseList, IIngredientRegistry ingredientRegistry, IIngredientType<V> ingredientType, IModIdHelper modIdHelper) {
		IIngredientHelper<V> ingredientHelper = ingredientRegistry.getIngredientHelper(ingredientType);
		IIngredientRenderer<V> ingredientRenderer = ingredientRegistry.getIngredientRenderer(ingredientType);

		Collection<V> ingredients = ingredientRegistry.getAllIngredients(ingredientType);
		ProgressManager.ProgressBar progressBar = ProgressManager.push("Registering ingredients: " + ingredientType.getIngredientClass().getSimpleName(), ingredients.size());
		for (V ingredient : ingredients) {
			progressBar.step("");
			if (ingredient != null) {
				int orderIndex = ORDER_TRACKER.getOrderIndex(ingredient, ingredientHelper);
				IngredientListElement<V> ingredientListElement = IngredientListElement.create(ingredient, ingredientType, ingredientHelper, ingredientRenderer, modIdHelper, orderIndex);
				if (ingredientListElement != null) {
					baseList.add(ingredientListElement);
				}
			}
		}
		ProgressManager.pop(progressBar);
	}

}
