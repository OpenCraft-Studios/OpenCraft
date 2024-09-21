
package net.opencraft.entity;

import java.util.HashMap;
import java.util.Map;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.world.World;

public class EntityList {

    private static Map stringToClassMapping;
    private static Map classToStringMapping;

    private static void addMapping(final Class class1, final String string) {
        EntityList.stringToClassMapping.put(string, class1);
        EntityList.classToStringMapping.put(class1, string);
    }

    public static Entity createEntityInWorld(final String string, final World world) {
        Entity entity = null;
        try {
            final Class clazz = (Class) EntityList.stringToClassMapping.get(string);
            if (clazz != null) {
                entity = (Entity) clazz.getConstructor(new Class[]{World.class}).newInstance(new Object[]{world});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return entity;
    }

    public static Entity createEntityFromNBT(final NBTTagCompound nbt, final World world) {
        Entity entity = null;
        try {
            final Class clazz = (Class) EntityList.stringToClassMapping.get(nbt.getString("id"));
            if (clazz != null) {
                entity = (Entity) clazz.getConstructor(new Class[]{World.class}).newInstance(new Object[]{world});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (entity != null) {
            entity.readFromNBT(nbt);
        } else {
            System.out.println("Skipping Entity with id " + nbt.getString("id"));
        }
        return entity;
    }

    public static String getEntityString(final Entity entity) {
        return (String) EntityList.classToStringMapping.get(entity.getClass());
    }

    static {
        EntityList.stringToClassMapping = (Map) new HashMap();
        EntityList.classToStringMapping = (Map) new HashMap();
        addMapping((Class) EntityArrow.class, "Arrow");
        addMapping((Class) EntityItem.class, "Item");
        addMapping((Class) EntityPainting.class, "Painting");
        addMapping((Class) EntityLiving.class, "Mob");
        addMapping((Class) EntityMonster.class, "Monster");
        addMapping((Class) EntityCreeper.class, "Creeper");
        addMapping((Class) EntitySkeleton.class, "Skeleton");
        addMapping((Class) EntitySpider.class, "Spider");
        addMapping((Class) EntityGiant.class, "Giant");
        addMapping((Class) EntityZombie.class, "Zombie");
        addMapping((Class) EntityPig.class, "Pig");
        addMapping((Class) EntitySheep.class, "Sheep");
        addMapping((Class) EntityTNTPrimed.class, "PrimedTnt");
        addMapping((Class) EntityFallingSand.class, "FallingSand");
        addMapping((Class) EntityMinecart.class, "Minecart");
    }
}
