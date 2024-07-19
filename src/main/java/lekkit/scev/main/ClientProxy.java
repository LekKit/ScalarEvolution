package lekkit.scev.main;

import lekkit.scev.render.item.*;
import lekkit.scev.render.tileentity.*;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.client.IItemRenderer;

import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenderers() {
        registerTileRenderer("vt100", new TileVT100Renderer(), new ItemModelRenderer("vt100"));
        registerTileRenderer("workstation", new TileModelRenderer("workstation", true), new ItemModelRenderer("workstation"));
        registerTileRenderer("powermark", new TileModelRenderer("powermark", true), new ItemModelRenderer("powermark"));
        registerTileRenderer("tinkerpad", new TileTinkerpadRenderer(), new ItemTinkerpadRenderer());
        registerTileRenderer("crt_monitor", new TileModelRenderer("crt_monitor", false), new ItemModelRenderer("crt_monitor"));
        registerTileRenderer("keyboard", new TileModelRenderer("keyboard", true), new ItemModelRenderer("keyboard"));
        registerTileRenderer("keyboard_mouse", new TileModelRenderer("keyboard_mouse", true), new ItemModelRenderer("keyboard_mouse"));
    }

    public void registerTileRenderer(String name, TileEntitySpecialRenderer teRenderer, IItemRenderer itemRenderer) {
        ClientRegistry.bindTileEntitySpecialRenderer(ScalarEvolution.getTileClass(name), teRenderer);
        MinecraftForgeClient.registerItemRenderer(ScalarEvolution.getItemBlock(name), itemRenderer);
    }

    public void registerItemRenderer(String name, IItemRenderer itemRenderer) {
        MinecraftForgeClient.registerItemRenderer(ScalarEvolution.getItem(name), itemRenderer);
    }
}
