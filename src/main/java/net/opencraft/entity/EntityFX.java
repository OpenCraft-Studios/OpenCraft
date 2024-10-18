
package net.opencraft.entity;

import static org.joml.Math.*;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.renderer.Tessellator;
import net.opencraft.util.Mth;
import net.opencraft.world.World;

public class EntityFX extends Entity {

	protected int particleTextureIndex;
	protected float particleTextureJitterX;
	protected float particleTextureJitterY;
	protected int particleAge;
	protected int particleMaxAge;
	protected float particleScale;
	protected float particleGravity;
	protected float particleRed;
	protected float particleGreen;
	protected float particleBlue;
	public static double interpPosX;
	public static double interpPosY;
	public static double interpPosZ;

	public EntityFX(final World fe, final double double2, final double double3, final double double4, final double double5, final double double6, final double double7) {
		super(fe);
		this.particleAge = 0;
		this.particleMaxAge = 0;
		this.setSize(0.2f, 0.2f);
		this.yOffset = this.height / 2.0f;
		this.setPosition(double2, double3, double4);
		final float particleRed = 1.0f;
		this.particleBlue = particleRed;
		this.particleGreen = particleRed;
		this.particleRed = particleRed;
		this.xd = double5 + (float) (random() * 2.0 - 1.0) * 0.4f;
		this.yd = double6 + (float) (random() * 2.0 - 1.0) * 0.4f;
		this.zd = double7 + (float) (random() * 2.0 - 1.0) * 0.4f;
		final float n = (float) (random() + random() + 1.0) * 0.15f;
		final float sqrt_double = Mth.sqrt_double(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
		this.xd = this.xd / sqrt_double * n * 0.4000000059604645;
		this.yd = this.yd / sqrt_double * n * 0.4000000059604645 + 0.10000000149011612;
		this.zd = this.zd / sqrt_double * n * 0.4000000059604645;
		this.particleTextureJitterX = this.rand.nextFloat() * 3.0f;
		this.particleTextureJitterY = this.rand.nextFloat() * 3.0f;
		this.particleScale = (this.rand.nextFloat() * 0.5f + 0.5f) * 2.0f;
		this.particleMaxAge = (int) (4.0f / (this.rand.nextFloat() * 0.9f + 0.1f));
		this.particleAge = 0;
		this.canTriggerWalking = false;
	}

	public EntityFX multiplyVelocity(final float float1) {
		this.xd *= float1;
		this.yd = (this.yd - 0.10000000149011612) * float1 + 0.10000000149011612;
		this.zd *= float1;
		return this;
	}

	public EntityFX multipleParticleScaleBy(final float float1) {
		this.setSize(0.2f * float1, 0.2f * float1);
		this.particleScale *= float1;
		return this;
	}

	@Override
	public void onUpdate() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.particleAge++ >= this.particleMaxAge) {
			this.setEntityDead();
		}
		this.yd -= 0.04 * this.particleGravity;
		this.moveEntity(this.xd, this.yd, this.zd);
		this.xd *= 0.9800000190734863;
		this.yd *= 0.9800000190734863;
		this.zd *= 0.9800000190734863;
		if (this.onGround) {
			this.xd *= 0.699999988079071;
			this.zd *= 0.699999988079071;
		}
	}

	public void renderParticle(final Tessellator ag, final float float2, final float float3, final float float4, final float float5, final float float6, final float float7) {
		final float n = this.particleTextureIndex % 16 / 16.0f;
		final float n2 = n + 0.0624375f;
		final float n3 = this.particleTextureIndex / 16 / 16.0f;
		final float n4 = n3 + 0.0624375f;
		final float n5 = 0.1f * this.particleScale;
		final float n6 = (float) (this.xo + (this.x - this.xo) * float2 - EntityFX.interpPosX);
		final float n7 = (float) (this.yo + (this.y - this.yo) * float2 - EntityFX.interpPosY);
		final float n8 = (float) (this.zo + (this.z - this.zo) * float2 - EntityFX.interpPosZ);
		final float entityBrightness = this.getEntityBrightness(float2);
		ag.color(this.particleRed * entityBrightness, this.particleGreen * entityBrightness, this.particleBlue * entityBrightness);
		ag.vertexUV(n6 - float3 * n5 - float6 * n5, n7 - float4 * n5, n8 - float5 * n5 - float7 * n5, n, n4);
		ag.vertexUV(n6 - float3 * n5 + float6 * n5, n7 + float4 * n5, n8 - float5 * n5 + float7 * n5, n, n3);
		ag.vertexUV(n6 + float3 * n5 + float6 * n5, n7 + float4 * n5, n8 + float5 * n5 + float7 * n5, n2, n3);
		ag.vertexUV(n6 + float3 * n5 - float6 * n5, n7 - float4 * n5, n8 + float5 * n5 - float7 * n5, n2, n4);
	}

	public int getFXLayer() {
		return 0;
	}

	public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
	}

	public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
	}

}
