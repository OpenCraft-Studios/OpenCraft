
package net.opencraft.blocks;

import java.util.Random;

import net.opencraft.blocks.material.EnumMaterial;
import net.opencraft.entity.EntityFallingSand;
import net.opencraft.world.World;

public class SandBlock extends Block {

	public static boolean fallInstantly;

	static {
		SandBlock.fallInstantly = false;
	}

	public SandBlock(final int blockid, final int blockIndexInTexture) {
		super(blockid, blockIndexInTexture, EnumMaterial.SAND);
	}

	@Override
	public void onBlockAdded(final World world, final int xCoord, final int yCoord, final int zCoord) {
		world.scheduleBlockUpdate(xCoord, yCoord, zCoord, this.id);
	}

	@Override
	public void onNeighborBlockChange(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
		world.scheduleBlockUpdate(xCoord, yCoord, zCoord, this.id);
	}

	@Override
	public void updateTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
		this.tryToFall(world, xCoord, yCoord, zCoord);
	}

	private void tryToFall(final World world, final int xCoord, final int yCoord, final int zCoord) {
		if (canFallBelow(world, xCoord, yCoord - 1, zCoord) && yCoord >= 0) {
			final EntityFallingSand entity = new EntityFallingSand(world, xCoord + 0.5f, yCoord + 0.5f, zCoord + 0.5f, this.id);
			if (SandBlock.fallInstantly) {
				while(!entity.isDead) {
					entity.onUpdate();
				}
			} else {
				world.onEntityJoin(entity);
			}
		}
	}

	@Override
	public int tickRate() {
		return 3;
	}

	public static boolean canFallBelow(final World world, final int xCoord, final int yCoord, final int zCoord) {
		final int blockId = world.getBlockId(xCoord, yCoord, zCoord);
		if (blockId == 0) {
			return true;
		}
		if (blockId == Block.fire.id) {
			return true;
		}
		final EnumMaterial blockMaterial = Block.BLOCKS[blockId].blockMaterial;
		return blockMaterial == EnumMaterial.WATER || blockMaterial == EnumMaterial.LAVA;
	}

}
