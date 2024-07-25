package lekkit.scev.main;

import lekkit.scev.tileentity.*;
import lekkit.scev.gui.*;
import lekkit.scev.container.*;
import lekkit.scev.inventory.*;

import lekkit.scev.server.IMachineHandle;
import lekkit.scev.server.IDisplayHandle;

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
                    return new ContainerComputerCase(player, (IMachineHandle)world.getTileEntity(x, y, z));
                case ScalarEvolution.GUI_MOTHERBOARD_INV:
                    return new ContainerMotherboard(player, new InventoryMotherboard(player.getHeldItem()));
                case ScalarEvolution.GUI_LAPTOP_INV:
                    // TODO
                    return new ContainerComputerCase(player, new InventoryLaptop(player.getHeldItem(), 3, 2));
                case ScalarEvolution.GUI_DISPLAY_TILE:
                    return new ContainerMachine(player, (IDisplayHandle)world.getTileEntity(x, y, z));
                case ScalarEvolution.GUI_DISPLAY_ITEM:
                    return new ContainerMachine(player, new InventoryLaptop(player.getHeldItem(), 3, 2));
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
                case ScalarEvolution.GUI_LAPTOP_INV:
                    return new GuiComputerCase((ContainerComputerCase)serverElement);
                case ScalarEvolution.GUI_DISPLAY_TILE:
                    return new GuiDisplay((ContainerMachine)serverElement);
                case ScalarEvolution.GUI_DISPLAY_ITEM:
                    return new GuiDisplay((ContainerMachine)serverElement);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}
