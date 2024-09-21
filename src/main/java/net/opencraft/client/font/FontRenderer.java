
package net.opencraft.client.font;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import net.opencraft.client.renderer.GLAllocation;
import net.opencraft.client.renderer.Tessellator;
import net.opencraft.client.renderer.entity.Renderer;
import net.opencraft.client.settings.GameSettings;

public class FontRenderer {

    private int[] charWidth = new int[256];
    private int fontTextureName = 0;
    private int fontDisplayLists;
    private IntBuffer buffer = BufferUtils.createIntBuffer(1024);

    public FontRenderer(GameSettings ja2, String string, Renderer id2) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        BufferedImage bi;
        try {
            bi = ImageIO.read(Renderer.class.getResourceAsStream(string));
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
        int n8 = bi.getWidth();
        int n9 = bi.getHeight();
        int[] nArray = new int[n8 * n9];
        bi.getRGB(0, 0, n8, n9, nArray, 0, n8);
        for (int i = 0; i < 128; ++i) {
            n7 = i % 16;
            n6 = i / 16;
            boolean bl = false;
            for (n5 = 0; n5 < 8 && !bl; ++n5) {
                n4 = n7 * 8 + n5;
                bl = true;
                for (n3 = 0; n3 < 8 && bl; ++n3) {
                    n2 = (n6 * 8 + n3) * n8;
                    n = nArray[n4 + n2] & 0xFF;
                    if (n <= 128) {
                        continue;
                    }
                    bl = false;
                }
            }
            if (i == 32) {
                n5 = 4;
            }
            this.charWidth[i] = n5;
        }
        this.fontTextureName = id2.getTexture(string);
        this.fontDisplayLists = GLAllocation.generateDisplayLists(288);
        Tessellator ag2 = Tessellator.instance;
        for (n7 = 0; n7 < 256; ++n7) {
            GL11.glNewList((int) (this.fontDisplayLists + n7), (int) 4864);
            ag2.startDrawingQuads();
            n6 = n7 % 16 * 8;
            n5 = n7 / 16 * 8;
            float f = 7.99f;
            ag2.addVertexWithUV(0.0, 0.0f + f, 0.0, (float) n6 / 128.0f, ((float) n5 + f) / 128.0f);
            ag2.addVertexWithUV(0.0f + f, 0.0f + f, 0.0, ((float) n6 + f) / 128.0f, ((float) n5 + f) / 128.0f);
            ag2.addVertexWithUV(0.0f + f, 0.0, 0.0, ((float) n6 + f) / 128.0f, (float) n5 / 128.0f);
            ag2.addVertexWithUV(0.0, 0.0, 0.0, (float) n6 / 128.0f, (float) n5 / 128.0f);
            ag2.draw();
            GL11.glTranslatef((float) this.charWidth[n7], (float) 0.0f, (float) 0.0f);
            GL11.glEndList();
        }
        for (n7 = 0; n7 < 32; ++n7) {
            n6 = (n7 & 8) * 8;
            n5 = (n7 & 1) * 191 + n6;
            int n10 = ((n7 & 2) >> 1) * 191 + n6;
            n4 = ((n7 & 4) >> 2) * 191 + n6;
            int n11 = n3 = n7 >= 16 ? 1 : 0;
            if (ja2.anaglyph) {
                n2 = (n4 * 30 + n10 * 59 + n5 * 11) / 100;
                n = (n4 * 30 + n10 * 70) / 100;
                int n12 = (n4 * 30 + n5 * 70) / 100;
                n4 = n2;
                n10 = n;
                n5 = n12;
            }
            n7 += 2;
            if (n3 != 0) {
                n4 /= 4;
                n10 /= 4;
                n5 /= 4;
            }
            GL11.glColor4f((float) ((float) n4 / 255.0f), (float) ((float) n10 / 255.0f), (float) ((float) n5 / 255.0f), (float) 1.0f);
        }
    }

    public void drawStringWithShadow2(final String string, final int integer2, final int integer3, final int integer4) {
        this.renderString2(string, integer2 + 1, integer3 + 1, integer4, true);
        this.drawString2(string, integer2, integer3, integer4);
    }

    public void drawString2(final String string, final int integer2, final int integer3, final int integer4) {
        this.renderString2(string, integer2, integer3, integer4, false);
    }

    public void renderString2(final String string, final int integer2, final int integer3, int integer4, final boolean boolean5) {
        if (string == null) {
            return;
        }
        if (boolean5) {
            integer4 = (integer4 & 0xFCFCFC) >> 2;
        }
        GL11.glBindTexture(3553, this.fontTextureName);
        final float n = (integer4 >> 16 & 0xFF) / 255.0f;
        final float n2 = (integer4 >> 8 & 0xFF) / 255.0f;
        final float n3 = (integer4 & 0xFF) / 255.0f;
        float n4 = (integer4 >> 24 & 0xFF) / 255.0f;
        n4 = 1.0f;
        GL11.glColor4f(n, n2, n3, n4);
        this.buffer.clear();
        GL11.glPushMatrix();
        GL11.glTranslatef((float) integer2, (float) integer3, 0.0f);
        for (int i = 0; i < string.length(); ++i) {
            while (string.charAt(i) == '&' && string.length() > i + 1) {
                int index = "0123456789abcdef".indexOf((int) string.charAt(i + 1));
                if (index < 0 || index > 15) {
                    index = 15;
                }
                this.buffer.put(this.fontDisplayLists + 256 + index + (boolean5 ? 16 : 0));
                if (this.buffer.remaining() == 0) {
                    this.buffer.flip();
                    GL11.glCallLists(this.buffer);
                    this.buffer.clear();
                }
                i += 2;
            }
            this.buffer.put(this.fontDisplayLists + string.charAt(i));
            if (this.buffer.remaining() == 0) {
                this.buffer.flip();
                GL11.glCallLists(this.buffer);
                this.buffer.clear();
            }
        }
        this.buffer.flip();
        GL11.glCallLists(this.buffer);
        GL11.glPopMatrix();
    }

    public int getStringWidth(final String string) {
        if (string == null) {
            return 0;
        }
        int n = 0;
        for (int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) == '&') {
                ++i;
            } else {
                n += this.charWidth[string.charAt(i)];
            }
        }
        return n;
    }
}
