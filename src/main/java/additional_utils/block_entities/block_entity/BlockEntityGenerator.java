package additional_utils.block_entities.block_entity;

import additional_utils.registries.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;

public class BlockEntityGenerator extends BlockEntity
{
    private static int max_energy = 100000;
    private static int rf_per_tick = 25;

    public final EnergyStorage energy = new EnergyStorage(max_energy, rf_per_tick, 0)
    {
        @Override
        public int extractEnergy(int maxExtract, boolean simulate)
        {
            int extracted = super.extractEnergy(maxExtract, simulate);
            if (extracted > 0 && !simulate)
                setChanged();

            return extracted;
        }
    };

    public BlockEntityGenerator(BlockPos pos, BlockState blockState)
    {
        super(BlockEntityRegistry.generator.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);

        setChanged();
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);

        setChanged();
    }

    public <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T e)
    {
        if (level.isClientSide())
            return;

        if ((energy.getEnergyStored() + rf_per_tick) <= energy.getMaxEnergyStored())
        {
            energy.receiveEnergy(rf_per_tick, false);
            setChanged();
        }

        System.out.println("Energy Stored: " + energy.getEnergyStored() + " / " + energy.getMaxEnergyStored() + " RF");
    }
}
