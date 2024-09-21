
package net.opencraft.world.chunk;

import net.opencraft.blocks.Block;
import net.opencraft.blocks.material.Material;
import net.opencraft.tileentity.TileEntity;
import net.opencraft.world.IBlockAccess;
import net.opencraft.world.World;

public class ChunkCache implements IBlockAccess {

    private int chunkX;
    private int chunkZ;
    private Chunk[][] chunkArray;
    private World worldObj;

    public ChunkCache(final World world, final int integer2, final int integer3, final int integer4, final int integer5, final int integer6, final int integer7) {
        this.worldObj = world;
        this.chunkX = integer2 >> 4;
        this.chunkZ = integer4 >> 4;
        final int n = integer5 >> 4;
        final int n2 = integer7 >> 4;
        this.chunkArray = new Chunk[n - this.chunkX + 1][n2 - this.chunkZ + 1];
        for (int i = this.chunkX; i <= n; ++i) {
            for (int j = this.chunkZ; j <= n2; ++j) {
                this.chunkArray[i - this.chunkX][j - this.chunkZ] = world.getChunkFromChunkCoords(i, j);
            }
        }
    }

    public int getBlockId(final int xCoord, final int yCoord, final int zCoord) {
        if (yCoord < 0) {
            return 0;
        }
        if (yCoord >= 128) {
            return 0;
        }
        return this.chunkArray[(xCoord >> 4) - this.chunkX][(zCoord >> 4) - this.chunkZ].getBlockID(xCoord & 0xF, yCoord, zCoord & 0xF);
    }

    public TileEntity getBlockTileEntity(final int xCoord, final int yCoord, final int zCoord) {
        return this.chunkArray[(xCoord >> 4) - this.chunkX][(zCoord >> 4) - this.chunkZ].getChunkBlockTileEntity(xCoord & 0xF, yCoord, zCoord & 0xF);
    }

    public float getLightBrightness(final int nya1, final int nya2, final int nya3) {
        return World.lightBrightnessTable[this.getLightValue(nya1, nya2, nya3)];
    }

    public int getLightValue(final int xCoord, final int yCoord, final int zCoord) {
        return this.getLightValueExt(xCoord, yCoord, zCoord, true);
    }

    public int getLightValueExt(final int xCoord, final int yCoord, final int zCoord, final boolean boolean4) {
        if (xCoord < -32000000 || zCoord < -32000000 || xCoord >= 32000000 || zCoord > 32000000) {
            return 15;
        }
        if (boolean4) {
            final int blockId = this.getBlockId(xCoord, yCoord, zCoord);
            if (blockId == Block.slabSingle.blockID || blockId == Block.tilledField.blockID) {
                int lightValueExt = this.getLightValueExt(xCoord, yCoord + 1, zCoord, false);
                final int lightValueExt2 = this.getLightValueExt(xCoord + 1, yCoord, zCoord, false);
                final int lightValueExt3 = this.getLightValueExt(xCoord - 1, yCoord, zCoord, false);
                final int lightValueExt4 = this.getLightValueExt(xCoord, yCoord, zCoord + 1, false);
                final int lightValueExt5 = this.getLightValueExt(xCoord, yCoord, zCoord - 1, false);
                if (lightValueExt2 > lightValueExt) {
                    lightValueExt = lightValueExt2;
                }
                if (lightValueExt3 > lightValueExt) {
                    lightValueExt = lightValueExt3;
                }
                if (lightValueExt4 > lightValueExt) {
                    lightValueExt = lightValueExt4;
                }
                if (lightValueExt5 > lightValueExt) {
                    lightValueExt = lightValueExt5;
                }
                return lightValueExt;
            }
        }
        if (yCoord < 0) {
            return 0;
        }
        if (yCoord >= 128) {
            int blockId = 15 - this.worldObj.skylightSubtracted;
            if (blockId < 0) {
                blockId = 0;
            }
            return blockId;
        }
        int blockId = (xCoord >> 4) - this.chunkX;
        int lightValueExt = (zCoord >> 4) - this.chunkZ;
        return this.chunkArray[blockId][lightValueExt].getBlockLightValue(xCoord & 0xF, yCoord, zCoord & 0xF, this.worldObj.skylightSubtracted);
    }

    public int getBlockMetadata(final int xCoord, final int yCoord, final int zCoord) {
        if (yCoord < 0) {
            return 0;
        }
        if (yCoord >= 128) {
            return 0;
        }
        return this.chunkArray[(xCoord >> 4) - this.chunkX][(zCoord >> 4) - this.chunkZ].getBlockMetadata(xCoord & 0xF, yCoord, zCoord & 0xF);
    }

    public Material getBlockMaterial(final int nya1, final int nya2, final int nya3) {
        final int blockId = this.getBlockId(nya1, nya2, nya3);
        if (blockId == 0) {
            return Material.AIR;
        }
        return Block.blocksList[blockId].blockMaterial;
    }

    public boolean isBlockNormalCube(final int xCoord, final int yCoord, final int zCoord) {
        final Block block = Block.blocksList[this.getBlockId(xCoord, yCoord, zCoord)];
        return block != null && block.isOpaqueCube();
    }
}
