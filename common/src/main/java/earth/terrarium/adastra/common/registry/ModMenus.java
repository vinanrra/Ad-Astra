package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.menus.machines.*;
import earth.terrarium.botarium.common.registry.RegistryHelpers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;

public class ModMenus {
    public static final ResourcefulRegistry<MenuType<?>> MENUS = ResourcefulRegistries.create(BuiltInRegistries.MENU, AdAstra.MOD_ID);

    public static final RegistryEntry<MenuType<BatteryMenu>> BATTERY = MENUS.register("battery", () -> RegistryHelpers.createMenuType(BatteryMenu::new));
    public static final RegistryEntry<MenuType<SeparatorMenu>> SEPARATOR = MENUS.register("separator", () -> RegistryHelpers.createMenuType(SeparatorMenu::new));
    public static final RegistryEntry<MenuType<HydraulicPressMenu>> HYDRAULIC_PRESS = MENUS.register("hydraulic_press", () -> RegistryHelpers.createMenuType(HydraulicPressMenu::new));
    public static final RegistryEntry<MenuType<RecyclerMenu>> RECYCLER = MENUS.register("recycler", () -> RegistryHelpers.createMenuType(RecyclerMenu::new));
    public static final RegistryEntry<MenuType<SolarPanelMenu>> SOLAR_PANEL = MENUS.register("solar_panel", () -> RegistryHelpers.createMenuType(SolarPanelMenu::new));
    public static final RegistryEntry<MenuType<OilRefineryMenu>> OIL_REFINERY = MENUS.register("oil_refinery", () -> RegistryHelpers.createMenuType(OilRefineryMenu::new));
}
