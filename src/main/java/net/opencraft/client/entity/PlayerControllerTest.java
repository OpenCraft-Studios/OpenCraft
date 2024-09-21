
package net.opencraft.client.entity;

import net.opencraft.OpenCraft;
import net.opencraft.Session;
import net.opencraft.block.Block;
import net.opencraft.entity.EntityPlayer;
import net.opencraft.item.ItemStack;
import net.opencraft.world.World;

public class PlayerControllerTest extends PlayerController {

    public PlayerControllerTest(final OpenCraft aw) {
        super(aw);
        this.field_1064_b = true;
    }

    @Override
    public void a() {
    }

    @Override
    public void func_6473_b(final EntityPlayer gi) {
        for (int i = 0; i < 9; ++i) {
            if (gi.inventory.mainInventory[i] == null) {
                this.mc.player.inventory.mainInventory[i] = new ItemStack(((Block) Session.registeredBlocksList.get(i)).blockID);
            } else {
                this.mc.player.inventory.mainInventory[i].stackSize = 1;
            }
        }
    }

    @Override
    public boolean shouldDrawHUD() {
        return false;
    }

    @Override
    public void func_717_a(final World fe) {
        super.func_717_a(fe);
    }

    @Override
    public void updateController() {
    }
}
