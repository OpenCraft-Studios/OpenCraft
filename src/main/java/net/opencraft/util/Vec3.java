
package net.opencraft.util;

public class Vec3 {

    public double x;
    public double y;
    public double z;

    public static Vec3 createVectorHelper(final double double1, final double double2, final double double3) {
        return new Vec3(double1, double2, double3);
    }

    /**
     * Notch's original name for this method
     * */
    public static Vec3 newTemp(final double double1, final double double2, final double double3) {
    	// +75 MB of space :D
        return createVectorHelper(double1, double2, double3);
    }

    private Vec3(double double1, double double2, double double3) {
        if (double1 == -0.0) {
            double1 = 0.0;
        }
        if (double2 == -0.0) {
            double2 = 0.0;
        }
        if (double3 == -0.0) {
            double3 = 0.0;
        }
        this.x = double1;
        this.y = double2;
        this.z = double3;
    }

    private Vec3 setComponents(final double double1, final double double2, final double double3) {
        this.x = double1;
        this.y = double2;
        this.z = double3;
        return this;
    }

    public Vec3 subtract(final Vec3 bo) {
        return newTemp(bo.x - this.x, bo.y - this.y, bo.z - this.z);
    }

    public Vec3 normalize() {
        final double n = Mth.sqrt_double(this.x * this.x + this.y * this.y + this.z * this.z);
        if (n < 1.0E-4) {
            return newTemp(0.0, 0.0, 0.0);
        }
        return newTemp(this.x / n, this.y / n, this.z / n);
    }

    public Vec3 cross(final Vec3 bo) {
        return newTemp(this.y * bo.z - this.z * bo.y, this.z * bo.x - this.x * bo.z, this.x * bo.y - this.y * bo.x);
    }

    public Vec3 add(final double double1, final double double2, final double double3) {
        return newTemp(this.x + double1, this.y + double2, this.z + double3);
    }

    public double distance(final Vec3 bo) {
        final double n = bo.x - this.x;
        final double n2 = bo.y - this.y;
        final double n3 = bo.z - this.z;
        return Mth.sqrt_double(n * n + n2 * n2 + n3 * n3);
    }

    public double distanceSquared(final Vec3 bo) {
        final double n = bo.x - this.x;
        final double n2 = bo.y - this.y;
        final double n3 = bo.z - this.z;
        return n * n + n2 * n2 + n3 * n3;
    }

    public double distanceSquared(final double double1, final double double2, final double double3) {
        final double n = double1 - this.x;
        final double n2 = double2 - this.y;
        final double n3 = double3 - this.z;
        return n * n + n2 * n2 + n3 * n3;
    }

    public double length() {
        return Mth.sqrt_double(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public Vec3 getIntermediateWithXValue(final Vec3 bo, final double double2) {
        final double n = bo.x - this.x;
        final double n2 = bo.y - this.y;
        final double n3 = bo.z - this.z;
        if (n * n < 1.0000000116860974E-7) {
            return null;
        }
        final double n4 = (double2 - this.x) / n;
        if (n4 < 0.0 || n4 > 1.0) {
            return null;
        }
        return newTemp(this.x + n * n4, this.y + n2 * n4, this.z + n3 * n4);
    }

    public Vec3 getIntermediateWithYValue(final Vec3 bo, final double double2) {
        final double n = bo.x - this.x;
        final double n2 = bo.y - this.y;
        final double n3 = bo.z - this.z;
        if (n2 * n2 < 1.0000000116860974E-7) {
            return null;
        }
        final double n4 = (double2 - this.y) / n2;
        if (n4 < 0.0 || n4 > 1.0) {
            return null;
        }
        return newTemp(this.x + n * n4, this.y + n2 * n4, this.z + n3 * n4);
    }

    public Vec3 getIntermediateWithZValue(final Vec3 bo, final double double2) {
        final double n = bo.x - this.x;
        final double n2 = bo.y - this.y;
        final double n3 = bo.z - this.z;
        if (n3 * n3 < 1.0000000116860974E-7) {
            return null;
        }
        final double n4 = (double2 - this.z) / n3;
        if (n4 < 0.0 || n4 > 1.0) {
            return null;
        }
        return newTemp(this.x + n * n4, this.y + n2 * n4, this.z + n3 * n4);
    }

    public String toString() {
        return new StringBuilder().append("(").append(this.x).append(", ").append(this.y).append(", ").append(this.z).append(")").toString();
    }
}
