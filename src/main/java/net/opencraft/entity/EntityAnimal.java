
package net.opencraft.entity;

import net.opencraft.blocks.Block;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.util.Mth;
import net.opencraft.world.World;

public abstract class EntityAnimal extends EntityCreature {

    public EntityAnimal(final World world) {
        super(world);
    }

    @Override
    protected float getBlockPathWeight(final int xCoord, final int yCoord, final int zCoord) {
        if (this.world.getBlockId(xCoord, yCoord - 1, zCoord) == Block.grass.blockID) {
            return 10.0f;
        }
        return this.world.getLightBrightness(xCoord, yCoord, zCoord) - 0.5f;
    }

    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
    }

    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
    }

    @Override
    public boolean getCanSpawnHere(final double nya1, final double nya2, final double nya3) {
        return this.world.getBlockLightValue(Mth.floor_double(nya1), Mth.floor_double(nya2), Mth.floor_double(nya3)) > 8 && super.getCanSpawnHere(nya1, nya2, nya3);
    }
}
