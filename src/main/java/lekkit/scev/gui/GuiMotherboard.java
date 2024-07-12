package lekkit.scev.gui;

import lekkit.scev.main.ScalarEvolution;
import lekkit.scev.container.ContainerMotherboard;
import lekkit.scev.inventory.InventoryItem;
import lekkit.scev.items.ItemMotherboard;

public class GuiMotherboard extends GuiContainerBase {
    public GuiMotherboard(ContainerMotherboard container) {
        super(container);

        setBackgroundTexture("motherboard" + getItemMotherboard().getMotherboardLevel());
    }

    public ItemMotherboard getItemMotherboard() {
        InventoryItem invItem = (InventoryItem)getClientSideContainer().getContainerInventory();
        return (ItemMotherboard)invItem.getInventoryItemStack().getItem();
    }

    @Override
    public boolean isSlotEnabled(int slotIndex) {
        return  getItemMotherboard().isInventorySlotEnabled(slotIndex);
    }
}
