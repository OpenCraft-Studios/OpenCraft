
package net.opencraft.client.entity;

import static net.opencraft.OpenCraft.*;

import net.opencraft.aa;
import net.opencraft.ck;
import net.opencraft.blocks.Block;
import net.opencraft.entity.*;
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
	private final ck j;
	private final ck k;

	public PlayerControllerSP() {
		super();
		field_1074_c = -1;
		field_1073_d = -1;
		field_1072_e = -1;
		curBlockDamage = 0.0f;
		prevBlockDamage = 0.0f;
		field_1069_h = 0.0f;
		blockHitWait = 0;
		j = new aa(this, 100, EntityMonster.class,
				new Class[] { EntityZombie.class, EntitySkeleton.class, EntityCreeper.class, EntitySpider.class });
		k = new ck(20, EntityAnimal.class, new Class[] { EntitySheep.class, EntityPig.class });
	}

	@Override
	public void flipPlayer(final Player gi) {
		gi.rotationYaw = -180.0f;
	}

	@Override
	public void a() {
	}

	@Override
	public boolean sendBlockRemoved(final int xCoord, final int yCoord, final int zCoord) {
		final int blockId = oc.world.getBlockId(xCoord, yCoord, zCoord);
		final int blockMetadata = oc.world.getBlockMetadata(xCoord, yCoord, zCoord);
		final boolean sendBlockRemoved = super.sendBlockRemoved(xCoord, yCoord, zCoord);
		final ItemStack currentEquippedItem = oc.player.getCurrentEquippedItem();
		if (currentEquippedItem != null) {
			currentEquippedItem.onDestroyBlock(blockId, xCoord, yCoord, zCoord);
			if (currentEquippedItem.stackSize == 0) {
				currentEquippedItem.onItemDestroyedByUse(oc.player);
				oc.player.displayGUIInventory();
			}
		}
		if (sendBlockRemoved && oc.player.canHarvestBlock(Block.BLOCKS[blockId]))
			Block.BLOCKS[blockId].dropBlockAsItem(oc.world, xCoord, yCoord, zCoord, blockMetadata);
		return sendBlockRemoved;
	}

	@Override
	public void clickBlock(final int xCoord, final int yCoord, final int zCoord) {
		final int blockId = oc.world.getBlockId(xCoord, yCoord, zCoord);
		if (blockId > 0 && curBlockDamage == 0.0f)
			Block.BLOCKS[blockId].onBlockClicked(oc.world, xCoord, yCoord, zCoord, oc.player);
		if (blockId > 0 && Block.BLOCKS[blockId].blockStrength(oc.player) >= 1.0f)
			this.sendBlockRemoved(xCoord, yCoord, zCoord);
	}

	@Override
	public void resetBlockRemoving() {
		curBlockDamage = 0.0f;
		blockHitWait = 0;
	}

	@Override
	public void sendBlockRemoving(final int integer1, final int integer2, final int integer3, final int integer4) {

		if (blockHitWait > 0) {
			--blockHitWait;
			return;
		}
		super.sendBlockRemoving(integer1, integer2, integer3, integer4);
		if (integer1 == field_1074_c && integer2 == field_1073_d && integer3 == field_1072_e) {
			final int blockId = oc.world.getBlockId(integer1, integer2, integer3);
			if (blockId == 0)
				return;
			final Block block = Block.BLOCKS[blockId];
			curBlockDamage += block.blockStrength(oc.player);
			if (field_1069_h % 4.0f == 0.0f && block != null)
				oc.sndManager.playSound(block.stepSound.stepSoundDir2(), integer1 + 0.5f, integer2 + 0.5f,
						integer3 + 0.5f, (block.stepSound.soundVolume() + 1.0f) / 8.0f,
						block.stepSound.soundPitch() * 0.5f);
			++field_1069_h;
			if (curBlockDamage >= 1.0f) {
				this.sendBlockRemoved(integer1, integer2, integer3);
				curBlockDamage = 0.0f;
				prevBlockDamage = 0.0f;
				field_1069_h = 0.0f;
				blockHitWait = 5;
			}
		} else {
			curBlockDamage = 0.0f;
			prevBlockDamage = 0.0f;
			field_1069_h = 0.0f;
			field_1074_c = integer1;
			field_1073_d = integer2;
			field_1072_e = integer3;
		}
	}

	@Override
	public void setPartialTime(final float float1) {
		if (curBlockDamage <= 0.0f) {
			oc.ingameGUI.damageGuiPartialTime = 0.0f;
			oc.renderGlobal.damagePartialTime = 0.0f;
		} else {
			final float n = prevBlockDamage + (curBlockDamage - prevBlockDamage) * float1;
			oc.ingameGUI.damageGuiPartialTime = n;
			oc.renderGlobal.damagePartialTime = n;
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
		prevBlockDamage = curBlockDamage;
		j.a(oc.world);
		k.a(oc.world);
		oc.sndManager.playRandomMusicIfReady();
	}

}
