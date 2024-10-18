
package net.opencraft.entity;

import static org.joml.Math.*;

import net.opencraft.renderer.Tessellator;
import net.opencraft.world.World;

public class EntitySmokeFX extends EntityFX {

	float smokeParticleScale;

	public EntitySmokeFX(final World fe, final double double2, final double double3, final double double4) {
		this(fe, double2, double3, double4, 1.0f);
	}

	public EntitySmokeFX(final World fe, final double double2, final double double3, final double double4, final float float5) {
		super(fe, double2, double3, double4, 0.0, 0.0, 0.0);
		this.xd *= 0.10000000149011612;
		this.yd *= 0.10000000149011612;
		this.zd *= 0.10000000149011612;
		final float particleRed = (float) (random() * 0.30000001192092896);
		this.particleBlue = particleRed;
		this.particleGreen = particleRed;
		this.particleRed = particleRed;
		this.particleScale *= 0.75f;
		this.particleScale *= float5;
		this.smokeParticleScale = this.particleScale;
		this.particleMaxAge = (int) (8.0 / (random() * 0.8 + 0.2));
		this.particleMaxAge *= (int) float5;
		this.noClip = false;
	}

	@Override
	public void renderParticle(final Tessellator ag, final float float2, final float float3, final float float4, final float float5, final float float6, final float float7) {
		float n = (this.particleAge + float2) / this.particleMaxAge * 32.0f;
		if (n < 0.0f) {
			n = 0.0f;
		}
		if (n > 1.0f) {
			n = 1.0f;
		}
		this.particleScale = this.smokeParticleScale * n;
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
		this.particleTextureIndex = 7 - this.particleAge * 8 / this.particleMaxAge;
		this.yd += 0.004;
		this.moveEntity(this.xd, this.yd, this.zd);
		if (this.y == this.yo) {
			this.xd *= 1.1;
			this.zd *= 1.1;
		}
		this.xd *= 0.9599999785423279;
		this.yd *= 0.9599999785423279;
		this.zd *= 0.9599999785423279;
		if (this.onGround) {
			this.xd *= 0.699999988079071;
			this.zd *= 0.699999988079071;
		}
	}

}
