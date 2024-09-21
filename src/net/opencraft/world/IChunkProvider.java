
package net.opencraft.world;

import net.opencraft.client.gui.IProgressUpdate;
import net.opencraft.world.chunk.Chunk;

public interface IChunkProvider {

    boolean chunkExists(final int integer1, final int integer2);

    Chunk provideChunk(final int integer1, final int integer2);

    void populate(final IChunkProvider ch, final int integer2, final int integer3);

    boolean saveChunks(final boolean boolean1, final IProgressUpdate jd);

    boolean unload100OldestChunks();

    boolean canSave();
}
