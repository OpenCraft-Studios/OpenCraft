
package net.opencraft;

import net.opencraft.client.canvas.CanvasIsomPreview;

public class ThreadRunIsoClient extends Thread {

    public final /* synthetic */ CanvasIsomPreview isoCanvas;

    public ThreadRunIsoClient(final CanvasIsomPreview be) {
        this.isoCanvas = be;
    }

    @Override
    public void run() {
        while (this.isoCanvas.running) {
            this.isoCanvas.showNextBuffer();
            try {
                Thread.sleep(1L);
            } catch (Exception ex) {
            }
        }
    }
}
