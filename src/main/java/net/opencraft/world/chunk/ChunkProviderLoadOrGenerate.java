
package net.opencraft.world.chunk;

import net.opencraft.block.SandBlock;
import net.opencraft.client.gui.IProgressUpdate;
import net.opencraft.world.IChunkLoader;
import net.opencraft.world.IChunkProvider;
import net.opencraft.world.World;

public class ChunkProviderLoadOrGenerate implements IChunkProvider {

    private Chunk blankChunk;
    private IChunkProvider chunkProvider;
    private IChunkLoader chunkLoader;
    private Chunk[] chunks;
    private World worldObj;
    private int lastQueriedChunkXPos;
    private int lastQueriedChunkZPos;
    private Chunk lastQueriedChunk;

    public ChunkProviderLoadOrGenerate(final World fe, final IChunkLoader bg, final IChunkProvider ch) {
        this.chunks = new Chunk[1024];
        this.lastQueriedChunkXPos = -999999999;
        this.lastQueriedChunkZPos = -999999999;
        this.blankChunk = new Chunk(fe, new byte[32768], 0, 0);
        this.blankChunk.q = true;
        this.blankChunk.neverSave = true;
        this.worldObj = fe;
        this.chunkLoader = bg;
        this.chunkProvider = ch;
    }

    public boolean chunkExists(final int integer1, final int integer2) {
        if (integer1 == this.lastQueriedChunkXPos && integer2 == this.lastQueriedChunkZPos && this.lastQueriedChunk != null) {
            return true;
        }
        final int n = (integer1 & 0x1F) + (integer2 & 0x1F) * 32;
        return this.chunks[n] != null && (this.chunks[n] == this.blankChunk || this.chunks[n].isAtLocation(integer1, integer2));
    }

    public Chunk provideChunk(final int integer1, final int integer2) {
        if (integer1 == this.lastQueriedChunkXPos && integer2 == this.lastQueriedChunkZPos && this.lastQueriedChunk != null) {
            return this.lastQueriedChunk;
        }
        final int n = (integer1 & 0x1F) + (integer2 & 0x1F) * 32;
        if (!this.chunkExists(integer1, integer2)) {
            SandBlock.fallInstantly = true;
            if (this.chunks[n] != null) {
                this.chunks[n].onChunkUnload();
                this.saveChunk(this.chunks[n]);
                this.saveExtraChunkData(this.chunks[n]);
            }
            Chunk chunk = this.func_542_c(integer1, integer2);
            if (chunk == null) {
                if (this.chunkProvider == null) {
                    chunk = this.blankChunk;
                } else {
                    chunk = this.chunkProvider.provideChunk(integer1, integer2);
                }
            }
            this.chunks[n] = chunk;
            if (this.chunks[n] != null) {
                this.chunks[n].onChunkLoad();
            }
            if (!this.chunks[n].isTerrainPopulated && this.chunkExists(integer1 + 1, integer2 + 1) && this.chunkExists(integer1, integer2 + 1) && this.chunkExists(integer1 + 1, integer2)) {
                this.populate(this, integer1, integer2);
            }
            if (this.chunkExists(integer1 - 1, integer2) && !this.provideChunk(integer1 - 1, integer2).isTerrainPopulated && this.chunkExists(integer1 - 1, integer2 + 1) && this.chunkExists(integer1, integer2 + 1) && this.chunkExists(integer1 - 1, integer2)) {
                this.populate(this, integer1 - 1, integer2);
            }
            if (this.chunkExists(integer1, integer2 - 1) && !this.provideChunk(integer1, integer2 - 1).isTerrainPopulated && this.chunkExists(integer1 + 1, integer2 - 1) && this.chunkExists(integer1, integer2 - 1) && this.chunkExists(integer1 + 1, integer2)) {
                this.populate(this, integer1, integer2 - 1);
            }
            if (this.chunkExists(integer1 - 1, integer2 - 1) && !this.provideChunk(integer1 - 1, integer2 - 1).isTerrainPopulated && this.chunkExists(integer1 - 1, integer2 - 1) && this.chunkExists(integer1, integer2 - 1) && this.chunkExists(integer1 - 1, integer2)) {
                this.populate(this, integer1 - 1, integer2 - 1);
            }
            SandBlock.fallInstantly = false;
        }
        this.lastQueriedChunkXPos = integer1;
        this.lastQueriedChunkZPos = integer2;
        this.lastQueriedChunk = this.chunks[n];
        return this.chunks[n];
    }

    private Chunk func_542_c(final int integer1, final int integer2) {
        if (this.chunkLoader == null) {
            return null;
        }
        try {
            final Chunk loadChunk = this.chunkLoader.loadChunk(this.worldObj, integer1, integer2);
            if (loadChunk != null) {
                loadChunk.lastSaveTime = this.worldObj.getWorldTime;
            }
            return loadChunk;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void saveExtraChunkData(final Chunk jw) {
        if (this.chunkLoader == null) {
            return;
        }
        try {
            this.chunkLoader.saveExtraChunkData(this.worldObj, jw);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void saveChunk(final Chunk jw) {
        if (this.chunkLoader == null) {
            return;
        }
        jw.lastSaveTime = this.worldObj.getWorldTime;
        this.chunkLoader.saveChunk(this.worldObj, jw);
    }

    public void populate(final IChunkProvider ch, final int integer2, final int integer3) {
        final Chunk provideChunk = this.provideChunk(integer2, integer3);
        if (!provideChunk.isTerrainPopulated) {
            provideChunk.isTerrainPopulated = true;
            if (this.chunkProvider != null) {
                this.chunkProvider.populate(ch, integer2, integer3);
                provideChunk.setChunkModified();
            }
        }
    }

    @Override
    public boolean saveChunks(final boolean boolean1, final IProgressUpdate jd) {
        int n = 0;
        int n2 = 0;
        if (jd != null) {
            for (int i = 0; i < this.chunks.length; ++i) {
                if (this.chunks[i] != null && this.chunks[i].needsSaving(boolean1)) {
                    ++n2;
                }
            }
        }
        int i = 0;
        for (int j = 0; j < this.chunks.length; ++j) {
            if (this.chunks[j] != null) {
                if (boolean1 && !this.chunks[j].neverSave) {
                    this.saveExtraChunkData(this.chunks[j]);
                }
                if (this.chunks[j].needsSaving(boolean1)) {
                    this.saveChunk(this.chunks[j]);
                    this.chunks[j].isModified = false;
                    if (++n == 2 && !boolean1) {
                        return false;
                    }
                    if (jd != null && ++i % 10 == 0) {
                        jd.setLoadingProgress(i * 100 / n2);
                    }
                }
            }
        }
        if (boolean1) {
            if (this.chunkLoader == null) {
                return true;
            }
            this.chunkLoader.saveExtraData();
        }
        return true;
    }

    public boolean unload100OldestChunks() {
        if (this.chunkLoader != null) {
            this.chunkLoader.chunkTick();
        }
        return this.chunkProvider.unload100OldestChunks();
    }

    public boolean canSave() {
        return true;
    }
}
