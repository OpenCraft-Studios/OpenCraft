
package net.opencraft.entity;

import static org.joml.Math.*;

import net.opencraft.renderer.Tessellator;
import net.opencraft.world.World;

public class EntityLavaFX extends EntityFX {

	private float lavaParticleScale;

	public EntityLavaFX(final World fe, final double double2, final double double3, final double double4) {
		super(fe, double2, double3, double4, 0.0, 0.0, 0.0);
		this.xd *= 0.800000011920929;
		this.yd *= 0.800000011920929;
		this.zd *= 0.800000011920929;
		this.yd = this.rand.nextFloat() * 0.4f + 0.05f;
		final float particleRed = 1.0f;
		this.particleBlue = particleRed;
		this.particleGreen = particleRed;
		this.particleRed = particleRed;
		this.particleScale *= this.rand.nextFloat() * 2.0f + 0.2f;
		this.lavaParticleScale = this.particleScale;
		this.particleMaxAge = (int) (16.0 / (random() * 0.8 + 0.2));
		this.noClip = false;
		this.particleTextureIndex = 49;
	}

	@Override
	public float getEntityBrightness(final float float1) {
		return 1.0f;
	}

	@Override
	public void renderParticle(final Tessellator ag, final float float2, final float float3, final float float4, final float float5, final float float6, final float float7) {
		final float n = (this.particleAge + float2) / this.particleMaxAge;
		this.particleScale = this.lavaParticleScale * (1.0f - n * n);
		super.renderParticle(ag, float2, float3, float4, float5, float6, float7);
	}

	@Override
	public void onUpdate() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.particleAge++ >= this.particleMaxAge) {
			this.setEntityDead();
		}
		if (this.rand.nextFloat() > this.particleAge / (float) this.particleMaxAge) {
			this.world.spawnParticle("smoke", this.x, this.y, this.z, this.xd, this.yd, this.zd);
		}
		this.yd -= 0.03;
		this.moveEntity(this.xd, this.yd, this.zd);
		this.xd *= 0.9990000128746033;
		this.yd *= 0.9990000128746033;
		this.zd *= 0.9990000128746033;
		if (this.onGround) {
			this.xd *= 0.699999988079071;
			this.zd *= 0.699999988079071;
		}
	}

}
