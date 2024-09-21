
package net.opencraft.client.renderer.texture;

import net.opencraft.blocks.Block;

public class TextureFlamesFX extends TextureFX {

    protected float[] field_1133_g;
    protected float[] field_1132_h;

    public TextureFlamesFX(final int integer) {
        super(Block.fire.blockIndexInTexture + integer * 16);
        this.field_1133_g = new float[320];
        this.field_1132_h = new float[320];
    }

    @Override
    public void onTick() {
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 20; ++j) {
                int n = 18;
                float n2 = this.field_1133_g[i + (j + 1) % 20 * 16] * n;
                for (int k = i - 1; k <= i + 1; ++k) {
                    for (int l = j; l <= j + 1; ++l) {
                        final int n3 = k;
                        final int n4 = l;
                        if (n3 >= 0 && n4 >= 0 && n3 < 16 && n4 < 20) {
                            n2 += this.field_1133_g[n3 + n4 * 16];
                        }
                        ++n;
                    }
                }
                this.field_1132_h[i + j * 16] = n2 / (n * 1.06f);
                if (j >= 19) {
                    this.field_1132_h[i + j * 16] = (float) (Math.random() * Math.random() * Math.random() * 4.0 + Math.random() * 0.10000000149011612 + 0.20000000298023224);
                }
            }
        }
        final float[] field_1132_h = this.field_1132_h;
        this.field_1132_h = this.field_1133_g;
        this.field_1133_g = field_1132_h;
        for (int j = 0; j < 256; ++j) {
            float n5 = this.field_1133_g[j] * 1.8f;
            if (n5 > 1.0f) {
                n5 = 1.0f;
            }
            if (n5 < 0.0f) {
                n5 = 0.0f;
            }
            float n2 = n5;
            int k = (int) (n2 * 155.0f + 100.0f);
            int l = (int) (n2 * n2 * 255.0f);
            int n3 = (int) (n2 * n2 * n2 * n2 * n2 * n2 * n2 * n2 * n2 * n2 * 255.0f);
            int n4 = 255;
            if (n2 < 0.5f) {
                n4 = 0;
            }
            n2 = (n2 - 0.5f) * 2.0f;
            if (this.anaglyphEnabled) {
                final int n6 = (k * 30 + l * 59 + n3 * 11) / 100;
                final int n7 = (k * 30 + l * 70) / 100;
                final int n8 = (k * 30 + n3 * 70) / 100;
                k = n6;
                l = n7;
                n3 = n8;
            }
            this.imageData[j * 4 + 0] = (byte) k;
            this.imageData[j * 4 + 1] = (byte) l;
            this.imageData[j * 4 + 2] = (byte) n3;
            this.imageData[j * 4 + 3] = (byte) n4;
        }
    }
}
