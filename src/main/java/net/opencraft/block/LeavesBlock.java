
package net.opencraft.block;

import java.util.Random;
import net.opencraft.block.material.Material;
import net.opencraft.entity.Entity;
import net.opencraft.world.World;

public class LeavesBlock extends LeavesBaseBlock {

    private final int textureIndexSlot;

    protected LeavesBlock(final int blockid, final int blockIndexInTexture) {
        super(blockid, blockIndexInTexture, Material.LEAVES, false);
        this.textureIndexSlot = blockIndexInTexture;
        this.setTickOnLoad(true);
    }

    @Override
    public void updateTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
        if (world.getBlockMaterial(xCoord, yCoord - 1, zCoord).isSolid()) {
            return;
        }
        for (int n = 2, i = xCoord - n; i <= xCoord + n; ++i) {
            for (int j = yCoord - 1; j <= yCoord + 1; ++j) {
                for (int k = zCoord - n; k <= zCoord + n; ++k) {
                    if (world.getBlockId(i, j, k) == Block.wood.blockID) {
                        return;
                    }
                }
            }
        }
        this.dropBlockAsItem(world, xCoord, yCoord, zCoord, world.getBlockMetadata(xCoord, yCoord, zCoord));
        world.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
    }

    @Override
    public int quantityDropped(final Random random) {
        return (random.nextInt(10) == 0) ? 1 : 0;
    }

    @Override
    public int idDropped(final int blockid, final Random random) {
        return Block.sapling.blockID;
    }

    @Override
    public boolean isOpaqueCube() {
        return !this.graphicsLevel;
    }

    public void setGraphicsLevel(final boolean boolean1) {
        this.graphicsLevel = boolean1;
        this.blockIndexInTexture = this.textureIndexSlot + (boolean1 ? 0 : 1);
    }

    @Override
    public void onEntityWalking(final World world, final int xCoord, final int yCoord, final int zCoord, final Entity entity) {
        super.onEntityWalking(world, xCoord, yCoord, zCoord, entity);
    }
}
