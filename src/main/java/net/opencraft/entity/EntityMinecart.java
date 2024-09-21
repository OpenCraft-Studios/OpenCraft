
package net.opencraft.entity;

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
        return entity.boundingBox;
    }

    @Override
    public AABB getBoundingBox() {
        return this.boundingBox;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    public EntityMinecart(final World fe, final double xCoord, final double yCoord, final double zCoord) {
        this(fe);
        this.setPosition(xCoord, yCoord + this.yOffset, zCoord);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = xCoord;
        this.prevPosY = yCoord;
        this.prevPosZ = zCoord;
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
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            final ItemStack stackInSlot = this.getStackInSlot(i);
            if (stackInSlot != null) {
                final float n = this.rand.nextFloat() * 0.8f + 0.1f;
                final float n2 = this.rand.nextFloat() * 0.8f + 0.1f;
                final float n3 = this.rand.nextFloat() * 0.8f + 0.1f;
                while (stackInSlot.stackSize > 0) {
                    int stackSize = this.rand.nextInt(21) + 10;
                    if (stackSize > stackInSlot.stackSize) {
                        stackSize = stackInSlot.stackSize;
                    }
                    final ItemStack itemStack = stackInSlot;
                    itemStack.stackSize -= stackSize;
                    final EntityItem entity = new EntityItem(this.worldObj, this.posX + n, this.posY + n2, this.posZ + n3, new ItemStack(stackInSlot.itemID, stackSize, stackInSlot.itemDamage));
                    final float n4 = 0.05f;
                    entity.motionX = (float) this.rand.nextGaussian() * n4;
                    entity.motionY = (float) this.rand.nextGaussian() * n4 + 0.2f;
                    entity.motionZ = (float) this.rand.nextGaussian() * n4;
                    this.worldObj.entityJoinedWorld(entity);
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
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033;
        final int floor_double = Mth.floor_double(this.posX);
        int floor_double2 = Mth.floor_double(this.posY);
        final int floor_double3 = Mth.floor_double(this.posZ);
        if (this.worldObj.getBlockId(floor_double, floor_double2 - 1, floor_double3) == Block.rail.blockID) {
            --floor_double2;
        }
        final double n = 0.4;
        final double n2 = 0.0078125;
        if (this.worldObj.getBlockId(floor_double, floor_double2, floor_double3) == Block.rail.blockID) {
            final Vec3 pos = this.getPos(this.posX, this.posY, this.posZ);
            final int blockMetadata = this.worldObj.getBlockMetadata(floor_double, floor_double2, floor_double3);
            this.posY = floor_double2;
            if (blockMetadata >= 2 && blockMetadata <= 5) {
                this.posY = floor_double2 + 1;
            }
            if (blockMetadata == 2) {
                this.motionX -= n2;
            }
            if (blockMetadata == 3) {
                this.motionX += n2;
            }
            if (blockMetadata == 4) {
                this.motionZ += n2;
            }
            if (blockMetadata == 5) {
                this.motionZ -= n2;
            }
            final int[][] array = EntityMinecart.MATRIX[blockMetadata];
            double n3 = array[1][0] - array[0][0];
            double n4 = array[1][2] - array[0][2];
            final double sqrt = Math.sqrt(n3 * n3 + n4 * n4);
            if (this.motionX * n3 + this.motionZ * n4 < 0.0) {
                n3 = -n3;
                n4 = -n4;
            }
            double n5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.motionX = n5 * n3 / sqrt;
            this.motionZ = n5 * n4 / sqrt;
            double n6 = 0.0;
            final double n7 = floor_double + 0.5 + array[0][0] * 0.5;
            final double n8 = floor_double3 + 0.5 + array[0][2] * 0.5;
            final double n9 = floor_double + 0.5 + array[1][0] * 0.5;
            final double n10 = floor_double3 + 0.5 + array[1][2] * 0.5;
            n3 = n9 - n7;
            n4 = n10 - n8;
            if (n3 == 0.0) {
                this.posX = floor_double + 0.5;
                n6 = this.posZ - floor_double3;
            } else if (n4 == 0.0) {
                this.posZ = floor_double3 + 0.5;
                n6 = this.posX - floor_double;
            } else {
                final double motionX = this.posX - n7;
                final double motionZ = this.posZ - n8;
                n6 = (motionX * n3 + motionZ * n4) * 2.0;
            }
            this.posX = n7 + n3 * n6;
            this.posZ = n8 + n4 * n6;
            this.setPosition(this.posX, this.posY + this.yOffset, this.posZ);
            double motionX = this.motionX;
            double motionZ = this.motionZ;
            if (this.riddenByEntity != null) {
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
            if (array[0][1] != 0 && Mth.floor_double(this.posX) - floor_double == array[0][0] && Mth.floor_double(this.posZ) - floor_double3 == array[0][2]) {
                this.setPosition(this.posX, this.posY + array[0][1], this.posZ);
            } else if (array[1][1] != 0 && Mth.floor_double(this.posX) - floor_double == array[1][0] && Mth.floor_double(this.posZ) - floor_double3 == array[1][2]) {
                this.setPosition(this.posX, this.posY + array[1][1], this.posZ);
            }
            if (this.riddenByEntity != null) {
                this.motionX *= 0.996999979019165;
                this.motionY *= 0.0;
                this.motionZ *= 0.996999979019165;
            } else {
                this.motionX *= 0.9599999785423279;
                this.motionY *= 0.0;
                this.motionZ *= 0.9599999785423279;
            }
            final Vec3 pos2 = this.getPos(this.posX, this.posY, this.posZ);
            if (pos2 != null && pos != null) {
                final double n11 = (pos.y - pos2.y) * 0.05;
                n5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                if (n5 > 0.0) {
                    this.motionX = this.motionX / n5 * (n5 + n11);
                    this.motionZ = this.motionZ / n5 * (n5 + n11);
                }
                this.setPosition(this.posX, pos2.y, this.posZ);
            }
            final int floor_double4 = Mth.floor_double(this.posX);
            final int floor_double5 = Mth.floor_double(this.posZ);
            if (floor_double4 != floor_double || floor_double5 != floor_double3) {
                n5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                this.motionX = n5 * (floor_double4 - floor_double);
                this.motionZ = n5 * (floor_double5 - floor_double3);
            }
        } else {
            if (this.motionX < -n) {
                this.motionX = -n;
            }
            if (this.motionX > n) {
                this.motionX = n;
            }
            if (this.motionZ < -n) {
                this.motionZ = -n;
            }
            if (this.motionZ > n) {
                this.motionZ = n;
            }
            if (this.onGround) {
                this.motionX *= 0.5;
                this.motionY *= 0.5;
                this.motionZ *= 0.5;
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            if (!this.onGround) {
                this.motionX *= 0.949999988079071;
                this.motionY *= 0.949999988079071;
                this.motionZ *= 0.949999988079071;
            }
        }
        this.rotationPitch = 0.0f;
        final double n12 = this.prevPosX - this.posX;
        final double n13 = this.prevPosZ - this.posZ;
        if (n12 * n12 + n13 * n13 > 0.001) {
            this.rotationYaw = (float) (Math.atan2(n13, n12) * 180.0 / 3.141592653589793);
            if (this.isInReverse) {
                this.rotationYaw += 180.0f;
            }
        }
        double n14;
        for (n14 = this.rotationYaw - this.prevRotationYaw; n14 >= 180.0; n14 -= 360.0) {
        }
        while (n14 < -180.0) {
            n14 += 360.0;
        }
        if (n14 < -170.0 || n14 >= 170.0) {
            this.rotationYaw += 180.0f;
            this.isInReverse = !this.isInReverse;
        }
        this.setRotation(this.rotationYaw, this.rotationPitch);
        final List entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224, 0.0, 0.20000000298023224));
        if (entitiesWithinAABBExcludingEntity != null && entitiesWithinAABBExcludingEntity.size() > 0) {
            for (int i = 0; i < entitiesWithinAABBExcludingEntity.size(); ++i) {
                final Entity entity = (Entity) entitiesWithinAABBExcludingEntity.get(i);
                if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntityMinecart) {
                    entity.applyEntityCollision(this);
                }
            }
        }
        if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
            this.riddenByEntity = null;
        }
    }

    public Vec3 getPosOffset(double double1, double double2, double double3, final double double4) {
        final int floor_double = Mth.floor_double(double1);
        int floor_double2 = Mth.floor_double(double2);
        final int floor_double3 = Mth.floor_double(double3);
        if (this.worldObj.getBlockId(floor_double, floor_double2 - 1, floor_double3) == Block.rail.blockID) {
            --floor_double2;
        }
        if (this.worldObj.getBlockId(floor_double, floor_double2, floor_double3) == Block.rail.blockID) {
            final int blockMetadata = this.worldObj.getBlockMetadata(floor_double, floor_double2, floor_double3);
            double2 = floor_double2;
            if (blockMetadata >= 2 && blockMetadata <= 5) {
                double2 = floor_double2 + 1;
            }
            final int[][] array = EntityMinecart.MATRIX[blockMetadata];
            double n = array[1][0] - array[0][0];
            double n2 = array[1][2] - array[0][2];
            final double sqrt = Math.sqrt(n * n + n2 * n2);
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
        if (this.worldObj.getBlockId(floor_double, floor_double2 - 1, floor_double3) == Block.rail.blockID) {
            --floor_double2;
        }
        if (this.worldObj.getBlockId(floor_double, floor_double2, floor_double3) == Block.rail.blockID) {
            final int blockMetadata = this.worldObj.getBlockMetadata(floor_double, floor_double2, floor_double3);
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
        for (int i = 0; i < this.cargoItems.length; ++i) {
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
        for (int i = 0; i < tagList.tagCount(); ++i) {
            final NBTTagCompound ae = (NBTTagCompound) tagList.tagAt(i);
            final int n = ae.getByte("Slot") & 0xFF;
            if (n >= 0 && n < this.cargoItems.length) {
                this.cargoItems[n] = new ItemStack(ae);
            }
        }
    }

    @Override
    public void applyEntityCollision(final Entity entity) {
        if (entity == this.riddenByEntity) {
            return;
        }
        double n = entity.posX - this.posX;
        double n2 = entity.posZ - this.posZ;
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
                final double n4 = (entity.motionX + this.motionX) / 2.0;
                final double n5 = (entity.motionZ + this.motionZ) / 2.0;
                final double n6 = 0.0;
                this.motionZ = n6;
                this.motionX = n6;
                this.addVelocity(n4 - n, 0.0, n5 - n2);
                final double n7 = 0.0;
                entity.motionZ = n7;
                entity.motionX = n7;
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
    public boolean interact(final EntityPlayer entityPlayer) {
        entityPlayer.mountEntity(this);
        return true;
    }

    static {
        MATRIX = new int[][][]{{{0, 0, -1}, {0, 0, 1}}, {{-1, 0, 0}, {1, 0, 0}}, {{-1, -1, 0}, {1, 0, 0}}, {{-1, 0, 0}, {1, -1, 0}}, {{0, 0, -1}, {0, -1, 1}}, {{0, -1, -1}, {0, 0, 1}}, {{0, 0, 1}, {1, 0, 0}}, {{0, 0, 1}, {-1, 0, 0}}, {{0, 0, -1}, {-1, 0, 0}}, {{0, 0, -1}, {1, 0, 0}}};
    }
}
