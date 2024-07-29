package lekkit.scev.entity.item;

import lekkit.scev.items.ItemBase;
import lekkit.scev.items.ItemBlockBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityItemBase extends EntityItem {
    protected boolean destroyed = false;

    public EntityItemBase(World world, double x, double y, double z, ItemStack stack) {
        super(world, x, y, z, stack);
    }

    public EntityItemBase(World world, Entity entityItem, ItemStack stack) {
        super(world, entityItem.posX, entityItem.posY, entityItem.posZ, stack);

        this.motionX = entityItem.motionX;
        this.motionY = entityItem.motionY;
        this.motionZ = entityItem.motionZ;
        this.delayBeforeCanPickup = 10;

        entityItem.setDead();
    }


    protected ItemBase getItemBase() {
        Item item = getEntityItem().getItem();
        if (item instanceof ItemBase) {
            return (ItemBase)item;
        }
        return null;
    }

    protected ItemBlockBase getItemBlockBase() {
        Item item = getEntityItem().getItem();
        if (item instanceof ItemBlockBase) {
            return (ItemBlockBase)item;
        }
        return null;
    }

    public void destroyItem() {
        if (getItemBase() != null) {
            getItemBase().onItemDestroyed(getEntityItem());
        } else if (getItemBlockBase() != null) {
            getItemBlockBase().onItemDestroyed(getEntityItem());
        }
    }

    @Override
    public boolean isEntityInvulnerable() {
        if (getItemBase() != null) {
            return !getItemBase().isItemDestructible(getEntityItem());
        } else if (getItemBlockBase() != null) {
            return !getItemBlockBase().isItemDestructible(getEntityItem());
        }
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        if (isEntityInvulnerable()) {
            return false;
        }

        if (!destroyed) {
            destroyed = true;
            destroyItem();
        }

        super.attackEntityFrom(source, damage);
        setDead();

        return false;
    }
}
