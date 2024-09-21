
package net.opencraft.entity;

import java.util.List;
import java.util.Random;

import net.opencraft.blocks.Block;
import net.opencraft.blocks.LiquidBlock;
import net.opencraft.blocks.material.Material;
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
    public Entity riddenByEntity;
    public Entity ridingEntity;
    protected World worldObj;
    public double prevPosX;
    public double prevPosY;
    public double prevPosZ;
    public double posX;
    public double posY;
    public double posZ;
    public double motionX;
    public double motionY;
    public double motionZ;
    public float rotationYaw;
    public float rotationPitch;
    public float prevRotationYaw;
    public float prevRotationPitch;
    public final AABB boundingBox;
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
        this.boundingBox = AABB.getBoundingBox(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
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
        this.worldObj = world;
        this.setPosition(0.0, 0.0, 0.0);
    }

    protected void preparePlayerToSpawn() {
        if (this.worldObj == null) {
            return;
        }
        while (this.posY > 0.0) {
            this.setPosition(this.posX, this.posY, this.posZ);
            if (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0) {
                break;
            }
            ++this.posY;
        }
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
        this.rotationPitch = 0.0f;
    }

    public void setEntityDead() {
        this.isDead = true;
    }

    protected void setSize(final float nya1, final float nya2) {
        this.width = nya1;
        this.height = nya2;
    }

    protected void setRotation(final float nya1, final float nya2) {
        this.rotationYaw = nya1;
        this.rotationPitch = nya2;
    }

    public void setPosition(final double xCoord, final double yCoord, final double zCoord) {
        this.posX = xCoord;
        this.posY = yCoord;
        this.posZ = zCoord;
        final float n = this.width / 2.0f;
        final float n2 = this.height / 2.0f;
        this.boundingBox.setBounds(xCoord - n, yCoord - n2, zCoord - n, xCoord + n, yCoord + n2, zCoord + n);
    }

    public void setAngles(final float nya1, final float nya2) {
        final float rotationPitch = this.rotationPitch;
        final float rotationYaw = this.rotationYaw;
        this.rotationYaw += (float) (nya1 * 0.15);
        this.rotationPitch -= (float) (nya2 * 0.15);
        if (this.rotationPitch < -90.0f) {
            this.rotationPitch = -90.0f;
        }
        if (this.rotationPitch > 90.0f) {
            this.rotationPitch = 90.0f;
        }
        this.prevRotationPitch += this.rotationPitch - rotationPitch;
        this.prevRotationYaw += this.rotationYaw - rotationYaw;
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
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;
        if (this.handleWaterMovement()) {
            if (!this.inWater && !this.isFirstUpdate) {
                float volume = Mth.sqrt_double(this.motionX * this.motionX * 0.20000000298023224 + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224) * 0.2f;
                if (volume > 1.0f) {
                    volume = 1.0f;
                }
                this.worldObj.playSoundAtEntity(this, "random.splash", volume, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                final float n = (float) Mth.floor_double(this.boundingBox.minY);
                for (int n2 = 0; n2 < 1.0f + this.width * 20.0f; ++n2) {
                    final float n3 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    final float n4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    this.worldObj.spawnParticle("bubble", this.posX + n3, (double) (n + 1.0f), this.posZ + n4, this.motionX, this.motionY - this.rand.nextFloat() * 0.2f, this.motionZ);
                }
                for (int n2 = 0; n2 < 1.0f + this.width * 20.0f; ++n2) {
                    final float n3 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    final float n4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    this.worldObj.spawnParticle("splash", this.posX + n3, (double) (n + 1.0f), this.posZ + n4, this.motionX, this.motionY, this.motionZ);
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
        if (this.posY < -64.0) {
            this.kill();
        }
        this.isFirstUpdate = false;
    }

    protected void kill() {
        this.setEntityDead();
    }

    public boolean isOffsetPositionInLiquid(final double xCoord, final double yCoord, final double zCoord) {
        final AABB offsetBoundingBox = this.boundingBox.getOffsetBoundingBox(xCoord, yCoord, zCoord);
        return this.worldObj.getCollidingBoundingBoxes(this, offsetBoundingBox).size() <= 0 && !this.worldObj.getIsAnyLiquid(offsetBoundingBox);
    }

    public void moveEntity(double xCoord, double yCoord, double zCoord) {
        if (this.noClip) {
            this.boundingBox.offset(xCoord, yCoord, zCoord);
            this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0;
            this.posY = this.boundingBox.minY + this.yOffset - this.ySize;
            this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0;
            return;
        }
        final double posX = this.posX;
        final double posZ = this.posZ;
        final double n = xCoord;
        final double n2 = yCoord;
        final double n3 = zCoord;
        final AABB copy = this.boundingBox.copy();
        final List collidingBoundingBoxes = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(xCoord, yCoord, zCoord));
        for (int i = 0; i < collidingBoundingBoxes.size(); ++i) {
            yCoord = ((AABB) collidingBoundingBoxes.get(i)).calculateYOffset(this.boundingBox, yCoord);
        }
        this.boundingBox.offset(0.0, yCoord, 0.0);
        if (!this.field_9293_aM && n2 != yCoord) {
            yCoord = (xCoord = (zCoord = 0.0));
        }
        final boolean b = this.onGround || (n2 != yCoord && n2 < 0.0);
        for (int j = 0; j < collidingBoundingBoxes.size(); ++j) {
            xCoord = ((AABB) collidingBoundingBoxes.get(j)).calculateXOffset(this.boundingBox, xCoord);
        }
        this.boundingBox.offset(xCoord, 0.0, 0.0);
        if (!this.field_9293_aM && n != xCoord) {
            yCoord = (xCoord = (zCoord = 0.0));
        }
        for (int j = 0; j < collidingBoundingBoxes.size(); ++j) {
            zCoord = ((AABB) collidingBoundingBoxes.get(j)).calculateZOffset(this.boundingBox, zCoord);
        }
        this.boundingBox.offset(0.0, 0.0, zCoord);
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
            final AABB copy2 = this.boundingBox.copy();
            this.boundingBox.setBB(copy);
            final List collidingBoundingBoxes2 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(xCoord, yCoord, zCoord));
            for (int k = 0; k < collidingBoundingBoxes2.size(); ++k) {
                yCoord = ((AABB) collidingBoundingBoxes2.get(k)).calculateYOffset(this.boundingBox, yCoord);
            }
            this.boundingBox.offset(0.0, yCoord, 0.0);
            if (!this.field_9293_aM && n2 != yCoord) {
                yCoord = (xCoord = (zCoord = 0.0));
            }
            for (int k = 0; k < collidingBoundingBoxes2.size(); ++k) {
                xCoord = ((AABB) collidingBoundingBoxes2.get(k)).calculateXOffset(this.boundingBox, xCoord);
            }
            this.boundingBox.offset(xCoord, 0.0, 0.0);
            if (!this.field_9293_aM && n != xCoord) {
                yCoord = (xCoord = (zCoord = 0.0));
            }
            for (int k = 0; k < collidingBoundingBoxes2.size(); ++k) {
                zCoord = ((AABB) collidingBoundingBoxes2.get(k)).calculateZOffset(this.boundingBox, zCoord);
            }
            this.boundingBox.offset(0.0, 0.0, zCoord);
            if (!this.field_9293_aM && n3 != zCoord) {
                yCoord = (xCoord = (zCoord = 0.0));
            }
            if (n4 * n4 + n6 * n6 >= xCoord * xCoord + zCoord * zCoord) {
                xCoord = n4;
                yCoord = n5;
                zCoord = n6;
                this.boundingBox.setBB(copy2);
            } else {
                this.ySize += 0.5;
            }
        }
        this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0;
        this.posY = this.boundingBox.minY + this.yOffset - this.ySize;
        this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0;
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
            this.motionX = 0.0;
        }
        if (n2 != yCoord) {
            this.motionY = 0.0;
        }
        if (n3 != zCoord) {
            this.motionZ = 0.0;
        }
        final double n4 = this.posX - posX;
        final double n5 = this.posZ - posZ;
        this.distanceWalkedModified += (float) (Mth.sqrt_double(n4 * n4 + n5 * n5) * 0.6);
        if (this.canTriggerWalking) {
            final int floor_double = Mth.floor_double(this.posX);
            final int floor_double2 = Mth.floor_double(this.posY - 0.20000000298023224 - this.yOffset);
            final int floor_double3 = Mth.floor_double(this.posZ);
            final int k = this.worldObj.getBlockId(floor_double, floor_double2, floor_double3);
            if (this.distanceWalkedModified > this.nextStepDistance && k > 0) {
                ++this.nextStepDistance;
                final StepSound stepSound = Block.blocksList[k].stepSound;
                if (!Block.blocksList[k].blockMaterial.isLiquid()) {
                    this.worldObj.playSoundAtEntity(this, stepSound.stepSoundDir2(), stepSound.soundVolume() * 0.15f, stepSound.soundPitch());
                }
                Block.blocksList[k].onEntityWalking(this.worldObj, floor_double, floor_double2, floor_double3, this);
            }
        }
        this.ySize *= 0.4f;
        final boolean handleWaterMovement = this.handleWaterMovement();
        if (this.worldObj.isBoundingBoxBurning(this.boundingBox)) {
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
            this.worldObj.playSoundAtEntity(this, "random.fizz", 0.7f, 1.6f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
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
        return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0, -0.4000000059604645, 0.0), Material.WATER, this);
    }

    public boolean isInsideOfMaterial(final Material material) {
        final double double1 = this.posY + this.getEyeHeight();
        final int floor_double = Mth.floor_double(this.posX);
        final int floor_float = Mth.floor_float((float) Mth.floor_double(double1));
        final int floor_double2 = Mth.floor_double(this.posZ);
        final int blockId = this.worldObj.getBlockId(floor_double, floor_float, floor_double2);
        return blockId != 0 && Block.blocksList[blockId].blockMaterial == material && double1 < floor_float + 1 - (LiquidBlock.getPercentAir(this.worldObj.getBlockMetadata(floor_double, floor_float, floor_double2)) - 0.11111111f);
    }

    protected float getEyeHeight() {
        return 0.0f;
    }

    public boolean handleLavaMovement() {
        return this.worldObj.isMaterialInBB(this.boundingBox.expand(0.0, -0.4000000059604645, 0.0), Material.LAVA);
    }

    public void moveFlying(float xCoord, float yCoord, final float zCoord) {
        float sqrt_float = Mth.sqrt_float(xCoord * xCoord + yCoord * yCoord);
        if (sqrt_float < 0.01f) {
            return;
        }
        if (sqrt_float < 1.0f) {
            sqrt_float = 1.0f;
        }
        sqrt_float = zCoord / sqrt_float;
        xCoord *= sqrt_float;
        yCoord *= sqrt_float;
        final float sin = Mth.sin(this.rotationYaw * 3.1415927f / 180.0f);
        final float cos = Mth.cos(this.rotationYaw * 3.1415927f / 180.0f);
        this.motionX += xCoord * cos - yCoord * sin;
        this.motionZ += yCoord * cos + xCoord * sin;
    }

    public float getEntityBrightness(final float float1) {
        return this.worldObj.getLightBrightness(Mth.floor_double(this.posX), Mth.floor_double(this.posY - this.yOffset + (this.boundingBox.maxY - this.boundingBox.minY) * 0.66), Mth.floor_double(this.posZ));
    }

    public void setWorld(final World world) {
        this.worldObj = world;
    }

    public void setPositionAndRotation(final double xCoord, final double yCoord, final double zCoord, final float yaw, final float pitch) {
        this.posX = xCoord;
        this.prevPosX = xCoord;
        final double n = yCoord + this.yOffset;
        this.posY = n;
        this.prevPosY = n;
        this.posZ = zCoord;
        this.prevPosZ = zCoord;
        this.rotationYaw = yaw;
        this.rotationPitch = pitch;
        this.setPosition(this.posX, this.posY, this.posZ);
    }

    public float getDistanceToEntity(final Entity entity) {
        final float n = (float) (this.posX - entity.posX);
        final float n2 = (float) (this.posY - entity.posY);
        final float n3 = (float) (this.posZ - entity.posZ);
        return Mth.sqrt_float(n * n + n2 * n2 + n3 * n3);
    }

    public double getDistanceSq(final double xCoord, final double yCoord, final double zCoord) {
        final double n = this.posX - xCoord;
        final double n2 = this.posY - yCoord;
        final double n3 = this.posZ - zCoord;
        return n * n + n2 * n2 + n3 * n3;
    }

    public double getDistance(final double xCoord, final double yCoord, final double zCoord) {
        final double n = this.posX - xCoord;
        final double n2 = this.posY - yCoord;
        final double n3 = this.posZ - zCoord;
        return Mth.sqrt_double(n * n + n2 * n2 + n3 * n3);
    }

    public double getDistanceSqToEntity(final Entity entity) {
        final double n = this.posX - entity.posX;
        final double n2 = this.posY - entity.posY;
        final double n3 = this.posZ - entity.posZ;
        return n * n + n2 * n2 + n3 * n3;
    }

    public void onCollideWithPlayer(final EntityPlayer entityPlayer) {
    }

    public void applyEntityCollision(final Entity entity) {
        double n = entity.posX - this.posX;
        double n2 = entity.posZ - this.posZ;
        double abs_max = Mth.abs_max(n, n2);
        if (abs_max >= 0.009999999776482582) {
            abs_max = Mth.sqrt_double(abs_max);
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
        this.motionX += xCoord;
        this.motionY += yCoord;
        this.motionZ += zCoord;
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
        final double n = this.posX - var1.xCoord;
        final double n2 = this.posY - var1.yCoord;
        final double n3 = this.posZ - var1.zCoord;
        return this.isInRangeToRenderDist(n * n + n2 * n2 + n3 * n3);
    }

    public boolean isInRangeToRenderDist(final double distance) {
        double averageEdgeLength = this.boundingBox.getAverageEdgeLength();
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
        nbt.setTag("Pos", (NBTBase) this.newDoubleNBTList(this.posX, this.posY, this.posZ));
        nbt.setTag("Motion", (NBTBase) this.newDoubleNBTList(this.motionX, this.motionY, this.motionZ));
        nbt.setTag("Rotation", (NBTBase) this.newFloatNBTList(this.rotationYaw, this.rotationPitch));
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
        this.motionX = ((NBTTagDouble) tagList2.tagAt(0)).doubleValue;
        this.motionY = ((NBTTagDouble) tagList2.tagAt(1)).doubleValue;
        this.motionZ = ((NBTTagDouble) tagList2.tagAt(2)).doubleValue;
        final double doubleValue = ((NBTTagDouble) tagList.tagAt(0)).doubleValue;
        this.posX = doubleValue;
        this.lastTickPosX = doubleValue;
        this.prevPosX = doubleValue;
        final double doubleValue2 = ((NBTTagDouble) tagList.tagAt(1)).doubleValue;
        this.posY = doubleValue2;
        this.lastTickPosY = doubleValue2;
        this.prevPosY = doubleValue2;
        final double doubleValue3 = ((NBTTagDouble) tagList.tagAt(2)).doubleValue;
        this.posZ = doubleValue3;
        this.lastTickPosZ = doubleValue3;
        this.prevPosZ = doubleValue3;
        final float floatValue = ((NBTTagFloat) tagList3.tagAt(0)).floatValue;
        this.rotationYaw = floatValue;
        this.prevRotationYaw = floatValue;
        final float floatValue2 = ((NBTTagFloat) tagList3.tagAt(1)).floatValue;
        this.rotationPitch = floatValue2;
        this.prevRotationPitch = floatValue2;
        this.fallDistance = nbt.getFloat("FallDistance");
        this.fire = nbt.getShort("Fire");
        this.air = nbt.getShort("Air");
        this.onGround = nbt.getBoolean("OnGround");
        this.setPosition(this.posX, this.posY, this.posZ);
        this.readEntityFromNBT(nbt);
    }

    protected final String getEntityString() {
        return EntityList.getEntityString(this);
    }

    protected abstract void readEntityFromNBT(final NBTTagCompound nbtTagCompound);

    protected abstract void writeEntityToNBT(final NBTTagCompound nbtTagCompound);

    protected NBTTagList newDoubleNBTList(final double... arr) {
        final NBTTagList list = new NBTTagList();
        for (int length = arr.length, i = 0; i < length; ++i) {
            list.setTag(new NBTTagDouble(arr[i]));
        }
        return list;
    }

    protected NBTTagList newFloatNBTList(final float... arr) {
        final NBTTagList list = new NBTTagList();
        for (int length = arr.length, i = 0; i < length; ++i) {
            list.setTag(new NBTTagFloat(arr[i]));
        }
        return list;
    }

    public EntityItem dropItemWithOffset(final int integer1, final int integer2) {
        return this.entityDropItem(integer1, integer2, 0.0f);
    }

    public EntityItem entityDropItem(final int integer1, final int integer2, final float float3) {
        final EntityItem entity = new EntityItem(this.worldObj, this.posX, this.posY + float3, this.posZ, new ItemStack(integer1, integer2));
        entity.delayBeforeCanPickup = 10;
        this.worldObj.entityJoinedWorld(entity);
        return entity;
    }

    public boolean isEntityAlive() {
        return !this.isDead;
    }

    public boolean isEntityInsideOpaqueBlock() {
        return this.worldObj.isBlockNormalCube(Mth.floor_double(this.posX), Mth.floor_double(this.posY + this.getEyeHeight()), Mth.floor_double(this.posZ));
    }

    public boolean interact(final EntityPlayer entityPlayer) {
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
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.onUpdate();
        this.setPosition(this.ridingEntity.posX, this.ridingEntity.posY + this.yOffset + this.ridingEntity.getYOffset(), this.ridingEntity.posZ);
        this.entityRiderYawDelta += this.ridingEntity.rotationYaw - this.ridingEntity.prevRotationYaw;
        this.entityRiderPitchDelta += this.ridingEntity.rotationPitch - this.ridingEntity.prevRotationPitch;
        while (this.entityRiderYawDelta >= 180.0) {
            this.entityRiderYawDelta -= 360.0;
        }
        while (this.entityRiderYawDelta < -180.0) {
            this.entityRiderYawDelta += 360.0;
        }
        while (this.entityRiderPitchDelta >= 180.0) {
            this.entityRiderPitchDelta -= 360.0;
        }
        while (this.entityRiderPitchDelta < -180.0) {
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
        this.rotationYaw += (float) n;
        this.rotationPitch += (float) n2;
    }

    public double getYOffset() {
        return this.height * 0.75;
    }

    public void mountEntity(final Entity entity) {
        this.entityRiderPitchDelta = 0.0;
        this.entityRiderYawDelta = 0.0;
        if (this.ridingEntity == entity) {
            this.ridingEntity.riddenByEntity = null;
            this.ridingEntity = null;
            return;
        }
        if (this.ridingEntity != null) {
            this.ridingEntity.riddenByEntity = null;
        }
        if (entity.riddenByEntity != null) {
            entity.riddenByEntity.ridingEntity = null;
        }
        this.ridingEntity = entity;
        entity.riddenByEntity = this;
    }
}
