
package net.opencraft.blocks;

import java.util.*;

import net.opencraft.entity.Entity;
import net.opencraft.entity.EntityPlayer;
import net.opencraft.physics.AABB;
import net.opencraft.util.Vec3;
import net.opencraft.world.IBlockAccess;
import net.opencraft.world.World;

public class StairBlock extends Block {

    private final Block modelBlock;

    protected StairBlock(final int blockid, final Block block) {
        super(blockid, block.blockIndexInTexture, block.blockMaterial);
        this.modelBlock = block;
        this.setHardness(block.blockHardness);
        this.setResistance(block.blockResistance / 3.0f);
        this.setStepSound(block.stepSound);
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
        return 10;
    }

    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        return super.shouldSideBeRendered(blockAccess, xCoord, yCoord, zCoord, nya4);
    }

    @Override
    public void getCollidingBoundingBoxes(World world, int xCoord, int yCoord, final int zCoord, AABB aabb, List<AABB> arrayList) {
        final int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
        if (blockMetadata == 0) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.5f, 0.5f, 1.0f);
            super.getCollidingBoundingBoxes(world, xCoord, yCoord, zCoord, aabb, arrayList);
            this.setBlockBounds(0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            super.getCollidingBoundingBoxes(world, xCoord, yCoord, zCoord, aabb, arrayList);
        } else if (blockMetadata == 1) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.5f, 1.0f, 1.0f);
            super.getCollidingBoundingBoxes(world, xCoord, yCoord, zCoord, aabb, arrayList);
            this.setBlockBounds(0.5f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
            super.getCollidingBoundingBoxes(world, xCoord, yCoord, zCoord, aabb, arrayList);
        } else if (blockMetadata == 2) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f);
            super.getCollidingBoundingBoxes(world, xCoord, yCoord, zCoord, aabb, arrayList);
            this.setBlockBounds(0.0f, 0.0f, 0.5f, 1.0f, 1.0f, 1.0f);
            super.getCollidingBoundingBoxes(world, xCoord, yCoord, zCoord, aabb, arrayList);
        } else if (blockMetadata == 3) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f);
            super.getCollidingBoundingBoxes(world, xCoord, yCoord, zCoord, aabb, arrayList);
            this.setBlockBounds(0.0f, 0.0f, 0.5f, 1.0f, 0.5f, 1.0f);
            super.getCollidingBoundingBoxes(world, xCoord, yCoord, zCoord, aabb, arrayList);
        }
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void onNeighborBlockChange(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        if (world.getBlockMaterial(xCoord, yCoord + 1, zCoord).isSolid()) {
            world.setBlockWithNotify(xCoord, yCoord, zCoord, this.modelBlock.blockID);
        } else {
            this.g(world, xCoord, yCoord, zCoord);
            this.g(world, xCoord + 1, yCoord - 1, zCoord);
            this.g(world, xCoord - 1, yCoord - 1, zCoord);
            this.g(world, xCoord, yCoord - 1, zCoord - 1);
            this.g(world, xCoord, yCoord - 1, zCoord + 1);
            this.g(world, xCoord + 1, yCoord + 1, zCoord);
            this.g(world, xCoord - 1, yCoord + 1, zCoord);
            this.g(world, xCoord, yCoord + 1, zCoord - 1);
            this.g(world, xCoord, yCoord + 1, zCoord + 1);
        }
        this.modelBlock.onNeighborBlockChange(world, xCoord, yCoord, zCoord, nya4);
    }

    private void g(final World world, final int xCoord, final int yCoord, final int zCoord) {
        if (!this.i(world, xCoord, yCoord, zCoord)) {
            return;
        }
        int metadataValue = -1;
        if (this.i(world, xCoord + 1, yCoord + 1, zCoord)) {
            metadataValue = 0;
        }
        if (this.i(world, xCoord - 1, yCoord + 1, zCoord)) {
            metadataValue = 1;
        }
        if (this.i(world, xCoord, yCoord + 1, zCoord + 1)) {
            metadataValue = 2;
        }
        if (this.i(world, xCoord, yCoord + 1, zCoord - 1)) {
            metadataValue = 3;
        }
        if (metadataValue < 0) {
            if (this.h(world, xCoord + 1, yCoord, zCoord) && !this.h(world, xCoord - 1, yCoord, zCoord)) {
                metadataValue = 0;
            }
            if (this.h(world, xCoord - 1, yCoord, zCoord) && !this.h(world, xCoord + 1, yCoord, zCoord)) {
                metadataValue = 1;
            }
            if (this.h(world, xCoord, yCoord, zCoord + 1) && !this.h(world, xCoord, yCoord, zCoord - 1)) {
                metadataValue = 2;
            }
            if (this.h(world, xCoord, yCoord, zCoord - 1) && !this.h(world, xCoord, yCoord, zCoord + 1)) {
                metadataValue = 3;
            }
        }
        if (metadataValue < 0) {
            if (this.i(world, xCoord - 1, yCoord - 1, zCoord)) {
                metadataValue = 0;
            }
            if (this.i(world, xCoord + 1, yCoord - 1, zCoord)) {
                metadataValue = 1;
            }
            if (this.i(world, xCoord, yCoord - 1, zCoord - 1)) {
                metadataValue = 2;
            }
            if (this.i(world, xCoord, yCoord - 1, zCoord + 1)) {
                metadataValue = 3;
            }
        }
        if (metadataValue >= 0) {
            world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, metadataValue);
        }
    }

    private boolean h(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return world.getBlockMaterial(xCoord, yCoord, zCoord).isSolid();
    }

    private boolean i(final World world, final int xCoord, final int yCoord, final int zCoord) {
        final int blockId = world.getBlockId(xCoord, yCoord, zCoord);
        return blockId != 0 && Block.blocksList[blockId].getRenderType() == 10;
    }

    @Override
    public void randomDisplayTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
        this.modelBlock.randomDisplayTick(world, xCoord, yCoord, zCoord, random);
    }

    @Override
    public void onBlockClicked(final World world, final int xCoord, final int yCoord, final int zCoord, final EntityPlayer entityPlayer) {
        this.modelBlock.onBlockClicked(world, xCoord, yCoord, zCoord, entityPlayer);
    }

    @Override
    public void onBlockDestroyedByPlayer(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        this.modelBlock.onBlockDestroyedByPlayer(world, xCoord, yCoord, zCoord, nya4);
    }

    @Override
    public float getBlockBrightness(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord) {
        return this.modelBlock.getBlockBrightness(blockAccess, xCoord, yCoord, zCoord);
    }

    @Override
    public float getExplosionResistance(final Entity entity) {
        return this.modelBlock.getExplosionResistance(entity);
    }

    @Override
    public int getRenderBlockPass() {
        return this.modelBlock.getRenderBlockPass();
    }

    @Override
    public int idDropped(final int blockid, final Random random) {
        return this.modelBlock.idDropped(blockid, random);
    }

    @Override
    public int quantityDropped(final Random random) {
        return this.modelBlock.quantityDropped(random);
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(final int textureIndexSlot, final int metadataValue) {
        return this.modelBlock.getBlockTextureFromSideAndMetadata(textureIndexSlot, metadataValue);
    }

    @Override
    public int getBlockTextureFromSide(final int textureIndexSlot) {
        return this.modelBlock.getBlockTextureFromSide(textureIndexSlot);
    }

    @Override
    public int getBlockTextureGeneric(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord, final int metadataValue) {
        return this.modelBlock.getBlockTextureGeneric(blockAccess, xCoord, yCoord, zCoord, metadataValue);
    }

    @Override
    public int tickRate() {
        return this.modelBlock.tickRate();
    }

    @Override
    public AABB getSelectedBoundingBoxFromPool(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return this.modelBlock.getSelectedBoundingBoxFromPool(world, xCoord, yCoord, zCoord);
    }

    @Override
    public void velocityToAddToEntity(final World world, final int xCoord, final int yCoord, final int zCoord, final Entity entity, final Vec3 var1) {
        this.modelBlock.velocityToAddToEntity(world, xCoord, yCoord, zCoord, entity, var1);
    }

    @Override
    public boolean isCollidable() {
        return this.modelBlock.isCollidable();
    }

    @Override
    public boolean canCollideCheck(final int nya1, final boolean boolean2) {
        return this.modelBlock.canCollideCheck(nya1, boolean2);
    }

    @Override
    public boolean canPlaceBlockAt(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return this.modelBlock.canPlaceBlockAt(world, xCoord, yCoord, zCoord);
    }

    @Override
    public void onBlockAdded(final World world, final int xCoord, final int yCoord, final int zCoord) {
        this.onNeighborBlockChange(world, xCoord, yCoord, zCoord, 0);
        this.modelBlock.onBlockAdded(world, xCoord, yCoord, zCoord);
    }

    @Override
    public void onBlockRemoval(final World world, final int xCoord, final int yCoord, final int zCoord) {
        this.modelBlock.onBlockRemoval(world, xCoord, yCoord, zCoord);
    }

    @Override
    public void dropBlockAsItemWithChance(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4, final float nya5) {
        this.modelBlock.dropBlockAsItemWithChance(world, xCoord, yCoord, zCoord, nya4, nya5);
    }

    @Override
    public void dropBlockAsItem(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        this.modelBlock.dropBlockAsItem(world, xCoord, yCoord, zCoord, nya4);
    }

    @Override
    public void onEntityWalking(final World world, final int xCoord, final int yCoord, final int zCoord, final Entity entity) {
        this.modelBlock.onEntityWalking(world, xCoord, yCoord, zCoord, entity);
    }

    @Override
    public void updateTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
        this.modelBlock.updateTick(world, xCoord, yCoord, zCoord, random);
    }

    @Override
    public boolean blockActivated(final World world, final int xCoord, final int yCoord, final int zCoord, final EntityPlayer entityPlayer) {
        return this.modelBlock.blockActivated(world, xCoord, yCoord, zCoord, entityPlayer);
    }

    @Override
    public void onBlockDestroyedByExplosion(final World world, final int xCoord, final int yCoord, final int zCoord) {
        this.modelBlock.onBlockDestroyedByExplosion(world, xCoord, yCoord, zCoord);
    }
}
