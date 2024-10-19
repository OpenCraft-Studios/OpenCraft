
package net.opencraft.entity;

import static org.joml.Math.*;

import net.opencraft.blocks.LiquidBlock;
import net.opencraft.blocks.material.EnumMaterial;
import net.opencraft.renderer.Tessellator;
import net.opencraft.util.Mth;
import net.opencraft.world.World;

public class EntityRainFX extends EntityFX {

	public EntityRainFX(final World fe, final double double2, final double double3, final double double4) {
		super(fe, double2, double3, double4, 0.0, 0.0, 0.0);
		this.xd *= 0.30000001192092896;
		this.yd = (float) random() * 0.2f + 0.1f;
		this.zd *= 0.30000001192092896;
		this.particleRed = 1.0f;
		this.particleGreen = 1.0f;
		this.particleBlue = 1.0f;
		this.particleTextureIndex = 16;
		this.setSize(0.01f, 0.01f);
		this.particleGravity = 0.06f;
		this.particleMaxAge = (int) (8.0 / (random() * 0.8 + 0.2));
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
		this.yd -= this.particleGravity;
		this.moveEntity(this.xd, this.yd, this.zd);
		this.xd *= 0.9800000190734863;
		this.yd *= 0.9800000190734863;
		this.zd *= 0.9800000190734863;
		if (this.particleMaxAge-- <= 0) {
			this.setEntityDead();
		}
		if (this.onGround) {
			if (random() < 0.5) {
				this.setEntityDead();
			}
			this.xd *= 0.699999988079071;
			this.zd *= 0.699999988079071;
		}
		final EnumMaterial blockMaterial = this.world.getBlockMaterial(Mth.floor_double(this.x), Mth.floor_double(this.y), Mth.floor_double(this.z));
		if ((blockMaterial.isLiquid() || blockMaterial.isSolid()) && this.y < Mth.floor_double(this.y) + 1 - LiquidBlock.getPercentAir(this.world.getBlockMetadata(Mth.floor_double(this.x), Mth.floor_double(this.y), Mth.floor_double(this.z)))) {
			this.setEntityDead();
		}
	}

}
