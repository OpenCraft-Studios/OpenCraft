
package net.opencraft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import net.opencraft.block.Block;
import net.opencraft.entity.Entity;
import net.opencraft.util.AxisAlignedBB;
import net.opencraft.util.MathHelper;
import net.opencraft.util.Vec3D;
import net.opencraft.world.World;
import net.opencraft.world.chunk.ChunkPosition;

public class Explosion {

    public void doExplosion(final World fe, final Entity eq, final double double3, final double double4, final double double5, float float6) {
        fe.playSoundEffect(double3, double4, double5, "random.explode", 4.0f, (1.0f + (fe.rand.nextFloat() - fe.rand.nextFloat()) * 0.2f) * 0.7f);
        final HashSet set = new HashSet();
        final float n = float6;
        for (int n2 = 16, i = 0; i < n2; ++i) {
            for (int j = 0; j < n2; ++j) {
                for (int k = 0; k < n2; ++k) {
                    if (i == 0 || i == n2 - 1 || j == 0 || j == n2 - 1 || k == 0 || k == n2 - 1) {
                        double n3 = i / (n2 - 1.0f) * 2.0f - 1.0f;
                        double n4 = j / (n2 - 1.0f) * 2.0f - 1.0f;
                        double n5 = k / (n2 - 1.0f) * 2.0f - 1.0f;
                        final double sqrt = Math.sqrt(n3 * n3 + n4 * n4 + n5 * n5);
                        n3 /= sqrt;
                        n4 /= sqrt;
                        n5 /= sqrt;
                        float n6 = float6 * (0.7f + fe.rand.nextFloat() * 0.6f);
                        double double6 = double3;
                        double double7 = double4;
                        double n7 = double5;
                        for (float n8 = 0.3f; n6 > 0.0f; n6 -= n8 * 0.75f) {
                            final int floor_double = MathHelper.floor_double(double6);
                            final int floor_double2 = MathHelper.floor_double(double7);
                            final int floor_double3 = MathHelper.floor_double(n7);
                            final int blockId = fe.getBlockId(floor_double, floor_double2, floor_double3);
                            if (blockId > 0) {
                                n6 -= (Block.blocksList[blockId].getExplosionResistance(eq) + 0.3f) * n8;
                            }
                            if (n6 > 0.0f) {
                                set.add(new ChunkPosition(floor_double, floor_double2, floor_double3));
                            }
                            double6 += n3 * n8;
                            double7 += n4 * n8;
                            n7 += n5 * n8;
                        }
                    }
                }
            }
        }
        float6 *= 2.0f;
        int i = MathHelper.floor_double(double3 - float6 - 1.0);
        int j = MathHelper.floor_double(double3 + float6 + 1.0);
        int k = MathHelper.floor_double(double4 - float6 - 1.0);
        final List entitiesWithinAABBExcludingEntity = fe.getEntitiesWithinAABBExcludingEntity(eq, AxisAlignedBB.getBoundingBoxFromPool(i, k, MathHelper.floor_double(double5 - float6 - 1.0), j, MathHelper.floor_double(double4 + float6 + 1.0), MathHelper.floor_double(double5 + float6 + 1.0)));
        final Vec3D vector = Vec3D.createVector(double3, double4, double5);
        for (int l = 0; l < entitiesWithinAABBExcludingEntity.size(); ++l) {
            final Entity entity = (Entity) entitiesWithinAABBExcludingEntity.get(l);
            final double n9 = entity.getDistance(double3, double4, double5) / float6;
            if (n9 <= 1.0) {
                double double6 = entity.posX - double3;
                double double7 = entity.posY - double4;
                double n7 = entity.posZ - double5;
                final double yCoordBlock = MathHelper.sqrt_double(double6 * double6 + double7 * double7 + n7 * n7);
                double6 /= yCoordBlock;
                double7 /= yCoordBlock;
                n7 /= yCoordBlock;
                final double zCoordBlock = fe.getBlockDensity(vector, entity.boundingBox);
                final double n10 = (1.0 - n9) * zCoordBlock;
                entity.attackEntityFrom(eq, (int) ((n10 * n10 + n10) / 2.0 * 8.0 * float6 + 1.0));
                final double n11 = n10;
                final Entity entity2 = entity;
                entity2.motionX += double6 * n11;
                final Entity entity3 = entity;
                entity3.motionY += double7 * n11;
                final Entity entity4 = entity;
                entity4.motionZ += n7 * n11;
            }
        }
        float6 = n;
        final ArrayList list = new ArrayList();
        list.addAll((Collection) set);
        for (int n12 = list.size() - 1; n12 >= 0; --n12) {
            final ChunkPosition chunkPosition = (ChunkPosition) list.get(n12);
            final int x = chunkPosition.x;
            final int y = chunkPosition.y;
            final int z = chunkPosition.z;
            final int blockId2 = fe.getBlockId(x, y, z);
            for (int n13 = 0; n13 < 1; ++n13) {
                final double n7 = x + fe.rand.nextFloat();
                final double yCoordBlock = y + fe.rand.nextFloat();
                final double zCoordBlock = z + fe.rand.nextFloat();
                double n10 = n7 - double3;
                double n11 = yCoordBlock - double4;
                double n14 = zCoordBlock - double5;
                final double n15 = MathHelper.sqrt_double(n10 * n10 + n11 * n11 + n14 * n14);
                n10 /= n15;
                n11 /= n15;
                n14 /= n15;
                double n16 = 0.5 / (n15 / float6 + 0.1);
                n16 *= fe.rand.nextFloat() * fe.rand.nextFloat() + 0.3f;
                n10 *= n16;
                n11 *= n16;
                n14 *= n16;
                fe.spawnParticle("explode", (n7 + double3 * 1.0) / 2.0, (yCoordBlock + double4 * 1.0) / 2.0, (zCoordBlock + double5 * 1.0) / 2.0, n10, n11, n14);
                fe.spawnParticle("smoke", n7, yCoordBlock, zCoordBlock, n10, n11, n14);
            }
            if (blockId2 > 0) {
                Block.blocksList[blockId2].dropBlockAsItemWithChance(fe, x, y, z, fe.getBlockMetadata(x, y, z), 0.3f);
                fe.setBlockWithNotify(x, y, z, 0);
                Block.blocksList[blockId2].onBlockDestroyedByExplosion(fe, x, y, z);
            }
        }
    }
}
