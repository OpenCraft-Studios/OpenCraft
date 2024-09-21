
package net.opencraft.block;

import java.util.Random;
import net.opencraft.block.material.Material;
import net.opencraft.client.input.MovingObjectPosition;
import net.opencraft.entity.EntityPlayer;
import net.opencraft.item.Item;
import net.opencraft.util.AxisAlignedBB;
import net.opencraft.util.Vec3D;
import net.opencraft.world.IBlockAccess;
import net.opencraft.world.World;

public class BlockDoor extends Block {

    protected BlockDoor(final int blockid) {
        super(blockid, Material.wood);
        this.blockIndexInTexture = 97;
        final float n = 0.5f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 1.0f, 0.5f + n);
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(final int textureIndexSlot, final int metadataValue) {
        if (textureIndexSlot == 0 || textureIndexSlot == 1) {
            return this.blockIndexInTexture;
        }
        final int state = this.getState(metadataValue);
        if ((state == 0 || state == 2) ^ textureIndexSlot <= 3) {
            return this.blockIndexInTexture;
        }
        int n = state / 2 + ((textureIndexSlot & 0x1) ^ state);
        n += (metadataValue & 0x4) / 4;
        int n2 = this.blockIndexInTexture - (metadataValue & 0x8) * 2;
        if ((n & 0x1) != 0x0) {
            n2 = -n2;
        }
        return n2;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 7;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World world, final int xCoord, final int yCoord, final int zCoord) {
        this.setBlockBoundsBasedOnState(world, xCoord, yCoord, zCoord);
        return super.getSelectedBoundingBoxFromPool(world, xCoord, yCoord, zCoord);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int xCoord, final int yCoord, final int zCoord) {
        this.setBlockBoundsBasedOnState(world, xCoord, yCoord, zCoord);
        return super.getCollisionBoundingBoxFromPool(world, xCoord, yCoord, zCoord);
    }

    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord) {
        this.setDoorRotation(this.getState(blockAccess.getBlockMetadata(xCoord, yCoord, zCoord)));
    }

    public void setDoorRotation(final int metadataValue) {
        final float n = 0.1875f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f);
        if (metadataValue == 0) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n);
        }
        if (metadataValue == 1) {
            this.setBlockBounds(1.0f - n, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        if (metadataValue == 2) {
            this.setBlockBounds(0.0f, 0.0f, 1.0f - n, 1.0f, 1.0f, 1.0f);
        }
        if (metadataValue == 3) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, n, 1.0f, 1.0f);
        }
    }

    @Override
    public void onBlockClicked(final World world, final int xCoord, final int yCoord, final int zCoord, final EntityPlayer entityPlayer) {
        this.blockActivated(world, xCoord, yCoord, zCoord, entityPlayer);
    }

    @Override
    public boolean blockActivated(final World world, final int xCoord, final int yCoord, final int zCoord, final EntityPlayer entityPlayer) {
        final int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
        if ((blockMetadata & 0x8) != 0x0) {
            if (world.getBlockId(xCoord, yCoord - 1, zCoord) == this.blockID) {
                this.blockActivated(world, xCoord, yCoord - 1, zCoord, entityPlayer);
            }
            return true;
        }
        if (world.getBlockId(xCoord, yCoord + 1, zCoord) == this.blockID) {
            world.setBlockMetadataWithNotify(xCoord, yCoord + 1, zCoord, (blockMetadata ^ 0x4) + 8);
        }
        world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, blockMetadata ^ 0x4);
        world.markBlocksDirty(xCoord, yCoord - 1, zCoord, xCoord, yCoord, zCoord);
        if (Math.random() < 0.5) {
            world.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "random.door_open", 1.0f, world.rand.nextFloat() * 0.1f + 0.9f);
        } else {
            world.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "random.door_close", 1.0f, world.rand.nextFloat() * 0.1f + 0.9f);
        }
        return true;
    }

    @Override
    public void onNeighborBlockChange(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        final int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
        if ((blockMetadata & 0x8) != 0x0) {
            if (world.getBlockId(xCoord, yCoord - 1, zCoord) != this.blockID) {
                world.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
            }
        } else {
            boolean b = false;
            if (world.getBlockId(xCoord, yCoord + 1, zCoord) != this.blockID) {
                world.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
                b = true;
            }
            if (!world.isBlockNormalCube(xCoord, yCoord - 1, zCoord)) {
                world.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
                b = true;
                if (world.getBlockId(xCoord, yCoord + 1, zCoord) == this.blockID) {
                    world.setBlockWithNotify(xCoord, yCoord + 1, zCoord, 0);
                }
            }
            if (b) {
                this.dropBlockAsItem(world, xCoord, yCoord, zCoord, blockMetadata);
            }
        }
    }

    @Override
    public int idDropped(final int blockid, final Random random) {
        if ((blockid & 0x8) != 0x0) {
            return 0;
        }
        return Item.door.shiftedIndex;
    }

    @Override
    public MovingObjectPosition collisionRayTrace(final World world, final int xCoord, final int yCoord, final int zCoord, final Vec3D var1, final Vec3D var2) {
        this.setBlockBoundsBasedOnState(world, xCoord, yCoord, zCoord);
        return super.collisionRayTrace(world, xCoord, yCoord, zCoord, var1, var2);
    }

    public int getState(final int state) {
        if ((state & 0x4) == 0x0) {
            return state - 1 & 0x3;
        }
        return state & 0x3;
    }

    @Override
    public boolean canPlaceBlockAt(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return yCoord < 127 && world.isBlockNormalCube(xCoord, yCoord - 1, zCoord) && super.canPlaceBlockAt(world, xCoord, yCoord, zCoord) && super.canPlaceBlockAt(world, xCoord, yCoord + 1, zCoord);
    }
}
