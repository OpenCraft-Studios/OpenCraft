
package net.opencraft.entity;

import static org.joml.Math.*;

import net.opencraft.blocks.material.Material;
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
		this.xd = double5 * 0.20000000298023224 + (float) (random() * 2.0 - 1.0) * 0.02f;
		this.yd = double6 * 0.20000000298023224 + (float) (random() * 2.0 - 1.0) * 0.02f;
		this.zd = double7 * 0.20000000298023224 + (float) (random() * 2.0 - 1.0) * 0.02f;
		this.particleMaxAge = (int) (8.0 / (random() * 0.8 + 0.2));
	}

	@Override
	public void onUpdate() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		this.yd += 0.002;
		this.moveEntity(this.xd, this.yd, this.zd);
		this.xd *= 0.8500000238418579;
		this.yd *= 0.8500000238418579;
		this.zd *= 0.8500000238418579;
		if (this.world.getBlockMaterial(Mth.floor_double(this.x), Mth.floor_double(this.y), Mth.floor_double(this.z)) != Material.WATER) {
			this.setEntityDead();
		}
		if (this.particleMaxAge-- <= 0) {
			this.setEntityDead();
		}
	}

}
