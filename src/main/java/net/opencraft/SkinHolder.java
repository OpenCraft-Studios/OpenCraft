
package net.opencraft;

import net.opencraft.renderer.texture.ImageProvider;

import java.awt.image.BufferedImage;

public class SkinHolder {

    public BufferedImage image;
    /** The number of times this texture has been registered */
    public int useCount;
    /** The OpenGL texture ID. -1 when unset */
    public int textureID;
    public boolean isBound;
    public final String targetURL;
    public final ImageProvider provider;

    public SkinHolder(final String url, final ImageProvider provider) {
        this.useCount = 1;
        this.textureID = -1;
        this.isBound = false;
        this.targetURL = url;
        this.provider = provider;
    }

}
