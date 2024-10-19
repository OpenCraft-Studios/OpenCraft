
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

import net.opencraft.SkinHolder;
import net.opencraft.client.config.GameSettings;
import net.opencraft.renderer.GLAllocation;
import net.opencraft.renderer.texture.ImageProvider;
import net.opencraft.renderer.texture.TextureFX;

public class Renderer {

	private final Map<String, Integer> textureMap = new HashMap<>();
	private final Map<Integer, BufferedImage> textureIDToImage = new HashMap<>();
	private final Set<String> missingTextures = new HashSet<>();
	private final Map<String, SkinHolder> skinURLToTextureHolder = new HashMap<>();
	private final List<TextureFX> effects = new ArrayList<>();

	private final IntBuffer intBuffer;
	private final ByteBuffer byteBuffer;
	private final GameSettings options;
	private boolean h;

	public Renderer(GameSettings options) {
		intBuffer = BufferUtils.createIntBuffer(1);
		byteBuffer = BufferUtils.createByteBuffer(0x5F00000);
		h = false;
		this.options = Objects.requireNonNull(options, "settings mustn't be null!");
	}

	/**
	 * Loads a texture from the specified file name. If the texture is not found,
	 * the "missing texture" texture is loaded instead.
	 * 
	 * @return textureID
	 */
	public int loadTexture(String filename) {
		Integer textureID = textureMap.get(filename);
		if (textureID != null)
			return textureID;

		try {
			intBuffer.clear();
			GLAllocation.generateDisplayLists(intBuffer);
			final int firstUnusedTextureID = intBuffer.get(0);

			// Try to load the texture based on different prefixes
			InputStream textureStream = null;
			if (filename.startsWith("##") || filename.startsWith("%%"))
				textureStream = Renderer.class.getResourceAsStream(filename.substring(2));
			else
				textureStream = Renderer.class.getResourceAsStream(filename);

			// If the texture was found, load it
			if (textureStream == null) {
				// If the texture is not found, load the missing texture
				logMissingTexture(filename);
				return loadMissingTexture();
			}
			if (filename.startsWith("##"))
				this.bindTexture(this.scaledImage(ImageIO.read(textureStream)), firstUnusedTextureID);
			else {
				if (filename.startsWith("%%"))
					h = true;
				this.bindTexture(ImageIO.read(textureStream), firstUnusedTextureID);
				if (filename.startsWith("%%"))
					h = false;
			}
			textureMap.put(filename, firstUnusedTextureID);
			return firstUnusedTextureID;

		} catch (IOException ex) {
			// Fallback to missing texture in case of any I/O errors
			return loadMissingTexture();
		}
	}

	public int bindTexture(String filename) {
		int texId = loadTexture(filename);
		glBindTexture(GL_TEXTURE_2D, texId);

		return texId;
	}

	private void logMissingTexture(String textureName) {
		// Log the missing texture only if it hasn't been logged before
		if (missingTextures.add(textureName))
			System.err.println("Texture not found: " + textureName + ", using missing texture instead.");
	}

	private int loadMissingTexture() {
		// Path to missing texture or generate it programmatically
		final String missingTexturePath = "/assets/missing.png";

		try {
			InputStream missingTextureStream = Renderer.class.getResourceAsStream(missingTexturePath);

			if (missingTextureStream != null) {
				intBuffer.clear();
				GLAllocation.generateDisplayLists(intBuffer);
				final int missingTextureValue = intBuffer.get(0);
				this.bindTexture(ImageIO.read(missingTextureStream), missingTextureValue);
				textureMap.put(missingTexturePath, missingTextureValue);
				return missingTextureValue;
			}
			// Handle the case where the missing texture itself is missing
			throw new RuntimeException("Missing texture could not be loaded.");

		} catch (IOException e) {
			throw new RuntimeException("Missing texture could not be loaded.", e);
		}
	}

	/**
	 * This provides a blank image at 1/16 scale of the input image.
	 */
	private BufferedImage scaledImage(final BufferedImage input) {
		final int widthDiv16 = input.getWidth() / 16;
		final BufferedImage bufferedImage2 = new BufferedImage(16, input.getHeight() * widthDiv16,
				BufferedImage.TYPE_INT_ARGB);
		final Graphics graphics = bufferedImage2.getGraphics();
		for (int i = 0; i < widthDiv16; ++i)
			graphics.drawImage(input, -i * 16, i * input.getHeight(), null);
		graphics.dispose();
		return bufferedImage2;
	}

	/**
	 * Registers a texture and returns the texture ID.
	 * 
	 * @return textureID
	 */
	public int registerTexture(final BufferedImage bufferedImage) {
		intBuffer.clear();
		GLAllocation.generateDisplayLists(intBuffer);
		final int textureID = intBuffer.get(0);
		this.bindTexture(bufferedImage, textureID);
		textureIDToImage.put(textureID, bufferedImage);
		return textureID;
	}

	/**
	 * Binds the specified image to the specified texture ID.
	 */
	public void bindTexture(final BufferedImage bi, final int textureID) {
		glBindTexture(GL_TEXTURE_2D, textureID);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, h ? GL_CLAMP : GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, h ? GL_CLAMP : GL_REPEAT);

		int width = bi.getWidth();
		int height = bi.getHeight();
		int[] pixels_raw = new int[width * height];
		byte[] array2 = new byte[width * height * 4];
		bi.getRGB(0, 0, width, height, pixels_raw, 0, width);
		for (int i = 0; i < pixels_raw.length; ++i) {
			final int n = pixels_raw[i] >> 24 & 0xFF;
			int n2 = pixels_raw[i] >> 16 & 0xFF;
			int n3 = pixels_raw[i] >> 8 & 0xFF;
			int n4 = pixels_raw[i] & 0xFF;
			if (options != null && options.anaglyph.get()) {
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
		byteBuffer.clear();
		byteBuffer.put(array2);
		byteBuffer.position(0).limit(array2.length);
		glTexImage2D(GL_TEXTURE_2D, 0, 6408, width, height, 0, 6408, 5121, byteBuffer);
	}

	/**
	 * Deletes the texture with the specified ID.
	 */
	public void deleteTexture(final int textureID) {
		textureIDToImage.remove(textureID);
		intBuffer.clear();
		intBuffer.put(textureID);
		intBuffer.flip();
		glDeleteTextures(intBuffer);
	}

	/**
	 * Loads and binds a texture from the specified URL or file name.
	 * 
	 * @return textureID
	 */
	public int loadAndBindTexture(final String textureURL, final String textureFileName) {
		final SkinHolder holder = skinURLToTextureHolder.get(textureURL);
		if (holder != null && holder.image != null && !holder.isBound) {
			if (holder.textureID < 0)
				holder.textureID = this.registerTexture(holder.image);
			else
				this.bindTexture(holder.image, holder.textureID);
			holder.isBound = true;
		}
		if (holder == null || holder.textureID < 0)
			return this.loadTexture(textureFileName);
		return holder.textureID;
	}

	public SkinHolder registerNewTextureHolder(final String skinURL, final ImageProvider p) {
		final SkinHolder holder = skinURLToTextureHolder.get(skinURL);
		if (holder == null)
			skinURLToTextureHolder.put(skinURL, new SkinHolder(skinURL, p));
		else
			++holder.useCount;
		return holder;
	}

	public void deleteTextureIfUnused(final String string) {
		final SkinHolder skinHolder = skinURLToTextureHolder.get(string);
		if (skinHolder != null) {
			--skinHolder.useCount;
			if (skinHolder.useCount == 0) {
				if (skinHolder.textureID >= 0)
					this.deleteTexture(skinHolder.textureID);

				skinURLToTextureHolder.remove(string);
			} /*
				 * else { chat bother System.out.println("SkinHolder not removed, use count: " +
				 * skinHolder.useCount); }
				 */
		}
	}

	public void registerTextureFX(final TextureFX effect) {
		effects.add(effect);
		effect.onTick();
	}

	public void updateDynamicTextures() {
		for (final TextureFX textureFX : effects) {
			textureFX.anaglyphEnabled = options.anaglyph.get();
			textureFX.onTick();
			byteBuffer.clear();
			byteBuffer.put(textureFX.imageData);
			byteBuffer.position(0).limit(textureFX.imageData.length);
			for (int j = 0; j < textureFX.tileSize; ++j)
				for (int k = 0; k < textureFX.tileSize; ++k)
					glTexSubImage2D(GL_TEXTURE_2D, 0, textureFX.iconIndex % 16 * 16 + j * 16,
							textureFX.iconIndex / 16 * 16 + k * 16, 16, 16, 6408, 5121, byteBuffer);
		}
		for (final TextureFX textureFX2 : effects)
			if (textureFX2.textureId > 0) {
				byteBuffer.clear();
				byteBuffer.put(textureFX2.imageData);
				byteBuffer.position(0).limit(textureFX2.imageData.length);
				glBindTexture(GL_TEXTURE_2D, textureFX2.textureId);
				glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, 16, 16, 6408, 5121, byteBuffer);
			}
	}

	public void refreshTextures() {
		for (final int intValue : textureIDToImage.keySet())
			this.bindTexture(textureIDToImage.get(intValue), intValue);
		for (SkinHolder holder : skinURLToTextureHolder.values())
			holder.isBound = false;
		for (final String s : textureMap.keySet())
			try {
				BufferedImage bufferedImage;
				if (s.startsWith("##"))
					bufferedImage = this.scaledImage(ImageIO.read(Renderer.class.getResourceAsStream(s.substring(2))));
				else if (s.startsWith("%%")) {
					h = true;
					bufferedImage = ImageIO.read(Renderer.class.getResourceAsStream(s.substring(2)));
					h = false;
				} else
					bufferedImage = ImageIO.read(Renderer.class.getResourceAsStream(s));
				this.bindTexture(bufferedImage, textureMap.get(s));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
	}

	/**
	 * Binds texture to the specified ID. ID must be > 0
	 */
	public void bindTexture(final int id) {
		if (id < 0) {
			System.err.println("Refusing to bind texture. Invalid texture ID: " + id);
			return;
		}

		glBindTexture(GL_TEXTURE_2D, id);
	}

}
