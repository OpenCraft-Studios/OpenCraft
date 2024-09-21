
package net.opencraft.entity;

import net.opencraft.OpenCraft;
import net.opencraft.Session;
import net.opencraft.client.input.MovementInput;
import net.opencraft.inventory.IInventory;
import net.opencraft.item.ItemStack;
import net.opencraft.nbt.NBTBase;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.nbt.NBTTagList;
import net.opencraft.client.renderer.gui.*;
import net.opencraft.tileentity.TileEntityFurnace;
import net.opencraft.tileentity.TileEntitySign;
import net.opencraft.world.World;

public class EntityPlayerSP extends EntityPlayer {

    public MovementInput movementInput;
    private OpenCraft mc;

    public EntityPlayerSP(final OpenCraft aw, final World fe, final Session gg) {
        super(fe);
        this.mc = aw;
        if (fe != null) {
            if (fe.player != null) {
                fe.setEntityDead(fe.player);
            }
            fe.player = this;
        }
        if (gg != null && gg.username != null && gg.username.length() > 0) {
            this.skinUrl = "http://www.minecraft.net/skin/" + gg.username + ".png";
        }
        this.username = gg.username;
    }

    public void updatePlayerActionState() {
        this.moveStrafing = this.movementInput.moveStrafe;
        this.moveForward = this.movementInput.moveForward;
        this.isJumping = this.movementInput.jump;
    }

    @Override
    public void onLivingUpdate() {
        this.movementInput.updatePlayerMoveState(this);
        super.onLivingUpdate();
    }

    public void resetPlayerKeyState() {
        this.movementInput.resetKeyState();
    }

    public void handleKeyPress(final int integer, final boolean boolean2) {
        this.movementInput.checkKeyForMovementInput(integer, boolean2);
    }

    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("Score", this.score);
        nbtTagCompound.setTag("Inventory", (NBTBase) this.inventory.writeToNBT(new NBTTagList()));
    }

    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.score = nbtTagCompound.getInteger("Score");
        this.inventory.readFromNBT(nbtTagCompound.getTagList("Inventory"));
    }

    @Override
    public void displayGUIChest(final IInventory kd) {
        this.mc.displayGuiScreen(new GuiChest(this.inventory, kd));
    }

    @Override
    public void displayGUIEditSign(final TileEntitySign jn) {
        this.mc.displayGuiScreen(new GuiEditSign(jn));
    }

    @Override
    public void displayWorkbenchGUI() {
        this.mc.displayGuiScreen(new GuiCrafting(this.inventory));
    }

    @Override
    public void displayGUIFurnace(final TileEntityFurnace el) {
        this.mc.displayGuiScreen(new GuiFurnace(this.inventory, el));
    }

    public ItemStack getCurrentEquippedItem() {
        return this.inventory.getCurrentItem();
    }

    public void displayGUIInventory() {
        this.inventory.setInventorySlotContents(this.inventory.currentItem, null);
    }

    public void a(final Entity eq) {
        final int damageVsEntity = this.inventory.getDamageVsEntity(eq);
        if (damageVsEntity > 0) {
            eq.attackEntityFrom(this, damageVsEntity);
            final ItemStack currentEquippedItem = this.getCurrentEquippedItem();
            if (currentEquippedItem != null && eq instanceof EntityLiving) {
                currentEquippedItem.hitEntity((EntityLiving) eq);
                if (currentEquippedItem.stackSize <= 0) {
                    currentEquippedItem.onItemDestroyedByUse(this);
                    this.displayGUIInventory();
                }
            }
        }
    }

    @Override
    public void onItemPickup(final Entity eq) {
        this.mc.effectRenderer.addEffect(new EntityPickupFX(this.mc.world, eq, this, -0.5f));
    }

    public int getPlayerArmorValue() {
        return this.inventory.getTotalArmorValue();
    }

    @Override
    public void c(final Entity eq) {
        if (eq.interact(this)) {
            return;
        }
        final ItemStack currentEquippedItem = this.getCurrentEquippedItem();
        if (currentEquippedItem != null && eq instanceof EntityLiving) {
            currentEquippedItem.useItemOnEntity((EntityLiving) eq);
            if (currentEquippedItem.stackSize <= 0) {
                currentEquippedItem.onItemDestroyedByUse(this);
                this.displayGUIInventory();
            }
        }
    }
}
