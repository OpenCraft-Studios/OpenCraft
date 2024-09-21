
package net.opencraft.client.texture;

public class TextureFX {

    public byte[] imageData;
    public int iconIndex;
    public boolean anaglyphEnabled;
    public int textureId;
    public int tileSize;

    public TextureFX(final int integer) {
        this.imageData = new byte[1024];
        this.anaglyphEnabled = false;
        this.textureId = 0;
        this.tileSize = 1;
        this.iconIndex = integer;
    }

    public void onTick() {
    }
}
