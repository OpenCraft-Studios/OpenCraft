
package net.opencraft.pathfinder;

public class Path {

    private PathPoint[] a;
    private int b;

    public Path() {
        this.a = new PathPoint[1024];
        this.b = 0;
    }

    public PathPoint addPoint(final PathPoint d) {
        if (d.index >= 0) {
            throw new IllegalStateException("OW KNOWS!");
        }
        if (this.b == this.a.length) {
            final PathPoint[] a = new PathPoint[this.b << 1];
            System.arraycopy(this.a, 0, a, 0, this.b);
            this.a = a;
        }
        this.a[this.b] = d;
        d.index = this.b;
        this.a(this.b++);
        return d;
    }

    public void clearPath() {
        this.b = 0;
    }

    public PathPoint dequeue() {
        final PathPoint pathPoint = this.a[0];
        final PathPoint[] a = this.a;
        final int n = 0;
        final PathPoint[] a2 = this.a;
        final int b = this.b - 1;
        this.b = b;
        a[n] = a2[b];
        this.a[this.b] = null;
        if (this.b > 0) {
            this.b(0);
        }
        pathPoint.index = -1;
        return pathPoint;
    }

    public void changeDistance(final PathPoint d, final float float2) {
        final float distanceToTarget = d.distanceToTarget;
        d.distanceToTarget = float2;
        if (float2 < distanceToTarget) {
            this.a(d.index);
        } else {
            this.b(d.index);
        }
    }

    private void a(int integer) {
        final PathPoint pathPoint = this.a[integer];
        final float distanceToTarget = pathPoint.distanceToTarget;
        while (integer > 0) {
            final int n = integer - 1 >> 1;
            final PathPoint pathPoint2 = this.a[n];
            if (distanceToTarget >= pathPoint2.distanceToTarget) {
                break;
            }
            this.a[integer] = pathPoint2;
            pathPoint2.index = integer;
            integer = n;
        }
        this.a[integer] = pathPoint;
        pathPoint.index = integer;
    }

    private void b(int integer) {
        final PathPoint pathPoint = this.a[integer];
        final float distanceToTarget = pathPoint.distanceToTarget;
        while (true) {
            final int n = 1 + (integer << 1);
            final int n2 = n + 1;
            if (n >= this.b) {
                break;
            }
            final PathPoint pathPoint2 = this.a[n];
            final float distanceToTarget2 = pathPoint2.distanceToTarget;
            PathPoint pathPoint3;
            float distanceToTarget3;
            if (n2 >= this.b) {
                pathPoint3 = null;
                distanceToTarget3 = Float.POSITIVE_INFINITY;
            } else {
                pathPoint3 = this.a[n2];
                distanceToTarget3 = pathPoint3.distanceToTarget;
            }
            if (distanceToTarget2 < distanceToTarget3) {
                if (distanceToTarget2 >= distanceToTarget) {
                    break;
                }
                this.a[integer] = pathPoint2;
                pathPoint2.index = integer;
                integer = n;
            } else {
                if (distanceToTarget3 >= distanceToTarget) {
                    break;
                }
                this.a[integer] = pathPoint3;
                pathPoint3.index = integer;
                integer = n2;
            }
        }
        this.a[integer] = pathPoint;
        pathPoint.index = integer;
    }

    public boolean isPathEmpty() {
        return this.b == 0;
    }
}
