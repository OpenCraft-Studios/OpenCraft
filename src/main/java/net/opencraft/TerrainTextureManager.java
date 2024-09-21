
package net.opencraft;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import net.opencraft.block.Block;
import net.opencraft.block.material.Material;
import net.opencraft.world.World;

public class TerrainTextureManager {

    private float[] field_1181_a;
    private int[] field_1180_b;
    private int[] field_1186_c;
    private int[] field_1185_d;
    private int[] field_1184_e;
    private int[] field_1183_f;
    private int[] field_1182_g;

    public TerrainTextureManager() {
        this.field_1181_a = new float[768];
        this.field_1180_b = new int[5120];
        this.field_1186_c = new int[5120];
        this.field_1185_d = new int[5120];
        this.field_1184_e = new int[5120];
        this.field_1183_f = new int[34];
        this.field_1182_g = new int[768];
        try {
            final BufferedImage read = ImageIO.read(TerrainTextureManager.class.getResource("/assets/terrain.png"));
            final int[] array = new int[65536];
            read.getRGB(0, 0, 256, 256, array, 0, 256);
            for (int i = 0; i < 256; ++i) {
                int n = 0;
                int n2 = 0;
                int n3 = 0;
                final int n4 = i % 16 * 16;
                final int n5 = i / 16 * 16;
                int n6 = 0;
                for (int j = 0; j < 16; ++j) {
                    for (int k = 0; k < 16; ++k) {
                        final int n7 = array[k + n4 + (j + n5) * 256];
                        if ((n7 >> 24 & 0xFF) > 128) {
                            n += (n7 >> 16 & 0xFF);
                            n2 += (n7 >> 8 & 0xFF);
                            n3 += (n7 & 0xFF);
                            ++n6;
                        }
                    }
                    if (n6 == 0) {
                        ++n6;
                    }
                    this.field_1181_a[i * 3 + 0] = (n / n6);
                    this.field_1181_a[i * 3 + 1] = (n2 / n6);
                    this.field_1181_a[i * 3 + 2] = (n3 / n6);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        for (int l = 0; l < 256; ++l) {
            if (Block.blocksList[l] != null) {
                this.field_1182_g[l * 3 + 0] = Block.blocksList[l].getBlockTextureFromSide(1);
                this.field_1182_g[l * 3 + 1] = Block.blocksList[l].getBlockTextureFromSide(2);
                this.field_1182_g[l * 3 + 2] = Block.blocksList[l].getBlockTextureFromSide(3);
            }
        }
    }

    public void func_799_a(final IsoImageBuffer di) {
        final World worldObj = di.worldObj;
        if (worldObj == null) {
            di.field_1351_f = true;
            di.field_1352_e = true;
            return;
        }
        final int n = di.chunkX * 16;
        final int n2 = di.chunkZ * 16;
        final int n3 = n + 16;
        final int n4 = n2 + 16;
        if (worldObj.getChunkFromChunkCoords(di.chunkX, di.chunkZ).q) {
            di.field_1351_f = true;
            di.field_1352_e = true;
            return;
        }
        di.field_1351_f = false;
        Arrays.fill(this.field_1186_c, 0);
        Arrays.fill(this.field_1185_d, 0);
        Arrays.fill(this.field_1183_f, 160);
        for (int i = n4 - 1; i >= n2; --i) {
            for (int j = n3 - 1; j >= n; --j) {
                final int n5 = j - n;
                final int n6 = i - n2;
                final int n7 = n5 + n6;
                boolean b = true;
                for (int k = 0; k < 128; ++k) {
                    final int n8 = n6 - n5 - k + 160 - 16;
                    if (n8 < this.field_1183_f[n7] || n8 < this.field_1183_f[n7 + 1]) {
                        final Block block = Block.blocksList[worldObj.getBlockId(j, k, i)];
                        if (block == null) {
                            b = false;
                        } else if (block.blockMaterial == Material.WATER) {
                            final int blockId = worldObj.getBlockId(j, k + 1, i);
                            if (blockId == 0 || Block.blocksList[blockId].blockMaterial != Material.WATER) {
                                final float n9 = worldObj.getLightBrightness(j, k + 1, i) * (k / 127.0f * 0.6f + 0.4f);
                                if (n8 >= 0) {
                                    if (n8 < 160) {
                                        final int n10 = n7 + n8 * 32;
                                        if (n7 >= 0 && n7 <= 32 && this.field_1185_d[n10] <= k) {
                                            this.field_1185_d[n10] = k;
                                            this.field_1184_e[n10] = (int) (n9 * 127.0f);
                                        }
                                        if (n7 >= -1 && n7 <= 31 && this.field_1185_d[n10 + 1] <= k) {
                                            this.field_1185_d[n10 + 1] = k;
                                            this.field_1184_e[n10 + 1] = (int) (n9 * 127.0f);
                                        }
                                        b = false;
                                    }
                                }
                            }
                        } else {
                            if (b) {
                                if (n8 < this.field_1183_f[n7]) {
                                    this.field_1183_f[n7] = n8;
                                }
                                if (n8 < this.field_1183_f[n7 + 1]) {
                                    this.field_1183_f[n7 + 1] = n8;
                                }
                            }
                            final float n11 = k / 127.0f * 0.6f + 0.4f;
                            if (n8 >= 0 && n8 < 160) {
                                final int n12 = n7 + n8 * 32;
                                final int n13 = this.field_1182_g[block.blockID * 3 + 0];
                                final float n14 = (worldObj.getLightBrightness(j, k + 1, i) * 0.8f + 0.2f) * n11;
                                final int n15 = n13;
                                if (n7 >= 0) {
                                    final float n16 = n14;
                                    if (this.field_1186_c[n12] <= k) {
                                        this.field_1186_c[n12] = k;
                                        this.field_1180_b[n12] = (0xFF000000 | (int) (this.field_1181_a[n15 * 3 + 0] * n16) << 16 | (int) (this.field_1181_a[n15 * 3 + 1] * n16) << 8 | (int) (this.field_1181_a[n15 * 3 + 2] * n16));
                                    }
                                }
                                if (n7 < 31) {
                                    final float n16 = n14 * 0.9f;
                                    if (this.field_1186_c[n12 + 1] <= k) {
                                        this.field_1186_c[n12 + 1] = k;
                                        this.field_1180_b[n12 + 1] = (0xFF000000 | (int) (this.field_1181_a[n15 * 3 + 0] * n16) << 16 | (int) (this.field_1181_a[n15 * 3 + 1] * n16) << 8 | (int) (this.field_1181_a[n15 * 3 + 2] * n16));
                                    }
                                }
                            }
                            if (n8 >= -1 && n8 < 159) {
                                final int n12 = n7 + (n8 + 1) * 32;
                                final int n13 = this.field_1182_g[block.blockID * 3 + 1];
                                final float n14 = worldObj.getLightBrightness(j - 1, k, i) * 0.8f + 0.2f;
                                final int n15 = this.field_1182_g[block.blockID * 3 + 2];
                                final float n16 = worldObj.getLightBrightness(j, k, i + 1) * 0.8f + 0.2f;
                                if (n7 >= 0) {
                                    final float n17 = n14 * n11 * 0.6f;
                                    if (this.field_1186_c[n12] <= k - 1) {
                                        this.field_1186_c[n12] = k - 1;
                                        this.field_1180_b[n12] = (0xFF000000 | (int) (this.field_1181_a[n13 * 3 + 0] * n17) << 16 | (int) (this.field_1181_a[n13 * 3 + 1] * n17) << 8 | (int) (this.field_1181_a[n13 * 3 + 2] * n17));
                                    }
                                }
                                if (n7 < 31) {
                                    final float n17 = n16 * 0.9f * n11 * 0.4f;
                                    if (this.field_1186_c[n12 + 1] <= k - 1) {
                                        this.field_1186_c[n12 + 1] = k - 1;
                                        this.field_1180_b[n12 + 1] = (0xFF000000 | (int) (this.field_1181_a[n15 * 3 + 0] * n17) << 16 | (int) (this.field_1181_a[n15 * 3 + 1] * n17) << 8 | (int) (this.field_1181_a[n15 * 3 + 2] * n17));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        this.func_800_a();
        if (di.field_1348_a == null) {
            di.field_1348_a = new BufferedImage(32, 160, 2);
        }
        di.field_1348_a.setRGB(0, 0, 32, 160, this.field_1180_b, 0, 32);
        di.field_1352_e = true;
    }

    private void func_800_a() {
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 160; ++j) {
                final int n = i + j * 32;
                if (this.field_1186_c[n] == 0) {
                    this.field_1180_b[n] = 0;
                }
                if (this.field_1185_d[n] > this.field_1186_c[n]) {
                    final int n2 = this.field_1180_b[n] >> 24 & 0xFF;
                    this.field_1180_b[n] = ((this.field_1180_b[n] & 0xFEFEFE) >> 1) + this.field_1184_e[n];
                    if (n2 < 128) {
                        this.field_1180_b[n] = Integer.MIN_VALUE + this.field_1184_e[n] * 2;
                    } else {
                        final int[] field_1180_b = this.field_1180_b;
                        final int n3 = n;
                        field_1180_b[n3] |= 0xFF000000;
                    }
                }
            }
        }
    }
}
