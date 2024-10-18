
package net.opencraft.entity;

import static org.joml.Math.*;

import net.opencraft.renderer.Tessellator;
import net.opencraft.world.World;

public class EntityExplodeFX extends EntityFX {

	public EntityExplodeFX(final World fe, final double double2, final double double3, final double double4, final double double5, final double double6, final double double7) {
		super(fe, double2, double3, double4, double5, double6, double7);
		this.xd = double5 + (float) (random() * 2.0 - 1.0) * 0.05f;
		this.yd = double6 + (float) (random() * 2.0 - 1.0) * 0.05f;
		this.zd = double7 + (float) (random() * 2.0 - 1.0) * 0.05f;
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
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.particleAge++ >= this.particleMaxAge) {
			this.setEntityDead();
		}
		this.particleTextureIndex = 7 - this.particleAge * 8 / this.particleMaxAge;
		this.yd += 0.004;
		this.moveEntity(this.xd, this.yd, this.zd);
		this.xd *= 0.8999999761581421;
		this.yd *= 0.8999999761581421;
		this.zd *= 0.8999999761581421;
		if (this.onGround) {
			this.xd *= 0.699999988079071;
			this.zd *= 0.699999988079071;
		}
	}

}
