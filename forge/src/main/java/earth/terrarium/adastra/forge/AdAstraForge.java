package earth.terrarium.adastra.forge;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.commands.PlanetsCommand;
import earth.terrarium.adastra.common.registry.ModEntityTypes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(AdAstra.MOD_ID)
public class AdAstraForge {

    public AdAstraForge() {
        AdAstra.init();
        MinecraftForge.EVENT_BUS.addListener(AdAstraForge::onAddReloadListener);
        MinecraftForge.EVENT_BUS.addListener(AdAstraForge::onDatapackSync);
        MinecraftForge.EVENT_BUS.addListener(AdAstraForge::onServerTick);
        MinecraftForge.EVENT_BUS.addListener(AdAstraForge::onAttributes);
        MinecraftForge.EVENT_BUS.addListener(AdAstraForge::registerCommands);
    }

    public static void onAddReloadListener(AddReloadListenerEvent event) {
        AdAstra.onAddReloadListener((id, listener) -> event.addListener(listener));
    }

    public static void onDatapackSync(OnDatapackSyncEvent event) {
        if (event.getPlayer() != null) {
            AdAstra.onDatapackSync(event.getPlayer());
        } else {
            for (var player : event.getPlayerList().getPlayers()) {
                AdAstra.onDatapackSync(player);
            }
        }
    }

    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            AdAstra.onServerTick(event.getServer());
        }
    }

    public static void onAttributes(EntityAttributeCreationEvent event) {
        ModEntityTypes.registerAttributes((entityType, attribute) -> event.put(entityType.get(), attribute.get().build()));
    }

    private static void registerCommands(RegisterCommandsEvent event) {
        PlanetsCommand.register(event.getDispatcher());
    }
}