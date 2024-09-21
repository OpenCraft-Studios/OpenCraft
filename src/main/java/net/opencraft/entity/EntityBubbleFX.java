
package net.opencraft.entity;

import net.opencraft.block.material.Material;
import net.opencraft.util.Mth;
import net.opencraft.world.World;

public class EntityBubbleFX extends EntityFX {

    public EntityBubbleFX(final World fe, final double double2, final double double3, final double double4, final double double5, final double double6, final double double7) {
        super(fe, double2, double3, double4, double5, double6, double7);
        this.particleRed = 1.0f;
        this.particleGreen = 1.0f;
        this.particleBlue = 1.0f;
        this.particleTextureIndex = 32;
        this.setSize(0.02f, 0.02f);
        this.particleScale *= this.rand.nextFloat() * 0.6f + 0.2f;
        this.motionX = double5 * 0.20000000298023224 + (float) (Math.random() * 2.0 - 1.0) * 0.02f;
        this.motionY = double6 * 0.20000000298023224 + (float) (Math.random() * 2.0 - 1.0) * 0.02f;
        this.motionZ = double7 * 0.20000000298023224 + (float) (Math.random() * 2.0 - 1.0) * 0.02f;
        this.particleMaxAge = (int) (8.0 / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY += 0.002;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.8500000238418579;
        this.motionY *= 0.8500000238418579;
        this.motionZ *= 0.8500000238418579;
        if (this.worldObj.getBlockMaterial(Mth.floor_double(this.posX), Mth.floor_double(this.posY), Mth.floor_double(this.posZ)) != Material.WATER) {
            this.setEntityDead();
        }
        if (this.particleMaxAge-- <= 0) {
            this.setEntityDead();
        }
    }
}
