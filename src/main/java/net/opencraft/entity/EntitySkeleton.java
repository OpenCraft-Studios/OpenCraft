
package net.opencraft.entity;

import net.opencraft.item.Item;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.util.Mth;
import net.opencraft.world.World;

public class EntitySkeleton extends EntityMonster {

    public EntitySkeleton(final World world) {
        super(world);
        this.texture = "/assets/mob/skeleton.png";
    }

    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isDaytime()) {
            final float entityBrightness = this.getEntityBrightness(1.0f);
            if (entityBrightness > 0.5f && this.worldObj.canBlockSeeTheSky(Mth.floor_double(this.posX), Mth.floor_double(this.posY), Mth.floor_double(this.posZ)) && this.rand.nextFloat() * 30.0f < (entityBrightness - 0.4f) * 2.0f) {
                this.fire = 300;
            }
        }
        super.onLivingUpdate();
    }

    @Override
    protected void attackEntity(final Entity entity, final float xCoord) {
        if (xCoord < 10.0f) {
            final double xCoord2 = entity.posX - this.posX;
            final double zCoord = entity.posZ - this.posZ;
            if (this.attackTime == 0) {
                final EntityArrow entityArrow;
                final EntityArrow entity2 = entityArrow = new EntityArrow(this.worldObj, this);
                entityArrow.posY += 1.399999976158142;
                final double n = entity.posY - 0.20000000298023224 - entity2.posY;
                final float n2 = Mth.sqrt_double(xCoord2 * xCoord2 + zCoord * zCoord) * 0.2f;
                this.worldObj.playSoundAtEntity((Entity) this, "random.bow", 1.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.8f));
                this.worldObj.entityJoinedWorld(entity2);
                entity2.shoot(xCoord2, n + n2, zCoord, 0.6f, 12.0f);
                this.attackTime = 30;
            }
            this.rotationYaw = (float) (Math.atan2(zCoord, xCoord2) * 180.0 / 3.1415927410125732) - 90.0f;
            this.hasAttacked = true;
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
        return Item.arrow.shiftedIndex;
    }
}
