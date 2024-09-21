
package net.ncraft.item;

import net.ncraft.entity.Entity;
import net.ncraft.entity.EntityArrow;
import net.ncraft.entity.EntityPlayer;
import net.ncraft.world.World;

public class ItemBow extends Item {

    public ItemBow(final int itemid) {
        super(itemid);
        this.maxStackSize = 1;
    }

    @Override
    public ItemStack onItemRightClick(final ItemStack hw, final World fe, final EntityPlayer gi) {
        if (gi.inventory.consumeInventoryItem(Item.arrow.shiftedIndex)) {
            fe.playSoundAtEntity((Entity) gi, "random.bow", 1.0f, 1.0f / (ItemBow.itemRand.nextFloat() * 0.4f + 0.8f));
            fe.entityJoinedWorld((Entity) new EntityArrow(fe, gi));
        }
        return hw;
    }
}
