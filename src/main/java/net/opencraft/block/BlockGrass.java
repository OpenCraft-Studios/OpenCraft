
package net.opencraft.block;

import java.util.Random;
import net.opencraft.block.material.Material;
import net.opencraft.world.World;

public class BlockGrass extends Block {

    protected BlockGrass(final int blockid) {
        super(blockid, Material.ground);
        this.blockIndexInTexture = 3;
        this.setTickOnLoad(true);
    }

    @Override
    public int getBlockTextureFromSide(final int textureIndexSlot) {
        if (textureIndexSlot == 1) {
            return 0;
        }
        if (textureIndexSlot == 0) {
            return 2;
        }
        return 3;
    }

    @Override
    public void updateTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
        if (world.getBlockLightValue(xCoord, yCoord + 1, zCoord) < 4 && world.getBlockMaterial(xCoord, yCoord + 1, zCoord).getCanBlockGrass()) {
            if (random.nextInt(4) != 0) {
                return;
            }
            world.setBlockWithNotify(xCoord, yCoord, zCoord, Block.dirt.blockID);
        } else if (world.getBlockLightValue(xCoord, yCoord + 1, zCoord) >= 9) {
            final int n = xCoord + random.nextInt(3) - 1;
            final int n2 = yCoord + random.nextInt(5) - 3;
            final int n3 = zCoord + random.nextInt(3) - 1;
            if (world.getBlockId(n, n2, n3) == Block.dirt.blockID && world.getBlockLightValue(n, n2 + 1, n3) >= 4 && !world.getBlockMaterial(n, n2 + 1, n3).getCanBlockGrass()) {
                world.setBlockWithNotify(n, n2, n3, Block.grass.blockID);
            }
        }
    }

    @Override
    public int idDropped(final int blockid, final Random random) {
        return Block.dirt.idDropped(0, random);
    }
}
