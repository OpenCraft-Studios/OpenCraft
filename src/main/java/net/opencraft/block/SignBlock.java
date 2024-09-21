
package net.opencraft.block;

import java.util.Random;
import net.opencraft.block.material.Material;
import net.opencraft.tileentity.TileEntity;
import net.opencraft.world.World;

public class SignBlock extends ContainerBlock {

    private final Class signEntityClass;
    private final int isFreestanding;

    protected SignBlock(final int blockid, final Class class2, final int standing) {
        super(blockid, Material.WOOD);
        this.blockIndexInTexture = 4;
        this.signEntityClass = class2;
        final float n = 0.25f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 1.625f, 0.5f + n);
        this.isFreestanding = standing;
    }

    @Override
    public int getRenderType() {
        return -1;
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
    protected TileEntity getBlockEntity() {
        try {
            return (TileEntity) this.signEntityClass.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean canPlaceBlockAt(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return super.canPlaceBlockAt(world, xCoord, yCoord, zCoord) && super.canPlaceBlockAt(world, xCoord, yCoord + 1, zCoord);
    }

    @Override
    public int idDropped(final int blockid, final Random random) {
        return this.isFreestanding;
    }

    @Override
    public void onNeighborBlockChange(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        if (!world.isBlockNormalCube(xCoord, yCoord - 1, zCoord)) {
            this.dropBlockAsItem(world, xCoord, yCoord, zCoord, world.getBlockMetadata(xCoord, yCoord, zCoord));
            world.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
        }
    }
}
