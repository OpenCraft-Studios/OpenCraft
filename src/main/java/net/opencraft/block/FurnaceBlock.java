
package net.opencraft.block;

import java.util.Random;
import net.opencraft.block.material.Material;
import net.opencraft.entity.EntityPlayer;
import net.opencraft.tileentity.TileEntity;
import net.opencraft.tileentity.TileEntityFurnace;
import net.opencraft.world.IBlockAccess;
import net.opencraft.world.World;

public class FurnaceBlock extends ContainerBlock {

    private final boolean isActive;

    protected FurnaceBlock(final int blockid, final boolean isActive) {
        super(blockid, Material.ROCK);
        this.isActive = isActive;
        this.blockIndexInTexture = 45;
    }

    @Override
    public void onBlockAdded(final World world, final int xCoord, final int yCoord, final int zCoord) {
        super.onBlockAdded(world, xCoord, yCoord, zCoord);
        this.setDefaultDirection(world, xCoord, yCoord, zCoord);
    }

    private void setDefaultDirection(final World world, final int xCoord, final int yCoord, final int zCoord) {
        final int blockId = world.getBlockId(xCoord, yCoord, zCoord - 1);
        final int blockId2 = world.getBlockId(xCoord, yCoord, zCoord + 1);
        final int blockId3 = world.getBlockId(xCoord - 1, yCoord, zCoord);
        final int blockId4 = world.getBlockId(xCoord + 1, yCoord, zCoord);
        int n = 3;
        if (Block.opaqueCubeLookup[blockId] && !Block.opaqueCubeLookup[blockId2]) {
            n = 3;
        }
        if (Block.opaqueCubeLookup[blockId2] && !Block.opaqueCubeLookup[blockId]) {
            n = 2;
        }
        if (Block.opaqueCubeLookup[blockId3] && !Block.opaqueCubeLookup[blockId4]) {
            n = 5;
        }
        if (Block.opaqueCubeLookup[blockId4] && !Block.opaqueCubeLookup[blockId3]) {
            n = 4;
        }
        world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, n);
    }

    @Override
    public int getBlockTextureGeneric(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord, final int metadataValue) {
        if (metadataValue == 1) {
            return Block.stone.blockIndexInTexture;
        }
        if (metadataValue == 0) {
            return Block.stone.blockIndexInTexture;
        }
        if (metadataValue != blockAccess.getBlockMetadata(xCoord, yCoord, zCoord)) {
            return this.blockIndexInTexture;
        }
        if (this.isActive) {
            return this.blockIndexInTexture + 16;
        }
        return this.blockIndexInTexture - 1;
    }

    @Override
    public void randomDisplayTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
        if (!this.isActive) {
            return;
        }
        final int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
        final float n = xCoord + 0.5f;
        final float n2 = yCoord + 0.0f + random.nextFloat() * 6.0f / 16.0f;
        final float n3 = zCoord + 0.5f;
        final float n4 = 0.52f;
        final float n5 = random.nextFloat() * 0.6f - 0.3f;
        if (blockMetadata == 4) {
            world.spawnParticle("smoke", (n - n4), n2, (n3 + n5), 0.0, 0.0, 0.0);
            world.spawnParticle("flame", (n - n4), n2, (n3 + n5), 0.0, 0.0, 0.0);
        } else if (blockMetadata == 5) {
            world.spawnParticle("smoke", (n + n4), n2, (n3 + n5), 0.0, 0.0, 0.0);
            world.spawnParticle("flame", (n + n4), n2, (n3 + n5), 0.0, 0.0, 0.0);
        } else if (blockMetadata == 2) {
            world.spawnParticle("smoke", (n + n5), n2, (n3 - n4), 0.0, 0.0, 0.0);
            world.spawnParticle("flame", (n + n5), n2, (n3 - n4), 0.0, 0.0, 0.0);
        } else if (blockMetadata == 3) {
            world.spawnParticle("smoke", (n + n5), n2, (n3 + n4), 0.0, 0.0, 0.0);
            world.spawnParticle("flame", (n + n5), n2, (n3 + n4), 0.0, 0.0, 0.0);
        }
    }

    @Override
    public int getBlockTextureFromSide(final int textureIndexSlot) {
        if (textureIndexSlot == 1) {
            return Block.stone.blockID;
        }
        if (textureIndexSlot == 0) {
            return Block.stone.blockID;
        }
        if (textureIndexSlot == 3) {
            return this.blockIndexInTexture - 1;
        }
        return this.blockIndexInTexture;
    }

    @Override
    public boolean blockActivated(final World world, final int xCoord, final int yCoord, final int zCoord, final EntityPlayer entityPlayer) {
        entityPlayer.displayGUIFurnace((TileEntityFurnace) world.getBlockTileEntity(xCoord, yCoord, zCoord));
        return true;
    }


    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityFurnace();
    }

    public static void updateFurnaceBlockState(final boolean isActive, final World world, final int xCoord, final int yCoord, final int zCoord) {
        final int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
        final TileEntity blockTileEntity = world.getBlockTileEntity(xCoord, yCoord, zCoord);
        if (isActive) {
            world.setBlockWithNotify(xCoord, yCoord, zCoord, Block.stoneOvenActive.blockID);
        } else {
            world.setBlockWithNotify(xCoord, yCoord, zCoord, Block.stoneOvenIdle.blockID);
        }
        world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, blockMetadata);
        world.setBlockTileEntity(xCoord, yCoord, zCoord, blockTileEntity);
    }
}
