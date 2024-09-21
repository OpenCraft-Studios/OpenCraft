
package net.opencraft.item;

import net.opencraft.block.Block;
import net.opencraft.entity.EntityPlayer;
import net.opencraft.world.World;

public class ItemFlintAndSteel extends Item {

    public ItemFlintAndSteel(final int itemid) {
        super(itemid);
        this.maxStackSize = 1;
        this.maxDamage = 64;
    }

    @Override
    public boolean onItemUse(final ItemStack hw, final EntityPlayer gi, final World fe, int xCoord, int yCoord, int zCoord, final int integer7) {
        if (integer7 == 0) {
            --yCoord;
        }
        if (integer7 == 1) {
            ++yCoord;
        }
        if (integer7 == 2) {
            --zCoord;
        }
        if (integer7 == 3) {
            ++zCoord;
        }
        if (integer7 == 4) {
            --xCoord;
        }
        if (integer7 == 5) {
            ++xCoord;
        }
        if (fe.getBlockId(xCoord, yCoord, zCoord) == 0) {
            fe.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "fire.ignite", 1.0f, ItemFlintAndSteel.itemRand.nextFloat() * 0.4f + 0.8f);
            fe.setBlockWithNotify(xCoord, yCoord, zCoord, Block.fire.blockID);
        }
        hw.damageItem(1);
        return true;
    }
}
