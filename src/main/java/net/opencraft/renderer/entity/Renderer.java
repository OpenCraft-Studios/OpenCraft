
package net.opencraft.renderer.entity;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.*;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import net.opencraft.ImageProvider;
import net.opencraft.ei;
import net.opencraft.client.config.GameSettings;
import net.opencraft.renderer.GLAllocation;
import net.opencraft.renderer.texture.TextureFX;

public class Renderer {

	private HashMap<String, Integer> a;
	private HashMap<Integer, BufferedImage> b;
	private IntBuffer c;
	private ByteBuffer d;
	private List e;
	private Map f;
	private GameSettings options;
	private boolean h;
	private Set<String> missingTextures = new HashSet<>();

	public Renderer(final GameSettings ja) {
		this.a = new HashMap();
		this.b = new HashMap();
		this.c = BufferUtils.createIntBuffer(1);
		this.d = BufferUtils.createByteBuffer(99999999);
		this.e = (List) new ArrayList();
		this.f = (Map) new HashMap();
		this.h = false;
		this.options = ja;
	}

	public int getTextureOld(final String string) {
		final Integer n = (Integer) this.a.get(string);
		if (n != null) {
			return n;
		}
		try {
			this.c.clear();
			GLAllocation.generateDisplayLists(this.c);
			final int value = this.c.get(0);

			if (string.startsWith("##")) {
				this.a(this.b(ImageIO.read(Renderer.class.getResourceAsStream(string.substring(2)))), value);
			} else if (string.startsWith("%%")) {
				this.h = true;
				this.a(ImageIO.read(Renderer.class.getResourceAsStream(string.substring(2))), value);
				this.h = false;
			} else {
				this.a(ImageIO.read(Renderer.class.getResourceAsStream(string)), value);
			}

			this.a.put(string, value);
			return value;

		} catch (IOException ex) {
			// Load the missing texture when the specified texture is not found
			System.err.println("Texture not found: " + string + ", using missing texture instead.");

			// Define the path for the missing texture (you need to provide this texture
			// file in the assets)
			final String missingTexturePath = "/assets/missing.png";

			try {
				// Try loading the missing texture
				this.c.clear();
				GLAllocation.generateDisplayLists(this.c);
				final int missingTextureValue = this.c.get(0);
				this.a(ImageIO.read(Renderer.class.getResourceAsStream(missingTexturePath)), missingTextureValue);
				this.a.put(string, missingTextureValue);
				return missingTextureValue;

			} catch (IOException missingTextureEx) {
				throw new RuntimeException("Missing texture also could not be loaded.");
			}
		}
	}

	public int getTexture(final String string) {
		final Integer n = (Integer) this.a.get(string);
		if (n != null) {
			return n;
		}
		try {
			this.c.clear();
			GLAllocation.generateDisplayLists(this.c);
			final int value = this.c.get(0);

			// Try to load the texture based on different prefixes
			InputStream textureStream = null;
			if (string.startsWith("##")) {
				textureStream = Renderer.class.getResourceAsStream(string.substring(2));
			} else if (string.startsWith("%%")) {
				textureStream = Renderer.class.getResourceAsStream(string.substring(2));
			} else {
				textureStream = Renderer.class.getResourceAsStream(string);
			}

			// If the texture was found, load it
			if (textureStream != null) {
				if (string.startsWith("##")) {
					this.a(this.b(ImageIO.read(textureStream)), value);
				} else {
					if (string.startsWith("%%")) {
						this.h = true;
					}
					this.a(ImageIO.read(textureStream), value);
					if (string.startsWith("%%")) {
						this.h = false;
					}
				}
				this.a.put(string, value);
				return value;
			} else {
				// If the texture is not found, load the missing texture
				logMissingTexture(string);
				return loadMissingTexture();
			}

		} catch (IOException ex) {
			// Fallback to missing texture in case of any I/O errors
			return loadMissingTexture();
		}
	}

	private void logMissingTexture(String textureName) {
		// Log the missing texture only if it hasn't been logged before
		if (missingTextures.add(textureName)) {
			System.err.println("Texture not found: " + textureName + ", using missing texture instead.");
		}
	}

	private int loadMissingTexture() {
		// Path to missing texture or generate it programmatically
		final String missingTexturePath = "/assets/missing.png";

		try {
			InputStream missingTextureStream = Renderer.class.getResourceAsStream(missingTexturePath);

			if (missingTextureStream != null) {
				this.c.clear();
				GLAllocation.generateDisplayLists(this.c);
				final int missingTextureValue = this.c.get(0);
				this.a(ImageIO.read(missingTextureStream), missingTextureValue);
				this.a.put(missingTexturePath, missingTextureValue);
				return missingTextureValue;
			} else {
				// Handle the case where the missing texture itself is missing
				throw new RuntimeException("Missing texture could not be loaded.");
			}

		} catch (IOException e) {
			throw new RuntimeException("Missing texture could not be loaded.", e);
		}
	}

	private BufferedImage b(final BufferedImage bi) {
		final int n = bi.getWidth() / 16;
		final BufferedImage bufferedImage2 = new BufferedImage(16, bi.getHeight() * n, 2);
		final Graphics graphics = bufferedImage2.getGraphics();
		for (int i = 0; i < n; ++i) {
			graphics.drawImage(bi, -i * 16, i * bi.getHeight(), null);
		}
		graphics.dispose();
		return bufferedImage2;
	}

	public int a(final BufferedImage bufferedImage) {
		this.c.clear();
		GLAllocation.generateDisplayLists(this.c);
		final int value = this.c.get(0);
		this.a(bufferedImage, value);
		this.b.put(value, bufferedImage);
		return value;
	}

	public void a(final BufferedImage bufferedImage, final int integer) {
		glBindTexture(GL_TEXTURE_2D, integer);
		glTexParameteri(GL_TEXTURE_2D, 10241, 9728);
		glTexParameteri(GL_TEXTURE_2D, 10240, 9728);
		if (this.h) {
			glTexParameteri(GL_TEXTURE_2D, 10242, 10496);
			glTexParameteri(GL_TEXTURE_2D, 10243, 10496);
		} else {
			glTexParameteri(GL_TEXTURE_2D, 10242, 10497);
			glTexParameteri(GL_TEXTURE_2D, 10243, 10497);
		}
		final int width = bufferedImage.getWidth();
		final int height = bufferedImage.getHeight();
		final int[] array = new int[width * height];
		final byte[] array2 = new byte[width * height * 4];
		bufferedImage.getRGB(0, 0, width, height, array, 0, width);
		for (int i = 0; i < array.length; ++i) {
			final int n = array[i] >> 24 & 0xFF;
			int n2 = array[i] >> 16 & 0xFF;
			int n3 = array[i] >> 8 & 0xFF;
			int n4 = array[i] & 0xFF;
			if (this.options != null && this.options.anaglyph) {
				final int n5 = (n2 * 30 + n3 * 59 + n4 * 11) / 100;
				final int n6 = (n2 * 30 + n3 * 70) / 100;
				final int n7 = (n2 * 30 + n4 * 70) / 100;
				n2 = n5;
				n3 = n6;
				n4 = n7;
			}
			array2[i * 4 + 0] = (byte) n2;
			array2[i * 4 + 1] = (byte) n3;
			array2[i * 4 + 2] = (byte) n4;
			array2[i * 4 + 3] = (byte) n;
		}
		this.d.clear();
		this.d.put(array2);
		this.d.position(0).limit(array2.length);
		glTexImage2D(GL_TEXTURE_2D, 0, 6408, width, height, 0, 6408, 5121, this.d);
	}

	public void a(final int integer) {
		this.b.remove(integer);
		this.c.clear();
		this.c.put(integer);
		this.c.flip();
		glDeleteTextures(this.c);
	}

	public int a(final String string1, final String string2) {
		final ei ei = (ei) this.f.get(string1);
		if (ei != null && ei.a != null && !ei.d) {
			if (ei.c < 0) {
				ei.c = this.a(ei.a);
			} else {
				this.a(ei.a, ei.c);
			}
			ei.d = true;
		}
		if (ei == null || ei.c < 0) {
			return this.getTexture(string2);
		}
		return ei.c;
	}

	public ei a(final String string, final ImageProvider p) {
		final ei ei = (ei) this.f.get(string);
		if (ei == null) {
			this.f.put(string, new ei(string, p));
		} else {
			final ei ei2 = ei;
			++ei2.b;
		}
		return ei;
	}

	public void b(final String string) {
		final ei ei = (ei) this.f.get(string);
		if (ei != null) {
			final ei ei2 = ei;
			--ei2.b;
			if (ei.b == 0) {
				if (ei.c >= 0) {
					this.a(ei.c);
				}
				this.f.remove(string);
			}
		}
	}

	public void registerTextureFX(final TextureFX at) {
		this.e.add(at);
		at.onTick();
	}

	public void updateDynamicTextures() {
		for (int i = 0; i < this.e.size(); ++i) {
			final TextureFX textureFX = (TextureFX) this.e.get(i);
			textureFX.anaglyphEnabled = this.options.anaglyph;
			textureFX.onTick();
			this.d.clear();
			this.d.put(textureFX.imageData);
			this.d.position(0).limit(textureFX.imageData.length);
			for (int j = 0; j < textureFX.tileSize; ++j) {
				for (int k = 0; k < textureFX.tileSize; ++k) {
					glTexSubImage2D(GL_TEXTURE_2D, 0, textureFX.iconIndex % 16 * 16 + j * 16,
							textureFX.iconIndex / 16 * 16 + k * 16, 16, 16, 6408, 5121, this.d);
				}
			}
		}
		for (int i = 0; i < this.e.size(); ++i) {
			final TextureFX textureFX2 = (TextureFX) this.e.get(i);
			if (textureFX2.textureId > 0) {
				this.d.clear();
				this.d.put(textureFX2.imageData);
				this.d.position(0).limit(textureFX2.imageData.length);
				glBindTexture(GL_TEXTURE_2D, textureFX2.textureId);
				glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, 16, 16, 6408, 5121, this.d);
			}
		}
	}

	public void refreshTextures() {
		for (final int intValue : this.b.keySet()) {
			this.a((BufferedImage) this.b.get(intValue), intValue);
		}
		final Iterator iterator2 = this.f.values().iterator();
		while (iterator2.hasNext()) {
			((ei) iterator2.next()).d = false;
		}
		for (final String s : this.a.keySet()) {
			try {
				BufferedImage bufferedImage;
				if (s.startsWith("##")) {
					bufferedImage = this.b(ImageIO.read(Renderer.class.getResourceAsStream(s.substring(2))));
				} else if (s.startsWith("%%")) {
					this.h = true;
					bufferedImage = ImageIO.read(Renderer.class.getResourceAsStream(s.substring(2)));
					this.h = false;
				} else {
					bufferedImage = ImageIO.read(Renderer.class.getResourceAsStream(s));
				}
				this.a(bufferedImage, (int) this.a.get(s));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void bindTexture(final int id) {
		if (id < 0)
			return;
		
		glBindTexture(GL_TEXTURE_2D, id);
	}
}
