
package net.opencraft.item;

import net.opencraft.blocks.Block;
import net.opencraft.entity.Entity;
import net.opencraft.entity.EntityMinecart;
import net.opencraft.entity.EntityPlayer;
import net.opencraft.world.World;

public class ItemMinecart extends Item {

    public ItemMinecart(final int itemid) {
        super(itemid);
        this.maxStackSize = 1;
    }

    @Override
    public boolean onItemUse(final ItemStack hw, final EntityPlayer gi, final World fe, final int xCoord, final int yCoord, final int zCoord, final int integer7) {
        if (fe.getBlockId(xCoord, yCoord, zCoord) == Block.rail.blockID) {
            fe.entityJoinedWorld((Entity) new EntityMinecart(fe, xCoord + 0.5f, yCoord + 0.5f, zCoord + 0.5f));
            --hw.stackSize;
            return true;
        }
        return false;
    }
}
