
package net.opencraft.item;

import net.opencraft.entity.EntityPlayer;
import net.opencraft.util.Mth;
import net.opencraft.world.World;
import net.opencraft.block.Block;

public class ItemDoor extends Item {

    public ItemDoor(int itemid) {
        super(itemid);
        this.maxDamage = 64;
        this.maxStackSize = 1;
    }

    public boolean onItemUse(ItemStack hw2, EntityPlayer gi2, World fe2, int xCoord, int yCoord, int zCoord, int n4) {
        if (n4 != 1) {
            return false;
        }
        if (!Block.door.canPlaceBlockAt(fe2, xCoord, ++yCoord, zCoord)) {
            return false;
        }
        int n5 = Mth.floor_double((double) ((gi2.rotationYaw + 180.0f) * 4.0f / 360.0f) - 0.5) & 3;
        int n6 = 0;
        int n7 = 0;
        if (n5 == 0) {
            n7 = 1;
        }
        if (n5 == 1) {
            n6 = -1;
        }
        if (n5 == 2) {
            n7 = -1;
        }
        if (n5 == 3) {
            n6 = 1;
        }
        int n8 = (fe2.isBlockNormalCube(xCoord - n6, yCoord, zCoord - n7) ? 1 : 0) + (fe2.isBlockNormalCube(xCoord - n6, yCoord + 1, zCoord - n7) ? 1 : 0);
        int n9 = (fe2.isBlockNormalCube(xCoord + n6, yCoord, zCoord + n7) ? 1 : 0) + (fe2.isBlockNormalCube(xCoord + n6, yCoord + 1, zCoord + n7) ? 1 : 0);
        boolean bl = fe2.getBlockId(xCoord - n6, yCoord, zCoord - n7) == Block.door.blockID || fe2.getBlockId(xCoord - n6, yCoord + 1, zCoord - n7) == Block.door.blockID;
        boolean bl2 = fe2.getBlockId(xCoord + n6, yCoord, zCoord + n7) == Block.door.blockID || fe2.getBlockId(xCoord + n6, yCoord + 1, zCoord + n7) == Block.door.blockID;
        boolean bl3 = false;
        if (bl && !bl2) {
            bl3 = true;
        } else if (n9 > n8) {
            bl3 = true;
        }
        if (bl3) {
            n5 = n5 - 1 & 3;
            n5 += 4;
        }
        fe2.setBlockWithNotify(xCoord, yCoord, zCoord, Block.door.blockID);
        fe2.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, n5);
        fe2.setBlockWithNotify(xCoord, yCoord + 1, zCoord, Block.door.blockID);
        fe2.setBlockMetadataWithNotify(xCoord, yCoord + 1, zCoord, n5 + 8);
        --hw2.stackSize;
        return true;
    }
}
