package net.opencraft.physics;

import static java.lang.Math.*;

import java.util.function.Predicate;

import net.opencraft.client.input.MovingObjectPosition;
import net.opencraft.util.Vec3;

public final class AABB {

	private static final AABBPool pool = AABBPool.createDynamic();

	public double minX, minY, minZ;
	public double maxX, maxY, maxZ;

	public static AABB getEmptyBoundingBox() {
		return getAABB(0, 0, 0, 0, 0, 0);
	}

	public static AABB getAABB(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
	}

	public static void clearAABBPool() {
		pool.aabbsInUse = 0;
	}

	public static AABB getAABBFromPool(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		if (pool.aabbsInUse >= pool.size())
			pool.add(getAABB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0));

		return pool.get(pool.aabbsInUse++).set(minX, minY, minZ, maxX, maxY, maxZ);
	}

	private AABB(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		set(minX, minY, minZ, maxX, maxY, maxZ);
	}

	public AABB set(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
		return this;
	}

	public AABB set(AABB bb) {
		return set(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
	}

	public AABB addCoord(double x, double y, double z) {
		double minX = this.minX;
		double minY = this.minY;
		double minZ = this.minZ;
		double maxX = this.maxX;
		double maxY = this.maxY;
		double maxZ = this.maxZ;
		if (x < 0.0)
			minX += x;
		else
			maxX += x;

		if (y < 0.0)
			minY += y;
		else
			maxY += y;

		if (z < 0.0)
			minZ += z;
		else
			maxZ += z;

		return getAABBFromPool(minX, minY, minZ, maxX, maxY, maxZ);
	}

	public AABB grow(double x, double y, double z) {
		return getAABBFromPool(minX - x, minY - y, minZ - z, maxX + x,
				maxY + y, maxZ + z);
	}

	public AABB getOffsetBoundingBox(double x, double y, double z) {
		return getAABBFromPool(minX + x, minY + y, minZ + z, maxX + x,
				maxY + y, maxZ + z);
	}

	public double calculateXOffset(AABB bb, double offsetX) {
		boolean isOutsideYRange = bb.maxY <= minY || bb.minY >= maxY;
		boolean isOutsideZRange = bb.maxZ <= minZ || bb.minZ >= maxZ;

		if (isOutsideYRange || isOutsideZRange)
			return offsetX;

		if (offsetX > 0 && bb.maxX <= minX)
			offsetX = min(offsetX, minX - bb.maxX);
		if (offsetX < 0 && bb.minX >= maxX)
			offsetX = max(offsetX, maxX - bb.minX);

		return offsetX;
	}

	public double calculateYOffset(AABB bb, double offsetY) {
		boolean isOutsideXRange = bb.maxX <= minX || bb.minX >= maxX;
		boolean isOutsideZRange = bb.maxZ <= minZ || bb.minZ >= maxZ;

		if (isOutsideXRange || isOutsideZRange)
			return offsetY;

		if (offsetY > 0 && bb.maxY <= minY)
			offsetY = min(offsetY, minY - bb.maxY);
		if (offsetY < 0 && bb.minY >= maxY)
			offsetY = max(offsetY, maxY - bb.minY);

		return offsetY;
	}

	public double calculateZOffset(AABB bb, double offsetZ) {
		boolean isOutsideXRange = bb.maxX <= minX || bb.minX >= maxX;
		boolean isOutsideYRange = bb.maxY <= minY || bb.minY >= maxY;

		if (isOutsideXRange || isOutsideYRange)
			return offsetZ;

		if (offsetZ > 0 && bb.maxZ <= minZ)
			offsetZ = min(offsetZ, minZ - bb.maxZ);
		if (offsetZ < 0 && bb.minZ >= maxZ)
			offsetZ = max(offsetZ, maxZ - bb.minZ);

		return offsetZ;
	}

	public boolean intersects(AABB bb) {
		boolean intersectX = bb.maxX > minX && bb.minX < maxX;
		boolean intersectY = bb.maxY > minY && bb.minY < maxY;
		boolean intersectZ = bb.maxZ > minZ && bb.minZ < maxZ;

		return intersectX && intersectY && intersectZ;
	}

	public AABB translate(double x, double y, double z) {
		minX += x;
		minY += y;
		minZ += z;
		maxX += x;
		maxY += y;
		maxZ += z;
		return this;
	}

	public double getAverageEdgeLength() {
		return (maxX - minX + (maxY - minY) + (maxZ - minZ)) / 3.0;
	}

	public AABB copy() {
		return getAABBFromPool(minX, minY, minZ, maxX, maxY, maxZ);
	}

	public MovingObjectPosition calculateIntercept(Vec3 start, Vec3 end) {
		// Calculate intersections with the min and max planes of each axis
		Vec3 xMinIntersection = getIntersectionWithPlaneX(start, end, minX);
		Vec3 xMaxIntersection = getIntersectionWithPlaneX(start, end, maxX);
		Vec3 yMinIntersection = getIntersectionWithPlaneY(start, end, minY);
		Vec3 yMaxIntersection = getIntersectionWithPlaneY(start, end, maxY);
		Vec3 zMinIntersection = getIntersectionWithPlaneZ(start, end, minZ);
		Vec3 zMaxIntersection = getIntersectionWithPlaneZ(start, end, maxZ);

		// Filter out the invalid intersections for each axis
		xMinIntersection = filterInvalidIntersection(xMinIntersection, this::isVecInYZ);
		xMaxIntersection = filterInvalidIntersection(xMaxIntersection, this::isVecInYZ);
		yMinIntersection = filterInvalidIntersection(yMinIntersection, this::isVecInXZ);
		yMaxIntersection = filterInvalidIntersection(yMaxIntersection, this::isVecInXZ);
		zMinIntersection = filterInvalidIntersection(zMinIntersection, this::isVecInXY);
		zMaxIntersection = filterInvalidIntersection(zMaxIntersection, this::isVecInXY);

		// Find the closest valid intersection point to the start vector
		Vec3 closestIntersection = getClosestIntersection(start, xMinIntersection, xMaxIntersection, yMinIntersection,
				yMaxIntersection, zMinIntersection, zMaxIntersection);

		// If no valid intersection is found, return null
		if (closestIntersection == null)
			return null;

		// Determine the face index based on the closest intersection point
		int faceIndex = getFaceIndex(closestIntersection, xMinIntersection, xMaxIntersection, yMinIntersection,
				yMaxIntersection, zMinIntersection, zMaxIntersection);

		// Return the resulting MovingObjectPosition with the calculated intersection
		// point
		return new MovingObjectPosition(0, 0, 0, faceIndex, closestIntersection);
	}

	// Method to calculate intersection with a plane on the X axis
	private Vec3 getIntersectionWithPlaneX(Vec3 start, Vec3 end, double xValue) {
		return start.getIntermediateWithXValue(end, xValue);
	}

	// Method to calculate intersection with a plane on the Y axis
	private Vec3 getIntersectionWithPlaneY(Vec3 start, Vec3 end, double yValue) {
		return start.getIntermediateWithYValue(end, yValue);
	}

	// Method to calculate intersection with a plane on the Z axis
	private Vec3 getIntersectionWithPlaneZ(Vec3 start, Vec3 end, double zValue) {
		return start.getIntermediateWithZValue(end, zValue);
	}

	// Filter out intersections that do not fall within the specified bounds
	private Vec3 filterInvalidIntersection(Vec3 intersection, Predicate<Vec3> isValid) {
		return (intersection != null && isValid.test(intersection)) ? intersection : null;
	}

	// Method to find the closest valid intersection point
	private Vec3 getClosestIntersection(Vec3 start, Vec3... intersections) {
		Vec3 closest = null;
		for (Vec3 intersection : intersections) {
			if (intersection != null
					&& (closest == null || start.distanceSquared(intersection) < start.distanceSquared(closest))) {
				closest = intersection;
			}
		}
		return closest;
	}

	// Determine the face index based on the closest intersection point
	private int getFaceIndex(Vec3 closest, Vec3 xMin, Vec3 xMax, Vec3 yMin, Vec3 yMax, Vec3 zMin, Vec3 zMax) {
		if (closest == xMin)
			return 4;
		if (closest == xMax)
			return 5;
		if (closest == yMin)
			return 0;
		if (closest == yMax)
			return 1;
		if (closest == zMin)
			return 2;
		if (closest == zMax)
			return 3;
		return -1; // Default case if no intersection matches (shouldn't happen)
	}

	private boolean isVecInYZ(final Vec3 var1) {
		return var1 != null && var1.y >= minY && var1.y <= maxY && var1.z >= minZ && var1.z <= maxZ;
	}

	private boolean isVecInXZ(final Vec3 var1) {
		return var1 != null && var1.x >= minX && var1.x <= maxX && var1.z >= minZ && var1.z <= maxZ;
	}

	private boolean isVecInXY(final Vec3 var1) {
		return var1 != null && var1.x >= minX && var1.x <= maxX && var1.y >= minY && var1.y <= maxY;
	}

}
