
package net.opencraft.item;

import net.opencraft.entity.Player;
import net.opencraft.world.World;

public class ItemSoup extends ItemFood {

	public ItemSoup(final int itemid, final int healAmount) {
		super(itemid, healAmount);
	}

	@Override
	public ItemStack onItemRightClick(final ItemStack hw, final World fe, final Player gi) {
		super.onItemRightClick(hw, fe, gi);
		return new ItemStack(Item.bowlEmpty);
	}

}
