
package net.opencraft.renderer.texture;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import net.opencraft.ImageProvider;

public class Texture implements ImageProvider {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 32;

    private int[] bufferedImageData;

    public BufferedImage a(final BufferedImage bufferedImage) {
        if (bufferedImage == null) {
            return null;
        }
        final BufferedImage bufferedImage2 = new BufferedImage(WIDTH, HEIGHT, 2);
        final Graphics graphics = bufferedImage2.getGraphics();
        graphics.drawImage(bufferedImage, 0, 0, null);
        graphics.dispose();
        bufferedImageData = ((DataBufferInt) bufferedImage2.getRaster().getDataBuffer()).getData();
        this.b(0, 0, 32, 16);
        this.a(32, 0, 64, 32);
        this.b(0, 16, 64, 32);
        int n = 0;
        for (int i = 32; i < 64; ++i) {
            for (int j = 0; j < 16; ++j) {
                final int n2 = bufferedImageData[i + j * 64];
                if ((n2 >> 24 & 0xFF) < 128) {
                    n = 1;
                }
            }
        }
        if (n == 0) {
            for (int i = 32; i < 64; ++i) {
                for (int j = 0; j < 16; ++j) {
                    final int n2 = bufferedImageData[i + j * 64];
                    if ((n2 >> 24 & 0xFF) < 128) {
                        n = 1;
                    }
                }
            }
        }
        return bufferedImage2;
    }

    private void a(final int integer1, final int integer2, final int integer3, final int integer4) {
        if (this.c(integer1, integer2, integer3, integer4)) {
            return;
        }
        for (int i = integer1; i < integer3; ++i) {
            for (int j = integer2; j < integer4; ++j) {
                bufferedImageData[i + j * WIDTH] &= 0xFFFFFF;
            }
        }
    }

    private void b(final int outerLoopStart, final int innerLoopStart, final int outerLoopEnd, final int innerLoopEnd) {
        for (int i = outerLoopStart; i < outerLoopEnd; ++i) {
            for (int j = innerLoopStart; j < innerLoopEnd; ++j) {
                bufferedImageData[i + j * HEIGHT] |= 0xFF000000;
            }
        }
    }

    private boolean c(final int outerLoopStart, final int innerLoopStart, final int outerLoopEnd, final int innerLoopEnd) {
        for (int i = outerLoopStart; i < outerLoopEnd; ++i) {
            for (int j = innerLoopStart; j < innerLoopEnd; ++j) {
                if ((bufferedImageData[i + j * WIDTH] >> 24 & 0xFF) < 128) {
                    return true;
                }
            }
        }
        return false;
    }
}
