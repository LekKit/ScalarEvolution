package lekkit.scev.container;

import lekkit.scev.tileentity.TileEntityWorkstation;
import lekkit.scev.inventory.*;

import net.minecraft.entity.player.EntityPlayer;

public class ContainerWorkstation extends ContainerBase {
    public ContainerWorkstation(EntityPlayer player, TileEntityWorkstation inventory) {
        super(player, inventory);

        addSlotToContainer(new SlotMotherboard(inventory, 0, 8, 8));
        addSlotToContainer(new SlotCPU(inventory, 1, 78, 17));
        addSlotToContainer(new SlotFlash(inventory, 2, 78, 53));

        addSlotToContainer(new SlotRAM(inventory, 3, 102, 17));
        addSlotToContainer(new SlotRAM(inventory, 4, 120, 17));
        addSlotToContainer(new SlotRAM(inventory, 5, 102, 39));
        addSlotToContainer(new SlotRAM(inventory, 6, 120, 39));

        addSlotToContainer(new SlotPCI(inventory, 7, 54, 17));
        addSlotToContainer(new SlotPCI(inventory, 8, 54, 35));
        addSlotToContainer(new SlotPCI(inventory, 9, 54, 53));

        addSlotToContainer(new SlotPCI(inventory, 10, 36, 17));
        addSlotToContainer(new SlotPCI(inventory, 11, 36, 35));
        addSlotToContainer(new SlotPCI(inventory, 12, 36, 53));
    }
}
