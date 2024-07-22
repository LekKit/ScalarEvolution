package lekkit.scev.items;

import lekkit.scev.main.ScalarEvolution;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;

public class ItemTinkerpad extends ItemBlockBase {
    public ItemTinkerpad(Block block) {
        super(block);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!player.isSneaking()) {
            if (!world.isRemote) {
                player.openGui(ScalarEvolution.instance, ScalarEvolution.GUI_DISPLAY, world, x, y, z);
            }
            return true;
        }
        return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }
}

