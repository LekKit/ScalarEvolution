package lekkit.scev.container;

import lekkit.scev.inventory.InventoryMotherboard;
import lekkit.scev.inventory.SlotBase;

import net.minecraft.entity.player.EntityPlayer;

public class ContainerMotherboard extends ContainerBaseItem {
    protected final InventoryMotherboard inventory;

    public ContainerMotherboard(EntityPlayer player, InventoryMotherboard inventoryItem) {
        super(player, inventoryItem);
        inventory = inventoryItem;

        addSlotToContainer(new SlotBase(inventory, 0, 8, 8));
        addSlotToContainer(new SlotBase(inventory, 1, 78, 17));
        addSlotToContainer(new SlotBase(inventory, 2, 78, 53));

        addSlotToContainer(new SlotBase(inventory, 3, 102, 17));
        addSlotToContainer(new SlotBase(inventory, 4, 120, 17));
        addSlotToContainer(new SlotBase(inventory, 5, 102, 39));
        addSlotToContainer(new SlotBase(inventory, 6, 120, 39));

        addSlotToContainer(new SlotBase(inventory, 7, 54, 17));
        addSlotToContainer(new SlotBase(inventory, 8, 54, 35));
        addSlotToContainer(new SlotBase(inventory, 9, 54, 53));

        addSlotToContainer(new SlotBase(inventory, 10, 36, 17));
        addSlotToContainer(new SlotBase(inventory, 11, 36, 35));
        addSlotToContainer(new SlotBase(inventory, 12, 36, 53));
    }
}
