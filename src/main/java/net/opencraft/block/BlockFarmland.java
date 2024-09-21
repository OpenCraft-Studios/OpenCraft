
package net.opencraft.block;

import java.util.Random;
import net.opencraft.block.material.Material;
import net.opencraft.entity.Entity;
import net.opencraft.util.AxisAlignedBB;
import net.opencraft.world.World;

public class BlockFarmland extends Block {

    protected BlockFarmland(final int blockid) {
        super(blockid, Material.ground);
        this.blockIndexInTexture = 87;
        this.setTickOnLoad(true);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.9375f, 1.0f);
        this.setLightOpacity(255);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return AxisAlignedBB.getBoundingBoxFromPool(xCoord + 0, yCoord + 0, zCoord + 0, xCoord + 1, yCoord + 1, zCoord + 1);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(final int textureIndexSlot, final int metadataValue) {
        if (textureIndexSlot == 1 && metadataValue > 0) {
            return this.blockIndexInTexture - 1;
        }
        if (textureIndexSlot == 1) {
            return this.blockIndexInTexture;
        }
        return 2;
    }

    @Override
    public void updateTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
        if (random.nextInt(5) == 0) {
            if (this.isWaterNearby(world, xCoord, yCoord, zCoord)) {
                world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 7);
            } else {
                final int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
                if (blockMetadata > 0) {
                    world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, blockMetadata - 1);
                } else if (!this.isCropsNearby(world, xCoord, yCoord, zCoord)) {
                    world.setBlockWithNotify(xCoord, yCoord, zCoord, Block.dirt.blockID);
                }
            }
        }
    }

    @Override
    public void onEntityWalking(final World world, final int xCoord, final int yCoord, final int zCoord, final Entity entity) {
        if (world.rand.nextInt(4) == 0) {
            world.setBlockWithNotify(xCoord, yCoord, zCoord, Block.dirt.blockID);
        }
    }

    private boolean isCropsNearby(final World world, final int xCoord, final int yCoord, final int zCoord) {
        for (int n = 0, i = xCoord - n; i <= xCoord + n; ++i) {
            for (int j = zCoord - n; j <= zCoord + n; ++j) {
                if (world.getBlockId(i, yCoord + 1, j) == Block.crops.blockID) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isWaterNearby(final World world, final int xCoord, final int yCoord, final int zCoord) {
        for (int i = xCoord - 4; i <= xCoord + 4; ++i) {
            for (int j = yCoord; j <= yCoord + 1; ++j) {
                for (int k = zCoord - 4; k <= zCoord + 4; ++k) {
                    if (world.getBlockMaterial(i, j, k) == Material.water) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onNeighborBlockChange(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        super.onNeighborBlockChange(world, xCoord, yCoord, zCoord, nya4);
        if (world.getBlockMaterial(xCoord, yCoord + 1, zCoord).isSolid()) {
            world.setBlockWithNotify(xCoord, yCoord, zCoord, Block.dirt.blockID);
        }
    }

    @Override
    public int idDropped(final int blockid, final Random random) {
        return Block.dirt.idDropped(0, random);
    }
}
