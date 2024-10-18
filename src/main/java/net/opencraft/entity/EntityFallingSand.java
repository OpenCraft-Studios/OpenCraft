
package net.opencraft.entity;

import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.util.Mth;
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
		this.xd = 0.0;
		this.yd = 0.0;
		this.zd = 0.0;
		this.canTriggerWalking = false;
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
		if (this.blockID == 0) {
			this.setEntityDead();
			return;
		}
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		++this.fallTime;
		this.yd -= 0.03999999910593033;
		this.moveEntity(this.xd, this.yd, this.zd);
		this.xd *= 0.9800000190734863;
		this.yd *= 0.9800000190734863;
		this.zd *= 0.9800000190734863;
		final int floor_double = Mth.floor_double(this.x);
		final int floor_double2 = Mth.floor_double(this.y);
		final int floor_double3 = Mth.floor_double(this.z);
		if (this.world.getBlockId(floor_double, floor_double2, floor_double3) == this.blockID) {
			this.world.setBlockWithNotify(floor_double, floor_double2, floor_double3, 0);
		}
		if (this.onGround) {
			this.xd *= 0.699999988079071;
			this.zd *= 0.699999988079071;
			this.yd *= -0.5;
			this.setEntityDead();
			if (!this.world.canBlockBePlacedAt(this.blockID, floor_double, floor_double2, floor_double3, true) || !this.world.setBlockWithNotify(floor_double, floor_double2, floor_double3, this.blockID)) {
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
		return this.world;
	}

}
