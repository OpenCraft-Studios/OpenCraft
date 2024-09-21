
package net.opencraft.world.chunk.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import net.opencraft.CompressedStreamTools;
import net.opencraft.entity.Entity;
import net.opencraft.entity.EntityList;
import net.opencraft.nbt.NBTBase;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.nbt.NBTTagList;
import net.opencraft.tileentity.TileEntity;
import net.opencraft.world.IChunkLoader;
import net.opencraft.world.World;
import net.opencraft.world.chunk.Chunk;
import net.opencraft.world.chunk.NibbleArray;

public class ChunkLoader implements IChunkLoader {

    private File saveDir;
    private boolean createIfNecessary;

    public ChunkLoader(final File file, final boolean boolean2) {
        this.saveDir = file;
        this.createIfNecessary = boolean2;
    }

    private File chunkFileForXZ(final int integer1, final int integer2) {
        final String string = "c." + Integer.toString(integer1, 36) + "." + Integer.toString(integer2, 36) + ".dat";
        final String string2 = Integer.toString(integer1 & 0x3F, 36);
        final String string3 = Integer.toString(integer2 & 0x3F, 36);
        final File file = new File(this.saveDir, string2);
        if (!file.exists()) {
            if (!this.createIfNecessary) {
                return null;
            }
            file.mkdir();
        }
        final File file2 = new File(file, string3);
        if (!file2.exists()) {
            if (!this.createIfNecessary) {
                return null;
            }
            file2.mkdir();
        }
        final File file3 = new File(file2, string);
        if (!file3.exists() && !this.createIfNecessary) {
            return null;
        }
        return file3;
    }

    public Chunk loadChunk(final World fe, final int integer2, final int integer3) {
        final File chunkFileForXZ = this.chunkFileForXZ(integer2, integer3);
        if (chunkFileForXZ != null && chunkFileForXZ.exists()) {
            try {
                return loadChunkIntoWorldFromCompound(fe, CompressedStreamTools.loadGzippedCompoundFromOutputStream((InputStream) new FileInputStream(chunkFileForXZ)).getCompoundTag("Level"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public void saveChunk(final World fe, final Chunk jw) {
        final File chunkFileForXZ = this.chunkFileForXZ(jw.xPosition, jw.zPosition);
        if (chunkFileForXZ.exists()) {
            fe.setSizeOnDisk -= chunkFileForXZ.length();
        }
        try {
            final File file = new File(this.saveDir, "tmp_chunk.dat");
            final FileOutputStream outputStream = new FileOutputStream(file);
            final NBTTagCompound ae = new NBTTagCompound();
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            ae.setTag("Level", (NBTBase) nbtTagCompound);
            this.storeChunkInCompound(jw, fe, nbtTagCompound);
            CompressedStreamTools.writeGzippedCompoundToOutputStream(ae, (OutputStream) outputStream);
            outputStream.close();
            if (chunkFileForXZ.exists()) {
                chunkFileForXZ.delete();
            }
            file.renameTo(chunkFileForXZ);
            fe.setSizeOnDisk += chunkFileForXZ.length();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void storeChunkInCompound(final Chunk jw, final World fe, final NBTTagCompound ae) {
        ae.setInteger("xPos", jw.xPosition);
        ae.setInteger("zPos", jw.zPosition);
        ae.setLong("LastUpdate", fe.getWorldTime);
        ae.setByteArray("Blocks", jw.blocks);
        ae.setByteArray("Data", jw.data.data);
        ae.setByteArray("SkyLight", jw.skylightMap.data);
        ae.setByteArray("BlockLight", jw.blocklightMap.data);
        ae.setByteArray("HeightMap", jw.heightMap);
        ae.setBoolean("TerrainPopulated", jw.isTerrainPopulated);
        jw.hasEntities = false;
        final NBTTagList hm = new NBTTagList();
        for (int i = 0; i < jw.entities.length; ++i) {
            for (final Entity entity : jw.entities[i]) {
                jw.hasEntities = true;
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                if (entity.addEntityID(nbtTagCompound)) {
                    hm.setTag(nbtTagCompound);
                }
            }
        }
        ae.setTag("Entities", (NBTBase) hm);
        final NBTTagList hm2 = new NBTTagList();
        for (final TileEntity tileEntity : jw.chunkTileEntityMap.values()) {
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            tileEntity.writeToNBT(nbtTagCompound2);
            hm2.setTag(nbtTagCompound2);
        }
        ae.setTag("TileEntities", (NBTBase) hm2);
    }

    public static Chunk loadChunkIntoWorldFromCompound(final World fe, final NBTTagCompound ae) {
        final Chunk chunk = new Chunk(fe, ae.getInteger("xPos"), ae.getInteger("zPos"));
        chunk.blocks = ae.getByteArray("Blocks");
        chunk.data = new NibbleArray(ae.getByteArray("Data"));
        chunk.skylightMap = new NibbleArray(ae.getByteArray("SkyLight"));
        chunk.blocklightMap = new NibbleArray(ae.getByteArray("BlockLight"));
        chunk.heightMap = ae.getByteArray("HeightMap");
        chunk.isTerrainPopulated = ae.getBoolean("TerrainPopulated");
        if (!chunk.data.isValid()) {
            chunk.data = new NibbleArray(chunk.blocks.length);
        }
        if (chunk.heightMap == null || !chunk.skylightMap.isValid()) {
            chunk.heightMap = new byte[256];
            chunk.skylightMap = new NibbleArray(chunk.blocks.length);
            chunk.generateSkylightMap();
        }
        if (!chunk.blocklightMap.isValid()) {
            chunk.blocklightMap = new NibbleArray(chunk.blocks.length);
            chunk.func_1014_a();
        }
        final NBTTagList tagList = ae.getTagList("Entities");
        if (tagList != null) {
            for (int i = 0; i < tagList.tagCount(); ++i) {
                final Entity entityFromNBT = EntityList.createEntityFromNBT((NBTTagCompound) tagList.tagAt(i), fe);
                chunk.hasEntities = true;
                if (entityFromNBT != null) {
                    chunk.addEntity(entityFromNBT);
                }
            }
        }
        final NBTTagList tagList2 = ae.getTagList("TileEntities");
        if (tagList2 != null) {
            for (int j = 0; j < tagList2.tagCount(); ++j) {
                final TileEntity andLoadEntity = TileEntity.createAndLoadEntity((NBTTagCompound) tagList2.tagAt(j));
                if (andLoadEntity != null) {
                    chunk.addTileEntity(andLoadEntity);
                }
            }
        }
        return chunk;
    }

    public void chunkTick() {
    }

    public void saveExtraData() {
    }

    public void saveExtraChunkData(final World fe, final Chunk jw) {
    }
}
