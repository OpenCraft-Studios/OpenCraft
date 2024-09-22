
package net.opencraft.pathfinder;

import static org.joml.Math.*;

public class PathPoint {

    public final int xCoord;
    public final int yCoord;
    public final int zCoord;
    public final int hash;
    int index;
    float totalPathDistance;
    float distanceToNext;
    float distanceToTarget;
    PathPoint previous;
    public boolean isFirst;

    public PathPoint(final int integer1, final int integer2, final int integer3) {
        this.index = -1;
        this.isFirst = false;
        this.xCoord = integer1;
        this.yCoord = integer2;
        this.zCoord = integer3;
        this.hash = (integer1 | integer2 << 10 | integer3 << 20);
    }

    public float distanceTo(final PathPoint d) {
        final float n = (float) (d.xCoord - this.xCoord);
        final float n2 = (float) (d.yCoord - this.yCoord);
        final float n3 = (float) (d.zCoord - this.zCoord);
        return sqrt(n * n + n2 * n2 + n3 * n3);
    }

    public boolean equals(final Object object) {
        return ((PathPoint) object).hash == this.hash;
    }

    public int hashCode() {
        return this.hash;
    }

    public boolean isAssigned() {
        return this.index >= 0;
    }

    public String toString() {
        return new StringBuilder().append(this.xCoord).append(", ").append(this.yCoord).append(", ").append(this.zCoord).toString();
    }
}
