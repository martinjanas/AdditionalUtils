package additional_utils.recipes.crafter;

import additional_utils.AdditionalUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class CrafterSerializer implements RecipeSerializer<CrafterRecipe>
{
    public static final ResourceLocation id = new ResourceLocation(AdditionalUtils.MOD_ID, "crafter");

    @Override
    public Codec<CrafterRecipe> codec() {
        return RecordCodecBuilder.create(instance -> instance.group(
                Ingredient.CODEC.fieldOf("input1").forGetter(recipe -> recipe.input1),
                Ingredient.CODEC.fieldOf("input2").forGetter(recipe -> recipe.input2),
                ItemStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output),
                ResourceLocation.CODEC.fieldOf("id").forGetter(recipe -> recipe.id)
        ).apply(instance, CrafterRecipe::new));
    }

    @Override
    public CrafterRecipe fromNetwork(FriendlyByteBuf buffer)
    {
        Ingredient input1 = Ingredient.fromNetwork(buffer);
        Ingredient input2 = Ingredient.fromNetwork(buffer);
        ItemStack result = buffer.readItem();
        return new CrafterRecipe(input1, input2, result, id);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, CrafterRecipe recipe)
    {
        recipe.input1.toNetwork(buffer);
        recipe.input2.toNetwork(buffer);
        buffer.writeItem(recipe.output);
    }
}
