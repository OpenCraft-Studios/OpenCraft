
package net.opencraft.blocks;

import net.opencraft.blocks.material.Material;

public class DirtBlock extends Block {

    protected DirtBlock(final int blockid, final int blockIndexInTexture) {
        super(blockid, blockIndexInTexture, Material.GROUND);
    }
}
