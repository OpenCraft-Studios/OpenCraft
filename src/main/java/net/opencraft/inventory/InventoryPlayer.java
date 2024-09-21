
package net.opencraft.inventory;

import net.opencraft.blocks.Block;
import net.opencraft.blocks.material.Material;
import net.opencraft.entity.Entity;
import net.opencraft.entity.EntityPlayer;
import net.opencraft.item.ItemArmor;
import net.opencraft.item.ItemStack;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.nbt.NBTTagList;

public class InventoryPlayer implements IInventory {

    public ItemStack[] mainInventory;
    public ItemStack[] armorInventory;
    public int currentItem;
    private EntityPlayer player;

    public InventoryPlayer(final EntityPlayer gi) {
        this.mainInventory = new ItemStack[36];
        this.armorInventory = new ItemStack[4];
        this.currentItem = 0;
        this.player = gi;
    }

    public ItemStack getCurrentItem() {
        return this.mainInventory[this.currentItem];
    }

    private int getInventorySlotContainItem(final int integer) {
        for (int i = 0; i < this.mainInventory.length; ++i) {
            if (this.mainInventory[i] != null && this.mainInventory[i].itemID == integer) {
                return i;
            }
        }
        return -1;
    }

    private int storeItemStack(final int integer) {
        for (int i = 0; i < this.mainInventory.length; ++i) {
            if (this.mainInventory[i] != null && this.mainInventory[i].itemID == integer && this.mainInventory[i].stackSize < this.mainInventory[i].getMaxStackSize() && this.mainInventory[i].stackSize < this.getInventoryStackLimit()) {
                return i;
            }
        }
        return -1;
    }

    private int getFirstEmptyStack() {
        for (int i = 0; i < this.mainInventory.length; ++i) {
            if (this.mainInventory[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public void setCurrentItem(final int integer, final boolean boolean2) {
        final int inventorySlotContainItem = this.getInventorySlotContainItem(integer);
        if (inventorySlotContainItem >= 0 && inventorySlotContainItem < 9) {
            this.currentItem = inventorySlotContainItem;
        }
    }

    public void changeCurrentItem(int integer) {
        if (integer > 0) {
            integer = 1;
        }
        if (integer < 0) {
            integer = -1;
        }
        this.currentItem -= integer;
        while (this.currentItem < 0) {
            this.currentItem += 9;
        }
        while (this.currentItem >= 9) {
            this.currentItem -= 9;
        }
    }

    private int storePartialItemStack(final int integer1, int integer2) {
        int n = this.storeItemStack(integer1);
        if (n < 0) {
            n = this.getFirstEmptyStack();
        }
        if (n < 0) {
            return integer2;
        }
        if (this.mainInventory[n] == null) {
            this.mainInventory[n] = new ItemStack(integer1, 0);
        }
        int n2 = integer2;
        if (n2 > this.mainInventory[n].getMaxStackSize() - this.mainInventory[n].stackSize) {
            n2 = this.mainInventory[n].getMaxStackSize() - this.mainInventory[n].stackSize;
        }
        if (n2 > this.getInventoryStackLimit() - this.mainInventory[n].stackSize) {
            n2 = this.getInventoryStackLimit() - this.mainInventory[n].stackSize;
        }
        if (n2 == 0) {
            return integer2;
        }
        integer2 -= n2;
        final ItemStack itemStack = this.mainInventory[n];
        itemStack.stackSize += n2;
        this.mainInventory[n].animationsToGo = 5;
        return integer2;
    }

    public void decrementAnimations() {
        for (int i = 0; i < this.mainInventory.length; ++i) {
            if (this.mainInventory[i] != null && this.mainInventory[i].animationsToGo > 0) {
                final ItemStack itemStack = this.mainInventory[i];
                --itemStack.animationsToGo;
            }
        }
    }

    public boolean consumeInventoryItem(final int integer) {
        final int inventorySlotContainItem = this.getInventorySlotContainItem(integer);
        if (inventorySlotContainItem < 0) {
            return false;
        }
        final ItemStack itemStack = this.mainInventory[inventorySlotContainItem];
        if (--itemStack.stackSize <= 0) {
            this.mainInventory[inventorySlotContainItem] = null;
        }
        return true;
    }

    public boolean addItemStackToInventory(final ItemStack hw) {
        if (hw.itemDamage == 0) {
            hw.stackSize = this.storePartialItemStack(hw.itemID, hw.stackSize);
            if (hw.stackSize == 0) {
                return true;
            }
        }
        final int firstEmptyStack = this.getFirstEmptyStack();
        if (firstEmptyStack >= 0) {
            this.mainInventory[firstEmptyStack] = hw;
            this.mainInventory[firstEmptyStack].animationsToGo = 5;
            return true;
        }
        return false;
    }

    public ItemStack decrStackSize(int integer1, final int integer2) {
        ItemStack[] array = this.mainInventory;
        if (integer1 >= this.mainInventory.length) {
            array = this.armorInventory;
            integer1 -= this.mainInventory.length;
        }
        if (array[integer1] == null) {
            return null;
        }
        if (array[integer1].stackSize <= integer2) {
            final ItemStack itemStack = array[integer1];
            array[integer1] = null;
            return itemStack;
        }
        final ItemStack splitStack = array[integer1].splitStack(integer2);
        if (array[integer1].stackSize == 0) {
            array[integer1] = null;
        }
        return splitStack;
    }

    public void setInventorySlotContents(int integer, final ItemStack hw) {
        ItemStack[] array = this.mainInventory;
        if (integer >= this.mainInventory.length) {
            array = this.armorInventory;
            integer -= this.mainInventory.length;
        }
        array[integer] = hw;
    }

    public float getStrVsBlock(final Block gs) {
        float n = 1.0f;
        if (this.mainInventory[this.currentItem] != null) {
            n *= this.mainInventory[this.currentItem].getStrVsBlock(gs);
        }
        return n;
    }

    public NBTTagList writeToNBT(final NBTTagList er) {
        for (int i = 0; i < this.mainInventory.length; ++i) {
            if (this.mainInventory[i] != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setByte("Slot", (byte) i);
                this.mainInventory[i].writeToNBT(nbtTagCompound);
                er.setTag(nbtTagCompound);
            }
        }
        for (int i = 0; i < this.armorInventory.length; ++i) {
            if (this.armorInventory[i] != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte("Slot", (byte) (i + 100));
                this.armorInventory[i].writeToNBT(nbtTagCompound2);
                er.setTag(nbtTagCompound2);
            }
        }
        return er;
    }

    public void readFromNBT(final NBTTagList er) {
        this.mainInventory = new ItemStack[36];
        this.armorInventory = new ItemStack[4];
        for (int i = 0; i < er.tagCount(); ++i) {
            final NBTTagCompound nbtTagCompound = (NBTTagCompound) er.tagAt(i);
            final int n = nbtTagCompound.getByte("Slot") & 0xFF;
            if (n >= 0 && n < this.mainInventory.length) {
                this.mainInventory[n] = new ItemStack(nbtTagCompound);
            }
            if (n >= 100 && n < this.armorInventory.length + 100) {
                this.armorInventory[n - 100] = new ItemStack(nbtTagCompound);
            }
        }
    }

    public int getSizeInventory() {
        return this.mainInventory.length + 4;
    }

    public ItemStack getStackInSlot(int integer) {
        ItemStack[] array = this.mainInventory;
        if (integer >= this.mainInventory.length) {
            array = this.armorInventory;
            integer -= this.mainInventory.length;
        }
        return array[integer];
    }

    public String getInvName() {
        return "Inventory";
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public int getDamageVsEntity(final Entity eq) {
        final ItemStack stackInSlot = this.getStackInSlot(this.currentItem);
        if (stackInSlot != null) {
            return stackInSlot.getDamageVsEntity(eq);
        }
        return 1;
    }

    public boolean canHarvestBlock(final Block gs) {
        if (gs.blockMaterial != Material.ROCK && gs.blockMaterial != Material.METAL) {
            return true;
        }
        final ItemStack stackInSlot = this.getStackInSlot(this.currentItem);
        return stackInSlot != null && stackInSlot.canHarvestBlock(gs);
    }

    public ItemStack armorItemInSlot(final int integer) {
        return this.armorInventory[integer];
    }

    public int getTotalArmorValue() {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        for (int i = 0; i < this.armorInventory.length; ++i) {
            if (this.armorInventory[i] != null && this.armorInventory[i].getItem() instanceof ItemArmor) {
                final int itemStackDamageable = this.armorInventory[i].isItemStackDamageable();
                n2 += itemStackDamageable - this.armorInventory[i].itemDamage;
                n3 += itemStackDamageable;
                n += ((ItemArmor) this.armorInventory[i].getItem()).damageReduceAmount;
            }
        }
        if (n3 == 0) {
            return 0;
        }
        return (n - 1) * n2 / n3 + 1;
    }

    public void damageArmor(final int integer) {
        for (int i = 0; i < this.armorInventory.length; ++i) {
            if (this.armorInventory[i] != null && this.armorInventory[i].getItem() instanceof ItemArmor) {
                this.armorInventory[i].damageItem(integer);
                if (this.armorInventory[i].stackSize == 0) {
                    this.armorInventory[i].onItemDestroyedByUse(this.player);
                    this.armorInventory[i] = null;
                }
            }
        }
    }

    public void dropAllItems() {
        for (int i = 0; i < this.mainInventory.length; ++i) {
            if (this.mainInventory[i] != null) {
                this.player.dropPlayerItemWithRandomChoice(this.mainInventory[i], true);
                this.mainInventory[i] = null;
            }
        }
        for (int i = 0; i < this.armorInventory.length; ++i) {
            if (this.armorInventory[i] != null) {
                this.player.dropPlayerItemWithRandomChoice(this.armorInventory[i], true);
                this.armorInventory[i] = null;
            }
        }
    }

    public void onInventoryChanged() {
    }
}
