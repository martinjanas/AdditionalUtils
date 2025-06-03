package additional_utils.api.energy_storage;

import net.neoforged.neoforge.energy.EnergyStorage;

public class MyEnergyStorage extends EnergyStorage
{
    public MyEnergyStorage(int capacity)
    {
        this(capacity, capacity, capacity, 0);
    }

    public MyEnergyStorage(int capacity, int maxTransfer)
    {
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public MyEnergyStorage(int capacity, int maxReceive, int maxExtract)
    {
        this(capacity, maxReceive, maxExtract, 0);
    }

    public MyEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy)
    {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public void SetEnergy(int energy)
    {
        this.energy = Math.min(energy, this.capacity);
    }

}
