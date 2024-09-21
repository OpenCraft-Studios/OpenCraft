
package net.opencraft.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.opencraft.block.material.Material;
import net.opencraft.client.input.MovingObjectPosition;
import net.opencraft.physics.AABB;
import net.opencraft.util.Vec3;
import net.opencraft.world.IBlockAccess;
import net.opencraft.world.World;
import net.opencraft.world.chunk.ChunkPosition;

public class RailBlock extends Block {

    protected RailBlock(final int blockid, final int blockIndexIntexture) {
        super(blockid, blockIndexIntexture, Material.REDSTONE);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }

    @Override
    public AABB getCollisionBoundingBoxFromPool(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public MovingObjectPosition collisionRayTrace(final World world, final int xCoord, final int yCoord, final int zCoord, final Vec3 var1, final Vec3 var2) {
        this.setBlockBoundsBasedOnState(world, xCoord, yCoord, zCoord);
        return super.collisionRayTrace(world, xCoord, yCoord, zCoord, var1, var2);
    }

    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord) {
        final int blockMetadata = blockAccess.getBlockMetadata(xCoord, yCoord, zCoord);
        if (blockMetadata >= 2 && blockMetadata <= 5) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.625f, 1.0f);
        } else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        }
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(final int textureIndexSlot, final int metadataValue) {
        if (metadataValue >= 6) {
            return this.blockIndexInTexture - 16;
        }
        return this.blockIndexInTexture;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 9;
    }

    @Override
    public int quantityDropped(final Random random) {
        return 1;
    }

    @Override
    public boolean canPlaceBlockAt(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return world.isBlockNormalCube(xCoord, yCoord - 1, zCoord);
    }

    @Override
    public void onBlockAdded(final World world, final int xCoord, final int yCoord, final int zCoord) {
        world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 15);
        this.refreshTrackShape(world, xCoord, yCoord, zCoord);
    }

    @Override
    public void onNeighborBlockChange(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        final int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
        boolean b = false;
        if (!world.isBlockNormalCube(xCoord, yCoord - 1, zCoord)) {
            b = true;
        }
        if (blockMetadata == 2 && !world.isBlockNormalCube(xCoord + 1, yCoord, zCoord)) {
            b = true;
        }
        if (blockMetadata == 3 && !world.isBlockNormalCube(xCoord - 1, yCoord, zCoord)) {
            b = true;
        }
        if (blockMetadata == 4 && !world.isBlockNormalCube(xCoord, yCoord, zCoord - 1)) {
            b = true;
        }
        if (blockMetadata == 5 && !world.isBlockNormalCube(xCoord, yCoord, zCoord + 1)) {
            b = true;
        }
        if (b) {
            this.dropBlockAsItem(world, xCoord, yCoord, zCoord, world.getBlockMetadata(xCoord, yCoord, zCoord));
            world.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
        }
    }

    private void refreshTrackShape(final World world, final int xCoord, final int yCoord, final int zCoord) {
        new RailLogic(this, world, xCoord, yCoord, zCoord).refreshTrackShape();
    }

    public static class RailLogic {

    	private final List<ChunkPosition> connectedTracks = new ArrayList<>();

    	private final World world;
        private final int trackX;
        private final int trackY;
        private final int trackZ;
        private int metadataValues;
        final /* synthetic */ RailBlock rail;

        public RailLogic(final RailBlock bn, final World fe, final int integer3, final int integer4, final int integer5) {
            this.rail = bn;
            this.world = fe;
            this.trackX = integer3;
            this.trackY = integer4;
            this.trackZ = integer5;
            this.metadataValues = fe.getBlockMetadata(integer3, integer4, integer5);
            this.setConnections();
        }

        private void setConnections() {
            this.connectedTracks.clear();
            if (this.metadataValues == 0) {
                this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ - 1));
                this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ + 1));
            } else if (this.metadataValues == 1) {
                this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY, this.trackZ));
                this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY, this.trackZ));
            } else if (this.metadataValues == 2) {
                this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY, this.trackZ));
                this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY + 1, this.trackZ));
            } else if (this.metadataValues == 3) {
                this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY + 1, this.trackZ));
                this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY, this.trackZ));
            } else if (this.metadataValues == 4) {
                this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY + 1, this.trackZ - 1));
                this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ + 1));
            } else if (this.metadataValues == 5) {
                this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ - 1));
                this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY + 1, this.trackZ + 1));
            } else if (this.metadataValues == 6) {
                this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY, this.trackZ));
                this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ + 1));
            } else if (this.metadataValues == 7) {
                this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY, this.trackZ));
                this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ + 1));
            } else if (this.metadataValues == 8) {
                this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY, this.trackZ));
                this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ - 1));
            } else if (this.metadataValues == 9) {
                this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY, this.trackZ));
                this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ - 1));
            }
        }

        private void func_785_b() {
            for (int i = 0; i < this.connectedTracks.size(); ++i) {
                final RailLogic minecartTrackLogic = this.getMinecartTrackLogic((ChunkPosition) this.connectedTracks.get(i));
                if (minecartTrackLogic == null || !minecartTrackLogic.isConnectedTo(this)) {
                    this.connectedTracks.remove(i--);
                } else {
                    this.connectedTracks.set(i, new ChunkPosition(minecartTrackLogic.trackX, minecartTrackLogic.trackY, minecartTrackLogic.trackZ));
                }
            }
        }

        private RailLogic getMinecartTrackLogic(final ChunkPosition ia) {
            if (this.world.getBlockId(ia.x, ia.y, ia.z) == this.rail.blockID) {
                return new RailLogic(this.rail, this.world, ia.x, ia.y, ia.z);
            }
            if (this.world.getBlockId(ia.x, ia.y + 1, ia.z) == this.rail.blockID) {
                return new RailLogic(this.rail, this.world, ia.x, ia.y + 1, ia.z);
            }
            if (this.world.getBlockId(ia.x, ia.y - 1, ia.z) == this.rail.blockID) {
                return new RailLogic(this.rail, this.world, ia.x, ia.y - 1, ia.z);
            }
            return null;
        }

        private boolean isConnectedTo(final RailLogic hk) {
            for (int i = 0; i < this.connectedTracks.size(); ++i) {
                final ChunkPosition chunkPosition = (ChunkPosition) this.connectedTracks.get(i);
                if (chunkPosition.x == hk.trackX && chunkPosition.z == hk.trackZ) {
                    return true;
                }
            }
            return false;
        }

        private boolean isInTrack(final int integer1, final int integer2, final int integer3) {
            for (int i = 0; i < this.connectedTracks.size(); ++i) {
                final ChunkPosition chunkPosition = (ChunkPosition) this.connectedTracks.get(i);
                if (chunkPosition.x == integer1 && chunkPosition.z == integer3) {
                    return true;
                }
            }
            return false;
        }

        private boolean handleKeyPress(final RailLogic hk) {
            if (this.isConnectedTo(hk)) {
                return true;
            }
            if (this.connectedTracks.size() == 2) {
                return false;
            }
            if (this.connectedTracks.isEmpty()) {
                return true;
            }
            final ChunkPosition chunkPosition = (ChunkPosition) this.connectedTracks.get(0);
            return hk.trackY != this.trackY || chunkPosition.y != this.trackY || true;
        }

        private void func_788_d(final RailLogic hk) {
            this.connectedTracks.add(new ChunkPosition(hk.trackX, hk.trackY, hk.trackZ));
            final boolean inTrack = this.isInTrack(this.trackX, this.trackY, this.trackZ - 1);
            final boolean inTrack2 = this.isInTrack(this.trackX, this.trackY, this.trackZ + 1);
            final boolean inTrack3 = this.isInTrack(this.trackX - 1, this.trackY, this.trackZ);
            final boolean inTrack4 = this.isInTrack(this.trackX + 1, this.trackY, this.trackZ);
            int metadataValue = -1;
            if (inTrack || inTrack2) {
                metadataValue = 0;
            }
            if (inTrack3 || inTrack4) {
                metadataValue = 1;
            }
            if (inTrack2 && inTrack4 && !inTrack && !inTrack3) {
                metadataValue = 6;
            }
            if (inTrack2 && inTrack3 && !inTrack && !inTrack4) {
                metadataValue = 7;
            }
            if (inTrack && inTrack3 && !inTrack2 && !inTrack4) {
                metadataValue = 8;
            }
            if (inTrack && inTrack4 && !inTrack2 && !inTrack3) {
                metadataValue = 9;
            }
            if (metadataValue == 0) {
                if (this.world.getBlockId(this.trackX, this.trackY + 1, this.trackZ - 1) == this.rail.blockID) {
                    metadataValue = 4;
                }
                if (this.world.getBlockId(this.trackX, this.trackY + 1, this.trackZ + 1) == this.rail.blockID) {
                    metadataValue = 5;
                }
            }
            if (metadataValue == 1) {
                if (this.world.getBlockId(this.trackX + 1, this.trackY + 1, this.trackZ) == this.rail.blockID) {
                    metadataValue = 2;
                }
                if (this.world.getBlockId(this.trackX - 1, this.trackY + 1, this.trackZ) == this.rail.blockID) {
                    metadataValue = 3;
                }
            }
            if (metadataValue < 0) {
                metadataValue = 0;
            }
            this.world.setBlockMetadataWithNotify(this.trackX, this.trackY, this.trackZ, metadataValue);
        }

        private boolean func_786_c(final int integer1, final int integer2, final int integer3) {
            final RailLogic minecartTrackLogic = this.getMinecartTrackLogic(new ChunkPosition(integer1, integer2, integer3));
            if (minecartTrackLogic == null) {
                return false;
            }
            minecartTrackLogic.func_785_b();
            return minecartTrackLogic.handleKeyPress(this);
        }

        public void refreshTrackShape() {
            final boolean func_786_c = this.func_786_c(this.trackX, this.trackY, this.trackZ - 1);
            final boolean func_786_c2 = this.func_786_c(this.trackX, this.trackY, this.trackZ + 1);
            final boolean func_786_c3 = this.func_786_c(this.trackX - 1, this.trackY, this.trackZ);
            final boolean func_786_c4 = this.func_786_c(this.trackX + 1, this.trackY, this.trackZ);
            int n = -1;
            if (func_786_c || func_786_c2) {
                n = 0;
            }
            if (func_786_c3 || func_786_c4) {
                n = 1;
            }
            if (func_786_c2 && func_786_c4 && !func_786_c && !func_786_c3) {
                n = 6;
            }
            if (func_786_c2 && func_786_c3 && !func_786_c && !func_786_c4) {
                n = 7;
            }
            if (func_786_c && func_786_c3 && !func_786_c2 && !func_786_c4) {
                n = 8;
            }
            if (func_786_c && func_786_c4 && !func_786_c2 && !func_786_c3) {
                n = 9;
            }
            if (n == 0) {
                if (this.world.getBlockId(this.trackX, this.trackY + 1, this.trackZ - 1) == this.rail.blockID) {
                    n = 4;
                }
                if (this.world.getBlockId(this.trackX, this.trackY + 1, this.trackZ + 1) == this.rail.blockID) {
                    n = 5;
                }
            }
            if (n == 1) {
                if (this.world.getBlockId(this.trackX + 1, this.trackY + 1, this.trackZ) == this.rail.blockID) {
                    n = 2;
                }
                if (this.world.getBlockId(this.trackX - 1, this.trackY + 1, this.trackZ) == this.rail.blockID) {
                    n = 3;
                }
            }
            if (n < 0) {
                n = 0;
            }
            this.metadataValues = n;
            this.setConnections();
            this.world.setBlockMetadataWithNotify(this.trackX, this.trackY, this.trackZ, n);
            for (int i = 0; i < this.connectedTracks.size(); ++i) {
                final RailLogic minecartTrackLogic = this.getMinecartTrackLogic((ChunkPosition) this.connectedTracks.get(i));
                if (minecartTrackLogic != null) {
                    minecartTrackLogic.func_785_b();
                    if (minecartTrackLogic.handleKeyPress(this)) {
                        minecartTrackLogic.func_788_d(this);
                    }
                }
            }
        }
    }
}
