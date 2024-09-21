
package net.opencraft.entity;

import net.opencraft.block.Block;
import net.opencraft.client.renderer.Tessellator;
import net.opencraft.world.World;

public class EntityDiggingFX extends EntityFX {

    public EntityDiggingFX(final World fe, final double double2, final double double3, final double double4, final double double5, final double double6, final double double7, final Block gs) {
        super(fe, double2, double3, double4, double5, double6, double7);
        this.particleTextureIndex = gs.blockIndexInTexture;
        this.particleGravity = gs.blockParticleGravity;
        final float particleRed = 0.6f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleScale /= 2.0f;
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void renderParticle(final Tessellator ag, final float float2, final float float3, final float float4, final float float5, final float float6, final float float7) {
        final float n = (this.particleTextureIndex % 16 + this.particleTextureJitterX / 4.0f) / 16.0f;
        final float n2 = n + 0.015609375f;
        final float n3 = (this.particleTextureIndex / 16 + this.particleTextureJitterY / 4.0f) / 16.0f;
        final float n4 = n3 + 0.015609375f;
        final float n5 = 0.1f * this.particleScale;
        final float n6 = (float) (this.prevPosX + (this.posX - this.prevPosX) * float2 - EntityDiggingFX.interpPosX);
        final float n7 = (float) (this.prevPosY + (this.posY - this.prevPosY) * float2 - EntityDiggingFX.interpPosY);
        final float n8 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * float2 - EntityDiggingFX.interpPosZ);
        final float entityBrightness = this.getEntityBrightness(float2);
        ag.setColorOpaque_F(entityBrightness * this.particleRed, entityBrightness * this.particleGreen, entityBrightness * this.particleBlue);
        ag.addVertexWithUV(n6 - float3 * n5 - float6 * n5, n7 - float4 * n5, n8 - float5 * n5 - float7 * n5, n, n4);
        ag.addVertexWithUV(n6 - float3 * n5 + float6 * n5, n7 + float4 * n5, n8 - float5 * n5 + float7 * n5, n, n3);
        ag.addVertexWithUV(n6 + float3 * n5 + float6 * n5, n7 + float4 * n5, n8 + float5 * n5 + float7 * n5, n2, n3);
        ag.addVertexWithUV(n6 + float3 * n5 - float6 * n5, n7 - float4 * n5, n8 + float5 * n5 - float7 * n5, n2, n4);
    }
}
