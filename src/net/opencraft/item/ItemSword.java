
package net.opencraft.item;

import net.opencraft.block.Block;
import net.opencraft.entity.Entity;
import net.opencraft.entity.EntityLiving;

public class ItemSword extends Item {

    private int weaponDamage;

    public ItemSword(final int itemid, final int toolTier) {
        super(itemid);
        this.maxStackSize = 1;
        this.maxDamage = 32 << toolTier;
        this.weaponDamage = 4 + toolTier * 2;
    }

    @Override
    public float getStrVsBlock(final ItemStack hw, final Block gs) {
        return 1.5f;
    }

    @Override
    public void hitEntity(final ItemStack hw, final EntityLiving ka) {
        hw.damageItem(1);
    }

    @Override
    public void onBlockDestroyed(final ItemStack hw, final int integer2, final int integer3, final int integer4, final int integer5) {
        hw.damageItem(2);
    }

    @Override
    public int getDamageVsEntity(final Entity eq) {
        return this.weaponDamage;
    }
}
