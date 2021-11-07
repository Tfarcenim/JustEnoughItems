package mezz.jei.gui.overlay;

import java.util.List;

import mezz.jei.gui.ingredients.IIngredientListElement;

public interface IIngredientGridSource<V> {
	List<IIngredientListElement<V>> getIngredientList();

	int size();

	void addListener(Listener listener);

	interface Listener {
		void onChange();
	}
}
