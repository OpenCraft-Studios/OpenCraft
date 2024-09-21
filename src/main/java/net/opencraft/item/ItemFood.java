
package net.opencraft.item;

import net.opencraft.entity.EntityPlayer;
import net.opencraft.world.World;

public class ItemFood extends Item {

    private int healAmount;

    public ItemFood(final int itemid, final int healAmount) {
        super(itemid);
        this.healAmount = healAmount;
        this.maxStackSize = 64;
    }

    @Override
    public ItemStack onItemRightClick(final ItemStack hw, final World fe, final EntityPlayer gi) {
        if (gi.health <= 20) {
            --hw.stackSize;
            gi.heal(this.healAmount);
            return hw;
        } else {
            return hw;
        }
    }
}
