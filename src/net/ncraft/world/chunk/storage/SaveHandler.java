
package net.ncraft.world.chunk.storage;

import net.ncraft.world.World;
import net.ncraft.world.IChunkProvider;
import net.ncraft.client.canvas.CanvasIsomPreview;
import net.ncraft.world.chunk.ChunkProviderClient;
import java.io.File;

public class SaveHandler extends World {

    public final /* synthetic */ CanvasIsomPreview q;

    public SaveHandler(final CanvasIsomPreview be, final File file, final String string) {
        super(file, string);
        this.q = be;
    }

    @Override
    protected IChunkProvider a(final File file) {
        return new ChunkProviderClient(this, new ChunkLoader(file, false));
    }
}
