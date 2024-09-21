
package net.opencraft.client.entity;

import net.opencraft.OpenCraft;
import net.opencraft.aa;
import net.opencraft.block.Block;
import net.opencraft.ck;
import net.opencraft.entity.EntityAnimal;
import net.opencraft.entity.EntityCreeper;
import net.opencraft.entity.EntityMonster;
import net.opencraft.entity.EntityPig;
import net.opencraft.entity.EntityPlayer;
import net.opencraft.entity.EntitySheep;
import net.opencraft.entity.EntitySkeleton;
import net.opencraft.entity.EntitySpider;
import net.opencraft.entity.EntityZombie;
import net.opencraft.item.ItemStack;
import net.opencraft.world.World;

public class PlayerControllerSP extends PlayerController {

    private int field_1074_c;
    private int field_1073_d;
    private int field_1072_e;
    private float curBlockDamage;
    private float prevBlockDamage;
    private float field_1069_h;
    private int blockHitWait;
    private ck j;
    private ck k;

    public PlayerControllerSP(final OpenCraft aw) {
        super(aw);
        this.field_1074_c = -1;
        this.field_1073_d = -1;
        this.field_1072_e = -1;
        this.curBlockDamage = 0.0f;
        this.prevBlockDamage = 0.0f;
        this.field_1069_h = 0.0f;
        this.blockHitWait = 0;
        this.j = new aa(this, 100, EntityMonster.class, new Class[]{EntityZombie.class, EntitySkeleton.class, EntityCreeper.class, EntitySpider.class});
        this.k = new ck(20, EntityAnimal.class, new Class[]{EntitySheep.class, EntityPig.class});
    }

    @Override
    public void flipPlayer(final EntityPlayer gi) {
        gi.rotationYaw = -180.0f;
    }

    @Override
    public void a() {
    }

    @Override
    public boolean sendBlockRemoved(final int xCoord, final int yCoord, final int zCoord) {
        final int blockId = this.mc.theWorld.getBlockId(xCoord, yCoord, zCoord);
        final int blockMetadata = this.mc.theWorld.getBlockMetadata(xCoord, yCoord, zCoord);
        final boolean sendBlockRemoved = super.sendBlockRemoved(xCoord, yCoord, zCoord);
        final ItemStack currentEquippedItem = this.mc.thePlayer.getCurrentEquippedItem();
        if (currentEquippedItem != null) {
            currentEquippedItem.onDestroyBlock(blockId, xCoord, yCoord, zCoord);
            if (currentEquippedItem.stackSize == 0) {
                currentEquippedItem.onItemDestroyedByUse(this.mc.thePlayer);
                this.mc.thePlayer.displayGUIInventory();
            }
        }
        if (sendBlockRemoved && this.mc.thePlayer.canHarvestBlock(Block.blocksList[blockId])) {
            Block.blocksList[blockId].dropBlockAsItem(this.mc.theWorld, xCoord, yCoord, zCoord, blockMetadata);
        }
        return sendBlockRemoved;
    }

    @Override
    public void clickBlock(final int xCoord, final int yCoord, final int zCoord) {
        final int blockId = this.mc.theWorld.getBlockId(xCoord, yCoord, zCoord);
        if (blockId > 0 && this.curBlockDamage == 0.0f) {
            Block.blocksList[blockId].onBlockClicked(this.mc.theWorld, xCoord, yCoord, zCoord, this.mc.thePlayer);
        }
        if (blockId > 0 && Block.blocksList[blockId].blockStrength(this.mc.thePlayer) >= 1.0f) {
            this.sendBlockRemoved(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public void resetBlockRemoving() {
        this.curBlockDamage = 0.0f;
        this.blockHitWait = 0;
    }

    @Override
    public void sendBlockRemoving(final int integer1, final int integer2, final int integer3, final int integer4) {

        if (this.blockHitWait > 0) {
            --this.blockHitWait;
            return;
        }
        super.sendBlockRemoving(integer1, integer2, integer3, integer4);
        if (integer1 == this.field_1074_c && integer2 == this.field_1073_d && integer3 == this.field_1072_e) {
            final int blockId = this.mc.theWorld.getBlockId(integer1, integer2, integer3);
            if (blockId == 0) {
                return;
            }
            final Block block = Block.blocksList[blockId];
            this.curBlockDamage += block.blockStrength(this.mc.thePlayer);
            if (this.field_1069_h % 4.0f == 0.0f && block != null) {
                this.mc.sndManager.playSound(block.stepSound.stepSoundDir2(), integer1 + 0.5f, integer2 + 0.5f, integer3 + 0.5f, (block.stepSound.soundVolume() + 1.0f) / 8.0f, block.stepSound.soundPitch() * 0.5f);
            }
            ++this.field_1069_h;
            if (this.curBlockDamage >= 1.0f) {
                this.sendBlockRemoved(integer1, integer2, integer3);
                this.curBlockDamage = 0.0f;
                this.prevBlockDamage = 0.0f;
                this.field_1069_h = 0.0f;
                this.blockHitWait = 5;
            }
        } else {
            this.curBlockDamage = 0.0f;
            this.prevBlockDamage = 0.0f;
            this.field_1069_h = 0.0f;
            this.field_1074_c = integer1;
            this.field_1073_d = integer2;
            this.field_1072_e = integer3;
        }
    }

    @Override
    public void setPartialTime(final float float1) {
        if (this.curBlockDamage <= 0.0f) {
            this.mc.ingameGUI.damageGuiPartialTime = 0.0f;
            this.mc.renderGlobal.damagePartialTime = 0.0f;
        } else {
            final float n = this.prevBlockDamage + (this.curBlockDamage - this.prevBlockDamage) * float1;
            this.mc.ingameGUI.damageGuiPartialTime = n;
            this.mc.renderGlobal.damagePartialTime = n;
        }
    }

    @Override
    public float getBlockReachDistance() {
        return 4.0f;
    }

    @Override
    public void func_717_a(final World fe) {
        super.func_717_a(fe);
    }

    @Override
    public void updateController() {
        this.prevBlockDamage = this.curBlockDamage;
        this.j.a(this.mc.theWorld);
        this.k.a(this.mc.theWorld);
        this.mc.sndManager.playRandomMusicIfReady();
    }
}
