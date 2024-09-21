
package net.ncraft.entity;

import net.ncraft.block.Block;
import net.ncraft.nbt.NBTTagCompound;
import net.ncraft.util.MathHelper;
import net.ncraft.world.World;

public abstract class EntityAnimal extends EntityCreature {

    public EntityAnimal(final World world) {
        super(world);
    }

    @Override
    protected float getBlockPathWeight(final int xCoord, final int yCoord, final int zCoord) {
        if (this.worldObj.getBlockId(xCoord, yCoord - 1, zCoord) == Block.grass.blockID) {
            return 10.0f;
        }
        return this.worldObj.getLightBrightness(xCoord, yCoord, zCoord) - 0.5f;
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
        return this.worldObj.getBlockLightValue(MathHelper.floor_double(nya1), MathHelper.floor_double(nya2), MathHelper.floor_double(nya3)) > 8 && super.getCanSpawnHere(nya1, nya2, nya3);
    }
}
