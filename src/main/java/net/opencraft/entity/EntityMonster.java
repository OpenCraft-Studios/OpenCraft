
package net.opencraft.entity;

import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.util.Mth;
import net.opencraft.world.World;

public class EntityMonster extends EntityCreature {

    protected int attackStrength;

    public EntityMonster(final World world) {
        super(world);
        this.attackStrength = 2;
        this.health = 20;
    }

    @Override
    public void onLivingUpdate() {
        if (this.getEntityBrightness(1.0f) > 0.5f) {
            this.entityAge += 2;
        }
        super.onLivingUpdate();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.worldObj.difficultySetting == 0) {
            this.setEntityDead();
        }
    }

    @Override
    protected Entity findPlayerToAttack() {
        final double distanceSqToEntity = this.worldObj.player.getDistanceSqToEntity(this);
        final double n = 16.0;
        if (distanceSqToEntity < n * n && this.canEntityBeSeen(this.worldObj.player)) {
            return this.worldObj.player;
        }
        return null;
    }

    @Override
    public boolean attackEntityFrom(final Entity entity, final int nya1) {
        if (super.attackEntityFrom(entity, nya1)) {
            if (entity != this) {
                this.playerToAttack = entity;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void attackEntity(final Entity entity, final float xCoord) {
        if (xCoord < 2.5 && entity.boundingBox.maxY > this.boundingBox.minY && entity.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            entity.attackEntityFrom(this, this.attackStrength);
        }
    }

    @Override
    protected float getBlockPathWeight(final int xCoord, final int yCoord, final int zCoord) {
        return 0.5f - this.worldObj.getLightBrightness(xCoord, yCoord, zCoord);
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
        return this.worldObj.getBlockLightValue(Mth.floor_double(nya1), Mth.floor_double(nya2), Mth.floor_double(nya3)) <= this.rand.nextInt(8) && super.getCanSpawnHere(nya1, nya2, nya3);
    }
}
