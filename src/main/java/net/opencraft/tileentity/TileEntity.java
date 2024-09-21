
package net.opencraft.tileentity;

import java.util.HashMap;
import java.util.Map;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.world.World;

public class TileEntity {

    private static Map nameToClassMap;
    private static Map classToNameMap;
    public World worldObj;
    public int xCoord;
    public int yCoord;
    public int zCoord;

    private static void addMapping(final Class class1, final String string) {
        if (TileEntity.classToNameMap.containsKey(string)) {
            throw new IllegalArgumentException("Duplicate id: " + string);
        }
        TileEntity.nameToClassMap.put(string, class1);
        TileEntity.classToNameMap.put(class1, string);
    }

    public void readFromNBT(final NBTTagCompound ae) {
        this.xCoord = ae.getInteger("x");
        this.yCoord = ae.getInteger("y");
        this.zCoord = ae.getInteger("z");
    }

    public void writeToNBT(final NBTTagCompound ae) {
        final String string2 = (String) TileEntity.classToNameMap.get(this.getClass());
        if (string2 == null) {
            throw new RuntimeException(new StringBuilder().append(this.getClass()).append(" is missing a mapping! This is a bug!").toString());
        }
        ae.setString("id", string2);
        ae.setInteger("x", this.xCoord);
        ae.setInteger("y", this.yCoord);
        ae.setInteger("z", this.zCoord);
    }

    public void updateEntity() {
    }

    public static TileEntity createAndLoadEntity(final NBTTagCompound ae) {
        TileEntity tileEntity = null;
        try {
            final Class clazz = (Class) TileEntity.nameToClassMap.get(ae.getString("id"));
            if (clazz != null) {
                tileEntity = (TileEntity) clazz.newInstance();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (tileEntity != null) {
            tileEntity.readFromNBT(ae);
        } else {
            System.out.println("Skipping TileEntity with id " + ae.getString("id"));
        }
        return tileEntity;
    }

    public int getBlockMetadata() {
        return this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
    }

    public void onInventoryChanged() {
        this.worldObj.func_698_b(this.xCoord, this.yCoord, this.zCoord);
    }

    public double getDistanceFrom(final double double1, final double double2, final double double3) {
        final double n = this.xCoord + 0.5 - double1;
        final double n2 = this.yCoord + 0.5 - double2;
        final double n3 = this.zCoord + 0.5 - double3;
        return n * n + n2 * n2 + n3 * n3;
    }

    static {
        TileEntity.nameToClassMap = (Map) new HashMap();
        TileEntity.classToNameMap = (Map) new HashMap();
        addMapping((Class) TileEntityFurnace.class, "Furnace");
        addMapping((Class) TileEntityChest.class, "Chest");
        addMapping((Class) TileEntitySign.class, "Sign");
        addMapping((Class) TileEntityMobSpawner.class, "MobSpawner");
    }
}
