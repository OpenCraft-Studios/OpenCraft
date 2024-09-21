
package net.opencraft.client.canvas;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.opencraft.PanelCrashReport;

class CanvasMojangLogo extends Canvas {

    private BufferedImage logo;

    public CanvasMojangLogo() {
        try {
            this.logo = ImageIO.read(PanelCrashReport.class.getResource("/assets/gui/logo.png"));
        } catch (IOException ex) {
        }
        final int n = 100;
        this.setPreferredSize(new Dimension(n, n));
        this.setMinimumSize(new Dimension(n, n));
    }

    public void paint(final Graphics graphics) {
        super.paint(graphics);
        graphics.drawImage((Image) this.logo, this.getWidth() / 2 - this.logo.getWidth() / 2, 32, (ImageObserver) null);
    }
}
