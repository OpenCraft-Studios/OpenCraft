
package net.opencraft;

public class ScaledResolution {

    private int scaledWidth;
    private int scaledHeight;

    public ScaledResolution(final int integer1, final int integer2) {
        this.scaledWidth = integer1;
        this.scaledHeight = integer2;
        int n;
        for (n = 1; this.scaledWidth / (n + 1) >= 320 && this.scaledHeight / (n + 1) >= 240; ++n) {
        }
        this.scaledWidth /= n;
        this.scaledHeight /= n;
    }

    public int getScaledWidth() {
        return this.scaledWidth;
    }

    public int getScaledHeight() {
        return this.scaledHeight;
    }
}
