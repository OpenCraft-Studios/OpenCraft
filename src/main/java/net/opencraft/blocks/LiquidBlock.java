
package net.opencraft.blocks;

import static org.joml.Math.*;

import java.util.Random;

import net.opencraft.blocks.material.Material;
import net.opencraft.entity.Entity;
import net.opencraft.physics.AABB;
import net.opencraft.util.Vec3;
import net.opencraft.world.IBlockAccess;
import net.opencraft.world.World;

public abstract class LiquidBlock extends Block {

	protected int unsure;

	protected LiquidBlock(final int blockid, final Material material) {
		super(blockid, ((material == Material.LAVA) ? 14 : 12) * 16 + 13, material);
		this.unsure = 1;
		final float n = 0.0f;
		final float n2 = 0.0f;
		if(material == Material.LAVA) {
			this.unsure = 2;
		}
		this.setShape(0.0f + n2, 0.0f + n, 0.0f + n2, 1.0f + n2, 1.0f + n, 1.0f + n2);
		this.setTickOnLoad(true);
	}

	@Override
	public int getBlockTextureFromSide(final int textureIndexSlot) {
		if(textureIndexSlot == 0 || textureIndexSlot == 1) {
			return this.blockIndexInTexture;
		}
		return this.blockIndexInTexture + 1;
	}

	protected int getFlowDecay(final World world, final int xCoord, final int yCoord, final int zCoord) {
		if(world.getBlockMaterial(xCoord, yCoord, zCoord) != this.blockMaterial) {
			return -1;
		}
		return world.getBlockMetadata(xCoord, yCoord, zCoord);
	}

	protected int getEffectiveFlowDecay(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord) {
		if(blockAccess.getBlockMaterial(xCoord, yCoord, zCoord) != this.blockMaterial) {
			return -1;
		}
		int blockMetadata = blockAccess.getBlockMetadata(xCoord, yCoord, zCoord);
		if(blockMetadata >= 8) {
			blockMetadata = 0;
		}
		return blockMetadata;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean canCollideCheck(final int nya1, final boolean boolean2) {
		return boolean2 && nya1 == 0;
	}

	@Override
	public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
		return blockAccess.getBlockMaterial(xCoord, yCoord, zCoord) != this.blockMaterial && (nya4 == 1 || super.shouldSideBeRendered(blockAccess, xCoord, yCoord, zCoord, nya4));
	}

	@Override
	public AABB getCollisionBoundingBoxFromPool(final World world, final int xCoord, final int yCoord, final int zCoord) {
		return null;
	}

	@Override
	public int getRenderType() {
		return 4;
	}

	@Override
	public int idDropped(final int blockid, final Random random) {
		return 0;
	}

	@Override
	public int quantityDropped(final Random random) {
		return 0;
	}

	private Vec3 getFlowVector(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord) {
		Vec3 vec3D = Vec3.newTemp(0.0, 0.0, 0.0);
		final int effectiveFlowDecay = this.getEffectiveFlowDecay(blockAccess, xCoord, yCoord, zCoord);
		for(int i = 0; i < 4; ++i) {
			int n = xCoord;
			int n2 = zCoord;
			if(i == 0) {
				--n;
			}
			if(i == 1) {
				--n2;
			}
			if(i == 2) {
				++n;
			}
			if(i == 3) {
				++n2;
			}
			int n3 = this.getEffectiveFlowDecay(blockAccess, n, yCoord, n2);
			if(n3 < 0) {
				n3 = this.getEffectiveFlowDecay(blockAccess, n, yCoord - 1, n2);
				if(n3 >= 0) {
					final int n4 = n3 - (effectiveFlowDecay - 8);
					vec3D = vec3D.add((n - xCoord) * n4, (yCoord - yCoord) * n4, (n2 - zCoord) * n4);
				}
			} else if(n3 >= 0) {
				final int n4 = n3 - effectiveFlowDecay;
				vec3D = vec3D.add((n - xCoord) * n4, (yCoord - yCoord) * n4, (n2 - zCoord) * n4);
			}
		}
		if(blockAccess.getBlockMetadata(xCoord, yCoord, zCoord) >= 8) {
			int n5 = 0;
			if(n5 != 0 || this.shouldSideBeRendered(blockAccess, xCoord, yCoord, zCoord - 1, 2)) {
				n5 = 1;
			}
			if(n5 != 0 || this.shouldSideBeRendered(blockAccess, xCoord, yCoord, zCoord + 1, 3)) {
				n5 = 1;
			}
			if(n5 != 0 || this.shouldSideBeRendered(blockAccess, xCoord - 1, yCoord, zCoord, 4)) {
				n5 = 1;
			}
			if(n5 != 0 || this.shouldSideBeRendered(blockAccess, xCoord + 1, yCoord, zCoord, 5)) {
				n5 = 1;
			}
			if(n5 != 0 || this.shouldSideBeRendered(blockAccess, xCoord, yCoord + 1, zCoord - 1, 2)) {
				n5 = 1;
			}
			if(n5 != 0 || this.shouldSideBeRendered(blockAccess, xCoord, yCoord + 1, zCoord + 1, 3)) {
				n5 = 1;
			}
			if(n5 != 0 || this.shouldSideBeRendered(blockAccess, xCoord - 1, yCoord + 1, zCoord, 4)) {
				n5 = 1;
			}
			if(n5 != 0 || this.shouldSideBeRendered(blockAccess, xCoord + 1, yCoord + 1, zCoord, 5)) {
				n5 = 1;
			}
			if(n5 != 0) {
				vec3D = vec3D.normalize().add(0.0, -6.0, 0.0);
			}
		}
		return vec3D.normalize();
	}

	@Override
	public void velocityToAddToEntity(final World world, final int xCoord, final int yCoord, final int zCoord, final Entity entity, final Vec3 var1) {
		final Vec3 flowVector = this.getFlowVector(world, xCoord, yCoord, zCoord);
		var1.x += flowVector.x;
		var1.y += flowVector.y;
		var1.z += flowVector.z;
	}

	@Override
	public int tickRate() {
		if(this.blockMaterial == Material.WATER) {
			return 5;
		}
		if(this.blockMaterial == Material.LAVA) {
			return 30;
		}
		return 0;
	}

	@Override
	public float getBlockBrightness(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord) {
		final float lightBrightness = blockAccess.getLightBrightness(xCoord, yCoord, zCoord);
		final float lightBrightness2 = blockAccess.getLightBrightness(xCoord, yCoord + 1, zCoord);
		return (lightBrightness > lightBrightness2) ? lightBrightness : lightBrightness2;
	}

	@Override
	public void updateTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
		super.updateTick(world, xCoord, yCoord, zCoord, random);
	}

	@Override
	public int getRenderBlockPass() {
		return (this.blockMaterial == Material.WATER) ? 1 : 0;
	}

	@Override
	public void randomDisplayTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
		if(this.blockMaterial == Material.WATER && random.nextInt(64) == 0) {
			final int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
			if(blockMetadata > 0 && blockMetadata < 8) {
				world.playSoundEffect((xCoord + 0.5f), (yCoord + 0.5f), (zCoord + 0.5f), "liquid.water", random.nextFloat() * 0.25f + 0.75f, random.nextFloat() * 1.0f + 0.5f);
			}
		}
		if(this.blockMaterial == Material.LAVA && world.getBlockMaterial(xCoord, yCoord + 1, zCoord) == Material.AIR && !world.isBlockNormalCube(xCoord, yCoord + 1, zCoord) && random.nextInt(100) == 0) {
			world.spawnParticle("lava", (xCoord + random.nextFloat()), yCoord + this.maxY, (zCoord + random.nextFloat()), 0.0, 0.0, 0.0);
		}
	}

	@Override
	public void onBlockAdded(final World world, final int xCoord, final int yCoord, final int zCoord) {
		this.checkForHarden(world, xCoord, yCoord, zCoord);
	}

	@Override
	public void onNeighborBlockChange(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
		this.checkForHarden(world, xCoord, yCoord, zCoord);
	}

	private void checkForHarden(final World world, final int xCoord, final int yCoord, final int zCoord) {
		if(world.getBlockId(xCoord, yCoord, zCoord) != this.blockID) {
			return;
		}
		if(this.blockMaterial == Material.LAVA) {
			int n = 0;
			if(n != 0 || world.getBlockMaterial(xCoord, yCoord, zCoord - 1) == Material.WATER) {
				n = 1;
			}
			if(n != 0 || world.getBlockMaterial(xCoord, yCoord, zCoord + 1) == Material.WATER) {
				n = 1;
			}
			if(n != 0 || world.getBlockMaterial(xCoord - 1, yCoord, zCoord) == Material.WATER) {
				n = 1;
			}
			if(n != 0 || world.getBlockMaterial(xCoord + 1, yCoord, zCoord) == Material.WATER) {
				n = 1;
			}
			if(n != 0 || world.getBlockMaterial(xCoord, yCoord + 1, zCoord) == Material.WATER) {
				n = 1;
			}
			if(n != 0) {
				final int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
				if(blockMetadata == 0) {
					world.setBlockWithNotify(xCoord, yCoord, zCoord, Block.obsidian.blockID);
				} else if(blockMetadata <= 4) {
					world.setBlockWithNotify(xCoord, yCoord, zCoord, Block.cobblestone.blockID);
				}
				this.triggerLavaMixEffects(world, xCoord, yCoord, zCoord);
			}
		}
	}

	protected void triggerLavaMixEffects(final World world, final int xCoord, final int yCoord, final int zCoord) {
		world.playSoundEffect((xCoord + 0.5f), (yCoord + 0.5f), (zCoord + 0.5f), "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
		for(int i = 0; i < 8; ++i) {
			world.spawnParticle("largesmoke", xCoord + random(), yCoord + 1.2, zCoord + random(), 0.0, 0.0, 0.0);
		}
	}

	public static float getPercentAir(int fluidSize) {
		if(fluidSize >= 8) {
			fluidSize = 0;
		}
		return (fluidSize + 1) / 9.0f;
	}

	public static double getFlowDirection(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord, final Material material) {
		Vec3 vec3D = null;
		if(material == Material.WATER) {
			vec3D = ((LiquidBlock) Block.waterMoving).getFlowVector(blockAccess, xCoord, yCoord, zCoord);
		}
		if(material == Material.LAVA) {
			vec3D = ((LiquidBlock) Block.lavaMoving).getFlowVector(blockAccess, xCoord, yCoord, zCoord);
		}
		if(vec3D.x == 0.0 && vec3D.z == 0.0) {
			return -1000.0;
		}
		return atan2(vec3D.z, vec3D.x) - 1.5707963267948966;
	}

}
