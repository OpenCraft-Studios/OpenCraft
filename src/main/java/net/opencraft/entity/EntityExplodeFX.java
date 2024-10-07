
package net.opencraft.entity;

import static org.joml.Math.*;

import net.opencraft.renderer.Tessellator;
import net.opencraft.world.World;

public class EntityExplodeFX extends EntityFX {

    public EntityExplodeFX(final World fe, final double double2, final double double3, final double double4, final double double5, final double double6, final double double7) {
        super(fe, double2, double3, double4, double5, double6, double7);
        this.motionX = double5 + (float) (random() * 2.0 - 1.0) * 0.05f;
        this.motionY = double6 + (float) (random() * 2.0 - 1.0) * 0.05f;
        this.motionZ = double7 + (float) (random() * 2.0 - 1.0) * 0.05f;
        final float particleRed = this.rand.nextFloat() * 0.3f + 0.7f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleScale = this.rand.nextFloat() * this.rand.nextFloat() * 6.0f + 1.0f;
        this.particleMaxAge = (int) (16.0 / (this.rand.nextFloat() * 0.8 + 0.2)) + 2;
    }

    @Override
    public void renderParticle(final Tessellator ag, final float float2, final float float3, final float float4, final float float5, final float float6, final float float7) {
        super.renderParticle(ag, float2, float3, float4, float5, float6, float7);
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setEntityDead();
        }
        this.particleTextureIndex = 7 - this.particleAge * 8 / this.particleMaxAge;
        this.motionY += 0.004;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.8999999761581421;
        this.motionY *= 0.8999999761581421;
        this.motionZ *= 0.8999999761581421;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
}
