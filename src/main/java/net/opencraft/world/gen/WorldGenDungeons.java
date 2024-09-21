
package net.opencraft.world.gen;

import java.util.Random;

import net.opencraft.blocks.Block;
import net.opencraft.blocks.material.Material;
import net.opencraft.item.Item;
import net.opencraft.item.ItemStack;
import net.opencraft.tileentity.TileEntityChest;
import net.opencraft.tileentity.TileEntityMobSpawner;
import net.opencraft.world.World;

public class WorldGenDungeons extends WorldGenerator {

    @Override
    public boolean generate(final World fe, final Random random, final int integer3, final int integer4, final int integer5) {
        final int n = 3;
        final int n2 = random.nextInt(2) + 2;
        final int n3 = random.nextInt(2) + 2;
        int n4 = 0;
        for (int i = integer3 - n2 - 1; i <= integer3 + n2 + 1; ++i) {
            for (int j = integer4 - 1; j <= integer4 + n + 1; ++j) {
                for (int k = integer5 - n3 - 1; k <= integer5 + n3 + 1; ++k) {
                    final Material blockMaterial = fe.getBlockMaterial(i, j, k);
                    if (j == integer4 - 1 && !blockMaterial.isSolid()) {
                        return false;
                    }
                    if (j == integer4 + n + 1 && !blockMaterial.isSolid()) {
                        return false;
                    }
                    if ((i == integer3 - n2 - 1 || i == integer3 + n2 + 1 || k == integer5 - n3 - 1 || k == integer5 + n3 + 1) && j == integer4 && fe.getBlockId(i, j, k) == 0 && fe.getBlockId(i, j + 1, k) == 0) {
                        ++n4;
                    }
                }
            }
        }
        if (n4 < 1 || n4 > 5) {
            return false;
        }
        for (int i = integer3 - n2 - 1; i <= integer3 + n2 + 1; ++i) {
            for (int j = integer4 + n; j >= integer4 - 1; --j) {
                for (int k = integer5 - n3 - 1; k <= integer5 + n3 + 1; ++k) {
                    if (i == integer3 - n2 - 1 || j == integer4 - 1 || k == integer5 - n3 - 1 || i == integer3 + n2 + 1 || j == integer4 + n + 1 || k == integer5 + n3 + 1) {
                        if (j >= 0 && !fe.getBlockMaterial(i, j - 1, k).isSolid()) {
                            fe.setBlockWithNotify(i, j, k, 0);
                        } else if (fe.getBlockMaterial(i, j, k).isSolid()) {
                            if (j == integer4 - 1 && random.nextInt(4) != 0) {
                                fe.setBlockWithNotify(i, j, k, Block.mossyCobblestone.blockID);
                            } else {
                                fe.setBlockWithNotify(i, j, k, Block.cobblestone.blockID);
                            }
                        }
                    } else {
                        fe.setBlockWithNotify(i, j, k, 0);
                    }
                }
            }
        }
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 3; ++j) {
                final int k = integer3 + random.nextInt(n2 * 2 + 1) - n2;
                final int zCoord = integer5 + random.nextInt(n3 * 2 + 1) - n3;
                if (fe.getBlockId(k, integer4, zCoord) == 0) {
                    int n5 = 0;
                    if (fe.getBlockMaterial(k - 1, integer4, zCoord).isSolid()) {
                        ++n5;
                    }
                    if (fe.getBlockMaterial(k + 1, integer4, zCoord).isSolid()) {
                        ++n5;
                    }
                    if (fe.getBlockMaterial(k, integer4, zCoord - 1).isSolid()) {
                        ++n5;
                    }
                    if (fe.getBlockMaterial(k, integer4, zCoord + 1).isSolid()) {
                        ++n5;
                    }
                    if (n5 == 1) {
                        fe.setBlockWithNotify(k, integer4, zCoord, Block.chest.blockID);
                        final TileEntityChest tileEntityChest = (TileEntityChest) fe.getBlockTileEntity(k, integer4, zCoord);
                        for (int l = 0; l < 8; ++l) {
                            final ItemStack pickCheckLootItem = this.pickCheckLootItem(random);
                            if (pickCheckLootItem != null) {
                                tileEntityChest.setInventorySlotContents(random.nextInt(tileEntityChest.getSizeInventory()), pickCheckLootItem);
                            }
                        }
                        break;
                    }
                }
            }
        }
        fe.setBlockWithNotify(integer3, integer4, integer5, Block.spawner.blockID);
        ((TileEntityMobSpawner) fe.getBlockTileEntity(integer3, integer4, integer5)).getMobID = this.pickMobSpawner(random);
        return true;
    }

    private ItemStack pickCheckLootItem(final Random random) {
        final int nextInt = random.nextInt(10);
        if (nextInt == 0) {
            return new ItemStack(Item.saddle);
        }
        if (nextInt == 1) {
            return new ItemStack(Item.ingotIron, random.nextInt(4) + 1);
        }
        if (nextInt == 2) {
            return new ItemStack(Item.bread);
        }
        if (nextInt == 3) {
            return new ItemStack(Item.wheat, random.nextInt(4) + 1);
        }
        if (nextInt == 4) {
            return new ItemStack(Item.gunpowder, random.nextInt(4) + 1);
        }
        if (nextInt == 5) {
            return new ItemStack(Item.silk, random.nextInt(4) + 1);
        }
        if (nextInt == 6) {
            return new ItemStack(Item.bucketEmpty);
        }
        if (nextInt == 7 && random.nextInt(100) == 0) {
            return new ItemStack(Item.appleGold);
        }
        return null;
    }

    private String pickMobSpawner(final Random random) {
        final int nextInt = random.nextInt(4);
        if (nextInt == 0) {
            return "Skeleton";
        }
        if (nextInt == 1) {
            return "Zombie";
        }
        if (nextInt == 2) {
            return "Zombie";
        }
        if (nextInt == 3) {
            return "Spider";
        }
        return "";
    }
}
