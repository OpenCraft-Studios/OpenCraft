
package net.opencraft.world.gen;

import java.util.Random;
import net.opencraft.block.Block;
import net.opencraft.util.Mth;
import net.opencraft.world.World;

public class WorldGenMinable extends WorldGenerator {

    private int minableBlockId;
    private int numberOfBlocks;

    public WorldGenMinable(final int integer1, final int integer2) {
        this.minableBlockId = integer1;
        this.numberOfBlocks = integer2;
    }

    @Override
    public boolean generate(final World fe, final Random random, final int integer3, final int integer4, final int integer5) {
        final float n = random.nextFloat() * 3.1415927f;
        final double n2 = integer3 + 8 + Mth.sin(n) * this.numberOfBlocks / 8.0f;
        final double n3 = integer3 + 8 - Mth.sin(n) * this.numberOfBlocks / 8.0f;
        final double n4 = integer5 + 8 + Mth.cos(n) * this.numberOfBlocks / 8.0f;
        final double n5 = integer5 + 8 - Mth.cos(n) * this.numberOfBlocks / 8.0f;
        final double n6 = integer4 + random.nextInt(3) + 2;
        final double n7 = integer4 + random.nextInt(3) + 2;
        for (int i = 0; i <= this.numberOfBlocks; ++i) {
            final double n8 = n2 + (n3 - n2) * i / this.numberOfBlocks;
            final double n9 = n6 + (n7 - n6) * i / this.numberOfBlocks;
            final double n10 = n4 + (n5 - n4) * i / this.numberOfBlocks;
            final double n11 = random.nextDouble() * this.numberOfBlocks / 16.0;
            final double n12 = (Mth.sin(i * 3.1415927f / this.numberOfBlocks) + 1.0f) * n11 + 1.0;
            final double n13 = (Mth.sin(i * 3.1415927f / this.numberOfBlocks) + 1.0f) * n11 + 1.0;
            for (int j = (int) (n8 - n12 / 2.0); j <= (int) (n8 + n12 / 2.0); ++j) {
                for (int k = (int) (n9 - n13 / 2.0); k <= (int) (n9 + n13 / 2.0); ++k) {
                    for (int l = (int) (n10 - n12 / 2.0); l <= (int) (n10 + n12 / 2.0); ++l) {
                        final double n14 = (j + 0.5 - n8) / (n12 / 2.0);
                        final double n15 = (k + 0.5 - n9) / (n13 / 2.0);
                        final double n16 = (l + 0.5 - n10) / (n12 / 2.0);
                        if (n14 * n14 + n15 * n15 + n16 * n16 < 1.0 && fe.getBlockId(j, k, l) == Block.stone.blockID) {
                            fe.setBlock(j, k, l, this.minableBlockId);
                        }
                    }
                }
            }
        }
        return true;
    }
}
