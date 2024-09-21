
package net.opencraft.entity;

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
        this.setPositionAndRotation(entityLiving.posX, entityLiving.posY, entityLiving.posZ, entityLiving.rotationYaw, entityLiving.rotationPitch);
        this.posX -= Mth.cos(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.posY -= 0.10000000149011612;
        this.posZ -= Mth.sin(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        this.motionX = -Mth.sin(this.rotationYaw / 180.0f * 3.1415927f) * Mth.cos(this.rotationPitch / 180.0f * 3.1415927f);
        this.motionZ = Mth.cos(this.rotationYaw / 180.0f * 3.1415927f) * Mth.cos(this.rotationPitch / 180.0f * 3.1415927f);
        this.motionY = -Mth.sin(this.rotationPitch / 180.0f * 3.1415927f);
        this.setArrowHeading(this.motionX, this.motionY, this.motionZ, 1.5f, 1.0f);
    }

    public void setArrowHeading(double xCoord, double yCoord, double zCoord, final float yaw, final float pitch) {
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
        this.motionX = xCoord;
        this.motionY = yCoord;
        this.motionZ = zCoord;
        final float sqrt_double2 = Mth.sqrt_double(xCoord * xCoord + zCoord * zCoord);
        final float n = (float) (Math.atan2(xCoord, zCoord) * 180.0 / 3.1415927410125732);
        this.rotationYaw = n;
        this.prevRotationYaw = n;
        final float n2 = (float) (Math.atan2(yCoord, (double) sqrt_double2) * 180.0 / 3.1415927410125732);
        this.rotationPitch = n2;
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
            if (this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile) == this.inTile) {
                ++this.ticksInGround;
                if (this.ticksInGround == 1200) {
                    this.setEntityDead();
                }
                return;
            }
            this.inGround = false;
            this.motionX *= this.rand.nextFloat() * 0.2f;
            this.motionY *= this.rand.nextFloat() * 0.2f;
            this.motionZ *= this.rand.nextFloat() * 0.2f;
            this.ticksInGround = 0;
            this.ticksInAir = 0;
        } else {
            ++this.ticksInAir;
        }
        MovingObjectPosition rayTraceBlocks = this.worldObj.rayTraceBlocks(Vec3.newTemp(this.posX, this.posY, this.posZ), Vec3.newTemp(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ));
        final Vec3 vector = Vec3.newTemp(this.posX, this.posY, this.posZ);
        Vec3 var2 = Vec3.newTemp(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        if (rayTraceBlocks != null) {
            var2 = Vec3.newTemp(rayTraceBlocks.hitVec.xCoord, rayTraceBlocks.hitVec.yCoord, rayTraceBlocks.hitVec.zCoord);
        }
        Entity eq = null;
        final List entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
        double n = 0.0;
        for (int i = 0; i < entitiesWithinAABBExcludingEntity.size(); ++i) {
            final Entity entity = (Entity) entitiesWithinAABBExcludingEntity.get(i);
            if (entity.canBeCollidedWith()) {
                if (entity != this.owner || this.ticksInAir >= 5) {
                    final float n2 = 0.3f;
                    final MovingObjectPosition calculateIntercept = entity.boundingBox.expand(n2, n2, n2).calculateIntercept(vector, var2);
                    if (calculateIntercept != null) {
                        final double distanceTo = vector.distanceTo(calculateIntercept.hitVec);
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
                    this.worldObj.playSoundAtEntity((Entity) this, "random.drr", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                    this.setEntityDead();
                } else {
                    this.motionX *= -0.10000000149011612;
                    this.motionY *= -0.10000000149011612;
                    this.motionZ *= -0.10000000149011612;
                    this.rotationYaw += 180.0f;
                    this.prevRotationYaw += 180.0f;
                    this.ticksInAir = 0;
                }
            } else {
                this.xTile = rayTraceBlocks.blockX;
                this.yTile = rayTraceBlocks.blockY;
                this.zTile = rayTraceBlocks.blockZ;
                this.inTile = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
                this.motionX = (float) (rayTraceBlocks.hitVec.xCoord - this.posX);
                this.motionY = (float) (rayTraceBlocks.hitVec.yCoord - this.posY);
                this.motionZ = (float) (rayTraceBlocks.hitVec.zCoord - this.posZ);
                final float n3 = Mth.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                this.posX -= this.motionX / n3 * 0.05000000074505806;
                this.posY -= this.motionY / n3 * 0.05000000074505806;
                this.posZ -= this.motionZ / n3 * 0.05000000074505806;
                this.worldObj.playSoundAtEntity((Entity) this, "random.drr", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                this.inGround = true;
                this.arrowShake = 7;
            }
        }
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        final float n3 = Mth.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.1415927410125732);
        this.rotationPitch = (float) (Math.atan2(this.motionY, (double) n3) * 180.0 / 3.1415927410125732);
        while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
            this.prevRotationPitch -= 360.0f;
        }
        while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
            this.prevRotationPitch += 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
            this.prevRotationYaw -= 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
            this.prevRotationYaw += 360.0f;
        }
        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
        float n4 = 0.99f;
        final float n2 = 0.03f;
        if (this.handleWaterMovement()) {
            for (int j = 0; j < 4; ++j) {
                final float n5 = 0.25f;
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * n5, this.posY - this.motionY * n5, this.posZ - this.motionZ * n5, this.motionX, this.motionY, this.motionZ);
            }
            n4 = 0.8f;
        }
        this.motionX *= n4;
        this.motionY *= n4;
        this.motionZ *= n4;
        this.motionY -= n2;
        this.setPosition(this.posX, this.posY, this.posZ);
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
    public void onCollideWithPlayer(final EntityPlayer entityPlayer) {
        if (this.inGround && this.owner == entityPlayer && this.arrowShake <= 0 && entityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.arrow.shiftedIndex, 1))) {
            this.worldObj.playSoundAtEntity((Entity) this, "random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            entityPlayer.onItemPickup(this);
            this.setEntityDead();
        }
    }
}
