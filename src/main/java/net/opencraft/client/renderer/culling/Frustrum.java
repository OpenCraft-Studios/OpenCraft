
package net.opencraft.client.renderer.culling;

import net.opencraft.util.AxisAlignedBB;

public class Frustrum implements ICamera {

    private ClippingHelper clippingHelper;
    private double xPosition;
    private double yPosition;
    private double zPosition;

    public Frustrum() {
        this.clippingHelper = ClippingHelperImpl.getInstance();
    }

    public void setPosition(final double xCoord, final double yCoord, final double zCoord) {
        this.xPosition = xCoord;
        this.yPosition = yCoord;
        this.zPosition = zCoord;
    }

    public boolean isBoxInFrustum(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
        return this.clippingHelper.isBoxInFrustum(minX - this.xPosition, minY - this.yPosition, minZ - this.zPosition, maxX - this.xPosition, maxY - this.yPosition, maxZ - this.zPosition);
    }

    public boolean isBoundingBoxInFrustum(final AxisAlignedBB aabb) {
        return this.isBoxInFrustum(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ);
    }
}
