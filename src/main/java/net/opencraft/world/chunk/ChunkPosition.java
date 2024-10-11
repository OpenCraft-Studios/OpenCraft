
package net.opencraft.world.chunk;

import java.util.Objects;

public class ChunkPosition {

	public final int x;
	public final int y;
	public final int z;

	public ChunkPosition(final int xCoord, final int yCoord, final int zCoord) {
		this.x = xCoord;
		this.y = yCoord;
		this.z = zCoord;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		ChunkPosition other = (ChunkPosition) obj;
		return x == other.x && y == other.y && z == other.z;
	}

	@Override
	public int hashCode() {
		return this.x * 8976890 + this.y * 981131 + this.z;
	}

}
