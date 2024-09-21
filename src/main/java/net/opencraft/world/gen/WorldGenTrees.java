
package net.opencraft.world.gen;

import java.util.Random;

import net.opencraft.blocks.Block;
import net.opencraft.world.World;

public class WorldGenTrees extends WorldGenerator {

    @Override
    public boolean generate(final World fe, final Random random, final int integer3, final int integer4, final int integer5) {
        final int n = random.nextInt(3) + 4;
        int n2 = 1;
        if (integer4 < 1 || integer4 + n + 1 > 128) {
            return false;
        }
        for (int i = integer4; i <= integer4 + 1 + n; ++i) {
            int j = 1;
            if (i == integer4) {
                j = 0;
            }
            if (i >= integer4 + 1 + n - 2) {
                j = 2;
            }
            for (int blockId = integer3 - j; blockId <= integer3 + j && n2 != 0; ++blockId) {
                for (int zCoord = integer5 - j; zCoord <= integer5 + j && n2 != 0; ++zCoord) {
                    if (i >= 0 && i < 128) {
                        final int k = fe.getBlockId(blockId, i, zCoord);
                        if (k != 0 && k != Block.leaves.blockID) {
                            n2 = 0;
                        }
                    } else {
                        n2 = 0;
                    }
                }
            }
        }
        if (n2 == 0) {
            return false;
        }
        int i = fe.getBlockId(integer3, integer4 - 1, integer5);
        if ((i != Block.grass.blockID && i != Block.dirt.blockID) || integer4 >= 128 - n - 1) {
            return false;
        }
        fe.setBlock(integer3, integer4 - 1, integer5, Block.dirt.blockID);
        for (int j = integer4 - 3 + n; j <= integer4 + n; ++j) {
            final int blockId = j - (integer4 + n);
            for (int zCoord = 1 - blockId / 2, k = integer3 - zCoord; k <= integer3 + zCoord; ++k) {
                final int n3 = k - integer3;
                for (int l = integer5 - zCoord; l <= integer5 + zCoord; ++l) {
                    final int n4 = l - integer5;
                    if (Math.abs(n3) == zCoord && Math.abs(n4) == zCoord) {
                        if (random.nextInt(2) == 0) {
                            continue;
                        }
                        if (blockId == 0) {
                            continue;
                        }
                    }
                    if (!Block.opaqueCubeLookup[fe.getBlockId(k, j, l)]) {
                        fe.setBlock(k, j, l, Block.leaves.blockID);
                    }
                }
            }
        }
        for (int j = 0; j < n; ++j) {
            final int blockId = fe.getBlockId(integer3, integer4 + j, integer5);
            if (blockId == 0 || blockId == Block.leaves.blockID) {
                fe.setBlock(integer3, integer4 + j, integer5, Block.wood.blockID);
            }
        }
        return true;
    }
}
