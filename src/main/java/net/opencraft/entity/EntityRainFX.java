
package net.opencraft.entity;

import net.opencraft.block.LiquidBlock;
import net.opencraft.block.material.Material;
import net.opencraft.client.renderer.Tessellator;
import net.opencraft.util.MathHelper;
import net.opencraft.world.World;

public class EntityRainFX extends EntityFX {

    public EntityRainFX(final World fe, final double double2, final double double3, final double double4) {
        super(fe, double2, double3, double4, 0.0, 0.0, 0.0);
        this.motionX *= 0.30000001192092896;
        this.motionY = (float) Math.random() * 0.2f + 0.1f;
        this.motionZ *= 0.30000001192092896;
        this.particleRed = 1.0f;
        this.particleGreen = 1.0f;
        this.particleBlue = 1.0f;
        this.particleTextureIndex = 16;
        this.setSize(0.01f, 0.01f);
        this.particleGravity = 0.06f;
        this.particleMaxAge = (int) (8.0 / (Math.random() * 0.8 + 0.2));
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
        this.motionY -= this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        if (this.particleMaxAge-- <= 0) {
            this.setEntityDead();
        }
        if (this.onGround) {
            if (Math.random() < 0.5) {
                this.setEntityDead();
            }
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
        final Material blockMaterial = this.worldObj.getBlockMaterial(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
        if ((blockMaterial.isLiquid() || blockMaterial.isSolid()) && this.posY < MathHelper.floor_double(this.posY) + 1 - LiquidBlock.getPercentAir(this.worldObj.getBlockMetadata(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)))) {
            this.setEntityDead();
        }
    }
}
