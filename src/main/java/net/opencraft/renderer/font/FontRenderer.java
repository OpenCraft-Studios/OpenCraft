package net.opencraft.renderer.font;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import net.opencraft.client.config.GameSettings;
import net.opencraft.renderer.GLAllocation;
import net.opencraft.renderer.Tessellator;
import net.opencraft.renderer.entity.Renderer;

public final class FontRenderer {

	public static final int SHADOW_MASK = 0xFCFCFC;

	private int[] charWidth = new int[256];
	private int fontTextureName = 0;
	private int fontDisplayLists;
	private IntBuffer buffer = BufferUtils.createIntBuffer(1024);

	public FontRenderer(GameSettings options, String string, Renderer id2) {
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
		this.fontTextureName = id2.loadTexture(string);
		this.fontDisplayLists = GLAllocation.generateDisplayLists(288);
		Tessellator ag2 = Tessellator.instance;
		for (n7 = 0; n7 < 256; ++n7) {
			glNewList((int) (this.fontDisplayLists + n7), (int) 4864);
			ag2.beginQuads();
			n6 = n7 % 16 * 8;
			n5 = n7 / 16 * 8;
			float f = 7.99f;
			ag2.vertexUV(0.0, 0.0f + f, 0.0, (float) n6 / 128.0f, ((float) n5 + f) / 128.0f);
			ag2.vertexUV(0.0f + f, 0.0f + f, 0.0, ((float) n6 + f) / 128.0f, ((float) n5 + f) / 128.0f);
			ag2.vertexUV(0.0f + f, 0.0, 0.0, ((float) n6 + f) / 128.0f, (float) n5 / 128.0f);
			ag2.vertexUV(0.0, 0.0, 0.0, (float) n6 / 128.0f, (float) n5 / 128.0f);
			ag2.draw();
			glTranslatef((float) this.charWidth[n7], (float) 0.0f, (float) 0.0f);
			glEndList();
		}
		for (n7 = 0; n7 < 32; ++n7) {
			n6 = (n7 & 8) * 8;
			n5 = (n7 & 1) * 191 + n6;
			int n10 = ((n7 & 2) >> 1) * 191 + n6;
			n4 = ((n7 & 4) >> 2) * 191 + n6;
			int n11 = n3 = n7 >= 16 ? 1 : 0;
			if (options.anaglyph) {
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
			glColor4f((float) ((float) n4 / 255.0f), (float) ((float) n10 / 255.0f), (float) ((float) n5 / 255.0f),
					(float) 1.0f);
		}
	}

	public void drawShadow(String string, int x, int y, int color) {
		this.draw(string, x + 1, y + 1, color, true);
		this.draw(string, x, y, color);
	}

	public void draw(String text, int x, int y, int color) {
		this.draw(text, x, y, color, false);
	}

	public void draw(String text, int x, int y, int color, boolean shadow) {
		Objects.requireNonNull(text, "The text to be renderer mustn't be null!");
		if (shadow)
			color = (color & SHADOW_MASK) >> 2;

		glBindTexture(GL_TEXTURE_2D, this.fontTextureName);

		float r = (color >> 16 & 0xFF) / 255.0f;
		float g = (color >> 8 & 0xFF) / 255.0f;
		float b = (color & 0xFF) / 255.0f;

		glColor3f(r, g, b);

		this.buffer.clear();
		glPushMatrix();
		{
			glTranslatef(x, y, 0);
			for (int i = 0; i < text.length(); ++i) {
				while (text.charAt(i) == '&' && text.length() > i + 1) {
					int index = "0123456789abcdef".indexOf((int) text.charAt(i + 1));
					if (index < 0 || index > 15)
						index = 15;

					buffer.put(this.fontDisplayLists + 256 + index + (shadow ? 16 : 0));
					if (!buffer.hasRemaining()) {
						buffer.flip();
						glCallLists(this.buffer);
						buffer.clear();
					}
					i += 2;
				}
				
				buffer.put(this.fontDisplayLists + text.charAt(i));
				if (!buffer.hasRemaining()) {
					buffer.flip();
					glCallLists(this.buffer);
					buffer.clear();
				}
			}

			buffer.flip();
			glCallLists(this.buffer);
		}
		glPopMatrix();
	}

	public int width(final String string) {
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
