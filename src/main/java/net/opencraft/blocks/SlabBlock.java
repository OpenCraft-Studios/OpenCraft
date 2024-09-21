
package net.opencraft.blocks;

import java.util.Random;

import net.opencraft.blocks.material.Material;
import net.opencraft.world.IBlockAccess;
import net.opencraft.world.World;

public class SlabBlock extends Block {

    private final boolean blockType;

    public SlabBlock(final int blockid, final boolean doubleSlab) {
        super(blockid, 6, Material.ROCK);
        if (!(this.blockType = doubleSlab)) {
            this.setShape(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
        this.setLightOpacity(255);
    }

    @Override
    public int getBlockTextureFromSide(final int textureIndexSlot) {
        if (textureIndexSlot <= 1) {
            return 6;
        }
        return 5;
    }

    @Override
    public boolean isOpaqueCube() {
        return this.blockType;
    }

    @Override
    public void onNeighborBlockChange(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        if (this != Block.slabSingle) {
            return;
        }
    }

    @Override
    public void onBlockAdded(final World world, final int xCoord, final int yCoord, final int zCoord) {
        if (this != Block.slabSingle) {
            super.onBlockAdded(world, xCoord, yCoord, zCoord);
        }
        if (world.getBlockId(xCoord, yCoord - 1, zCoord) == SlabBlock.slabSingle.blockID) {
            world.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
            world.setBlockWithNotify(xCoord, yCoord - 1, zCoord, Block.slabDouble.blockID);
        }
    }

    @Override
    public int idDropped(final int blockid, final Random random) {
        return Block.slabSingle.blockID;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return this.blockType;
    }

    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        if (this != Block.slabSingle) {
            super.shouldSideBeRendered(blockAccess, xCoord, yCoord, zCoord, nya4);
        }
        return nya4 == 1 || (super.shouldSideBeRendered(blockAccess, xCoord, yCoord, zCoord, nya4) && (nya4 == 0 || blockAccess.getBlockId(xCoord, yCoord, zCoord) != this.blockID));
    }
}
