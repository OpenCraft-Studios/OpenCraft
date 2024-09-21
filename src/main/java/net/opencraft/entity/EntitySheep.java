
package net.opencraft.entity;

import net.opencraft.blocks.Block;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.world.World;

public class EntitySheep extends EntityAnimal {

    public boolean sheared;

    public EntitySheep(final World world) {
        super(world);
        this.sheared = false;
        this.texture = "/assets/mob/sheep.png";
        this.setSize(0.9f, 1.3f);
    }

    @Override
    public boolean attackEntityFrom(final Entity entity, final int nya1) {
        if (!this.sheared && entity instanceof EntityLiving) {
            this.sheared = true;
            for (int n = 1 + this.rand.nextInt(3), i = 0; i < n; ++i) {
                final EntityItem entityDropItem;
                final EntityItem entityItem = entityDropItem = this.entityDropItem(Block.woolGray.blockID, 1, 1.0f);
                entityDropItem.motionY += this.rand.nextFloat() * 0.05f;
                final EntityItem entityItem2 = entityItem;
                entityItem2.motionX += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
                final EntityItem entityItem3 = entityItem;
                entityItem3.motionZ += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
            }
        }
        return super.attackEntityFrom(entity, nya1);
    }

    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("Sheared", this.sheared);
    }

    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.sheared = nbtTagCompound.getBoolean("Sheared");
    }

    @Override
    protected String livingSound() {
        return "mob.sheep";
    }

    @Override
    protected String getHurtSound() {
        return "mob.sheep";
    }

    @Override
    protected String getDeathSound() {
        return "mob.sheep";
    }
}
