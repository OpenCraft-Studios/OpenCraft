
package net.opencraft.entity;

import static org.joml.Math.*;

import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.util.Mth;
import net.opencraft.world.World;

public class EntityTNTPrimed extends Entity {

    public int fuse;

    public EntityTNTPrimed(final World world) {
        super(world);
        this.fuse = 0;
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.98f);
        this.yOffset = this.height / 2.0f;
    }

    public EntityTNTPrimed(final World fe, final float xCoord, final float yCoord, final float zCoord) {
        this(fe);
        this.setPosition(xCoord, yCoord, zCoord);
        final float n = (float) (Math.random() * 3.1415927410125732 * 2.0);
        this.motionX = -sin(toRadians(n)) * 0.02f;
        this.motionY = 0.20000000298023224;
        this.motionZ = -Mth.cos(n * 3.1415927f / 180.0f) * 0.02f;
        this.canTriggerWalking = false;
        this.fuse = 80;
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
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
            this.motionY *= -0.5;
        }
        if (this.fuse-- <= 0) {
            this.setEntityDead();
            this.createExplosion();
        } else {
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0);
        }
    }

    private void createExplosion() {
        this.worldObj.createExplosion((Entity) null, this.posX, this.posY, this.posZ, 4.0f);
    }

    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setByte("Fuse", (byte) this.fuse);
    }

    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.fuse = nbtTagCompound.getByte("Fuse");
    }
}
