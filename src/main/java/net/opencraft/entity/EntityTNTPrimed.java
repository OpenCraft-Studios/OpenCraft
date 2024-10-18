
package net.opencraft.entity;

import static org.joml.Math.*;

import net.opencraft.nbt.NBTTagCompound;
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
		final float n = (float) (random() * PI_TIMES_2);
		this.xd = -sin(toRadians(n)) * 0.02f;
		this.yd = 0.20000000298023224;
		this.zd = -cos(toRadians(n)) * 0.02f;
		this.canTriggerWalking = false;
		this.fuse = 80;
		this.xo = xCoord;
		this.yo = yCoord;
		this.zo = zCoord;
	}

	@Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	@Override
	public void onUpdate() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		this.yd -= 0.03999999910593033;
		this.moveEntity(this.xd, this.yd, this.zd);
		this.xd *= 0.9800000190734863;
		this.yd *= 0.9800000190734863;
		this.zd *= 0.9800000190734863;
		if (this.onGround) {
			this.xd *= 0.699999988079071;
			this.zd *= 0.699999988079071;
			this.yd *= -0.5;
		}
		if (this.fuse-- <= 0) {
			this.setEntityDead();
			this.createExplosion();
		} else {
			this.world.spawnParticle("smoke", this.x, this.y + 0.5, this.z, 0.0, 0.0, 0.0);
		}
	}

	private void createExplosion() {
		this.world.createExplosion((Entity) null, this.x, this.y, this.z, 4.0f);
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
