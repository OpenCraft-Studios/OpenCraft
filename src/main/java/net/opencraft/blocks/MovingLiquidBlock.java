
package net.opencraft.blocks;

import java.util.Random;

import net.opencraft.blocks.material.Material;
import net.opencraft.world.World;

public class MovingLiquidBlock extends LiquidBlock {

    int numAdjacentSources;
    boolean[] isOptimalFlowDirection;
    int[] flowCost;

    protected MovingLiquidBlock(final int blockid, final Material material) {
        super(blockid, material);
        this.numAdjacentSources = 0;
        this.isOptimalFlowDirection = new boolean[4];
        this.flowCost = new int[4];
    }

    private void updateFlow(final World world, final int xCoord, final int yCoord, final int zCoord) {
        world.setBlockAndMetadata(xCoord, yCoord, zCoord, this.blockID + 1, world.getBlockMetadata(xCoord, yCoord, zCoord));
        world.markBlocksDirty(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    }

    @Override
    public void updateTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
        int flowDecay = this.getFlowDecay(world, xCoord, yCoord, zCoord);
        boolean b = true;
        if (flowDecay > 0) {
            int n = -100;
            this.numAdjacentSources = 0;
            n = this.getSmallestFlowDecay(world, xCoord - 1, yCoord, zCoord, n);
            n = this.getSmallestFlowDecay(world, xCoord + 1, yCoord, zCoord, n);
            n = this.getSmallestFlowDecay(world, xCoord, yCoord, zCoord - 1, n);
            n = this.getSmallestFlowDecay(world, xCoord, yCoord, zCoord + 1, n);
            int n2 = n + this.unsure;
            if (n2 >= 8 || n < 0) {
                n2 = -1;
            }
            if (this.getFlowDecay(world, xCoord, yCoord + 1, zCoord) >= 0) {
                final int flowDecay2 = this.getFlowDecay(world, xCoord, yCoord + 1, zCoord);
                if (flowDecay2 >= 8) {
                    n2 = flowDecay2;
                } else {
                    n2 = flowDecay2 + 8;
                }
            }
            if (this.numAdjacentSources >= 2 && this.blockMaterial == Material.WATER) {
                n2 = 0;
            }
            if (this.blockMaterial == Material.LAVA && flowDecay < 8 && n2 < 8 && n2 > flowDecay && random.nextInt(4) != 0) {
                n2 = flowDecay;
                b = false;
            }
            if (n2 != flowDecay) {
                flowDecay = n2;
                if (flowDecay < 0) {
                    world.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
                } else {
                    world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, flowDecay);
                    world.scheduleBlockUpdate(xCoord, yCoord, zCoord, this.blockID);
                    world.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, this.blockID);
                }
            } else if (b) {
                this.updateFlow(world, xCoord, yCoord, zCoord);
            }
        } else {
            this.updateFlow(world, xCoord, yCoord, zCoord);
        }
        if (this.liquidCanDisplaceBlock(world, xCoord, yCoord - 1, zCoord)) {
            if (flowDecay >= 8) {
                world.setBlockAndMetadataWithNotify(xCoord, yCoord - 1, zCoord, this.blockID, flowDecay);
            } else {
                world.setBlockAndMetadataWithNotify(xCoord, yCoord - 1, zCoord, this.blockID, flowDecay + 8);
            }
        } else if (flowDecay >= 0 && (flowDecay == 0 || this.blockBlocksFlow(world, xCoord, yCoord - 1, zCoord))) {
            final boolean[] optimalFlowDirections = this.getOptimalFlowDirections(world, xCoord, yCoord, zCoord);
            int n2 = flowDecay + this.unsure;
            if (flowDecay >= 8) {
                n2 = 1;
            }
            if (n2 >= 8) {
                return;
            }
            if (optimalFlowDirections[0]) {
                this.flowIntoBlock(world, xCoord - 1, yCoord, zCoord, n2);
            }
            if (optimalFlowDirections[1]) {
                this.flowIntoBlock(world, xCoord + 1, yCoord, zCoord, n2);
            }
            if (optimalFlowDirections[2]) {
                this.flowIntoBlock(world, xCoord, yCoord, zCoord - 1, n2);
            }
            if (optimalFlowDirections[3]) {
                this.flowIntoBlock(world, xCoord, yCoord, zCoord + 1, n2);
            }
        }
    }

    private void flowIntoBlock(final World world, final int xCoord, final int yCoord, final int zCoord, final int metadataValue) {
        if (this.liquidCanDisplaceBlock(world, xCoord, yCoord, zCoord)) {
            final int blockId = world.getBlockId(xCoord, yCoord, zCoord);
            if (blockId > 0) {
                if (this.blockMaterial == Material.LAVA) {
                    this.triggerLavaMixEffects(world, xCoord, yCoord, zCoord);
                } else {
                    Block.blocksList[blockId].dropBlockAsItem(world, xCoord, yCoord, zCoord, world.getBlockMetadata(xCoord, yCoord, zCoord));
                }
            }
            world.setBlockAndMetadataWithNotify(xCoord, yCoord, zCoord, this.blockID, metadataValue);
        }
    }

    private int calculateFlowCost(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4, final int nya5) {
        int n = 1000;
        for (int i = 0; i < 4; ++i) {
            if (i != 0 || nya5 != 1) {
                if (i != 1 || nya5 != 0) {
                    if (i != 2 || nya5 != 3) {
                        if (i != 3 || nya5 != 2) {
                            int xCoord2 = xCoord;
                            int zCoord2 = zCoord;
                            if (i == 0) {
                                --xCoord2;
                            }
                            if (i == 1) {
                                ++xCoord2;
                            }
                            if (i == 2) {
                                --zCoord2;
                            }
                            if (i == 3) {
                                ++zCoord2;
                            }
                            if (!this.blockBlocksFlow(world, xCoord2, yCoord, zCoord2)) {
                                if (world.getBlockMaterial(xCoord2, yCoord, zCoord2) != this.blockMaterial || world.getBlockMetadata(xCoord2, yCoord, zCoord2) != 0) {
                                    if (!this.blockBlocksFlow(world, xCoord2, yCoord - 1, zCoord2)) {
                                        return nya4;
                                    }
                                    if (nya4 < 4) {
                                        final int calculateFlowCost = this.calculateFlowCost(world, xCoord2, yCoord, zCoord2, nya4 + 1, i);
                                        if (calculateFlowCost < n) {
                                            n = calculateFlowCost;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return n;
    }

    private boolean[] getOptimalFlowDirections(final World world, final int xCoord, final int yCoord, final int zCoord) {
        for (int i = 0; i < 4; ++i) {
            this.flowCost[i] = 1000;
            int j = xCoord;
            int zCoord2 = zCoord;
            if (i == 0) {
                --j;
            }
            if (i == 1) {
                ++j;
            }
            if (i == 2) {
                --zCoord2;
            }
            if (i == 3) {
                ++zCoord2;
            }
            if (!this.blockBlocksFlow(world, j, yCoord, zCoord2)) {
                if (world.getBlockMaterial(j, yCoord, zCoord2) != this.blockMaterial || world.getBlockMetadata(j, yCoord, zCoord2) != 0) {
                    if (!this.blockBlocksFlow(world, j, yCoord - 1, zCoord2)) {
                        this.flowCost[i] = 0;
                    } else {
                        this.flowCost[i] = this.calculateFlowCost(world, j, yCoord, zCoord2, 1, i);
                    }
                }
            }
        }
        int i = this.flowCost[0];
        for (int j = 1; j < 4; ++j) {
            if (this.flowCost[j] < i) {
                i = this.flowCost[j];
            }
        }
        for (int j = 0; j < 4; ++j) {
            this.isOptimalFlowDirection[j] = (this.flowCost[j] == i);
        }
        return this.isOptimalFlowDirection;
    }

    private boolean blockBlocksFlow(final World world, final int xCoord, final int yCoord, final int zCoord) {
        final int blockId = world.getBlockId(xCoord, yCoord, zCoord);
        return blockId == Block.door.blockID || blockId == Block.signPost.blockID || blockId == Block.ladder.blockID || (blockId != 0 && Block.blocksList[blockId].blockMaterial.isSolid());
    }

    protected int getSmallestFlowDecay(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        int flowDecay = this.getFlowDecay(world, xCoord, yCoord, zCoord);
        if (flowDecay < 0) {
            return nya4;
        }
        if (flowDecay == 0) {
            ++this.numAdjacentSources;
        }
        if (flowDecay >= 8) {
            flowDecay = 0;
        }
        return (nya4 < 0 || flowDecay < nya4) ? flowDecay : nya4;
    }

    private boolean liquidCanDisplaceBlock(final World world, final int xCoord, final int yCoord, final int zCoord) {
        final Material blockMaterial = world.getBlockMaterial(xCoord, yCoord, zCoord);
        return blockMaterial != this.blockMaterial && blockMaterial != Material.LAVA && !this.blockBlocksFlow(world, xCoord, yCoord, zCoord);
    }

    @Override
    public void onBlockAdded(final World world, final int xCoord, final int yCoord, final int zCoord) {
        super.onBlockAdded(world, xCoord, yCoord, zCoord);
        if (world.getBlockId(xCoord, yCoord, zCoord) == this.blockID) {
            world.scheduleBlockUpdate(xCoord, yCoord, zCoord, this.blockID);
        }
    }
}
