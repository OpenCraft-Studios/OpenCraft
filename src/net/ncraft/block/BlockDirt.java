
package net.ncraft.block;

import net.ncraft.block.material.Material;

public class BlockDirt extends Block {

    protected BlockDirt(final int blockid, final int blockIndexInTexture) {
        super(blockid, blockIndexInTexture, Material.ground);
    }
}
