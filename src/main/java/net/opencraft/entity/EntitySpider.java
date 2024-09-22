
package net.opencraft.entity;

import net.opencraft.item.Item;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.util.Mth;
import net.opencraft.world.World;

public class EntitySpider extends EntityMonster {

    public EntitySpider(final World world) {
        super(world);
        this.texture = "/assets/mob/spider.png";
        this.setSize(1.4f, 0.9f);
        this.moveSpeed = 0.8f;
    }

    @Override
    protected Entity findPlayerToAttack() {
        if (this.getEntityBrightness(1.0f) < 0.5f) {
            final double distanceSqToEntity = this.world.player.getDistanceSqToEntity(this);
            final double n = 16.0;
            if (distanceSqToEntity < n * n) {
                return this.world.player;
            }
        }
        return null;
    }

    @Override
    protected void attackEntity(final Entity entity, final float xCoord) {
        if (this.getEntityBrightness(1.0f) > 0.5f && this.rand.nextInt(100) == 0) {
            this.playerToAttack = null;
            return;
        }
        if (xCoord > 2.0f && xCoord < 6.0f && this.rand.nextInt(10) == 0) {
            if (this.onGround) {
                final double n = entity.posX - this.posX;
                final double n2 = entity.posZ - this.posZ;
                final float sqrt_double = Mth.sqrt_double(n * n + n2 * n2);
                this.motionX = n / sqrt_double * 0.5 * 0.800000011920929 + this.motionX * 0.20000000298023224;
                this.motionZ = n2 / sqrt_double * 0.5 * 0.800000011920929 + this.motionZ * 0.20000000298023224;
                this.motionY = 0.4000000059604645;
            }
        } else {
            super.attackEntity(entity, xCoord);
        }
    }

    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
    }

    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
    }

    @Override
    protected int scoreValue() {
        return Item.silk.shiftedIndex;
    }
}
