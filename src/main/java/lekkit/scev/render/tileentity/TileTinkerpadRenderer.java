package lekkit.scev.render.tileentity;

import lekkit.scev.render.util.DisplayRenderer;
import lekkit.scev.gui.MachineGui;

import net.minecraft.tileentity.TileEntity;

public class TileTinkerpadRenderer extends TileRendererBase {
    public TileTinkerpadRenderer() {
        super(false);
    }

    @Override
    public void renderTileEntity(TileEntity tileEntity) {
        int textureID = MachineGui.texID;
        DisplayRenderer.renderDisplay(textureID, -0.4375, 0.4375, 0.4375, -0.3125, -0.4375);
    }
}
