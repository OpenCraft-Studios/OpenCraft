
package net.opencraft.item;

import net.opencraft.blocks.Block;
import net.opencraft.entity.EntityItem;
import net.opencraft.entity.Player;
import net.opencraft.world.World;

public class ItemHoe extends Item {

	public ItemHoe(final int itemid, final int toolTier) {
		super(itemid);
		this.maxStackSize = 1;
		this.maxDamage = 32 << toolTier;
	}

	@Override
	public boolean onItemUse(final ItemStack hw, final Player gi, final World fe, final int xCoord, final int yCoord, final int zCoord, final int integer7) {
		final int blockId = fe.getBlockId(xCoord, yCoord, zCoord);
		if ((!fe.getBlockMaterial(xCoord, yCoord + 1, zCoord).isSolid() && blockId == Block.grass.id) || blockId == Block.dirt.id) {
			final Block tilledField = Block.tilledField;
			fe.playSoundEffect(xCoord + 0.5f, yCoord + 0.5f, zCoord + 0.5f, tilledField.stepSound.stepSoundDir2(), (tilledField.stepSound.soundVolume() + 1.0f) / 2.0f, tilledField.stepSound.soundPitch() * 0.8f);
			fe.setBlockWithNotify(xCoord, yCoord, zCoord, tilledField.id);
			hw.damageItem(1);
			if (fe.random.nextInt(8) == 0 && blockId == Block.grass.id) {
				for ( int n = 1, i = 0; i < n; ++i ) {
					final float n2 = 0.7f;
					final EntityItem entity = new EntityItem(fe, xCoord + (fe.random.nextFloat() * n2 + (1.0f - n2) * 0.5f), yCoord + 1.2f, zCoord + (fe.random.nextFloat() * n2 + (1.0f - n2) * 0.5f), new ItemStack(Item.seeds));
					entity.delayBeforeCanPickup = 10;
					fe.onEntityJoin(entity);
				}
			}
			return true;
		}
		return false;
	}

}
