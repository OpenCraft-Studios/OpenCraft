
package net.opencraft.client.texture;

import java.io.IOException;
import javax.imageio.ImageIO;
import net.opencraft.block.Block;
import net.opencraft.util.MathHelper;

public class TextureGearsFX extends TextureFX {

    private int gearsInt1;
    private int[] gearsIntArray1;
    private int[] gearsIntArray2;
    private int gearsInt2;

    public TextureGearsFX(final int integer) {
        super(Block.gears.blockIndexInTexture + integer);
        this.gearsInt1 = 0;
        this.gearsIntArray1 = new int[1024];
        this.gearsIntArray2 = new int[1024];
        this.gearsInt2 = integer * 2 - 1;
        this.gearsInt1 = 2;
        try {
            ImageIO.read(TextureGearsFX.class.getResource("/assets/misc/gear.png")).getRGB(0, 0, 32, 32, this.gearsIntArray1, 0, 32);
            ImageIO.read(TextureGearsFX.class.getResource("/assets/misc/gearmiddle.png")).getRGB(0, 0, 16, 16, this.gearsIntArray2, 0, 16);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onTick() {
        this.gearsInt1 = (this.gearsInt1 + this.gearsInt2 & 0x3F);
        final float sin = MathHelper.sin(this.gearsInt1 / 64.0f * 3.1415927f * 2.0f);
        final float cos = MathHelper.cos(this.gearsInt1 / 64.0f * 3.1415927f * 2.0f);
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                final float n = (i / 15.0f - 0.5f) * 31.0f;
                final float n2 = (j / 15.0f - 0.5f) * 31.0f;
                final float n3 = cos * n - sin * n2;
                final float n4 = cos * n2 + sin * n;
                final int n5 = (int) (n3 + 16.0f);
                final int n6 = (int) (n4 + 16.0f);
                int n7 = 0;
                if (n5 >= 0 && n6 >= 0 && n5 < 32 && n6 < 32) {
                    n7 = this.gearsIntArray1[n5 + n6 * 32];
                    final int n8 = this.gearsIntArray2[i + j * 16];
                    if ((n8 >> 24 & 0xFF) > 128) {
                        n7 = n8;
                    }
                }
                final int n8 = n7 >> 16 & 0xFF;
                final int n9 = n7 >> 8 & 0xFF;
                final int n10 = n7 & 0xFF;
                final int n11 = ((n7 >> 24 & 0xFF) > 128) ? 255 : 0;
                final int n12 = i + j * 16;
                this.imageData[n12 * 4 + 0] = (byte) n8;
                this.imageData[n12 * 4 + 1] = (byte) n9;
                this.imageData[n12 * 4 + 2] = (byte) n10;
                this.imageData[n12 * 4 + 3] = (byte) n11;
            }
        }
    }
}
