
package net.opencraft.world.chunk;

import net.opencraft.entity.Entity;
import net.opencraft.block.BlockContainer;
import net.opencraft.block.Block;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.opencraft.util.AxisAlignedBB;
import net.opencraft.EnumSkyBlock;
import net.opencraft.util.MathHelper;
import net.opencraft.tileentity.TileEntity;
import net.opencraft.world.World;

public class Chunk {

    public static boolean isLit;
    public byte[] blocks;
    public boolean isChunkLoaded;
    public World worldObj;
    public NibbleArray data;
    public NibbleArray skylightMap;
    public NibbleArray blocklightMap;
    public byte[] heightMap;
    public int lowestBlockHeight;
    public final int xPosition;
    public final int zPosition;
    public Map<Integer, TileEntity> chunkTileEntityMap;
    public List<Entity>[] entities;
    public boolean isTerrainPopulated;
    public boolean isModified;
    public boolean neverSave;
    public boolean q;
    public boolean hasEntities;
    public long lastSaveTime;

    public Chunk(final World world, final int xCoord, final int zCoord) {
        this.chunkTileEntityMap = new HashMap<>();
        this.entities = new List[8];
        this.isTerrainPopulated = false;
        this.isModified = false;
        this.q = false;
        this.hasEntities = false;
        this.lastSaveTime = 0L;
        this.worldObj = world;
        this.xPosition = xCoord;
        this.zPosition = zCoord;
        this.heightMap = new byte[256];
        for (int i = 0; i < this.entities.length; ++i) {
            this.entities[i] = new ArrayList<Entity>();
        }
    }

    public Chunk(final World world, final byte[] byteArray, final int xCoord, final int zCoord) {
        this(world, xCoord, zCoord);
        this.blocks = byteArray;
        this.data = new NibbleArray(byteArray.length);
        this.skylightMap = new NibbleArray(byteArray.length);
        this.blocklightMap = new NibbleArray(byteArray.length);
    }

    public boolean isAtLocation(final int xCoord, final int zCoord) {
        return xCoord == this.xPosition && zCoord == this.zPosition;
    }

    public int getHeightValue(final int nya1, final int nya2) {
        return this.heightMap[nya2 << 4 | nya1] & 0xFF;
    }

    public void func_1014_a() {
    }

    public void generateSkylightMap() {
        int lowestBlockHeight = 127;
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                this.heightMap[j << 4 | i] = -128;
                this.relightBlock(i, 127, j);
                if ((this.heightMap[j << 4 | i] & 0xFF) < lowestBlockHeight) {
                    lowestBlockHeight = (this.heightMap[j << 4 | i] & 0xFF);
                }
            }
        }
        this.lowestBlockHeight = lowestBlockHeight;
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                this.propagateSkylightOcclusion(i, j);
            }
        }
        this.isModified = true;
    }

    private void propagateSkylightOcclusion(final int integer1, final int integer2) {
        final int heightValue = this.getHeightValue(integer1, integer2);
        final int n = this.xPosition * 16 + integer1;
        final int n2 = this.zPosition * 16 + integer2;
        this.checkSkylightNeighborHeight(n - 1, n2, heightValue);
        this.checkSkylightNeighborHeight(n + 1, n2, heightValue);
        this.checkSkylightNeighborHeight(n, n2 - 1, heightValue);
        this.checkSkylightNeighborHeight(n, n2 + 1, heightValue);
    }

    private void checkSkylightNeighborHeight(final int integer1, final int integer2, final int integer3) {
        final int heightValue = this.worldObj.getHeightValue(integer1, integer2);
        if (heightValue > integer3) {
            this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, integer1, integer3, integer2, integer1, heightValue, integer2);
        } else if (heightValue < integer3) {
            this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, integer1, heightValue, integer2, integer1, integer3, integer2);
        }
        this.isModified = true;
    }

    private void relightBlock(final int xCoord, final int yCoord, final int zCoord) {
        int n2;
        final int n = n2 = (this.heightMap[zCoord << 4 | xCoord] & 0xFF);
        if (yCoord > n) {
            n2 = yCoord;
        }
        while (n2 > 0 && Block.lightOpacity[this.getBlockID(xCoord, n2 - 1, zCoord)] == 0) {
            --n2;
        }
        if (n2 == n) {
            return;
        }
        this.worldObj.markBlocksDirtyVertical(xCoord, zCoord, n2, n);
        this.heightMap[zCoord << 4 | xCoord] = (byte) n2;
        if (n2 < this.lowestBlockHeight) {
            this.lowestBlockHeight = n2;
        } else {
            int n3 = 127;
            for (int i = 0; i < 16; ++i) {
                for (int j = 0; j < 16; ++j) {
                    if ((this.heightMap[j << 4 | i] & 0xFF) < n3) {
                        n3 = (this.heightMap[j << 4 | i] & 0xFF);
                    }
                }
            }
            this.lowestBlockHeight = n3;
        }
        int n3 = this.xPosition * 16 + xCoord;
        int i = this.zPosition * 16 + zCoord;
        if (n2 < n) {
            for (int j = n2; j < n; ++j) {
                this.skylightMap.setNibble(xCoord, j, zCoord, 15);
            }
        } else {
            this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, n3, n, i, n3, n2, i);
            for (int j = n; j < n2; ++j) {
                this.skylightMap.setNibble(xCoord, j, zCoord, 0);
            }
        }
        int j = 15;
        final int integer6 = n2;
        while (n2 > 0 && j > 0) {
            --n2;
            int n4 = Block.lightOpacity[this.getBlockID(xCoord, n2, zCoord)];
            if (n4 == 0) {
                n4 = 1;
            }
            j -= n4;
            if (j < 0) {
                j = 0;
            }
            this.skylightMap.setNibble(xCoord, n2, zCoord, j);
        }
        while (n2 > 0 && Block.lightOpacity[this.getBlockID(xCoord, n2 - 1, zCoord)] == 0) {
            --n2;
        }
        if (n2 != integer6) {
            this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, n3 - 1, n2, i - 1, n3 + 1, integer6, i + 1);
        }
        this.isModified = true;
    }

    public int getBlockID(final int integer1, final int integer2, final int integer3) {
        return this.blocks[integer1 << 11 | integer3 << 7 | integer2];
    }

    public boolean setBlockIDWithMetadata(final int integer1, final int integer2, final int integer3, final int integer4, final int integer5) {
        final byte b = (byte) integer4;
        final int n = this.heightMap[integer3 << 4 | integer1] & 0xFF;
        final int n2 = this.blocks[integer1 << 11 | integer3 << 7 | integer2] & 0xFF;
        if (n2 == integer4) {
            return false;
        }
        final int xCoord = this.xPosition * 16 + integer1;
        final int zCoord = this.zPosition * 16 + integer3;
        this.blocks[integer1 << 11 | integer3 << 7 | integer2] = b;
        if (n2 != 0) {
            Block.blocksList[n2].onBlockRemoval(this.worldObj, xCoord, integer2, zCoord);
        }
        this.data.setNibble(integer1, integer2, integer3, integer5);
        if (Block.lightOpacity[b] != 0) {
            if (integer2 >= n) {
                this.relightBlock(integer1, integer2 + 1, integer3);
            }
        } else if (integer2 == n - 1) {
            this.relightBlock(integer1, integer2, integer3);
        }
        this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, xCoord, integer2, zCoord, xCoord, integer2, zCoord);
        this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Block, xCoord, integer2, zCoord, xCoord, integer2, zCoord);
        this.propagateSkylightOcclusion(integer1, integer3);
        if (integer4 != 0) {
            Block.blocksList[integer4].onBlockAdded(this.worldObj, xCoord, integer2, zCoord);
        }
        return this.isModified = true;
    }

    public boolean setBlockID(final int integer1, final int integer2, final int integer3, final int integer4) {
        final byte b = (byte) integer4;
        final int n = this.heightMap[integer3 << 4 | integer1] & 0xFF;
        final int n2 = this.blocks[integer1 << 11 | integer3 << 7 | integer2] & 0xFF;
        if (n2 == integer4) {
            return false;
        }
        final int xCoord = this.xPosition * 16 + integer1;
        final int zCoord = this.zPosition * 16 + integer3;
        this.blocks[integer1 << 11 | integer3 << 7 | integer2] = b;
        if (n2 != 0) {
            Block.blocksList[n2].onBlockRemoval(this.worldObj, xCoord, integer2, zCoord);
        }
        this.data.setNibble(integer1, integer2, integer3, 0);
        if (Block.lightOpacity[b] != 0) {
            if (integer2 >= n) {
                this.relightBlock(integer1, integer2 + 1, integer3);
            }
        } else if (integer2 == n - 1) {
            this.relightBlock(integer1, integer2, integer3);
        }
        this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, xCoord, integer2, zCoord, xCoord, integer2, zCoord);
        this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Block, xCoord, integer2, zCoord, xCoord, integer2, zCoord);
        this.propagateSkylightOcclusion(integer1, integer3);
        if (integer4 != 0) {
            Block.blocksList[integer4].onBlockAdded(this.worldObj, xCoord, integer2, zCoord);
        }
        return this.isModified = true;
    }

    public int getBlockMetadata(final int integer1, final int integer2, final int integer3) {
        return this.data.getNibble(integer1, integer2, integer3);
    }

    public void setBlockMetadata(final int integer1, final int integer2, final int integer3, final int integer4) {
        this.isModified = true;
        this.data.setNibble(integer1, integer2, integer3, integer4);
    }

    public int getSavedLightValue(final EnumSkyBlock enumSkyBlock, final int integer2, final int integer3, final int integer4) {
        if (enumSkyBlock == EnumSkyBlock.Sky) {
            return this.skylightMap.getNibble(integer2, integer3, integer4);
        }
        if (enumSkyBlock == EnumSkyBlock.Block) {
            return this.blocklightMap.getNibble(integer2, integer3, integer4);
        }
        return 0;
    }

    public void setLightValue(final EnumSkyBlock enumSkyBlock, final int integer2, final int integer3, final int integer4, final int integer5) {
        this.isModified = true;
        if (enumSkyBlock == EnumSkyBlock.Sky) {
            this.skylightMap.setNibble(integer2, integer3, integer4, integer5);
        } else {
            if (enumSkyBlock != EnumSkyBlock.Block) {
                return;
            }
            this.blocklightMap.setNibble(integer2, integer3, integer4, integer5);
        }
    }

    public int getBlockLightValue(final int integer1, final int integer2, final int integer3, final int integer4) {
        int nibble = this.skylightMap.getNibble(integer1, integer2, integer3);
        if (nibble > 0) {
            Chunk.isLit = true;
        }
        nibble -= integer4;
        final int nibble2 = this.blocklightMap.getNibble(integer1, integer2, integer3);
        if (nibble2 > nibble) {
            nibble = nibble2;
        }
        return nibble;
    }

    public void addEntity(final Entity entity) {
        this.hasEntities = true;
        final int floor_double = MathHelper.floor_double(entity.posX / 16.0);
        final int floor_double2 = MathHelper.floor_double(entity.posZ / 16.0);
        if (floor_double != this.xPosition || floor_double2 != this.zPosition) {
            System.out.println(new StringBuilder().append("Wrong location! ").append(entity).toString());
        }
        int floor_double3 = MathHelper.floor_double(entity.posY / 16.0);
        if (floor_double3 < 0) {
            floor_double3 = 0;
        }
        if (floor_double3 >= this.entities.length) {
            floor_double3 = this.entities.length - 1;
        }
        this.entities[floor_double3].add(entity);
    }

    public void removeEntity(final Entity entity) {
        this.removeEntityAtIndex(entity, MathHelper.floor_double(entity.posY / 16.0));
    }

    public void removeEntityAtIndex(final Entity entity, int integer) {
        if (integer < 0) {
            integer = 0;
        }
        if (integer >= this.entities.length) {
            integer = this.entities.length - 1;
        }
        if (!this.entities[integer].contains(entity)) {
            System.out.println(new StringBuilder().append("There's no such entity to remove: ").append(entity).toString());
        }
        this.entities[integer].remove(entity);
    }

    public boolean canBlockSeeTheSky(final int integer1, final int integer2, final int integer3) {
        return integer2 >= (this.heightMap[integer3 << 4 | integer1] & 0xFF);
    }

    public TileEntity getChunkBlockTileEntity(final int xCoord, final int yCoord, final int zCoord) {
        final int n = xCoord + yCoord * 1024 + zCoord * 1024 * 1024;
        TileEntity tileEntity = (TileEntity) this.chunkTileEntityMap.get(n);
        if (tileEntity == null) {
            ((BlockContainer) Block.blocksList[this.getBlockID(xCoord, yCoord, zCoord)]).onBlockAdded(this.worldObj, this.xPosition * 16 + xCoord, yCoord, this.zPosition * 16 + zCoord);
            tileEntity = (TileEntity) this.chunkTileEntityMap.get(n);
        }
        return tileEntity;
    }

    public void addTileEntity(final TileEntity tileEntity) {
        this.setChunkBlockTileEntity(tileEntity.xCoord - this.xPosition * 16, tileEntity.yCoord, tileEntity.zCoord - this.zPosition * 16, tileEntity);
    }

    public void setChunkBlockTileEntity(final int xCoord, final int yCoord, final int zCoord, final TileEntity tileEntity) {
        final int n = xCoord + yCoord * 1024 + zCoord * 1024 * 1024;
        tileEntity.worldObj = this.worldObj;
        tileEntity.xCoord = this.xPosition * 16 + xCoord;
        tileEntity.yCoord = yCoord;
        tileEntity.zCoord = this.zPosition * 16 + zCoord;
        if (this.getBlockID(xCoord, yCoord, zCoord) == 0 || !(Block.blocksList[this.getBlockID(xCoord, yCoord, zCoord)] instanceof BlockContainer)) {
            System.out.println("Attempted to place a tile entity where there was no entity tile!");
            return;
        }
        if (this.isChunkLoaded) {
            if (this.chunkTileEntityMap.get(n) != null) {
                this.worldObj.loadedTileEntityList.remove(this.chunkTileEntityMap.get(n));
            }
            this.worldObj.loadedTileEntityList.add(tileEntity);
        }
        this.chunkTileEntityMap.put(n, tileEntity);
    }

    public void removeChunkBlockTileEntity(final int integer1, final int integer2, final int integer3) {
        final int n = integer1 + integer2 * 1024 + integer3 * 1024 * 1024;
        if (this.isChunkLoaded) {
            this.worldObj.loadedTileEntityList.remove(this.chunkTileEntityMap.remove(n));
        }
    }

    public void onChunkLoad() {
        this.isChunkLoaded = true;
        this.worldObj.loadedTileEntityList.addAll(this.chunkTileEntityMap.values());
        for (int i = 0; i < this.entities.length; ++i) {
            this.worldObj.addLoadedEntities(this.entities[i]);
        }
    }

    public void onChunkUnload() {
        this.isChunkLoaded = false;
        this.worldObj.loadedTileEntityList.removeAll(this.chunkTileEntityMap.values());
        for (int i = 0; i < this.entities.length; ++i) {
            this.worldObj.unloadEntities(this.entities[i]);
        }
    }

    public void setChunkModified() {
        this.isModified = true;
    }

    public void getEntitiesWithinAABBForEntity(final Entity entity, final AxisAlignedBB aabb, final List list) {
        int floor_double = MathHelper.floor_double((aabb.minY - 2.0) / 16.0);
        int floor_double2 = MathHelper.floor_double((aabb.maxY + 2.0) / 16.0);
        if (floor_double < 0) {
            floor_double = 0;
        }
        if (floor_double2 >= this.entities.length) {
            floor_double2 = this.entities.length - 1;
        }
        for (int i = floor_double; i <= floor_double2; ++i) {
            final List list2 = this.entities[i];
            for (int j = 0; j < list2.size(); ++j) {
                final Entity entity2 = (Entity) list2.get(j);
                if (entity2 != entity && entity2.boundingBox.intersectsWith(aabb)) {
                    list.add(entity2);
                }
            }
        }
    }

    public void getEntitiesOfTypeWithinAAAB(final Class class1, final AxisAlignedBB aabb, final List list) {
        int floor_double = MathHelper.floor_double((aabb.minY - 2.0) / 16.0);
        int floor_double2 = MathHelper.floor_double((aabb.maxY + 2.0) / 16.0);
        if (floor_double < 0) {
            floor_double = 0;
        }
        if (floor_double2 >= this.entities.length) {
            floor_double2 = this.entities.length - 1;
        }
        for (int i = floor_double; i <= floor_double2; ++i) {
            final List list2 = this.entities[i];
            for (int j = 0; j < list2.size(); ++j) {
                final Entity entity = (Entity) list2.get(j);
                if (class1.isAssignableFrom(entity.getClass()) && entity.boundingBox.intersectsWith(aabb)) {
                    list.add(entity);
                }
            }
        }
    }

    public boolean needsSaving(final boolean boolean1) {
        return !this.neverSave && ((this.hasEntities && this.worldObj.getWorldTime != this.lastSaveTime) || this.isModified);
    }
}
