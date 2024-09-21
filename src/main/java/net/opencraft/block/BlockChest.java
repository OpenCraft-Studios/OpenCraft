
package net.opencraft.block;

import java.util.Random;
import net.opencraft.block.material.Material;
import net.opencraft.entity.EntityItem;
import net.opencraft.entity.EntityPlayer;
import net.opencraft.inventory.IInventory;
import net.opencraft.inventory.InventoryLargeChest;
import net.opencraft.item.ItemStack;
import net.opencraft.tileentity.TileEntity;
import net.opencraft.tileentity.TileEntityChest;
import net.opencraft.world.IBlockAccess;
import net.opencraft.world.World;

public class BlockChest extends BlockContainer {

    private final Random random;

    protected BlockChest(final int blockid) {
        super(blockid, Material.wood);
        this.random = new Random();
        this.blockIndexInTexture = 26;
    }

    @Override
    public int getBlockTextureGeneric(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord, final int metadataValue) {
        if (metadataValue == 1) {
            return this.blockIndexInTexture - 1;
        }
        if (metadataValue == 0) {
            return this.blockIndexInTexture - 1;
        }
        final int blockId = blockAccess.getBlockId(xCoord, yCoord, zCoord - 1);
        final int blockId2 = blockAccess.getBlockId(xCoord, yCoord, zCoord + 1);
        final int blockId3 = blockAccess.getBlockId(xCoord - 1, yCoord, zCoord);
        final int blockId4 = blockAccess.getBlockId(xCoord + 1, yCoord, zCoord);
        if (blockId == this.blockID || blockId2 == this.blockID) {
            if (metadataValue == 2 || metadataValue == 3) {
                return this.blockIndexInTexture;
            }
            int n = 0;
            if (blockId == this.blockID) {
                n = -1;
            }
            final int n2 = blockAccess.getBlockId(xCoord - 1, yCoord, (blockId == this.blockID) ? (zCoord - 1) : (zCoord + 1));
            final int n3 = blockAccess.getBlockId(xCoord + 1, yCoord, (blockId == this.blockID) ? (zCoord - 1) : (zCoord + 1));
            if (metadataValue == 4) {
                n = -1 - n;
            }
            int n4 = 5;
            if ((Block.opaqueCubeLookup[blockId3] || Block.opaqueCubeLookup[n2]) && !Block.opaqueCubeLookup[blockId4] && !Block.opaqueCubeLookup[n3]) {
                n4 = 5;
            }
            if ((Block.opaqueCubeLookup[blockId4] || Block.opaqueCubeLookup[n3]) && !Block.opaqueCubeLookup[blockId3] && !Block.opaqueCubeLookup[n2]) {
                n4 = 4;
            }
            return ((metadataValue == n4) ? (this.blockIndexInTexture + 16) : (this.blockIndexInTexture + 32)) + n;
        } else {
            if (blockId3 != this.blockID && blockId4 != this.blockID) {
                int n = 3;
                if (Block.opaqueCubeLookup[blockId] && !Block.opaqueCubeLookup[blockId2]) {
                    n = 3;
                }
                if (Block.opaqueCubeLookup[blockId2] && !Block.opaqueCubeLookup[blockId]) {
                    n = 2;
                }
                if (Block.opaqueCubeLookup[blockId3] && !Block.opaqueCubeLookup[blockId4]) {
                    n = 5;
                }
                if (Block.opaqueCubeLookup[blockId4] && !Block.opaqueCubeLookup[blockId3]) {
                    n = 4;
                }
                return (metadataValue == n) ? (this.blockIndexInTexture + 1) : this.blockIndexInTexture;
            }
            if (metadataValue == 4 || metadataValue == 5) {
                return this.blockIndexInTexture;
            }
            int n = 0;
            if (blockId3 == this.blockID) {
                n = -1;
            }
            final int n2 = blockAccess.getBlockId((blockId3 == this.blockID) ? (xCoord - 1) : (xCoord + 1), yCoord, zCoord - 1);
            final int n3 = blockAccess.getBlockId((blockId3 == this.blockID) ? (xCoord - 1) : (xCoord + 1), yCoord, zCoord + 1);
            if (metadataValue == 3) {
                n = -1 - n;
            }
            int n4 = 3;
            if ((Block.opaqueCubeLookup[blockId] || Block.opaqueCubeLookup[n2]) && !Block.opaqueCubeLookup[blockId2] && !Block.opaqueCubeLookup[n3]) {
                n4 = 3;
            }
            if ((Block.opaqueCubeLookup[blockId2] || Block.opaqueCubeLookup[n3]) && !Block.opaqueCubeLookup[blockId] && !Block.opaqueCubeLookup[n2]) {
                n4 = 2;
            }
            return ((metadataValue == n4) ? (this.blockIndexInTexture + 16) : (this.blockIndexInTexture + 32)) + n;
        }
    }

    @Override
    public int getBlockTextureFromSide(final int textureIndexSlot) {
        if (textureIndexSlot == 1) {
            return this.blockIndexInTexture - 1;
        }
        if (textureIndexSlot == 0) {
            return this.blockIndexInTexture - 1;
        }
        if (textureIndexSlot == 3) {
            return this.blockIndexInTexture + 1;
        }
        return this.blockIndexInTexture;
    }

    @Override
    public boolean canPlaceBlockAt(final World world, final int xCoord, final int yCoord, final int zCoord) {
        int n = 0;
        if (world.getBlockId(xCoord - 1, yCoord, zCoord) == this.blockID) {
            ++n;
        }
        if (world.getBlockId(xCoord + 1, yCoord, zCoord) == this.blockID) {
            ++n;
        }
        if (world.getBlockId(xCoord, yCoord, zCoord - 1) == this.blockID) {
            ++n;
        }
        if (world.getBlockId(xCoord, yCoord, zCoord + 1) == this.blockID) {
            ++n;
        }
        return n <= 1 && !this.isThereANeighborChest(world, xCoord - 1, yCoord, zCoord) && !this.isThereANeighborChest(world, xCoord + 1, yCoord, zCoord) && !this.isThereANeighborChest(world, xCoord, yCoord, zCoord - 1) && !this.isThereANeighborChest(world, xCoord, yCoord, zCoord + 1);
    }

    private boolean isThereANeighborChest(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return world.getBlockId(xCoord, yCoord, zCoord) == this.blockID && (world.getBlockId(xCoord - 1, yCoord, zCoord) == this.blockID || world.getBlockId(xCoord + 1, yCoord, zCoord) == this.blockID || world.getBlockId(xCoord, yCoord, zCoord - 1) == this.blockID || world.getBlockId(xCoord, yCoord, zCoord + 1) == this.blockID);
    }

    @Override
    public void onBlockRemoval(final World world, final int xCoord, final int yCoord, final int zCoord) {
        final TileEntityChest tileEntityChest = (TileEntityChest) world.getBlockTileEntity(xCoord, yCoord, zCoord);
        for (int i = 0; i < tileEntityChest.getSizeInventory(); ++i) {
            final ItemStack stackInSlot = tileEntityChest.getStackInSlot(i);
            if (stackInSlot != null) {
                final float n = this.random.nextFloat() * 0.8f + 0.1f;
                final float n2 = this.random.nextFloat() * 0.8f + 0.1f;
                final float n3 = this.random.nextFloat() * 0.8f + 0.1f;
                while (stackInSlot.stackSize > 0) {
                    int stackSize = this.random.nextInt(21) + 10;
                    if (stackSize > stackInSlot.stackSize) {
                        stackSize = stackInSlot.stackSize;
                    }
                    final ItemStack itemStack = stackInSlot;
                    itemStack.stackSize -= stackSize;
                    final EntityItem entity = new EntityItem(world, xCoord + n, yCoord + n2, zCoord + n3, new ItemStack(stackInSlot.itemID, stackSize, stackInSlot.itemDamage));
                    final float n4 = 0.05f;
                    entity.motionX = (float) this.random.nextGaussian() * n4;
                    entity.motionY = (float) this.random.nextGaussian() * n4 + 0.2f;
                    entity.motionZ = (float) this.random.nextGaussian() * n4;
                    world.entityJoinedWorld(entity);
                }
            }
        }
        super.onBlockRemoval(world, xCoord, yCoord, zCoord);
    }

    @Override
    public boolean blockActivated(final World world, final int xCoord, final int yCoord, final int zCoord, final EntityPlayer entityPlayer) {
        IInventory kd = (IInventory) world.getBlockTileEntity(xCoord, yCoord, zCoord);
        if (world.isBlockNormalCube(xCoord, yCoord + 1, zCoord)) {
            return true;
        }
        if (world.getBlockId(xCoord - 1, yCoord, zCoord) == this.blockID && world.isBlockNormalCube(xCoord - 1, yCoord + 1, zCoord)) {
            return true;
        }
        if (world.getBlockId(xCoord + 1, yCoord, zCoord) == this.blockID && world.isBlockNormalCube(xCoord + 1, yCoord + 1, zCoord)) {
            return true;
        }
        if (world.getBlockId(xCoord, yCoord, zCoord - 1) == this.blockID && world.isBlockNormalCube(xCoord, yCoord + 1, zCoord - 1)) {
            return true;
        }
        if (world.getBlockId(xCoord, yCoord, zCoord + 1) == this.blockID && world.isBlockNormalCube(xCoord, yCoord + 1, zCoord + 1)) {
            return true;
        }
        if (world.getBlockId(xCoord - 1, yCoord, zCoord) == this.blockID) {
            kd = new InventoryLargeChest("Large chest", (IInventory) world.getBlockTileEntity(xCoord - 1, yCoord, zCoord), kd);
        }
        if (world.getBlockId(xCoord + 1, yCoord, zCoord) == this.blockID) {
            kd = new InventoryLargeChest("Large chest", kd, (IInventory) world.getBlockTileEntity(xCoord + 1, yCoord, zCoord));
        }
        if (world.getBlockId(xCoord, yCoord, zCoord - 1) == this.blockID) {
            kd = new InventoryLargeChest("Large chest", (IInventory) world.getBlockTileEntity(xCoord, yCoord, zCoord - 1), kd);
        }
        if (world.getBlockId(xCoord, yCoord, zCoord + 1) == this.blockID) {
            kd = new InventoryLargeChest("Large chest", kd, (IInventory) world.getBlockTileEntity(xCoord, yCoord, zCoord + 1));
        }
        entityPlayer.displayGUIChest(kd);
        return true;
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityChest();
    }
}
