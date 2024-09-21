
package net.opencraft.entity;

import net.opencraft.pathfinder.PathEntity;
import net.opencraft.util.MathHelper;
import net.opencraft.util.Vec3D;
import net.opencraft.world.World;

public class EntityCreature extends EntityLiving {

    private PathEntity pathToEntity;
    protected Entity playerToAttack;
    protected boolean hasAttacked;

    public EntityCreature(final World world) {
        super(world);
        this.hasAttacked = false;
    }

    protected boolean canEntityBeSeen(final Entity entity) {
        return this.worldObj.rayTraceBlocks(Vec3D.createVector(this.posX, this.posY + this.getEyeHeight(), this.posZ), Vec3D.createVector(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ)) == null;
    }

    @Override
    protected void updatePlayerActionState() {
        this.hasAttacked = false;
        final float float5 = 16.0f;
        if (this.playerToAttack == null) {
            this.playerToAttack = this.findPlayerToAttack();
            if (this.playerToAttack != null) {
                this.pathToEntity = this.worldObj.getPathToEntity(this, this.playerToAttack, float5);
            }
        } else if (!this.playerToAttack.isEntityAlive()) {
            this.playerToAttack = null;
        } else {
            final float distanceToEntity = this.playerToAttack.getDistanceToEntity(this);
            if (this.canEntityBeSeen(this.playerToAttack)) {
                this.attackEntity(this.playerToAttack, distanceToEntity);
            }
        }
        if (!this.hasAttacked && this.playerToAttack != null && (this.pathToEntity == null || this.rand.nextInt(20) == 0)) {
            this.pathToEntity = this.worldObj.getPathToEntity(this, this.playerToAttack, float5);
        } else if (this.pathToEntity == null || this.rand.nextInt(100) == 0) {
            int floor_double = -1;
            int yCoord = -1;
            int zCoord = -1;
            float n = -99999.0f;
            for (int i = 0; i < 50; ++i) {
                final int floor_double2 = MathHelper.floor_double(this.posX + this.rand.nextInt(11) - 5.0);
                final int floor_double3 = MathHelper.floor_double(this.posY + this.rand.nextInt(7) - 3.0);
                final int floor_double4 = MathHelper.floor_double(this.posZ + this.rand.nextInt(11) - 5.0);
                final float blockPathWeight = this.getBlockPathWeight(floor_double2, floor_double3, floor_double4);
                if (blockPathWeight > n) {
                    n = blockPathWeight;
                    floor_double = floor_double2;
                    yCoord = floor_double3;
                    zCoord = floor_double4;
                }
            }
            if (floor_double > 0) {
                this.pathToEntity = this.worldObj.getEntityPathToXYZ(this, floor_double, yCoord, zCoord, float5);
            }
        }
        int floor_double = MathHelper.floor_double(this.boundingBox.minY);
        final boolean handleWaterMovement = this.handleWaterMovement();
        final boolean handleLavaMovement = this.handleLavaMovement();
        if (this.pathToEntity == null || this.rand.nextInt(100) == 0) {
            super.updatePlayerActionState();
            this.pathToEntity = null;
            return;
        }
        Vec3D vec3D = this.pathToEntity.getPosition(this);
        final float n2 = this.width * 2.0f;
        while (vec3D != null && vec3D.squareDistanceTo(this.posX, this.posY, this.posZ) < n2 * n2 && vec3D.yCoord <= floor_double) {
            this.pathToEntity.incrementPathIndex();
            if (this.pathToEntity.isFinished()) {
                vec3D = null;
                this.pathToEntity = null;
            } else {
                vec3D = this.pathToEntity.getPosition(this);
            }
        }
        this.isJumping = false;
        if (vec3D != null) {
            final double n3 = vec3D.xCoord - this.posX;
            final double n4 = vec3D.zCoord - this.posZ;
            final double n5 = vec3D.yCoord - floor_double;
            this.rotationYaw = (float) (Math.atan2(n4, n3) * 180.0 / 3.1415927410125732) - 90.0f;
            this.moveForward = this.moveSpeed;
            if (this.hasAttacked && this.playerToAttack != null) {
                final double n6 = this.playerToAttack.posX - this.posX;
                final double n7 = this.playerToAttack.posZ - this.posZ;
                final float rotationYaw = this.rotationYaw;
                this.rotationYaw = (float) (Math.atan2(n7, n6) * 180.0 / 3.1415927410125732) - 90.0f;
                final float n8 = (rotationYaw - this.rotationYaw + 90.0f) * 3.1415927f / 180.0f;
                this.moveStrafing = -MathHelper.sin(n8) * this.moveForward * 1.0f;
                this.moveForward = MathHelper.cos(n8) * this.moveForward * 1.0f;
            }
            if (n5 != 0.0) {
                this.isJumping = true;
            }
        }
        if (this.rand.nextFloat() < 0.8f && (handleWaterMovement || handleLavaMovement)) {
            this.isJumping = true;
        }
    }

    protected void attackEntity(final Entity entity, final float xCoord) {
    }

    protected float getBlockPathWeight(final int xCoord, final int yCoord, final int zCoord) {
        return 0.0f;
    }

    protected Entity findPlayerToAttack() {
        return null;
    }

    @Override
    public boolean getCanSpawnHere(final double nya1, final double nya2, final double nya3) {
        return super.getCanSpawnHere(nya1, nya2, nya3) && this.getBlockPathWeight((int) nya1, (int) nya2, (int) nya3) >= 0.0f;
    }
}
