package lekkit.scev.render.tileentity;

import lekkit.scev.gui.MachineGui;
import lekkit.scev.render.util.DisplayRenderer;
import net.minecraft.tileentity.TileEntity;

public class TileVT100Renderer extends TileRendererBase {
    public TileVT100Renderer() {
        super(false);
    }

    @Override
    public void renderTileEntity(TileEntity tileEntity) {
        int textureID = MachineGui.texID;
        DisplayRenderer.renderDisplay(textureID, -0.375, 0.1875, 0.1875, -0.25, 0.125);
    }
}
