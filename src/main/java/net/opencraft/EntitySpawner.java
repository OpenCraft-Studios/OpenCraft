
package net.opencraft;

import static net.opencraft.util.Mth.*;

import net.opencraft.blocks.material.Material;
import net.opencraft.entity.Entity;
import net.opencraft.entity.EntityLiving;
import net.opencraft.renderer.gui.IProgressUpdate;
import net.opencraft.util.Mth;
import net.opencraft.world.World;
import net.opencraft.world.chunk.ChunkPosition;

public class EntitySpawner {

	private final int maxEntities;
	private final Class<? extends Entity> mainEntityClass;
	private final Class<? extends Entity>[] spawnableEntities;

	public EntitySpawner(int maxEntities, Class<? extends Entity> mainEntityClass, Class<? extends Entity>[] spawnableEntities) {
		this.maxEntities = maxEntities;
		this.mainEntityClass = mainEntityClass;
		this.spawnableEntities = spawnableEntities;
	}

	public void spawnEntitiesIfNecessary(World world) {
		if(world.countEntities(mainEntityClass) >= this.maxEntities)
			return;

		for(int i = 0; i < 10; i++)
			spawnEntityBatch(world, 1, world.player, null);
	}

	protected ChunkPosition getRandomChunkPos(World world, int baseX, int baseZ) {
		return new ChunkPosition(baseX + world.random.nextInt(256) - 128, world.random.nextInt(128), baseZ + world.random.nextInt(256) - 128);
	}

	private int spawnEntityBatch(World world, int batchSize, Entity player, IProgressUpdate ipu) {
		int entitiesSpawned = 0;
		int baseX = Mth.floor_double(player.x);
		int baseZ = Mth.floor_double(player.z);
		int entityTypeIndex = world.random.nextInt(this.spawnableEntities.length);

		ChunkPosition spawnPos = this.getRandomChunkPos(world, baseX, baseZ);

		int x = spawnPos.x;
		int y = spawnPos.y;
		int z = spawnPos.z;

		if(world.isBlockNormalCube(x, y, z))
			return 0;
		if(world.getBlockMaterial(x, y, z) != Material.AIR)
			return 0;

		for(int i = 0; i < 3; ++i) {
			int xOffset = x;
			int yOffset = y;
			int zOffset = z;
			final int maxOffset = 6;
			for(int j = 0; j < 3; ++j) {
				xOffset += world.random.nextInt(maxOffset) - world.random.nextInt(maxOffset);
				yOffset += world.random.nextInt(1) - world.random.nextInt(1);
				zOffset += world.random.nextInt(maxOffset) - world.random.nextInt(maxOffset);

				if(world.isBlockNormalCube(xOffset, yOffset - 1, zOffset) && !world.isBlockNormalCube(xOffset, yOffset, zOffset) && !world.getBlockMaterial(xOffset, yOffset, zOffset).isLiquid() && !world.isBlockNormalCube(xOffset, yOffset + 1, zOffset)) {
					float spawnX = xOffset + 0.5f;
					float spawnY = yOffset + 1.0f;
					float spawnZ = zOffset + 0.5f;

					double distX, distY, distZ;
					if(player != null) {
						distX = spawnX - player.x;
						distY = spawnY - player.y;
						distZ = spawnZ - player.z;
					} else {
						distX = spawnX - world.x;
						distY = spawnY - world.y;
						distZ = spawnZ - world.z;
					}
					if(square(distX) + square(distY) + square(distZ) < 1024.0)
						continue;
					EntityLiving entity;
					try {
						entity = (EntityLiving) this.spawnableEntities[entityTypeIndex].getConstructor(new Class[] { World.class }).newInstance(new Object[] { world });
					} catch(Exception ex) {
						ex.printStackTrace();
						System.err.println("=== MESSAGE FROM PAST CIRO:");
						System.err.println("    EVERY ENTITY MUST HAVE AN ARGUMENT IN THEIR CONSTRUCTOR (World)");
						return entitiesSpawned;
					}
					entity.setPositionAndRotation(spawnX, spawnY, spawnZ, world.random.nextFloat() * 360.0f, 0.0f);
					if(entity.getCanSpawnHere(spawnX, spawnY, spawnZ)) {
						++entitiesSpawned;
						world.entityJoinedWorld(entity);
					}
				}
			}
		}
		return entitiesSpawned;
	}

}
