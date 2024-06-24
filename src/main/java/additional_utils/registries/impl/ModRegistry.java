package additional_utils.registries.impl;

import net.neoforged.bus.api.IEventBus;

public interface ModRegistry
{
    void register();
    void register_to_bus(IEventBus bus);
}
