
package net.opencraft.client.texture;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;
import net.opencraft.p;

public class Texture implements p {

    private int[] a;
    private int b;
    private int c;

    public BufferedImage a(final BufferedImage bufferedImage) {
        if (bufferedImage == null) {
            return null;
        }
        this.b = 64;
        this.c = 32;
        final BufferedImage bufferedImage2 = new BufferedImage(this.b, this.c, 2);
        final Graphics graphics = bufferedImage2.getGraphics();
        graphics.drawImage((Image) bufferedImage, 0, 0, (ImageObserver) null);
        graphics.dispose();
        this.a = ((DataBufferInt) bufferedImage2.getRaster().getDataBuffer()).getData();
        this.b(0, 0, 32, 16);
        this.a(32, 0, 64, 32);
        this.b(0, 16, 64, 32);
        int n = 0;
        for (int i = 32; i < 64; ++i) {
            for (int j = 0; j < 16; ++j) {
                final int n2 = this.a[i + j * 64];
                if ((n2 >> 24 & 0xFF) < 128) {
                    n = 1;
                }
            }
        }
        if (n == 0) {
            for (int i = 32; i < 64; ++i) {
                for (int j = 0; j < 16; ++j) {
                    final int n2 = this.a[i + j * 64];
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
                final int[] a = this.a;
                final int n = i + j * this.b;
                a[n] &= 0xFFFFFF;
            }
        }
    }

    private void b(final int integer1, final int integer2, final int integer3, final int integer4) {
        for (int i = integer1; i < integer3; ++i) {
            for (int j = integer2; j < integer4; ++j) {
                final int[] a = this.a;
                final int n = i + j * this.b;
                a[n] |= 0xFF000000;
            }
        }
    }

    private boolean c(final int integer1, final int integer2, final int integer3, final int integer4) {
        for (int i = integer1; i < integer3; ++i) {
            for (int j = integer2; j < integer4; ++j) {
                if ((this.a[i + j * this.b] >> 24 & 0xFF) < 128) {
                    return true;
                }
            }
        }
        return false;
    }
}
