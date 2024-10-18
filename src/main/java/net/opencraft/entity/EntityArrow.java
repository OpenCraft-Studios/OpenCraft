
package net.opencraft.entity;

import static org.joml.Math.*;

import java.util.List;
import net.opencraft.client.input.MovingObjectPosition;
import net.opencraft.item.Item;
import net.opencraft.item.ItemStack;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.util.Mth;
import net.opencraft.util.Vec3;
import net.opencraft.world.World;

public class EntityArrow extends Entity {

	private int xTile;
	private int yTile;
	private int zTile;
	private int inTile;
	private boolean inGround;
	public int arrowShake;
	private EntityLiving owner;
	private int ticksInGround;
	private int ticksInAir;

	public EntityArrow(final World world) {
		super(world);
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		this.inTile = 0;
		this.inGround = false;
		this.arrowShake = 0;
		this.ticksInAir = 0;
		this.setSize(0.5f, 0.5f);
	}

	public EntityArrow(final World world, final EntityLiving entityLiving) {
		super(world);
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		this.inTile = 0;
		this.inGround = false;
		this.arrowShake = 0;
		this.ticksInAir = 0;
		this.owner = entityLiving;
		this.setSize(0.5f, 0.5f);
		this.setPositionAndRotation(entityLiving.x, entityLiving.y, entityLiving.z, entityLiving.yRot, entityLiving.xRot);
		this.x -= cos(toRadians(yRot)) * 0.16f;
		this.y -= 0.10000000149011612;
		this.z -= sin(toRadians(yRot)) * 0.16f;
		this.setPosition(this.x, this.y, this.z);
		this.yOffset = 0.0f;
		this.xd = -sin(toRadians(yRot)) * cos(toRadians(xRot));
		this.zd = cos(toRadians(yRot)) * cos(toRadians(xRot));
		this.yd = -sin(toRadians(xRot));
		this.shoot(this.xd, this.yd, this.zd, 1.5f, 1.0f);
	}

	public void shoot(double xCoord, double yCoord, double zCoord, final float yaw, final float pitch) {
		final float sqrt_double = Mth.sqrt_double(xCoord * xCoord + yCoord * yCoord + zCoord * zCoord);
		xCoord /= sqrt_double;
		yCoord /= sqrt_double;
		zCoord /= sqrt_double;
		xCoord += this.rand.nextGaussian() * 0.007499999832361937 * pitch;
		yCoord += this.rand.nextGaussian() * 0.007499999832361937 * pitch;
		zCoord += this.rand.nextGaussian() * 0.007499999832361937 * pitch;
		xCoord *= yaw;
		yCoord *= yaw;
		zCoord *= yaw;
		this.xd = xCoord;
		this.yd = yCoord;
		this.zd = zCoord;
		final float sqrt_double2 = Mth.sqrt_double(xCoord * xCoord + zCoord * zCoord);
		final float n = (float) (toRadians(atan2(xCoord, zCoord)));
		this.yRot = n;
		this.prevRotationYaw = n;
		final float n2 = (float) (toRadians(atan2(yCoord, (double) sqrt_double2)));
		this.xRot = n2;
		this.prevRotationPitch = n2;
		this.ticksInGround = 0;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.arrowShake > 0) {
			--this.arrowShake;
		}
		if (this.inGround) {
			if (this.world.getBlockId(this.xTile, this.yTile, this.zTile) == this.inTile) {
				++this.ticksInGround;
				if (this.ticksInGround == 1200) {
					this.setEntityDead();
				}
				return;
			}
			this.inGround = false;
			this.xd *= this.rand.nextFloat() * 0.2f;
			this.yd *= this.rand.nextFloat() * 0.2f;
			this.zd *= this.rand.nextFloat() * 0.2f;
			this.ticksInGround = 0;
			this.ticksInAir = 0;
		} else {
			++this.ticksInAir;
		}
		MovingObjectPosition rayTraceBlocks = this.world.raycastBlocks(Vec3.newTemp(this.x, this.y, this.z), Vec3.newTemp(this.x + this.xd, this.y + this.yd, this.z + this.zd));
		final Vec3 vector = Vec3.newTemp(this.x, this.y, this.z);
		Vec3 var2 = Vec3.newTemp(this.x + this.xd, this.y + this.yd, this.z + this.zd);
		if (rayTraceBlocks != null) {
			var2 = Vec3.newTemp(rayTraceBlocks.hitVec.x, rayTraceBlocks.hitVec.y, rayTraceBlocks.hitVec.z);
		}
		Entity eq = null;
		final List entitiesWithinAABBExcludingEntity = this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.addCoord(this.xd, this.yd, this.zd).grow(1.0, 1.0, 1.0));
		double n = 0.0;
		for ( int i = 0; i < entitiesWithinAABBExcludingEntity.size(); ++i ) {
			final Entity entity = (Entity) entitiesWithinAABBExcludingEntity.get(i);
			if (entity.canBeCollidedWith()) {
				if (entity != this.owner || this.ticksInAir >= 5) {
					final float n2 = 0.3f;
					final MovingObjectPosition calculateIntercept = entity.bb.grow(n2, n2, n2).calculateIntercept(vector, var2);
					if (calculateIntercept != null) {
						final double distanceTo = vector.distance(calculateIntercept.hitVec);
						if (distanceTo < n || n == 0.0) {
							eq = entity;
							n = distanceTo;
						}
					}
				}
			}
		}
		if (eq != null) {
			rayTraceBlocks = new MovingObjectPosition(eq);
		}
		if (rayTraceBlocks != null) {
			if (rayTraceBlocks.entityHit != null) {
				if (rayTraceBlocks.entityHit.attackEntityFrom(this.owner, 4)) {
					this.world.playSound((Entity) this, "random.drr", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
					this.setEntityDead();
				} else {
					this.xd *= -0.10000000149011612;
					this.yd *= -0.10000000149011612;
					this.zd *= -0.10000000149011612;
					this.yRot += 180.0f;
					this.prevRotationYaw += 180.0f;
					this.ticksInAir = 0;
				}
			} else {
				this.xTile = rayTraceBlocks.blockX;
				this.yTile = rayTraceBlocks.blockY;
				this.zTile = rayTraceBlocks.blockZ;
				this.inTile = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
				this.xd = (float) (rayTraceBlocks.hitVec.x - this.x);
				this.yd = (float) (rayTraceBlocks.hitVec.y - this.y);
				this.zd = (float) (rayTraceBlocks.hitVec.z - this.z);
				final float n3 = Mth.sqrt_double(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
				this.x -= this.xd / n3 * 0.05000000074505806;
				this.y -= this.yd / n3 * 0.05000000074505806;
				this.z -= this.zd / n3 * 0.05000000074505806;
				this.world.playSound((Entity) this, "random.drr", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
				this.inGround = true;
				this.arrowShake = 7;
			}
		}
		this.x += this.xd;
		this.y += this.yd;
		this.z += this.zd;
		final float n3 = Mth.sqrt_double(this.xd * this.xd + this.zd * this.zd);
		this.yRot = (float) (toRadians(atan2(this.xd, this.zd)));
		this.xRot = (float) (toRadians(atan2(this.yd, (double) n3)));
		while(this.xRot - this.prevRotationPitch < -180.0f) {
			this.prevRotationPitch -= 360.0f;
		}
		while(this.xRot - this.prevRotationPitch >= 180.0f) {
			this.prevRotationPitch += 360.0f;
		}
		while(this.yRot - this.prevRotationYaw < -180.0f) {
			this.prevRotationYaw -= 360.0f;
		}
		while(this.yRot - this.prevRotationYaw >= 180.0f) {
			this.prevRotationYaw += 360.0f;
		}
		this.xRot = this.prevRotationPitch + (this.xRot - this.prevRotationPitch) * 0.2f;
		this.yRot = this.prevRotationYaw + (this.yRot - this.prevRotationYaw) * 0.2f;
		float n4 = 0.99f;
		final float n2 = 0.03f;
		if (this.handleWaterMovement()) {
			for ( int j = 0; j < 4; ++j ) {
				final float n5 = 0.25f;
				this.world.spawnParticle("bubble", this.x - this.xd * n5, this.y - this.yd * n5, this.z - this.zd * n5, this.xd, this.yd, this.zd);
			}
			n4 = 0.8f;
		}
		this.xd *= n4;
		this.yd *= n4;
		this.zd *= n4;
		this.yd -= n2;
		this.setPosition(this.x, this.y, this.z);
	}

	public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
		nbtTagCompound.setShort("xTile", (short) this.xTile);
		nbtTagCompound.setShort("yTile", (short) this.yTile);
		nbtTagCompound.setShort("zTile", (short) this.zTile);
		nbtTagCompound.setByte("inTile", (byte) this.inTile);
		nbtTagCompound.setByte("shake", (byte) this.arrowShake);
		nbtTagCompound.setByte("inGround", (byte) (byte) (this.inGround ? 1 : 0));
	}

	public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
		this.xTile = nbtTagCompound.getShort("xTile");
		this.yTile = nbtTagCompound.getShort("yTile");
		this.zTile = nbtTagCompound.getShort("zTile");
		this.inTile = (nbtTagCompound.getByte("inTile") & 0xFF);
		this.arrowShake = (nbtTagCompound.getByte("shake") & 0xFF);
		this.inGround = (nbtTagCompound.getByte("inGround") == 1);
	}

	@Override
	public void onCollideWithPlayer(final Player entityPlayer) {
		if (this.inGround && this.owner == entityPlayer && this.arrowShake <= 0 && entityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.arrow.shiftedIndex, 1))) {
			this.world.playSound((Entity) this, "random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
			entityPlayer.onItemPickup(this);
			this.setEntityDead();
		}
	}

}
