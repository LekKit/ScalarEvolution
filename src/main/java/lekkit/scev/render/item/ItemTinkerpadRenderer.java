package lekkit.scev.render.item;

import lekkit.scev.render.util.DisplayRenderer;
import lekkit.scev.gui.MachineGui;

import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraft.item.ItemStack;

public class ItemTinkerpadRenderer extends ItemModelRenderer {
    public ItemTinkerpadRenderer() {
        super("tinkerpad");
    }

    @Override
    public void renderDecorations(ItemRenderType type, ItemStack item) {
        int textureID = MachineGui.texID;
        DisplayRenderer.renderDisplay(textureID, -0.4375, 0.4375, 0.4375, -0.3125, -0.4375);
    }
}
