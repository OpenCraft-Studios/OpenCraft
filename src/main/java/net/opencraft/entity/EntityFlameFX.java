
package net.opencraft.entity;

import static org.joml.Math.*;
import net.opencraft.renderer.Tessellator;
import net.opencraft.world.World;

public class EntityFlameFX extends EntityFX {

	private float flameScale;

	public EntityFlameFX(final World fe, double double2, double double3, double double4, final double double5, final double double6, final double double7) {
		super(fe, double2, double3, double4, double5, double6, double7);
		this.xd = this.xd * 0.009999999776482582 + double5;
		this.yd = this.yd * 0.009999999776482582 + double6;
		this.zd = this.zd * 0.009999999776482582 + double7;
		double2 += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f;
		double3 += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f;
		double4 += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f;
		this.flameScale = this.particleScale;
		final float particleRed = 1.0f;
		this.particleBlue = particleRed;
		this.particleGreen = particleRed;
		this.particleRed = particleRed;
		this.particleMaxAge = (int) (8.0 / (random() * 0.8 + 0.2)) + 4;
		this.noClip = true;
		this.particleTextureIndex = 48;
	}

	@Override
	public void renderParticle(final Tessellator ag, final float float2, final float float3, final float float4, final float float5, final float float6, final float float7) {
		final float n = (this.particleAge + float2) / this.particleMaxAge;
		this.particleScale = this.flameScale * (1.0f - n * n * 0.5f);
		super.renderParticle(ag, float2, float3, float4, float5, float6, float7);
	}

	@Override
	public float getEntityBrightness(final float float1) {
		float n = (this.particleAge + float1) / this.particleMaxAge;
		if (n < 0.0f) {
			n = 0.0f;
		}
		if (n > 1.0f) {
			n = 1.0f;
		}
		return super.getEntityBrightness(float1) * n + (1.0f - n);
	}

	@Override
	public void onUpdate() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.particleAge++ >= this.particleMaxAge) {
			this.setEntityDead();
		}
		this.moveEntity(this.xd, this.yd, this.zd);
		this.xd *= 0.9599999785423279;
		this.yd *= 0.9599999785423279;
		this.zd *= 0.9599999785423279;
		if (this.onGround) {
			this.xd *= 0.699999988079071;
			this.zd *= 0.699999988079071;
		}
	}

}
