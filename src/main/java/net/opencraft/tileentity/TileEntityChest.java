
package net.opencraft.tileentity;

import net.opencraft.inventory.IInventory;
import net.opencraft.item.ItemStack;
import net.opencraft.nbt.NBTBase;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.nbt.NBTTagList;

public class TileEntityChest extends TileEntity implements IInventory {

    private ItemStack[] chestContents;

    public TileEntityChest() {
        this.chestContents = new ItemStack[36];
    }

    public int getSizeInventory() {
        return 27;
    }

    public ItemStack getStackInSlot(final int integer) {
        return this.chestContents[integer];
    }

    public ItemStack decrStackSize(final int integer1, final int integer2) {
        if (this.chestContents[integer1] == null) {
            return null;
        }
        if (this.chestContents[integer1].stackSize <= integer2) {
            final ItemStack itemStack = this.chestContents[integer1];
            this.chestContents[integer1] = null;
            return itemStack;
        }
        final ItemStack splitStack = this.chestContents[integer1].splitStack(integer2);
        if (this.chestContents[integer1].stackSize == 0) {
            this.chestContents[integer1] = null;
        }
        return splitStack;
    }

    public void setInventorySlotContents(final int integer, final ItemStack hw) {
        this.chestContents[integer] = hw;
        if (hw != null && hw.stackSize > this.getInventoryStackLimit()) {
            hw.stackSize = this.getInventoryStackLimit();
        }
    }

    public String getInvName() {
        return "Chest";
    }

    @Override
    public void readFromNBT(final NBTTagCompound ae) {
        super.readFromNBT(ae);
        final NBTTagList tagList = ae.getTagList("Items");
        this.chestContents = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); ++i) {
            final NBTTagCompound ae2 = (NBTTagCompound) tagList.tagAt(i);
            final int n = ae2.getByte("Slot") & 0xFF;
            if (n >= 0 && n < this.chestContents.length) {
                this.chestContents[n] = new ItemStack(ae2);
            }
        }
    }

    @Override
    public void writeToNBT(final NBTTagCompound ae) {
        super.writeToNBT(ae);
        final NBTTagList hm = new NBTTagList();
        for (int i = 0; i < this.chestContents.length; ++i) {
            if (this.chestContents[i] != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setByte("Slot", (byte) i);
                this.chestContents[i].writeToNBT(nbtTagCompound);
                hm.setTag(nbtTagCompound);
            }
        }
        ae.setTag("Items", (NBTBase) hm);
    }

    public int getInventoryStackLimit() {
        return 64;
    }
}
