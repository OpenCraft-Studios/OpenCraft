
package net.opencraft;

import net.opencraft.client.entity.PlayerControllerSP;
import net.opencraft.world.World;
import net.opencraft.world.chunk.ChunkPosition;

public class aa extends EntitySpawner {

	public final PlayerControllerSP a;

	public aa(final PlayerControllerSP bb, final int integer, final Class class3, final Class[] arr) {
		super(integer, class3, arr);
		this.a = bb;
	}

	@Override
	protected ChunkPosition getRandomChunkPos(final World fe, final int integer2, final int integer3) {
		return new ChunkPosition(integer2 + fe.random.nextInt(256) - 128, fe.random.nextInt(fe.random.nextInt(fe.random.nextInt(112) + 8) + 8), integer3 + fe.random.nextInt(256) - 128);
	}

}
