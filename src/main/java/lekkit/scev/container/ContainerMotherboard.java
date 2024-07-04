package lekkit.scev.container;

import lekkit.scev.inventory.*;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.Slot;

public class ContainerMotherboard extends ContainerBase {
    protected final InventoryMotherboard inventory;

    public ContainerMotherboard(EntityPlayer player, InventoryMotherboard inventoryItem) {
        super(player);
        inventory = inventoryItem;

        addSlotToContainer(new SlotMotherboard(inventory, 0, 8, 8));
        addSlotToContainer(new SlotMotherboard(inventory, 1, 78, 17));
        addSlotToContainer(new SlotMotherboard(inventory, 2, 78, 53));

        addSlotToContainer(new SlotMotherboard(inventory, 3, 102, 17));
        addSlotToContainer(new SlotMotherboard(inventory, 4, 120, 17));
        addSlotToContainer(new SlotMotherboard(inventory, 5, 102, 39));
        addSlotToContainer(new SlotMotherboard(inventory, 6, 120, 39));

        addSlotToContainer(new SlotMotherboard(inventory, 7, 54, 17));
        addSlotToContainer(new SlotMotherboard(inventory, 8, 54, 35));
        addSlotToContainer(new SlotMotherboard(inventory, 9, 54, 53));

        addSlotToContainer(new SlotMotherboard(inventory, 10, 36, 17));
        addSlotToContainer(new SlotMotherboard(inventory, 11, 36, 35));
        addSlotToContainer(new SlotMotherboard(inventory, 12, 36, 53));
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return inventory.isUseableByPlayer(player);
    }

    @Override
    public ItemStack slotClick(int slot, int button, int flag, EntityPlayer player) {
        // This will prevent the player from interacting with the item that opened the inventory:
        if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItem()) {
            return null;
        }
        return super.slotClick(slot, button, flag, player);
    }
}
