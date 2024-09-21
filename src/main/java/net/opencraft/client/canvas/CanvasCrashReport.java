
package net.opencraft.client.canvas;

import java.awt.Canvas;
import java.awt.Dimension;

class CanvasCrashReport extends Canvas {

    public CanvasCrashReport(final int integer) {
        this.setPreferredSize(new Dimension(integer, integer));
        this.setMinimumSize(new Dimension(integer, integer));
    }
}
