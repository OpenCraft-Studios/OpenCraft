
package net.opencraft.client.input;

import net.opencraft.entity.Entity;
import net.opencraft.util.Vector3d;

public class MovingObjectPosition {

    public int typeOfHit;
    public int blockX;
    public int blockY;
    public int blockZ;
    public int sideHit;
    public Vector3d hitVec;
    public Entity entityHit;

    public MovingObjectPosition(final int integer1, final int integer2, final int integer3, final int integer4, final Vector3d bo) {
        this.typeOfHit = 0;
        this.blockX = integer1;
        this.blockY = integer2;
        this.blockZ = integer3;
        this.sideHit = integer4;
        this.hitVec = new Vector3d(bo.x, bo.y, bo.z);
    }

    public MovingObjectPosition(final Entity eq) {
        this.typeOfHit = 1;
        this.entityHit = eq;
        this.hitVec = new Vector3d(eq.posX, eq.posY, eq.posZ);
    }
}
