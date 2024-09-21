
package net.opencraft.entity;

import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.util.MathHelper;
import net.opencraft.world.World;

public class EntityFallingSand extends Entity {

    public int blockID;
    public int fallTime;

    public EntityFallingSand(final World world) {
        super(world);
        this.fallTime = 0;
    }

    public EntityFallingSand(final World world, final float xCoord, final float yCoord, final float zCoord, final int blockid) {
        super(world);
        this.fallTime = 0;
        this.blockID = blockid;
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.98f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(xCoord, yCoord, zCoord);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.canTriggerWalking = false;
        this.prevPosX = xCoord;
        this.prevPosY = yCoord;
        this.prevPosZ = zCoord;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public void onUpdate() {
        if (this.blockID == 0) {
            this.setEntityDead();
            return;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        ++this.fallTime;
        this.motionY -= 0.03999999910593033;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        final int floor_double = MathHelper.floor_double(this.posX);
        final int floor_double2 = MathHelper.floor_double(this.posY);
        final int floor_double3 = MathHelper.floor_double(this.posZ);
        if (this.worldObj.getBlockId(floor_double, floor_double2, floor_double3) == this.blockID) {
            this.worldObj.setBlockWithNotify(floor_double, floor_double2, floor_double3, 0);
        }
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
            this.motionY *= -0.5;
            this.setEntityDead();
            if (!this.worldObj.canBlockBePlacedAt(this.blockID, floor_double, floor_double2, floor_double3, true) || !this.worldObj.setBlockWithNotify(floor_double, floor_double2, floor_double3, this.blockID)) {
                this.dropItemWithOffset(this.blockID, 1);
            }
        } else if (this.fallTime > 100) {
            this.dropItemWithOffset(this.blockID, 1);
            this.setEntityDead();
        }
    }

    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setByte("Tile", (byte) this.blockID);
    }

    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.blockID = (nbtTagCompound.getByte("Tile") & 0xFF);
    }

    public World getWorld() {
        return this.worldObj;
    }
}
