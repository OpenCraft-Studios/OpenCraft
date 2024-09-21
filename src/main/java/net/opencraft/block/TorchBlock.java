
package net.opencraft.block;

import java.util.Random;
import net.opencraft.block.material.Material;
import net.opencraft.client.input.MovingObjectPosition;
import net.opencraft.physics.AABB;
import net.opencraft.util.Vec3;
import net.opencraft.world.World;

public class TorchBlock extends Block {

    protected TorchBlock(final int blockid, final int blockIndexInTexture) {
        super(blockid, blockIndexInTexture, Material.REDSTONE);
        this.setTickOnLoad(true);
    }

    @Override
    public AABB getCollisionBoundingBoxFromPool(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return null;
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
    public int getRenderType() {
        return 2;
    }

    @Override
    public boolean canPlaceBlockAt(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return world.isBlockNormalCube(xCoord - 1, yCoord, zCoord) || world.isBlockNormalCube(xCoord + 1, yCoord, zCoord) || world.isBlockNormalCube(xCoord, yCoord, zCoord - 1) || world.isBlockNormalCube(xCoord, yCoord, zCoord + 1) || world.isBlockNormalCube(xCoord, yCoord - 1, zCoord);
    }

    @Override
    public void onBlockPlaced(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
        if (nya4 == 1 && world.isBlockNormalCube(xCoord, yCoord - 1, zCoord)) {
            blockMetadata = 5;
        }
        if (nya4 == 2 && world.isBlockNormalCube(xCoord, yCoord, zCoord + 1)) {
            blockMetadata = 4;
        }
        if (nya4 == 3 && world.isBlockNormalCube(xCoord, yCoord, zCoord - 1)) {
            blockMetadata = 3;
        }
        if (nya4 == 4 && world.isBlockNormalCube(xCoord + 1, yCoord, zCoord)) {
            blockMetadata = 2;
        }
        if (nya4 == 5 && world.isBlockNormalCube(xCoord - 1, yCoord, zCoord)) {
            blockMetadata = 1;
        }
        world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, blockMetadata);
    }

    @Override
    public void updateTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
        super.updateTick(world, xCoord, yCoord, zCoord, random);
        if (world.getBlockMetadata(xCoord, yCoord, zCoord) == 0) {
            this.onBlockAdded(world, xCoord, yCoord, zCoord);
        }
    }

    @Override
    public void onBlockAdded(final World world, final int xCoord, final int yCoord, final int zCoord) {
        if (world.isBlockNormalCube(xCoord - 1, yCoord, zCoord)) {
            world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1);
        } else if (world.isBlockNormalCube(xCoord + 1, yCoord, zCoord)) {
            world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 2);
        } else if (world.isBlockNormalCube(xCoord, yCoord, zCoord - 1)) {
            world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 3);
        } else if (world.isBlockNormalCube(xCoord, yCoord, zCoord + 1)) {
            world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 4);
        } else if (world.isBlockNormalCube(xCoord, yCoord - 1, zCoord)) {
            world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 5);
        }
        this.dropTorchIfCantStay(world, xCoord, yCoord, zCoord);
    }

    @Override
    public void onNeighborBlockChange(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        if (this.dropTorchIfCantStay(world, xCoord, yCoord, zCoord)) {
            final int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
            boolean b = false;
            if (!world.isBlockNormalCube(xCoord - 1, yCoord, zCoord) && blockMetadata == 1) {
                b = true;
            }
            if (!world.isBlockNormalCube(xCoord + 1, yCoord, zCoord) && blockMetadata == 2) {
                b = true;
            }
            if (!world.isBlockNormalCube(xCoord, yCoord, zCoord - 1) && blockMetadata == 3) {
                b = true;
            }
            if (!world.isBlockNormalCube(xCoord, yCoord, zCoord + 1) && blockMetadata == 4) {
                b = true;
            }
            if (!world.isBlockNormalCube(xCoord, yCoord - 1, zCoord) && blockMetadata == 5) {
                b = true;
            }
            if (b) {
                this.dropBlockAsItem(world, xCoord, yCoord, zCoord, world.getBlockMetadata(xCoord, yCoord, zCoord));
                world.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
            }
        }
    }

    private boolean dropTorchIfCantStay(final World world, final int xCoord, final int yCoord, final int zCoord) {
        if (!this.canPlaceBlockAt(world, xCoord, yCoord, zCoord)) {
            this.dropBlockAsItem(world, xCoord, yCoord, zCoord, world.getBlockMetadata(xCoord, yCoord, zCoord));
            world.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
            return false;
        }
        return true;
    }

    @Override
    public MovingObjectPosition collisionRayTrace(final World world, final int xCoord, final int yCoord, final int zCoord, final Vec3 var1, final Vec3 var2) {
        final int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
        float n = 0.15f;
        if (blockMetadata == 1) {
            this.setBlockBounds(0.0f, 0.2f, 0.5f - n, n * 2.0f, 0.8f, 0.5f + n);
        } else if (blockMetadata == 2) {
            this.setBlockBounds(1.0f - n * 2.0f, 0.2f, 0.5f - n, 1.0f, 0.8f, 0.5f + n);
        } else if (blockMetadata == 3) {
            this.setBlockBounds(0.5f - n, 0.2f, 0.0f, 0.5f + n, 0.8f, n * 2.0f);
        } else if (blockMetadata == 4) {
            this.setBlockBounds(0.5f - n, 0.2f, 1.0f - n * 2.0f, 0.5f + n, 0.8f, 1.0f);
        } else {
            n = 0.1f;
            this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 0.6f, 0.5f + n);
        }
        return super.collisionRayTrace(world, xCoord, yCoord, zCoord, var1, var2);
    }

    @Override
    public void randomDisplayTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
        final int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
        final float n = xCoord + 0.5f;
        final float n2 = yCoord + 0.7f;
        final float n3 = zCoord + 0.5f;
        final float n4 = 0.22f;
        final float n5 = 0.27f;
        if (blockMetadata == 1) {
            world.spawnParticle("smoke", (n - n5), (n2 + n4), n3, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", (n - n5), (n2 + n4), n3, 0.0, 0.0, 0.0);
        } else if (blockMetadata == 2) {
            world.spawnParticle("smoke", (n + n5), (n2 + n4), n3, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", (n + n5), (n2 + n4), n3, 0.0, 0.0, 0.0);
        } else if (blockMetadata == 3) {
            world.spawnParticle("smoke", n, (n2 + n4), (n3 - n5), 0.0, 0.0, 0.0);
            world.spawnParticle("flame", n, (n2 + n4), (n3 - n5), 0.0, 0.0, 0.0);
        } else if (blockMetadata == 4) {
            world.spawnParticle("smoke", n, (n2 + n4), (n3 + n5), 0.0, 0.0, 0.0);
            world.spawnParticle("flame", n, (n2 + n4), (n3 + n5), 0.0, 0.0, 0.0);
        } else {
            world.spawnParticle("smoke", n, n2, n3, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", n, n2, n3, 0.0, 0.0, 0.0);
        }
    }
}
