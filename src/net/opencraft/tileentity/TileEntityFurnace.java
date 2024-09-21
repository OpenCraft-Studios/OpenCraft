
package net.opencraft.tileentity;

import net.opencraft.block.Block;
import net.opencraft.block.BlockFurnace;
import net.opencraft.block.material.Material;
import net.opencraft.inventory.IInventory;
import net.opencraft.item.Item;
import net.opencraft.item.ItemStack;
import net.opencraft.nbt.NBTBase;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.nbt.NBTTagList;

public class TileEntityFurnace extends TileEntity implements IInventory {

    private ItemStack[] furnaceItemStacks;
    private int furnaceBurnTime;
    private int currentItemBurnTime;
    private int furnaceCookTime;

    public TileEntityFurnace() {
        this.furnaceItemStacks = new ItemStack[3];
        this.furnaceBurnTime = 0;
        this.currentItemBurnTime = 0;
        this.furnaceCookTime = 0;
    }

    public int getSizeInventory() {
        return this.furnaceItemStacks.length;
    }

    public ItemStack getStackInSlot(final int integer) {
        return this.furnaceItemStacks[integer];
    }

    public ItemStack decrStackSize(final int integer1, final int integer2) {
        if (this.furnaceItemStacks[integer1] == null) {
            return null;
        }
        if (this.furnaceItemStacks[integer1].stackSize <= integer2) {
            final ItemStack itemStack = this.furnaceItemStacks[integer1];
            this.furnaceItemStacks[integer1] = null;
            return itemStack;
        }
        final ItemStack splitStack = this.furnaceItemStacks[integer1].splitStack(integer2);
        if (this.furnaceItemStacks[integer1].stackSize == 0) {
            this.furnaceItemStacks[integer1] = null;
        }
        return splitStack;
    }

    public void setInventorySlotContents(final int integer, final ItemStack hw) {
        this.furnaceItemStacks[integer] = hw;
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
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); ++i) {
            final NBTTagCompound ae2 = (NBTTagCompound) tagList.tagAt(i);
            final byte byte1 = ae2.getByte("Slot");
            if (byte1 >= 0 && byte1 < this.furnaceItemStacks.length) {
                this.furnaceItemStacks[byte1] = new ItemStack(ae2);
            }
        }
        this.furnaceBurnTime = ae.getShort("BurnTime");
        this.furnaceCookTime = ae.getShort("CookTime");
        this.currentItemBurnTime = this.getItemBurnTime(this.furnaceItemStacks[1]);
    }

    @Override
    public void writeToNBT(final NBTTagCompound ae) {
        super.writeToNBT(ae);
        ae.setShort("BurnTime", (short) this.furnaceBurnTime);
        ae.setShort("CookTime", (short) this.furnaceCookTime);
        final NBTTagList hm = new NBTTagList();
        for (int i = 0; i < this.furnaceItemStacks.length; ++i) {
            if (this.furnaceItemStacks[i] != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setByte("Slot", (byte) i);
                this.furnaceItemStacks[i].writeToNBT(nbtTagCompound);
                hm.setTag(nbtTagCompound);
            }
        }
        ae.setTag("Items", (NBTBase) hm);
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public int getCookProgressScaled(final int integer) {
        return this.furnaceCookTime * integer / 200;
    }

    public int getBurnTimeRemainingScaled(final int integer) {
        if (this.currentItemBurnTime == 0) {
            this.currentItemBurnTime = 200;
        }
        return this.furnaceBurnTime * integer / this.currentItemBurnTime;
    }

    public boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }

    @Override
    public void updateEntity() {
        final boolean b = this.furnaceBurnTime > 0;
        boolean b2 = false;
        if (this.furnaceBurnTime > 0) {
            --this.furnaceBurnTime;
            b2 = true;
        }
        if (this.furnaceBurnTime == 0 && this.canSmelt()) {
            final int itemBurnTime = this.getItemBurnTime(this.furnaceItemStacks[1]);
            this.furnaceBurnTime = itemBurnTime;
            this.currentItemBurnTime = itemBurnTime;
            if (this.furnaceBurnTime > 0) {
                b2 = true;
                if (this.furnaceItemStacks[1] != null) {
                    final ItemStack itemStack = this.furnaceItemStacks[1];
                    --itemStack.stackSize;
                    if (this.furnaceItemStacks[1].stackSize == 0) {
                        this.furnaceItemStacks[1] = null;
                    }
                }
            }
        }
        if (this.isBurning() && this.canSmelt()) {
            ++this.furnaceCookTime;
            if (this.furnaceCookTime == 200) {
                this.furnaceCookTime = 0;
                this.smeltItem();
                b2 = true;
            }
        } else {
            this.furnaceCookTime = 0;
        }
        if (b != this.furnaceBurnTime > 0) {
            b2 = true;
            BlockFurnace.updateFurnaceBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
        }
        if (b2) {
            this.worldObj.func_698_b(this.xCoord, this.yCoord, this.zCoord);
        }
    }

    private boolean canSmelt() {
        if (this.furnaceItemStacks[0] == null) {
            return false;
        }
        final int smeltingResult = this.getSmeltingResult(this.furnaceItemStacks[0].getItem().shiftedIndex);
        return smeltingResult >= 0 && (this.furnaceItemStacks[2] == null || (this.furnaceItemStacks[2].itemID == smeltingResult && ((this.furnaceItemStacks[2].stackSize < this.getInventoryStackLimit() && this.furnaceItemStacks[2].stackSize < this.furnaceItemStacks[2].getMaxStackSize()) || this.furnaceItemStacks[2].stackSize < Item.itemsList[smeltingResult].getItemStackLimit())));
    }

    public void smeltItem() {
        if (!this.canSmelt()) {
            return;
        }
        final int smeltingResult = this.getSmeltingResult(this.furnaceItemStacks[0].getItem().shiftedIndex);
        if (this.furnaceItemStacks[2] == null) {
            this.furnaceItemStacks[2] = new ItemStack(smeltingResult, 1);
        } else if (this.furnaceItemStacks[2].itemID == smeltingResult) {
            final ItemStack itemStack = this.furnaceItemStacks[2];
            ++itemStack.stackSize;
        }
        final ItemStack itemStack2 = this.furnaceItemStacks[0];
        --itemStack2.stackSize;
        if (this.furnaceItemStacks[0].stackSize <= 0) {
            this.furnaceItemStacks[0] = null;
        }
    }

    private int getSmeltingResult(final int result) {
        if (result == Block.oreIron.blockID) {
            return Item.ingotIron.shiftedIndex;
        }
        if (result == Block.oreGold.blockID) {
            return Item.ingotGold.shiftedIndex;
        }
        if (result == Block.oreDiamond.blockID) {
            return Item.diamond.shiftedIndex;
        }
        if (result == Block.sand.blockID) {
            return Block.glass.blockID;
        }
        if (result == Item.porkRaw.shiftedIndex) {
            return Item.porkCooked.shiftedIndex;
        }
        if (result == Block.cobblestone.blockID) {
            return Block.stone.blockID;
        }
        return -1;
    }

    private int getItemBurnTime(final ItemStack hw) {
        if (hw == null) {
            return 0;
        }
        final int shiftedIndex = hw.getItem().shiftedIndex;
        if (shiftedIndex < 256 && Block.blocksList[shiftedIndex].blockMaterial == Material.wood) {
            return 300;
        }
        if (shiftedIndex == Item.stick.shiftedIndex) {
            return 100;
        }
        if (shiftedIndex == Item.coal.shiftedIndex) {
            return 1600;
        }
        return 0;
    }

    @Override
    public void onInventoryChanged() {
        this.worldObj.func_698_b(this.xCoord, this.yCoord, this.zCoord);
    }
}
