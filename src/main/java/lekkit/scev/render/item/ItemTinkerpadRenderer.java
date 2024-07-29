package lekkit.scev.render.item;

import lekkit.scev.client.DisplayManager;
import lekkit.scev.client.DisplayState;
import lekkit.scev.items.util.NBTUtil;
import lekkit.scev.render.util.DisplayRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class ItemTinkerpadRenderer extends ItemModelRenderer {
    public ItemTinkerpadRenderer() {
        super("tinkerpad");
    }

    @Override
    public void renderDecorations(ItemRenderType type, ItemStack stack) {
        try {
            DisplayState display = DisplayManager.getDisplayState(NBTUtil.getItemUUID(stack));
            if (display != null) {
                display.bindTexture();
                DisplayRenderer.renderOverlay(-0.4375, 0.4375, 0.4375, -0.3125, -0.4375);
            }
        } catch (Throwable e) {}
    }
}
