
package net.opencraft.block;

import net.opencraft.block.material.Material;

public class DirtBlock extends Block {

    protected DirtBlock(final int blockid, final int blockIndexInTexture) {
        super(blockid, blockIndexInTexture, Material.GROUND);
    }
}
