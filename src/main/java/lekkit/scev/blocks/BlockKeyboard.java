package lekkit.scev.blocks;

import lekkit.scev.tileentity.TileEntityKeyboard;

import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;

// Reference vanilla class for ease of porting: BlockCocoa

public class BlockKeyboard extends BlockDirectionalModel {
    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return new TileEntityKeyboard();
    }

    @Override
    public void setBlockBoundsBasedOnDirection(int direction) {
        switch (direction) {
            case 0:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 0.5625F);
                break;
            case 1:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5625F, 0.0625F, 1.0F);
                break;
            case 2:
                this.setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 0.0625F, 1.0F);
                break;
            case 3:
                this.setBlockBounds(0.4375F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
                break;
        }
    }
}
