
package net.opencraft.pathfinder;

import java.util.HashMap;
import java.util.Map;
import net.opencraft.block.material.Material;
import net.opencraft.entity.Entity;
import net.opencraft.util.MathHelper;
import net.opencraft.world.IBlockAccess;

public class Pathfinder {

    private IBlockAccess worldMap;
    private Path path;
    private Map pointMap;
    private PathPoint[] pathOptions;

    public Pathfinder(final IBlockAccess iv) {
        this.path = new Path();
        this.pointMap = (Map) new HashMap();
        this.pathOptions = new PathPoint[32];
        this.worldMap = iv;
    }

    public PathEntity createEntityPathTo(final Entity eq1, final Entity eq2, final float float3) {
        return this.createEntityPathTo(eq1, eq2.posX, eq2.boundingBox.minY, eq2.posZ, float3);
    }

    public PathEntity createEntityPathTo(final Entity eq, final int integer2, final int integer3, final int integer4, final float float5) {
        return this.createEntityPathTo(eq, integer2 + 0.5f, integer3 + 0.5f, integer4 + 0.5f, float5);
    }

    private PathEntity createEntityPathTo(final Entity eq, final double double2, final double double3, final double double4, final float float5) {
        this.path.clearPath();
        this.pointMap.clear();
        return this.addToPath(eq, this.openPoint(MathHelper.floor_double(eq.boundingBox.minX), MathHelper.floor_double(eq.boundingBox.minY), MathHelper.floor_double(eq.boundingBox.minZ)), this.openPoint(MathHelper.floor_double(double2 - eq.width / 2.0f), MathHelper.floor_double(double3), MathHelper.floor_double(double4 - eq.width / 2.0f)), new PathPoint(MathHelper.floor_float(eq.width + 1.0f), MathHelper.floor_float(eq.height + 1.0f), MathHelper.floor_float(eq.width + 1.0f)), float5);
    }

    private PathEntity addToPath(final Entity eq, final PathPoint d2, final PathPoint d3, final PathPoint d4, final float float5) {
        d2.totalPathDistance = 0.0f;
        d2.distanceToNext = d2.distanceTo(d3);
        d2.distanceToTarget = d2.distanceToNext;
        this.path.clearPath();
        this.path.addPoint(d2);
        PathPoint d5 = d2;
        while (!this.path.isPathEmpty()) {
            final PathPoint dequeue = this.path.dequeue();
            if (dequeue.hash == d3.hash) {
                return this.createEntityPath(d2, d3);
            }
            if (dequeue.distanceTo(d3) < d5.distanceTo(d3)) {
                d5 = dequeue;
            }
            dequeue.isFirst = true;
            for (int pathOptions = this.findPathOptions(eq, dequeue, d4, d3, float5), i = 0; i < pathOptions; ++i) {
                final PathPoint d6 = this.pathOptions[i];
                final float totalPathDistance = dequeue.totalPathDistance + dequeue.distanceTo(d6);
                if (!d6.isAssigned() || totalPathDistance < d6.totalPathDistance) {
                    d6.previous = dequeue;
                    d6.totalPathDistance = totalPathDistance;
                    d6.distanceToNext = d6.distanceTo(d3);
                    if (d6.isAssigned()) {
                        this.path.changeDistance(d6, d6.totalPathDistance + d6.distanceToNext);
                    } else {
                        d6.distanceToTarget = d6.totalPathDistance + d6.distanceToNext;
                        this.path.addPoint(d6);
                    }
                }
            }
        }
        if (d5 == d2) {
            return null;
        }
        return this.createEntityPath(d2, d5);
    }

    private int findPathOptions(final Entity eq, final PathPoint d2, final PathPoint d3, final PathPoint d4, final float float5) {
        int n = 0;
        int n2 = 0;
        if (this.getVerticalOffset(eq, d2.xCoord, d2.yCoord + 1, d2.zCoord, d3) > 0) {
            n2 = 1;
        }
        final PathPoint safePoint = this.getSafePoint(eq, d2.xCoord, d2.yCoord, d2.zCoord + 1, d3, n2);
        final PathPoint safePoint2 = this.getSafePoint(eq, d2.xCoord - 1, d2.yCoord, d2.zCoord, d3, n2);
        final PathPoint safePoint3 = this.getSafePoint(eq, d2.xCoord + 1, d2.yCoord, d2.zCoord, d3, n2);
        final PathPoint safePoint4 = this.getSafePoint(eq, d2.xCoord, d2.yCoord, d2.zCoord - 1, d3, n2);
        if (safePoint != null && !safePoint.isFirst && safePoint.distanceTo(d4) < float5) {
            this.pathOptions[n++] = safePoint;
        }
        if (safePoint2 != null && !safePoint2.isFirst && safePoint2.distanceTo(d4) < float5) {
            this.pathOptions[n++] = safePoint2;
        }
        if (safePoint3 != null && !safePoint3.isFirst && safePoint3.distanceTo(d4) < float5) {
            this.pathOptions[n++] = safePoint3;
        }
        if (safePoint4 != null && !safePoint4.isFirst && safePoint4.distanceTo(d4) < float5) {
            this.pathOptions[n++] = safePoint4;
        }
        return n;
    }

    private PathPoint getSafePoint(final Entity eq, final int integer2, int integer3, final int integer4, final PathPoint d, final int integer6) {
        PathPoint pathPoint = null;
        if (this.getVerticalOffset(eq, integer2, integer3, integer4, d) > 0) {
            pathPoint = this.openPoint(integer2, integer3, integer4);
        }
        if (pathPoint == null && this.getVerticalOffset(eq, integer2, integer3 + integer6, integer4, d) > 0) {
            pathPoint = this.openPoint(integer2, integer3 + integer6, integer4);
        }
        if (pathPoint != null) {
            int n = 0;
            int verticalOffset;
            while (integer3 > 0 && (verticalOffset = this.getVerticalOffset(eq, integer2, integer3 - 1, integer4, d)) > 0) {
                if (verticalOffset < 0) {
                    return null;
                }
                if (++n >= 4) {
                    return null;
                }
                --integer3;
            }
            if (integer3 > 0) {
                pathPoint = this.openPoint(integer2, integer3, integer4);
            }
            final Material blockMaterial = this.worldMap.getBlockMaterial(integer2, integer3 - 1, integer4);
            if (blockMaterial == Material.water || blockMaterial == Material.lava) {
                return null;
            }
        }
        return pathPoint;
    }

    private final PathPoint openPoint(final int integer1, final int integer2, final int integer3) {
        final int n = integer1 | integer2 << 10 | integer3 << 20;
        PathPoint pathPoint = (PathPoint) this.pointMap.get(n);
        if (pathPoint == null) {
            pathPoint = new PathPoint(integer1, integer2, integer3);
            this.pointMap.put(n, pathPoint);
        }
        return pathPoint;
    }

    private int getVerticalOffset(final Entity eq, final int integer2, final int integer3, final int integer4, final PathPoint d) {
        for (int i = integer2; i < integer2 + d.xCoord; ++i) {
            for (int j = integer3; j < integer3 + d.yCoord; ++j) {
                for (int k = integer4; k < integer4 + d.zCoord; ++k) {
                    final Material blockMaterial = this.worldMap.getBlockMaterial(integer2, integer3, integer4);
                    if (blockMaterial.getIsSolid()) {
                        return 0;
                    }
                    if (blockMaterial == Material.water || blockMaterial == Material.lava) {
                        return -1;
                    }
                }
            }
        }
        return 1;
    }

    private PathEntity createEntityPath(final PathPoint d1, final PathPoint d2) {
        int n = 1;
        for (PathPoint previous = d2; previous.previous != null; previous = previous.previous) {
            ++n;
        }
        final PathPoint[] arr = new PathPoint[n];
        PathPoint previous2 = d2;
        arr[--n] = previous2;
        while (previous2.previous != null) {
            previous2 = previous2.previous;
            arr[--n] = previous2;
        }
        return new PathEntity(arr);
    }
}
