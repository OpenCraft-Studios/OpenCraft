
package net.opencraft.world.chunk;

import net.opencraft.client.renderer.gui.IProgressUpdate;
import net.opencraft.world.IChunkLoader;
import net.opencraft.world.IChunkProvider;
import net.opencraft.world.World;

public class ChunkProviderClient implements IChunkProvider {

    private Chunk[] blankChunk;
    private World worldObj;
    private IChunkLoader chunkLoader;
    byte[] byteSomething;

    public ChunkProviderClient(final World fe, final IChunkLoader bg) {
        this.blankChunk = new Chunk[256];
        this.byteSomething = new byte[32768];
        this.worldObj = fe;
        this.chunkLoader = bg;
    }

    public boolean chunkExists(final int integer1, final int integer2) {
        final int n = (integer1 & 0xF) | (integer2 & 0xF) * 16;
        return this.blankChunk[n] != null && this.blankChunk[n].isAtLocation(integer1, integer2);
    }

    public Chunk provideChunk(final int integer1, final int integer2) {
        final int n = (integer1 & 0xF) | (integer2 & 0xF) * 16;
        try {
            if (!this.chunkExists(integer1, integer2)) {
                Chunk c = this.c(integer1, integer2);
                if (c == null) {
                    c = new Chunk(this.worldObj, this.byteSomething, integer1, integer2);
                    c.q = true;
                    c.neverSave = true;
                }
                this.blankChunk[n] = c;
            }
            return this.blankChunk[n];
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private synchronized Chunk c(final int integer1, final int integer2) {
        return this.chunkLoader.loadChunk(this.worldObj, integer1, integer2);
    }

    public void populate(final IChunkProvider ch, final int integer2, final int integer3) {
    }

    public boolean saveChunks(final boolean boolean1, final IProgressUpdate jd) {
        return true;
    }

    public boolean unload100OldestChunks() {
        return false;
    }

    public boolean canSave() {
        return false;
    }
}
