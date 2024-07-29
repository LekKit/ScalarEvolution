package lekkit.scev.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class SolderingRecipes implements IRecipe {

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return null;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    @Override
    public int getRecipeSize() {
        return 0;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return false;
    }
}
