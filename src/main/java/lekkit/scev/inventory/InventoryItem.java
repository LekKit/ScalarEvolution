package lekkit.scev.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class InventoryItem implements IInventory {
    private final ItemStack item_stack;
    private final int inv_size;
    private ItemStack[] inventory;

    public InventoryItem(ItemStack stack, int size) {
        item_stack = stack;
        inv_size = size;
        inventory = new ItemStack[inv_size];

        if (!item_stack.hasTagCompound()) {
            item_stack.setTagCompound(new NBTTagCompound());
        }

        readFromNBT(item_stack.getTagCompound());
    }

    public ItemStack getInventoryItemStack() {
        return item_stack;
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
            if (stack.stackSize > amount) {
                stack = stack.splitStack(amount);
                // Don't forget this line or your inventory will not be saved!
                markDirty();
            } else {
                // this method also calls onInventoryChanged, so we don't need to call it again
                setInventorySlotContents(slot, null);
            }
        }
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = getStackInSlot(slot);
        setInventorySlotContents(slot, null);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory[slot] = stack;

        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }

        // Don't forget this line or your inventory will not be saved!
        markDirty();
    }

    @Override
    public String getInventoryName() {
        return "";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    /**
    * This is the method that will handle saving the inventory contents, as it is called (or should be called!)
    * anytime the inventory changes. Perfect. Much better than using onUpdate in an Item, as this will also
    * let you change things in your inventory without ever opening a Gui, if you want.
    */
    @Override
    public void markDirty() {
        for (int i = 0; i < getSizeInventory(); ++i) {
            if (getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0) {
                inventory[i] = null;
            }
        }

        // This line here does the work:
        writeToNBT(item_stack.getTagCompound());
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
        // Don't want to be able to store the inventory item within itself
        // Bad things will happen, like losing your inventory
        // Actually, this needs a custom Slot to work
        return itemstack.getItem() != item_stack.getItem();
    }

    public void readFromNBT(NBTTagCompound compound) {
        NBTTagList items = compound.getTagList("Items", Constants.NBT.TAG_COMPOUND);

        inventory = new ItemStack[getSizeInventory()];
        for (int i = 0; i < items.tagCount(); ++i) {
            NBTTagCompound item = (NBTTagCompound) items.getCompoundTagAt(i);
            int slot = item.getInteger("Slot");

            // Just double-checking that the saved slot index is within our inventory array bounds
            if (slot >= 0 && slot < getSizeInventory()) {
                inventory[slot] = ItemStack.loadItemStackFromNBT(item);
            }
        }
    }

    public void writeToNBT(NBTTagCompound tagcompound) {
        // Create a new NBT Tag List to store itemstacks as NBT Tags
        NBTTagList items = new NBTTagList();

        for (int i = 0; i < getSizeInventory(); ++i) {
            // Only write stacks that contain items
            if (getStackInSlot(i) != null) {
                // Make a new NBT Tag Compound to write the itemstack and slot index to
                NBTTagCompound item = new NBTTagCompound();
                item.setInteger("Slot", i);
                // Writes the itemstack in slot(i) to the Tag Compound we just made
                getStackInSlot(i).writeToNBT(item);

                // add the tag compound to our tag list
                items.appendTag(item);
            }
        }
        // Add the TagList to the ItemStack's Tag Compound with the name "Items"
        tagcompound.setTag("Items", items);
    }
}
