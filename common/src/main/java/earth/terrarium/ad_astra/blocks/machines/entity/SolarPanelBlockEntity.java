package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.ad_astra.screen.menu.SolarPanelMenu;
import earth.terrarium.ad_astra.util.ModUtils;
import earth.terrarium.botarium.api.energy.EnergyBlock;
import earth.terrarium.botarium.api.energy.EnergyHooks;
import earth.terrarium.botarium.api.energy.ExtractOnlyEnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SolarPanelBlockEntity extends AbstractMachineBlockEntity implements EnergyBlock {
    private ExtractOnlyEnergyContainer energyContainer;

    public SolarPanelBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.SOLAR_PANEL.get(), blockPos, blockState);
    }

    public static long getEnergyForDimension(Level level) {
        if (level != null) {
            return (long) (ModUtils.getSolarEnergy(level) * AdAstra.CONFIG.solarPanel.energyMultiplier);
        } else {
            return 0;
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new SolarPanelMenu(syncId, inv, this);
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide()) {
            // Check solar panel conditions.
            if (level.isDay() && (!this.level.dimension().equals(Level.OVERWORLD) || !this.level.isRaining() && !this.level.isThundering()) && level.canSeeSky(this.getBlockPos().above())) {
                this.getEnergyStorage().internalInsert(this.getEnergyPerTick(), false);
                this.setActive(true);
            } else {
                this.setActive(false);
            }

            EnergyHooks.distributeEnergyNearby(this, this.getEnergyPerTick());
        }
    }

    public long getEnergyPerTick() {
        return getEnergyForDimension(this.getLevel());
    }

    public long getMaxCapacity() {
        return this.getEnergyStorage().getMaxCapacity();
    }

    @Override
    public ExtractOnlyEnergyContainer getEnergyStorage() {
        return energyContainer == null ? energyContainer = new ExtractOnlyEnergyContainer(this, (int) AdAstra.CONFIG.solarPanel.maxEnergy) : this.energyContainer;
    }

    @Override
    public void update() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
    }
}