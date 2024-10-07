
package net.opencraft.entity;

import static org.joml.Math.*;

import net.opencraft.blocks.Block;
import net.opencraft.blocks.material.Material;
import net.opencraft.item.ItemStack;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.util.Mth;
import net.opencraft.world.World;

public class EntityItem extends Entity {

    public ItemStack item;
    private int field_803_e;
    public int age;
    public int delayBeforeCanPickup;
    private int health;
    public float hoverStart;

    public EntityItem(final World world, final double xCoord, final double yCoord, final double zCoord, final ItemStack itemStack) {
        super(world);
        this.age = 0;
        this.health = 5;
        this.hoverStart = (float) (random() * PI_TIMES_2);
        this.setSize(0.25f, 0.25f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(xCoord, yCoord, zCoord);
        this.item = itemStack;
        this.rotationYaw = (float) (random() * 360.0);
        this.motionX = (float) (random() * 0.20000000298023224 - 0.10000000149011612);
        this.motionY = 0.20000000298023224;
        this.motionZ = (float) (random() * 0.20000000298023224 - 0.10000000149011612);
        this.canTriggerWalking = false;
    }

    public EntityItem(final World world) {
        super(world);
        this.age = 0;
        this.health = 5;
        this.hoverStart = (float) (random() * PI_TIMES_2);
        this.setSize(0.25f, 0.25f);
        this.yOffset = this.height / 2.0f;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.delayBeforeCanPickup > 0) {
            --this.delayBeforeCanPickup;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033;
        if (this.world.getBlockMaterial(Mth.floor_double(this.posX), Mth.floor_double(this.posY), Mth.floor_double(this.posZ)) == Material.LAVA) {
            this.motionY = 0.20000000298023224;
            this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
            this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
            this.world.playSound((Entity) this, "random.fizz", 0.4f, 2.0f + this.rand.nextFloat() * 0.4f);
        }
        this.pushOutOfBlocks(this.posX, this.posY, this.posZ);
        this.handleWaterMovement();
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
            this.motionY *= -0.5;
        }
        ++this.field_803_e;
        ++this.age;
        if (this.age >= 6000) {
            this.setEntityDead();
        }
    }

    @Override
    public boolean handleWaterMovement() {
        return this.world.handleMaterialAcceleration(this.boundingBox, Material.WATER, this);
    }

    private boolean pushOutOfBlocks(final double double1, final double double2, final double double3) {
        final int floor_double = Mth.floor_double(double1);
        final int floor_double2 = Mth.floor_double(double2);
        final int floor_double3 = Mth.floor_double(double3);
        final double n = double1 - floor_double;
        final double n2 = double2 - floor_double2;
        final double n3 = double3 - floor_double3;
        if (Block.opaqueCubeLookup[this.world.getBlockId(floor_double, floor_double2, floor_double3)]) {
            final boolean b = !Block.opaqueCubeLookup[this.world.getBlockId(floor_double - 1, floor_double2, floor_double3)];
            final boolean b2 = !Block.opaqueCubeLookup[this.world.getBlockId(floor_double + 1, floor_double2, floor_double3)];
            final boolean b3 = !Block.opaqueCubeLookup[this.world.getBlockId(floor_double, floor_double2 - 1, floor_double3)];
            final boolean b4 = !Block.opaqueCubeLookup[this.world.getBlockId(floor_double, floor_double2 + 1, floor_double3)];
            final boolean b5 = !Block.opaqueCubeLookup[this.world.getBlockId(floor_double, floor_double2, floor_double3 - 1)];
            final boolean b6 = !Block.opaqueCubeLookup[this.world.getBlockId(floor_double, floor_double2, floor_double3 + 1)];
            int n4 = -1;
            double n5 = 9999.0;
            if (b && n < n5) {
                n5 = n;
                n4 = 0;
            }
            if (b2 && 1.0 - n < n5) {
                n5 = 1.0 - n;
                n4 = 1;
            }
            if (b3 && n2 < n5) {
                n5 = n2;
                n4 = 2;
            }
            if (b4 && 1.0 - n2 < n5) {
                n5 = 1.0 - n2;
                n4 = 3;
            }
            if (b5 && n3 < n5) {
                n5 = n3;
                n4 = 4;
            }
            if (b6 && 1.0 - n3 < n5) {
                n5 = 1.0 - n3;
                n4 = 5;
            }
            final float n6 = this.rand.nextFloat() * 0.2f + 0.1f;
            if (n4 == 0) {
                this.motionX = -n6;
            }
            if (n4 == 1) {
                this.motionX = n6;
            }
            if (n4 == 2) {
                this.motionY = -n6;
            }
            if (n4 == 3) {
                this.motionY = n6;
            }
            if (n4 == 4) {
                this.motionZ = -n6;
            }
            if (n4 == 5) {
                this.motionZ = n6;
            }
        }
        return false;
    }

    @Override
    protected void dealFireDamage(final int damage) {
        this.attackEntityFrom(null, damage);
    }

    @Override
    public boolean attackEntityFrom(final Entity entity, final int nya1) {
        this.health -= nya1;
        if (this.health <= 0) {
            this.setEntityDead();
        }
        return false;
    }

    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setShort("Health", (short) (byte) this.health);
        nbtTagCompound.setShort("Age", (short) this.age);
        nbtTagCompound.setCompoundTag("Item", this.item.writeToNBT(new NBTTagCompound()));
    }

    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.health = (nbtTagCompound.getShort("Health") & 0xFF);
        this.age = nbtTagCompound.getShort("Age");
        this.item = new ItemStack(nbtTagCompound.getCompoundTag("Item"));
    }

    @Override
    public void onCollideWithPlayer(final EntityPlayer entityPlayer) {
        if (this.delayBeforeCanPickup == 0 && entityPlayer.inventory.addItemStackToInventory(this.item)) {
            this.world.playSound((Entity) this, "random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            entityPlayer.onItemPickup(this);
            this.setEntityDead();
        }
    }
}
