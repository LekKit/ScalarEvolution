package lekkit.scev.render.block;

import lekkit.scev.main.ScalarEvolution;
import lekkit.scev.render.util.ObjTesselator;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class BlockModelRenderer implements ISimpleBlockRenderingHandler {
    protected final int renderId;
    protected final boolean fatModel;
    protected final WavefrontObject model;

    public BlockModelRenderer(String modelName, boolean fatModel) {
        ResourceLocation resModel = new ResourceLocation(ScalarEvolution.MODID, "models/" + modelName + ".obj");
        WavefrontObject model = (WavefrontObject)AdvancedModelLoader.loadModel(resModel);

        this.renderId = RenderingRegistry.getNextAvailableRenderId();
        this.model = model;
        this.fatModel = ScalarEvolution.config.fatModels ? true : fatModel;
    }

    public void tesselateItem(IIcon icon, Tessellator tes) {
        ObjTesselator.tesselateWithIcon(model, icon, tes);
    }

    public void tesselateBlock(IBlockAccess world, int x, int y, int z, IIcon icon, Tessellator tes) {
        int metadata = world.getBlockMetadata(x, y, z);
        float rotation = metadata * 22.5f;
        if (fatModel) {
            rotation = (((metadata + 2) / 4) % 4) * 90.f;
        }

        ObjTesselator.tesselateWithIcon(model, icon, tes, rotation, fatModel ? 1.f : 0.75f);
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        Tessellator tes = Tessellator.instance;
        RenderHelper.disableStandardItemLighting();
        tes.startDrawingQuads();
        tes.setColorOpaque_F(1.f, 1.f, 1.f);

        tesselateItem(block.getIcon(0, metadata), tes);

        tes.draw();
        RenderHelper.enableStandardItemLighting();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        int metadata = world.getBlockMetadata(x, y, z);
        Tessellator tes = Tessellator.instance;

        tes.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        tes.setColorOpaque_F(1.f, 1.f, 1.f);

        float tx = x + 0.5f;
        float ty = y + (fatModel ? 0.5f : 0.375f);
        float tz = z + 0.5f;

        tes.addTranslation(tx, ty, tz);

        tesselateBlock(world, x, y, z, block.getIcon(0, metadata), tes);

        tes.addTranslation(-tx, -ty, -tz);

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return renderId;
    }
}
