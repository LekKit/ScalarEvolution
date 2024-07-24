package lekkit.scev.container;

import java.util.UUID;

import lekkit.scev.tileentity.TileEntityBase;

import net.minecraft.entity.player.EntityPlayer;

public class ContainerTileDummy extends ContainerBase {
    protected TileEntityBase tile;

    public ContainerTileDummy(EntityPlayer player, TileEntityBase tile) {
        super(player);
        this.tile = tile;
    }

    public TileEntityBase getTileEntity() {
        return tile;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        if (tile != null) {
            return tile.isValid();
        }
        return false;
    }
}
