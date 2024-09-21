
package net.opencraft.item;

import net.opencraft.blocks.Block;
import net.opencraft.entity.Entity;
import net.opencraft.entity.EntityLiving;
import net.opencraft.entity.EntityPlayer;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.world.World;

public final class ItemStack {

    public int stackSize;
    public int animationsToGo;
    public int itemID;
    public int itemDamage;

    public ItemStack(final Block gs) {
        this(gs, 1);
    }

    public ItemStack(final Block gs, final int integer) {
        this(gs.blockID, integer);
    }

    public ItemStack(final Item ge) {
        this(ge, 1);
    }

    public ItemStack(final Item ge, final int integer) {
        this(ge.shiftedIndex, integer);
    }

    public ItemStack(final int integer) {
        this(integer, 1);
    }

    public ItemStack(final int itemID, final int stackSize) {
        this.stackSize = 0;
        this.itemID = itemID;
        this.stackSize = stackSize;
    }

    public ItemStack(final int itemID, final int stackSize, final int itemDamage) {
        this.stackSize = 0;
        this.itemID = itemID;
        this.stackSize = stackSize;
        this.itemDamage = itemDamage;
    }

    public ItemStack(final NBTTagCompound ae) {
        this.stackSize = 0;
        this.readFromNBT(ae);
    }

    public ItemStack splitStack(final int integer) {
        this.stackSize -= integer;
        return new ItemStack(this.itemID, integer, this.itemDamage);
    }

    public Item getItem() {
        return Item.itemsList[this.itemID];
    }

    public int getIconIndex() {
        return this.getItem().getIconFromDamage(this);
    }

    public boolean useItem(final EntityPlayer gi, final World fe, final int integer3, final int integer4, final int integer5, final int integer6) {
        return this.getItem().onItemUse(this, gi, fe, integer3, integer4, integer5, integer6);
    }

    public float getStrVsBlock(final Block gs) {
        return this.getItem().getStrVsBlock(this, gs);
    }

    public ItemStack useItemRightClick(final World fe, final EntityPlayer gi) {
        return this.getItem().onItemRightClick(this, fe, gi);
    }

    public NBTTagCompound writeToNBT(final NBTTagCompound ae) {
        ae.setShort("id", (short) this.itemID);
        ae.setByte("Count", (byte) this.stackSize);
        ae.setShort("Damage", (short) this.itemDamage);
        return ae;
    }

    public void readFromNBT(final NBTTagCompound ae) {
        this.itemID = ae.getShort("id");
        this.stackSize = ae.getByte("Count");
        this.itemDamage = ae.getShort("Damage");
    }

    public int getMaxStackSize() {
        return this.getItem().getItemStackLimit();
    }

    public int isItemStackDamageable() {
        return Item.itemsList[this.itemID].getMaxDamage();
    }

    public void damageItem(final int integer) {
        this.itemDamage += integer;
        if (this.itemDamage > this.isItemStackDamageable()) {
            --this.stackSize;
            if (this.stackSize < 0) {
                this.stackSize = 0;
            }
            this.itemDamage = 0;
        }
    }

    public void hitEntity(final EntityLiving ka) {
        Item.itemsList[this.itemID].hitEntity(this, ka);
    }

    public void onDestroyBlock(final int integer1, final int integer2, final int integer3, final int integer4) {
        Item.itemsList[this.itemID].onBlockDestroyed(this, integer1, integer2, integer3, integer4);
    }

    public int getDamageVsEntity(final Entity eq) {
        return Item.itemsList[this.itemID].getDamageVsEntity(eq);
    }

    public boolean canHarvestBlock(final Block gs) {
        return Item.itemsList[this.itemID].canHarvestBlock(gs);
    }

    public void onItemDestroyedByUse(final EntityPlayer gi) {
    }

    public void useItemOnEntity(final EntityLiving ka) {
        Item.itemsList[this.itemID].saddleEntity(this, ka);
    }
}
