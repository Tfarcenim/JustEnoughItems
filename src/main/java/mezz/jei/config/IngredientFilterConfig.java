package mezz.jei.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class IngredientFilterConfig implements IIngredientFilterConfig, IJEIConfig {
	private final IngredientFilterConfigValues values;

	// Forge config
	public final ForgeConfigSpec.EnumValue<SearchMode> modNameSearchMode;
	public final ForgeConfigSpec.EnumValue<SearchMode> tooltipSearchMode;
	public final ForgeConfigSpec.EnumValue<SearchMode> tagSearchMode;
	public final ForgeConfigSpec.EnumValue<SearchMode> creativeTabSearchMode;
	public final ForgeConfigSpec.EnumValue<SearchMode> colorSearchMode;
	public final ForgeConfigSpec.EnumValue<SearchMode> resourceIdSearchMode;
	public final ForgeConfigSpec.BooleanValue searchAdvancedTooltips;

	public IngredientFilterConfig(ForgeConfigSpec.Builder builder) {
		this.values = new IngredientFilterConfigValues();
		IngredientFilterConfigValues defaultValues = new IngredientFilterConfigValues();

		builder.push("search");
		builder.comment("Search mode for Mod Names (prefix: @)");
		modNameSearchMode = builder.defineEnum("ModNameSearchMode", defaultValues.modNameSearchMode);
		builder.comment("Search mode for Tooltips (prefix: #)");
		tooltipSearchMode = builder.defineEnum("TooltipSearchMode", defaultValues.tooltipSearchMode);
		builder.comment("Search mode for Tag Names (prefix: $)");
		tagSearchMode = builder.defineEnum("TagSearchMode", defaultValues.tagSearchMode);
		builder.comment("Search mode for Creative Tab Names (prefix: %)");
		creativeTabSearchMode = builder.defineEnum("CreativeTabSearchMode", defaultValues.creativeTabSearchMode);
		builder.comment("Search mode for Colors (prefix: ^)");
		colorSearchMode = builder.defineEnum("ColorSearchMode", defaultValues.colorSearchMode);
		builder.comment("Search mode for resources IDs (prefix: &)");
		resourceIdSearchMode = builder.defineEnum("ResourceIdSearchMode", defaultValues.resourceIdSearchMode);
		builder.comment("Search advanced tooltips (visible with F3+H)");
		searchAdvancedTooltips = builder.define("SearchAdvancedTooltips", defaultValues.searchAdvancedTooltips);
		builder.pop();
	}

	@Override
	public void reload() {
		values.modNameSearchMode = modNameSearchMode.get();
		values.tooltipSearchMode = tooltipSearchMode.get();
		values.tagSearchMode = tagSearchMode.get();
		values.creativeTabSearchMode = creativeTabSearchMode.get();
		values.colorSearchMode = colorSearchMode.get();
		values.resourceIdSearchMode = resourceIdSearchMode.get();
		values.searchAdvancedTooltips = searchAdvancedTooltips.get();
	}

	@Override
	public SearchMode getModNameSearchMode() {
		return values.modNameSearchMode;
	}

	@Override
	public SearchMode getTooltipSearchMode() {
		return values.tooltipSearchMode;
	}

	@Override
	public SearchMode getTagSearchMode() {
		return values.tagSearchMode;
	}

	@Override
	public SearchMode getCreativeTabSearchMode() {
		return values.creativeTabSearchMode;
	}

	@Override
	public SearchMode getColorSearchMode() {
		return values.colorSearchMode;
	}

	@Override
	public SearchMode getResourceIdSearchMode() {
		return values.resourceIdSearchMode;
	}

	@Override
	public boolean getSearchAdvancedTooltips() {
		return values.searchAdvancedTooltips;
	}

}
