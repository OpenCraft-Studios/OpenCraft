
package net.opencraft.world.chunk;

public class ChunkPosition {

    public final int x;
    public final int y;
    public final int z;

    public ChunkPosition(final int xCoord, final int yCoord, final int zCoord) {
        this.x = xCoord;
        this.y = yCoord;
        this.z = zCoord;
    }

    public boolean equals(final Object object) {
        if (object instanceof ChunkPosition) {
            final ChunkPosition chunkPosition = (ChunkPosition) object;
            return chunkPosition.x == this.x && chunkPosition.y == this.y && chunkPosition.z == this.z;
        }
        return false;
    }

    public int hashCode() {
        return this.x * 8976890 + this.y * 981131 + this.z;
    }
}
