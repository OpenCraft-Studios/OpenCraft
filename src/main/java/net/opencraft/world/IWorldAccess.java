
package net.opencraft.world;

import net.opencraft.entity.Entity;

public interface IWorldAccess {

    void markBlockAndNeighborsNeedsUpdate(final int integer1, final int integer2, final int integer3);

    void markBlockRangeNeedsUpdate(final int integer1, final int integer2, final int integer3, final int integer4, final int integer5, final int integer6);

    void playSound(final String soundName, final double xCoord, final double yCoord, final double zCoord, final float volume, final float pitch);

    void spawnParticle(final String particle, final double xCoordBlock, final double yCoordBlock, final double zCoordBlock, final double xPosition, final double yPosition, final double zPosition);

    void obtainEntitySkin(final Entity entity);

    void releaseEntitySkin(final Entity entity);

    void updateAllRenderers();
}
