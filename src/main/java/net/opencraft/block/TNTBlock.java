
package net.opencraft.block;

import java.util.Random;
import net.opencraft.block.material.Material;
import net.opencraft.entity.EntityTNTPrimed;
import net.opencraft.world.World;

public class TNTBlock extends Block {

    public TNTBlock(final int blockid, final int blockIndexInTexture) {
        super(blockid, blockIndexInTexture, Material.TNT);
    }

    @Override
    public int getBlockTextureFromSide(final int textureIndexSlot) {
        if (textureIndexSlot == 0) {
            return this.blockIndexInTexture + 2;
        }
        if (textureIndexSlot == 1) {
            return this.blockIndexInTexture + 1;
        }
        return this.blockIndexInTexture;
    }

    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }

    @Override
    public void onBlockDestroyedByExplosion(final World world, final int xCoord, final int yCoord, final int zCoord) {
        final EntityTNTPrimed entity = new EntityTNTPrimed(world, xCoord + 0.5f, yCoord + 0.5f, zCoord + 0.5f);
        entity.fuse = world.rand.nextInt(entity.fuse / 4) + entity.fuse / 8;
        world.entityJoinedWorld(entity);
    }

    @Override
    public void onBlockDestroyedByPlayer(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        final EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed(world, xCoord + 0.5f, yCoord + 0.5f, zCoord + 0.5f);
        world.entityJoinedWorld(entityTNTPrimed);
        world.playSoundAtEntity(entityTNTPrimed, "random.fuse", 1.0f, 1.0f);
    }
}
