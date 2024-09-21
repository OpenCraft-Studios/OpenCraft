
package net.opencraft.item;

import net.opencraft.block.material.Material;
import net.opencraft.client.input.MovingObjectPosition;
import net.opencraft.entity.EntityPlayer;
import net.opencraft.util.Mth;
import net.opencraft.util.Vec3;
import net.opencraft.world.World;

public class ItemBucket extends Item {

    private int isFull;

    public ItemBucket(final int itemid, final int integer2) {
        super(itemid);
        this.maxStackSize = 1;
        this.maxDamage = 64;
        this.isFull = integer2;
    }

    @Override
    public ItemStack onItemRightClick(final ItemStack hw, final World fe, final EntityPlayer gi) {
        final float n = 1.0f;
        final float n2 = gi.prevRotationPitch + (gi.rotationPitch - gi.prevRotationPitch) * n;
        final float n3 = gi.prevRotationYaw + (gi.rotationYaw - gi.prevRotationYaw) * n;
        final Vec3 vector = Vec3.newTemp(gi.prevPosX + (gi.posX - gi.prevPosX) * n, gi.prevPosY + (gi.posY - gi.prevPosY) * n, gi.prevPosZ + (gi.posZ - gi.prevPosZ) * n);
        final float cos = Mth.cos(-n3 * 0.017453292f - 3.1415927f);
        final float sin = Mth.sin(-n3 * 0.017453292f - 3.1415927f);
        final float n4 = -Mth.cos(-n2 * 0.017453292f);
        final float sin2 = Mth.sin(-n2 * 0.017453292f);
        final float n5 = sin * n4;
        final float n6 = sin2;
        final float n7 = cos * n4;
        final double n8 = 5.0;
        final MovingObjectPosition rayTraceBlocks_do_do = fe.rayTraceBlocks_do_do(vector, vector.addVector(n5 * n8, n6 * n8, n7 * n8), this.isFull == 0);
        if (rayTraceBlocks_do_do == null) {
            return hw;
        }
        if (rayTraceBlocks_do_do.typeOfHit == 0) {
            int blockX = rayTraceBlocks_do_do.blockX;
            int blockY = rayTraceBlocks_do_do.blockY;
            int blockZ = rayTraceBlocks_do_do.blockZ;
            if (this.isFull == 0) {
                if (fe.getBlockMaterial(blockX, blockY, blockZ) == Material.WATER && fe.getBlockMetadata(blockX, blockY, blockZ) == 0) {
                    fe.setBlockWithNotify(blockX, blockY, blockZ, 0);
                    return new ItemStack(Item.bucketWater);
                }
                if (fe.getBlockMaterial(blockX, blockY, blockZ) == Material.LAVA && fe.getBlockMetadata(blockX, blockY, blockZ) == 0) {
                    fe.setBlockWithNotify(blockX, blockY, blockZ, 0);
                    return new ItemStack(Item.bucketLava);
                }
            } else {
                if (rayTraceBlocks_do_do.sideHit == 0) {
                    --blockY;
                }
                if (rayTraceBlocks_do_do.sideHit == 1) {
                    ++blockY;
                }
                if (rayTraceBlocks_do_do.sideHit == 2) {
                    --blockZ;
                }
                if (rayTraceBlocks_do_do.sideHit == 3) {
                    ++blockZ;
                }
                if (rayTraceBlocks_do_do.sideHit == 4) {
                    --blockX;
                }
                if (rayTraceBlocks_do_do.sideHit == 5) {
                    ++blockX;
                }
                if (fe.getBlockId(blockX, blockY, blockZ) == 0 || !fe.getBlockMaterial(blockX, blockY, blockZ).isSolid()) {
                    fe.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, this.isFull, 0);
                    return new ItemStack(Item.bucketEmpty);
                }
            }
        }
        return hw;
    }
}
