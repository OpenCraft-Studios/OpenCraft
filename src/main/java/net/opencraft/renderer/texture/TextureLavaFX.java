
package net.opencraft.renderer.texture;

import net.opencraft.blocks.Block;
import net.opencraft.util.Mth;

public class TextureLavaFX extends TextureFX {

    protected float[] field_1147_g;
    protected float[] field_1146_h;
    protected float[] field_1145_i;
    protected float[] field_1144_j;

    public TextureLavaFX() {
        super(Block.lavaMoving.blockIndexInTexture);
        this.field_1147_g = new float[256];
        this.field_1146_h = new float[256];
        this.field_1145_i = new float[256];
        this.field_1144_j = new float[256];
    }

    @Override
    public void onTick() {
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                float n = 0.0f;
                final int n2 = (int) (Mth.sin(j * 3.1415927f * 2.0f / 16.0f) * 1.2f);
                final int n3 = (int) (Mth.sin(i * 3.1415927f * 2.0f / 16.0f) * 1.2f);
                for (int k = i - 1; k <= i + 1; ++k) {
                    for (int l = j - 1; l <= j + 1; ++l) {
                        final int n4 = k + n2 & 0xF;
                        final int n5 = l + n3 & 0xF;
                        n += this.field_1147_g[n4 + n5 * 16];
                    }
                }
                this.field_1146_h[i + j * 16] = n / 10.0f + (this.field_1145_i[(i + 0 & 0xF) + (j + 0 & 0xF) * 16] + this.field_1145_i[(i + 1 & 0xF) + (j + 0 & 0xF) * 16] + this.field_1145_i[(i + 1 & 0xF) + (j + 1 & 0xF) * 16] + this.field_1145_i[(i + 0 & 0xF) + (j + 1 & 0xF) * 16]) / 4.0f * 0.8f;
                final float[] field_1145_i = this.field_1145_i;
                final int n6 = i + j * 16;
                field_1145_i[n6] += this.field_1144_j[i + j * 16] * 0.01f;
                if (this.field_1145_i[i + j * 16] < 0.0f) {
                    this.field_1145_i[i + j * 16] = 0.0f;
                }
                final float[] field_1144_j = this.field_1144_j;
                final int n7 = i + j * 16;
                field_1144_j[n7] -= 0.06f;
                if (Math.random() < 0.005) {
                    this.field_1144_j[i + j * 16] = 1.5f;
                }
            }
        }
        final float[] field_1146_h = this.field_1146_h;
        this.field_1146_h = this.field_1147_g;
        this.field_1147_g = field_1146_h;
        for (int j = 0; j < 256; ++j) {
            float n = this.field_1147_g[j] * 2.0f;
            if (n > 1.0f) {
                n = 1.0f;
            }
            if (n < 0.0f) {
                n = 0.0f;
            }
            final float n8 = n;
            int n3 = (int) (n8 * 100.0f + 155.0f);
            int k = (int) (n8 * n8 * 255.0f);
            int l = (int) (n8 * n8 * n8 * n8 * 128.0f);
            if (this.anaglyphEnabled) {
                final int n4 = (n3 * 30 + k * 59 + l * 11) / 100;
                final int n5 = (n3 * 30 + k * 70) / 100;
                final int n9 = (n3 * 30 + l * 70) / 100;
                n3 = n4;
                k = n5;
                l = n9;
            }
            this.imageData[j * 4 + 0] = (byte) n3;
            this.imageData[j * 4 + 1] = (byte) k;
            this.imageData[j * 4 + 2] = (byte) l;
            this.imageData[j * 4 + 3] = -1;
        }
    }
}
