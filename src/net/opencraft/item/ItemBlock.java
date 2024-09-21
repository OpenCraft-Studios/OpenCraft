
package net.opencraft.item;

import net.opencraft.block.Block;
import net.opencraft.entity.EntityPlayer;
import net.opencraft.world.World;

public class ItemBlock extends Item {

    private int blockID;

    public ItemBlock(final int itemid) {
        super(itemid);
        this.blockID = itemid + 256;
        this.setIconIndex(Block.blocksList[itemid + 256].getBlockTextureFromSide(2));
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
        if (hw.stackSize == 0) {
            return false;
        }
        if (fe.canBlockBePlacedAt(this.blockID, xCoord, yCoord, zCoord, false)) {
            final Block block = Block.blocksList[this.blockID];
            if (fe.setBlockWithNotify(xCoord, yCoord, zCoord, this.blockID)) {
                Block.blocksList[this.blockID].onBlockPlaced(fe, xCoord, yCoord, zCoord, integer7);
                fe.playSoundEffect(xCoord + 0.5f, yCoord + 0.5f, zCoord + 0.5f, block.stepSound.stepSoundDir2(), (block.stepSound.soundVolume() + 1.0f) / 2.0f, block.stepSound.soundPitch() * 0.8f);
                --hw.stackSize;
            }
        }
        return true;
    }
}
