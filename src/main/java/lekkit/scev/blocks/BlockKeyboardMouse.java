package lekkit.scev.blocks;

import lekkit.scev.tileentity.TileEntityKeyboardMouse;

import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;

// Reference vanilla class for ease of porting: BlockCocoa

public class BlockKeyboardMouse extends BlockKeyboard {
    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return new TileEntityKeyboardMouse();
    }
}
