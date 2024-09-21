
package net.opencraft.block;

import java.util.Random;
import net.opencraft.block.material.Material;
import net.opencraft.entity.EntityFallingSand;
import net.opencraft.world.World;

public class SandBlock extends Block {

    public static boolean fallInstantly;

    static {
        SandBlock.fallInstantly = false;
    }

    public SandBlock(final int blockid, final int blockIndexInTexture) {
        super(blockid, blockIndexInTexture, Material.SAND);
    }

    @Override
    public void onBlockAdded(final World world, final int xCoord, final int yCoord, final int zCoord) {
        world.scheduleBlockUpdate(xCoord, yCoord, zCoord, this.blockID);
    }

    @Override
    public void onNeighborBlockChange(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        world.scheduleBlockUpdate(xCoord, yCoord, zCoord, this.blockID);
    }

    @Override
    public void updateTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
        this.tryToFall(world, xCoord, yCoord, zCoord);
    }

    private void tryToFall(final World world, final int xCoord, final int yCoord, final int zCoord) {
        if (canFallBelow(world, xCoord, yCoord - 1, zCoord) && yCoord >= 0) {
            final EntityFallingSand entity = new EntityFallingSand(world, xCoord + 0.5f, yCoord + 0.5f, zCoord + 0.5f, this.blockID);
            if (SandBlock.fallInstantly) {
                while (!entity.isDead) {
                    entity.onUpdate();
                }
            } else {
                world.entityJoinedWorld(entity);
            }
        }
    }

    @Override
    public int tickRate() {
        return 3;
    }

    public static boolean canFallBelow(final World world, final int xCoord, final int yCoord, final int zCoord) {
        final int blockId = world.getBlockId(xCoord, yCoord, zCoord);
        if (blockId == 0) {
            return true;
        }
        if (blockId == Block.fire.blockID) {
            return true;
        }
        final Material blockMaterial = Block.blocksList[blockId].blockMaterial;
        return blockMaterial == Material.WATER || blockMaterial == Material.LAVA;
    }

}
