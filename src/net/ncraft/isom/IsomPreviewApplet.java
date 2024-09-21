
package net.ncraft.isom;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import net.ncraft.client.canvas.CanvasIsomPreview;

public class IsomPreviewApplet extends Applet {

    private CanvasIsomPreview canvas;

    public IsomPreviewApplet() {
        this.canvas = new CanvasIsomPreview();
        this.setLayout((LayoutManager) new BorderLayout());
        this.add((Component) this.canvas, "Center");
    }

    public void start() {
        this.canvas.startThreads();
    }

    public void stop() {
        this.canvas.exit();
    }
}
