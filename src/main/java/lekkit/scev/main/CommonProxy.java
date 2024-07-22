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
        try {
            switch (guiId) {
                case ScalarEvolution.GUI_COMPUTER_CASE_INV:
                    return new ContainerComputerCase(player, (TileEntityComputerCase)world.getTileEntity(x, y, z));
                case ScalarEvolution.GUI_MOTHERBOARD_INV:
                    return new ContainerMotherboard(player, new InventoryMotherboard(player.getHeldItem()));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
        try {
            Object serverElement = getServerGuiElement(guiId, player, world, x, y, z);
            switch (guiId) {
                case ScalarEvolution.GUI_COMPUTER_CASE_INV:
                    return new GuiComputerCase((ContainerComputerCase)serverElement);
                case ScalarEvolution.GUI_MOTHERBOARD_INV:
                    return new GuiMotherboard((ContainerMotherboard)serverElement);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}
