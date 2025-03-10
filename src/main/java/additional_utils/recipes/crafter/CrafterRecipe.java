package additional_utils.recipes.crafter;

import additional_utils.registries.RecipeRegistry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class CrafterRecipe implements Recipe<Container>
{
    public final Ingredient input1;
    public final Ingredient input2;
    public final ItemStack output;
    public final ResourceLocation id;

    public CrafterRecipe(Ingredient input1, Ingredient input2, ItemStack output, ResourceLocation id)
    {
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.id = id;
    }

    @Override
    public boolean matches(Container container, Level level)
    {
        if (level.isClientSide())
            return false;

        return input1.test(container.getItem(0)) && input2.test(container.getItem(1));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess)
    {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess)
    {
        return output.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return RecipeRegistry.crafter_serializer.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return RecipeRegistry.crafter_type.get();
    }
}
