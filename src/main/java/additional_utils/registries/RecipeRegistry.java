package additional_utils.registries;

import additional_utils.AdditionalUtils;
import additional_utils.recipes.CrafterRecipe;
import additional_utils.recipes.serializers.CrafterSerializer;
import additional_utils.registries.impl.ModRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RecipeRegistry implements ModRegistry
{
    public static final DeferredRegister<RecipeSerializer<?>> serializers = DeferredRegister.create(Registries.RECIPE_SERIALIZER, AdditionalUtils.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> recipe_types = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, AdditionalUtils.MOD_ID);

    public static DeferredHolder<RecipeType<?>, RecipeType<CrafterRecipe>> crafter_type;
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CrafterRecipe>> crafter_serializer;

    @Override
    public void register(IEventBus bus)
    {
        crafter_type = recipe_types.register("crafter", () -> new RecipeType<CrafterRecipe>() { });
        crafter_serializer = serializers.register("crafter", CrafterSerializer::new);

        recipe_types.register(bus);
        serializers.register(bus);
    }
}
