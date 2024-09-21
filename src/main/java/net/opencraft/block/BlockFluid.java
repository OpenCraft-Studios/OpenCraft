
package net.opencraft.block;

import java.util.Random;
import net.opencraft.block.material.Material;
import net.opencraft.entity.Entity;
import net.opencraft.util.AxisAlignedBB;
import net.opencraft.util.Vec3D;
import net.opencraft.world.IBlockAccess;
import net.opencraft.world.World;

public abstract class BlockFluid extends Block {

    protected int unsure;

    protected BlockFluid(final int blockid, final Material material) {
        super(blockid, ((material == Material.lava) ? 14 : 12) * 16 + 13, material);
        this.unsure = 1;
        final float n = 0.0f;
        final float n2 = 0.0f;
        if (material == Material.lava) {
            this.unsure = 2;
        }
        this.setBlockBounds(0.0f + n2, 0.0f + n, 0.0f + n2, 1.0f + n2, 1.0f + n, 1.0f + n2);
        this.setTickOnLoad(true);
    }


    @Override
    public int getBlockTextureFromSide(final int textureIndexSlot) {
        if (textureIndexSlot == 0 || textureIndexSlot == 1) {
            return this.blockIndexInTexture;
        }
        return this.blockIndexInTexture + 1;
    }

    protected int getFlowDecay(final World world, final int xCoord, final int yCoord, final int zCoord) {
        if (world.getBlockMaterial(xCoord, yCoord, zCoord) != this.blockMaterial) {
            return -1;
        }
        return world.getBlockMetadata(xCoord, yCoord, zCoord);
    }

    protected int getEffectiveFlowDecay(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord) {
        if (blockAccess.getBlockMaterial(xCoord, yCoord, zCoord) != this.blockMaterial) {
            return -1;
        }
        int blockMetadata = blockAccess.getBlockMetadata(xCoord, yCoord, zCoord);
        if (blockMetadata >= 8) {
            blockMetadata = 0;
        }
        return blockMetadata;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean canCollideCheck(final int nya1, final boolean boolean2) {
        return boolean2 && nya1 == 0;
    }

    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        return blockAccess.getBlockMaterial(xCoord, yCoord, zCoord) != this.blockMaterial && (nya4 == 1 || super.shouldSideBeRendered(blockAccess, xCoord, yCoord, zCoord, nya4));
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return null;
    }

    @Override
    public int getRenderType() {
        return 4;
    }

    @Override
    public int idDropped(final int blockid, final Random random) {
        return 0;
    }

    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }

    private Vec3D getFlowVector(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord) {
        Vec3D vec3D = Vec3D.createVector(0.0, 0.0, 0.0);
        final int effectiveFlowDecay = this.getEffectiveFlowDecay(blockAccess, xCoord, yCoord, zCoord);
        for (int i = 0; i < 4; ++i) {
            int n = xCoord;
            int n2 = zCoord;
            if (i == 0) {
                --n;
            }
            if (i == 1) {
                --n2;
            }
            if (i == 2) {
                ++n;
            }
            if (i == 3) {
                ++n2;
            }
            int n3 = this.getEffectiveFlowDecay(blockAccess, n, yCoord, n2);
            if (n3 < 0) {
                n3 = this.getEffectiveFlowDecay(blockAccess, n, yCoord - 1, n2);
                if (n3 >= 0) {
                    final int n4 = n3 - (effectiveFlowDecay - 8);
                    vec3D = vec3D.addVector((n - xCoord) * n4, (yCoord - yCoord) * n4, (n2 - zCoord) * n4);
                }
            } else if (n3 >= 0) {
                final int n4 = n3 - effectiveFlowDecay;
                vec3D = vec3D.addVector((n - xCoord) * n4, (yCoord - yCoord) * n4, (n2 - zCoord) * n4);
            }
        }
        if (blockAccess.getBlockMetadata(xCoord, yCoord, zCoord) >= 8) {
            int n5 = 0;
            if (n5 != 0 || this.shouldSideBeRendered(blockAccess, xCoord, yCoord, zCoord - 1, 2)) {
                n5 = 1;
            }
            if (n5 != 0 || this.shouldSideBeRendered(blockAccess, xCoord, yCoord, zCoord + 1, 3)) {
                n5 = 1;
            }
            if (n5 != 0 || this.shouldSideBeRendered(blockAccess, xCoord - 1, yCoord, zCoord, 4)) {
                n5 = 1;
            }
            if (n5 != 0 || this.shouldSideBeRendered(blockAccess, xCoord + 1, yCoord, zCoord, 5)) {
                n5 = 1;
            }
            if (n5 != 0 || this.shouldSideBeRendered(blockAccess, xCoord, yCoord + 1, zCoord - 1, 2)) {
                n5 = 1;
            }
            if (n5 != 0 || this.shouldSideBeRendered(blockAccess, xCoord, yCoord + 1, zCoord + 1, 3)) {
                n5 = 1;
            }
            if (n5 != 0 || this.shouldSideBeRendered(blockAccess, xCoord - 1, yCoord + 1, zCoord, 4)) {
                n5 = 1;
            }
            if (n5 != 0 || this.shouldSideBeRendered(blockAccess, xCoord + 1, yCoord + 1, zCoord, 5)) {
                n5 = 1;
            }
            if (n5 != 0) {
                vec3D = vec3D.normalize().addVector(0.0, -6.0, 0.0);
            }
        }
        return vec3D.normalize();
    }

    @Override
    public void velocityToAddToEntity(final World world, final int xCoord, final int yCoord, final int zCoord, final Entity entity, final Vec3D var1) {
        final Vec3D flowVector = this.getFlowVector(world, xCoord, yCoord, zCoord);
        var1.xCoord += flowVector.xCoord;
        var1.yCoord += flowVector.yCoord;
        var1.zCoord += flowVector.zCoord;
    }

    @Override
    public int tickRate() {
        if (this.blockMaterial == Material.water) {
            return 5;
        }
        if (this.blockMaterial == Material.lava) {
            return 30;
        }
        return 0;
    }

    @Override
    public float getBlockBrightness(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord) {
        final float lightBrightness = blockAccess.getLightBrightness(xCoord, yCoord, zCoord);
        final float lightBrightness2 = blockAccess.getLightBrightness(xCoord, yCoord + 1, zCoord);
        return (lightBrightness > lightBrightness2) ? lightBrightness : lightBrightness2;
    }

    @Override
    public void updateTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
        super.updateTick(world, xCoord, yCoord, zCoord, random);
    }

    @Override
    public int getRenderBlockPass() {
        return (this.blockMaterial == Material.water) ? 1 : 0;
    }

    @Override
    public void randomDisplayTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
        if (this.blockMaterial == Material.water && random.nextInt(64) == 0) {
            final int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
            if (blockMetadata > 0 && blockMetadata < 8) {
                world.playSoundEffect((xCoord + 0.5f), (yCoord + 0.5f), (zCoord + 0.5f), "liquid.water", random.nextFloat() * 0.25f + 0.75f, random.nextFloat() * 1.0f + 0.5f);
            }
        }
        if (this.blockMaterial == Material.lava && world.getBlockMaterial(xCoord, yCoord + 1, zCoord) == Material.air && !world.isBlockNormalCube(xCoord, yCoord + 1, zCoord) && random.nextInt(100) == 0) {
            world.spawnParticle("lava", (xCoord + random.nextFloat()), yCoord + this.maxY, (zCoord + random.nextFloat()), 0.0, 0.0, 0.0);
        }
    }


    @Override
    public void onBlockAdded(final World world, final int xCoord, final int yCoord, final int zCoord) {
        this.checkForHarden(world, xCoord, yCoord, zCoord);
    }

    @Override
    public void onNeighborBlockChange(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        this.checkForHarden(world, xCoord, yCoord, zCoord);
    }

    private void checkForHarden(final World world, final int xCoord, final int yCoord, final int zCoord) {
        if (world.getBlockId(xCoord, yCoord, zCoord) != this.blockID) {
            return;
        }
        if (this.blockMaterial == Material.lava) {
            int n = 0;
            if (n != 0 || world.getBlockMaterial(xCoord, yCoord, zCoord - 1) == Material.water) {
                n = 1;
            }
            if (n != 0 || world.getBlockMaterial(xCoord, yCoord, zCoord + 1) == Material.water) {
                n = 1;
            }
            if (n != 0 || world.getBlockMaterial(xCoord - 1, yCoord, zCoord) == Material.water) {
                n = 1;
            }
            if (n != 0 || world.getBlockMaterial(xCoord + 1, yCoord, zCoord) == Material.water) {
                n = 1;
            }
            if (n != 0 || world.getBlockMaterial(xCoord, yCoord + 1, zCoord) == Material.water) {
                n = 1;
            }
            if (n != 0) {
                final int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
                if (blockMetadata == 0) {
                    world.setBlockWithNotify(xCoord, yCoord, zCoord, Block.obsidian.blockID);
                } else if (blockMetadata <= 4) {
                    world.setBlockWithNotify(xCoord, yCoord, zCoord, Block.cobblestone.blockID);
                }
                this.triggerLavaMixEffects(world, xCoord, yCoord, zCoord);
            }
        }
    }

    protected void triggerLavaMixEffects(final World world, final int xCoord, final int yCoord, final int zCoord) {
        world.playSoundEffect((xCoord + 0.5f), (yCoord + 0.5f), (zCoord + 0.5f), "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
        for (int i = 0; i < 8; ++i) {
            world.spawnParticle("largesmoke", xCoord + Math.random(), yCoord + 1.2, zCoord + Math.random(), 0.0, 0.0, 0.0);
        }
    }

    public static float getPercentAir(int fluidSize) {
        if (fluidSize >= 8) {
            fluidSize = 0;
        }
        return (fluidSize + 1) / 9.0f;
    }

    public static double getFlowDirection(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord, final Material material) {
        Vec3D vec3D = null;
        if (material == Material.water) {
            vec3D = ((BlockFluid) Block.waterMoving).getFlowVector(blockAccess, xCoord, yCoord, zCoord);
        }
        if (material == Material.lava) {
            vec3D = ((BlockFluid) Block.lavaMoving).getFlowVector(blockAccess, xCoord, yCoord, zCoord);
        }
        if (vec3D.xCoord == 0.0 && vec3D.zCoord == 0.0) {
            return -1000.0;
        }
        return Math.atan2(vec3D.zCoord, vec3D.xCoord) - 1.5707963267948966;
    }
}
