
package net.opencraft;

import java.awt.image.BufferedImage;

public class TextureHolder {

    public BufferedImage image;
    /** The number of times this texture has been registered */
    public int useCount;
    /** The OpenGL texture ID. -1 when unset */
    public int textureID;
    public boolean isBound;

    public TextureHolder(final String url, final ImageProvider provider) {
        this.useCount = 1;
        this.textureID = -1;
        this.isBound = false;
        new ImageLoader(this, url, provider).start();
    }
}
