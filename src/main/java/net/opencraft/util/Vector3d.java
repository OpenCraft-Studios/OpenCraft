
package net.opencraft.util;

public class Vector3d {

    public double x;
    public double y;
    public double z;

    public Vector3d(double double1, double double2, double double3) {
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

    public Vector3d sub(Vector3d bo) {
        return new Vector3d(bo.x - this.x, bo.y - this.y, bo.z - this.z);
    }

    public Vector3d normalize() {
        final double n = Mth.sqrt_double(this.x * this.x + this.y * this.y + this.z * this.z);
        if (n < 1.0E-4) {
            return new Vector3d(0.0, 0.0, 0.0);
        }
        return new Vector3d(this.x / n, this.y / n, this.z / n);
    }

    public Vector3d cross(final Vector3d bo) {
        return new Vector3d(this.y * bo.z - this.z * bo.y, this.z * bo.x - this.x * bo.z, this.x * bo.y - this.y * bo.x);
    }

    public Vector3d add(final double double1, final double double2, final double double3) {
        return new Vector3d(this.x + double1, this.y + double2, this.z + double3);
    }

    public double distance(final Vector3d bo) {
        final double n = bo.x - this.x;
        final double n2 = bo.y - this.y;
        final double n3 = bo.z - this.z;
        return Mth.sqrt_double(n * n + n2 * n2 + n3 * n3);
    }

    public double distanceSquared(final Vector3d bo) {
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

    public Vector3d getIntermediateWithXValue(final Vector3d bo, final double double2) {
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
        return new Vector3d(this.x + n * n4, this.y + n2 * n4, this.z + n3 * n4);
    }

    public Vector3d getIntermediateWithYValue(final Vector3d bo, final double double2) {
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
        return new Vector3d(this.x + n * n4, this.y + n2 * n4, this.z + n3 * n4);
    }

    public Vector3d getIntermediateWithZValue(final Vector3d bo, final double double2) {
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
        return new Vector3d(this.x + n * n4, this.y + n2 * n4, this.z + n3 * n4);
    }

    public String toString() {
        return new StringBuilder().append("(").append(this.x).append(", ").append(this.y).append(", ").append(this.z).append(")").toString();
    }
}
