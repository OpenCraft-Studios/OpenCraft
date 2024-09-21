
package net.ncraft.client.renderer.culling;

import net.ncraft.util.AxisAlignedBB;

public interface ICamera {

    boolean isBoundingBoxInFrustum(final AxisAlignedBB aabb);

    void setPosition(final double xCoord, final double yCoord, final double zCoord);
}
