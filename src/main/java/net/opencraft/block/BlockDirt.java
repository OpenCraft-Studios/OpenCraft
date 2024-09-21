
package net.opencraft.block;

import net.opencraft.block.material.Material;

public class BlockDirt extends Block {

    protected BlockDirt(final int blockid, final int blockIndexInTexture) {
        super(blockid, blockIndexInTexture, Material.ground);
    }
}
