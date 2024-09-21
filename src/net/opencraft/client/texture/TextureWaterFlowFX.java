
package net.opencraft.client.texture;

import net.opencraft.block.Block;

public class TextureWaterFlowFX extends TextureFX {

    protected float[] field_1138_g;
    protected float[] field_1137_h;
    protected float[] field_1136_i;
    protected float[] field_1135_j;
    private int field_1134_k;

    public TextureWaterFlowFX() {
        super(Block.waterMoving.blockIndexInTexture + 1);
        this.field_1138_g = new float[256];
        this.field_1137_h = new float[256];
        this.field_1136_i = new float[256];
        this.field_1135_j = new float[256];
        this.field_1134_k = 0;
        this.tileSize = 2;
    }

    @Override
    public void onTick() {
        ++this.field_1134_k;
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                float n = 0.0f;
                for (int k = j - 2; k <= j; ++k) {
                    final int n2 = i & 0xF;
                    final int n3 = k & 0xF;
                    n += this.field_1138_g[n2 + n3 * 16];
                }
                this.field_1137_h[i + j * 16] = n / 3.2f + this.field_1136_i[i + j * 16] * 0.8f;
            }
        }
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                final float[] field_1136_i = this.field_1136_i;
                final int n4 = i + j * 16;
                field_1136_i[n4] += this.field_1135_j[i + j * 16] * 0.05f;
                if (this.field_1136_i[i + j * 16] < 0.0f) {
                    this.field_1136_i[i + j * 16] = 0.0f;
                }
                final float[] field_1135_j = this.field_1135_j;
                final int n5 = i + j * 16;
                field_1135_j[n5] -= 0.3f;
                if (Math.random() < 0.2) {
                    this.field_1135_j[i + j * 16] = 0.5f;
                }
            }
        }
        final float[] field_1137_h = this.field_1137_h;
        this.field_1137_h = this.field_1138_g;
        this.field_1138_g = field_1137_h;
        for (int j = 0; j < 256; ++j) {
            float n = this.field_1138_g[j - this.field_1134_k * 16 & 0xFF];
            if (n > 1.0f) {
                n = 1.0f;
            }
            if (n < 0.0f) {
                n = 0.0f;
            }
            final float n6 = n * n;
            int n2 = (int) (32.0f + n6 * 32.0f);
            int n3 = (int) (50.0f + n6 * 64.0f);
            int n7 = 255;
            final int n8 = (int) (146.0f + n6 * 50.0f);
            if (this.anaglyphEnabled) {
                final int n9 = (n2 * 30 + n3 * 59 + n7 * 11) / 100;
                final int n10 = (n2 * 30 + n3 * 70) / 100;
                final int n11 = (n2 * 30 + n7 * 70) / 100;
                n2 = n9;
                n3 = n10;
                n7 = n11;
            }
            this.imageData[j * 4 + 0] = (byte) n2;
            this.imageData[j * 4 + 1] = (byte) n3;
            this.imageData[j * 4 + 2] = (byte) n7;
            this.imageData[j * 4 + 3] = (byte) n8;
        }
    }
}
