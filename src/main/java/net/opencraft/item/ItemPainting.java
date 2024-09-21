
package net.opencraft.item;

import net.opencraft.entity.EntityPainting;
import net.opencraft.entity.EntityPlayer;
import net.opencraft.world.World;

public class ItemPainting extends Item {

    public ItemPainting(final int itemid) {
        super(itemid);
        this.maxDamage = 64;
    }

    @Override
    public boolean onItemUse(final ItemStack hw, final EntityPlayer gi, final World fe, final int xCoord, final int yCoord, final int zCoord, final int integer7) {
        if (integer7 == 0) {
            return false;
        }
        if (integer7 == 1) {
            return false;
        }
        int integer8 = 0;
        if (integer7 == 4) {
            integer8 = 1;
        }
        if (integer7 == 3) {
            integer8 = 2;
        }
        if (integer7 == 5) {
            integer8 = 3;
        }
        final EntityPainting entity = new EntityPainting(fe, xCoord, yCoord, zCoord, integer8);
        if (entity.onValidSurface()) {
            fe.entityJoinedWorld(entity);
            --hw.stackSize;
        }
        return true;
    }
}
