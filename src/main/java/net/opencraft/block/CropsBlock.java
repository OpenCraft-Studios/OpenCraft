
package net.opencraft.block;

import java.util.Random;
import net.opencraft.entity.EntityItem;
import net.opencraft.item.Item;
import net.opencraft.item.ItemStack;
import net.opencraft.world.World;

public class CropsBlock extends FlowerBlock {

    protected CropsBlock(final int blockid, final int blockIndexInTexture) {
        super(blockid, blockIndexInTexture);
        this.blockIndexInTexture = blockIndexInTexture;
        this.setTickOnLoad(true);
        final float n = 0.5f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 0.25f, 0.5f + n);
    }

    @Override
    protected boolean canThisPlantGrowOnThisBlockID(final int blockid) {
        return blockid == Block.tilledField.blockID;
    }

    @Override
    public void updateTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
        super.updateTick(world, xCoord, yCoord, zCoord, random);
        if (world.getBlockLightValue(xCoord, yCoord + 1, zCoord) >= 9) {
            int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
            if (blockMetadata < 7 && random.nextInt((int) (100.0f / this.updateTick(world, xCoord, yCoord, zCoord))) == 0) {
                ++blockMetadata;
                world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, blockMetadata);
            }
        }
    }

    private float updateTick(final World world, final int xCoord, final int yCoord, final int zCoord) {
        float n = 1.0f;
        final int blockId = world.getBlockId(xCoord, yCoord, zCoord - 1);
        final int blockId2 = world.getBlockId(xCoord, yCoord, zCoord + 1);
        final int blockId3 = world.getBlockId(xCoord - 1, yCoord, zCoord);
        final int blockId4 = world.getBlockId(xCoord + 1, yCoord, zCoord);
        final int blockId5 = world.getBlockId(xCoord - 1, yCoord, zCoord - 1);
        final int blockId6 = world.getBlockId(xCoord + 1, yCoord, zCoord - 1);
        final int blockId7 = world.getBlockId(xCoord + 1, yCoord, zCoord + 1);
        final int blockId8 = world.getBlockId(xCoord - 1, yCoord, zCoord + 1);
        final boolean b = blockId3 == this.blockID || blockId4 == this.blockID;
        final boolean b2 = blockId == this.blockID || blockId2 == this.blockID;
        final boolean b3 = blockId5 == this.blockID || blockId6 == this.blockID || blockId7 == this.blockID || blockId8 == this.blockID;
        for (int i = xCoord - 1; i <= xCoord + 1; ++i) {
            for (int j = zCoord - 1; j <= zCoord + 1; ++j) {
                final int blockId9 = world.getBlockId(i, yCoord - 1, j);
                float n2 = 0.0f;
                if (blockId9 == Block.tilledField.blockID) {
                    n2 = 1.0f;
                    if (world.getBlockMetadata(i, yCoord - 1, j) > 0) {
                        n2 = 3.0f;
                    }
                }
                if (i != xCoord || j != zCoord) {
                    n2 /= 4.0f;
                }
                n += n2;
            }
        }
        if (b3 || (b && b2)) {
            n /= 2.0f;
        }
        return n;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(final int textureIndexSlot, int metadataValue) {
        if (metadataValue < 0) {
            metadataValue = 7;
        }
        return this.blockIndexInTexture + metadataValue;
    }

    @Override
    public int getRenderType() {
        return 6;
    }

    @Override
    public void onBlockDestroyedByPlayer(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        super.onBlockDestroyedByPlayer(world, xCoord, yCoord, zCoord, nya4);
        for (int i = 0; i < 3; ++i) {
            if (world.rand.nextInt(15) <= nya4) {
                final float n = 0.7f;
                final EntityItem entity = new EntityItem(world, xCoord + (world.rand.nextFloat() * n + (1.0f - n) * 0.5f), yCoord + (world.rand.nextFloat() * n + (1.0f - n) * 0.5f), zCoord + (world.rand.nextFloat() * n + (1.0f - n) * 0.5f), new ItemStack(Item.seeds));
                entity.delayBeforeCanPickup = 10;
                world.entityJoinedWorld(entity);
            }
        }
    }

    @Override
    public int idDropped(final int blockid, final Random random) {
        System.out.println(new StringBuilder().append("Get resource: ").append(blockid).toString());
        if (blockid == 7) {
            return Item.wheat.shiftedIndex;
        }
        return -1;
    }

    @Override
    public int quantityDropped(final Random random) {
        return 1;
    }
}
