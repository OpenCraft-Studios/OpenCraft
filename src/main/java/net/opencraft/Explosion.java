
package net.opencraft;

import static org.joml.Math.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import net.opencraft.blocks.Block;
import net.opencraft.entity.Entity;
import net.opencraft.physics.AABB;
import net.opencraft.util.Mth;
import net.opencraft.util.Vec3;
import net.opencraft.world.World;
import net.opencraft.world.chunk.ChunkPosition;

public class Explosion {

	public void doExplosion(World world, Entity eq, double double3, double double4, double double5, float float6) {
		world.playSoundEffect(double3, double4, double5, "random.explode", 4.0f, (1.0f + (world.random.nextFloat() - world.random.nextFloat()) * 0.2f) * 0.7f);
		HashSet set = new HashSet();
		float n = float6;
		for(int n2 = 16, i = 0; i < n2; ++i) {
			for(int j = 0; j < n2; ++j) {
				for(int k = 0; k < n2; ++k) {
					if(i == 0 || i == n2 - 1 || j == 0 || j == n2 - 1 || k == 0 || k == n2 - 1) {
						double n3 = i / (n2 - 1.0f) * 2.0f - 1.0f;
						double n4 = j / (n2 - 1.0f) * 2.0f - 1.0f;
						double n5 = k / (n2 - 1.0f) * 2.0f - 1.0f;
						double sqrt = sqrt(n3 * n3 + n4 * n4 + n5 * n5);
						n3 /= sqrt;
						n4 /= sqrt;
						n5 /= sqrt;
						float n6 = float6 * (0.7f + world.random.nextFloat() * 0.6f);
						double double6 = double3;
						double double7 = double4;
						double n7 = double5;
						for(float n8 = 0.3f; n6 > 0.0f; n6 -= n8 * 0.75f) {
							int floor_double = Mth.floor_double(double6);
							int floor_double2 = Mth.floor_double(double7);
							int floor_double3 = Mth.floor_double(n7);
							int blockId = world.getBlockId(floor_double, floor_double2, floor_double3);
							if(blockId > 0) {
								n6 -= (Block.blocksList[blockId].getExplosionResistance(eq) + 0.3f) * n8;
							}
							if(n6 > 0.0f) {
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
		int i = Mth.floor_double(double3 - float6 - 1.0);
		int j = Mth.floor_double(double3 + float6 + 1.0);
		int k = Mth.floor_double(double4 - float6 - 1.0);
		List entitiesWithinAABBExcludingEntity = world.getEntitiesWithinAABBExcludingEntity(eq, AABB.getBoundingBoxFromPool(i, k, Mth.floor_double(double5 - float6 - 1.0), j, Mth.floor_double(double4 + float6 + 1.0), Mth.floor_double(double5 + float6 + 1.0)));
		Vec3 vector = Vec3.newTemp(double3, double4, double5);
		for(int l = 0; l < entitiesWithinAABBExcludingEntity.size(); ++l) {
			Entity entity = (Entity) entitiesWithinAABBExcludingEntity.get(l);
			double n9 = entity.getDistance(double3, double4, double5) / float6;
			if(n9 <= 1.0) {
				double double6 = entity.x - double3;
				double double7 = entity.y - double4;
				double n7 = entity.z - double5;
				double yCoordBlock = Mth.sqrt_double(double6 * double6 + double7 * double7 + n7 * n7);
				double6 /= yCoordBlock;
				double7 /= yCoordBlock;
				n7 /= yCoordBlock;
				double zCoordBlock = world.getBlockDensity(vector, entity.boundingBox);
				double n10 = (1.0 - n9) * zCoordBlock;
				entity.attackEntityFrom(eq, (int) ((n10 * n10 + n10) / 2.0 * 8.0 * float6 + 1.0));
				double n11 = n10;
				Entity entity2 = entity;
				entity2.motionX += double6 * n11;
				Entity entity3 = entity;
				entity3.motionY += double7 * n11;
				Entity entity4 = entity;
				entity4.motionZ += n7 * n11;
			}
		}
		float6 = n;
		ArrayList list = new ArrayList();
		list.addAll((Collection) set);
		for(int n12 = list.size() - 1; n12 >= 0; --n12) {
			ChunkPosition chunkPosition = (ChunkPosition) list.get(n12);
			int x = chunkPosition.x;
			int y = chunkPosition.y;
			int z = chunkPosition.z;
			int blockId2 = world.getBlockId(x, y, z);
			for(int n13 = 0; n13 < 1; ++n13) {
				double n7 = x + world.random.nextFloat();
				double yCoordBlock = y + world.random.nextFloat();
				double zCoordBlock = z + world.random.nextFloat();
				double n10 = n7 - double3;
				double n11 = yCoordBlock - double4;
				double n14 = zCoordBlock - double5;
				double n15 = Mth.sqrt_double(n10 * n10 + n11 * n11 + n14 * n14);
				n10 /= n15;
				n11 /= n15;
				n14 /= n15;
				double n16 = 0.5 / (n15 / float6 + 0.1);
				n16 *= world.random.nextFloat() * world.random.nextFloat() + 0.3f;
				n10 *= n16;
				n11 *= n16;
				n14 *= n16;
				world.spawnParticle("explode", (n7 + double3 * 1.0) / 2.0, (yCoordBlock + double4 * 1.0) / 2.0, (zCoordBlock + double5 * 1.0) / 2.0, n10, n11, n14);
				world.spawnParticle("smoke", n7, yCoordBlock, zCoordBlock, n10, n11, n14);
			}
			if(blockId2 > 0) {
				Block.blocksList[blockId2].dropBlockAsItemWithChance(world, x, y, z, world.getBlockMetadata(x, y, z), 0.3f);
				world.setBlockWithNotify(x, y, z, 0);
				Block.blocksList[blockId2].onBlockDestroyedByExplosion(world, x, y, z);
			}
		}
	}

}
