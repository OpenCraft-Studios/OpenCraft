
package net.opencraft.physics;

import java.util.ArrayList;
import java.util.List;
import net.opencraft.client.input.MovingObjectPosition;
import net.opencraft.util.Vec3;

public class AABB {

    private static List boundingBoxes;
    private static int numBoundingBoxesInUse;
    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;

    public static AABB getBoundingBox(final double aabbMinX, final double aabbMinY, final double aabbMinZ, final double aabbMaxX, final double aabbMaxY, final double aabbMaxZ) {
        return new AABB(aabbMinX, aabbMinY, aabbMinZ, aabbMaxX, aabbMaxY, aabbMaxZ);
    }

    public static void clearBoundingBoxPool() {
        AABB.numBoundingBoxesInUse = 0;
    }

    public static AABB getBoundingBoxFromPool(final double aabbMinX, final double aabbMinY, final double aabbMinZ, final double aabbMaxX, final double aabbMaxY, final double aabbMaxZ) {
        if (AABB.numBoundingBoxesInUse >= AABB.boundingBoxes.size()) {
            AABB.boundingBoxes.add(getBoundingBox(0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
        }
        return ((AABB) AABB.boundingBoxes.get(AABB.numBoundingBoxesInUse++)).setBounds(aabbMinX, aabbMinY, aabbMinZ, aabbMaxX, aabbMaxY, aabbMaxZ);
    }

    private AABB(final double aabbMinX, final double aabbMinY, final double aabbMinZ, final double aabbMaxX, final double aabbMaxY, final double aabbMaxZ) {
        this.minX = aabbMinX;
        this.minY = aabbMinY;
        this.minZ = aabbMinZ;
        this.maxX = aabbMaxX;
        this.maxY = aabbMaxY;
        this.maxZ = aabbMaxZ;
    }

    public AABB setBounds(final double aabbMinX, final double aabbMinY, final double aabbMinZ, final double aabbMaxX, final double aabbMaxY, final double aabbMaxZ) {
        this.minX = aabbMinX;
        this.minY = aabbMinY;
        this.minZ = aabbMinZ;
        this.maxX = aabbMaxX;
        this.maxY = aabbMaxY;
        this.maxZ = aabbMaxZ;
        return this;
    }

    public AABB addCoord(final double xCoord, final double yCoord, final double zCoord) {
        double minX = this.minX;
        double minY = this.minY;
        double minZ = this.minZ;
        double maxX = this.maxX;
        double maxY = this.maxY;
        double maxZ = this.maxZ;
        if (xCoord < 0.0) {
            minX += xCoord;
        }
        if (xCoord > 0.0) {
            maxX += xCoord;
        }
        if (yCoord < 0.0) {
            minY += yCoord;
        }
        if (yCoord > 0.0) {
            maxY += yCoord;
        }
        if (zCoord < 0.0) {
            minZ += zCoord;
        }
        if (zCoord > 0.0) {
            maxZ += zCoord;
        }
        return getBoundingBoxFromPool(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public AABB expand(final double xCoord, final double yCoord, final double zCoord) {
        return getBoundingBoxFromPool(this.minX - xCoord, this.minY - yCoord, this.minZ - zCoord, this.maxX + xCoord, this.maxY + yCoord, this.maxZ + zCoord);
    }

    public AABB getOffsetBoundingBox(final double xCoord, final double yCoord, final double zCoord) {
        return getBoundingBoxFromPool(this.minX + xCoord, this.minY + yCoord, this.minZ + zCoord, this.maxX + xCoord, this.maxY + yCoord, this.maxZ + zCoord);
    }

    public double calculateXOffset(final AABB aabb, double offsetX) {
        if (aabb.maxY <= this.minY || aabb.minY >= this.maxY) {
            return offsetX;
        }
        if (aabb.maxZ <= this.minZ || aabb.minZ >= this.maxZ) {
            return offsetX;
        }
        if (offsetX > 0.0 && aabb.maxX <= this.minX) {
            final double n = this.minX - aabb.maxX;
            if (n < offsetX) {
                offsetX = n;
            }
        }
        if (offsetX < 0.0 && aabb.minX >= this.maxX) {
            final double n = this.maxX - aabb.minX;
            if (n > offsetX) {
                offsetX = n;
            }
        }
        return offsetX;
    }

    public double calculateYOffset(final AABB aabb, double offsetY) {
        if (aabb.maxX <= this.minX || aabb.minX >= this.maxX) {
            return offsetY;
        }
        if (aabb.maxZ <= this.minZ || aabb.minZ >= this.maxZ) {
            return offsetY;
        }
        if (offsetY > 0.0 && aabb.maxY <= this.minY) {
            final double n = this.minY - aabb.maxY;
            if (n < offsetY) {
                offsetY = n;
            }
        }
        if (offsetY < 0.0 && aabb.minY >= this.maxY) {
            final double n = this.maxY - aabb.minY;
            if (n > offsetY) {
                offsetY = n;
            }
        }
        return offsetY;
    }

    public double calculateZOffset(final AABB aabb, double offsetZ) {
        if (aabb.maxX <= this.minX || aabb.minX >= this.maxX) {
            return offsetZ;
        }
        if (aabb.maxY <= this.minY || aabb.minY >= this.maxY) {
            return offsetZ;
        }
        if (offsetZ > 0.0 && aabb.maxZ <= this.minZ) {
            final double n = this.minZ - aabb.maxZ;
            if (n < offsetZ) {
                offsetZ = n;
            }
        }
        if (offsetZ < 0.0 && aabb.minZ >= this.maxZ) {
            final double n = this.maxZ - aabb.minZ;
            if (n > offsetZ) {
                offsetZ = n;
            }
        }
        return offsetZ;
    }

    public boolean intersectsWith(final AABB aabb) {
        return aabb.maxX > this.minX && aabb.minX < this.maxX && aabb.maxY > this.minY && aabb.minY < this.maxY && aabb.maxZ > this.minZ && aabb.minZ < this.maxZ;
    }

    public AABB offset(final double xCoord, final double yCoord, final double zCoord) {
        this.minX += xCoord;
        this.minY += yCoord;
        this.minZ += zCoord;
        this.maxX += xCoord;
        this.maxY += yCoord;
        this.maxZ += zCoord;
        return this;
    }

    public double getAverageEdgeLength() {
        return (this.maxX - this.minX + (this.maxY - this.minY) + (this.maxZ - this.minZ)) / 3.0;
    }

    public AABB copy() {
        return getBoundingBoxFromPool(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }

    public MovingObjectPosition calculateIntercept(final Vec3 var1, final Vec3 var2) {
        Vec3 intermediateWithXValue = var1.getIntermediateWithXValue(var2, this.minX);
        Vec3 intermediateWithXValue2 = var1.getIntermediateWithXValue(var2, this.maxX);
        Vec3 intermediateWithYValue = var1.getIntermediateWithYValue(var2, this.minY);
        Vec3 intermediateWithYValue2 = var1.getIntermediateWithYValue(var2, this.maxY);
        Vec3 intermediateWithZValue = var1.getIntermediateWithZValue(var2, this.minZ);
        Vec3 intermediateWithZValue2 = var1.getIntermediateWithZValue(var2, this.maxZ);
        if (!this.isVecInYZ(intermediateWithXValue)) {
            intermediateWithXValue = null;
        }
        if (!this.isVecInYZ(intermediateWithXValue2)) {
            intermediateWithXValue2 = null;
        }
        if (!this.isVecInXZ(intermediateWithYValue)) {
            intermediateWithYValue = null;
        }
        if (!this.isVecInXZ(intermediateWithYValue2)) {
            intermediateWithYValue2 = null;
        }
        if (!this.isVecInXY(intermediateWithZValue)) {
            intermediateWithZValue = null;
        }
        if (!this.isVecInXY(intermediateWithZValue2)) {
            intermediateWithZValue2 = null;
        }
        Vec3 bo = null;
        if (intermediateWithXValue != null && (bo == null || var1.squareDistanceTo(intermediateWithXValue) < var1.squareDistanceTo(bo))) {
            bo = intermediateWithXValue;
        }
        if (intermediateWithXValue2 != null && (bo == null || var1.squareDistanceTo(intermediateWithXValue2) < var1.squareDistanceTo(bo))) {
            bo = intermediateWithXValue2;
        }
        if (intermediateWithYValue != null && (bo == null || var1.squareDistanceTo(intermediateWithYValue) < var1.squareDistanceTo(bo))) {
            bo = intermediateWithYValue;
        }
        if (intermediateWithYValue2 != null && (bo == null || var1.squareDistanceTo(intermediateWithYValue2) < var1.squareDistanceTo(bo))) {
            bo = intermediateWithYValue2;
        }
        if (intermediateWithZValue != null && (bo == null || var1.squareDistanceTo(intermediateWithZValue) < var1.squareDistanceTo(bo))) {
            bo = intermediateWithZValue;
        }
        if (intermediateWithZValue2 != null && (bo == null || var1.squareDistanceTo(intermediateWithZValue2) < var1.squareDistanceTo(bo))) {
            bo = intermediateWithZValue2;
        }
        if (bo == null) {
            return null;
        }
        int integer4 = -1;
        if (bo == intermediateWithXValue) {
            integer4 = 4;
        }
        if (bo == intermediateWithXValue2) {
            integer4 = 5;
        }
        if (bo == intermediateWithYValue) {
            integer4 = 0;
        }
        if (bo == intermediateWithYValue2) {
            integer4 = 1;
        }
        if (bo == intermediateWithZValue) {
            integer4 = 2;
        }
        if (bo == intermediateWithZValue2) {
            integer4 = 3;
        }
        return new MovingObjectPosition(0, 0, 0, integer4, bo);
    }

    private boolean isVecInYZ(final Vec3 var1) {
        return var1 != null && var1.yCoord >= this.minY && var1.yCoord <= this.maxY && var1.zCoord >= this.minZ && var1.zCoord <= this.maxZ;
    }

    private boolean isVecInXZ(final Vec3 var1) {
        return var1 != null && var1.xCoord >= this.minX && var1.xCoord <= this.maxX && var1.zCoord >= this.minZ && var1.zCoord <= this.maxZ;
    }

    private boolean isVecInXY(final Vec3 var1) {
        return var1 != null && var1.xCoord >= this.minX && var1.xCoord <= this.maxX && var1.yCoord >= this.minY && var1.yCoord <= this.maxY;
    }

    public void setBB(final AABB aabb) {
        this.minX = aabb.minX;
        this.minY = aabb.minY;
        this.minZ = aabb.minZ;
        this.maxX = aabb.maxX;
        this.maxY = aabb.maxY;
        this.maxZ = aabb.maxZ;
    }

    static {
        AABB.boundingBoxes = new ArrayList<AABB>();
        AABB.numBoundingBoxesInUse = 0;
    }
}
