
package net.opencraft;

import net.opencraft.client.entity.PlayerControllerSP;
import net.opencraft.world.World;
import net.opencraft.world.chunk.ChunkPosition;

public class aa extends ck {

    public final /* synthetic */ PlayerControllerSP a;

    public aa(final PlayerControllerSP bb, final int integer, final Class class3, final Class[] arr) {
        super(integer, class3, arr);
        this.a = bb;
    }

    @Override
    protected ChunkPosition a(final World fe, final int integer2, final int integer3) {
        return new ChunkPosition(integer2 + fe.rand.nextInt(256) - 128, fe.rand.nextInt(fe.rand.nextInt(fe.rand.nextInt(112) + 8) + 8), integer3 + fe.rand.nextInt(256) - 128);
    }
}
