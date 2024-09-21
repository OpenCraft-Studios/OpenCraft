
package net.opencraft.client.renderer.culling;

import net.opencraft.util.AxisAlignedBB;

public interface ICamera {

    boolean isBoundingBoxInFrustum(final AxisAlignedBB aabb);

    void setPosition(final double xCoord, final double yCoord, final double zCoord);
}
