
package net.opencraft.util;

import java.util.ArrayList;
import java.util.List;

public class Vec3D {

    private static List vectorList;
    private static int nextVector;
    public double xCoord;
    public double yCoord;
    public double zCoord;

    public static Vec3D createVectorHelper(final double double1, final double double2, final double double3) {
        return new Vec3D(double1, double2, double3);
    }

    public static void initialize() {
        Vec3D.nextVector = 0;
    }

    public static Vec3D createVector(final double double1, final double double2, final double double3) {
        if (Vec3D.nextVector >= Vec3D.vectorList.size()) {
            Vec3D.vectorList.add(createVectorHelper(0.0, 0.0, 0.0));
        }
        return ((Vec3D) Vec3D.vectorList.get(Vec3D.nextVector++)).setComponents(double1, double2, double3);
    }

    private Vec3D(double double1, double double2, double double3) {
        if (double1 == -0.0) {
            double1 = 0.0;
        }
        if (double2 == -0.0) {
            double2 = 0.0;
        }
        if (double3 == -0.0) {
            double3 = 0.0;
        }
        this.xCoord = double1;
        this.yCoord = double2;
        this.zCoord = double3;
    }

    private Vec3D setComponents(final double double1, final double double2, final double double3) {
        this.xCoord = double1;
        this.yCoord = double2;
        this.zCoord = double3;
        return this;
    }

    public Vec3D subtract(final Vec3D bo) {
        return createVector(bo.xCoord - this.xCoord, bo.yCoord - this.yCoord, bo.zCoord - this.zCoord);
    }

    public Vec3D normalize() {
        final double n = MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
        if (n < 1.0E-4) {
            return createVector(0.0, 0.0, 0.0);
        }
        return createVector(this.xCoord / n, this.yCoord / n, this.zCoord / n);
    }

    public Vec3D crossProduct(final Vec3D bo) {
        return createVector(this.yCoord * bo.zCoord - this.zCoord * bo.yCoord, this.zCoord * bo.xCoord - this.xCoord * bo.zCoord, this.xCoord * bo.yCoord - this.yCoord * bo.xCoord);
    }

    public Vec3D addVector(final double double1, final double double2, final double double3) {
        return createVector(this.xCoord + double1, this.yCoord + double2, this.zCoord + double3);
    }

    public double distanceTo(final Vec3D bo) {
        final double n = bo.xCoord - this.xCoord;
        final double n2 = bo.yCoord - this.yCoord;
        final double n3 = bo.zCoord - this.zCoord;
        return MathHelper.sqrt_double(n * n + n2 * n2 + n3 * n3);
    }

    public double squareDistanceTo(final Vec3D bo) {
        final double n = bo.xCoord - this.xCoord;
        final double n2 = bo.yCoord - this.yCoord;
        final double n3 = bo.zCoord - this.zCoord;
        return n * n + n2 * n2 + n3 * n3;
    }

    public double squareDistanceTo(final double double1, final double double2, final double double3) {
        final double n = double1 - this.xCoord;
        final double n2 = double2 - this.yCoord;
        final double n3 = double3 - this.zCoord;
        return n * n + n2 * n2 + n3 * n3;
    }

    public double lengthVector() {
        return MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
    }

    public Vec3D getIntermediateWithXValue(final Vec3D bo, final double double2) {
        final double n = bo.xCoord - this.xCoord;
        final double n2 = bo.yCoord - this.yCoord;
        final double n3 = bo.zCoord - this.zCoord;
        if (n * n < 1.0000000116860974E-7) {
            return null;
        }
        final double n4 = (double2 - this.xCoord) / n;
        if (n4 < 0.0 || n4 > 1.0) {
            return null;
        }
        return createVector(this.xCoord + n * n4, this.yCoord + n2 * n4, this.zCoord + n3 * n4);
    }

    public Vec3D getIntermediateWithYValue(final Vec3D bo, final double double2) {
        final double n = bo.xCoord - this.xCoord;
        final double n2 = bo.yCoord - this.yCoord;
        final double n3 = bo.zCoord - this.zCoord;
        if (n2 * n2 < 1.0000000116860974E-7) {
            return null;
        }
        final double n4 = (double2 - this.yCoord) / n2;
        if (n4 < 0.0 || n4 > 1.0) {
            return null;
        }
        return createVector(this.xCoord + n * n4, this.yCoord + n2 * n4, this.zCoord + n3 * n4);
    }

    public Vec3D getIntermediateWithZValue(final Vec3D bo, final double double2) {
        final double n = bo.xCoord - this.xCoord;
        final double n2 = bo.yCoord - this.yCoord;
        final double n3 = bo.zCoord - this.zCoord;
        if (n3 * n3 < 1.0000000116860974E-7) {
            return null;
        }
        final double n4 = (double2 - this.zCoord) / n3;
        if (n4 < 0.0 || n4 > 1.0) {
            return null;
        }
        return createVector(this.xCoord + n * n4, this.yCoord + n2 * n4, this.zCoord + n3 * n4);
    }

    public String toString() {
        return new StringBuilder().append("(").append(this.xCoord).append(", ").append(this.yCoord).append(", ").append(this.zCoord).append(")").toString();
    }

    static {
        Vec3D.vectorList = (List) new ArrayList();
        Vec3D.nextVector = 0;
    }
}
