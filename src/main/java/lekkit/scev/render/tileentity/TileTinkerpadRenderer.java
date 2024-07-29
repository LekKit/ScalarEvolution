package lekkit.scev.render.tileentity;

import lekkit.scev.client.DisplayManager;
import lekkit.scev.client.DisplayState;
import lekkit.scev.render.util.DisplayRenderer;
import lekkit.scev.tileentity.TileEntityTinkerpad;
import net.minecraft.tileentity.TileEntity;

public class TileTinkerpadRenderer extends TileRendererBase {
    public TileTinkerpadRenderer() {
        super(false);
    }

    @Override
    public void renderTileEntity(TileEntity tileEntity) {
        try {
            TileEntityTinkerpad tileLaptop = (TileEntityTinkerpad)tileEntity;
            DisplayState display = DisplayManager.getDisplayState(tileLaptop.getMachineUUID());
            if (display != null) {
                display.bindTexture();
                DisplayRenderer.renderOverlay(-0.4375, 0.4375, 0.4375, -0.3125, -0.4375);
            }
        } catch (Throwable e) {}
    }
}
