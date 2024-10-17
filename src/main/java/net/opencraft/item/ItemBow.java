
package net.opencraft.item;

import net.opencraft.entity.Entity;
import net.opencraft.entity.EntityArrow;
import net.opencraft.entity.Player;
import net.opencraft.world.World;

public class ItemBow extends Item {

	public ItemBow(final int itemid) {
		super(itemid);
		this.maxStackSize = 1;
	}

	@Override
	public ItemStack onItemRightClick(final ItemStack hw, final World fe, Player player) {
		if (player.inventory.consumeInventoryItem(Item.arrow.shiftedIndex)) {
			fe.playSound((Entity) player, "random.bow", 1.0f, 1.0f / (ItemBow.itemRand.nextFloat() * 0.4f + 0.8f));
			fe.onEntityJoin((Entity) new EntityArrow(fe, player));
		}
		return hw;
	}

}
