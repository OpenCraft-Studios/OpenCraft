
package net.opencraft.entity;

import net.opencraft.item.Item;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.world.World;

public class EntityCreeper extends EntityMonster {

    int timeSinceIgnited;
    int lastActiveTime;
    int fuseTime;
    int creeperState;

    public EntityCreeper(final World world) {
        super(world);
        this.fuseTime = 30;
        this.creeperState = -1;
        this.texture = "/assets/mob/creeper.png";
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
    protected void updatePlayerActionState() {
        this.lastActiveTime = this.timeSinceIgnited;
        if (this.timeSinceIgnited > 0 && this.creeperState < 0) {
            --this.timeSinceIgnited;
        }
        if (this.creeperState >= 0) {
            this.creeperState = 2;
        }
        super.updatePlayerActionState();
        if (this.creeperState != 1) {
            this.creeperState = -1;
        }
    }

    @Override
    protected void attackEntity(final Entity entity, final float xCoord) {
        if ((this.creeperState <= 0 && xCoord < 3.0f) || (this.creeperState > 0 && xCoord < 7.0f)) {
            if (this.timeSinceIgnited == 0) {
                this.world.playSound((Entity) this, "random.fuse", 1.0f, 0.5f);
            }
            this.creeperState = 1;
            ++this.timeSinceIgnited;
            if (this.timeSinceIgnited == this.fuseTime) {
                this.world.createExplosion(this, this.posX, this.posY, this.posZ, 3.0f);
                this.setEntityDead();
            }
            this.hasAttacked = true;
        }
    }

    public float setCreeperFlashTime(final float float1) {
        return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * float1) / (this.fuseTime - 2);
    }

    @Override
    protected int scoreValue() {
        return Item.gunpowder.shiftedIndex;
    }
}
