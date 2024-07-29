package lekkit.scev.blocks;

import lekkit.scev.tileentity.TileEntityKeyboardMouse;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

// Reference vanilla class for ease of porting: BlockCocoa

public class BlockKeyboardMouse extends BlockKeyboard {
    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return new TileEntityKeyboardMouse();
    }
}
