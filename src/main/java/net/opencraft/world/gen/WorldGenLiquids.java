
package net.opencraft.world.gen;

import java.util.Random;
import net.opencraft.block.Block;
import net.opencraft.world.World;

public class WorldGenLiquids extends WorldGenerator {

    private int liquidBlockId;

    public WorldGenLiquids(final int integer) {
        this.liquidBlockId = integer;
    }

    @Override
    public boolean generate(final World fe, final Random random, final int integer3, final int integer4, final int integer5) {
        if (fe.getBlockId(integer3, integer4 + 1, integer5) != Block.stone.blockID) {
            return false;
        }
        if (fe.getBlockId(integer3, integer4 - 1, integer5) != Block.stone.blockID) {
            return false;
        }
        if (fe.getBlockId(integer3, integer4, integer5) != 0 && fe.getBlockId(integer3, integer4, integer5) != Block.stone.blockID) {
            return false;
        }
        int n = 0;
        if (fe.getBlockId(integer3 - 1, integer4, integer5) == Block.stone.blockID) {
            ++n;
        }
        if (fe.getBlockId(integer3 + 1, integer4, integer5) == Block.stone.blockID) {
            ++n;
        }
        if (fe.getBlockId(integer3, integer4, integer5 - 1) == Block.stone.blockID) {
            ++n;
        }
        if (fe.getBlockId(integer3, integer4, integer5 + 1) == Block.stone.blockID) {
            ++n;
        }
        int n2 = 0;
        if (fe.getBlockId(integer3 - 1, integer4, integer5) == 0) {
            ++n2;
        }
        if (fe.getBlockId(integer3 + 1, integer4, integer5) == 0) {
            ++n2;
        }
        if (fe.getBlockId(integer3, integer4, integer5 - 1) == 0) {
            ++n2;
        }
        if (fe.getBlockId(integer3, integer4, integer5 + 1) == 0) {
            ++n2;
        }
        if (n == 3 && n2 == 1) {
            fe.setBlockWithNotify(integer3, integer4, integer5, this.liquidBlockId);
        }
        return true;
    }
}
