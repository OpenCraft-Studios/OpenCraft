
package net.opencraft.world;

import net.opencraft.EnumSkyBlock;
import net.opencraft.blocks.Block;

public class MetadataChunkBlock {

    public final EnumSkyBlock field_1299_a;
    public int field_1298_b;
    public int field_1304_c;
    public int field_1303_d;
    public int field_1302_e;
    public int field_1301_f;
    public int field_1300_g;

    public MetadataChunkBlock(final EnumSkyBlock ec, final int integer2, final int integer3, final int integer4, final int integer5, final int integer6, final int integer7) {
        this.field_1299_a = ec;
        this.field_1298_b = integer2;
        this.field_1304_c = integer3;
        this.field_1303_d = integer4;
        this.field_1302_e = integer5;
        this.field_1301_f = integer6;
        this.field_1300_g = integer7;
    }

    public void updateLightingChunk(final World fe) {
        for (int i = this.field_1298_b; i <= this.field_1302_e; ++i) {
            for (int j = this.field_1303_d; j <= this.field_1300_g; ++j) {
                if (fe.blockExists(i, 0, j)) {
                    for (int k = this.field_1304_c; k <= this.field_1301_f; ++k) {
                        if (k >= 0) {
                            if (k < 128) {
                                final int savedLightValue = fe.getSavedLightValue(this.field_1299_a, i, k, j);
                                final int blockId = fe.getBlockId(i, k, j);
                                int n = Block.lightOpacity[blockId];
                                if (n == 0) {
                                    n = 1;
                                }
                                int n2 = 0;
                                if (this.field_1299_a == EnumSkyBlock.Sky) {
                                    if (fe.canExistingBlockSeeTheSky(i, k, j)) {
                                        n2 = 15;
                                    }
                                } else if (this.field_1299_a == EnumSkyBlock.Block) {
                                    n2 = Block.lightValue[blockId];
                                }
                                int nya4;
                                if (n >= 15 && n2 == 0) {
                                    nya4 = 0;
                                } else {
                                    final int savedLightValue2 = fe.getSavedLightValue(this.field_1299_a, i - 1, k, j);
                                    final int savedLightValue3 = fe.getSavedLightValue(this.field_1299_a, i + 1, k, j);
                                    final int savedLightValue4 = fe.getSavedLightValue(this.field_1299_a, i, k - 1, j);
                                    final int savedLightValue5 = fe.getSavedLightValue(this.field_1299_a, i, k + 1, j);
                                    final int savedLightValue6 = fe.getSavedLightValue(this.field_1299_a, i, k, j - 1);
                                    final int savedLightValue7 = fe.getSavedLightValue(this.field_1299_a, i, k, j + 1);
                                    nya4 = savedLightValue2;
                                    if (savedLightValue3 > nya4) {
                                        nya4 = savedLightValue3;
                                    }
                                    if (savedLightValue4 > nya4) {
                                        nya4 = savedLightValue4;
                                    }
                                    if (savedLightValue5 > nya4) {
                                        nya4 = savedLightValue5;
                                    }
                                    if (savedLightValue6 > nya4) {
                                        nya4 = savedLightValue6;
                                    }
                                    if (savedLightValue7 > nya4) {
                                        nya4 = savedLightValue7;
                                    }
                                    nya4 -= n;
                                    if (nya4 < 0) {
                                        nya4 = 0;
                                    }
                                    if (n2 > nya4) {
                                        nya4 = n2;
                                    }
                                }
                                if (savedLightValue != nya4) {
                                    fe.setLightValue(this.field_1299_a, i, k, j, nya4);
                                    int savedLightValue2 = nya4 - 1;
                                    if (savedLightValue2 < 0) {
                                        savedLightValue2 = 0;
                                    }
                                    fe.neighborLightPropagationChanged(this.field_1299_a, i - 1, k, j, savedLightValue2);
                                    fe.neighborLightPropagationChanged(this.field_1299_a, i, k - 1, j, savedLightValue2);
                                    fe.neighborLightPropagationChanged(this.field_1299_a, i, k, j - 1, savedLightValue2);
                                    if (i + 1 >= this.field_1302_e) {
                                        fe.neighborLightPropagationChanged(this.field_1299_a, i + 1, k, j, savedLightValue2);
                                    }
                                    if (k + 1 >= this.field_1301_f) {
                                        fe.neighborLightPropagationChanged(this.field_1299_a, i, k + 1, j, savedLightValue2);
                                    }
                                    if (j + 1 >= this.field_1300_g) {
                                        fe.neighborLightPropagationChanged(this.field_1299_a, i, k, j + 1, savedLightValue2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean func_866_a(final int integer1, final int integer2, final int integer3, final int integer4, final int integer5, final int integer6) {
        if (integer1 >= this.field_1298_b && integer2 >= this.field_1304_c && integer3 >= this.field_1303_d && integer4 <= this.field_1302_e && integer5 <= this.field_1301_f && integer6 <= this.field_1300_g) {
            return true;
        }
        final int n = 1;
        if (integer1 >= this.field_1298_b - n && integer2 >= this.field_1304_c - n && integer3 >= this.field_1303_d - n && integer4 <= this.field_1302_e + n && integer5 <= this.field_1301_f + n && integer6 <= this.field_1300_g + n) {
            if (integer1 < this.field_1298_b) {
                this.field_1298_b = integer1;
            }
            if (integer2 < this.field_1304_c) {
                this.field_1304_c = integer2;
            }
            if (integer3 < this.field_1303_d) {
                this.field_1303_d = integer3;
            }
            if (integer4 > this.field_1302_e) {
                this.field_1302_e = integer4;
            }
            if (integer5 > this.field_1301_f) {
                this.field_1301_f = integer5;
            }
            if (integer6 > this.field_1300_g) {
                this.field_1300_g = integer6;
            }
            return true;
        }
        return false;
    }
}
