package lekkit.scev.tileentity;

import lekkit.scev.server.IDisplayHandle;

import net.minecraft.tileentity.TileEntity;

public class TileEntityTinkerpad extends TileEntityComputerCase implements IDisplayHandle {
    public TileEntityTinkerpad() {
        super(3, 2);
    }
}
