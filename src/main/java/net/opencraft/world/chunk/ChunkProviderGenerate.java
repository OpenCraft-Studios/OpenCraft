
package net.opencraft.world.chunk;

import static org.joml.Math.*;

import java.util.Random;

import net.opencraft.blocks.Block;
import net.opencraft.renderer.gui.IProgressUpdate;
import net.opencraft.util.Mth;
import net.opencraft.world.IChunkProvider;
import net.opencraft.world.World;
import net.opencraft.world.gen.NoiseGeneratorOctaves;
import net.opencraft.world.gen.WorldGenDungeons;
import net.opencraft.world.gen.WorldGenFlowers;
import net.opencraft.world.gen.WorldGenLiquids;
import net.opencraft.world.gen.WorldGenMinable;
import net.opencraft.world.gen.WorldGenTrees;

public class ChunkProviderGenerate implements IChunkProvider {

	private Random rand;
	private NoiseGeneratorOctaves field_912_k;
	private NoiseGeneratorOctaves field_911_l;
	private NoiseGeneratorOctaves field_910_m;
	private NoiseGeneratorOctaves field_909_n;
	private NoiseGeneratorOctaves field_908_o;
	private NoiseGeneratorOctaves field_922_a;
	private NoiseGeneratorOctaves field_921_b;
	private NoiseGeneratorOctaves mobSpawnerNoise;
	private World worldObj;
	private double[] field_4180_q;
	double[] field_4185_d;
	double[] field_4184_e;
	double[] field_4183_f;
	double[] field_4182_g;
	double[] field_4181_h;
	int[][] field_914_i;

	public ChunkProviderGenerate(final World fe, final long long2) {
		this.field_914_i = new int[32][32];
		this.worldObj = fe;
		this.rand = new Random(long2);
		this.field_912_k = new NoiseGeneratorOctaves(this.rand, 16);
		this.field_911_l = new NoiseGeneratorOctaves(this.rand, 16);
		this.field_910_m = new NoiseGeneratorOctaves(this.rand, 8);
		this.field_909_n = new NoiseGeneratorOctaves(this.rand, 4);
		this.field_908_o = new NoiseGeneratorOctaves(this.rand, 4);
		this.field_922_a = new NoiseGeneratorOctaves(this.rand, 10);
		this.field_921_b = new NoiseGeneratorOctaves(this.rand, 16);
		this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
	}

	public void generateTerrain(final int integer1, final int integer2, final byte[] arr) {
		final int n = 4;
		final int n2 = 64;
		final int integer3 = n + 1;
		final int integer4 = 17;
		final int integer5 = n + 1;
		this.field_4180_q = this.func_4061_a(this.field_4180_q, integer1 * n, 0, integer2 * n, integer3, integer4,
				integer5);
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				for (int k = 0; k < 16; ++k) {
					final double n3 = 0.125;
					double n4 = this.field_4180_q[((i + 0) * integer5 + (j + 0)) * integer4 + (k + 0)];
					double n5 = this.field_4180_q[((i + 0) * integer5 + (j + 1)) * integer4 + (k + 0)];
					double n6 = this.field_4180_q[((i + 1) * integer5 + (j + 0)) * integer4 + (k + 0)];
					double n7 = this.field_4180_q[((i + 1) * integer5 + (j + 1)) * integer4 + (k + 0)];
					final double n8 = (this.field_4180_q[((i + 0) * integer5 + (j + 0)) * integer4 + (k + 1)] - n4)
							* n3;
					final double n9 = (this.field_4180_q[((i + 0) * integer5 + (j + 1)) * integer4 + (k + 1)] - n5)
							* n3;
					final double n10 = (this.field_4180_q[((i + 1) * integer5 + (j + 0)) * integer4 + (k + 1)] - n6)
							* n3;
					final double n11 = (this.field_4180_q[((i + 1) * integer5 + (j + 1)) * integer4 + (k + 1)] - n7)
							* n3;
					for (int l = 0; l < 8; ++l) {
						final double n12 = 0.25;
						double n13 = n4;
						double n14 = n5;
						final double n15 = (n6 - n4) * n12;
						final double n16 = (n7 - n5) * n12;
						for (int n17 = 0; n17 < 4; ++n17) {
							int n18 = n17 + i * 4 << 11 | 0 + j * 4 << 7 | k * 8 + l;
							final int n19 = 128;
							final double n20 = 0.25;
							double n21 = n13;
							final double n22 = (n14 - n13) * n20;
							for (int n23 = 0; n23 < 4; ++n23) {
								int n24 = 0;
								if (k * 8 + l < n2) {
									n24 = Block.waterStill.blockID;
								}
								if (n21 > 0.0) {
									n24 = Block.stone.blockID;
								}
								arr[n18] = (byte) n24;
								n18 += n19;
								n21 += n22;
							}
							n13 += n15;
							n14 += n16;
						}
						n4 += n8;
						n5 += n9;
						n6 += n10;
						n7 += n11;
					}
				}
			}
		}
	}

	public void replaceBlocks(final int integer1, final int integer2, final byte[] arr) {
		final int n = 64;
		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				final double n2 = integer1 * 16 + i;
				final double n3 = integer2 * 16 + j;
				final double n4 = 0.03125;
				final boolean b = this.field_909_n.a(n2 * n4, n3 * n4, 0.0) + this.rand.nextDouble() * 0.2 > 0.0;
				final boolean b2 = this.field_909_n.a(n3 * n4, 109.0134, n2 * n4) + this.rand.nextDouble() * 0.2 > 3.0;
				final int n5 = (int) (this.field_908_o.a(n2 * n4 * 2.0, n3 * n4 * 2.0) / 3.0 + 3.0
						+ this.rand.nextDouble() * 0.25);
				int n6 = -1;
				int n7 = Block.grass.blockID;
				int n8 = Block.dirt.blockID;
				int n9 = (i * 16 + j) * 128 + 128;
				for (int k = 127; k >= 0; --k) {
					--n9;
					if (k <= 0 + this.rand.nextInt(6) - 1) {
						arr[n9] = (byte) Block.bedrock.blockID;
					} else if (arr[n9] == 0) {
						n6 = -1;
					} else if (arr[n9] == Block.stone.blockID) {
						if (n6 == -1) {
							if (n5 <= 0) {
								n7 = 0;
								n8 = (byte) Block.stone.blockID;
							} else if (k >= n - 4 && k <= n + 1) {
								n7 = Block.grass.blockID;
								n8 = Block.dirt.blockID;
								if (b2) {
									n7 = 0;
								}
								if (b2) {
									n8 = Block.gravel.blockID;
								}
								if (b) {
									n7 = Block.sand.blockID;
								}
								if (b) {
									n8 = Block.sand.blockID;
								}
							}
							if (k < n && n7 == 0) {
								n7 = Block.waterStill.blockID;
							}
							n6 = n5;
							if (k >= n - 1) {
								arr[n9] = (byte) n7;
							} else {
								arr[n9] = (byte) n8;
							}
						} else if (n6 > 0) {
							--n6;
							arr[n9] = (byte) n8;
						}
					}
				}
			}
		}
	}

	public Chunk provideChunk(final int integer1, final int integer2) {
		this.rand.setSeed(integer1 * 341873128712L + integer2 * 132897987541L);
		final byte[] array = new byte[32768];
		final Chunk chunk = new Chunk(this.worldObj, array, integer1, integer2);
		this.generateTerrain(integer1, integer2, array);
		this.replaceBlocks(integer1, integer2, array);
		this.field_902_u(integer1, integer2, array);
		chunk.generateSkylightMap();
		return chunk;
	}

	protected void a(final int integer1, final int integer2, final byte[] arr, final double double4,
			final double double5, final double double6) {
		this.a(integer1, integer2, arr, double4, double5, double6, 1.0f + this.rand.nextFloat() * 6.0f, 0.0f, 0.0f, -1,
				-1, 0.5);
	}

	protected void a(final int integer1, final int integer2, final byte[] arr, double double4, double double5,
			double double6, final float float7, float float8, float float9, int integer10, int integer11,
			final double double12) {
		final double n = integer1 * 16 + 8;
		final double n2 = integer2 * 16 + 8;
		float n3 = 0.0f;
		float n4 = 0.0f;
		final Random random = new Random(this.rand.nextLong());
		if (integer11 <= 0) {
			final int n5 = 112;
			integer11 = n5 - random.nextInt(n5 / 4);
		}
		boolean b = false;
		if (integer10 == -1) {
			integer10 = integer11 / 2;
			b = true;
		}
		final int n6 = random.nextInt(integer11 / 2) + integer11 / 4;
		final boolean b2 = random.nextInt(6) == 0;
		while (integer10 < integer11) {
			final double n7 = 1.5 + sin(integer10 * 3.1415927f / integer11) * float7 * 1.0f;
			final double n8 = n7 * double12;
			final float cos = Mth.cos(float9);
			final float sin = sin(float9);
			double4 += Mth.cos(float8) * cos;
			double5 += sin;
			double6 += sin(float8) * cos;
			if (b2) {
				float9 *= 0.92f;
			} else {
				float9 *= 0.7f;
			}
			float9 += n4 * 0.1f;
			float8 += n3 * 0.1f;
			n4 *= 0.9f;
			n3 *= 0.75f;
			n4 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0f;
			n3 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0f;
			if (!b && integer10 == n6 && float7 > 1.0f) {
				this.a(integer1, integer2, arr, double4, double5, double6, random.nextFloat() * 0.5f + 0.5f,
						float8 - 1.5707964f, float9 / 3.0f, integer10, integer11, 1.0);
				this.a(integer1, integer2, arr, double4, double5, double6, random.nextFloat() * 0.5f + 0.5f,
						float8 + 1.5707964f, float9 / 3.0f, integer10, integer11, 1.0);
				return;
			}
			if (b || random.nextInt(4) != 0) {
				final double n9 = double4 - n;
				final double n10 = double6 - n2;
				final double n11 = integer11 - integer10;
				final double n12 = float7 + 2.0f + 16.0f;
				if (n9 * n9 + n10 * n10 - n11 * n11 > n12 * n12) {
					return;
				}
				if (double4 >= n - 16.0 - n7 * 2.0 && double6 >= n2 - 16.0 - n7 * 2.0
						&& double4 <= n + 16.0 + n7 * 2.0) {
					if (double6 <= n2 + 16.0 + n7 * 2.0) {
						int n13 = Mth.floor_double(double4 - n7) - integer1 * 16 - 1;
						int n14 = Mth.floor_double(double4 + n7) - integer1 * 16 + 1;
						int n15 = Mth.floor_double(double5 - n8) - 1;
						int n16 = Mth.floor_double(double5 + n8) + 1;
						int n17 = Mth.floor_double(double6 - n7) - integer2 * 16 - 1;
						int n18 = Mth.floor_double(double6 + n7) - integer2 * 16 + 1;
						if (n13 < 0) {
							n13 = 0;
						}
						if (n14 > 16) {
							n14 = 16;
						}
						if (n15 < 1) {
							n15 = 1;
						}
						if (n16 > 120) {
							n16 = 120;
						}
						if (n17 < 0) {
							n17 = 0;
						}
						if (n18 > 16) {
							n18 = 16;
						}
						int n19 = 0;
						for (int i = n13; n19 == 0 && i < n14; ++i) {
							for (int n20 = n17; n19 == 0 && n20 < n18; ++n20) {
								for (int n21 = n16 + 1; n19 == 0 && n21 >= n15 - 1; --n21) {
									final int j = (i * 16 + n20) * 128 + n21;
									if (n21 >= 0) {
										if (n21 < 128) {
											if (arr[j] == Block.waterMoving.blockID
													|| arr[j] == Block.waterStill.blockID) {
												n19 = 1;
											}
											if (n21 != n15 - 1 && i != n13 && i != n14 - 1 && n20 != n17
													&& n20 != n18 - 1) {
												n21 = n15;
											}
										}
									}
								}
							}
						}
						if (n19 == 0) {
							for (int i = n13; i < n14; ++i) {
								final double n22 = (i + integer1 * 16 + 0.5 - double4) / n7;
								for (int j = n17; j < n18; ++j) {
									final double n23 = (j + integer2 * 16 + 0.5 - double6) / n7;
									int n24 = (i * 16 + j) * 128 + n16;
									boolean b3 = false;
									for (int k = n16 - 1; k >= n15; --k) {
										final double n25 = (k + 0.5 - double5) / n8;
										if (n25 > -0.7 && n22 * n22 + n25 * n25 + n23 * n23 < 1.0) {
											final byte b4 = arr[n24];
											if (b4 == Block.grass.blockID) {
												b3 = true;
											}
											if (b4 == Block.stone.blockID || b4 == Block.dirt.blockID
													|| b4 == Block.grass.blockID) {
												if (k < 10) {
													arr[n24] = (byte) Block.lavaMoving.blockID;
												} else {
													arr[n24] = 0;
													if (b3 && arr[n24 - 1] == Block.dirt.blockID) {
														arr[n24 - 1] = (byte) Block.grass.blockID;
													}
												}
											}
										}
										--n24;
									}
								}
							}
							if (b) {
								break;
							}
						}
					}
				}
			}
			++integer10;
		}
	}

	private void field_902_u(final int integer1, final int integer2, final byte[] arr) {
		final int n = 8;
		this.rand.setSeed(this.worldObj.n);
		final long n2 = this.rand.nextLong() / 2L * 2L + 1L;
		final long n3 = this.rand.nextLong() / 2L * 2L + 1L;
		for (int i = integer1 - n; i <= integer1 + n; ++i) {
			for (int j = integer2 - n; j <= integer2 + n; ++j) {
				this.rand.setSeed(i * n2 + j * n3 ^ this.worldObj.n);
				int nextInt = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(40) + 1) + 1);
				if (this.rand.nextInt(15) != 0) {
					nextInt = 0;
				}
				for (int k = 0; k < nextInt; ++k) {
					final double n4 = i * 16 + this.rand.nextInt(16);
					final double n5 = this.rand.nextInt(this.rand.nextInt(120) + 8);
					final double n6 = j * 16 + this.rand.nextInt(16);
					int n7 = 1;
					if (this.rand.nextInt(4) == 0) {
						this.a(integer1, integer2, arr, n4, n5, n6);
						n7 += this.rand.nextInt(4);
					}
					for (int l = 0; l < n7; ++l) {
						this.a(integer1, integer2, arr, n4, n5, n6,
								this.rand.nextFloat() * 2.0f + this.rand.nextFloat(),
								this.rand.nextFloat() * 3.1415927f * 2.0f, (this.rand.nextFloat() - 0.5f) * 2.0f / 8.0f,
								0, 0, 1.0);
					}
				}
			}
		}
	}

	private double[] func_4061_a(double[] arr, final int integer2, final int integer3, final int integer4,
			final int integer5, final int integer6, final int integer7) {
		if (arr == null) {
			arr = new double[integer5 * integer6 * integer7];
		}
		final double n = 684.412;
		final double n2 = 684.412;
		this.field_4182_g = this.field_922_a.a(this.field_4182_g, integer2, integer3, integer4, integer5, 1, integer7,
				1.0, 0.0, 1.0);
		this.field_4181_h = this.field_921_b.a(this.field_4181_h, integer2, integer3, integer4, integer5, 1, integer7,
				100.0, 0.0, 100.0);
		this.field_4185_d = this.field_910_m.a(this.field_4185_d, integer2, integer3, integer4, integer5, integer6,
				integer7, n / 80.0, n2 / 160.0, n / 80.0);
		this.field_4184_e = this.field_912_k.a(this.field_4184_e, integer2, integer3, integer4, integer5, integer6,
				integer7, n, n2, n);
		this.field_4183_f = this.field_911_l.a(this.field_4183_f, integer2, integer3, integer4, integer5, integer6,
				integer7, n, n2, n);
		int n3 = 0;
		int n4 = 0;
		for (int i = 0; i < integer5; ++i) {
			for (int j = 0; j < integer7; ++j) {
				double n5 = (this.field_4182_g[n4] + 256.0) / 512.0;
				if (n5 > 1.0) {
					n5 = 1.0;
				}
				final double n6 = 0.0;
				double n7 = this.field_4181_h[n4] / 8000.0;
				if (n7 < 0.0) {
					n7 = -n7;
				}
				n7 = n7 * 3.0 - 3.0;
				if (n7 < 0.0) {
					n7 /= 2.0;
					if (n7 < -1.0) {
						n7 = -1.0;
					}
					n7 /= 1.4;
					n7 /= 2.0;
					n5 = 0.0;
				} else {
					if (n7 > 1.0) {
						n7 = 1.0;
					}
					n7 /= 6.0;
				}
				n5 += 0.5;
				n7 = n7 * integer6 / 16.0;
				final double n8 = integer6 / 2.0 + n7 * 4.0;
				++n4;
				for (int k = 0; k < integer6; ++k) {
					double n9 = 0.0;
					double n10 = (k - n8) * 12.0 / n5;
					if (n10 < 0.0) {
						n10 *= 4.0;
					}
					final double n11 = this.field_4184_e[n3] / 512.0;
					final double n12 = this.field_4183_f[n3] / 512.0;
					final double n13 = (this.field_4185_d[n3] / 10.0 + 1.0) / 2.0;
					if (n13 < 0.0) {
						n9 = n11;
					} else if (n13 > 1.0) {
						n9 = n12;
					} else {
						n9 = n11 + (n12 - n11) * n13;
					}
					n9 -= n10;
					if (k > integer6 - 4) {
						final double n14 = (k - (integer6 - 4)) / 3.0f;
						n9 = n9 * (1.0 - n14) + -10.0 * n14;
					}
					if (k < n6) {
						double n14 = (n6 - k) / 4.0;
						if (n14 < 0.0) {
							n14 = 0.0;
						}
						if (n14 > 1.0) {
							n14 = 1.0;
						}
						n9 = n9 * (1.0 - n14) + -10.0 * n14;
					}
					arr[n3] = n9;
					++n3;
				}
			}
		}
		return arr;
	}

	public boolean chunkExists(final int integer1, final int integer2) {
		return true;
	}

	public void populate(final IChunkProvider ch, final int integer2, final int integer3) {
		final int n = integer2 * 16;
		final int n2 = integer3 * 16;
		this.rand.setSeed(this.worldObj.n);
		this.rand.setSeed(
				integer2 * (this.rand.nextLong() / 2L * 2L + 1L) + integer3 * (this.rand.nextLong() / 2L * 2L + 1L)
						^ this.worldObj.n);
		double n3 = 0.25;
		for (int i = 0; i < 4; ++i) {
			final int integer4 = n + this.rand.nextInt(16) + 8;
			final int j = this.rand.nextInt(128);
			final int integer5 = n2 + this.rand.nextInt(16) + 8;
			new WorldGenDungeons().generate(this.worldObj, this.rand, integer4, j, integer5);
		}
		for (int i = 0; i < 20; ++i) {
			final int integer4 = n + this.rand.nextInt(16);
			final int j = this.rand.nextInt(128);
			final int integer5 = n2 + this.rand.nextInt(16);
			new WorldGenMinable(Block.dirt.blockID, 32).generate(this.worldObj, this.rand, integer4, j, integer5);
		}
		for (int i = 0; i < 10; ++i) {
			final int integer4 = n + this.rand.nextInt(16);
			final int j = this.rand.nextInt(128);
			final int integer5 = n2 + this.rand.nextInt(16);
			new WorldGenMinable(Block.gravel.blockID, 32).generate(this.worldObj, this.rand, integer4, j, integer5);
		}
		for (int i = 0; i < 20; ++i) {
			final int integer4 = n + this.rand.nextInt(16);
			final int j = this.rand.nextInt(128);
			final int integer5 = n2 + this.rand.nextInt(16);
			new WorldGenMinable(Block.oreCoal.blockID, 16).generate(this.worldObj, this.rand, integer4, j, integer5);
		}
		for (int i = 0; i < 20; ++i) {
			final int integer4 = n + this.rand.nextInt(16);
			final int j = this.rand.nextInt(64);
			final int integer5 = n2 + this.rand.nextInt(16);
			new WorldGenMinable(Block.oreIron.blockID, 8).generate(this.worldObj, this.rand, integer4, j, integer5);
		}
		if (this.rand.nextInt(1) == 0) {
			final int i = n + this.rand.nextInt(16);
			final int integer4 = this.rand.nextInt(32);
			final int j = n2 + this.rand.nextInt(16);
			new WorldGenMinable(Block.oreGold.blockID, 8).generate(this.worldObj, this.rand, i, integer4, j);
		}
		if (this.rand.nextInt(4) == 0) {
			final int i = n + this.rand.nextInt(16);
			final int integer4 = this.rand.nextInt(16);
			final int j = n2 + this.rand.nextInt(16);
			new WorldGenMinable(Block.oreDiamond.blockID, 8).generate(this.worldObj, this.rand, i, integer4, j);
		}
		n3 = 0.5;
		int i = (int) ((this.mobSpawnerNoise.a(n * n3, n2 * n3) / 8.0 + this.rand.nextDouble() * 4.0 + 4.0) / 3.0);
		if (i < 0) {
			i = 0;
		}
		final WorldGenTrees worldGenTrees = new WorldGenTrees();
		if (this.rand.nextInt(10) == 0) {
			++i;
		}
		for (int j = 0; j < i; ++j) {
			final int integer5 = n + this.rand.nextInt(16) + 8;
			final int n4 = n2 + this.rand.nextInt(16) + 8;
			worldGenTrees.func_517_a(1.0, 1.0, 1.0);
			worldGenTrees.generate(this.worldObj, this.rand, integer5, this.worldObj.getHeightValue(integer5, n4), n4);
		}
		for (int j = 0; j < 2; ++j) {
			final int integer5 = n + this.rand.nextInt(16) + 8;
			final int n4 = this.rand.nextInt(128);
			final int integer6 = n2 + this.rand.nextInt(16) + 8;
			new WorldGenFlowers(Block.plantYellow.blockID).generate(this.worldObj, this.rand, integer5, n4, integer6);
		}
		if (this.rand.nextInt(2) == 0) {
			final int j = n + this.rand.nextInt(16) + 8;
			final int integer5 = this.rand.nextInt(128);
			final int n4 = n2 + this.rand.nextInt(16) + 8;
			new WorldGenFlowers(Block.plantRed.blockID).generate(this.worldObj, this.rand, j, integer5, n4);
		}
		if (this.rand.nextInt(4) == 0) {
			final int j = n + this.rand.nextInt(16) + 8;
			final int integer5 = this.rand.nextInt(128);
			final int n4 = n2 + this.rand.nextInt(16) + 8;
			new WorldGenFlowers(Block.mushroomBrown.blockID).generate(this.worldObj, this.rand, j, integer5, n4);
		}
		if (this.rand.nextInt(8) == 0) {
			final int j = n + this.rand.nextInt(16) + 8;
			final int integer5 = this.rand.nextInt(128);
			final int n4 = n2 + this.rand.nextInt(16) + 8;
			new WorldGenFlowers(Block.mushroomRed.blockID).generate(this.worldObj, this.rand, j, integer5, n4);
		}
		for (int j = 0; j < 50; ++j) {
			final int integer5 = n + this.rand.nextInt(16) + 8;
			final int n4 = this.rand.nextInt(this.rand.nextInt(120) + 8);
			final int integer6 = n2 + this.rand.nextInt(16) + 8;
			new WorldGenLiquids(Block.waterMoving.blockID).generate(this.worldObj, this.rand, integer5, n4, integer6);
		}
		for (int j = 0; j < 20; ++j) {
			final int integer5 = n + this.rand.nextInt(16) + 8;
			final int n4 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(112) + 8) + 8);
			final int integer6 = n2 + this.rand.nextInt(16) + 8;
			new WorldGenLiquids(Block.lavaMoving.blockID).generate(this.worldObj, this.rand, integer5, n4, integer6);
		}
	}

	public boolean saveChunks(final boolean boolean1, final IProgressUpdate jd) {
		return true;
	}

	public boolean unload100OldestChunks() {
		return false;
	}

	public boolean canSave() {
		return true;
	}
}
