
package net.opencraft.item;

import net.opencraft.blocks.Block;
import net.opencraft.entity.EntityPlayer;
import net.opencraft.tileentity.TileEntitySign;
import net.opencraft.util.Mth;
import net.opencraft.world.World;

public class ItemSign extends Item {

    public ItemSign(final int itemid) {
        super(itemid);
        this.maxDamage = 64;
        this.maxStackSize = 1;
    }

    @Override
    public boolean onItemUse(final ItemStack hw, final EntityPlayer gi, final World fe, final int xCoord, int yCoord, final int zCoord, final int integer7) {
        if (integer7 != 1) {
            return false;
        }
        ++yCoord;
        if (!Block.signPost.canPlaceBlockAt(fe, xCoord, yCoord, zCoord)) {
            return false;
        }
        fe.setBlockWithNotify(xCoord, yCoord, zCoord, Block.signPost.blockID);
        fe.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, Mth.floor_double((gi.rotationYaw + 180.0f) * 16.0f / 360.0f - 0.5) & 0xF);
        --hw.stackSize;
        gi.displayGUIEditSign((TileEntitySign) fe.getBlockTileEntity(xCoord, yCoord, zCoord));
        return true;
    }
}
