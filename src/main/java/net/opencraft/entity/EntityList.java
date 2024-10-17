
package net.opencraft.entity;

import java.util.HashMap;
import java.util.Map;

import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.world.World;

public class EntityList {

	private static Map<String, String> stcMapping = new HashMap<>();
	private static Map<String, String> ctsMapping = new HashMap<>();

	private static void registerEntity(Class<? extends Entity> clazz, final String id) {
		System.out.println("Entity registred successfully %s!".formatted("opencraft:".concat(id.toLowerCase())));
		
		stcMapping.put(id, clazz.getName());
		ctsMapping.put(clazz.getName(), id);
	}

	@SuppressWarnings("unchecked")
	public static Entity createEntityInWorld(String id, World world) {
		Entity entity = null;
		try {
			Class<? extends Entity> clazz = (Class<? extends Entity>) Class.forName(stcMapping.get(id));
			if (clazz != null)
				entity = clazz.getConstructor(new Class[] { World.class }).newInstance(new Object[] { world });
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return entity;
	}

	public static Entity createEntityFromNBT(NBTTagCompound nbt, World world) {
		Entity entity = createEntityInWorld(nbt.getString("id"), world);
		
		if (entity != null) {
			entity.readFromNBT(nbt);
		} else
			System.out.println("Skipping Entity with id " + nbt.getString("id"));
		
		return entity;
	}

	public static String getEntityString(final Entity entity) {
		return ctsMapping.get(entity.getClass().getName());
	}

	static {
		registerEntity(EntityArrow.class, "Arrow");
		registerEntity(EntityItem.class, "Item");
		registerEntity(EntityPainting.class, "Painting");
		registerEntity(EntityLiving.class, "Mob");
		registerEntity(EntityMonster.class, "Monster");
		registerEntity(EntityCreeper.class, "Creeper");
		registerEntity(EntitySkeleton.class, "Skeleton");
		registerEntity(EntitySpider.class, "Spider");
		registerEntity(EntityGiant.class, "Giant");
		registerEntity(EntityZombie.class, "Zombie");
		registerEntity(EntityPig.class, "Pig");
		registerEntity(EntitySheep.class, "Sheep");
		registerEntity(EntityTNTPrimed.class, "PrimedTnt");
		registerEntity(EntityFallingSand.class, "FallingSand");
		registerEntity(EntityMinecart.class, "Minecart");
	}

}
