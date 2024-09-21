
package net.opencraft.item;

import net.opencraft.block.Block;
import net.opencraft.entity.Entity;
import net.opencraft.entity.EntityLiving;

public class ItemTool extends Item {

    private Block[] blocksEffectiveAgainst;
    private float efficiencyOnProperMaterial;
    private int damageVsEntity;
    protected int toolMaterial;

    public ItemTool(final int itemid, final int integer2, final int integer3, final Block[] block) {
        super(itemid);
        this.efficiencyOnProperMaterial = 4.0f;
        this.toolMaterial = integer3;
        this.blocksEffectiveAgainst = block;
        this.maxStackSize = 1;
        this.maxDamage = 32 << integer3;
        if (integer3 == 3) {
            this.maxDamage *= 2;
        }
        this.efficiencyOnProperMaterial = (float) ((integer3 + 1) * 2);
        this.damageVsEntity = integer2 + integer3;
    }

    @Override
    public float getStrVsBlock(final ItemStack hw, final Block gs) {
        for (int i = 0; i < this.blocksEffectiveAgainst.length; ++i) {
            if (this.blocksEffectiveAgainst[i] == gs) {
                return this.efficiencyOnProperMaterial;
            }
        }
        return 1.0f;
    }

    @Override
    public void hitEntity(final ItemStack hw, final EntityLiving ka) {
        hw.damageItem(2);
    }

    @Override
    public void onBlockDestroyed(final ItemStack hw, final int integer2, final int integer3, final int integer4, final int integer5) {
        hw.damageItem(1);
    }

    @Override
    public int getDamageVsEntity(final Entity eq) {
        return this.damageVsEntity;
    }
}
