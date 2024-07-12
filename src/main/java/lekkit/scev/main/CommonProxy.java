package lekkit.scev.main;

import lekkit.scev.tileentity.*;
import lekkit.scev.gui.*;
import lekkit.scev.container.*;
import lekkit.scev.inventory.*;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler {
    public void registerRenderers() {}

    @Override
    public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
        if (guiId == ScalarEvolution.GUI_WORKSTATION_INV) {
            TileEntityWorkstation te = (TileEntityWorkstation)world.getTileEntity(x, y, z);

            if (te != null) {
                return new ContainerComputerCase(player, te);
            }
        }
        if (guiId == ScalarEvolution.GUI_MOTHERBOARD_INV) {
            return new ContainerMotherboard(player, new InventoryMotherboard(player.getHeldItem()));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
        if (guiId == ScalarEvolution.GUI_WORKSTATION_INV) {
            TileEntityWorkstation te = (TileEntityWorkstation)world.getTileEntity(x, y, z);

            if (te != null) {
                return new GuiComputerCase(new ContainerComputerCase(player, te));
            }
        }
        if (guiId == ScalarEvolution.GUI_MOTHERBOARD_INV) {
            return new GuiMotherboard(new ContainerMotherboard(player, new InventoryMotherboard(player.getHeldItem())));
        }
        return null;
    }
}
