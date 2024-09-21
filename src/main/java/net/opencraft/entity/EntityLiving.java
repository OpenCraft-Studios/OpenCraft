
package net.opencraft.entity;

import java.util.List;
import net.opencraft.block.Block;
import net.opencraft.block.material.Material;
import net.opencraft.client.sound.StepSound;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.util.MathHelper;
import net.opencraft.world.World;

public class EntityLiving extends Entity {

    public int heartsHalvesLife;
    public float field_9365_p;
    public float ae;
    public float field_9363_r;
    public float renderYawOffset;
    public float prevRenderYawOffset;
    protected float field_9362_u;
    protected float rotationYawHead;
    protected float prevRotationYawHead;
    protected float field_9359_x;
    protected boolean field_9358_y;
    protected String texture;
    protected boolean field_9355_A;
    protected float unused180;
    protected String entityType;
    protected float field_9349_D;
    protected int scoreValue;
    protected float field_9345_F;
    public int health;
    public int prevHealth;
    private int livingSoundTime;
    public int hurtTime;
    public int maxHurtTime;
    public float attackedAtYaw;
    public int deathTime;
    public int attackTime;
    public float prevCameraPitch;
    public float cameraPitch;
    protected boolean unused_flag;
    public int field_9326_T;
    public float field_9325_U;
    public float newPosZ;
    public float newRotationYaw;
    public float newRotationPitch;
    protected int entityAge;
    protected float moveStrafing;
    protected float moveForward;
    protected float randomYawVelocity;
    protected boolean isJumping;
    protected float defaultPitch;
    protected float moveSpeed;

    public EntityLiving(final World world) {
        super(world);
        this.heartsHalvesLife = 20;
        this.renderYawOffset = 0.0f;
        this.prevRenderYawOffset = 0.0f;
        this.field_9358_y = true;
        this.texture = "/assets/char.png";
        this.field_9355_A = true;
        this.unused180 = 0.0f;
        this.entityType = null;
        this.field_9349_D = 1.0f;
        this.scoreValue = 0;
        this.field_9345_F = 0.0f;
        this.attackedAtYaw = 0.0f;
        this.deathTime = 0;
        this.attackTime = 0;
        this.unused_flag = false;
        this.field_9326_T = -1;
        this.field_9325_U = (float) (Math.random() * 0.8999999761581421 + 0.10000000149011612);
        this.entityAge = 0;
        this.isJumping = false;
        this.defaultPitch = 0.0f;
        this.moveSpeed = 0.7f;
        this.health = 10;
        this.preventEntitySpawning = true;
        this.field_9363_r = (float) (Math.random() + 1.0) * 0.01f;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.field_9365_p = (float) Math.random() * 12398.0f;
        this.rotationYaw = (float) (Math.random() * 3.1415927410125732 * 2.0);
        this.ae = 1.0f;
        this.stepHeight = 0.5f;
    }

    @Override
    public String addToPlayerScore() {
        return this.texture;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public boolean canBePushed() {
        return !this.isDead;
    }

    @Override
    protected float getEyeHeight() {
        return this.height * 0.85f;
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (this.rand.nextInt(1000) < this.livingSoundTime++) {
            this.livingSoundTime = -80;
            final String livingSound = this.livingSound();
            if (livingSound != null) {
                this.worldObj.playSoundAtEntity(this, livingSound, 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
        }
        if (this.isEntityAlive() && this.isEntityInsideOpaqueBlock()) {
            this.attackEntityFrom(null, 1);
        }
        if (this.isEntityAlive() && this.isInsideOfMaterial(Material.WATER)) {
            --this.air;
            if (this.air == -20) {
                this.air = 0;
                for (int i = 0; i < 8; ++i) {
                    this.worldObj.spawnParticle("bubble", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), this.motionX, this.motionY, this.motionZ);
                }
                this.attackEntityFrom(null, 2);
            }
            this.fire = 0;
        } else {
            this.air = this.maxAir;
        }
        this.prevCameraPitch = this.cameraPitch;
        if (this.attackTime > 0) {
            --this.attackTime;
        }
        if (this.hurtTime > 0) {
            --this.hurtTime;
        }
        if (this.heartsLife > 0) {
            --this.heartsLife;
        }
        if (this.health <= 0) {
            ++this.deathTime;
            if (this.deathTime > 20) {
                this.onEntityDeath();
                this.setEntityDead();
                for (int i = 0; i < 20; ++i) {
                    this.worldObj.spawnParticle("explode", this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02);
                }
            }
        }
        this.field_9359_x = this.prevRotationYawHead;
        this.prevRenderYawOffset = this.renderYawOffset;
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }

    public void spawnExplosionParticle() {
        for (int i = 0; i < 20; ++i) {
            final double xPosition = this.rand.nextGaussian() * 0.02;
            final double yPosition = this.rand.nextGaussian() * 0.02;
            final double zPosition = this.rand.nextGaussian() * 0.02;
            final double n = 10.0;
            this.worldObj.spawnParticle("explode", this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width - xPosition * n, this.posY + this.rand.nextFloat() * this.height - yPosition * n, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width - zPosition * n, xPosition, yPosition, zPosition);
        }
    }

    @Override
    public void updateRidden() {
        super.updateRidden();
        this.field_9362_u = this.rotationYawHead;
        this.rotationYawHead = 0.0f;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.onLivingUpdate();
        final double n = this.posX - this.prevPosX;
        final double n2 = this.posZ - this.prevPosZ;
        final float sqrt_double = MathHelper.sqrt_double(n * n + n2 * n2);
        float renderYawOffset = this.renderYawOffset;
        float n3 = 0.0f;
        this.field_9362_u = this.rotationYawHead;
        float n4 = 0.0f;
        if (sqrt_double > 0.05f) {
            n4 = 1.0f;
            n3 = sqrt_double * 3.0f;
            renderYawOffset = (float) Math.atan2(n2, n) * 180.0f / 3.1415927f - 90.0f;
        }
        if (!this.onGround) {
            n4 = 0.0f;
        }
        this.rotationYawHead += (n4 - this.rotationYawHead) * 0.3f;
        float n5;
        for (n5 = renderYawOffset - this.renderYawOffset; n5 < -180.0f; n5 += 360.0f) {
        }
        while (n5 >= 180.0f) {
            n5 -= 360.0f;
        }
        this.renderYawOffset += n5 * 0.1f;
        float n6;
        for (n6 = this.rotationYaw - this.renderYawOffset; n6 < -180.0f; n6 += 360.0f) {
        }
        while (n6 >= 180.0f) {
            n6 -= 360.0f;
        }
        final boolean b = n6 < -90.0f || n6 >= 90.0f;
        if (n6 < -75.0f) {
            n6 = -75.0f;
        }
        if (n6 >= 75.0f) {
            n6 = 75.0f;
        }
        this.renderYawOffset = this.rotationYaw - n6;
        this.renderYawOffset += n6 * 0.1f;
        if (b) {
            n3 *= -1.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
            this.prevRotationYaw -= 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
            this.prevRotationYaw += 360.0f;
        }
        while (this.renderYawOffset - this.prevRenderYawOffset < -180.0f) {
            this.prevRenderYawOffset -= 360.0f;
        }
        while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0f) {
            this.prevRenderYawOffset += 360.0f;
        }
        while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
            this.prevRotationPitch -= 360.0f;
        }
        while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
            this.prevRotationPitch += 360.0f;
        }
        this.prevRotationYawHead += n3;
    }

    @Override
    protected void setSize(final float nya1, final float nya2) {
        super.setSize(nya1, nya2);
    }

    public void heal(final int nya1) {
        if (this.health <= 0) {
            return;
        }
        this.health += nya1;
        if (this.health > 20) {
            this.health = 20;
        }
        this.heartsLife = this.heartsHalvesLife / 2;
    }

    @Override
    public boolean attackEntityFrom(final Entity entity, final int nya1) {
        this.entityAge = 0;
        if (this.health <= 0) {
            return false;
        }
        this.newRotationYaw = 1.5f;
        if (this.heartsLife > this.heartsHalvesLife / 2.0f) {
            if (this.prevHealth - nya1 >= this.health) {
                return false;
            }
            this.health = this.prevHealth - nya1;
        } else {
            this.prevHealth = this.health;
            this.heartsLife = this.heartsHalvesLife;
            this.health -= nya1;
            final int n = 10;
            this.maxHurtTime = n;
            this.hurtTime = n;
        }
        this.attackedAtYaw = 0.0f;
        if (entity != null) {
            double nya2;
            double nya3;
            for (nya2 = entity.posX - this.posX, nya3 = entity.posZ - this.posZ; nya2 * nya2 + nya3 * nya3 < 1.0E-4; nya2 = (Math.random() - Math.random()) * 0.01, nya3 = (Math.random() - Math.random()) * 0.01) {
            }
            this.attackedAtYaw = (float) (Math.atan2(nya3, nya2) * 180.0 / 3.1415927410125732) - this.rotationYaw;
            this.knockBack(entity, nya1, nya2, nya3);
        } else {
            this.attackedAtYaw = (float) ((int) (Math.random() * 2.0) * 180);
        }
        if (this.health <= 0) {
            this.worldObj.playSoundAtEntity(this, this.getDeathSound(), 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.onDeath(entity);
        } else {
            this.worldObj.playSoundAtEntity(this, this.getHurtSound(), 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
        return true;
    }

    protected String livingSound() {
        return null;
    }

    protected String getHurtSound() {
        return "random.hurt";
    }

    protected String getDeathSound() {
        return "random.hurt";
    }

    public void knockBack(final Entity entity, final int nya1, final double nya2, final double nya3) {
        final float sqrt_double = MathHelper.sqrt_double(nya2 * nya2 + nya3 * nya3);
        final float n = 0.4f;
        this.motionX /= 2.0;
        this.motionY /= 2.0;
        this.motionZ /= 2.0;
        this.motionX -= nya2 / sqrt_double * n;
        this.motionY += 0.4000000059604645;
        this.motionZ -= nya3 / sqrt_double * n;
        if (this.motionY > 0.4000000059604645) {
            this.motionY = 0.4000000059604645;
        }
    }

    public void onDeath(final Entity entity) {
        if (this.scoreValue > 0 && entity != null) {
            entity.addToPlayerScore(this, this.scoreValue);
        }
        this.unused_flag = true;
        final int scoreValue = this.scoreValue();
        if (scoreValue > 0) {
            for (int nextInt = this.rand.nextInt(3), i = 0; i < nextInt; ++i) {
                this.dropItemWithOffset(scoreValue, 1);
            }
        }
    }

    protected int scoreValue() {
        return 0;
    }

    @Override
    protected void fall(final float nya1) {
        final int nya2 = (int) Math.ceil((double) (nya1 - 3.0f));
        if (nya2 > 0) {
            this.attackEntityFrom(null, nya2);
            final int blockId = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.20000000298023224 - this.yOffset), MathHelper.floor_double(this.posZ));
            if (blockId > 0) {
                final StepSound stepSound = Block.blocksList[blockId].stepSound;
                this.worldObj.playSoundAtEntity(this, stepSound.stepSoundDir2(), stepSound.soundVolume() * 0.5f, stepSound.soundPitch() * 0.75f);
            }
        }
    }

    public void moveEntityWithHeading(final float nya1, final float moveForward) {
        if (this.handleWaterMovement()) {
            final double n = this.posY;
            this.moveFlying(nya1, moveForward, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.800000011920929;
            this.motionY *= 0.800000011920929;
            this.motionZ *= 0.800000011920929;
            this.motionY -= 0.02;
            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579 - this.posY + n, this.motionZ)) {
                this.motionY = 0.30000001192092896;
            }
        } else if (this.handleLavaMovement()) {
            final double n = this.posY;
            this.moveFlying(nya1, moveForward, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
            this.motionY -= 0.02;
            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579 - this.posY + n, this.motionZ)) {
                this.motionY = 0.30000001192092896;
            }
        } else {
            this.moveFlying(nya1, moveForward, this.onGround ? 0.1f : 0.02f);
            if (this.isOnLadder()) {
                this.fallDistance = 0.0f;
                if (this.motionY < -0.15) {
                    this.motionY = -0.15;
                }
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            if (this.isCollidedHorizontally && this.isOnLadder()) {
                this.motionY = 0.2;
            }
            this.motionX *= 0.9100000262260437;
            this.motionY *= 0.9800000190734863;
            this.motionZ *= 0.9100000262260437;
            this.motionY -= 0.08;
            if (this.onGround) {
                final float n2 = 0.6f;
                this.motionX *= n2;
                this.motionZ *= n2;
            }
        }
        this.newPosZ = this.newRotationYaw;
        final double n = this.posX - this.prevPosX;
        final double n3 = this.posZ - this.prevPosZ;
        float n4 = MathHelper.sqrt_double(n * n + n3 * n3) * 4.0f;
        if (n4 > 1.0f) {
            n4 = 1.0f;
        }
        this.newRotationYaw += (n4 - this.newRotationYaw) * 0.4f;
        this.newRotationPitch += this.newRotationYaw;
    }

    public boolean isOnLadder() {
        final int floor_double = MathHelper.floor_double(this.posX);
        final int floor_double2 = MathHelper.floor_double(this.boundingBox.minY);
        final int floor_double3 = MathHelper.floor_double(this.posZ);
        return this.worldObj.getBlockId(floor_double, floor_double2, floor_double3) == Block.ladder.blockID || this.worldObj.getBlockId(floor_double, floor_double2 + 1, floor_double3) == Block.ladder.blockID;
    }

    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setShort("Health", (short) this.health);
        nbtTagCompound.setShort("HurtTime", (short) this.hurtTime);
        nbtTagCompound.setShort("DeathTime", (short) this.deathTime);
        nbtTagCompound.setShort("AttackTime", (short) this.attackTime);
    }

    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.health = nbtTagCompound.getShort("Health");
        if (!nbtTagCompound.hasKey("Health")) {
            this.health = 10;
        }
        this.hurtTime = nbtTagCompound.getShort("HurtTime");
        this.deathTime = nbtTagCompound.getShort("DeathTime");
        this.attackTime = nbtTagCompound.getShort("AttackTime");
    }

    @Override
    public boolean isEntityAlive() {
        return !this.isDead && this.health > 0;
    }

    public void onLivingUpdate() {
        ++this.entityAge;
        final Entity player = this.worldObj.getPlayer();
        if (player != null) {
            final double n = player.posX - this.posX;
            final double n2 = player.posY - this.posY;
            final double n3 = player.posZ - this.posZ;
            final double n4 = n * n + n2 * n2 + n3 * n3;
            if (n4 > 16384.0) {
                this.setEntityDead();
            }
            if (this.entityAge > 600 && this.rand.nextInt(800) == 0) {
                if (n4 < 1024.0) {
                    this.entityAge = 0;
                } else {
                    this.setEntityDead();
                }
            }
        }
        if (this.health <= 0) {
            this.isJumping = false;
            this.moveStrafing = 0.0f;
            this.moveForward = 0.0f;
            this.randomYawVelocity = 0.0f;
        } else {
            this.updatePlayerActionState();
        }
        final boolean handleWaterMovement = this.handleWaterMovement();
        final boolean handleLavaMovement = this.handleLavaMovement();
        if (this.isJumping) {
            if (handleWaterMovement) {
                this.motionY += 0.03999999910593033;
            } else if (handleLavaMovement) {
                this.motionY += 0.03999999910593033;
            } else if (this.onGround) {
                this.jump();
            }
        }
        this.moveStrafing *= 0.98f;
        this.moveForward *= 0.98f;
        this.randomYawVelocity *= 0.9f;
        this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
        final List entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224, 0.0, 0.20000000298023224));
        if (entitiesWithinAABBExcludingEntity != null && entitiesWithinAABBExcludingEntity.size() > 0) {
            for (int i = 0; i < entitiesWithinAABBExcludingEntity.size(); ++i) {
                final Entity entity = (Entity) entitiesWithinAABBExcludingEntity.get(i);
                if (entity.canBePushed()) {
                    entity.applyEntityCollision(this);
                }
            }
        }
    }

    protected void jump() {
        this.motionY = 0.41999998688697815;
    }

    protected void updatePlayerActionState() {
        if (this.rand.nextFloat() < 0.07f) {
            this.moveStrafing = (this.rand.nextFloat() - 0.5f) * this.moveSpeed;
            this.moveForward = this.rand.nextFloat() * this.moveSpeed;
        }
        this.isJumping = (this.rand.nextFloat() < 0.01f);
        if (this.rand.nextFloat() < 0.04f) {
            this.randomYawVelocity = (this.rand.nextFloat() - 0.5f) * 60.0f;
        }
        this.rotationYaw += this.randomYawVelocity;
        this.rotationPitch = this.defaultPitch;
        final boolean handleWaterMovement = this.handleWaterMovement();
        final boolean handleLavaMovement = this.handleLavaMovement();
        if (handleWaterMovement || handleLavaMovement) {
            this.isJumping = (this.rand.nextFloat() < 0.8f);
        }
    }

    public void onEntityDeath() {
    }

    public boolean getCanSpawnHere(final double nya1, final double nya2, final double nya3) {
        this.setPosition(nya1, nya2 + this.height / 2.0f, nya3);
        return this.worldObj.checkIfAABBIsClear1(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.getIsAnyLiquid(this.boundingBox);
    }

    @Override
    protected void kill() {
        this.attackEntityFrom(null, 4);
    }
}
