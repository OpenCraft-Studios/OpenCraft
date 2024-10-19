
package net.opencraft.entity;

import static org.joml.Math.*;

import java.util.List;
import java.util.Random;

import net.opencraft.blocks.Block;
import net.opencraft.blocks.LiquidBlock;
import net.opencraft.blocks.material.EnumMaterial;
import net.opencraft.client.sound.StepSound;
import net.opencraft.item.ItemStack;
import net.opencraft.nbt.NBTBase;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.nbt.NBTTagDouble;
import net.opencraft.nbt.NBTTagFloat;
import net.opencraft.nbt.NBTTagList;
import net.opencraft.physics.AABB;
import net.opencraft.util.Mth;
import net.opencraft.util.Vec3;
import net.opencraft.world.World;

public abstract class Entity {

	public boolean preventEntitySpawning;
	public Entity passenger;
	public Entity ridingEntity;
	protected World world;
	public double xo;
	public double yo;
	public double zo;
	public double x;
	public double y;
	public double z;
	public double xd;
	public double yd;
	public double zd;
	public float yRot;
	public float xRot;
	public float prevRotationYaw;
	public float prevRotationPitch;
	public final AABB bb;
	public boolean onGround;
	public boolean isCollidedHorizontally;
	public boolean beenAttacked;
	public boolean field_9293_aM;
	public boolean isDead;
	public float yOffset;
	public float width;
	public float height;
	public float prevDistanceWalkedModified;
	public float distanceWalkedModified;
	protected boolean canTriggerWalking;
	protected float fallDistance;
	private int nextStepDistance;
	public double lastTickPosX;
	public double lastTickPosY;
	public double lastTickPosZ;
	public float ySize;
	public float stepHeight;
	public boolean noClip;
	public float entityCollisionReduction;
	public boolean N;
	protected Random rand;
	public int ticksExisted;
	public int fireResistance;
	public int fire;
	protected int maxAir;
	protected boolean inWater;
	public int heartsLife;
	public int air;
	private boolean isFirstUpdate;
	public String skinUrl;
	private double entityRiderPitchDelta;
	private double entityRiderYawDelta;

	public Entity(final World world) {
		this.preventEntitySpawning = false;
		this.bb = AABB.getAABB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
		this.onGround = false;
		this.isCollidedHorizontally = false;
		this.beenAttacked = false;
		this.field_9293_aM = true;
		this.isDead = false;
		this.yOffset = 0.0f;
		this.width = 0.6f;
		this.height = 1.8f;
		this.prevDistanceWalkedModified = 0.0f;
		this.distanceWalkedModified = 0.0f;
		this.canTriggerWalking = true;
		this.fallDistance = 0.0f;
		this.nextStepDistance = 1;
		this.ySize = 0.0f;
		this.stepHeight = 0.0f;
		this.noClip = false;
		this.entityCollisionReduction = 0.0f;
		this.N = false;
		this.rand = new Random();
		this.ticksExisted = 0;
		this.fireResistance = 1;
		this.fire = 0;
		this.maxAir = 300;
		this.inWater = false;
		this.heartsLife = 0;
		this.air = 300;
		this.isFirstUpdate = true;
		this.world = world;
		this.setPosition(0.0, 0.0, 0.0);
	}

	protected void preparePlayerToSpawn() {
		if (this.world == null) {
			return;
		}
		while(this.y > 0.0) {
			this.setPosition(this.x, this.y, this.z);
			if (this.world.getCollidingBoundingBoxes(this, this.bb).size() == 0) {
				break;
			}
			++this.y;
		}
		final double motionX = 0.0;
		this.zd = motionX;
		this.yd = motionX;
		this.xd = motionX;
		this.xRot = 0.0f;
	}

	public void setEntityDead() {
		this.isDead = true;
	}

	protected void setSize(final float nya1, final float nya2) {
		this.width = nya1;
		this.height = nya2;
	}

	protected void setRotation(final float nya1, final float nya2) {
		this.yRot = nya1;
		this.xRot = nya2;
	}

	public void setPosition(final double xCoord, final double yCoord, final double zCoord) {
		this.x = xCoord;
		this.y = yCoord;
		this.z = zCoord;
		final float n = this.width / 2.0f;
		final float n2 = this.height / 2.0f;
		this.bb.set(xCoord - n, yCoord - n2, zCoord - n, xCoord + n, yCoord + n2, zCoord + n);
	}

	public void setAngles(final float nya1, final float nya2) {
		final float rotationPitch = this.xRot;
		final float rotationYaw = this.yRot;
		this.yRot += (float) (nya1 * 0.15);
		this.xRot -= (float) (nya2 * 0.15);
		if (this.xRot < -90.0f) {
			this.xRot = -90.0f;
		}
		if (this.xRot > 90.0f) {
			this.xRot = 90.0f;
		}
		this.prevRotationPitch += this.xRot - rotationPitch;
		this.prevRotationYaw += this.yRot - rotationYaw;
	}

	public void onUpdate() {
		this.onEntityUpdate();
	}

	public void onEntityUpdate() {
		if (this.ridingEntity != null && this.ridingEntity.isDead) {
			this.ridingEntity = null;
		}
		++this.ticksExisted;
		this.prevDistanceWalkedModified = this.distanceWalkedModified;
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		this.prevRotationPitch = this.xRot;
		this.prevRotationYaw = this.yRot;
		if (this.handleWaterMovement()) {
			if (!this.inWater && !this.isFirstUpdate) {
				float volume = (float) (sqrt(this.xd * this.xd * 0.20000000298023224 + this.yd * this.yd + this.zd * this.zd * 0.20000000298023224) * 0.2f);
				if (volume > 1.0f) {
					volume = 1.0f;
				}
				this.world.playSound(this, "random.splash", volume, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
				final float n = (float) Mth.floor_double(this.bb.minY);
				for ( int n2 = 0; n2 < 1.0f + this.width * 20.0f; ++n2 ) {
					final float n3 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
					final float n4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
					this.world.spawnParticle("bubble", this.x + n3, (double) (n + 1.0f), this.z + n4, this.xd, this.yd - this.rand.nextFloat() * 0.2f, this.zd);
				}
				for ( int n2 = 0; n2 < 1.0f + this.width * 20.0f; ++n2 ) {
					final float n3 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
					final float n4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
					this.world.spawnParticle("splash", this.x + n3, (double) (n + 1.0f), this.z + n4, this.xd, this.yd, this.zd);
				}
			}
			this.fallDistance = 0.0f;
			this.inWater = true;
			this.fire = 0;
		} else {
			this.inWater = false;
		}
		if (this.fire > 0) {
			if (this.fire % 20 == 0) {
				this.attackEntityFrom(null, 1);
			}
			--this.fire;
		}
		if (this.handleLavaMovement()) {
			this.attackEntityFrom(null, 10);
			this.fire = 600;
		}
		if (this.y < -64.0) {
			this.kill();
		}
		this.isFirstUpdate = false;
	}

	protected void kill() {
		this.setEntityDead();
	}

	public boolean isOffsetPositionInLiquid(final double xCoord, final double yCoord, final double zCoord) {
		final AABB offsetBoundingBox = this.bb.getOffsetBoundingBox(xCoord, yCoord, zCoord);
		return this.world.getCollidingBoundingBoxes(this, offsetBoundingBox).size() <= 0 && !this.world.getIsAnyLiquid(offsetBoundingBox);
	}

	public void moveEntity(double xCoord, double yCoord, double zCoord) {
		if (this.noClip) {
			this.bb.translate(xCoord, yCoord, zCoord);
			this.x = (this.bb.minX + this.bb.maxX) / 2.0;
			this.y = this.bb.minY + this.yOffset - this.ySize;
			this.z = (this.bb.minZ + this.bb.maxZ) / 2.0;
			return;
		}
		final double posX = this.x;
		final double posZ = this.z;
		final double n = xCoord;
		final double n2 = yCoord;
		final double n3 = zCoord;
		final AABB copy = this.bb.copy();
		final List collidingBoundingBoxes = this.world.getCollidingBoundingBoxes(this, this.bb.addCoord(xCoord, yCoord, zCoord));
		for ( int i = 0; i < collidingBoundingBoxes.size(); ++i ) {
			yCoord = ((AABB) collidingBoundingBoxes.get(i)).calculateYOffset(this.bb, yCoord);
		}
		this.bb.translate(0.0, yCoord, 0.0);
		if (!this.field_9293_aM && n2 != yCoord) {
			yCoord = (xCoord = (zCoord = 0.0));
		}
		final boolean b = this.onGround || (n2 != yCoord && n2 < 0.0);
		for ( int j = 0; j < collidingBoundingBoxes.size(); ++j ) {
			xCoord = ((AABB) collidingBoundingBoxes.get(j)).calculateXOffset(this.bb, xCoord);
		}
		this.bb.translate(xCoord, 0.0, 0.0);
		if (!this.field_9293_aM && n != xCoord) {
			yCoord = (xCoord = (zCoord = 0.0));
		}
		for ( int j = 0; j < collidingBoundingBoxes.size(); ++j ) {
			zCoord = ((AABB) collidingBoundingBoxes.get(j)).calculateZOffset(this.bb, zCoord);
		}
		this.bb.translate(0.0, 0.0, zCoord);
		if (!this.field_9293_aM && n3 != zCoord) {
			yCoord = (xCoord = (zCoord = 0.0));
		}
		if (this.stepHeight > 0.0f && b && this.ySize < 0.05f && (n != xCoord || n3 != zCoord)) {
			final double n4 = xCoord;
			final double n5 = yCoord;
			final double n6 = zCoord;
			xCoord = n;
			yCoord = this.stepHeight;
			zCoord = n3;
			final AABB copy2 = this.bb.copy();
			this.bb.set(copy);
			final List collidingBoundingBoxes2 = this.world.getCollidingBoundingBoxes(this, this.bb.addCoord(xCoord, yCoord, zCoord));
			for ( int k = 0; k < collidingBoundingBoxes2.size(); ++k ) {
				yCoord = ((AABB) collidingBoundingBoxes2.get(k)).calculateYOffset(this.bb, yCoord);
			}
			this.bb.translate(0.0, yCoord, 0.0);
			if (!this.field_9293_aM && n2 != yCoord) {
				yCoord = (xCoord = (zCoord = 0.0));
			}
			for ( int k = 0; k < collidingBoundingBoxes2.size(); ++k ) {
				xCoord = ((AABB) collidingBoundingBoxes2.get(k)).calculateXOffset(this.bb, xCoord);
			}
			this.bb.translate(xCoord, 0.0, 0.0);
			if (!this.field_9293_aM && n != xCoord) {
				yCoord = (xCoord = (zCoord = 0.0));
			}
			for ( int k = 0; k < collidingBoundingBoxes2.size(); ++k ) {
				zCoord = ((AABB) collidingBoundingBoxes2.get(k)).calculateZOffset(this.bb, zCoord);
			}
			this.bb.translate(0.0, 0.0, zCoord);
			if (!this.field_9293_aM && n3 != zCoord) {
				yCoord = (xCoord = (zCoord = 0.0));
			}
			if (n4 * n4 + n6 * n6 >= xCoord * xCoord + zCoord * zCoord) {
				xCoord = n4;
				yCoord = n5;
				zCoord = n6;
				this.bb.set(copy2);
			} else {
				this.ySize += 0.5;
			}
		}
		this.x = (this.bb.minX + this.bb.maxX) / 2.0;
		this.y = this.bb.minY + this.yOffset - this.ySize;
		this.z = (this.bb.minZ + this.bb.maxZ) / 2.0;
		this.isCollidedHorizontally = (n != xCoord || n3 != zCoord);
		this.onGround = (n2 != yCoord && n2 < 0.0);
		this.beenAttacked = (this.isCollidedHorizontally || n2 != yCoord);
		if (this.onGround) {
			if (this.fallDistance > 0.0f) {
				this.fall(this.fallDistance);
				this.fallDistance = 0.0f;
			}
		} else if (yCoord < 0.0) {
			this.fallDistance -= (float) yCoord;
		}
		if (n != xCoord) {
			this.xd = 0.0;
		}
		if (n2 != yCoord) {
			this.yd = 0.0;
		}
		if (n3 != zCoord) {
			this.zd = 0.0;
		}
		final double n4 = this.x - posX;
		final double n5 = this.z - posZ;
		this.distanceWalkedModified += (float) (sqrt(n4 * n4 + n5 * n5) * 0.6);
		if (this.canTriggerWalking) {
			final int floor_double = Mth.floor_double(this.x);
			final int floor_double2 = Mth.floor_double(this.y - 0.20000000298023224 - this.yOffset);
			final int floor_double3 = Mth.floor_double(this.z);
			final int k = this.world.getBlockId(floor_double, floor_double2, floor_double3);
			if (this.distanceWalkedModified > this.nextStepDistance && k > 0) {
				++this.nextStepDistance;
				final StepSound stepSound = Block.BLOCKS[k].stepSound;
				if (!Block.BLOCKS[k].blockMaterial.isLiquid()) {
					this.world.playSound(this, stepSound.stepSoundDir2(), stepSound.soundVolume() * 0.15f, stepSound.soundPitch());
				}
				Block.BLOCKS[k].onEntityWalking(this.world, floor_double, floor_double2, floor_double3, this);
			}
		}
		this.ySize *= 0.4f;
		final boolean handleWaterMovement = this.handleWaterMovement();
		if (this.world.isBoundingBoxBurning(this.bb)) {
			this.dealFireDamage(1);
			if (!handleWaterMovement) {
				++this.fire;
				if (this.fire == 0) {
					this.fire = 300;
				}
			}
		} else if (this.fire <= 0) {
			this.fire = -this.fireResistance;
		}
		if (handleWaterMovement && this.fire > 0) {
			this.world.playSound(this, "random.fizz", 0.7f, 1.6f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
			this.fire = -this.fireResistance;
		}
	}

	public AABB getBoundingBox() {
		return null;
	}

	protected void dealFireDamage(final int damage) {
		this.attackEntityFrom(null, damage);
	}

	protected void fall(final float nya1) {
	}

	public boolean handleWaterMovement() {
		return this.world.handleMaterialAcceleration(this.bb.grow(0.0, -0.4000000059604645, 0.0), EnumMaterial.WATER, this);
	}

	public boolean isInsideOfMaterial(final EnumMaterial material) {
		final double double1 = this.y + this.getEyeHeight();
		final int floor_double = Mth.floor_double(this.x);
		final int floor_float = (int) double1;
		final int floor_double2 = Mth.floor_double(this.z);
		final int blockId = this.world.getBlockId(floor_double, floor_float, floor_double2);
		return blockId != 0 && Block.BLOCKS[blockId].blockMaterial == material && double1 < floor_float + 1 - (LiquidBlock.getPercentAir(this.world.getBlockMetadata(floor_double, floor_float, floor_double2)) - 0.11111111f);
	}

	protected float getEyeHeight() {
		return 0.0f;
	}

	public boolean handleLavaMovement() {
		return this.world.isMaterialInBB(this.bb.grow(0.0, -0.4000000059604645, 0.0), EnumMaterial.LAVA);
	}

	public void moveFlying(float xCoord, float yCoord, final float zCoord) {
		float sqrt_f = sqrt(xCoord * xCoord + yCoord * yCoord);
		sqrt_f = clamp(0.01f, 1, sqrt_f);
		sqrt_f = zCoord / sqrt_f;

		xCoord *= sqrt_f;
		yCoord *= sqrt_f;

		final float sin = sin(toRadians(yRot));
		final float cos = cos(toRadians(yRot));

		this.xd += xCoord * cos - yCoord * sin;
		this.zd += yCoord * cos + xCoord * sin;
	}

	public float getEntityBrightness(final float float1) {
		return this.world.getLightBrightness(Mth.floor_double(this.x), Mth.floor_double(this.y - this.yOffset + (this.bb.maxY - this.bb.minY) * 0.66), Mth.floor_double(this.z));
	}

	public void setWorld(final World world) {
		this.world = world;
	}

	public void setPositionAndRotation(final double xCoord, final double yCoord, final double zCoord, final float yaw, final float pitch) {
		this.x = xCoord;
		this.xo = xCoord;
		final double n = yCoord + this.yOffset;
		this.y = n;
		this.yo = n;
		this.z = zCoord;
		this.zo = zCoord;
		this.yRot = yaw;
		this.xRot = pitch;
		this.setPosition(this.x, this.y, this.z);
	}

	public float getDistanceToEntity(final Entity entity) {
		final float n = (float) (this.x - entity.x);
		final float n2 = (float) (this.y - entity.y);
		final float n3 = (float) (this.z - entity.z);
		return sqrt(n * n + n2 * n2 + n3 * n3);
	}

	public double getDistanceSq(final double xCoord, final double yCoord, final double zCoord) {
		final double n = this.x - xCoord;
		final double n2 = this.y - yCoord;
		final double n3 = this.z - zCoord;
		return n * n + n2 * n2 + n3 * n3;
	}

	public double getDistance(final double xCoord, final double yCoord, final double zCoord) {
		final double n = this.x - xCoord;
		final double n2 = this.y - yCoord;
		final double n3 = this.z - zCoord;
		return sqrt(n * n + n2 * n2 + n3 * n3);
	}

	public double getDistanceSqToEntity(final Entity entity) {
		final double n = this.x - entity.x;
		final double n2 = this.y - entity.y;
		final double n3 = this.z - entity.z;
		return n * n + n2 * n2 + n3 * n3;
	}

	public void onCollideWithPlayer(final Player entityPlayer) {
	}

	public void applyEntityCollision(final Entity entity) {
		double n = entity.x - this.x;
		double n2 = entity.z - this.z;
		double abs_max = max(abs(n), abs(n2));
		if (abs_max >= 0.009999999776482582) {
			abs_max = sqrt(abs_max);
			n /= abs_max;
			n2 /= abs_max;
			double n3 = 1.0 / abs_max;
			if (n3 > 1.0) {
				n3 = 1.0;
			}
			n *= n3;
			n2 *= n3;
			n *= 0.05000000074505806;
			n2 *= 0.05000000074505806;
			n *= 1.0f - this.entityCollisionReduction;
			n2 *= 1.0f - this.entityCollisionReduction;
			this.addVelocity(-n, 0.0, -n2);
			entity.addVelocity(n, 0.0, n2);
		}
	}

	public void addVelocity(final double xCoord, final double yCoord, final double zCoord) {
		this.xd += xCoord;
		this.yd += yCoord;
		this.zd += zCoord;
	}

	public boolean attackEntityFrom(final Entity entity, final int nya1) {
		return false;
	}

	public boolean canBeCollidedWith() {
		return false;
	}

	public boolean canBePushed() {
		return false;
	}

	public void addToPlayerScore(final Entity entity, final int score) {
	}

	public boolean isInRangeToRenderVec3D(final Vec3 var1) {
		final double n = this.x - var1.x;
		final double n2 = this.y - var1.y;
		final double n3 = this.z - var1.z;
		return this.isInRangeToRenderDist(n * n + n2 * n2 + n3 * n3);
	}

	public boolean isInRangeToRenderDist(final double distance) {
		double averageEdgeLength = this.bb.getAverageEdgeLength();
		averageEdgeLength *= 64.0;
		return distance < averageEdgeLength * averageEdgeLength;
	}

	public String addToPlayerScore() {
		return null;
	}

	public boolean addEntityID(final NBTTagCompound nbt) {
		final String entityString = this.getEntityString();
		if (this.isDead || entityString == null) {
			return false;
		}
		nbt.setString("id", entityString);
		this.writeToNBT(nbt);
		return true;
	}

	public void writeToNBT(final NBTTagCompound nbt) {
		nbt.setTag("Pos", (NBTBase) this.newDoubleNBTList(this.x, this.y, this.z));
		nbt.setTag("Motion", (NBTBase) this.newDoubleNBTList(this.xd, this.yd, this.zd));
		nbt.setTag("Rotation", (NBTBase) this.newFloatNBTList(this.yRot, this.xRot));
		nbt.setFloat("FallDistance", this.fallDistance);
		nbt.setShort("Fire", (short) this.fire);
		nbt.setShort("Air", (short) this.air);
		nbt.setBoolean("OnGround", this.onGround);
		this.writeEntityToNBT(nbt);
	}

	public void readFromNBT(final NBTTagCompound nbt) {
		final NBTTagList tagList = nbt.getTagList("Pos");
		final NBTTagList tagList2 = nbt.getTagList("Motion");
		final NBTTagList tagList3 = nbt.getTagList("Rotation");
		this.setPosition(0.0, 0.0, 0.0);
		this.xd = ((NBTTagDouble) tagList2.getTag(0)).doubleValue;
		this.yd = ((NBTTagDouble) tagList2.getTag(1)).doubleValue;
		this.zd = ((NBTTagDouble) tagList2.getTag(2)).doubleValue;
		final double doubleValue = ((NBTTagDouble) tagList.getTag(0)).doubleValue;
		this.x = doubleValue;
		this.lastTickPosX = doubleValue;
		this.xo = doubleValue;
		final double doubleValue2 = ((NBTTagDouble) tagList.getTag(1)).doubleValue;
		this.y = doubleValue2;
		this.lastTickPosY = doubleValue2;
		this.yo = doubleValue2;
		final double doubleValue3 = ((NBTTagDouble) tagList.getTag(2)).doubleValue;
		this.z = doubleValue3;
		this.lastTickPosZ = doubleValue3;
		this.zo = doubleValue3;
		final float floatValue = ((NBTTagFloat) tagList3.getTag(0)).floatValue;
		this.yRot = floatValue;
		this.prevRotationYaw = floatValue;
		final float floatValue2 = ((NBTTagFloat) tagList3.getTag(1)).floatValue;
		this.xRot = floatValue2;
		this.prevRotationPitch = floatValue2;
		this.fallDistance = nbt.getFloat("FallDistance");
		this.fire = nbt.getShort("Fire");
		this.air = nbt.getShort("Air");
		this.onGround = nbt.getBoolean("OnGround");
		this.setPosition(this.x, this.y, this.z);
		this.readEntityFromNBT(nbt);
	}

	protected final String getEntityString() {
		return EntityList.getEntityString(this);
	}

	protected abstract void readEntityFromNBT(final NBTTagCompound nbtTagCompound);

	protected abstract void writeEntityToNBT(final NBTTagCompound nbtTagCompound);

	protected NBTTagList newDoubleNBTList(final double... arr) {
		final NBTTagList list = new NBTTagList();
		for ( int length = arr.length, i = 0; i < length; ++i ) {
			list.setTag(new NBTTagDouble(arr[i]));
		}
		return list;
	}

	protected NBTTagList newFloatNBTList(final float... arr) {
		final NBTTagList list = new NBTTagList();
		for ( int length = arr.length, i = 0; i < length; ++i ) {
			list.setTag(new NBTTagFloat(arr[i]));
		}
		return list;
	}

	public EntityItem dropItemWithOffset(final int integer1, final int integer2) {
		return this.entityDropItem(integer1, integer2, 0.0f);
	}

	public EntityItem entityDropItem(final int integer1, final int integer2, final float float3) {
		final EntityItem entity = new EntityItem(this.world, this.x, this.y + float3, this.z, new ItemStack(integer1, integer2));
		entity.delayBeforeCanPickup = 10;
		this.world.onEntityJoin(entity);
		return entity;
	}

	public boolean isEntityAlive() {
		return !this.isDead;
	}

	public boolean isEntityInsideOpaqueBlock() {
		return this.world.isBlockNormalCube(Mth.floor_double(this.x), Mth.floor_double(this.y + this.getEyeHeight()), Mth.floor_double(this.z));
	}

	public boolean interact(final Player entityPlayer) {
		return false;
	}

	public AABB getCollisionBox(final Entity entity) {
		return null;
	}

	public void updateRidden() {
		if (this.ridingEntity.isDead) {
			this.ridingEntity = null;
			return;
		}
		this.xd = 0.0;
		this.yd = 0.0;
		this.zd = 0.0;
		this.onUpdate();
		this.setPosition(this.ridingEntity.x, this.ridingEntity.y + this.yOffset + this.ridingEntity.getYOffset(), this.ridingEntity.z);
		this.entityRiderYawDelta += this.ridingEntity.yRot - this.ridingEntity.prevRotationYaw;
		this.entityRiderPitchDelta += this.ridingEntity.xRot - this.ridingEntity.prevRotationPitch;
		while(this.entityRiderYawDelta >= 180.0) {
			this.entityRiderYawDelta -= 360.0;
		}
		while(this.entityRiderYawDelta < -180.0) {
			this.entityRiderYawDelta += 360.0;
		}
		while(this.entityRiderPitchDelta >= 180.0) {
			this.entityRiderPitchDelta -= 360.0;
		}
		while(this.entityRiderPitchDelta < -180.0) {
			this.entityRiderPitchDelta += 360.0;
		}
		double n = this.entityRiderYawDelta * 0.5;
		double n2 = this.entityRiderPitchDelta * 0.5;
		final float n3 = 10.0f;
		if (n > n3) {
			n = n3;
		}
		if (n < -n3) {
			n = -n3;
		}
		if (n2 > n3) {
			n2 = n3;
		}
		if (n2 < -n3) {
			n2 = -n3;
		}
		this.entityRiderYawDelta -= n;
		this.entityRiderPitchDelta -= n2;
		this.yRot += (float) n;
		this.xRot += (float) n2;
	}

	public double getYOffset() {
		return this.height * 0.75;
	}

	public void mountEntity(final Entity entity) {
		this.entityRiderPitchDelta = 0.0;
		this.entityRiderYawDelta = 0.0;
		if (this.ridingEntity == entity) {
			this.ridingEntity.passenger = null;
			this.ridingEntity = null;
			return;
		}
		if (this.ridingEntity != null) {
			this.ridingEntity.passenger = null;
		}
		if (entity.passenger != null) {
			entity.passenger.ridingEntity = null;
		}
		this.ridingEntity = entity;
		entity.passenger = this;
	}

	public void joinWorld(World world) {
		world.onEntityJoin(this);
	}

}
