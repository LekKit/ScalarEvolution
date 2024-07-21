package lekkit.scev.render.item;

import lekkit.scev.main.ScalarEvolution;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;

import net.minecraft.client.renderer.OpenGlHelper;

public class ItemModelRenderer implements IItemRenderer {
    private ResourceLocation texture;
    private WavefrontObject model;

    public ItemModelRenderer(String modelName) {
        ResourceLocation resModel = new ResourceLocation(ScalarEvolution.MODID, "models/" + modelName + ".obj");
        texture = new ResourceLocation(ScalarEvolution.MODID, "textures/blocks/" + modelName + ".png");
        model = (WavefrontObject)AdvancedModelLoader.loadModel(resModel);
    }

    public void renderDecorations(ItemRenderType type, ItemStack item) {
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        //GL11.glDisable(GL11.GL_LIGHTING);

        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glTranslatef(0F, 1.5f, 0.5f);
            GL11.glScalef(1.5f, 1.5f, 1.5f);
            GL11.glRotatef(270F, 0F, 1F, 0F);
        } else if (type == ItemRenderType.EQUIPPED) {
            GL11.glTranslatef(0F, 1F, 0F);
            GL11.glScalef(1.5f, 1.5f, 1.5f);
        } else if (type == ItemRenderType.ENTITY) {
            GL11.glTranslatef(0F, 1F, 0F);
            GL11.glScaled(1.5, 1.5, 1.5);
        }

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
        model.renderAll();

        renderDecorations(type, item);

        //GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}
