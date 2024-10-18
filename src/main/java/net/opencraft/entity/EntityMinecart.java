
package net.opencraft.entity;

import static org.joml.Math.*;

import java.util.List;

import net.opencraft.blocks.Block;
import net.opencraft.inventory.IInventory;
import net.opencraft.item.Item;
import net.opencraft.item.ItemStack;
import net.opencraft.nbt.NBTBase;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.nbt.NBTTagList;
import net.opencraft.physics.AABB;
import net.opencraft.util.Mth;
import net.opencraft.util.Vec3;
import net.opencraft.world.World;

public class EntityMinecart extends Entity implements IInventory {

	private ItemStack[] cargoItems;
	public int minecartCurrentDamage;
	public int minecartTimeSinceHit;
	public int minecartRockDirection;
	private boolean isInReverse;
	private static final int[][][] MATRIX;

	public EntityMinecart(final World world) {
		super(world);
		this.cargoItems = new ItemStack[36];
		this.minecartCurrentDamage = 0;
		this.minecartTimeSinceHit = 0;
		this.minecartRockDirection = 1;
		this.isInReverse = false;
		this.preventEntitySpawning = true;
		this.setSize(0.98f, 0.7f);
		this.yOffset = this.height / 2.0f;
		this.canTriggerWalking = false;
	}

	@Override
	public AABB getCollisionBox(final Entity entity) {
		return entity.bb;
	}

	@Override
	public AABB getBoundingBox() {
		return this.bb;
	}

	@Override
	public boolean canBePushed() {
		return true;
	}

	public EntityMinecart(final World fe, final double xCoord, final double yCoord, final double zCoord) {
		this(fe);
		this.setPosition(xCoord, yCoord + this.yOffset, zCoord);
		this.xd = 0.0;
		this.yd = 0.0;
		this.zd = 0.0;
		this.xo = xCoord;
		this.yo = yCoord;
		this.zo = zCoord;
	}

	@Override
	public double getYOffset() {
		return this.height * 0.2;
	}

	@Override
	public boolean attackEntityFrom(final Entity entity, final int nya1) {
		this.minecartRockDirection = -this.minecartRockDirection;
		this.minecartTimeSinceHit = 10;
		this.minecartCurrentDamage += nya1 * 10;
		if (this.minecartCurrentDamage > 40) {
			this.entityDropItem(Item.minecart.shiftedIndex, 1, 0.0f);
			this.setEntityDead();
		}
		return true;
	}

	@Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	@Override
	public void setEntityDead() {
		for ( int i = 0; i < this.getSizeInventory(); ++i ) {
			final ItemStack stackInSlot = this.getStackInSlot(i);
			if (stackInSlot != null) {
				final float n = this.rand.nextFloat() * 0.8f + 0.1f;
				final float n2 = this.rand.nextFloat() * 0.8f + 0.1f;
				final float n3 = this.rand.nextFloat() * 0.8f + 0.1f;
				while(stackInSlot.stackSize > 0) {
					int stackSize = this.rand.nextInt(21) + 10;
					if (stackSize > stackInSlot.stackSize) {
						stackSize = stackInSlot.stackSize;
					}
					final ItemStack itemStack = stackInSlot;
					itemStack.stackSize -= stackSize;
					final EntityItem entity = new EntityItem(this.world, this.x + n, this.y + n2, this.z + n3, new ItemStack(stackInSlot.itemID, stackSize, stackInSlot.itemDamage));
					final float n4 = 0.05f;
					entity.xd = (float) this.rand.nextGaussian() * n4;
					entity.yd = (float) this.rand.nextGaussian() * n4 + 0.2f;
					entity.zd = (float) this.rand.nextGaussian() * n4;
					this.world.onEntityJoin(entity);
				}
			}
		}
		super.setEntityDead();
	}

	@Override
	public void onUpdate() {
		if (this.minecartTimeSinceHit > 0) {
			--this.minecartTimeSinceHit;
		}
		if (this.minecartCurrentDamage > 0) {
			--this.minecartCurrentDamage;
		}
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		this.yd -= 0.03999999910593033;
		final int floor_double = Mth.floor_double(this.x);
		int floor_double2 = Mth.floor_double(this.y);
		final int floor_double3 = Mth.floor_double(this.z);
		if (this.world.getBlockId(floor_double, floor_double2 - 1, floor_double3) == Block.rail.id) {
			--floor_double2;
		}
		final double n = 0.4;
		final double n2 = 0.0078125;
		if (this.world.getBlockId(floor_double, floor_double2, floor_double3) == Block.rail.id) {
			final Vec3 pos = this.getPos(this.x, this.y, this.z);
			final int blockMetadata = this.world.getBlockMetadata(floor_double, floor_double2, floor_double3);
			this.y = floor_double2;
			if (blockMetadata >= 2 && blockMetadata <= 5) {
				this.y = floor_double2 + 1;
			}
			if (blockMetadata == 2) {
				this.xd -= n2;
			}
			if (blockMetadata == 3) {
				this.xd += n2;
			}
			if (blockMetadata == 4) {
				this.zd += n2;
			}
			if (blockMetadata == 5) {
				this.zd -= n2;
			}
			final int[][] array = EntityMinecart.MATRIX[blockMetadata];
			double n3 = array[1][0] - array[0][0];
			double n4 = array[1][2] - array[0][2];
			final double sqrt = sqrt(n3 * n3 + n4 * n4);
			if (this.xd * n3 + this.zd * n4 < 0.0) {
				n3 = -n3;
				n4 = -n4;
			}
			double n5 = sqrt(this.xd * this.xd + this.zd * this.zd);
			this.xd = n5 * n3 / sqrt;
			this.zd = n5 * n4 / sqrt;
			double n6 = 0.0;
			final double n7 = floor_double + 0.5 + array[0][0] * 0.5;
			final double n8 = floor_double3 + 0.5 + array[0][2] * 0.5;
			final double n9 = floor_double + 0.5 + array[1][0] * 0.5;
			final double n10 = floor_double3 + 0.5 + array[1][2] * 0.5;
			n3 = n9 - n7;
			n4 = n10 - n8;
			if (n3 == 0.0) {
				this.x = floor_double + 0.5;
				n6 = this.z - floor_double3;
			} else if (n4 == 0.0) {
				this.z = floor_double3 + 0.5;
				n6 = this.x - floor_double;
			} else {
				final double motionX = this.x - n7;
				final double motionZ = this.z - n8;
				n6 = (motionX * n3 + motionZ * n4) * 2.0;
			}
			this.x = n7 + n3 * n6;
			this.z = n8 + n4 * n6;
			this.setPosition(this.x, this.y + this.yOffset, this.z);
			double motionX = this.xd;
			double motionZ = this.zd;
			if (this.passenger != null) {
				motionX *= 0.75;
				motionZ *= 0.75;
			}
			if (motionX < -n) {
				motionX = -n;
			}
			if (motionX > n) {
				motionX = n;
			}
			if (motionZ < -n) {
				motionZ = -n;
			}
			if (motionZ > n) {
				motionZ = n;
			}
			this.moveEntity(motionX, 0.0, motionZ);
			if (array[0][1] != 0 && Mth.floor_double(this.x) - floor_double == array[0][0] && Mth.floor_double(this.z) - floor_double3 == array[0][2]) {
				this.setPosition(this.x, this.y + array[0][1], this.z);
			} else if (array[1][1] != 0 && Mth.floor_double(this.x) - floor_double == array[1][0] && Mth.floor_double(this.z) - floor_double3 == array[1][2]) {
				this.setPosition(this.x, this.y + array[1][1], this.z);
			}
			if (this.passenger != null) {
				this.xd *= 0.996999979019165;
				this.yd *= 0.0;
				this.zd *= 0.996999979019165;
			} else {
				this.xd *= 0.9599999785423279;
				this.yd *= 0.0;
				this.zd *= 0.9599999785423279;
			}
			final Vec3 pos2 = this.getPos(this.x, this.y, this.z);
			if (pos2 != null && pos != null) {
				final double n11 = (pos.y - pos2.y) * 0.05;
				n5 = sqrt(this.xd * this.xd + this.zd * this.zd);
				if (n5 > 0.0) {
					this.xd = this.xd / n5 * (n5 + n11);
					this.zd = this.zd / n5 * (n5 + n11);
				}
				this.setPosition(this.x, pos2.y, this.z);
			}
			final int floor_double4 = Mth.floor_double(this.x);
			final int floor_double5 = Mth.floor_double(this.z);
			if (floor_double4 != floor_double || floor_double5 != floor_double3) {
				n5 = sqrt(this.xd * this.xd + this.zd * this.zd);
				this.xd = n5 * (floor_double4 - floor_double);
				this.zd = n5 * (floor_double5 - floor_double3);
			}
		} else {
			if (this.xd < -n) {
				this.xd = -n;
			}
			if (this.xd > n) {
				this.xd = n;
			}
			if (this.zd < -n) {
				this.zd = -n;
			}
			if (this.zd > n) {
				this.zd = n;
			}
			if (this.onGround) {
				this.xd *= 0.5;
				this.yd *= 0.5;
				this.zd *= 0.5;
			}
			this.moveEntity(this.xd, this.yd, this.zd);
			if (!this.onGround) {
				this.xd *= 0.949999988079071;
				this.yd *= 0.949999988079071;
				this.zd *= 0.949999988079071;
			}
		}
		this.xRot = 0.0f;
		final double n12 = this.xo - this.x;
		final double n13 = this.zo - this.z;
		if (n12 * n12 + n13 * n13 > 0.001) {
			this.yRot = (float) toRadians(atan2(n13, n12));
			if (this.isInReverse) {
				this.yRot += 180.0f;
			}
		}
		double n14;
		for ( n14 = this.yRot - this.prevRotationYaw; n14 >= 180.0; n14 -= 360.0 ) {
		}
		while(n14 < -180.0) {
			n14 += 360.0;
		}
		if (n14 < -170.0 || n14 >= 170.0) {
			this.yRot += 180.0f;
			this.isInReverse = !this.isInReverse;
		}
		this.setRotation(this.yRot, this.xRot);
		final List entitiesWithinAABBExcludingEntity = this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.grow(0.20000000298023224, 0.0, 0.20000000298023224));
		if (entitiesWithinAABBExcludingEntity != null && entitiesWithinAABBExcludingEntity.size() > 0) {
			for ( int i = 0; i < entitiesWithinAABBExcludingEntity.size(); ++i ) {
				final Entity entity = (Entity) entitiesWithinAABBExcludingEntity.get(i);
				if (entity != this.passenger && entity.canBePushed() && entity instanceof EntityMinecart) {
					entity.applyEntityCollision(this);
				}
			}
		}
		if (this.passenger != null && this.passenger.isDead) {
			this.passenger = null;
		}
	}

	public Vec3 getPosOffset(double double1, double double2, double double3, final double double4) {
		final int floor_double = Mth.floor_double(double1);
		int floor_double2 = Mth.floor_double(double2);
		final int floor_double3 = Mth.floor_double(double3);
		if (this.world.getBlockId(floor_double, floor_double2 - 1, floor_double3) == Block.rail.id) {
			--floor_double2;
		}
		if (this.world.getBlockId(floor_double, floor_double2, floor_double3) == Block.rail.id) {
			final int blockMetadata = this.world.getBlockMetadata(floor_double, floor_double2, floor_double3);
			double2 = floor_double2;
			if (blockMetadata >= 2 && blockMetadata <= 5) {
				double2 = floor_double2 + 1;
			}
			final int[][] array = EntityMinecart.MATRIX[blockMetadata];
			double n = array[1][0] - array[0][0];
			double n2 = array[1][2] - array[0][2];
			final double sqrt = sqrt(n * n + n2 * n2);
			n /= sqrt;
			n2 /= sqrt;
			double1 += n * double4;
			double3 += n2 * double4;
			if (array[0][1] != 0 && Mth.floor_double(double1) - floor_double == array[0][0] && Mth.floor_double(double3) - floor_double3 == array[0][2]) {
				double2 += array[0][1];
			} else if (array[1][1] != 0 && Mth.floor_double(double1) - floor_double == array[1][0] && Mth.floor_double(double3) - floor_double3 == array[1][2]) {
				double2 += array[1][1];
			}
			return this.getPos(double1, double2, double3);
		}
		return null;
	}

	public Vec3 getPos(double double1, double double2, double double3) {
		final int floor_double = Mth.floor_double(double1);
		int floor_double2 = Mth.floor_double(double2);
		final int floor_double3 = Mth.floor_double(double3);
		if (this.world.getBlockId(floor_double, floor_double2 - 1, floor_double3) == Block.rail.id) {
			--floor_double2;
		}
		if (this.world.getBlockId(floor_double, floor_double2, floor_double3) == Block.rail.id) {
			final int blockMetadata = this.world.getBlockMetadata(floor_double, floor_double2, floor_double3);
			double2 = floor_double2;
			if (blockMetadata >= 2 && blockMetadata <= 5) {
				double2 = floor_double2 + 1;
			}
			final int[][] array = EntityMinecart.MATRIX[blockMetadata];
			double n = 0.0;
			final double n2 = floor_double + 0.5 + array[0][0] * 0.5;
			final double n3 = floor_double2 + 0.5 + array[0][1] * 0.5;
			final double n4 = floor_double3 + 0.5 + array[0][2] * 0.5;
			final double n5 = floor_double + 0.5 + array[1][0] * 0.5;
			final double n6 = floor_double2 + 0.5 + array[1][1] * 0.5;
			final double n7 = floor_double3 + 0.5 + array[1][2] * 0.5;
			final double n8 = n5 - n2;
			final double n9 = (n6 - n3) * 2.0;
			final double n10 = n7 - n4;
			if (n8 == 0.0) {
				double1 = floor_double + 0.5;
				n = double3 - floor_double3;
			} else if (n10 == 0.0) {
				double3 = floor_double3 + 0.5;
				n = double1 - floor_double;
			} else {
				n = ((double1 - n2) * n8 + (double3 - n4) * n10) * 2.0;
			}
			double1 = n2 + n8 * n;
			double2 = n3 + n9 * n;
			double3 = n4 + n10 * n;
			if (n9 < 0.0) {
				++double2;
			}
			if (n9 > 0.0) {
				double2 += 0.5;
			}
			return Vec3.newTemp(double1, double2, double3);
		}
		return null;
	}

	@Override
	protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
		final NBTTagList hm = new NBTTagList();
		for ( int i = 0; i < this.cargoItems.length; ++i ) {
			if (this.cargoItems[i] != null) {
				final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
				nbtTagCompound2.setByte("Slot", (byte) i);
				this.cargoItems[i].writeToNBT(nbtTagCompound2);
				hm.setTag(nbtTagCompound2);
			}
		}
		nbtTagCompound.setTag("Items", (NBTBase) hm);
	}

	@Override
	protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
		final NBTTagList tagList = nbtTagCompound.getTagList("Items");
		this.cargoItems = new ItemStack[this.getSizeInventory()];
		for ( int i = 0; i < tagList.tagCount(); ++i ) {
			final NBTTagCompound ae = (NBTTagCompound) tagList.getTag(i);
			final int n = ae.getByte("Slot") & 0xFF;
			if (n >= 0 && n < this.cargoItems.length) {
				this.cargoItems[n] = new ItemStack(ae);
			}
		}
	}

	@Override
	public void applyEntityCollision(final Entity entity) {
		if (entity == this.passenger) {
			return;
		}
		double n = entity.x - this.x;
		double n2 = entity.z - this.z;
		double double1 = n * n + n2 * n2;
		if (double1 >= 9.999999747378752E-5) {
			double1 = Mth.sqrt_double(double1);
			n /= double1;
			n2 /= double1;
			double n3 = 1.0 / double1;
			if (n3 > 1.0) {
				n3 = 1.0;
			}
			n *= n3;
			n2 *= n3;
			n *= 0.10000000149011612;
			n2 *= 0.10000000149011612;
			n *= 1.0f - this.entityCollisionReduction;
			n2 *= 1.0f - this.entityCollisionReduction;
			n *= 0.5;
			n2 *= 0.5;
			if (entity instanceof EntityMinecart) {
				final double n4 = (entity.xd + this.xd) / 2.0;
				final double n5 = (entity.zd + this.zd) / 2.0;
				final double n6 = 0.0;
				this.zd = n6;
				this.xd = n6;
				this.addVelocity(n4 - n, 0.0, n5 - n2);
				final double n7 = 0.0;
				entity.zd = n7;
				entity.xd = n7;
				entity.addVelocity(n4 + n, 0.0, n5 + n2);
			} else {
				this.addVelocity(-n, 0.0, -n2);
				entity.addVelocity(n / 4.0, 0.0, n2 / 4.0);
			}
		}
	}

	public int getSizeInventory() {
		return 27;
	}

	public ItemStack getStackInSlot(final int integer) {
		return this.cargoItems[integer];
	}

	public ItemStack decrStackSize(final int integer1, final int integer2) {
		if (this.cargoItems[integer1] == null) {
			return null;
		}
		if (this.cargoItems[integer1].stackSize <= integer2) {
			final ItemStack itemStack = this.cargoItems[integer1];
			this.cargoItems[integer1] = null;
			return itemStack;
		}
		final ItemStack splitStack = this.cargoItems[integer1].splitStack(integer2);
		if (this.cargoItems[integer1].stackSize == 0) {
			this.cargoItems[integer1] = null;
		}
		return splitStack;
	}

	public void setInventorySlotContents(final int integer, final ItemStack hw) {
		this.cargoItems[integer] = hw;
		if (hw != null && hw.stackSize > this.getInventoryStackLimit()) {
			hw.stackSize = this.getInventoryStackLimit();
		}
	}

	public String getInvName() {
		return "Minecart";
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public void onInventoryChanged() {
	}

	@Override
	public boolean interact(final Player entityPlayer) {
		entityPlayer.mountEntity(this);
		return true;
	}

	static {
		MATRIX = new int[][][] { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1, 0, 0 }, { 1, 0, 0 } }, { { -1, -1, 0 }, { 1, 0, 0 } }, { { -1, 0, 0 }, { 1, -1, 0 } }, { { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1, 0, 0 } }, { { 0, 0, 1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { 1, 0, 0 } } };
	}

}
