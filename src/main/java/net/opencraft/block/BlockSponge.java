
package net.opencraft.block;

import net.opencraft.block.material.Material;
import net.opencraft.world.World;

public class BlockSponge extends Block {

    protected BlockSponge(final int integer) {
        super(integer, Material.sponge);
        this.blockIndexInTexture = 48;
    }

    @Override
    public void onBlockAdded(final World world, final int xCoord, final int yCoord, final int zCoord) {
        for (int n = 2, i = xCoord - n; i <= xCoord + n; ++i) {
            for (int j = yCoord - n; j <= yCoord + n; ++j) {
                for (int k = zCoord - n; k <= zCoord + n; ++k) {
                    if (world.getBlockMaterial(i, j, k) == Material.water) {
                    }
                }
            }
        }
    }

    @Override
    public void onBlockRemoval(final World world, final int xCoord, final int yCoord, final int zCoord) {
        for (int n = 2, i = xCoord - n; i <= xCoord + n; ++i) {
            for (int j = yCoord - n; j <= yCoord + n; ++j) {
                for (int k = zCoord - n; k <= zCoord + n; ++k) {
                    world.notifyBlocksOfNeighborChange(i, j, k, world.getBlockId(i, j, k));
                }
            }
        }
    }
}
