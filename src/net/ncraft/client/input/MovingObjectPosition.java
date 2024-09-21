
package net.ncraft.client.input;

import net.ncraft.entity.Entity;
import net.ncraft.util.Vec3D;

public class MovingObjectPosition {

    public int typeOfHit;
    public int blockX;
    public int blockY;
    public int blockZ;
    public int sideHit;
    public Vec3D hitVec;
    public Entity entityHit;

    public MovingObjectPosition(final int integer1, final int integer2, final int integer3, final int integer4, final Vec3D bo) {
        this.typeOfHit = 0;
        this.blockX = integer1;
        this.blockY = integer2;
        this.blockZ = integer3;
        this.sideHit = integer4;
        this.hitVec = Vec3D.createVector(bo.xCoord, bo.yCoord, bo.zCoord);
    }

    public MovingObjectPosition(final Entity eq) {
        this.typeOfHit = 1;
        this.entityHit = eq;
        this.hitVec = Vec3D.createVector(eq.posX, eq.posY, eq.posZ);
    }
}
