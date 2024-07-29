package lekkit.scev.container;

import lekkit.scev.inventory.InventoryMotherboard;
import lekkit.scev.inventory.SlotBase;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerMotherboard extends ContainerBaseItem {

    public ContainerMotherboard(EntityPlayer player, InventoryMotherboard inventory) {
        super(player, inventory, true);

        // CPU slot
        addSlotToContainer(new SlotBase(inventory, 0, 80, 36, "slot_cpu"));

        // Firmware flash slot
        addSlotToContainer(new SlotBase(inventory, 1, 110, 102, "slot_flash"));

        // RAM slots
        addSlotToContainer(new SlotBase(inventory, 2, 110, 24, "slot_ram"));
        addSlotToContainer(new SlotBase(inventory, 3, 110, 42, "slot_ram"));
        addSlotToContainer(new SlotBase(inventory, 4, 110, 60, "slot_ram"));
        addSlotToContainer(new SlotBase(inventory, 5, 110, 78, "slot_ram"));

        // NVMe slots
        addSlotToContainer(new SlotBase(inventory, 6, 80, 72, "slot_m2"));
        addSlotToContainer(new SlotBase(inventory, 7, 80, 90, "slot_m2"));

        // PCI slots
        addSlotToContainer(new SlotBase(inventory, 8, 44, 18, "slot_pci"));
        addSlotToContainer(new SlotBase(inventory, 9, 44, 36, "slot_pci"));
        addSlotToContainer(new SlotBase(inventory, 10, 44, 54, "slot_pci"));
        addSlotToContainer(new SlotBase(inventory, 11, 44, 72, "slot_pci"));
        addSlotToContainer(new SlotBase(inventory, 12, 44, 90, "slot_pci"));
        addSlotToContainer(new SlotBase(inventory, 13, 44, 108, "slot_pci"));
    }
}
