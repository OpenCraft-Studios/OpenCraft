
package net.opencraft.block;

import java.util.Random;
import net.opencraft.block.material.Material;
import net.opencraft.physics.AABB;
import net.opencraft.world.IBlockAccess;
import net.opencraft.world.World;

public class FireBlock extends Block {

    private final int[] chanceToEncourageFire;
    private final int[] abilityToCatchFire;

    protected FireBlock(final int blockid, final int blockIndexInTexture) {
        super(blockid, blockIndexInTexture, Material.FIRE);
        this.chanceToEncourageFire = new int[256];
        this.abilityToCatchFire = new int[256];
        this.setBurnRate(Block.planks.blockID, 5, 20);
        this.setBurnRate(Block.wood.blockID, 5, 5);
        this.setBurnRate(Block.leaves.blockID, 30, 60);
        this.setBurnRate(Block.bookshelf.blockID, 30, 20);
        this.setBurnRate(Block.tnt.blockID, 15, 100);
        this.setBurnRate(Block.woolGray.blockID, 30, 60);
        this.setTickOnLoad(true);
    }

    private void setBurnRate(final int nya1, final int nya2, final int nya3) {
        this.chanceToEncourageFire[nya1] = nya2;
        this.abilityToCatchFire[nya1] = nya3;
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
        return 3;
    }

    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }

    @Override
    public int tickRate() {
        return 20;
    }

    @Override
    public void updateTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
        final int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
        if (blockMetadata < 15) {
            world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, blockMetadata + 1);
            world.scheduleBlockUpdate(xCoord, yCoord, zCoord, this.blockID);
        }
        if (!this.canNeighborCatchFire(world, xCoord, yCoord, zCoord)) {
            if (!world.isBlockNormalCube(xCoord, yCoord - 1, zCoord) || blockMetadata > 3) {
                world.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
            }
            return;
        }
        if (!this.canBlockCatchFire(world, xCoord, yCoord - 1, zCoord) && blockMetadata == 15 && random.nextInt(4) == 0) {
            world.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
            return;
        }
        if (blockMetadata % 5 == 0 && blockMetadata > 5) {
            this.tryToCatchBlockOnFire(world, xCoord + 1, yCoord, zCoord, 300, random);
            this.tryToCatchBlockOnFire(world, xCoord - 1, yCoord, zCoord, 300, random);
            this.tryToCatchBlockOnFire(world, xCoord, yCoord - 1, zCoord, 100, random);
            this.tryToCatchBlockOnFire(world, xCoord, yCoord + 1, zCoord, 200, random);
            this.tryToCatchBlockOnFire(world, xCoord, yCoord, zCoord - 1, 300, random);
            this.tryToCatchBlockOnFire(world, xCoord, yCoord, zCoord + 1, 300, random);
            for (int i = xCoord - 1; i <= xCoord + 1; ++i) {
                for (int j = zCoord - 1; j <= zCoord + 1; ++j) {
                    for (int k = yCoord - 1; k <= yCoord + 4; ++k) {
                        if (i != xCoord || k != yCoord || j != zCoord) {
                            int n = 100;
                            if (k > yCoord + 1) {
                                n += (k - (yCoord + 1)) * 100;
                            }
                            final int chanceOfNeighborsEncouragingFire = this.getChanceOfNeighborsEncouragingFire(world, i, k, j);
                            if (chanceOfNeighborsEncouragingFire > 0 && random.nextInt(n) <= chanceOfNeighborsEncouragingFire) {
                                world.setBlockWithNotify(i, k, j, this.blockID);
                            }
                        }
                    }
                }
            }
        }
    }

    private void tryToCatchBlockOnFire(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4, final Random random) {
        if (random.nextInt(nya4) < this.abilityToCatchFire[world.getBlockId(xCoord, yCoord, zCoord)]) {
            final boolean b = world.getBlockId(xCoord, yCoord, zCoord) == Block.tnt.blockID;
            if (random.nextInt(2) == 0) {
                world.setBlockWithNotify(xCoord, yCoord, zCoord, this.blockID);
            } else {
                world.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
            }
            if (b) {
                Block.tnt.onBlockDestroyedByPlayer(world, xCoord, yCoord, zCoord, 0);
            }
        }
    }

    private boolean canNeighborCatchFire(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return this.canBlockCatchFire(world, xCoord + 1, yCoord, zCoord) || this.canBlockCatchFire(world, xCoord - 1, yCoord, zCoord) || this.canBlockCatchFire(world, xCoord, yCoord - 1, zCoord) || this.canBlockCatchFire(world, xCoord, yCoord + 1, zCoord) || this.canBlockCatchFire(world, xCoord, yCoord, zCoord - 1) || this.canBlockCatchFire(world, xCoord, yCoord, zCoord + 1);
    }

    private int getChanceOfNeighborsEncouragingFire(final World world, final int xCoord, final int yCoord, final int zCoord) {
        final int nya4 = 0;
        if (world.getBlockId(xCoord, yCoord, zCoord) != 0) {
            return 0;
        }
        int nya5 = this.getChanceToEncourageFire(world, xCoord + 1, yCoord, zCoord, nya4);
        nya5 = this.getChanceToEncourageFire(world, xCoord - 1, yCoord, zCoord, nya5);
        nya5 = this.getChanceToEncourageFire(world, xCoord, yCoord - 1, zCoord, nya5);
        nya5 = this.getChanceToEncourageFire(world, xCoord, yCoord + 1, zCoord, nya5);
        nya5 = this.getChanceToEncourageFire(world, xCoord, yCoord, zCoord - 1, nya5);
        nya5 = this.getChanceToEncourageFire(world, xCoord, yCoord, zCoord + 1, nya5);
        return nya5;
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    public boolean canBlockCatchFire(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord) {
        return this.chanceToEncourageFire[blockAccess.getBlockId(xCoord, yCoord, zCoord)] > 0;
    }

    public int getChanceToEncourageFire(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        final int n = this.chanceToEncourageFire[world.getBlockId(xCoord, yCoord, zCoord)];
        if (n > nya4) {
            return n;
        }
        return nya4;
    }

    @Override
    public boolean canPlaceBlockAt(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return world.isBlockNormalCube(xCoord, yCoord - 1, zCoord) || this.canNeighborCatchFire(world, xCoord, yCoord, zCoord);
    }

    @Override
    public void onNeighborBlockChange(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        if (!world.isBlockNormalCube(xCoord, yCoord - 1, zCoord) && !this.canNeighborCatchFire(world, xCoord, yCoord, zCoord)) {
            world.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
        }
    }

    @Override
    public void onBlockAdded(final World world, final int xCoord, final int yCoord, final int zCoord) {
        if (!world.isBlockNormalCube(xCoord, yCoord - 1, zCoord) && !this.canNeighborCatchFire(world, xCoord, yCoord, zCoord)) {
            world.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
            return;
        }
        world.scheduleBlockUpdate(xCoord, yCoord, zCoord, this.blockID);
    }

    @Override
    public void randomDisplayTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
        if (random.nextInt(24) == 0) {
            world.playSoundEffect((xCoord + 0.5f), (yCoord + 0.5f), (zCoord + 0.5f), "fire.fire", 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f);
        }
        if (world.isBlockNormalCube(xCoord, yCoord - 1, zCoord) || Block.fire.canBlockCatchFire(world, xCoord, yCoord - 1, zCoord)) {
            for (int i = 0; i < 3; ++i) {
                final float n = xCoord + random.nextFloat();
                final float n2 = yCoord + random.nextFloat() * 0.5f + 0.5f;
                final float n3 = zCoord + random.nextFloat();
                world.spawnParticle("largesmoke", n, n2, n3, 0.0, 0.0, 0.0);
            }
        } else {
            if (Block.fire.canBlockCatchFire(world, xCoord - 1, yCoord, zCoord)) {
                for (int i = 0; i < 2; ++i) {
                    final float n = xCoord + random.nextFloat() * 0.1f;
                    final float n2 = yCoord + random.nextFloat();
                    final float n3 = zCoord + random.nextFloat();
                    world.spawnParticle("largesmoke", n, n2, n3, 0.0, 0.0, 0.0);
                }
            }
            if (Block.fire.canBlockCatchFire(world, xCoord + 1, yCoord, zCoord)) {
                for (int i = 0; i < 2; ++i) {
                    final float n = xCoord + 1 - random.nextFloat() * 0.1f;
                    final float n2 = yCoord + random.nextFloat();
                    final float n3 = zCoord + random.nextFloat();
                    world.spawnParticle("largesmoke", n, n2, n3, 0.0, 0.0, 0.0);
                }
            }
            if (Block.fire.canBlockCatchFire(world, xCoord, yCoord, zCoord - 1)) {
                for (int i = 0; i < 2; ++i) {
                    final float n = xCoord + random.nextFloat();
                    final float n2 = yCoord + random.nextFloat();
                    final float n3 = zCoord + random.nextFloat() * 0.1f;
                    world.spawnParticle("largesmoke", n, n2, n3, 0.0, 0.0, 0.0);
                }
            }
            if (Block.fire.canBlockCatchFire(world, xCoord, yCoord, zCoord + 1)) {
                for (int i = 0; i < 2; ++i) {
                    final float n = xCoord + random.nextFloat();
                    final float n2 = yCoord + random.nextFloat();
                    final float n3 = zCoord + 1 - random.nextFloat() * 0.1f;
                    world.spawnParticle("largesmoke", n, n2, n3, 0.0, 0.0, 0.0);
                }
            }
            if (Block.fire.canBlockCatchFire(world, xCoord, yCoord + 1, zCoord)) {
                for (int i = 0; i < 2; ++i) {
                    final float n = xCoord + random.nextFloat();
                    final float n2 = yCoord + 1 - random.nextFloat() * 0.1f;
                    final float n3 = zCoord + random.nextFloat();
                    world.spawnParticle("largesmoke", n, n2, n3, 0.0, 0.0, 0.0);
                }
            }
        }
    }
}
